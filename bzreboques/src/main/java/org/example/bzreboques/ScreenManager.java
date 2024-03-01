package org.example.bzreboques;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private static ScreenManager instance;
    private static Stage stg;

    private Scene telaCadastrarScene;
    private Scene telaPesquisarScene;
    private Scene telaConfiguracoesScene;
    private Scene telaNuvemScene;
    private Scene telaEditarScene;
    private Scene telaDetalharScene;
    private Scene telaDelecaoScene;
    private Scene telaConfirmacaoCadastroScene;

    private TelaCadastrarController telaCadastrarController;
    private TelaPesquisarController telaPesquisarController;
    private TelaConfiguracoesController telaConfiguracoesController;
    private TelaNuvemController telaNuvemController;
    private TelaEditarController telaEditarController;
    private TelaDetalharController telaDetalharController;
    private TelaDelecaoController telaDelecaoController;
    private TelaConfirmacaoCadastroController telaConfirmacaoCadastroController;

    public ScreenManager(){
        this.carregarTelas();
    }

    public static ScreenManager getInstance(){
        if(instance == null){
            instance = new ScreenManager();
        }
        return instance;
    }

    // GET/SET Stage
    public static Stage getStg() {
        return stg;
    }
    public static void setStg(Stage stg) {
        ScreenManager.stg = stg;
    }

    // GETTERS das Scenes
    public Scene getTelaCadastrarScene() {
        return telaCadastrarScene;
    }
    public Scene getTelaConfiguracoesScene() {
        return telaConfiguracoesScene;
    }
    public Scene getTelaNuvemScene() {
        return telaNuvemScene;
    }
    public Scene getTelaPesquisarScene() {
        return telaPesquisarScene;
    };
    public Scene getTelaEditarScene() {
        return telaEditarScene;
    }
    public Scene getTelaDetalharScene() {
        return telaDetalharScene;
    }
    public Scene getTelaDelecaoScene() {
        return telaDelecaoScene;
    }
    public Scene getTelaConfirmacaoCadastroScene() {
        return telaConfirmacaoCadastroScene;
    }

    // GETTERS dos Controllers
    public TelaCadastrarController getTelaCadastrarController() {
        return telaCadastrarController;
    }
    public TelaConfiguracoesController getTelaConfiguracoesController() {
        return telaConfiguracoesController;
    }
    public TelaNuvemController getTelaNuvemController() {
        return telaNuvemController;
    }
    public TelaPesquisarController getTelaPesquisarController() {
        return telaPesquisarController;
    }
    public TelaEditarController getTelaEditarController() {
        return telaEditarController;
    }
    public TelaDetalharController getTelaDetalharController() {
        return telaDetalharController;
    }
    public TelaDelecaoController getTelaDelecaoController() {
        return telaDelecaoController;
    }
    public TelaConfirmacaoCadastroController getTelaConfirmacaoCadastroController() {
        return telaConfirmacaoCadastroController;
    }

    private void carregarTelas(){
        try {
            FXMLLoader telaCadastrarPane = new FXMLLoader(getClass().getResource("tela-cadastrar.fxml"));
            this.telaCadastrarScene = new Scene(telaCadastrarPane.load());
            this.telaCadastrarController = telaCadastrarPane.getController();

            FXMLLoader telaPesquisarPane = new FXMLLoader(getClass().getResource("tela-pesquisar.fxml"));
            this.telaPesquisarScene = new Scene(telaPesquisarPane.load());
            this.telaPesquisarController = telaPesquisarPane.getController();

            FXMLLoader telaConfiguracoesPane = new FXMLLoader(getClass().getResource("tela-configuracoes.fxml"));
            this.telaConfiguracoesScene = new Scene(telaConfiguracoesPane.load());
            this.telaConfiguracoesController = telaConfiguracoesPane.getController();

            FXMLLoader telaNuvemPane = new FXMLLoader(getClass().getResource("tela-nuvem.fxml"));
            this.telaNuvemScene = new Scene(telaNuvemPane.load());
            this.telaNuvemController = telaNuvemPane.getController();

            FXMLLoader telaEditarPane = new FXMLLoader(getClass().getResource("tela-editar.fxml"));
            this.telaEditarScene = new Scene(telaEditarPane.load());
            this.telaEditarController = telaEditarPane.getController();

            FXMLLoader telaDetalharPane = new FXMLLoader(getClass().getResource("tela-detalhar.fxml"));
            this.telaDetalharScene = new Scene(telaDetalharPane.load());
            this.telaDetalharController = telaDetalharPane.getController();

            FXMLLoader telaDelecaoPane = new FXMLLoader(getClass().getResource("tela-delecao.fxml"));
            this.telaDelecaoScene = new Scene(telaDelecaoPane.load());
            this.telaDelecaoController = telaDelecaoPane.getController();

            FXMLLoader telaConfirmacaoCadastroPane = new FXMLLoader(getClass().getResource("tela-confirmacao-cadastro.fxml"));
            this.telaConfirmacaoCadastroScene = new Scene(telaConfirmacaoCadastroPane.load());
            this.telaConfirmacaoCadastroController = telaConfirmacaoCadastroPane.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mudarScene(String fxml, String titulo){
        boolean max = stg.isMaximized();
        if(max) stg.setMaximized(false);
        switch (fxml){
            case "tela-cadastrar.fxml" -> stg.setScene(telaCadastrarScene);
            case "tela-pesquisar.fxml" -> stg.setScene(telaPesquisarScene);
            case "tela-configuracoes.fxml" -> stg.setScene(telaConfiguracoesScene);
            case "tela-nuvem.fxml" -> stg.setScene(telaNuvemScene);
            case "tela-editar.fxml" -> stg.setScene(telaEditarScene);
            case "tela-detalhar.fxml" -> stg.setScene(telaDetalharScene);
        }
        stg.setTitle(titulo);
        if(max) stg.setMaximized(true);
    }

}
