package com.jlucacariolato.views.dialogs;

import com.jlucacariolato.services.BombasCombustivelService;
import com.jlucacariolato.views.JanelaPrincipal;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class DeletarBomba extends JDialog {

    private BombasCombustivelService bombasCombustivelService;

    //CONSTRUTOR
    public DeletarBomba(JFrame parent){
        super(parent, "Deletar Bomba de Combustível", true);
        this.bombasCombustivelService = new BombasCombustivelService();
    }

    // Exibe o diálogo para deletar uma bomba de combustível
    public void exibir() {
        exibirOpcoesDeletar(); // chama o JOptionPane
        dispose();
    }

    // Solicita o ID da bomba e confirma a exclusão
    private void exibirOpcoesDeletar() {
        String input = JOptionPane.showInputDialog(
                getParent(),
                "Digite o ID da bomba a ser deletada:",
                "Deletar bomba de combustível",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) return;

        try {
            int id = Integer.parseInt(input.trim());
            int confirm = JOptionPane.showConfirmDialog(
                    getParent(),
                    "Tem certeza que deseja deletar a bomba com ID: " + id + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                bombasCombustivelService.deletar(id);
                JOptionPane.showMessageDialog(
                        getParent(),
                        "Deletado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (getParent() instanceof JanelaPrincipal) {
                    ((JanelaPrincipal) getParent()).verBombas();
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
