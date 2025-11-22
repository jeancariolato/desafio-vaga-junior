package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.model.Abastecimentos;
import com.jlucacariolato.model.BombasCombustivel;
import com.jlucacariolato.services.AbastecimentosService;
import com.jlucacariolato.services.BombasCombustivelService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CadastroAbastecimentos {
    private final JFrame parent;
    private JComboBox<String> comboBombas;
    private JTextField txtPrecoCombustivel;
    private JTextField txtQuantidade;
    private JTextField txtTotalPago;
    private AbastecimentosService abastecimentosService;
    private BombasCombustivelService bombasCombustivelService;
    private List<BombasCombustivel> bombas;
    private BombasCombustivel bombaSelecionada;

    public CadastroAbastecimentos(JFrame parent) {
        this.parent = parent;
        this.abastecimentosService = new AbastecimentosService();
        this.bombasCombustivelService = new BombasCombustivelService();
    }

    public void exibir() {
        bombas = bombasCombustivelService.listar();

        if (bombas.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Cadastre uma bomba de combustível primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelBomba = new JLabel("Bomba de Combustível:");
        comboBombas = new JComboBox<>();
        for (BombasCombustivel bomba : bombas) {
            comboBombas.addItem(bomba.getNomeBomba());
        }
        comboBombas.setSelectedIndex(-1);

        JLabel labelPreco = new JLabel("Preço do Combustível (R$):");
        txtPrecoCombustivel = new JTextField(20);
        txtPrecoCombustivel.setEditable(false);

        JLabel labelQuantidade = new JLabel("Quantidade Abastecida (L):");
        txtQuantidade = new JTextField(20);

        JLabel labelTotal = new JLabel("Total Pago (R$):");
        txtTotalPago = new JTextField(20);
        txtTotalPago.setEditable(false);

        panel.add(labelBomba);
        panel.add(comboBombas);
        panel.add(labelPreco);
        panel.add(txtPrecoCombustivel);
        panel.add(labelQuantidade);
        panel.add(txtQuantidade);
        panel.add(labelTotal);
        panel.add(txtTotalPago);

        // Listeners para atualização automática
        comboBombas.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                atualizarPrecoCombustivel();
                calcularTotal();
            }
        });

        txtQuantidade.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                calcularTotal();
            }
            public void removeUpdate(DocumentEvent e) {
                calcularTotal();
            }
            public void insertUpdate(DocumentEvent e) {
                calcularTotal();
            }
        });
        
        // Selecionar a primeira bomba por padrão para evitar campos vazios
        if (!bombas.isEmpty()) {
            comboBombas.setSelectedIndex(0);
            atualizarPrecoCombustivel();
        }

        int resultado = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Cadastrar Abastecimento",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            processarCadastro();
        }
    }

    private void atualizarPrecoCombustivel() {
        int selectedIndex = comboBombas.getSelectedIndex();
        if (selectedIndex != -1) {
            bombaSelecionada = bombas.get(selectedIndex);
            double preco = bombaSelecionada.getTipoCombustivel().getPrecoLitro();
            DecimalFormat df = new DecimalFormat("#,##0.00");
            txtPrecoCombustivel.setText(df.format(preco));
        }
    }

    private void calcularTotal() {
        try {
            double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", "."));
            if (bombaSelecionada != null) {
                double preco = bombaSelecionada.getTipoCombustivel().getPrecoLitro();
                double total = quantidade * preco;
                DecimalFormat df = new DecimalFormat("#,##0.00");
                txtTotalPago.setText(df.format(total));
            }
        } catch (NumberFormatException e) {
            txtTotalPago.setText("");
        }
    }

    private void processarCadastro() {
        if (bombaSelecionada == null) {
            exibirErro("Selecione uma bomba de combustível.");
            return;
        }

        String quantidadeTexto = txtQuantidade.getText().trim();
        if (quantidadeTexto.isEmpty()) {
            exibirErro("Por favor, preencha a quantidade abastecida.");
            return;
        }

        try {
            double quantidade = Double.parseDouble(quantidadeTexto.replace(",", "."));
            double preco = bombaSelecionada.getTipoCombustivel().getPrecoLitro();
            double totalPago = quantidade * preco;
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dataHora = sdf.format(new Date());

            Abastecimentos abastecimento = new Abastecimentos();
            abastecimento.setBombaCombustivel(bombaSelecionada);
            abastecimento.setDataAbastecimento(dataHora);
            abastecimento.setLitros(quantidade);
            abastecimento.setQuantidadeValor(preco);
            abastecimento.setTotalPago(totalPago);

            abastecimentosService.criar(abastecimento);
            JOptionPane.showMessageDialog(parent, "Abastecimento cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            exibirErro("A quantidade abastecida deve ser um número válido.");
        }
    }

    private void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(parent, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}