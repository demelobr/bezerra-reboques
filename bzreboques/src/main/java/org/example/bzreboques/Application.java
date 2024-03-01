package org.example.bzreboques;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Servidor servidor;

    public Application() {
        this.servidor = Servidor.getInstance();
    }
    public Servidor getServidor(){
        return servidor;
    }

    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager.setStg(stage);
        Parent root = FXMLLoader.load(getClass().getResource("tela-pesquisar.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Bezerra Reboques - Pesquisar");
        stage.getIcons().add(new Image("file:" + "src/main/resources/org/example/bzreboques/icon-16x16.png"));
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        Application app = new Application();
        try {
            app.getServidor().setConfiguracoes(app.getServidor().carregarConfiguracoes());
            app.getServidor().setNuvem(app.getServidor().carregarConfiguracoesCloud());
        } catch (FalhaDeCarregamentoDasConfiguracoesException | FalhaDeSalvamentoDasConfiguracoesDeCloudException e) {
            throw new RuntimeException(e);
        }
        launch();
    }
}