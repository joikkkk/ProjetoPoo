package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BibliotecaController {

    private List<ItemAcervo> acervo = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public BibliotecaController() {
        acervo.add(new Livro("L1", "Java para Web"));
        acervo.add(new Revista("R1", "Spring Magazine"));
        acervo.add(new DVD("D1", "Curso de HTML"));
        usuarios.add(new Aluno("A1", "João Silva"));
        usuarios.add(new Professor("P1", "Maria Oliveira"));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("acervo", acervo);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("emprestimos", emprestimos);
        return "index";
    }

    @PostMapping("/cadastrar-item")
    public String cadastrarItem(@RequestParam String id, @RequestParam String titulo, @RequestParam String tipo) {
        if (tipo.equals("Livro")) acervo.add(new Livro(id, titulo));
        else if (tipo.equals("Revista")) acervo.add(new Revista(id, titulo));
        else if (tipo.equals("DVD")) acervo.add(new DVD(id, titulo));
        return "redirect:/";
    }

    // NOVA OPÇÃO: Remover Item
    @PostMapping("/remover-item")
    public String removerItem(@RequestParam String id, RedirectAttributes attributes) {
        ItemAcervo item = acervo.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if (item == null) {
            attributes.addFlashAttribute("erro", "Erro: Item não encontrado no acervo!");
        } else if (item.isEmprestado()) {
            attributes.addFlashAttribute("erro", "Erro: Não é possível remover um item que está emprestado!");
        } else {
            acervo.remove(item);
            attributes.addFlashAttribute("sucesso", "Item removido com sucesso!");
        }
        return "redirect:/";
    }

    @PostMapping("/cadastrar-usuario")
    public String cadastrarUsuario(@RequestParam String matricula, @RequestParam String nome, @RequestParam String tipo) {
        if (tipo.equals("Aluno")) usuarios.add(new Aluno(matricula, nome));
        else if (tipo.equals("Professor")) usuarios.add(new Professor(matricula, nome));
        return "redirect:/";
    }

    // NOVA OPÇÃO: Remover Usuário
    @PostMapping("/remover-usuario")
    public String removerUsuario(@RequestParam String matricula, RedirectAttributes attributes) {
        Usuario u = usuarios.stream().filter(x -> x.getMatricula().equals(matricula)).findFirst().orElse(null);
        boolean temEmprestimo = emprestimos.stream().anyMatch(emp -> emp.getUsuario().getMatricula().equals(matricula));
        
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

    @PostMapping("/emprestar")
    public String emprestar(@RequestParam String matricula, @RequestParam String idItem, RedirectAttributes attributes) {
        Usuario u = usuarios.stream().filter(x -> x.getMatricula().equals(matricula)).findFirst().orElse(null);
        ItemAcervo item = acervo.stream().filter(x -> x.getId().equals(idItem)).findFirst().orElse(null);

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

    @PostMapping("/devolver")
    public String devolver(@RequestParam String idItem, @RequestParam int diasAtraso, RedirectAttributes attributes) {
        Emprestimo emp = emprestimos.stream().filter(x -> x.getItem().getId().equals(idItem)).findFirst().orElse(null);
        
        if (emp == null) {
            attributes.addFlashAttribute("erro", "Empréstimo não encontrado!");
            return "redirect:/";
        }

        double multa = 0.0;
        if (diasAtraso > 0) {
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