package org.example.bzreboques;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TelaEditarController implements Initializable {
    private Application app;
    private Documento documentoEditado;

    @FXML
    private Button btnCadastrarTelaEditar;

    @FXML
    private Button btnEscolherFotosTelaEditar;

    @FXML
    private HBox btnFecharPushMsgTelaEditar;

    @FXML
    private HBox btnVoltarTelaEditar;

    @FXML
    private HBox hbPushMsgTelaEditar;

    @FXML
    private Label lbDataHoraTelaEditar;

    @FXML
    private Label lbFoto1TelaEditar;

    @FXML
    private Label lbFoto2TelaEditar;

    @FXML
    private Label lbPushMsgTelaEditar;

    @FXML
    private RadioButton rbChassiTelaEditar;

    @FXML
    private RadioButton rbMotorTelaEditar;

    @FXML
    private TextField tfAnoFabricacaoTelaEditar;

    @FXML
    private TextField tfAnoModeloTelaEditar;

    @FXML
    private TextField tfCorTelaEditar;

    @FXML
    private TextField tfDovTelaEditar;

    @FXML
    private TextField tfMarcaModeloTelaEditar;

    @FXML
    private TextField tfNomeClienteTelaEditar;

    @FXML
    private TextField tfNumeracaoTelaEditar;

    @FXML
    private TextField tfPlacaTelaEditar;

    @FXML
    private TextField tfProcessoTelaEditar;

    @FXML
    private TextField tfRenavamTelaEditar;

    public TelaEditarController(){
        this.app = new Application();
    }

    @FXML
    public void fecharPushMsg(MouseEvent event){
        hbPushMsgTelaEditar.setVisible(false);
    }

    @FXML
    public void salvarDocumento(){
        String nomeCliente = tfNomeClienteTelaEditar.getText();
        String dov = tfDovTelaEditar.getText();
        String numeracao = tfNumeracaoTelaEditar.getText();
        String processo = tfProcessoTelaEditar.getText();
        String placa = tfPlacaTelaEditar.getText();
        String renavam = tfRenavamTelaEditar.getText();
        String cor = tfCorTelaEditar.getText();
        String marcaModelo = tfMarcaModeloTelaEditar.getText();
        String anoFabricacao = tfAnoFabricacaoTelaEditar.getText();
        String anoModelo = tfAnoModeloTelaEditar.getText();
        String foto1 = lbFoto1TelaEditar.getText();
        String foto2 = lbFoto2TelaEditar.getText();
        String equipamento = "";

        if(rbMotorTelaEditar.isSelected()) equipamento = "MOTOR";
        else if(rbChassiTelaEditar.isSelected()) equipamento = "CHASSI";

        if(nomeCliente.isEmpty() && dov.isEmpty() && numeracao.isEmpty() && processo.isEmpty() && placa.isEmpty() &&
                renavam.isEmpty() && cor.isEmpty() && marcaModelo.isEmpty() && anoFabricacao.isEmpty() && anoModelo.isEmpty() &&
                equipamento.equals(documentoEditado.getEquipamento())  && foto1.equals(documentoEditado.getFotos().get(0)) && foto2.equals(documentoEditado.getFotos().get(1))){
            lbPushMsgTelaEditar.setText("Não houve alterações!");
            hbPushMsgTelaEditar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaEditar.setVisible(true);
        }else{
            if(nomeCliente.isEmpty() || nomeCliente.equals(documentoEditado.getNomeCliente())){
                nomeCliente = documentoEditado.getNomeCliente();
            }
            if(dov.isEmpty() || dov.equals(documentoEditado.getDov())){
                dov = documentoEditado.getDov();
            }
            if(numeracao.isEmpty() || numeracao.equals(documentoEditado.getNumeracao())){
                numeracao = documentoEditado.getNumeracao();
            }
            if(processo.isEmpty() || processo.equals(documentoEditado.getProcesso())){
                processo = documentoEditado.getProcesso();
            }
            if(placa.isEmpty() || placa.equals(documentoEditado.getPlaca())){
                placa = documentoEditado.getPlaca();
            }
            if(renavam.isEmpty() || renavam.equals(documentoEditado.getRenavam())){
                renavam = documentoEditado.getRenavam();
            }
            if(cor.isEmpty() || cor.equals(documentoEditado.getCor())){
                cor = documentoEditado.getCor();
            }
            if(marcaModelo.isEmpty() || marcaModelo.equals(documentoEditado.getMarcaModelo())){
                marcaModelo = documentoEditado.getMarcaModelo();
            }
            if(anoFabricacao.isEmpty() || anoFabricacao.equals(documentoEditado.getAnoFabricacao().toString())){
                anoFabricacao = documentoEditado.getAnoFabricacao().toString();
            }
            if(anoModelo.isEmpty() || anoModelo.equals(documentoEditado.getAnoModelo().toString())){
                anoModelo = documentoEditado.getAnoModelo().toString();
            }
            if(foto1.isEmpty() || foto1.equals(documentoEditado.getFotos().get(0))){
                foto1 = documentoEditado.getFotos().get(0);
            }
            if(foto2.isEmpty() || foto2.equals(documentoEditado.getFotos().get(1))){
                foto2 = documentoEditado.getFotos().get(1);
            }
            if(equipamento.equals(documentoEditado.getEquipamento())){
                equipamento = documentoEditado.getEquipamento();
            }

            try {
                app.getServidor().atualizarDocumento(documentoEditado, nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, Year.of(Integer.parseInt(anoFabricacao)), Year.of(Integer.parseInt(anoModelo)), foto1, foto2, documentoEditado.getDataHora());
            } catch (DocumentoNaoExisteException | DocumentoExisteException | FalhaDeConexaoDbException | DocumentoNuloException e) {
                lbPushMsgTelaEditar.setText(e.getMessage());
                hbPushMsgTelaEditar.getStyleClass().setAll("push-msg-error");
                hbPushMsgTelaEditar.setVisible(true);
            } catch (DocumentoAtualizadoComSucessoException e) {
                lbPushMsgTelaEditar.setText(e.getMessage());
                hbPushMsgTelaEditar.getStyleClass().setAll("push-msg-success");
                hbPushMsgTelaEditar.setVisible(true);

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
    }

    @FXML
    public void escolherImagens(ActionEvent event){
        Stage stg = ScreenManager.getStg();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.png", "*.jpeg"));
        List<File> fotos = fc.showOpenMultipleDialog(stg);
        if(fotos != null && fotos.size() == 2){
            lbFoto1TelaEditar.setText(fotos.get(0).getAbsolutePath());
            lbFoto2TelaEditar.setText(fotos.get(1).getAbsolutePath());
        }else{
            lbPushMsgTelaEditar.setText("Necessário escolher 2 fotos!");
            hbPushMsgTelaEditar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaEditar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaEditar.setVisible(false);
                });
            }).start();
        }
    }

    @FXML
    public void selecionarMotor(ActionEvent event){
        rbMotorTelaEditar.setSelected(true);
        rbChassiTelaEditar.setSelected(false);
    }

    @FXML
    public void selecionarChassi(ActionEvent event){
        rbMotorTelaEditar.setSelected(false);
        rbChassiTelaEditar.setSelected(true);
    }

    @FXML
    public void trocarParaTelaPesquisar(){
        this.resetarTela();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getTelaPesquisarController().inicializarTabela();
        sm.mudarScene("tela-pesquisar.fxml","Bezerra Reboques - Pesquisar");
    }

    public void setarDocumentoEditado(Documento documentoEditado){
        this.documentoEditado = documentoEditado;
    }

    private void atualizarRelogio() {
        LocalDateTime dateTimeAtual = LocalDateTime.now();
        Locale locale = new Locale("pt", "BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", locale);
        String dataHoraFormatada = dateTimeAtual.format(formatter);
        lbDataHoraTelaEditar.setText(dataHoraFormatada);
    }

    public void setarCampos(){
        if(documentoEditado.getEquipamento().equalsIgnoreCase("MOTOR")) {
            rbMotorTelaEditar.setSelected(true);
            rbChassiTelaEditar.setSelected(false);
        }
        else {
            rbMotorTelaEditar.setSelected(false);
            rbChassiTelaEditar.setSelected(true);
        }
        tfNomeClienteTelaEditar.setPromptText(documentoEditado.getNomeCliente());
        tfDovTelaEditar.setPromptText(documentoEditado.getDov());
        tfNumeracaoTelaEditar.setPromptText(documentoEditado.getNumeracao());
        tfProcessoTelaEditar.setPromptText(documentoEditado.getProcesso());
        tfRenavamTelaEditar.setPromptText(documentoEditado.getRenavam());
        tfCorTelaEditar.setPromptText(documentoEditado.getCor());
        tfMarcaModeloTelaEditar.setPromptText(documentoEditado.getMarcaModelo());
        tfAnoFabricacaoTelaEditar.setPromptText(documentoEditado.getAnoFabricacao().toString());
        tfAnoModeloTelaEditar.setPromptText(documentoEditado.getAnoModelo().toString());
        lbFoto1TelaEditar.setText(documentoEditado.getFotos().get(0));
        lbFoto2TelaEditar.setText(documentoEditado.getFotos().get(1));
    }

    public void resetarTela(){
        hbPushMsgTelaEditar.setVisible(false);
        tfNomeClienteTelaEditar.setText("");
        tfDovTelaEditar.setText("");
        tfProcessoTelaEditar.setText("");
        tfRenavamTelaEditar.setText("");
        tfCorTelaEditar.setText("");
        tfMarcaModeloTelaEditar.setText("");
        tfAnoFabricacaoTelaEditar.setText("");
        tfAnoModeloTelaEditar.setText("");
        lbFoto1TelaEditar.setText("");
        lbFoto2TelaEditar.setText("");
        rbMotorTelaEditar.setSelected(true);
        rbChassiTelaEditar.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgTelaEditar.setVisible(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> atualizarRelogio()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

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
        tfAnoFabricacaoTelaEditar.setTextFormatter(textFormatterAnoFabricacao);
        tfAnoModeloTelaEditar.setTextFormatter(textFormatterAnoModelo);

        this.resetarTela();
    }
}
