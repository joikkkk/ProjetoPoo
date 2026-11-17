package com.example.demo;

public class DVD extends ItemAcervo {
    public DVD(String id, String titulo) { 
        super(id, titulo); 
    }
    
    @Override 
    public int getPrazoMaximoEmprestimo() { return 3; }
    
    @Override 
    public double calcularMulta(int diasAtraso) { return diasAtraso * 5.00; }
}

