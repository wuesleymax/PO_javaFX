package dao;

import model.Venda;
import model.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class VendaDAO {

    private Connection connection;

    public VendaDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void insert(Venda venda) {
        String sql = "INSERT INTO vendas (data_venda, cliente_id, produto_id, quantidade, valor_total) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setInt(2, venda.getClienteId());
            stmt.setInt(3, venda.getProdutoId());
            stmt.setInt(4, venda.getQuantidade());
            stmt.setDouble(5, venda.getValorTotal());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    venda.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir venda: " + e.getMessage());
        }
    }

    public List<Venda> findAll() {
        List<Venda> lista = new ArrayList<>();

        String sql = "SELECT v.*, c.nome AS cliente_nome, p.nome AS produto_nome "
                   + "FROM vendas v "
                   + "LEFT JOIN clientes c ON v.cliente_id = c.id "
                   + "LEFT JOIN produtos p ON v.produto_id = p.id "
                   + "ORDER BY v.data_venda DESC, v.id DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setProdutoId(rs.getInt("produto_id"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setClienteNome(rs.getString("cliente_nome"));
                venda.setProdutoNome(rs.getString("produto_nome"));

                lista.add(venda);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas: " + e.getMessage());
        }

        return lista;
    }

    public Venda findById(int id) {
        String sql = "SELECT v.*, c.nome AS cliente_nome, p.nome AS produto_nome "
                   + "FROM vendas v "
                   + "LEFT JOIN clientes c ON v.cliente_id = c.id "
                   + "LEFT JOIN produtos p ON v.produto_id = p.id "
                   + "WHERE v.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                venda.setClienteId(rs.getInt("cliente_id"));
                venda.setProdutoId(rs.getInt("produto_id"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setClienteNome(rs.getString("cliente_nome"));
                venda.setProdutoNome(rs.getString("produto_nome"));
                return venda;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar venda: " + e.getMessage());
        }

        return null;
    }

    public void update(Venda venda) {
        String sql = "UPDATE vendas SET data_venda=?, cliente_id=?, produto_id=?, quantidade=?, valor_total=? "
                   + "WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setInt(2, venda.getClienteId());
            stmt.setInt(3, venda.getProdutoId());
            stmt.setInt(4, venda.getQuantidade());
            stmt.setDouble(5, venda.getValorTotal());
            stmt.setInt(6, venda.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar venda: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM vendas WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir venda: " + e.getMessage());
        }
    }

    // ---------------------- FILTROS -----------------------

    public List<Venda> findByDateRange(LocalDate inicio, LocalDate fim) {
        List<Venda> lista = new ArrayList<>();

        String sql = "SELECT v.*, c.nome AS cliente_nome, p.nome AS produto_nome "
                   + "FROM vendas v "
                   + "LEFT JOIN clientes c ON v.cliente_id = c.id "
                   + "LEFT JOIN produtos p ON v.produto_id = p.id "
                   + "WHERE v.data_venda BETWEEN ? AND ? "
                   + "ORDER BY v.data_venda DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(inicio));
            stmt.setDate(2, Date.valueOf(fim));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setDataVenda(rs.getDate("data_venda").toLocalDate());
                v.setClienteId(rs.getInt("cliente_id"));
                v.setProdutoId(rs.getInt("produto_id"));
                v.setQuantidade(rs.getInt("quantidade"));
                v.setValorTotal(rs.getDouble("valor_total"));
                v.setClienteNome(rs.getString("cliente_nome"));
                v.setProdutoNome(rs.getString("produto_nome"));
                lista.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao filtrar vendas por data: " + e.getMessage());
        }

        return lista;
    }

    public List<Venda> findByCliente(int clienteId) {
        List<Venda> lista = new ArrayList<>();

        String sql = "SELECT v.*, c.nome AS cliente_nome, p.nome AS produto_nome "
                   + "FROM vendas v "
                   + "LEFT JOIN clientes c ON v.cliente_id = c.id "
                   + "LEFT JOIN produtos p ON v.produto_id = p.id "
                   + "WHERE v.cliente_id = ? "
                   + "ORDER BY v.data_venda DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getInt("id"));
                v.setDataVenda(rs.getDate("data_venda").toLocalDate());
                v.setClienteId(rs.getInt("cliente_id"));
                v.setProdutoId(rs.getInt("produto_id"));
                v.setQuantidade(rs.getInt("quantidade"));
                v.setValorTotal(rs.getDouble("valor_total"));
                v.setClienteNome(rs.getString("cliente_nome"));
                v.setProdutoNome(rs.getString("produto_nome"));
                lista.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao filtrar por cliente: " + e.getMessage());
        }

        return lista;
    }

    // ---------------------- RELATÓRIOS / GRÁFICOS -----------------------

    public Map<String, Double> vendasMensais() {
        Map<String, Double> mapa = new LinkedHashMap<>();

        String sql = "SELECT DATE_FORMAT(data_venda, '%Y-%m') AS mes, SUM(valor_total) AS total "
                   + "FROM vendas "
                   + "GROUP BY DATE_FORMAT(data_venda, '%Y-%m') "
                   + "ORDER BY mes";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                mapa.put(rs.getString("mes"), rs.getDouble("total"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter vendas mensais: " + e.getMessage());
        }

        return mapa;
    }

    public double totalVendas() {
        String sql = "SELECT SUM(valor_total) AS total FROM vendas";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter total de vendas: " + e.getMessage());
        }

        return 0.0;
    }

    public Map<String, Double> vendasUltimosMeses(int meses) {
        Map<String, Double> mapa = new LinkedHashMap<>();

        String sql = "SELECT DATE_FORMAT(data_venda, '%Y-%m') AS mes, SUM(valor_total) AS total "
                   + "FROM vendas "
                   + "WHERE data_venda >= DATE_SUB(CURDATE(), INTERVAL ? MONTH) "
                   + "GROUP BY DATE_FORMAT(data_venda, '%Y-%m') "
                   + "ORDER BY mes";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, meses);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                mapa.put(rs.getString("mes"), rs.getDouble("total"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter vendas dos últimos meses: " + e.getMessage());
        }

        return mapa;
    }
}
