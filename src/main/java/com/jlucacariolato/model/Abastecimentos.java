package com.jlucacariolato.model;

public class Abastecimentos {
    BombasCombustivel bombaCombustivel;
    String dataAbastecimento;
    double quantidadeValor;
    double litros;

    public Abastecimentos(BombasCombustivel bombaCombustivel, String dataAbastecimento, double quantidadeValor, double litros) {
        this.bombaCombustivel = bombaCombustivel;
        this.dataAbastecimento = dataAbastecimento;
        this.quantidadeValor = quantidadeValor;
        this.litros = litros;
    }

    public BombasCombustivel getBombaCombustivel() {
        return bombaCombustivel;
    }

    public void setBombaCombustivel(BombasCombustivel bombaCombustivel) {
        this.bombaCombustivel = bombaCombustivel;
    }

    public String getDataAbastecimento() {
        return dataAbastecimento;
    }

    public void setDataAbastecimento(String dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }

    public double getQuantidadeValor() {
        return quantidadeValor;
    }

    public void setQuantidadeValor(double quantidadeValor) {
        this.quantidadeValor = quantidadeValor;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }
}
