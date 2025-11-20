package com.jlucacariolato.dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.jlucacariolato.model.Abastecimentos;
import com.jlucacariolato.model.BombasCombustivel;

public class AbastecimentosDAO {

    public AbastecimentosDAO() {
        criarTabela();
    }

    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS abastecimentos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "bombaCombustivelId INT," +
                "dataHoraAbastecimento DATETIME," +
                "quantidadeAbastecida DOUBLE," +
                "precoCombustivel DOUBLE," +
                "totalPago DOUBLE," +
                "FOREIGN KEY (bombaCombustivelId) REFERENCES bombas_combustivel(id)" +
                ")";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Criar
    public void criarAbastecimento(Abastecimentos abastecimento) {
        String sql = "INSERT INTO abastecimentos (bombaCombustivelId, dataAbastecimento, quantidadeAbastecida, precoCombustivel, totalPago) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Parse String para data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(abastecimento.getDataAbastecimento(), formatter);
            LocalDateTime ldt = data.atStartOfDay(); // 00:00:00

            pstmt.setInt(1, abastecimento.getBombaCombustivel().getId());
            pstmt.setTimestamp(2, Timestamp.valueOf(ldt));
            pstmt.setDouble(3, abastecimento.getLitros());
            pstmt.setDouble(4, abastecimento.getBombaCombustivel().getTipoCombustivel().getPrecoLitro());
            pstmt.setDouble(5, abastecimento.getTotalPago());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar
    public List<Abastecimentos> listarAbastecimentos() {
        List<Abastecimentos> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.bombaCombustivelId, a.dataHoraAbastecimento, a.quantidadeAbastecida, a.precoCombustivel, a.totalPago FROM abastecimentos a";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            while (rs.next()) {
                Abastecimentos abastecimento = new Abastecimentos();
                abastecimento.setId(rs.getInt("id"));
                abastecimento.getBombaCombustivel().getId();
                abastecimento.setDataAbastecimento(rs.getTimestamp("dataHoraAbastecimento").toLocalDateTime().format(formatter));
                abastecimento.setLitros(rs.getDouble("quantidadeAbastecida"));
                lista.add(abastecimento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Atualizar
    public void update(Abastecimentos abastecimento) {
        String sql = "UPDATE abastecimentos SET bombaCombustivelId = ?, dataHoraAbastecimento = ?, quantidadeAbastecida = ?, precoCombustivel = ?, totalPago = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Parse String para data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(abastecimento.getDataAbastecimento(), formatter);
            LocalDateTime ldt = data.atStartOfDay();
            
            pstmt.setInt(1, abastecimento.getBombaCombustivel().getId());
            pstmt.setTimestamp(2, Timestamp.valueOf(ldt));
            pstmt.setDouble(3, abastecimento.getLitros());
            pstmt.setDouble(4, abastecimento.getBombaCombustivel().getTipoCombustivel().getPrecoLitro());
            pstmt.setDouble(5, abastecimento.getTotalPago());
            pstmt.setInt(6, abastecimento.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deletar
    public void deletar(int id) {
        String sql = "DELETE FROM abastecimentos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
