package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal responsável por inicializar a aplicação Spring Boot e o servidor web embarcado (Tomcat).
 * A anotação @SpringBootApplication habilita a configuração automática e a varredura dos componentes do pacote.
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Método main (ponto de entrada da aplicação web).
     * Inicia todo o contexto do Spring e disponibiliza o sistema localmente na porta 8080.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}