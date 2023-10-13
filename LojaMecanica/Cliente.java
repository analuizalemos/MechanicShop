import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Cliente extends Pessoa {
    private int numCliente;
    private ArrayList<Compra> historicoCompras;
    private ArrayList<Peca> carrinho;
    private Cliente cliente;

    public Cliente(String nome, String cpf, String endereco, String telefone, String email) {
        super(nome, cpf, endereco, telefone, email);
        this.numCliente = gerarNumeroAleatorio();
        this.historicoCompras = new ArrayList<>();
        this.carrinho = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO clientes (nome, cpf, endereco, telefone, email, numCliente) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, cpf);
            statement.setString(3, endereco);
            statement.setString(4, telefone);
            statement.setString(5, email);
            statement.setInt(6, numCliente);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Cliente cadastrarCliente() {
        String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");
        String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente:");
        String endereco = JOptionPane.showInputDialog("Digite o endereço do cliente:");
        String telefone = JOptionPane.showInputDialog("Digite o telefone do cliente:");
        String email = JOptionPane.showInputDialog("Digite o email do cliente:");

        Cliente cliente = new Cliente(nome, cpf, endereco, telefone, email);
        return cliente;
    }

    public static void criarTabela() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS clientes (nome VARCHAR(255), cpf VARCHAR(255), endereco VARCHAR(255), telefone VARCHAR(255), email VARCHAR(255), numCliente INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserirNoBanco() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO clientes (nome, cpf, endereco, telefone, email, numCliente) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, getNome());
            statement.setString(2, getCpf());
            statement.setString(3, getEndereco());
            statement.setString(4, getTelefone());
            statement.setString(5, getEmail());
            statement.setInt(6, numCliente);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int gerarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    // ... rest of your code

    @Override
    public String toString() {
        return "== Cliente ==" +
                "\nNome:" + getNome() +
                "\nCPF:" + getCpf() +
                "\nEndereço: " + getEndereco() +
                "\nTelefone: " + getTelefone() +
                "\nEmail: " + getEmail();
    }

    public void adicionarPecaAoCarrinho(Peca peca, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            carrinho.add(peca);
        }
    }

    public void removerPecaDoCarrinho(Peca peca) {
        if (carrinho.contains(peca)) {
            carrinho.remove(peca);
            JOptionPane.showMessageDialog(null, "Peça removida do carrinho com sucesso.");
        } else {
            JOptionPane.showMessageDialog(null, "Peça não encontrada no carrinho.");
        }
    }

    public void realizarCompra() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O carrinho está vazio. Adicione peças antes de realizar uma compra.");
            return;
        }

        Compra novaCompra = new Compra(numCliente, this);
        for (Peca peca : carrinho) {
            novaCompra.adicionarItemCompra(peca, peca.getQuantidade());
        }
        historicoCompras.add(novaCompra);
        carrinho.clear();

        JOptionPane.showMessageDialog(null, "Compra realizada com sucesso. Obrigado!");
    }

    public void visualizarHistoricoCompras() {
        System.out.println("=== Histórico de Compras ===");
        for (Compra compra : historicoCompras) {
            compra.visualizarItensCompra();
        }
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public int getNumeroCliente() {
        return numCliente;
    }

    public ArrayList<Peca> getCarrinho() {
        return carrinho;
    }

    public ArrayList<Compra> getHistoricoCompras() {
        return historicoCompras;
    }

    public void adicionarCompra(Compra compra) {
        historicoCompras.add(compra);
    }

    public void limparCarrinho() {
        carrinho.clear();
    }
}