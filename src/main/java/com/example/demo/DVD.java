package com.example.demo;

/**
 * Classe concreta que representa um DVD no acervo da biblioteca.
 * Estende ItemAcervo aplicando o conceito de HERANÇA.
 */
public class DVD extends ItemAcervo {

    /**
     * Construtor da classe DVD. Chama o construtor da superclasse via 'super'.
     */
    public DVD(String id, String titulo) { 
        super(id, titulo); 
    }
    
    /**
     * Sobrescrita de método (POLIMORFISMO): estabelece prazo reduzido de 3 dias para DVDs.
     */
    @Override 
    public int getPrazoMaximoEmprestimo() { 
        return 3; 
    }
    
    /**
     * Sobrescrita de método (POLIMORFISMO): estabelece multa mais alta (R$ 5,00/dia) para atrasos de DVDs.
     */
    @Override 
    public double calcularMulta(int diasAtraso) { 
        return diasAtraso * 5.00; 
    }
}