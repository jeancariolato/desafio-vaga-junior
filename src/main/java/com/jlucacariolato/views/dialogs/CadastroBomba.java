package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.dao.BombasCombustivelDAO;
import com.jlucacariolato.model.BombasCombustivel;
import com.jlucacariolato.model.TipoCombustivel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroBomba {
    private JFrame parent;
    private JTextField txtNomeBomba;
    private JComboBox<String> comboTipo;
    private List<String> tiposCombustivel;

    //CONSTRUTOR

    public CadastroBomba(JFrame parent, List<String> tiposCombustivel) {
        this.parent = parent;
        this.tiposCombustivel = tiposCombustivel;
    }

    public void exibir() {
        // Criar painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Criar componentes
        JLabel lblNome = new JLabel("Nome da Bomba:");
        txtNomeBomba = new JTextField(20);

        JLabel labelTipo = new JLabel("Tipo de Combustível:");
        comboTipo = new JComboBox<>();

        // Adicionar opção padrão
        comboTipo.addItem("-- Selecione --");

        // Carregar tipos de combustível no dropdown
        if (tiposCombustivel != null && !tiposCombustivel.isEmpty()) {
            for (String tipo : tiposCombustivel) {
                comboTipo.addItem(tipo);
            }
        }

        // Adicionar componentes ao painel
        panel.add(lblNome);
        panel.add(txtNomeBomba);
        panel.add(labelTipo);
        panel.add(comboTipo);

        // Exibir diálogo
        int resultado = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Cadastrar Bomba de Combustível",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Processar resultado
        if (resultado == JOptionPane.OK_OPTION) {
            processarCadastro();
        }
    }

    private void processarCadastro() {
        String nomeBomba = txtNomeBomba.getText().trim();
        String tipoCombustivel = (String) comboTipo.getSelectedItem();

        if (nomeBomba.isEmpty()) {
            exibirErro("Por favor, informe o nome da bomba!");
            return;
        }

        // Validar seleção de combustível
        if (tipoCombustivel == null || tipoCombustivel.equals("-- Selecione --")) {
            exibirErro("Por favor, selecione um tipo de combustível!");
            return;
        }

        // Salvar os dados
        salvarBomba(nomeBomba, tipoCombustivel);

        // Exibir sucesso
        exibirSucesso(nomeBomba, tipoCombustivel);
    }

    private void salvarBomba(String nomeBomba, String tipoCombustivel) {
        // TODO: Implementar lógica de salvamento
        // Exemplo:
        // Bomba bomba = new Bomba(nomeBomba, tipoCombustivel);
        // BombaDAO dao = new BombaDAO();
        // dao.salvar(bomba);

        BombasCombustivel bomba = new BombasCombustivel( );
        BombasCombustivelDAO dao = new BombasCombustivelDAO();
        dao.criarBombaCombustivel(bomba);


        System.out.println("Salvando bomba: " + nomeBomba + " - Combustível: " + tipoCombustivel);
    }

    private void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(
                parent,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void exibirSucesso(String nomeBomba, String tipoCombustivel) {
        String mensagem = String.format(
                "Bomba cadastrada com sucesso!\n\nNome: %s\nTipo de Combustível: %s",
                nomeBomba,
                tipoCombustivel
        );

        JOptionPane.showMessageDialog(
                parent,
                mensagem,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
