package com.jlucacariolato.services;

import java.util.List;

import com.jlucacariolato.dao.BombasCombustivelDAO;
import com.jlucacariolato.model.BombasCombustivel;

public class BombasCombustivelService {
    //Carregar a DAO
    BombasCombustivelDAO bombaDAO = new BombasCombustivelDAO();

    public void criar(BombasCombustivel bomba){
        bombaDAO.criarBombaCombustivel(bomba);
    }

     public List<BombasCombustivel> listar(){
        return bombaDAO.listarBombas();
    }

    public void atualizar (BombasCombustivel bomba){
       bombaDAO.update(bomba);
    }

    public void deletar(int id){
        bombaDAO.deletar(id);
    }

}