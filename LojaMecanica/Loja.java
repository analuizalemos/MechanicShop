import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Loja {
    private String nome;
    private String URL;
    private String CNPJ;
    private ArrayList<Compra> vendas;

    // Construtor da classe Loja
    public Loja(String nome, String URL, String CNPJ, Dono dono, Estoque estoque) {
        this.nome = nome;
        this.URL = URL;
        this.CNPJ = CNPJ;
        vendas = new ArrayList<>();

        // Insert the new store into the database
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO lojas (nome, URL, CNPJ) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, URL);
            statement.setString(3, CNPJ);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to create the store table
    public static void criarTabelaLoja() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS lojas (nome VARCHAR(255), URL VARCHAR(255), CNPJ VARCHAR(255))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para criar o site da loja
    public String criarSiteLoja() {
        return ("Site criado!\nhttp://www.radiadoreslemos.com");
    }

    public void setUrl(String url) {
        this.URL = url;
        updateLoja();
    }

    public String getUrl(String url) {
        return this.URL;
    }

    public void setCnpj(String cnpj) {
        this.CNPJ = cnpj;
        updateLoja();
    }

    public String getCnpj(String cnpj) {
        return this.CNPJ;
    }

    public void setNome(String nomeLoja) {
        this.nome = nomeLoja;
        updateLoja();
    }

    public String getNome(String nomeLoja) {
        return this.nome;
    }

    public ArrayList<Compra> getVendas() {
        return vendas;
    }

    // Method to update the store's information in the database
    private void updateLoja() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "UPDATE lojas SET nome = ?, URL = ?, CNPJ = ? WHERE nome = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, URL);
            statement.setString(3, CNPJ);
            statement.setString(4, nome);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarVenda(Compra compra) {
        this.vendas.add(compra);
        System.out.println("Venda registrada com sucesso. Número do pedido: " + compra.getNumeroPedido());
    }
}