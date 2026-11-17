package com.example.demo;

/**
 * Classe concreta que representa um Livro do acervo da biblioteca.
 * Aplica o conceito de HERANÇA ao estender a classe abstrata ItemAcervo,
 * herdando seus atributos (id, titulo, emprestado) e comportamentos básicos.
 */
public class Livro extends ItemAcervo {

    /**
     * Construtor da classe Livro.
     * Utiliza a palavra-chave 'super' para repassar os atributos ao construtor da superclasse ItemAcervo.
     */
    public Livro(String id, String titulo) { 
        super(id, titulo); 
    }
    
    /**
     * Sobrescrita de método (POLIMORFISMO).
     * Define a regra específica de prazo máximo de empréstimo para livros (14 dias).
     */
    @Override 
    public int getPrazoMaximoEmprestimo() { 
        return 14; 
    }
    
    /**
     * Sobrescrita de método (POLIMORFISMO).
     * Calcula o valor da multa por atraso específica para livros (R$ 2,00 por dia).
     */
    @Override 
    public double calcularMulta(int diasAtraso) { 
        return diasAtraso * 2.00; 
    }
}