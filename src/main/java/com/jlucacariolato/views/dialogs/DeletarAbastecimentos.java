package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.services.AbastecimentosService;
import com.jlucacariolato.views.JanelaPrincipal;

import javax.swing.*;

public class DeletarAbastecimentos extends JDialog {

    private AbastecimentosService abastecimentosService;

    public DeletarAbastecimentos(JFrame parent) {
        super(parent, "Deletar Abastecimento", true);
        this.abastecimentosService = new AbastecimentosService();
    }

    public void exibir() {
        exibirOpcoesDeletar();
        dispose();
    }

    private void exibirOpcoesDeletar() {
        String input = JOptionPane.showInputDialog(
                getParent(),
                "Digite o ID do abastecimento a ser deletado:",
                "Deletar Abastecimento",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) return;

        try {
            int id = Integer.parseInt(input.trim());
            int confirm = JOptionPane.showConfirmDialog(
                    getParent(),
                    "Tem certeza que deseja deletar o abastecimento com ID: " + id + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                abastecimentosService.deletar(id);
                JOptionPane.showMessageDialog(
                        getParent(),
                        "Deletado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (getParent() instanceof JanelaPrincipal) {
                    ((JanelaPrincipal) getParent()).verAbastecimentos();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    getParent(),
                    "Erro ao deletar: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}