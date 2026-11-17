package com.example.demo;

/**
 * Classe responsável por executar testes de console para validar as regras de negócio
 * e os pilares da Programação Orientada a Objetos (Herança, Polimorfismo, Encapsulamento e Interfaces).
 */
public class Testador {

    /**
     * Método principal (ponto de entrada da aplicação de teste).
     * Instancia os objetos do domínio e executa quatro cenários de validação.
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("      INICIANDO TESTES DE REGRAS DE NEGÓCIO       ");
        System.out.println("==================================================\n");

        // INSTANCIAÇÃO DE OBJETOS: Criação de instâncias concretas das subclasses de Usuario e ItemAcervo
        Aluno alunoJoão = new Aluno("A01", "João Silva");
        Professor profMaria = new Professor("P01", "Prof. Maria");

        Livro livro1 = new Livro("L1", "O Senhor dos Anéis");
        Livro livro2 = new Livro("L2", "Harry Potter");
        Livro livro3 = new Livro("L3", "Pai Rico, Pai Pobre");
        Livro livro4 = new Livro("L4", "1984"); // Item extra para testar o limite máximo do aluno
        
        Revista revista = new Revista("R1", "Superinteressante");
        DVD dvd = new DVD("D1", "A Origem");

        // ----------------------------------------------------------------------
        System.out.println("TESTE 1: Limite de Empréstimos (Aluno = max 3)");
        // ----------------------------------------------------------------------
        // Valida a regra encapsulada na classe Aluno (limite de 3 itens)
        tentarEmprestar(alunoJoão, livro1); // 1º - OK
        tentarEmprestar(alunoJoão, livro2); // 2º - OK
        tentarEmprestar(alunoJoão, livro3); // 3º - OK
        tentarEmprestar(alunoJoão, livro4); // 4º - DEVE FALHAR (atingiu o limite)!


        // ----------------------------------------------------------------------
        System.out.println("\nTESTE 2: Disponibilidade de Item");
        // ----------------------------------------------------------------------
        // Valida o estado encapsulado (atributo boolean 'emprestado') do objeto livro1
        System.out.println("Prof. Maria tentando pegar 'O Senhor dos Anéis'...");
        tentarEmprestar(profMaria, livro1); // DEVE FALHAR (item já está indisponível)!


        // ----------------------------------------------------------------------
        System.out.println("\nTESTE 3: Cálculo Polimórfico de Multa (3 DIAS DE ATRASO)");
        // ----------------------------------------------------------------------
        // Empresta diferentes subclasses de ItemAcervo para a Professora
        tentarEmprestar(profMaria, revista);
        tentarEmprestar(profMaria, dvd);
        
        System.out.println("\n>>> Simulando devolução com 3 dias de atraso:");
        
        // POLIMORFISMO DE SOBRESCRITA: O mesmo método calcularMulta() comporta-se de forma diferente em cada subclasse
        // Livro: R$ 2,00 por dia (3 dias = R$ 6.0)
        System.out.println("Multa Livro: R$ " + livro1.calcularMulta(3)); 
        
        // Revista: R$ 1,00 por dia (3 dias = R$ 3.0)
        System.out.println("Multa Revista: R$ " + revista.calcularMulta(3)); 
        
        // DVD: R$ 5,00 por dia (3 dias = R$ 15.0)
        System.out.println("Multa DVD: R$ " + dvd.calcularMulta(3)); 


        // ----------------------------------------------------------------------
        System.out.println("\nTESTE 4: Polimorfismo de Interface (Exibivel)");
        // ----------------------------------------------------------------------
        // Demonstra o uso de uma Interface como tipo genérico para diferentes classes
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

    /**
     * Método auxiliar que aplica POLIMORFISMO nos parâmetros ao receber os tipos abstratos
     * 'Usuario' e 'ItemAcervo', permitindo testar qualquer subclasse (Aluno/Professor, Livro/DVD/Revista).
     */
    private static void tentarEmprestar(Usuario u, ItemAcervo item) {
        // Verifica o encapsulamento do estado do item através do método getter
        if (item.isEmprestado()) {
            System.out.println("[BLOQUEADO] O item '" + item.getTitulo() + "' já está emprestado para outra pessoa!");
            return;
        }
        
        // Invoca o método polimórfico getLimiteEmprestimo() indiretamente através de podeEmprestar()
        if (!u.podeEmprestar()) {
            System.out.println("[BLOQUEADO] O usuário '" + u.getNome() + "' atingiu o limite de empréstimos!");
            return;
        }

        // Atualiza o estado do objeto (via setter) e a associação na lista do usuário
        item.setEmprestado(true);
        u.adicionarItem(item);
        System.out.println("[SUCESSO] Item '" + item.getTitulo() + "' emprestado para " + u.getNome() + ".");
    }

    /**
     * Método polimórfico que recebe qualquer objeto que implemente o contrato da interface Exibivel,
     * executando sua respectiva implementação de obterDetalhes().
     */
    private static void imprimirQualquerCoisa(Exibivel e) {
        System.out.println(" -> " + e.obterDetalhes());
    }
}