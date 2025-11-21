package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.model.TipoCombustivel;
import com.jlucacariolato.services.TipoCombustivelService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class AtualizarTipoCombustivel extends JDialog {
    private JComboBox<Integer> combo;
    private JTextField txtNome;
    private JTextField txtPreco;
    private TipoCombustivelService tipoCombustivelService;

    //CONSTRUTOR
    public AtualizarTipoCombustivel(JFrame parent) {
        super(parent, "Atualizar Tipo de Combustível", true);
        this.tipoCombustivelService = new TipoCombustivelService();
        carregarIds();

        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Integer id = (Integer) e.getItem();
                    if (id != null) {
                        TipoCombustivel tipo = tipoCombustivelService.buscarPorId(id);
                        if (tipo != null) {
                            txtNome.setText(tipo.getNome());
                            txtPreco.setText(String.valueOf(tipo.getPrecoLitro()));
                        }
                    }
                }
            }
        });

        // Preenche os campos com o primeiro item da lista
        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0);
        }
    }

    private void carregarIds() {
        try {
            List<TipoCombustivel> tipos = tipoCombustivelService.listar();
            combo = new JComboBox<>();
            for (TipoCombustivel tipo : tipos) {
                combo.addItem(tipo.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar IDs: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void exibir(){
        // Criar painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Criar componentes
        JLabel labelId = new JLabel("Selecione o id");

        JLabel labelNome = new JLabel("Nome do Combustível:");
        txtNome = new JTextField(20);

        JLabel labelPreco = new JLabel("Preço por Litro (R$):");
        txtPreco = new JTextField(20);

        // Adicionar componentes ao painel
        panel.add(labelId);
        panel.add(combo);
        panel.add(labelNome);
        panel.add(txtNome);
        panel.add(labelPreco);
        panel.add(txtPreco);

        // Exibir diálogo
        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Atualizar Tipo de Combustível",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                Integer id = (Integer) combo.getSelectedItem();
                if (id == null) {
                    JOptionPane.showMessageDialog(this, "Nenhum ID selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String nome = txtNome.getText();
                double preco = Double.parseDouble(txtPreco.getText());

                TipoCombustivel tipoAtualizado = new TipoCombustivel();
                tipoAtualizado.setId(id);
                tipoAtualizado.setNome(nome);
                tipoAtualizado.setPrecoLitro(preco);

                tipoCombustivelService.atualizar(tipoAtualizado);

                JOptionPane.showMessageDialog(this, "Tipo de combustível atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "O preço deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar o tipo de combustível: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

}
