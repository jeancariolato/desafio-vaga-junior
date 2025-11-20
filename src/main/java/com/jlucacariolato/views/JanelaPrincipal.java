package com.jlucacariolato.views;

import com.jlucacariolato.dao.TipoCombustivelDAO;
import com.jlucacariolato.model.TipoCombustivel;
import com.jlucacariolato.services.AbastecimentosService;
import com.jlucacariolato.services.BombasCombustivelService;
import com.jlucacariolato.services.TipoCombustivelService;
import com.jlucacariolato.views.dialogs.CadastroBomba;
import com.jlucacariolato.views.dialogs.CadastroTipoCombustivel;

import java.util.List;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    //Carregando os services
    private AbastecimentosService abastecimentosService = new AbastecimentosService();
    private BombasCombustivelService bombasCombustivelService = new BombasCombustivelService();
    private TipoCombustivelService tipoCombustivelService = new TipoCombustivelService();
    private JTextArea displayArea;

    public JanelaPrincipal() {
        //Configuração da tela
        setTitle("GERENCIADOR - ABASTECIMENTO");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Barra de menu
        // Cria uma barra de menu para o JFrame
        JMenuBar menuBar = new JMenuBar();

        // Adiciona a barra de menu ao  frame
        setJMenuBar(menuBar);

        // Define e adiciona dois menus drop down na barra de menus
        JMenu tiposMenu = new JMenu("Tipos de Combustível");
        JMenu bombasMenu = new JMenu("Bombas");
        JMenu abastecimentosMenu = new JMenu("Abastecimentos");
        menuBar.add(tiposMenu);
        menuBar.add(bombasMenu);
        menuBar.add(abastecimentosMenu);

        //ITENS DO MENU
        JMenuItem criarTipo = new JMenuItem("Criar");
        JMenuItem atualizarTipo = new JMenuItem("Atualizar");
        JMenuItem verTipo = new JMenuItem("Ver");
        JMenuItem deletarTipo = new JMenuItem("Deletar");

        JMenuItem criarBomba = new JMenuItem("Criar");
        JMenuItem atualizarBomba = new JMenuItem("Atualizar");
        JMenuItem verBomba = new JMenuItem("Ver");
        JMenuItem deletarBomba = new JMenuItem("Deletar");

        JMenuItem criarAbastecimento = new JMenuItem("Criar");
        JMenuItem atualizarAbastecimento = new JMenuItem("Atualizar");
        JMenuItem verAbastecimento = new JMenuItem("Ver");
        JMenuItem deletarAbastecimento = new JMenuItem("Deletar");

        //Associando itens do menu ao menubar
        tiposMenu.add(verTipo);
        tiposMenu.add(criarTipo);
        tiposMenu.add(atualizarTipo);
        tiposMenu.add(deletarTipo);

        bombasMenu.add(verBomba);
        bombasMenu.add(criarBomba);
        bombasMenu.add(atualizarBomba);
        bombasMenu.add(deletarBomba);

        abastecimentosMenu.add(verAbastecimento);
        abastecimentosMenu.add(criarAbastecimento);
        abastecimentosMenu.add(atualizarAbastecimento);
        abastecimentosMenu.add(deletarAbastecimento);

        //Área de exibição dos dados
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        //Ações para os menus
        criarTipo.addActionListener(e -> cadastrarTipoCombustivel());
        /*criarBomba.addActionListener(e -> cadastrarBomba());
        /*criarAbastecimento.addActionListener(e -> cadastrarAbastecimento());*/

        verTipo.addActionListener(e -> verTiposCombustivel());

        setVisible(true);
    }

    //Métodos para chamar os dialogs para abrir os painel de cadastro
    private void cadastrarTipoCombustivel() {
        CadastroTipoCombustivel cadastro = new CadastroTipoCombustivel(this);
        cadastro.exibir();
    }

    /*private void cadastrarBomba() {
        CadastroBomba cadastro = new CadastroBomba(this);
        cadastro.exibir();
    }*/


    //Métodos para ver a lista de itens cadastrados
    private void verTiposCombustivel() {
        TipoCombustivelDAO dao = new TipoCombustivelDAO();
        dao.listarTipos();
    }


}
