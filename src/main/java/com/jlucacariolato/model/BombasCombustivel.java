package com.jlucacariolato.model;

public class BombasCombustivel {
    String nomeBomba;
    TipoCombustivel tipoCombustivel;

    public BombasCombustivel(String nomeBomba, TipoCombustivel tipoCombustivel) {
        this.nomeBomba = nomeBomba;
        this.tipoCombustivel = tipoCombustivel;
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
