package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.dao.TipoCombustivelDAO;
import com.jlucacariolato.model.TipoCombustivel;

import com.jlucacariolato.views.JanelaPrincipal;

import javax.swing.BorderFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;

public class CadastroTipoCombustivel {

    private JFrame parent;

    private JTextField txtNome;
    private JTextField txtPreco;

    //CONSTRUTOR
    public CadastroTipoCombustivel(JFrame parent) {
        this.parent = parent;
    }

    // Exibe o diálogo de cadastro de novo tipo de combustível
    public void exibir() {
        // Criar painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Criar componentes
        JLabel labelNome = new JLabel("Nome do Combustível:");
        txtNome = new JTextField(20);

        JLabel labelPreco = new JLabel("Preço por Litro (R$):");
        txtPreco = new JTextField(20);

        // Adicionar componentes ao painel
        panel.add(labelNome);
        panel.add(txtNome);
        panel.add(labelPreco);
        panel.add(txtPreco);

        // Exibir diálogo
        int resultado = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Cadastrar Tipo de Combustível",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Processar resultado
        if (resultado == JOptionPane.OK_OPTION) {
            processarCadastro();
        }
    }

    // Valida os dados e salva o novo tipo de combustível no banco de dados
    private void processarCadastro() {
        String nome = txtNome.getText().trim();
        String precoStr = txtPreco.getText().trim();

        // Validar campos vazios
        if (nome.isEmpty() || precoStr.isEmpty()) {
            exibirErro("Por favor, preencha todos os campos!");
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr.replace(",", "."));

            // Validar preço positivo
            if (preco <= 0) {
                exibirErro("O preço deve ser maior que zero!");
                return;
            }

            // Salvar os dados
            salvarCombustivel(nome, preco);

            // Exibir sucesso
            exibirSucesso(nome, preco);
            if (parent instanceof JanelaPrincipal) {
                ((JanelaPrincipal) parent).verTiposCombustivel();
            }

        } catch (NumberFormatException e) {
            exibirErro("Por favor, insira um preço válido!");
        }
    }

    // Cria e salva um novo tipo de combustível no banco de dados
    private void salvarCombustivel(String nome, double preco) {
        //Chamando DAO para fazer o salvamento
        TipoCombustivel tipoCombustivel = new TipoCombustivel(nome, preco);
        TipoCombustivelDAO tipoCombustivelDAO = new TipoCombustivelDAO();
        tipoCombustivelDAO.criarTipoCombustivel(tipoCombustivel);

        System.out.println("Salvando combustível: " + nome + " - R$ " + preco);
    }

    // Exibe uma mensagem de erro ao usuário
    private void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(
                parent,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // Exibe uma mensagem de sucesso com os dados cadastrados
    private void exibirSucesso(String nome, double preco) {
        String mensagem = String.format(
                "Combustível cadastrado com sucesso!\n\nNome: %s\nPreço: R$ %.2f",
                nome,
                preco
        );

        JOptionPane.showMessageDialog(
                parent,
                mensagem,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}

