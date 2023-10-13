import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Funcionario extends Pessoa {
    private double salario;
    private String funcao;

    public Funcionario(String nome, String cpf, String endereco, String telefone, String email, double salario,
            String funcao) {
        super(nome, cpf, endereco, telefone, email);
        this.salario = salario;
        this.funcao = funcao;
    }

    public static void criarTabelaFuncionarios() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            java.sql.Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS funcionarios (nome VARCHAR(255), cpf VARCHAR(255), endereco VARCHAR(255), telefone VARCHAR(255), email VARCHAR(255), salario DOUBLE, funcao VARCHAR(255))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public double getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    // Setters
    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public static Funcionario cadastrarFuncionario() {
        // Code to gather necessary information and create new Funcionario object
        // For example:
        String nome = JOptionPane.showInputDialog("Digite o nome do funcionário:");
        String cpf = JOptionPane.showInputDialog("Digite o CPF do funcionário:");
        String endereco = JOptionPane.showInputDialog("Digite o endereço do funcionário:");
        String telefone = JOptionPane.showInputDialog("Digite o telefone do funcionário:");
        String email = JOptionPane.showInputDialog("Digite o email do funcionário:");
        double salario = Double.parseDouble(JOptionPane.showInputDialog("Digite o salário do funcionário:"));
        String funcao = JOptionPane.showInputDialog("Digite a função do funcionário:");

        Funcionario funcionario = new Funcionario(nome, cpf, endereco, telefone, email, salario, funcao);
        return funcionario;
    }

    // Método para inserir um funcionário no banco de dados
    public void inserirNoBanco() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO funcionarios (nome, cpf, endereco, telefone, email, salario, funcao) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, getNome());
            statement.setString(2, getCpf());
            statement.setString(3, getEndereco());
            statement.setString(4, getTelefone());
            statement.setString(5, getEmail());
            statement.setDouble(6, salario);
            statement.setString(7, funcao);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "\n==== Funcionario ====" +
                "\nNome:" + getNome() +
                "\nCPF:" + getCpf() +
                "\nEndereço: " + getEndereco() +
                "\nTelefone: " + getTelefone() +
                "\nEmail: " + getEmail() +
                "\nSalário: " + salario +
                "\nFunção: " + funcao;
    }
}
