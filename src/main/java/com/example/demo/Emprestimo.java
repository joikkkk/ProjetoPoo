package com.example.demo;

import java.time.LocalDate;

/**
 * Classe que representa a associação de empréstimo entre um Usuario e um ItemAcervo.
 * Implementa a interface Exibivel (CONTRATO/INTERFACE), obrigando a implementação do método obterDetalhes().
 */
public class Emprestimo implements Exibivel {
    // Atributos privados (ENCAPSULAMENTO): protegem os dados internos da classe contra alterações diretas externas
    private Usuario usuario;          // Associação com a superclasse abstrata Usuario (aceita Aluno ou Professor)
    private ItemAcervo item;          // Associação com a superclasse abstrata ItemAcervo (aceita Livro, Revista ou DVD)
    private LocalDate dataVencimento; // Armazena a data prevista para devolução

    /**
     * Construtor da classe Emprestimo.
     * Calcula automaticamente a data de vencimento usando POLIMORFISMO ao chamar
     * item.getPrazoMaximoEmprestimo(), que retorna prazos diferentes conforme o tipo do item (14, 7 ou 3 dias).
     */
    public Emprestimo(Usuario usuario, ItemAcervo item) {
        this.usuario = usuario;
        this.item = item;
        this.dataVencimento = LocalDate.now().plusDays(item.getPrazoMaximoEmprestimo());
    }

    // Métodos getters para acesso controlado aos atributos encapsulados
    public Usuario getUsuario() { return usuario; }
    public ItemAcervo getItem() { return item; }
    public LocalDate getDataVencimento() { return dataVencimento; }

    /**
     * Método de regra de negócio que prorroga o empréstimo a partir da data atual,
     * respeitando novamente o prazo polimórfico específico de cada tipo de item.
     */
    public void renovar() {
        this.dataVencimento = LocalDate.now().plusDays(item.getPrazoMaximoEmprestimo());
    }

    /**
     * Sobrescrita do método exigido pela interface Exibivel (POLIMORFISMO DE INTERFACE).
     * Retorna um resumo formatado com o título do item, nome do usuário e data de vencimento.
     */
    @Override
    public String obterDetalhes() {
        return "Item [" + item.getTitulo() + "] emprestado para [" + usuario.getNome() + "] com vencimento em " + dataVencimento;
    }
}