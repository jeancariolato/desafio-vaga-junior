package com.jlucacariolato.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jlucacariolato.model.BombasCombustivel;
import com.jlucacariolato.model.TipoCombustivel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BombasCombustivelDAO {
    private TipoCombustivelDAO tipoCombustivelDAO = new TipoCombustivelDAO();

    public BombasCombustivelDAO() {
        criarTabela();
    }

    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS bombas_combustivel (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nome VARCHAR(255)," +
                "tipoCombustivelId INT," +
                "FOREIGN KEY (tipoCombustivelId) REFERENCES tipo_combustivel(id)" +
                ")";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Criar bomba de combustível
    public void criarBombaCombustivel(BombasCombustivel bombasCombustivel) {
        String sql = "INSERT INTO bombas_combustivel (nome, tipoCombustivelId) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bombasCombustivel.getNomeBomba());
            pstmt.setInt(2, bombasCombustivel.getTipoCombustivel().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar todas as bombas de combustível
    public List<BombasCombustivel> listarBombas() {
        List<BombasCombustivel> lista = new ArrayList<>();
        String sql = "SELECT b.id, b.nome, b.tipoCombustivelId FROM bombas_combustivel b";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BombasCombustivel bomba = new BombasCombustivel();
                bomba.setId(rs.getInt("id"));
                bomba.setNomeBomba(rs.getString("nome"));
                int tipoCombustivelID = rs.getInt("tipoCombustivelId");
                // Busca o tipo de combustivel que está associado
                for (TipoCombustivel tipo : TipoCombustivelDAO.listarTipos()) {
                    if (tipo.getId() == tipoCombustivelID) {
                        bomba.setTipoCombustivel(tipo);
                        break;
                    }
                }
                lista.add(bomba);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Atualizar bomba de combustível
    public void update(BombasCombustivel bombasCombustivel) {
        String sql = "UPDATE bombas_combustivel SET nome = ?, tipoCombustivelId = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bombasCombustivel.getNomeBomba());
            pstmt.setInt(2, bombasCombustivel.getTipoCombustivel().getId());
            pstmt.setInt(3, bombasCombustivel.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deletar bomba de combustivel
    public void deletar(int id) {
        String sql = "DELETE FROM bombas_combustivel WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
