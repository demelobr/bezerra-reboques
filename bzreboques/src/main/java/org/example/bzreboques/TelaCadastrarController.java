package org.example.bzreboques;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class TelaCadastrarController implements Initializable {
    private Application app;
    private List<Documento> listaDeDocumentos;
    private boolean match;

    @FXML
    private Button btnCadastrarTelaCadastrar;

    @FXML
    private Button btnEscolherFotosTelaCadastrar;

    @FXML
    private HBox btnFecharPushMsgTelaCadastrar;

    @FXML
    private HBox hbCadastrarNavTelaCadastrar;

    @FXML
    private HBox hbConfigNavTelaCadastrar;

    @FXML
    private HBox hbNuvemNavTelaCadastrar;

    @FXML
    private HBox hbPesquisarNavTelaCadastrar;

    @FXML
    private HBox hbPushMsgTelaCadastrar;

    @FXML
    private Label lbConexaoNavTelaCadastrar;

    @FXML
    private Label lbDataHoraTelaCadastrar;

    @FXML
    private Label lbFoto1TelaCadastrar;

    @FXML
    private Label lbFoto2TelaCadastrar;

    @FXML
    private Label lbPushMsgTelaCadastrar;

    @FXML
    private RadioButton rbChassiTelaCadastrar;

    @FXML
    private RadioButton rbMotorTelaCadastrar;

    @FXML
    private TextField tfAnoFabricacaoTelaCadastrar;

    @FXML
    private TextField tfAnoModeloTelaCadastrar;

    @FXML
    private TextField tfCorTelaCadastrar;

    @FXML
    private TextField tfDovTelaCadastrar;

    @FXML
    private TextField tfMarcaModeloTelaCadastrar;

    @FXML
    private TextField tfNomeClienteTelaCadastrar;

    @FXML
    private TextField tfNumeracaoTelaCadastrar;

    @FXML
    private TextField tfPlacaTelaCadastrar;

    @FXML
    private TextField tfProcessoTelaCadastrar;

    @FXML
    private TextField tfRenavamTelaCadastrar;

    public TelaCadastrarController(){
        this.app = new Application();
        this.match = false;
    }

    @FXML
    public void fecharPushMsg(MouseEvent event){
        hbPushMsgTelaCadastrar.setVisible(false);
    }

    @FXML
    public void cadastrarDocumento(){
        AtomicBoolean inserir = new AtomicBoolean(false);
        Documento novoDocumento = null;

        String nomeCliente = tfNomeClienteTelaCadastrar.getText();
        String dov = tfDovTelaCadastrar.getText();
        String numeracao = tfNumeracaoTelaCadastrar.getText();
        String processo = tfProcessoTelaCadastrar.getText();
        String placa = tfPlacaTelaCadastrar.getText();
        String renavam = tfRenavamTelaCadastrar.getText();
        String cor = tfCorTelaCadastrar.getText();
        String marcaModelo = tfMarcaModeloTelaCadastrar.getText();
        String anoFabricacao = tfAnoFabricacaoTelaCadastrar.getText();
        String anoModelo = tfAnoModeloTelaCadastrar.getText();
        String foto1 = lbFoto1TelaCadastrar.getText();
        String foto2 = lbFoto2TelaCadastrar.getText();
        String equipamento = "";
        if(rbMotorTelaCadastrar.isSelected()) equipamento = "MOTOR";
        else if(rbChassiTelaCadastrar.isSelected()) equipamento = "CHASSI";

        if(!nomeCliente.isEmpty() && !dov.isEmpty() && !numeracao.isEmpty() && !processo.isEmpty() && !placa.isEmpty() &&
                !renavam.isEmpty() && !cor.isEmpty() && !marcaModelo.isEmpty() && !anoFabricacao.isEmpty() && !anoModelo.isEmpty() &&
                !foto1.isEmpty() && !foto2.isEmpty()){
            List<String> fotos = new ArrayList<>();
            fotos.add(foto1);
            fotos.add(foto2);
            novoDocumento = new Documento(nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, Year.of(Integer.parseInt(anoFabricacao)), Year.of(Integer.parseInt(anoModelo)), fotos, LocalDateTime.now());
            try {
                if(app.getServidor().existeDocumento(novoDocumento.getEquipamento(), novoDocumento.getPlaca())){
                    ScreenManager sm = ScreenManager.getInstance();
                    Scene scene = sm.getTelaConfirmacaoCadastroScene();
                    Button simBtn = sm.getTelaConfirmacaoCadastroController().getBtnSimTelaConfirmacaoCadastro();
                    Button naoBtn = sm.getTelaConfirmacaoCadastroController().getBtnNaoTelaConfirmacaoCadastro();

                    Stage dialogStage = new Stage();
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.setResizable(false);
                    dialogStage.setTitle("Registro de documento");
                    dialogStage.getIcons().add(new Image("file:" + "src/main/resources/com/example/bzreboques/icon-16x16.png"));
                    dialogStage.setScene(scene);

                    simBtn.setOnAction(e -> {
                        inserir.set(true);
                        dialogStage.close();
                    });

                    naoBtn.setOnAction(e -> {
                        dialogStage.close();
                    });

                    dialogStage.showAndWait();
                }else{
                    inserir.set(true);
                }

                if(inserir.get()){
                    try {
                        app.getServidor().inserirDocumento(novoDocumento);
                    } catch (FalhaDeConexaoDbException | DocumentoNuloException | DocumentoInvalidoException e) {
                        lbPushMsgTelaCadastrar.setText(e.getMessage());
                        hbPushMsgTelaCadastrar.getStyleClass().setAll("push-msg-error");
                        hbPushMsgTelaCadastrar.setVisible(true);
                    } catch (DocumentoInseridoComSucessoException e) {
                        lbPushMsgTelaCadastrar.setText(e.getMessage());
                        hbPushMsgTelaCadastrar.getStyleClass().setAll("push-msg-success");
                        hbPushMsgTelaCadastrar.setVisible(true);

                        new Thread(() -> {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ev) {
                                ev.printStackTrace();
                            }
                            Platform.runLater(() -> {
                                this.resetarTela();
                                ScreenManager sm = ScreenManager.getInstance();
                                sm.getTelaPesquisarController().inicializarTabela();
                                sm.mudarScene("tela-pesquisar.fxml", "Bezerra Reboques - Pesquisar");
                            });
                        }).start();
                    }
                }

            } catch (FalhaDeConexaoDbException e) {
                throw new RuntimeException(e);
            }
        }else{
            lbPushMsgTelaCadastrar.setText("Preencha todos os campos!");
            hbPushMsgTelaCadastrar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaCadastrar.setVisible(true);
        }
    }

    @FXML
    public void escolherImagens(ActionEvent event){
        Stage stg = ScreenManager.getStg();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        List<File> fotos = fc.showOpenMultipleDialog(stg);
        if(fotos != null && fotos.size() == 2){
            lbFoto1TelaCadastrar.setText(fotos.get(0).getAbsolutePath());
            lbFoto2TelaCadastrar.setText(fotos.get(1).getAbsolutePath());
        }else{
            lbPushMsgTelaCadastrar.setText("Necessário escolher 2 fotos!");
            hbPushMsgTelaCadastrar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaCadastrar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaCadastrar.setVisible(false);
                });
            }).start();
        }
    }

    @FXML
    public void selecionarMotor(ActionEvent event){
        rbMotorTelaCadastrar.setSelected(true);
        rbChassiTelaCadastrar.setSelected(false);
    }

    @FXML
    public void selecionarChassi(ActionEvent event){
        rbMotorTelaCadastrar.setSelected(false);
        rbChassiTelaCadastrar.setSelected(true);
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
        lbDataHoraTelaCadastrar.setText(dataHoraFormatada);
    }

    public List<Documento> buscaDeDocumentosPorPlaca(){
        List<Documento> listaDeDocumentosDaPesquisa = new ArrayList<>();
        for(Documento documento : listaDeDocumentos){
            if(documento.getPlaca().equalsIgnoreCase(tfPlacaTelaCadastrar.getText())){
                listaDeDocumentosDaPesquisa.add(documento);
            }
        }
        return listaDeDocumentosDaPesquisa;
    }

    public void resetarTela(){
        try (Connection conn = ConnectionFactory.getConnection()){
            lbConexaoNavTelaCadastrar.setText("Conectado");
            this.listaDeDocumentos = app.getServidor().listarTodosDocumentos();
        } catch (SQLException | FalhaDeConexaoDbException e ) {
            lbConexaoNavTelaCadastrar.setText("Desconectado");
        }
        hbPushMsgTelaCadastrar.setVisible(false);
        tfNomeClienteTelaCadastrar.setText("");
        tfDovTelaCadastrar.setText("");
        rbMotorTelaCadastrar.setSelected(true);
        rbChassiTelaCadastrar.setSelected(false);
        tfNumeracaoTelaCadastrar.setText("");
        tfProcessoTelaCadastrar.setText("");
        tfPlacaTelaCadastrar.setText("");
        tfRenavamTelaCadastrar.setText("");
        tfCorTelaCadastrar.setText("");
        tfMarcaModeloTelaCadastrar.setText("");
        tfAnoFabricacaoTelaCadastrar.setText("");
        tfAnoModeloTelaCadastrar.setText("");
        lbFoto1TelaCadastrar.setText("");
        lbFoto2TelaCadastrar.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgTelaCadastrar.setVisible(false);
        rbMotorTelaCadastrar.setSelected(true);
        rbChassiTelaCadastrar.setSelected(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> atualizarRelogio()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        try (Connection conn = ConnectionFactory.getConnection()){
            lbConexaoNavTelaCadastrar.setText("Conectado");
            try {
                this.listaDeDocumentos = app.getServidor().listarTodosDocumentos();
                tfPlacaTelaCadastrar.setOnKeyReleased((KeyEvent e) -> {
                    List<Documento> listaDeDocumentosDaPesquisa = this.buscaDeDocumentosPorPlaca();
                    if(!listaDeDocumentosDaPesquisa.isEmpty()){
                        match = true;
                        tfNomeClienteTelaCadastrar.setText(listaDeDocumentosDaPesquisa.get(0).getNomeCliente());
                        tfDovTelaCadastrar.setText(listaDeDocumentosDaPesquisa.get(0).getDov());
                        tfProcessoTelaCadastrar.setText(listaDeDocumentosDaPesquisa.get(0).getProcesso());
                        tfRenavamTelaCadastrar.setText(listaDeDocumentosDaPesquisa.get(0).getRenavam());
                        tfCorTelaCadastrar.setText(listaDeDocumentosDaPesquisa.get(0).getCor());
                        tfMarcaModeloTelaCadastrar.setText(listaDeDocumentosDaPesquisa.get(0).getMarcaModelo());
                        tfAnoFabricacaoTelaCadastrar.setText(listaDeDocumentosDaPesquisa.get(0).getAnoFabricacao().toString());
                        tfAnoModeloTelaCadastrar.setText(listaDeDocumentosDaPesquisa.get(0).getAnoFabricacao().toString());

                        rbChassiTelaCadastrar.setSelected(false);
                        rbMotorTelaCadastrar.setSelected(true);
                        lbPushMsgTelaCadastrar.setText(String.format("Existe(m) %s documento(s) cadastrado(s) com essa placa!", listaDeDocumentosDaPesquisa.size()));
                        hbPushMsgTelaCadastrar.getStyleClass().setAll("push-msg-success");
                        hbPushMsgTelaCadastrar.setVisible(true);

                    }else if(match){
                        match = false;
                        tfNomeClienteTelaCadastrar.setText("");
                        tfDovTelaCadastrar.setText("");
                        tfNumeracaoTelaCadastrar.setText("");
                        tfProcessoTelaCadastrar.setText("");
                        tfRenavamTelaCadastrar.setText("");
                        tfCorTelaCadastrar.setText("");
                        tfMarcaModeloTelaCadastrar.setText("");
                        tfAnoFabricacaoTelaCadastrar.setText("");
                        tfAnoModeloTelaCadastrar.setText("");
                        lbFoto1TelaCadastrar.setText("");
                        lbFoto2TelaCadastrar.setText("");
                        rbChassiTelaCadastrar.setSelected(false);
                        rbMotorTelaCadastrar.setSelected(true);
                        hbPushMsgTelaCadastrar.setVisible(false);
                    }
                });

                StringConverter<Integer> converter = new IntegerStringConverter();
                TextFormatter<Integer> textFormatterAnoFabricacao = new TextFormatter<>(converter, null, change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("\\d*")) { // Permite apenas dígitos
                        return change;
                    }
                    return null; // Rejeita a alteração
                });
                TextFormatter<Integer> textFormatterAnoModelo = new TextFormatter<>(converter, null, change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("\\d*")) { // Permite apenas dígitos
                        return change;
                    }
                    return null; // Rejeita a alteração
                });
                tfAnoFabricacaoTelaCadastrar.setTextFormatter(textFormatterAnoFabricacao);
                tfAnoModeloTelaCadastrar.setTextFormatter(textFormatterAnoModelo);
            } catch (FalhaDeConexaoDbException e) {
                lbPushMsgTelaCadastrar.setText(e.getMessage());
                hbPushMsgTelaCadastrar.getStyleClass().setAll("push-msg-error");
                hbPushMsgTelaCadastrar.setVisible(true);
            }
        } catch (SQLException | FalhaDeConexaoDbException e) {
            lbConexaoNavTelaCadastrar.setText("Desconectado");
        }
    }

}
