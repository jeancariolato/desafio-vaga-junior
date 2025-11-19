package com.jlucacariolato.model;

public class TipoCombustivel {
    String nome;
    double precoLitro;

    public TipoCombustivel(String nome, double precoLitro) {
        this.nome = nome;
        this.precoLitro = precoLitro;
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
