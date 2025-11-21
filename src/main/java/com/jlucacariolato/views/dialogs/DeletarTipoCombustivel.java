package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.services.TipoCombustivelService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletarTipoCombustivel extends JDialog {

    private TipoCombustivelService tipoCombustivelService;

    public DeletarTipoCombustivel(JFrame parent) {
        super(parent, "Deletar Tipo de Combustível", true);
        this.tipoCombustivelService = new TipoCombustivelService();

    }


    public void exibir() {
        exibirOpcoesDeletar(); // chama o JOptionPane
        dispose();
    }

    private void exibirOpcoesDeletar() {
        String input = JOptionPane.showInputDialog(
                getParent(),
                "Digite o ID do Tipo de Combustível a ser deletado:",
                "Deletar Tipo de Combustível",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) return;

        try {
            int id = Integer.parseInt(input.trim());
            int confirm = JOptionPane.showConfirmDialog(
                    getParent(),
                    "Tem certeza que deseja deletar o Tipo com ID: " + id + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                tipoCombustivelService.deletar(id);
                JOptionPane.showMessageDialog(
                        getParent(),
                        "Deletado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
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