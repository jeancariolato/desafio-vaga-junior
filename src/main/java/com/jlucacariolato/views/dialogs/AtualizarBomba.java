package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.model.BombasCombustivel;
import com.jlucacariolato.model.TipoCombustivel;
import com.jlucacariolato.services.BombasCombustivelService;
import com.jlucacariolato.services.TipoCombustivelService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class AtualizarBomba extends JDialog {
    private JComboBox<Integer> comboBombaId;
    private JTextField txtNome;
    private JComboBox<String> comboTipoCombustivel;
    private BombasCombustivelService bombasCombustivelService;
    private TipoCombustivelService tipoCombustivelService;
    private List<TipoCombustivel> tiposCombustivel;

    public AtualizarBomba(JFrame parent) {
        super(parent, "Atualizar Bomba de Combustível", true);
        this.bombasCombustivelService = new BombasCombustivelService();
        this.tipoCombustivelService = new TipoCombustivelService();
        this.txtNome = new JTextField();

        carregarBombasIds();
        carregarTiposCombustivel();

        comboBombaId.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    atualizarCamposParaBombaId((Integer) e.getItem());
                }
            }
        });

        if (comboBombaId.getItemCount() > 0) {
            comboBombaId.setSelectedIndex(0);
        }
    }

    private void atualizarCamposParaBombaId(Integer id) {
        if (id == null) {
            return;
        }
        BombasCombustivel bomba = bombasCombustivelService.buscarPorId(id);
        if (bomba != null) {
            txtNome.setText(bomba.getNomeBomba());
            for (int i = 0; i < tiposCombustivel.size(); i++) {
                if (tiposCombustivel.get(i).getId() == bomba.getTipoCombustivel().getId()) {
                    comboTipoCombustivel.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void carregarBombasIds() {
        try {
            List<BombasCombustivel> bombas = bombasCombustivelService.listar();
            comboBombaId = new JComboBox<>();
            for (BombasCombustivel bomba : bombas) {
                comboBombaId.addItem(bomba.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar IDs das bombas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTiposCombustivel() {
        try {
            tiposCombustivel = tipoCombustivelService.listar();
            comboTipoCombustivel = new JComboBox<>();
            for (TipoCombustivel tipo : tiposCombustivel) {
                comboTipoCombustivel.addItem(tipo.getNome());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar tipos de combustível: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibir() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelBombaId = new JLabel("Selecione o ID da Bomba:");
        JLabel labelNome = new JLabel("Novo Nome da Bomba:");
        JLabel labelTipoCombustivel = new JLabel("Novo Tipo de Combustível:");

        panel.add(labelBombaId);
        panel.add(comboBombaId);
        panel.add(labelNome);
        panel.add(txtNome);
        panel.add(labelTipoCombustivel);
        panel.add(comboTipoCombustivel);

        // Atualiza os campos com os dados da bomba selecionada inicialmente
        if (comboBombaId.getItemCount() > 0) {
            atualizarCamposParaBombaId((Integer) comboBombaId.getSelectedItem());
        }

        int resultado = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Atualizar Bomba de Combustível",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                Integer bombaId = (Integer) comboBombaId.getSelectedItem();
                if (bombaId == null) {
                    JOptionPane.showMessageDialog(this, "Nenhum ID de bomba selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String novoNome = txtNome.getText();
                if (novoNome == null || novoNome.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O nome da bomba não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int selectedTipoIndex = comboTipoCombustivel.getSelectedIndex();
                if (selectedTipoIndex == -1) {
                    JOptionPane.showMessageDialog(this, "Nenhum tipo de combustível selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TipoCombustivel tipoSelecionado = tiposCombustivel.get(selectedTipoIndex);

                BombasCombustivel bombaParaAtualizar = bombasCombustivelService.buscarPorId(bombaId);
                if (bombaParaAtualizar == null) {
                    JOptionPane.showMessageDialog(this, "Bomba não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                bombaParaAtualizar.setNomeBomba(novoNome);
                bombaParaAtualizar.setTipoCombustivel(tipoSelecionado);

                bombasCombustivelService.atualizar(bombaParaAtualizar);

                JOptionPane.showMessageDialog(this, "Bomba de combustível atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar a bomba de combustível: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}