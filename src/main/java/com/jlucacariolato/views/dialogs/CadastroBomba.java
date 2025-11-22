package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.model.BombasCombustivel;
import com.jlucacariolato.model.TipoCombustivel;
import com.jlucacariolato.services.BombasCombustivelService;
import com.jlucacariolato.services.TipoCombustivelService;
import com.jlucacariolato.views.JanelaPrincipal;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.util.List;

public class CadastroBomba {
    private final JFrame parent;
    private JTextField txtNome;
    private JComboBox<String> combo;
    private BombasCombustivelService bombaService;
    private TipoCombustivelService tipoCombustivelService;
    private List<TipoCombustivel> tiposCombustivel;

    public CadastroBomba(JFrame parent) {
        this.parent = parent;
        this.tipoCombustivelService = new TipoCombustivelService();
        this.bombaService = new BombasCombustivelService();
    }

    // Exibe o diálogo de cadastro de nova bomba de combustível
    public void exibir() {
        tiposCombustivel = tipoCombustivelService.listar();

        if (tiposCombustivel.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Cadastre um tipo de combustível primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelNome = new JLabel("Nome da bomba:");
        txtNome = new JTextField(20);

        JLabel labelTipo = new JLabel("Tipo de Combustível:");
        combo = new JComboBox<>();
        for (TipoCombustivel tipo : tiposCombustivel) {
            combo.addItem(tipo.getNome());
        }

        panel.add(labelNome);
        panel.add(txtNome);
        panel.add(labelTipo);
        panel.add(combo);

        int resultado = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Cadastrar bomba",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            processarCadastro();
        }
    }

    // Valida os dados e salva a nova bomba no banco de dados
    private void processarCadastro() {
        String nome = txtNome.getText().trim();
        String tipoSelecionadoNome = (String) combo.getSelectedItem();

        if (nome.isEmpty()) {
            exibirErro("Por favor, preencha o nome da bomba!");
            return;
        }

        TipoCombustivel tipoSelecionado = null;
        for (TipoCombustivel tipo : tiposCombustivel) {
            if (tipo.getNome().equals(tipoSelecionadoNome)) {
                tipoSelecionado = tipo;
                break;
            }
        }

        if (tipoSelecionado != null) {
            salvarBomba(nome, tipoSelecionado);
        } else {
            exibirErro("Tipo de combustível selecionado é inválido!");
        }
    }

    // Cria e salva uma nova bomba de combustível no banco de dados
    private void salvarBomba(String nome, TipoCombustivel tipoCombustivel) {
        BombasCombustivel bombaCombustivel = new BombasCombustivel(nome, tipoCombustivel);
        bombaService.criar(bombaCombustivel);
        JOptionPane.showMessageDialog(parent, "Bomba cadastrada com sucesso!");
        if (parent instanceof JanelaPrincipal) {
            ((JanelaPrincipal) parent).verBombas();
        }
    }

    // Exibe uma mensagem de erro ao usuário
    private void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
