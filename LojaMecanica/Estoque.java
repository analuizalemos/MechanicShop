import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Estoque {
    private ArrayList<Peca> pecas;

    // Construtor da classe Estoque
    public Estoque() {
        pecas = new ArrayList<>();
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }

    // Método para adicionar uma peça ao estoque
    public void adicionarPeca(Peca peca) {
        pecas.add(peca);

        // Inserindo a peça no banco de dados
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO pecas (nome, numeroSerie, modelo, marca, tamanho, preco, quantidade) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, peca.getNome());
            statement.setString(2, peca.getNumeroSerie());
            statement.setString(3, peca.getModelo());
            statement.setString(4, peca.getMarca());
            statement.setDouble(5, peca.getTamanho());
            statement.setDouble(6, peca.getPreco());
            statement.setInt(7, peca.getQuantidade());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para remover uma peça do estoque
    public void removerPeca(Peca peca) {
        if (pecas.contains(peca)) {
            pecas.remove(peca);

            // Removendo a peça do banco de dados
            try {
                Connection connection = DatabaseConnection.getConnection();
                String sql = "DELETE FROM pecas WHERE numeroSerie = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, peca.getNumeroSerie());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para listar o estoque
    public String listarEstoque() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Estoque de Peças ===\n");
        for (Peca peca : pecas) {
            sb.append("Nome: " + peca.getNome() + "\n");
            sb.append("Número de Série: " + peca.getNumeroSerie() + "\n");
            sb.append("Modelo: " + peca.getModelo() + "\n");
            sb.append("Marca: " + peca.getMarca() + "\n");
            sb.append("Tamanho: " + peca.getTamanho() + "\n");
            sb.append("Preço: " + peca.getPreco() + "\n");
            sb.append("Quantidade: " + peca.getQuantidade() + "\n\n");
        }
        return sb.toString();
    }

    public void diminuirQuantidadePeca(Peca peca, int quantidade) {
        int novaQuantidade = peca.getQuantidade() - quantidade;
        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade insuficiente no estoque.");
        }
        peca.setQuantidade(novaQuantidade);
    }

}
