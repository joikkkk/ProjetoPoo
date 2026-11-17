package com.example.demo;

/**
 * Interface que define um CONTRATO para todas as classes que podem ser exibidas em formato textual no sistema.
 * Aplica o conceito de POLIMORFISMO DE INTERFACE, permitindo que objetos distintos (como Usuario, ItemAcervo
 * e Emprestimo) sejam tratados de forma uniforme através de um comportamento comum.
 */
public interface Exibivel {

    /**
     * Assinatura do método obrigatório que deve ser implementado (sobrescrito)
     * por toda classe que assinar este contrato.
     */
    String obterDetalhes();
}