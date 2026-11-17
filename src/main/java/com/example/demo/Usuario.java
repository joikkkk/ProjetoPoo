package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario implements Exibivel {
    protected String matricula;
    protected String nome;
    protected List<ItemAcervo> itensEmprestados;

    public Usuario(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
        this.itensEmprestados = new ArrayList<>();
    }

    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public List<ItemAcervo> getItensEmprestados() { return itensEmprestados; }

    public abstract int getLimiteEmprestimo();

    public boolean podeEmprestar() {
        return itensEmprestados.size() < getLimiteEmprestimo();
    }

    public void adicionarItem(ItemAcervo item) { itensEmprestados.add(item); }
    public void removerItem(ItemAcervo item) { itensEmprestados.remove(item); }

    @Override
    public String obterDetalhes() {
        return "Matrícula: " + matricula + " | " + getClass().getSimpleName() + " | Nome: " + nome + 
               " | Empréstimos: " + itensEmprestados.size() + "/" + getLimiteEmprestimo();
    }
}
