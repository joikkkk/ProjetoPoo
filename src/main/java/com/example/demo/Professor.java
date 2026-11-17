package com.example.demo;

/**
 * Subclasse concreta que representa um Professor.
 * Aplica HERANÇA ao estender a classe abstrata Usuario.
 */
public class Professor extends Usuario {

    /**
     * Construtor que repassa matrícula e nome para o construtor da superclasse Usuario.
     */
    public Professor(String matricula, String nome) { 
        super(matricula, nome); 
    }
    
    /**
     * Sobrescrita do método abstrato getLimiteEmprestimo() (POLIMORFISMO).
     * Professores possuem limite maior, podendo pegar até 7 itens simultaneamente.
     */
    @Override 
    public int getLimiteEmprestimo() { 
        return 7; 
    }
}