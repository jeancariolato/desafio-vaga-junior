package com.jlucacariolato.model;

public class Abastecimentos {
    private int id;
    BombasCombustivel bombaCombustivel;
    String dataAbastecimento;
    double quantidadeValor;
    double litros;
    double totalPago;

    // CONSTRUTOR
    public Abastecimentos() {
    }

    public Abastecimentos(int id, BombasCombustivel bombaCombustivel, String dataAbastecimento, double quantidadeValor, double litros, double totalPago) {
        this.id = id;
        this.bombaCombustivel = bombaCombustivel;
        this.dataAbastecimento = dataAbastecimento;
        this.quantidadeValor = quantidadeValor;
        this.litros = litros;
        this.totalPago = totalPago;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }
}
