package com.jlucacariolato.model;

public class BombasCombustivel {
    private int id;
    private String nomeBomba;
    private TipoCombustivel tipoCombustivel;

    // CONSTRUTOR
    public BombasCombustivel() {
    }

    public BombasCombustivel(String nomeBomba, TipoCombustivel tipoCombustivel) {
        this.id = id;
        this.nomeBomba = nomeBomba;
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeBomba() {
        return nomeBomba;
    }

    public void setNomeBomba(String nomeBomba) {
        this.nomeBomba = nomeBomba;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
}
