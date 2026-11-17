
package com.example.demo;

public class Aluno extends Usuario {
    public Aluno(String matricula, String nome) { 
        super(matricula, nome); 
    }
    
    @Override 
    public int getLimiteEmprestimo() { return 3; }
}
