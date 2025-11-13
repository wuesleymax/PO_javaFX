package controller;

import dao.ClienteDAO;
import model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClientesController {
    
    @FXML private TableView<Cliente> tableView;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TextField txtNome, txtEmail, txtTelefone, txtEndereco;
    
    private ObservableList<Cliente> clientesList;
    private ClienteDAO clienteDAO;
    
    @FXML
    public void initialize() {
        clienteDAO = new ClienteDAO();
        clientesList = FXCollections.observableArrayList();
        tableView.setItems(clientesList);
        
        // Configurar colunas
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colTelefone.setCellValueFactory(cellData -> cellData.getValue().telefoneProperty());
        
        carregarClientes();
        
        // Listener para seleção na tabela
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarCliente(newValue));
    }
    
    private void carregarClientes() {
        try {
            clientesList.clear();
            clientesList.addAll(clienteDAO.read());
        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar clientes: " + e.getMessage());
        }
    }
    
    private void selecionarCliente(Cliente cliente) {
        if (cliente != null) {
            txtNome.setText(cliente.getNome());
            txtEmail.setText(cliente.getEmail());
            txtTelefone.setText(cliente.getTelefone());
            txtEndereco.setText(cliente.getEndereco());
        }
    }
    
    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                Cliente cliente = new Cliente();
                cliente.setNome(txtNome.getText());
                cliente.setEmail(txtEmail.getText());
                cliente.setTelefone(txtTelefone.getText());
                cliente.setEndereco(txtEndereco.getText());
                
                clienteDAO.create(cliente);
                carregarClientes();
                limparCampos();
                mostrarAlerta("Cliente salvo com sucesso!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Erro ao salvar cliente: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleAtualizar() {
        Cliente clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null && validarCampos()) {
            try {
                clienteSelecionado.setNome(txtNome.getText());
                clienteSelecionado.setEmail(txtEmail.getText());
                clienteSelecionado.setTelefone(txtTelefone.getText());
                clienteSelecionado.setEndereco(txtEndereco.getText());
                
                clienteDAO.update(clienteSelecionado);
                carregarClientes();
                limparCampos();
                mostrarAlerta("Cliente atualizado com sucesso!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Erro ao atualizar cliente: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione um cliente para atualizar!");
        }
    }
    
    @FXML
    private void handleExcluir() {
        Cliente clienteSelecionado = tableView.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                clienteDAO.delete(clienteSelecionado.getId());
                carregarClientes();
                limparCampos();
                mostrarAlerta("Cliente excluído com sucesso!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Erro ao excluir cliente: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Selecione um cliente para excluir!");
        }
    }
    
    @FXML
    private void handleLimpar() {
        limparCampos();
        tableView.getSelectionModel().clearSelection();
    }
    
    private void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtTelefone.clear();
        txtEndereco.clear();
    }
    
    private boolean validarCampos() {
        if (txtNome.getText().isEmpty()) {
            mostrarAlerta("Nome é obrigatório!");
            return false;
        }
        return true;
    }
    
    private void mostrarAlerta(String mensagem) {
        mostrarAlerta(mensagem, Alert.AlertType.ERROR);
    }
    
    private void mostrarAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}