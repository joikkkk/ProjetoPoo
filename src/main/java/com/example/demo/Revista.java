package com.example.demo;

/**
 * Classe concreta que representa uma Revista do acervo.
 * Herda atributos e métodos da superclasse ItemAcervo (HERANÇA).
 */
public class Revista extends ItemAcervo {

    /**
     * Construtor que inicializa a revista repassando id e título para a classe pai (ItemAcervo).
     */
    public Revista(String id, String titulo) { 
        super(id, titulo); 
    }
    
    /**
     * Implementação polimórfica que define o prazo máximo de 7 dias para empréstimo de revistas.
     */
    @Override 
    public int getPrazoMaximoEmprestimo() { 
        return 7; 
    }
    
    /**
     * Implementação polimórfica que define a multa diária de R$ 1,00 em caso de atraso.
     */
    @Override 
    public double calcularMulta(int diasAtraso) { 
        return diasAtraso * 1.00; 
    }
}