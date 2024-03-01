package org.example.bzreboques;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class TelaConfiguracoesController implements Initializable {
    private Application app;

    @FXML
    private HBox btnFecharPushMsgTelaConfiguracoes;

    @FXML
    private Button btnSalvarTelaConfiguracoes;

    @FXML
    private HBox hbCadastrarNavTelaConfiguracoes;

    @FXML
    private HBox hbConfigNavTelaConfiguracoes;

    @FXML
    private HBox hbNuvemNavTelaConfiguracoes;

    @FXML
    private HBox hbPesquisarNavTelaConfiguracoes;

    @FXML
    private HBox hbPushMsgTelaConfiguracoes;

    @FXML
    private Label lbConexaoNavTelaConfiguracoes;

    @FXML
    private Label lbDataHoraTelaConfiguracoes;

    @FXML
    private Label lbPushMsgTelaConfiguracoes;

    @FXML
    private TextField tfCnpjTelaConfiguracoes;

    @FXML
    private TextField tfEnderecoTelaConfiguracoes;

    @FXML
    private TextField tfInscricaoMunicipalTelaConfiguracoes;

    @FXML
    private TextField tfRazaoSocialTelaConfiguracoes;

    @FXML
    private TextField tfResponsavelDetranTelaConfiguracoes;

    @FXML
    private TextField tfSenhaTelaConfiguracoes;

    @FXML
    private TextField tfTelefoneTelaConfiguracoes;

    @FXML
    private TextField tfUrlTelaConfiguracoes;

    @FXML
    private TextField tfUsuarioTelaConfiguracoes;

    public TelaConfiguracoesController(){
        this.app = new Application();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgTelaConfiguracoes.setVisible(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> atualizarRelogio()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        tfRazaoSocialTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getRazaoSocial());
        tfCnpjTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getCnpj());
        tfInscricaoMunicipalTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getInscricaoMunicipal());
        tfEnderecoTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getEndereco());
        tfTelefoneTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getTelefone());
        tfResponsavelDetranTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getResponsavelDetran());
        tfUrlTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getUrlDB());
        tfUsuarioTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getUserDB());
        tfSenhaTelaConfiguracoes.setPromptText("**********");
    }

    @FXML public void fecharPushMsg(MouseEvent event){
        hbPushMsgTelaConfiguracoes.setVisible(false);
    }

    @FXML
    public void salvarConfiguracoes(){
        String razaoSocial = tfRazaoSocialTelaConfiguracoes.getText();
        String cnpj = tfCnpjTelaConfiguracoes.getText();
        String inscricaoMunicipal = tfInscricaoMunicipalTelaConfiguracoes.getText();
        String endereco = tfEnderecoTelaConfiguracoes.getText();
        String telefone = tfTelefoneTelaConfiguracoes.getText();
        String responsavelDetran = tfResponsavelDetranTelaConfiguracoes.getText();
        String url = tfUrlTelaConfiguracoes.getText();
        String usuario = tfUsuarioTelaConfiguracoes.getText();
        String senha = tfSenhaTelaConfiguracoes.getText();

        if(razaoSocial.isEmpty() && cnpj.isEmpty() && inscricaoMunicipal.isEmpty() && endereco.isEmpty() && telefone.isEmpty() &&
                responsavelDetran.isEmpty() && url.isEmpty() && usuario.isEmpty() && senha.isEmpty()){
            lbPushMsgTelaConfiguracoes.setText("Não houve alterações!");
            hbPushMsgTelaConfiguracoes.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaConfiguracoes.setVisible(true);
        }else{
            if(razaoSocial.isEmpty() || razaoSocial.equals(app.getServidor().getConfiguracoes().getRazaoSocial())){
                razaoSocial = app.getServidor().getConfiguracoes().getRazaoSocial();
            }
            if(cnpj.isEmpty() || cnpj.equals(app.getServidor().getConfiguracoes().getCnpj())){
                cnpj = app.getServidor().getConfiguracoes().getCnpj();
            }
            if(inscricaoMunicipal.isEmpty() || inscricaoMunicipal.equals(app.getServidor().getConfiguracoes().getInscricaoMunicipal())){
                inscricaoMunicipal = app.getServidor().getConfiguracoes().getInscricaoMunicipal();
            }
            if(endereco.isEmpty() || endereco.equals(app.getServidor().getConfiguracoes().getEndereco())){
                endereco = app.getServidor().getConfiguracoes().getEndereco();
            }
            if(telefone.isEmpty() || telefone.equals(app.getServidor().getConfiguracoes().getTelefone())){
                telefone = app.getServidor().getConfiguracoes().getTelefone();
            }
            if(responsavelDetran.isEmpty() || responsavelDetran.equals(app.getServidor().getConfiguracoes().getResponsavelDetran())){
                responsavelDetran = app.getServidor().getConfiguracoes().getResponsavelDetran();
            }
            if(url.isEmpty() || url.equals(app.getServidor().getConfiguracoes().getUrlDB())){
                url = app.getServidor().getConfiguracoes().getUrlDB();
            }
            if(usuario.isEmpty() || usuario.equals(app.getServidor().getConfiguracoes().getUserDB())){
                usuario = app.getServidor().getConfiguracoes().getUserDB();
            }
            if(senha.isEmpty() || senha.equals(app.getServidor().getConfiguracoes().getPasswordDB())){
                senha = app.getServidor().getConfiguracoes().getPasswordDB();
            }

            Settings novasConfiguracoes = new Settings(razaoSocial, cnpj, inscricaoMunicipal, endereco, telefone, responsavelDetran, url, usuario, senha);

            try {
                ManipulacaoDasConfiguracoes.salvarConfiguracoes(novasConfiguracoes);
            } catch (ConfiguracoesSalvasComSucessoException e) {
                lbPushMsgTelaConfiguracoes.setText(e.getMessage());
                hbPushMsgTelaConfiguracoes.getStyleClass().setAll("push-msg-success");
                hbPushMsgTelaConfiguracoes.setVisible(true);

                app.getServidor().setConfiguracoes(novasConfiguracoes);

                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ev) {
                        ev.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        this.resetarTela();
                    });
                }).start();

            } catch (FalhaDeSalvamentoDasConfiguracoesException e) {
                lbPushMsgTelaConfiguracoes.setText(e.getMessage());
                hbPushMsgTelaConfiguracoes.getStyleClass().setAll("push-msg-error");
                hbPushMsgTelaConfiguracoes.setVisible(true);
            }
        }
    }

    @FXML
    public void trocarParaTelaCadastrar(){
        this.resetarTela();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getTelaCadastrarController().resetarTela();
        sm.mudarScene("tela-cadastrar.fxml","Bezerra Reboques - Cadastrar");
    }

    @FXML
    public void trocarParaTelaPesquisar(){
        this.resetarTela();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getTelaPesquisarController().inicializarTabela();
        sm.mudarScene("tela-pesquisar.fxml","Bezerra Reboques - Pesquisar");
    }

    @FXML
    public void trocarParaTelaNuvem(){
        this.resetarTela();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getTelaNuvemController().resetarTela();
        sm.mudarScene("tela-nuvem.fxml","Bezerra Reboques - Nuvem");
    }

    private void atualizarRelogio() {
        LocalDateTime dateTimeAtual = LocalDateTime.now();
        Locale locale = new Locale("pt", "BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", locale);
        String dataHoraFormatada = dateTimeAtual.format(formatter);
        lbDataHoraTelaConfiguracoes.setText(dataHoraFormatada);
    }

    public void resetarTela(){
        try (Connection conn = ConnectionFactory.getConnection()){
            lbConexaoNavTelaConfiguracoes.setText("Conectado");
        } catch (SQLException | FalhaDeConexaoDbException e ) {
            lbConexaoNavTelaConfiguracoes.setText("Desconectado");
        }
        hbPushMsgTelaConfiguracoes.setVisible(false);
        tfRazaoSocialTelaConfiguracoes.setText("");
        tfCnpjTelaConfiguracoes.setText("");
        tfInscricaoMunicipalTelaConfiguracoes.setText("");
        tfEnderecoTelaConfiguracoes.setText("");
        tfTelefoneTelaConfiguracoes.setText("");
        tfResponsavelDetranTelaConfiguracoes.setText("");
        tfUrlTelaConfiguracoes.setText("");
        tfUsuarioTelaConfiguracoes.setText("");
        tfSenhaTelaConfiguracoes.setText("");
        tfRazaoSocialTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getRazaoSocial());
        tfCnpjTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getCnpj());
        tfInscricaoMunicipalTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getInscricaoMunicipal());
        tfEnderecoTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getEndereco());
        tfTelefoneTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getTelefone());
        tfResponsavelDetranTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getResponsavelDetran());
        tfUrlTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getUrlDB());
        tfUsuarioTelaConfiguracoes.setPromptText(app.getServidor().getConfiguracoes().getUserDB());
        tfSenhaTelaConfiguracoes.setPromptText("**********");
    }
}
