package com.example.demo;

public class Professor extends Usuario {
    public Professor(String matricula, String nome) { 
        super(matricula, nome); 
    }
    
    @Override 
    public int getLimiteEmprestimo() { return 7; }
}
