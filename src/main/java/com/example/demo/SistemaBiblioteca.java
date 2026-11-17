package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaBiblioteca {
    
    // Listas para guardar os dados na memória
    private static List<ItemAcervo> acervo = new ArrayList<>();
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Emprestimo> emprestimos = new ArrayList<>();
    
    // Scanner para ler o que o usuário digita
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarDados(); // Coloca alguns dados falsos para facilitar os testes

        int opcao = 0;

        // Loop principal do menu
        while (opcao != 8) {
            System.out.println("\n====================================");
            System.out.println("   SISTEMA DE BIBLIOTECA ESCOLAR");
            System.out.println("====================================");
            System.out.println("1. Cadastrar Item no Acervo");
            System.out.println("2. Cadastrar Usuário");
            System.out.println("3. Realizar Empréstimo");
            System.out.println("4. Realizar Devolução");
            System.out.println("5. Renovar Empréstimo");
            System.out.println("6. Relatórios (Listar Tudo)");
            System.out.println("7. Remover Item do Acervo");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o "enter" (quebra de linha) do teclado

            switch (opcao) {
                case 1:
                    cadastrarItem();
                    break;
                case 2:
                    cadastrarUsuario();
                    break;
                case 3:
                    realizarEmprestimo();
                    break;
                case 4:
                    realizarDevolucao();
                    break;
                case 5:
                    renovarEmprestimo();
                    break;
                case 6:
                    exibirRelatorios();
                    break;
                case 7:
                    removerItem();
                    break;
                case 8:
                    System.out.println("Saindo do sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    // ==========================================================
    // MÉTODOS SIMPLIFICADOS COM FOR TRADICIONAL E IFs
    // ==========================================================

    private static void cadastrarItem() {
        System.out.println("\n--- CADASTRAR ITEM ---");
        System.out.print("Digite o ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Digite o Título: ");
        String titulo = scanner.nextLine();
        
        System.out.println("Qual o tipo? (1=Livro, 2=Revista, 3=DVD): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); 

        if (tipo == 1) {
            acervo.add(new Livro(id, titulo));
            System.out.println("Livro cadastrado com sucesso!");
        } else if (tipo == 2) {
            acervo.add(new Revista(id, titulo));
            System.out.println("Revista cadastrada com sucesso!");
        } else if (tipo == 3) {
            acervo.add(new DVD(id, titulo));
            System.out.println("DVD cadastrado com sucesso!");
        } else {
            System.out.println("Tipo inválido. Cadastro cancelado.");
        }
    }

    private static void cadastrarUsuario() {
        System.out.println("\n--- CADASTRAR USUÁRIO ---");
        System.out.print("Digite a Matrícula: ");
        String matricula = scanner.nextLine();
        
        System.out.print("Digite o Nome: ");
        String nome = scanner.nextLine();
        
        System.out.println("Qual o tipo? (1=Aluno, 2=Professor): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            usuarios.add(new Aluno(matricula, nome));
            System.out.println("Aluno cadastrado com sucesso!");
        } else if (tipo == 2) {
            usuarios.add(new Professor(matricula, nome));
            System.out.println("Professor cadastrado com sucesso!");
        } else {
            System.out.println("Tipo inválido. Cadastro cancelado.");
        }
    }

    private static void realizarEmprestimo() {
        System.out.println("\n--- REALIZAR EMPRÉSTIMO ---");
        System.out.print("Digite a Matrícula do Usuário: ");
        String matricula = scanner.nextLine();
        
        System.out.print("Digite o ID do Item: ");
        String idItem = scanner.nextLine();

        // Busca o usuário da forma tradicional (sem streams)
        Usuario usuarioEncontrado = null;
        for (Usuario u : usuarios) {
            if (u.getMatricula().equals(matricula)) {
                usuarioEncontrado = u;
                break;
            }
        }

        // Busca o item da forma tradicional
        ItemAcervo itemEncontrado = null;
        for (ItemAcervo item : acervo) {
            if (item.getId().equals(idItem)) {
                itemEncontrado = item;
                break;
            }
        }

        // Validações
        if (usuarioEncontrado == null) {
            System.out.println("Erro: Usuário não encontrado!");
            return;
        }
        if (itemEncontrado == null) {
            System.out.println("Erro: Item não encontrado no acervo!");
            return;
        }
        if (itemEncontrado.isEmprestado()) {
            System.out.println("Erro: Este item já está emprestado!");
            return;
        }
        if (usuarioEncontrado.podeEmprestar() == false) {
            System.out.println("Erro: O usuário atingiu o limite máximo de empréstimos!");
            return;
        }

        // Efetiva o empréstimo
        itemEncontrado.setEmprestado(true);
        usuarioEncontrado.adicionarItem(itemEncontrado);
        Emprestimo novoEmprestimo = new Emprestimo(usuarioEncontrado, itemEncontrado);
        emprestimos.add(novoEmprestimo);
        
        System.out.println("Empréstimo realizado com sucesso!");
        System.out.println("Data de devolução prevista: " + novoEmprestimo.getDataVencimento());
    }

    private static void realizarDevolucao() {
        System.out.println("\n--- REALIZAR DEVOLUÇÃO ---");
        System.out.print("Digite o ID do Item que está sendo devolvido: ");
        String idItem = scanner.nextLine();

        // Procura o empréstimo ativo desse item
        Emprestimo emprestimoEncontrado = null;
        for (Emprestimo emp : emprestimos) {
            if (emp.getItem().getId().equals(idItem)) {
                emprestimoEncontrado = emp;
                break;
            }
        }

        if (emprestimoEncontrado == null) {
            System.out.println("Erro: Nenhum empréstimo ativo encontrado para este item!");
            return;
        }

        // Pergunta se teve atraso para simular o cálculo da multa (Polimorfismo em ação!)
        System.out.print("Quantos dias de atraso a devolução teve? (Digite 0 se entregou no prazo): ");
        int diasAtraso = scanner.nextInt();
        scanner.nextLine();

        double multa = 0.0;
        
        // REQUISITO: Cálculo de multa por chamada polimórfica!
        if (diasAtraso > 0) {
            multa = emprestimoEncontrado.getItem().calcularMulta(diasAtraso);
        }

        // Limpa os dados (devolve o item)
        emprestimoEncontrado.getItem().setEmprestado(false);
        emprestimoEncontrado.getUsuario().removerItem(emprestimoEncontrado.getItem());
        emprestimos.remove(emprestimoEncontrado);

        System.out.println("Item devolvido com sucesso!");
        if (multa > 0) {
            System.out.println("ATENÇÃO: O usuário deve pagar uma multa de R$ " + multa);
        }
    }

    private static void renovarEmprestimo() {
        System.out.println("\n--- RENOVAR EMPRÉSTIMO ---");
        System.out.print("Digite o ID do Item que deseja renovar: ");
        String idItem = scanner.nextLine();

        for (Emprestimo emp : emprestimos) {
            if (emp.getItem().getId().equals(idItem)) {
                if (emp.getItem().isReservado()) {
                    System.out.println("Erro: Este item tem uma reserva e não pode ser renovado.");
                } else {
                    emp.renovar();
                    System.out.println("Renovação feita! Nova data de vencimento: " + emp.getDataVencimento());
                }
                return; // Encerra a busca
            }
        }
        System.out.println("Erro: Empréstimo não encontrado para o ID informado.");
    }

    private static void removerItem() {
        System.out.println("\n--- REMOVER ITEM ---");
        System.out.print("Digite o ID do Item para remover: ");
        String idItem = scanner.nextLine();

        ItemAcervo itemParaRemover = null;
        for (ItemAcervo item : acervo) {
            if (item.getId().equals(idItem)) {
                itemParaRemover = item;
                break;
            }
        }

        if (itemParaRemover != null) {
            if (itemParaRemover.isEmprestado()) {
                System.out.println("Erro: Não é possível remover um item que está emprestado!");
            } else {
                acervo.remove(itemParaRemover);
                System.out.println("Item removido com sucesso!");
            }
        } else {
            System.out.println("Erro: Item não encontrado!");
        }
    }

    private static void exibirRelatorios() {
        System.out.println("\n--- RELATÓRIOS (USANDO POLIMORFISMO DE INTERFACE) ---");
        
        System.out.println("\n>>> USUÁRIOS CADASTRADOS:");
        imprimirListaPolimorfica(usuarios);

        System.out.println("\n>>> ACERVO DA BIBLIOTECA:");
        imprimirListaPolimorfica(acervo);

        System.out.println("\n>>> EMPRÉSTIMOS ATIVOS:");
        imprimirListaPolimorfica(emprestimos);
    }

    // Método que aceita qualquer lista de objetos que implementam a interface "Exibivel"
    private static void imprimirListaPolimorfica(List<? extends Exibivel> lista) {
        if (lista.isEmpty()) {
            System.out.println("  (Nenhum registro encontrado)");
        } else {
            for (Exibivel item : lista) {
                System.out.println("  - " + item.obterDetalhes());
            }
        }
    }

    // Popula o sistema para você não ter que digitar tudo toda vez que rodar o programa
    private static void inicializarDados() {
        acervo.add(new Livro("L1", "Java Para Iniciantes"));
        acervo.add(new Revista("R1", "InfoExame"));
        acervo.add(new DVD("D1", "Curso de Lógica"));
        usuarios.add(new Aluno("A1", "João Silva (Aluno)"));
        usuarios.add(new Professor("P1", "Maria Oliveira (Professora)"));
    }
}

