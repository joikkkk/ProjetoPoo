package com.example.demo;

import java.time.LocalDate;

public class Emprestimo implements Exibivel {
    private Usuario usuario;
    private ItemAcervo item;
    private LocalDate dataVencimento;

    public Emprestimo(Usuario usuario, ItemAcervo item) {
        this.usuario = usuario;
        this.item = item;
        this.dataVencimento = LocalDate.now().plusDays(item.getPrazoMaximoEmprestimo());
    }

    public Usuario getUsuario() { return usuario; }
    public ItemAcervo getItem() { return item; }
    public LocalDate getDataVencimento() { return dataVencimento; }

    public void renovar() {
        this.dataVencimento = LocalDate.now().plusDays(item.getPrazoMaximoEmprestimo());
    }

    @Override
    public String obterDetalhes() {
        return "Item [" + item.getTitulo() + "] emprestado para [" + usuario.getNome() + "] com vencimento em " + dataVencimento;
    }
}
