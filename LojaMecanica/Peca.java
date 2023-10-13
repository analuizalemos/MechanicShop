import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Peca {
    private String nome;
    private String numeroSerie;
    private String modeloVeiculo;
    private String marcaVeiculo;
    private double tamanho;
    private double preco;
    private int quantidade;

    // Construtor da classe Peca
    public Peca(String nome, String numeroSerie, String modeloVeiculo, String marcaVeiculo, double tamanho,
            double preco, int quantidade) {
        this.nome = nome;
        this.numeroSerie = numeroSerie;
        this.modeloVeiculo = modeloVeiculo;
        this.marcaVeiculo = marcaVeiculo;
        this.tamanho = tamanho;
        this.preco = preco;
        this.quantidade = quantidade;

        // Inserindo a peça no banco de dados
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO pecas (nome, numeroSerie, modeloVeiculo, marcaVeiculo, tamanho, preco, quantidade) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, numeroSerie);
            statement.setString(3, modeloVeiculo);
            statement.setString(4, marcaVeiculo);
            statement.setDouble(5, tamanho);
            statement.setDouble(6, preco);
            statement.setInt(7, quantidade);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos get e set para acessar e modificar os atributos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    public void setMarcaVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getModelo() {
        return null;
    }

    public String getMarca() {
        return null;
    }

    // Método para obter detalhes da peça
    public String getDetalhes() {
        return "Nome: " + nome + "\n" +
                "Número de Série: " + numeroSerie + "\n" +
                "Modelo do Veículo: " + modeloVeiculo + "\n" +
                "Marca do Veículo: " + marcaVeiculo + "\n" +
                "Tamanho: " + tamanho + "\n" +
                "Preço: " + preco + "\n" +
                "Quantidade: " + quantidade;
    }

    public void removerQuantidade(int quantidadeRemover) {
    }

    @Override
    public String toString() {
        return "Peca: " + this.nome;
    }
}
