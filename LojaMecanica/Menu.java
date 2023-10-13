import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Menu {
    private JFrame frame;
    private Dono dono;
    private ArrayList<Cliente> clientes;
    private Estoque estoque;
    private Loja loja;
    private Cliente clienteAtual;
    private ArrayList<Compra> vendas;
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();

    public Menu() {
        frame = new JFrame("Menu da Loja de Peças de Radiadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        dono = null;
        clientes = new ArrayList<Cliente>();
        vendas = new ArrayList<Compra>();
        estoque = new Estoque();
        loja = new Loja(null, null, null, dono, estoque);

        createMenuInterface();
    }

    private void createMenuInterface() {
        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10)); // 3 rows, 3 columns, 10px horizontal and vertical gaps
        frame.add(panel);

        // Criação dos botões
        JButton donoButton = createButton("Dono", e -> handleDonoAction());
        JButton clienteButton = createButton("Cliente", e -> handleClienteAction());
        JButton funcionarioButton = createButton("Funcionário", e -> handleFuncionarioAction());
        JButton comprasButton = createButton("Compras", e -> handleComprasAction());
        JButton estoqueButton = createButton("Estoque", e -> handleEstoqueAction());
        JButton pecaButton = createButton("Peça", e -> handlePecaAction());
        JButton lojaButton = createButton("Loja", e -> handleLojaAction());

        panel.add(donoButton);
        panel.add(clienteButton);
        panel.add(funcionarioButton);
        panel.add(comprasButton);
        panel.add(estoqueButton);
        panel.add(pecaButton);
        panel.add(lojaButton);

        frame.setVisible(true);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void handleDonoAction() {
        String[] options = { "Cadastrar Dono", "Visualizar Dados do Dono" };
        int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Dono", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Janela de cadastro do Dono
            String nome = JOptionPane.showInputDialog("Digite o nome do Dono:");
            String cpf = JOptionPane.showInputDialog("Digite o CPF do Dono:");
            String endereco = JOptionPane.showInputDialog("Digite o endereço do Dono:");
            String telefone = JOptionPane.showInputDialog("Digite o telefone do Dono:");
            String email = JOptionPane.showInputDialog("Digite o email do Dono:");

            dono = new Dono(nome, cpf, endereco, telefone, email);
            JOptionPane.showMessageDialog(null, "Dono cadastrado com sucesso.");
        } else if (choice == 1) {
            // Exibir dados do Dono
            if (dono != null) {
                JOptionPane.showMessageDialog(null, dono.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Dono não cadastrado.");
            }
        }
    }

    private void handleFuncionarioAction() {
        String[] options = { "Cadastrar Funcionário", "Visualizar Funcionários", "Demitir Funcionário" };
        int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Funcionário", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Lógica para cadastrar um funcionário
            Funcionario funcionario = Funcionario.cadastrarFuncionario();
            funcionario.inserirNoBanco(); // Insere o funcionário no banco de dados
            funcionarios.add(funcionario);
        } else if (choice == 1) {
            // Lógica para visualizar funcionários
            StringBuilder sb = new StringBuilder();
            for (Funcionario funcionario : funcionarios) {
                sb.append(funcionario.toString());
                sb.append("\n------------------\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } else if (choice == 2) {
            // Lógica para demitir um funcionário
            String cpf = JOptionPane.showInputDialog("Digite o CPF do funcionário a ser demitido:");
            for (int i = 0; i < funcionarios.size(); i++) {
                if (funcionarios.get(i).getCpf().equals(cpf)) {
                    funcionarios.remove(i);
                    JOptionPane.showMessageDialog(null, "Funcionário demitido com sucesso.");
                    break;
                }
            }
        }
    }

    private void handleClienteAction() {
        String[] options = { "Cadastrar Cliente", "Visualizar Clientes", "Excluir Cliente" };
        int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Cliente", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Lógica para cadastrar um cliente
            Cliente cliente = Cliente.cadastrarCliente();
            cliente.inserirNoBanco(); // Insere o cliente no banco de dados
            clientes.add(cliente);
        } else if (choice == 1) {
            // Lógica para visualizar clientes
            StringBuilder sb = new StringBuilder();
            for (Cliente cliente : clientes) {
                sb.append(cliente.toString());
                sb.append("\n------------------\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } else if (choice == 2) {
            // Lógica para excluir um cliente
            String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente a ser excluído:");
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getCpf().equals(cpf)) {
                    clientes.remove(i);
                    JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso.");
                    break;
                }
            }
        }
    }

    private void handleComprasAction() {
        String[] options = { "Comprar peça", "Gerar relatório de vendas" };
        int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Compras", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            JComboBox<Cliente> comboBoxCliente = new JComboBox<>(clientes.toArray(new Cliente[0]));
            int resultCliente = JOptionPane.showConfirmDialog(null, comboBoxCliente,
                    "Selecione um cliente para realizar a compra:",
                    JOptionPane.OK_CANCEL_OPTION);
            if (resultCliente == JOptionPane.OK_OPTION) {
                clienteAtual = (Cliente) comboBoxCliente.getSelectedItem();
            } else {
                // Se o usuário cancelar a seleção do cliente, retorne e não continue com a
                // compra
                return;
            }

            // Adicione a peça ao carrinho
            JComboBox<Peca> comboBoxPeca = new JComboBox<>(estoque.getPecas().toArray(new Peca[0]));
            int resultPeca = JOptionPane.showConfirmDialog(null, comboBoxPeca,
                    "Selecione uma peça para adicionar ao carrinho:",
                    JOptionPane.OK_CANCEL_OPTION);
            if (resultPeca == JOptionPane.OK_OPTION) {
                Peca selectedPeca = (Peca) comboBoxPeca.getSelectedItem();
                String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade desejada:");
                int quantidade = Integer.parseInt(quantidadeStr);
                clienteAtual.adicionarPecaAoCarrinho(selectedPeca, quantidade);
                JOptionPane.showMessageDialog(null, "Peça adicionada ao carrinho.");
            }

            // Finalize a compra
            double total = 0;
            for (Peca peca : clienteAtual.getCarrinho()) {
                total += peca.getPreco();
            }
            JOptionPane.showMessageDialog(null, "Compra finalizada com sucesso. O total da compra foi: " + total);
            Compra compra = new Compra(clienteAtual.getNumeroCliente(), clienteAtual);
            vendas.add(compra);
            // Visualize a compra
            StringBuilder sb = new StringBuilder();
            sb.append("=== Itens da Compra ===\n");
            for (Peca peca : clienteAtual.getCarrinho()) {
                sb.append("Nome da Peça: " + peca.getNome() + "\n");
                sb.append("Preço: " + peca.getPreco() + "\n");
            }
            sb.append("Total da Compra: " + total);
            JOptionPane.showMessageDialog(null, sb.toString());
        } else if (choice == 1) {
            if (clienteAtual.getHistoricoCompras().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O cliente não tem nenhuma compra.");
            } else {
                Compra ultimaCompra = clienteAtual.getHistoricoCompras()
                        .get(clienteAtual.getHistoricoCompras().size() - 1);
                ultimaCompra.gerarRelatorioVendas(clienteAtual);
            }
        }
    }

    private void handleEstoqueAction() {

        try {
            String[] options = { "Adicionar Peça ao Estoque", "Visualizar Estoque" };
            int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Estoque", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                Peca[] pecasArray = estoque.getPecas().toArray(new Peca[0]);
                Peca selectedPeca = (Peca) JOptionPane.showInputDialog(null, "Selecione uma peça:", "Estoque",
                        JOptionPane.QUESTION_MESSAGE, null, pecasArray, pecasArray[0]);

                if (selectedPeca != null) {
                    String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade:");
                    int quantidade = Integer.parseInt(quantidadeStr);
                    // Update the quantity of the selected piece
                    selectedPeca.setQuantidade(selectedPeca.getQuantidade() + quantidade);
                }
            } else if (choice == 1) {
                // Visualizar Estoque
                StringBuilder sb = new StringBuilder();
                sb.append("=== Estoque ===\n");
                for (Peca peca : estoque.getPecas()) {
                    sb.append("Nome da Peça: " + peca.getNome() + "\n");
                    sb.append("Preço: " + peca.getPreco() + "\n");
                    sb.append("Quantidade: " + peca.getQuantidade() + "\n");
                    sb.append("--------------------\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePecaAction() {
        // Opções para o JOptionPane
        try {
            Object[] options = { "Cadastrar Peça", "Visualizar Peças" };

            // Exibe um JOptionPane com as opções
            int choice = JOptionPane.showOptionDialog(null,
                    "Escolha uma opção",
                    "Menu Peça",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            // Se o usuário escolher "Cadastrar Peça"
            if (choice == JOptionPane.YES_OPTION) {
                cadastrarPeca();
            }
            // Se o usuário escolher "Visualizar Peças"
            else if (choice == JOptionPane.NO_OPTION) {
                visualizarPecas();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cadastrarPeca() {
        String nome = JOptionPane.showInputDialog("Digite o nome da peça:");
        String numeroSerie = JOptionPane.showInputDialog("Digite o número de série da peça:");
        String modeloVeiculo = JOptionPane.showInputDialog("Digite o modelo do veículo da peça:");
        String marcaVeiculo = JOptionPane.showInputDialog("Digite a marca do veículo da peça:");
        double tamanho = Double.parseDouble(JOptionPane.showInputDialog("Digite o tamanho da peça:"));
        double preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o preço da peça:"));

        // Set the initial quantity to 0
        int quantidade = 0;

        Peca peca = new Peca(nome, numeroSerie, modeloVeiculo, marcaVeiculo, tamanho, preco, quantidade);

        estoque.adicionarPeca(peca);
    }

    private void visualizarPecas() {
        ArrayList<Peca> pecas = estoque.getPecas();
        JComboBox<Peca> comboBox = new JComboBox<>(pecas.toArray(new Peca[0]));

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Peca selectedPeca = (Peca) comboBox.getSelectedItem();
                JOptionPane.showMessageDialog(null, selectedPeca.getDetalhes());
            }
        });

        JOptionPane.showMessageDialog(null, comboBox, "Selecione uma peça", JOptionPane.QUESTION_MESSAGE);
    }

    private void handleLojaAction() {
        try {
            String[] options = { "Criar Site", "Visualizar dados da Loja" };
            int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Loja", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                String message = loja.criarSiteLoja();
                JOptionPane.showMessageDialog(null, message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start() {
        frame.setVisible(true);
    }
}