package com.example.demo;

/**
 * Classe ABSTRATA que serve como modelo base (superclasse) para todos os itens do acervo.
 * Não pode ser instanciada diretamente, apenas herdada por subclasses concretas (Livro, Revista, DVD).
 * Implementa a interface Exibivel (CONTRATO/INTERFACE), obrigando a implementação de obterDetalhes().
 */
public abstract class ItemAcervo implements Exibivel {
    // Atributos com modificador 'protected' (ENCAPSULAMENTO): acessíveis pela própria classe e suas subclasses
    protected String id;
    protected String titulo;
    protected boolean emprestado;
    protected boolean reservado;

    /**
     * Construtor base que inicializa os dados comuns a qualquer item do acervo,
     * definindo inicialmente que o item não está emprestado nem reservado.
     */
    public ItemAcervo(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.emprestado = false;
        this.reservado = false;
    }

    // Métodos getters e setters para leitura e modificação controlada dos atributos encapsulados
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public boolean isEmprestado() { return emprestado; }
    public void setEmprestado(boolean emprestado) { this.emprestado = emprestado; }
    public boolean isReservado() { return reservado; }
    public void setReservado(boolean reservado) { this.reservado = reservado; }

    /**
     * Métodos ABSTRATOS (POLIMORFISMO): não possuem corpo aqui e forçam cada subclasse
     * (Livro, Revista, DVD) a definir suas próprias regras de prazo e cálculo de multa.
     */
    public abstract int getPrazoMaximoEmprestimo();
    public abstract double calcularMulta(int diasAtraso);

    /**
     * Sobrescrita do método exigido pela interface Exibivel (POLIMORFISMO DE INTERFACE).
     * Retorna uma representação textual padronizada com o tipo da subclasse, título e disponibilidade.
     */
    @Override
    public String obterDetalhes() {
        return "ID: " + id + " | " + getClass().getSimpleName() + " | Título: " + titulo + 
               " | Status: " + (emprestado ? "Emprestado" : "Disponível");
    }
}