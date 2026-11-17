package com.example.demo;

public abstract class ItemAcervo implements Exibivel {
    protected String id;
    protected String titulo;
    protected boolean emprestado;
    protected boolean reservado;

    public ItemAcervo(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.emprestado = false;
        this.reservado = false;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public boolean isEmprestado() { return emprestado; }
    public void setEmprestado(boolean emprestado) { this.emprestado = emprestado; }
    public boolean isReservado() { return reservado; }
    public void setReservado(boolean reservado) { this.reservado = reservado; }

    public abstract int getPrazoMaximoEmprestimo();
    public abstract double calcularMulta(int diasAtraso);

    @Override
    public String obterDetalhes() {
        return "ID: " + id + " | " + getClass().getSimpleName() + " | Título: " + titulo + 
               " | Status: " + (emprestado ? "Emprestado" : "Disponível");
    }
}
