package com.example.demo;

public class Revista extends ItemAcervo {
    public Revista(String id, String titulo) { 
        super(id, titulo); 
    }
    
    @Override 
    public int getPrazoMaximoEmprestimo() { return 7; }
    
    @Override 
    public double calcularMulta(int diasAtraso) { return diasAtraso * 1.00; }
}
