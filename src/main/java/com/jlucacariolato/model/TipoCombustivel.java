package com.jlucacariolato.model;

public class TipoCombustivel {
    private int id;
    private String nome;
    private double precoLitro;

    // CONSTRUTORES
    public TipoCombustivel() {
    }

    public TipoCombustivel(String nome, double precoLitro) {
        this.nome = nome;
        this.precoLitro = precoLitro;
    }

    public TipoCombustivel(int id, String nome, double precoLitro) {
        this.id = id;
        this.nome = nome;
        this.precoLitro = precoLitro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoLitro() {
        return precoLitro;
    }

    public void setPrecoLitro(double precoLitro) {
        this.precoLitro = precoLitro;
    }
}
