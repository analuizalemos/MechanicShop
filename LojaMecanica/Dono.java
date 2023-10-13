import javax.swing.JOptionPane;

public class Dono extends Pessoa {
 

    // Construtor da classe Dono
    public Dono(String nome, String cpf, String endereco, String telefone, String email) {
        super(nome, cpf, endereco, telefone, email);
    }

    // Método para cadastrar o dono
    public static Dono cadastrarDono() {
        String nome = JOptionPane.showInputDialog("Digite o nome do dono:");
        String cpf = JOptionPane.showInputDialog("Digite o CPF do dono:");
        String endereco = JOptionPane.showInputDialog("Digite o endereço do dono:");
        String telefone = JOptionPane.showInputDialog("Digite o telefone do dono:");
        String email = JOptionPane.showInputDialog("Digite o email do dono:");

        Dono dono = new Dono(nome, cpf, endereco, telefone, email);
        JOptionPane.showMessageDialog(null, "Dono cadastrado com sucesso.");

        return dono;
    }

    @Override
    public String toString() {
    return "== Dono ==" +
        "\nNome:" + getNome() + 
        "\nCPF:" + getCpf() + 
        "\nEndereço: " + getEndereco() + 
        "\nTelefone: " + getTelefone() + 
        "\nEmail: " + getEmail();
}
}
