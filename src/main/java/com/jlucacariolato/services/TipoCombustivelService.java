package com.jlucacariolato.services;

import java.util.List;

import com.jlucacariolato.dao.TipoCombustivelDAO;
import com.jlucacariolato.model.TipoCombustivel;

public class TipoCombustivelService {

    //Carregar a DAO

    TipoCombustivelDAO tipoCombustivelDAO = new TipoCombustivelDAO();

    //Métodos de chamamento das DAOs
    public void criar(TipoCombustivel tipo){
        tipoCombustivelDAO.criarTipoCombustivel(tipo);
    }

    public List<TipoCombustivel> listar(){
        return tipoCombustivelDAO.listarTipos();
    }

    public void atualizar (TipoCombustivel tipo){
        tipoCombustivelDAO.atualizarTipoCombustivel(tipo);
    }

    public void deletar(int id){
        tipoCombustivelDAO.deletarTipoCombustivel(id);
    }
    
}
