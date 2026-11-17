package com.example.demo;

/**
 * Subclasse concreta que representa um Aluno.
 * Aplica HERANÇA ao estender a classe abstrata Usuario.
 */
public class Aluno extends Usuario {

    /**
     * Construtor que repassa matrícula e nome para o construtor da superclasse Usuario.
     */
    public Aluno(String matricula, String nome) { 
        super(matricula, nome); 
    }
    
    /**
     * Sobrescrita do método abstrato getLimiteEmprestimo() (POLIMORFISMO).
     * Alunos podem pegar no máximo 3 itens simultaneamente.
     */
    @Override 
    public int getLimiteEmprestimo() { 
        return 3; 
    }
}