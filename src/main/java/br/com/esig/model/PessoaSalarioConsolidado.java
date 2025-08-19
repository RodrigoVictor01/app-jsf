package br.com.esig.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa_salario_consolidado")
public class PessoaSalarioConsolidado {
    @Id
    private Integer pessoa_id;

    private String nome_pessoa;
    private String nome_cargo;
    private Double salario;

    public Integer getPessoa_id() {
        return pessoa_id;
    }

    public void setPessoa_id(Integer pessoa_id) {
        this.pessoa_id = pessoa_id;
    }

    public String getNome_pessoa() {
        return nome_pessoa;
    }

    public void setNome_pessoa(String nome_pessoa) {
        this.nome_pessoa = nome_pessoa;
    }

    public String getNome_cargo() {
        return nome_cargo;
    }

    public void setNome_cargo(String nome_cargo) {
        this.nome_cargo = nome_cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getSalarioFormatado() {
        return String.format("%.2f", salario).replace('.', ',');
    }
}
