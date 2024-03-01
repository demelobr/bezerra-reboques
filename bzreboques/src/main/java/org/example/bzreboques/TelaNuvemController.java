package org.example.bzreboques;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class TelaNuvemController implements Initializable {
    private Application app;
    private String[] opcoes = {"Diário","Semanal","Mensal"};

    @FXML
    private HBox btnBackupTelaNuvem;

    @FXML
    private HBox btnFecharPushMsgTelaNuvem;

    @FXML
    private HBox btnPastaTelaNuvem;

    @FXML
    private Button btnSalvarTelaNuvem;

    @FXML
    private ChoiceBox<String> cbPeriodicidadeTelaNuvem;

    @FXML
    private HBox hbCadastrarNavTelaNuvem;

    @FXML
    private HBox hbConfigNavTelaNuvem;

    @FXML
    private HBox hbNuvemNavTelaNuvem;

    @FXML
    private HBox hbPesquisarNavTelaNuvem;

    @FXML
    private HBox hbPushMsgTelaNuvem;

    @FXML
    private Label lbCaminhoDaPastaTelaNuvem;

    @FXML
    private Label lbConexaoNavTelaNuvem;

    @FXML
    private Label lbDataHoraTelaNuvem;

    @FXML
    private Label lbPushMsgTelaNuvem;

    @FXML
    private Label lbUltimoUploadTelaNuvem;

    public TelaNuvemController(){
        this.app = new Application();
    }

    @FXML
    public void fecharPushMsg(MouseEvent event){
        hbPushMsgTelaNuvem.setVisible(false);
    }

    @FXML
    public void selecionarCaminhoDaPasta(MouseEvent event){
        Stage stg = ScreenManager.getStg();
        DirectoryChooser dc = new DirectoryChooser();
        File pastaSelecionada = dc.showDialog(stg);
        if(pastaSelecionada != null){
            lbCaminhoDaPastaTelaNuvem.setText(pastaSelecionada.getAbsolutePath());
        }
    }

    @FXML
    public void salvarConfiguracoesDeNuvem(ActionEvent event){
        String caminhoDaPasta = lbCaminhoDaPastaTelaNuvem.getText();
        String periodicidade = cbPeriodicidadeTelaNuvem.getValue();

        if(!caminhoDaPasta.equals(app.getServidor().getNuvem().getCaminhoDaPasta()) || !periodicidade.equals(app.getServidor().getNuvem().getPeriodicidade())){
            if(caminhoDaPasta.isEmpty() || caminhoDaPasta.equals(app.getServidor().getNuvem().getCaminhoDaPasta())){
                caminhoDaPasta = app.getServidor().getNuvem().getCaminhoDaPasta();
            }
            if(periodicidade.isEmpty() || periodicidade.equals(app.getServidor().getNuvem().getPeriodicidade())){
                periodicidade = app.getServidor().getNuvem().getPeriodicidade();
            }

            Nuvem nuvem = new Nuvem(caminhoDaPasta, periodicidade, app.getServidor().getNuvem().getUltimoUpload());
//            Nuvem nuvem = new Nuvem(caminhoDaPasta, periodicidade, LocalDateTime.now());

            try {
                ManipulacaoCloud.salvarConfiguracoesCloud(nuvem);
            } catch (ConfiguracoesCloudSalvasComSucessoException e) {
                lbPushMsgTelaNuvem.setText(e.getMessage());
                hbPushMsgTelaNuvem.getStyleClass().setAll("push-msg-success");
                hbPushMsgTelaNuvem.setVisible(true);

                app.getServidor().setNuvem(nuvem);

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

            } catch (FalhaDeSalvamentoDasConfiguracoesDeCloudException e) {
                lbPushMsgTelaNuvem.setText(e.getMessage());
                hbPushMsgTelaNuvem.getStyleClass().setAll("push-msg-error");
                hbPushMsgTelaNuvem.setVisible(true);
            }
        }else{
            lbPushMsgTelaNuvem.setText("Não houve alterações!");
            hbPushMsgTelaNuvem.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaNuvem.setVisible(true);
        }
    }

    @FXML
    public void salvarNaNuvem(MouseEvent event){
        if(BackupBancoDeDados.criarBackupDoBancoDeDados() == 0){
            lbPushMsgTelaNuvem.setText("Salvamento manual feito com sucesso!");
            hbPushMsgTelaNuvem.getStyleClass().setAll("push-msg-success");
            hbPushMsgTelaNuvem.setVisible(true);
            LocalDateTime dateTimeAtual = LocalDateTime.now();
            app.getServidor().getNuvem().setUltimoUpload(dateTimeAtual);
            try {
                ManipulacaoCloud.salvarConfiguracoesCloud(app.getServidor().getNuvem());
            } catch (ConfiguracoesCloudSalvasComSucessoException ignored) {

            } catch (FalhaDeSalvamentoDasConfiguracoesDeCloudException e) {
                lbPushMsgTelaNuvem.setText(e.getMessage());
                hbPushMsgTelaNuvem.getStyleClass().setAll("push-msg-error");
                hbPushMsgTelaNuvem.setVisible(true);
            }
            Locale locale = new Locale("pt", "BR");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy H:m", locale);
            lbUltimoUploadTelaNuvem.setText("Último backup manual: " + dateTimeAtual.format(formatter));
        }else{
            lbPushMsgTelaNuvem.setText("Falha ao salvar manualmente!");
            hbPushMsgTelaNuvem.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaNuvem.setVisible(true);
        }
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ev) {
                ev.printStackTrace();
            }
            Platform.runLater(() -> {
                hbPushMsgTelaNuvem.setVisible(false);
            });
        }).start();
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
    public void trocarParaTelaConfiguracoes(){
        this.resetarTela();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getTelaConfiguracoesController().resetarTela();
        sm.mudarScene("tela-configuracoes.fxml","Bezerra Reboques - Configurações");
    }

    private void atualizarRelogio() {
        LocalDateTime dateTimeAtual = LocalDateTime.now();
        Locale locale = new Locale("pt", "BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", locale);
        String dataHoraFormatada = dateTimeAtual.format(formatter);
        lbDataHoraTelaNuvem.setText(dataHoraFormatada);
    }

    public void resetarTela(){
        try (Connection conn = ConnectionFactory.getConnection()){
            lbConexaoNavTelaNuvem.setText("Conectado");
        } catch (SQLException | FalhaDeConexaoDbException e ) {
            lbConexaoNavTelaNuvem.setText("Desconectado");
        }
        hbPushMsgTelaNuvem.setVisible(false);
        lbCaminhoDaPastaTelaNuvem.setText(app.getServidor().getNuvem().getCaminhoDaPasta());
        cbPeriodicidadeTelaNuvem.setValue(app.getServidor().getNuvem().getPeriodicidade());
        Locale locale = new Locale("pt", "BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy H:m", locale);
        lbUltimoUploadTelaNuvem.setText("Último backup manual: " + app.getServidor().getNuvem().getUltimoUpload().format(formatter));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgTelaNuvem.setVisible(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> atualizarRelogio()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        lbCaminhoDaPastaTelaNuvem.setText(app.getServidor().getNuvem().getCaminhoDaPasta());
        cbPeriodicidadeTelaNuvem.setValue(app.getServidor().getNuvem().getPeriodicidade());
        cbPeriodicidadeTelaNuvem.getItems().addAll(opcoes);
        Locale locale = new Locale("pt", "BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy H:m", locale);
        lbUltimoUploadTelaNuvem.setText("Último backup manual: " + app.getServidor().getNuvem().getUltimoUpload().format(formatter));
    }

}
