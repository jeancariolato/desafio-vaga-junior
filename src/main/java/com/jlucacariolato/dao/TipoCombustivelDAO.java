package com.jlucacariolato.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jlucacariolato.model.TipoCombustivel;
public class TipoCombustivelDAO {

    // CONSTRUTOR
    // Criando tabela de Tipo de Combustível
    public TipoCombustivelDAO() {
        criarTabela();
    }

    // MÉTODO PARA CRIAR TABELA CASO NAO EXISTA
    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS tipo_combustivel (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nome VARCHAR(255)," +
                "precoLitro DOUBLE" +
                ")";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CRUD

    //Criar tipo de combustível
    public void criarTipoCombustivel(TipoCombustivel tipoCombustivel) {
        String sql = "INSERT INTO tipo_combustivel (nome, precoLitro) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipoCombustivel.getNome());
            pstmt.setDouble(2, tipoCombustivel.getPrecoLitro());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Listar todos os tipos de combustíveis
    public static List<TipoCombustivel> listarTipos() {
        List <TipoCombustivel> tiposCombustivel = new ArrayList<>();
        String sql = "SELECT * FROM tipo_combustivel";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoCombustivel tipoCombustivel = new TipoCombustivel();
                tipoCombustivel.setId(rs.getInt("id"));
                tipoCombustivel.setNome(rs.getString("nome"));
                tipoCombustivel.setPrecoLitro(rs.getDouble("precoLitro"));

                tiposCombustivel.add(tipoCombustivel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiposCombustivel;
    }

    //Atualizar
    public void atualizarTipoCombustivel(TipoCombustivel tipoCombustivel) {
        String sql = "UPDATE tipo_combustivel SET nome = ?, precoLitro = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipoCombustivel.getNome());
            pstmt.setDouble(2, tipoCombustivel.getPrecoLitro());
            pstmt.setInt(3, tipoCombustivel.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Deletar
    public void deletarTipoCombustivel(int id) {
        String sql = "DELETE FROM tipo_combustivel WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
