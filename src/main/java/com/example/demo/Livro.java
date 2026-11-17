package com.example.demo;

public class Livro extends ItemAcervo {
    public Livro(String id, String titulo) { 
        super(id, titulo); 
    }
    
    @Override 
    public int getPrazoMaximoEmprestimo() { return 14; }
    
    @Override 
    public double calcularMulta(int diasAtraso) { return diasAtraso * 2.00; }
}
