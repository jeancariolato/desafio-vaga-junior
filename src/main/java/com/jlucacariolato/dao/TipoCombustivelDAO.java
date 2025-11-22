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


    }

    //CRUD

    // Insere um novo tipo de combustível no banco de dados
    public void criarTipoCombustivel(TipoCombustivel tipoCombustivel) {
        String sql = "INSERT INTO tipos_combustivel (nome, preco_litro) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipoCombustivel.getNome());
            pstmt.setDouble(2, tipoCombustivel.getPrecoLitro());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retorna todos os tipos de combustíveis cadastrados
    public List<TipoCombustivel> listarTipos() {
        List<TipoCombustivel> tiposCombustivel = new ArrayList<>();
        String sql = "SELECT * FROM tipos_combustivel";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TipoCombustivel tipoCombustivel = new TipoCombustivel();
                tipoCombustivel.setId(rs.getInt("id"));
                tipoCombustivel.setNome(rs.getString("nome"));
                tipoCombustivel.setPrecoLitro(rs.getDouble("preco_litro"));

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço/Litro: " + rs.getDouble("preco_litro"));

                tiposCombustivel.add(tipoCombustivel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiposCombustivel;
    }

    // Atualiza os dados de um tipo de combustível existente
    public void atualizarTipoCombustivel(TipoCombustivel tipoCombustivel) {
        String sql = "UPDATE tipos_combustivel SET nome = ?, preco_litro = ? WHERE id = ?";
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

    // Remove um tipo de combustível do banco de dados pelo ID
    public void deletarTipoCombustivel(int id) {
        String sql = "DELETE FROM tipos_combustivel WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Busca e retorna um tipo de combustível específico pelo ID
    public TipoCombustivel buscarPorId(int id) {
        String sql = "SELECT * FROM tipos_combustivel WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TipoCombustivel tipoCombustivel = new TipoCombustivel();
                tipoCombustivel.setId(rs.getInt("id"));
                tipoCombustivel.setNome(rs.getString("nome"));
                tipoCombustivel.setPrecoLitro(rs.getDouble("preco_litro"));
                return tipoCombustivel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
