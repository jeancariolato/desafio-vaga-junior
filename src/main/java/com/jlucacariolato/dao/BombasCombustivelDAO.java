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
    public BombasCombustivelDAO() {

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
        String sql = "SELECT b.id as bomba_id, b.nome as bomba_nome, t.id as tipo_id, t.nome as tipo_nome, t.precoLitro as tipo_preco " +
                     "FROM bombas_combustivel b " +
                     "JOIN tipo_combustivel t ON b.tipoCombustivelId = t.id";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BombasCombustivel bomba = new BombasCombustivel();
                bomba.setId(rs.getInt("bomba_id"));
                bomba.setNomeBomba(rs.getString("bomba_nome"));

                TipoCombustivel tipo = new TipoCombustivel();
                tipo.setId(rs.getInt("tipo_id"));
                tipo.setNome(rs.getString("tipo_nome"));
                tipo.setPrecoLitro(rs.getDouble("tipo_preco"));

                bomba.setTipoCombustivel(tipo);
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

    // Buscar bomba por ID
    public BombasCombustivel buscarBombaPorId(int id) {
        String sql = "SELECT b.id as bomba_id, b.nome as bomba_nome, t.id as tipo_id, t.nome as tipo_nome, t.precoLitro as tipo_preco " +
                     "FROM bombas_combustivel b " +
                     "JOIN tipo_combustivel t ON b.tipoCombustivelId = t.id " +
                     "WHERE b.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                BombasCombustivel bomba = new BombasCombustivel();
                bomba.setId(rs.getInt("bomba_id"));
                bomba.setNomeBomba(rs.getString("bomba_nome"));

                TipoCombustivel tipo = new TipoCombustivel();
                tipo.setId(rs.getInt("tipo_id"));
                tipo.setNome(rs.getString("tipo_nome"));
                tipo.setPrecoLitro(rs.getDouble("tipo_preco"));

                bomba.setTipoCombustivel(tipo);
                return bomba;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
