package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador Web (Spring MVC) responsável por gerenciar as requisições HTTP
 * e integrar as classes de domínio da biblioteca com a interface HTML (index.html).
 */
@Controller
public class BibliotecaController {

    // Listas polimórficas que armazenam instâncias das subclasses de ItemAcervo e Usuario
    private List<ItemAcervo> acervo = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    /**
     * Construtor que inicializa o sistema com alguns objetos pré-cadastrados para teste.
     */
    public BibliotecaController() {
        acervo.add(new Livro("L1", "Java para Web"));
        acervo.add(new Revista("R1", "Spring Magazine"));
        acervo.add(new DVD("D1", "Curso de HTML"));
        usuarios.add(new Aluno("A1", "João Silva"));
        usuarios.add(new Professor("P1", "Maria Oliveira"));
    }

    /**
     * Rota principal (GET /) que envia as listas de acervo, usuários e empréstimos para a página index.html.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("acervo", acervo);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("emprestimos", emprestimos);
        return "index";
    }

    /**
     * Cadastra um novo item instanciando a subclasse correspondente (Livro, Revista ou DVD).
     */
    @PostMapping("/cadastrar-item")
    public String cadastrarItem(@RequestParam String id, @RequestParam String titulo, @RequestParam String tipo) {
        if (tipo.equals("Livro")) acervo.add(new Livro(id, titulo));
        else if (tipo.equals("Revista")) acervo.add(new Revista(id, titulo));
        else if (tipo.equals("DVD")) acervo.add(new DVD(id, titulo));
        return "redirect:/";
    }

    /**
     * Remove um item do acervo pelo ID, validando se ele existe e se não está emprestado.
     * Aceita tanto o parâmetro 'id' quanto 'idItem' e evita erro 400 caso o campo venha vazio.
     */
    @PostMapping("/remover-item")
    public String removerItem(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "idItem", required = false) String idItem,
            RedirectAttributes attributes) {

        String identificador = (id != null && !id.trim().isEmpty()) ? id.trim() : (idItem != null ? idItem.trim() : "");

        if (identificador.isEmpty()) {
            attributes.addFlashAttribute("erro", "Erro: Informe ou selecione o ID do item para remover!");
            return "redirect:/";
        }

        ItemAcervo item = acervo.stream()
                .filter(x -> x.getId().equalsIgnoreCase(identificador))
                .findFirst()
                .orElse(null);

        if (item == null) {
            attributes.addFlashAttribute("erro", "Erro: Item '" + identificador + "' não encontrado no acervo!");
        } else if (item.isEmprestado()) {
            attributes.addFlashAttribute("erro", "Erro: Não é possível remover um item que está emprestado!");
        } else {
            acervo.remove(item);
            attributes.addFlashAttribute("sucesso", "Item removido com sucesso!");
        }
        return "redirect:/";
    }

    /**
     * Cadastra um novo usuário instanciando a subclasse correspondente (Aluno ou Professor).
     */
    @PostMapping("/cadastrar-usuario")
    public String cadastrarUsuario(@RequestParam String matricula, @RequestParam String nome, @RequestParam String tipo) {
        if (tipo.equals("Aluno")) usuarios.add(new Aluno(matricula, nome));
        else if (tipo.equals("Professor")) usuarios.add(new Professor(matricula, nome));
        return "redirect:/";
    }

    /**
     * Remove um usuário pela matrícula, impedindo a exclusão caso possua empréstimos ativos.
     */
    @PostMapping("/remover-usuario")
    public String removerUsuario(@RequestParam(required = false) String matricula, RedirectAttributes attributes) {
        if (matricula == null || matricula.trim().isEmpty()) {
            attributes.addFlashAttribute("erro", "Erro: Informe a matrícula do usuário para remover!");
            return "redirect:/";
        }

        String mat = matricula.trim();
        Usuario u = usuarios.stream().filter(x -> x.getMatricula().equalsIgnoreCase(mat)).findFirst().orElse(null);
        boolean temEmprestimo = emprestimos.stream().anyMatch(emp -> emp.getUsuario().getMatricula().equalsIgnoreCase(mat));
        
        if (u == null) {
            attributes.addFlashAttribute("erro", "Erro: Usuário não encontrado!");
        } else if (temEmprestimo) {
            attributes.addFlashAttribute("erro", "Erro: O usuário possui itens emprestados e não pode ser removido!");
        } else {
            usuarios.remove(u);
            attributes.addFlashAttribute("sucesso", "Usuário removido com sucesso!");
        }
        return "redirect:/";
    }

    /**
     * Realiza o empréstimo validando disponibilidade do item e o limite polimórfico do usuário.
     */
    @PostMapping("/emprestar")
    public String emprestar(@RequestParam String matricula, @RequestParam String idItem, RedirectAttributes attributes) {
        Usuario u = usuarios.stream().filter(x -> x.getMatricula().equalsIgnoreCase(matricula.trim())).findFirst().orElse(null);
        ItemAcervo item = acervo.stream().filter(x -> x.getId().equalsIgnoreCase(idItem.trim())).findFirst().orElse(null);

        if (u == null || item == null) {
            attributes.addFlashAttribute("erro", "Usuário ou Item não encontrado!");
            return "redirect:/";
        }
        if (item.isEmprestado()) {
            attributes.addFlashAttribute("erro", "Item já está emprestado!");
            return "redirect:/";
        }
        if (!u.podeEmprestar()) {
            attributes.addFlashAttribute("erro", "Limite de empréstimos atingido!");
            return "redirect:/";
        }

        item.setEmprestado(true);
        u.adicionarItem(item);
        emprestimos.add(new Emprestimo(u, item));
        attributes.addFlashAttribute("sucesso", "Empréstimo realizado!");
        return "redirect:/";
    }

    /**
     * Registra a devolução de um item e aciona o cálculo polimórfico de multa caso haja atraso.
     */
    @PostMapping("/devolver")
    public String devolver(@RequestParam String idItem, @RequestParam(defaultValue = "0") int diasAtraso, RedirectAttributes attributes) {
        Emprestimo emp = emprestimos.stream().filter(x -> x.getItem().getId().equalsIgnoreCase(idItem.trim())).findFirst().orElse(null);
        
        if (emp == null) {
            attributes.addFlashAttribute("erro", "Empréstimo não encontrado!");
            return "redirect:/";
        }

        double multa = 0.0;
        if (diasAtraso > 0) {
            // Chamada polimórfica: cada tipo de item aplica sua própria tarifa diária
            multa = emp.getItem().calcularMulta(diasAtraso);
        }

        emp.getItem().setEmprestado(false);
        emp.getUsuario().removerItem(emp.getItem());
        emprestimos.remove(emp);

        String msg = "Item devolvido!";
        if (multa > 0) msg += " MULTA GERADA: R$ " + multa;
        
        attributes.addFlashAttribute("sucesso", msg);
        return "redirect:/";
    }
}