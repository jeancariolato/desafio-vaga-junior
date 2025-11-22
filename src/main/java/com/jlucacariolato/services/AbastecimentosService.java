package com.jlucacariolato.services;

import com.jlucacariolato.dao.AbastecimentosDAO;
import com.jlucacariolato.model.Abastecimentos;

import java.util.List;

public class AbastecimentosService {

    //DAO
    AbastecimentosDAO abastecimentosDAO = new AbastecimentosDAO();

    //Chamar métodos
    public void criar(Abastecimentos abastecimento){
        abastecimentosDAO.criarAbastecimento(abastecimento);
    }

    public List<Abastecimentos> listar(){
        return abastecimentosDAO.listarAbastecimentos();
    }

    public void atualizar (Abastecimentos abastecimento){
        abastecimentosDAO.update(abastecimento);
    }

    public Abastecimentos buscarPorId(long id) {
        return abastecimentosDAO.buscarAbastecimentoPorId(id);
    }

    public void deletar(int id){
        abastecimentosDAO.deletar(id);
    }
}
