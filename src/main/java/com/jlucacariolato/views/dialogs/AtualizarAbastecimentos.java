package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.model.Abastecimentos;
import com.jlucacariolato.model.BombasCombustivel;
import com.jlucacariolato.services.AbastecimentosService;
import com.jlucacariolato.services.BombasCombustivelService;
import com.jlucacariolato.views.JanelaPrincipal;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.List;

public class AtualizarAbastecimentos extends JDialog {
    private JComboBox<Long> comboAbastecimentoId;
    private JComboBox<String> comboBombas;
    private JTextField txtQuantidade;
    private JTextField txtTotalPago;
    private JTextField txtData;
    private AbastecimentosService abastecimentosService;
    private BombasCombustivelService bombasCombustivelService;
    private List<Abastecimentos> abastecimentos;
    private List<BombasCombustivel> bombas;
    private Abastecimentos abastecimentoSelecionado;
    private BombasCombustivel bombaSelecionada;

    public AtualizarAbastecimentos(JFrame parent) {
        super(parent, "Atualizar Abastecimento", true);
        this.abastecimentosService = new AbastecimentosService();
        this.bombasCombustivelService = new BombasCombustivelService();

        carregarAbastecimentosIds();
        carregarBombas();
    }

    // Carrega os IDs de todos os abastecimentos cadastrados no combo box
    private void carregarAbastecimentosIds() {
        abastecimentos = abastecimentosService.listar();
        comboAbastecimentoId = new JComboBox<>();
        for (Abastecimentos ab : abastecimentos) {
            comboAbastecimentoId.addItem((long) ab.getId());
        }
    }

    // Carrega todas as bombas de combustível disponíveis no combo box
    private void carregarBombas() {
        bombas = bombasCombustivelService.listar();
        comboBombas = new JComboBox<>();
        for (BombasCombustivel bomba : bombas) {
            comboBombas.addItem(bomba.getNomeBomba());
        }
    }

    // Exibe o diálogo de atualização de abastecimento com os campos preenchidos
    public void exibir() {
        if (abastecimentos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum abastecimento para atualizar.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Selecione o ID do Abastecimento:"));
        panel.add(comboAbastecimentoId);

        panel.add(new JLabel("Bomba de Combustível:"));
        panel.add(comboBombas);

        panel.add(new JLabel("Quantidade Abastecida (L):"));
        txtQuantidade = new JTextField();
        panel.add(txtQuantidade);

        panel.add(new JLabel("Data do Abastecimento:"));
        txtData = new JTextField();
        txtData.setEditable(false);
        panel.add(txtData);

        panel.add(new JLabel("Total Pago (R$):"));
        txtTotalPago = new JTextField();
        txtTotalPago.setEditable(false);
        panel.add(txtTotalPago);

        comboAbastecimentoId.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                povoarCampos();
            }
        });

        comboBombas.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int selectedIndex = comboBombas.getSelectedIndex();
                if (selectedIndex != -1) {
                    bombaSelecionada = bombas.get(selectedIndex);
                    calcularTotal();
                }
            }
        });
        
        txtQuantidade.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { calcularTotal(); }
            public void removeUpdate(DocumentEvent e) { calcularTotal(); }
            public void insertUpdate(DocumentEvent e) { calcularTotal(); }
        });

        if (!abastecimentos.isEmpty()) {
            comboAbastecimentoId.setSelectedIndex(0);
            povoarCampos();
        }

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Atualizar Abastecimento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            processarAtualizacao();
        }
    }

    // Preenche os campos do formulário com os dados do abastecimento selecionado
    private void povoarCampos() {
        Long selectedId = (Long) comboAbastecimentoId.getSelectedItem();
        if (selectedId == null) return;

        abastecimentoSelecionado = abastecimentosService.buscarPorId(selectedId);

        if (abastecimentoSelecionado != null) {
            txtQuantidade.setText(String.valueOf(abastecimentoSelecionado.getLitros()).replace('.', ','));
            txtData.setText(abastecimentoSelecionado.getDataAbastecimento());
            bombaSelecionada = abastecimentoSelecionado.getBombaCombustivel();
            
            for (int i = 0; i < bombas.size(); i++) {
                if (bombas.get(i).getId() == bombaSelecionada.getId()) {
                    comboBombas.setSelectedIndex(i);
                    break;
                }
            }
            calcularTotal();
        }
    }

    // Calcula automaticamente o valor total baseado na quantidade e preço do combustível
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

    // Processa e salva as alterações do abastecimento no banco de dados
    private void processarAtualizacao() {
        if (abastecimentoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Nenhum abastecimento selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", "."));
            int bombaIndex = comboBombas.getSelectedIndex();
            BombasCombustivel novaBomba = bombas.get(bombaIndex);

            abastecimentoSelecionado.setLitros(quantidade);
            abastecimentoSelecionado.setBombaCombustivel(novaBomba);
            
            double preco = novaBomba.getTipoCombustivel().getPrecoLitro();
            double totalPago = quantidade * preco;
            abastecimentoSelecionado.setTotalPago(totalPago);
            abastecimentoSelecionado.setQuantidadeValor(preco);


            abastecimentosService.atualizar(abastecimentoSelecionado);
            JOptionPane.showMessageDialog(this, "Abastecimento atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            if (getParent() instanceof JanelaPrincipal) {
                ((JanelaPrincipal) getParent()).verAbastecimentos();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "A quantidade deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar o abastecimento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}