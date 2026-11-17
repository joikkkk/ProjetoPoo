package com.example.demo;

public class Testador {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("      INICIANDO TESTES DE REGRAS DE NEGÓCIO       ");
        System.out.println("==================================================\n");

        // Criando objetos para o teste
        Aluno alunoJoão = new Aluno("A01", "João Silva");
        Professor profMaria = new Professor("P01", "Prof. Maria");

        Livro livro1 = new Livro("L1", "O Senhor dos Anéis");
        Livro livro2 = new Livro("L2", "Harry Potter");
        Livro livro3 = new Livro("L3", "Pai Rico, Pai Pobre");
        Livro livro4 = new Livro("L4", "1984"); // Item extra para testar o limite
        
        Revista revista = new Revista("R1", "Superinteressante");
        DVD dvd = new DVD("D1", "A Origem");

        // ----------------------------------------------------------------------
        System.out.println("TESTE 1: Limite de Empréstimos (Aluno = max 3)");
        // ----------------------------------------------------------------------
        tentarEmprestar(alunoJoão, livro1); // 1º - OK
        tentarEmprestar(alunoJoão, livro2); // 2º - OK
        tentarEmprestar(alunoJoão, livro3); // 3º - OK
        tentarEmprestar(alunoJoão, livro4); // 4º - DEVE FALHAR!


        // ----------------------------------------------------------------------
        System.out.println("\nTESTE 2: Disponibilidade de Item");
        // ----------------------------------------------------------------------
        // O livro1 já está com o João. A Maria vai tentar pegar:
        System.out.println("Prof. Maria tentando pegar 'O Senhor dos Anéis'...");
        tentarEmprestar(profMaria, livro1); // DEVE FALHAR!


        // ----------------------------------------------------------------------
        System.out.println("\nTESTE 3: Cálculo Polimórfico de Multa (3 DIAS DE ATRASO)");
        // ----------------------------------------------------------------------
        // Vamos emprestar a revista e o DVD para a Professora
        tentarEmprestar(profMaria, revista);
        tentarEmprestar(profMaria, dvd);
        
        System.out.println("\n>>> Simulando devolução com 3 dias de atraso:");
        
        // Livro: R$ 2 por dia (3 dias = R$ 6.0)
        System.out.println("Multa Livro: R$ " + livro1.calcularMulta(3)); 
        
        // Revista: R$ 1 por dia (3 dias = R$ 3.0)
        System.out.println("Multa Revista: R$ " + revista.calcularMulta(3)); 
        
        // DVD: R$ 5 por dia (3 dias = R$ 15.0)
        System.out.println("Multa DVD: R$ " + dvd.calcularMulta(3)); 


        // ----------------------------------------------------------------------
        System.out.println("\nTESTE 4: Polimorfismo de Interface (Exibivel)");
        // ----------------------------------------------------------------------
        System.out.println("O método abaixo aceita qualquer coisa que seja 'Exibivel':");
        imprimirQualquerCoisa(alunoJoão);
        imprimirQualquerCoisa(livro1);
        imprimirQualquerCoisa(new Emprestimo(alunoJoão, livro1));


        System.out.println("\n==================================================");
        System.out.println("                TESTES FINALIZADOS                  ");
        System.out.println("==================================================");
    }

    // ========================================================================
    // MÉTODOS AUXILIARES PARA FACILITAR OS TESTES
    // ========================================================================

    // Simula a tentativa de empréstimo respeitando as regras das classes
    private static void tentarEmprestar(Usuario u, ItemAcervo item) {
        if (item.isEmprestado()) {
            System.out.println("[BLOQUEADO] O item '" + item.getTitulo() + "' já está emprestado para outra pessoa!");
            return;
        }
        
        if (!u.podeEmprestar()) {
            System.out.println("[BLOQUEADO] O usuário '" + u.getNome() + "' atingiu o limite de empréstimos!");
            return;
        }

        // Se passou pelas regras, faz o empréstimo
        item.setEmprestado(true);
        u.adicionarItem(item);
        System.out.println("[SUCESSO] Item '" + item.getTitulo() + "' emprestado para " + u.getNome() + ".");
    }

    // Testa a interface
    private static void imprimirQualquerCoisa(Exibivel e) {
        System.out.println(" -> " + e.obterDetalhes());
    }
    
}

