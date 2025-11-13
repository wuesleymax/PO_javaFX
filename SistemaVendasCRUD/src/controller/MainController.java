// Define o pacote onde esta classe está localizada
// Segue a estrutura de diretórios: src/controller/
package controller;

// Importa as classes necessárias do JavaFX e Java
import javafx.fxml.FXML;           // Para anotação que conecta FXML com código
import javafx.fxml.FXMLLoader;     // Para carregar arquivos FXML
import javafx.scene.Parent;        // Classe base para elementos da interface
import javafx.scene.Scene;         // Representa o conteúdo de uma janela
import javafx.scene.control.Alert; // Para caixas de diálogo de alerta
import javafx.stage.Stage;         // Representa uma janela da aplicação
import java.io.IOException;        // Para tratamento de erros de entrada/saída

// Classe controladora principal que gerencia a navegação do sistema
// Implementa o padrão MVC (Model-View-Controller)
public class MainController {
    
    // Método anotado com @FXML - conectado ao botão no arquivo FXML
    // Quando usuário clica em "Gerenciar Clientes" no menu
    @FXML
    private void abrirClientes() {
        // Chama método auxiliar para carregar a tela de clientes
        // Parâmetros: caminho do FXML e título da janela
        carregarTela("/view/ClientesView.fxml", "Gerenciar Clientes");
    }
    
    // Método para abrir tela de produtos
    // Conectado ao botão correspondente no FXML via @FXML
    @FXML
    private void abrirProdutos() {
        // Carrega a interface de produtos
        carregarTela("/view/ProdutosView.fxml", "Gerenciar Produtos");
    }
    
    // Método para abrir tela de categorias
    @FXML
    private void abrirCategorias() {
        // Carrega a interface de categorias
        carregarTela("/view/CategoriasView.fxml", "Gerenciar Categorias");
    }
    
    // Método para abrir tela de vendas
    @FXML
    private void abrirVendas() {
        // Carrega a interface de vendas
        carregarTela("/view/VendasView.fxml", "Gerenciar Vendas");
    }
    
    // Método para abrir tela de gráficos
    @FXML
    private void abrirGrafico() {
        // Carrega a interface de gráficos
        carregarTela("/view/GraficoView.fxml", "Gráfico de Vendas Mensal");
    }
    
    // Método auxiliar privado que centraliza o carregamento de telas
    // Recebe o caminho do FXML e o título como parâmetros
    private void carregarTela(String fxml, String titulo) {
        // Bloco try-catch para tratamento de erros no carregamento
        try {
            // Carrega o arquivo FXML e cria a estrutura de componentes
            // getClass().getResource() busca o arquivo no classpath
            // FXMLLoader.load() transforma o XML em objetos Java
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            
            // Cria uma nova janela (Stage) para exibir a tela
            Stage stage = new Stage();
            
            // Define o título da janela (aparece na barra de título)
            stage.setTitle(titulo);
            
            // Cria uma cena (Scene) contendo os componentes carregados
            // e associa esta cena à janela
            stage.setScene(new Scene(root));
            
            // Torna a janela visível para o usuário
            stage.show();
            
        } catch (IOException e) {
            // Se ocorrer erro no carregamento (arquivo não encontrado, etc.)
            // Exibe mensagem de erro amigável para o usuário
            mostrarAlerta("Erro ao carregar a tela: " + e.getMessage());
        }
    }
    
    // Método auxiliar para exibir mensagens de erro
    // Recebe a mensagem como parâmetro
    private void mostrarAlerta(String mensagem) {
        // Cria uma caixa de diálogo do tipo ERRO (ícone de erro)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        // Define o título da caixa de diálogo
        alert.setTitle("Erro");
        
        // Define o texto principal da mensagem
        alert.setContentText(mensagem);
        
        // Exibe o alerta e AGUARDA o usuário fechá-lo
        // (showAndWait() bloqueia até o usuário interagir)
        alert.showAndWait();
    }
}