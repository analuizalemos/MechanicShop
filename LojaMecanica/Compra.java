import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class Compra {
    private int clienteIndex;
    private Cliente cliente;
    private int numeroPedido;
    private ArrayList<Cliente> clientes;
    private Date data;
    private ArrayList<Peca> itensCompra;

    public static void criarTabelaCompras() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS compras (numeroPedido INT, cliente VARCHAR(255), data DATE)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void criarTabelaItensCompra() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS itens_compra (numeroPedido INT, peca VARCHAR(255), quantidade INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Compra(int numCliente, Cliente clients) {
        this.numeroPedido = numCliente;
        this.data = new Date();
        this.itensCompra = new ArrayList<>();
        // rest of your code...

        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO compras (numeroPedido, cliente, data) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numeroPedido);
            statement.setString(2, clients.getNome());
            statement.setDate(3, new java.sql.Date(data.getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente selecionarCliente() {
        Cliente[] clientesArray = clientes.toArray(new Cliente[0]);
        Cliente selectedCliente = (Cliente) JOptionPane.showInputDialog(null, "Selecione um cliente:", "Clientes",
                JOptionPane.QUESTION_MESSAGE, null, clientesArray, clientesArray[0]);
        return selectedCliente;
    }

    // Método para remover um item da compra
    public void removerItemCompra(Peca peca) {
        itensCompra.remove(peca);
    }

    // Método para calcular o total da compra
    public double calcularTotalCompra() {
        double total = 0;

        Map<Peca, Integer> countMap = new HashMap<>();
        for (Peca peca : itensCompra) {
            countMap.put(peca, countMap.getOrDefault(peca, 0) + 1);
        }

        for (Map.Entry<Peca, Integer> entry : countMap.entrySet()) {
            total += entry.getKey().getPreco() * entry.getValue();
        }

        return total;
    }

    public void realizarCompra(int clienteIndex, Peca peca, int quantidade, Loja loja) {
        this.clienteIndex = clienteIndex;
        adicionarItemCompra(peca, quantidade);
        calcularTotalCompra();
        visualizarItensCompra();
        loja.registrarVenda(this);
        System.out.println("Compra registrada. Número do pedido: " + this.numeroPedido);
    }

    // Método para visualizar os itens da compra
    public void visualizarItensCompra() {
        System.out.println("=== Itens da Compra (Pedido #" + numeroPedido + ") ===");
        for (Peca peca : itensCompra) {
            System.out.println("Nome da Peça: " + peca.getNome());
            System.out.println("Preço: " + peca.getPreco());
        }
        System.out.println("Total da Compra: " + calcularTotalCompra());
    }

    public void adicionarItemCompra(Peca peca, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            this.itensCompra.add(peca);
        }
    }

    public void gerarRelatorioVendas(Cliente cliente) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Relatório de Vendas ===\n");
        sb.append("Dados do Cliente: " + cliente.getNome() + "\n"); // Adiciona os dados do cliente ao relatório
        for (Compra compra : cliente.getHistoricoCompras()) {
            Map<Peca, Integer> countMap = new HashMap<>();
            for (Peca peca : compra.getItensCompra()) {
                countMap.put(peca, countMap.getOrDefault(peca, 0) + 1);
            }
            for (Map.Entry<Peca, Integer> entry : countMap.entrySet()) {
                sb.append("Nome da Peça: " + entry.getKey().getNome() + "\n"); // Adiciona o nome da peça ao relatório
                sb.append("Quantidade: " + entry.getValue() + "\n"); // Adiciona a quantidade de peças ao relatório
                sb.append("Preço: " + entry.getKey().getPreco() + "\n"); // Adiciona o preço da peça ao relatório
            }
            sb.append("Total da Compra: " + compra.calcularTotalCompra() + "\n"); // Adiciona o total da compra ao
                                                                                  // relatório
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public ArrayList<Cliente> getClientes() {
        return this.clientes;
    }

    public Date getData() {
        return this.data;
    }

    public ArrayList<Peca> getItensCompra() {
        return this.itensCompra;
    }

    public double calcularTotal() {
        return calcularTotalCompra();
    }

    public int getNumeroPedido() {
        return this.numeroPedido;
    }

    public void registrarVenda(Loja loja) {
        loja.getVendas().add(this);
    }
}
