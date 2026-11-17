package com.example.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe ABSTRATA que serve como modelo base para todos os usuários da biblioteca.
 * Implementa a interface Exibivel (CONTRATO/INTERFACE), obrigando a definição de obterDetalhes().
 */
public abstract class Usuario implements Exibivel {
    // Atributos com modificador 'protected' (ENCAPSULAMENTO): acessíveis pela própria classe e suas subclasses
    protected String matricula;
    protected String nome;
    protected List<ItemAcervo> itensEmprestados; // Associação (composição/agregação) com itens do acervo

    /**
     * Construtor base que inicializa os atributos do usuário e cria a lista vazia de empréstimos.
     */
    public Usuario(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
        this.itensEmprestados = new ArrayList<>();
    }

    // Métodos getters para leitura controlada dos atributos encapsulados
    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public List<ItemAcervo> getItensEmprestados() { return itensEmprestados; }

    /**
     * Método ABSTRATO: força cada subclasse (Aluno, Professor) a definir seu próprio limite de itens.
     */
    public abstract int getLimiteEmprestimo();

    /**
     * Método de regra de negócio que verifica se o usuário ainda pode realizar novos empréstimos.
     */
    public boolean podeEmprestar() {
        return itensEmprestados.size() < getLimiteEmprestimo();
    }

    // Métodos para gerenciar a lista de itens emprestados ao usuário
    public void adicionarItem(ItemAcervo item) { itensEmprestados.add(item); }
    public void removerItem(ItemAcervo item) { itensEmprestados.remove(item); }

    /**
     * Implementação do método exigido pela interface Exibivel.
     * Formata os dados principais do usuário para exibição no sistema.
     */
    @Override
    public String obterDetalhes() {
        return "Matrícula: " + matricula + " | " + getClass().getSimpleName() + " | Nome: " + nome + 
               " | Empréstimos: " + itensEmprestados.size() + "/" + getLimiteEmprestimo();
    }
}