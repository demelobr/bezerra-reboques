package org.example.bzreboques;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TelaPesquisarController implements Initializable {
    private Application app;
    private ObservableList<Documento> listaDeDocumentos;
    private boolean primeiraFoto;

    @FXML
    private HBox btnDeletarTelaPesquisar;

    @FXML
    private HBox btnDetalharTelaPesquisar;

    @FXML
    private HBox btnDireitaTelaPesquisar;

    @FXML
    private HBox btnEditarTelaPesquisar;

    @FXML
    private HBox btnEsquerdaTelaPesquisar;

    @FXML
    private HBox btnFecharPushMsgTelaPesquisar;

    @FXML
    private Button btnGerarTelaPesquisar;

    @FXML
    private HBox hbCadastrarNavTelaPesquisar;

    @FXML
    private HBox hbConfigNavTelaPesquisar;

    @FXML
    private HBox hbNuvemNavTelaPesquisar;

    @FXML
    private HBox hbPesquisarNavTelaPesquisar;

    @FXML
    private HBox hbPesquisarTelaPesquisar;

    @FXML
    private HBox hbPushMsgTelaPesquisar;

    @FXML
    private ImageView imgEquipamentosTelaPesquisar;

    @FXML
    private Label lbAnoFabricacaoTelaPesquisar;

    @FXML
    private Label lbAnoModeloTelaPesquisar;

    @FXML
    private Label lbConexaoNavTelaPesquisar;

    @FXML
    private Label lbCorTelaPesquisar;

    @FXML
    private Label lbDataHoraTelaPesquisar;

    @FXML
    private Label lbDovTelaPesquisar;

    @FXML
    private Label lbEquipamentoTelaPesquisar;

    @FXML
    private Label lbMarcaModeloTelaPesquisar;

    @FXML
    private Label lbNomeClienteTelaPesquisar;

    @FXML
    private Label lbNumeracaoTelaPesquisar;

    @FXML
    private Label lbPlacaTelaPesquisar;

    @FXML
    private Label lbProcessoTelaPesquisar;

    @FXML
    private Label lbPushMsgTelaPesquisar;

    @FXML
    private Label lbRenavamTelaPesquisar;

    @FXML
    private Label lbResultadosEncontradosTelaPesquisar;

    @FXML
    private TableColumn<Documento, String> tcDataHoraTelaPesquisar;

    @FXML
    private TableColumn<Documento, String> tcEquipamentoTelaPesquisar;

    @FXML
    private TableColumn<Documento, String> tcNumeracaoTelaPesquisar;

    @FXML
    private TableColumn<Documento, String> tcPlacaTelaPesquisar;

    @FXML
    private TableView<Documento> tvDocumentosTelaPesquisar;

    @FXML
    private TextField tfPesquisarTelaPesquisar;

    public TelaPesquisarController(){
        this.app = new Application();
        this.listaDeDocumentos = FXCollections.observableArrayList();
        this.primeiraFoto = true;
    }

    @FXML
    public void fecharPushMsg(MouseEvent event){
        hbPushMsgTelaPesquisar.setVisible(false);
    }

    @FXML
    public void setaEsquerda(){
        btnEsquerdaTelaPesquisar.setDisable(true);
        btnDireitaTelaPesquisar.setDisable(false);
        Documento documentoSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedItem();
        if(documentoSelecionado != null){
            if(!primeiraFoto){
                primeiraFoto = true;
                imgEquipamentosTelaPesquisar.setImage(new Image("file:" + documentoSelecionado.getFotos().get(0)));
            }
        }else{
            lbPushMsgTelaPesquisar.setText("Selecione um documento!");
            hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaPesquisar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaPesquisar.setVisible(false);
                });
            }).start();
        }
    }

    @FXML
    public void setaDireita(){
        btnEsquerdaTelaPesquisar.setDisable(false);
        btnDireitaTelaPesquisar.setDisable(true);
        Documento documentoSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedItem();
        if(documentoSelecionado != null){
            if(primeiraFoto){
                primeiraFoto = false;
                imgEquipamentosTelaPesquisar.setImage(new Image("file:" + documentoSelecionado.getFotos().get(1)));
            }
        }else{
            lbPushMsgTelaPesquisar.setText("Selecione um documento!");
            hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaPesquisar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaPesquisar.setVisible(false);
                });
            }).start();
        }
    }

    @FXML
    public void editarDocumento(){
        Documento documentoSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedItem();
        if(documentoSelecionado != null){
            ScreenManager sm = ScreenManager.getInstance();
            sm.getTelaEditarController().resetarTela();
            sm.getTelaEditarController().setarDocumentoEditado(documentoSelecionado);
            sm.getTelaEditarController().setarCampos();
            sm.mudarScene("tela-editar.fxml","Bezerra Reboques - Editar");
        }else{
            lbPushMsgTelaPesquisar.setText("Selecione um documento!");
            hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaPesquisar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaPesquisar.setVisible(false);
                });
            }).start();
        }
    }

    @FXML
    public void gerarDocumento(){
        Documento documentoSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedItem();
        if(documentoSelecionado != null){
            List<Documento> listaComODocumentoASerGerado = new ArrayList<>();
            listaComODocumentoASerGerado.add(documentoSelecionado);
            try {
                GeradorDeDocumentos gerador = new GeradorDeDocumentos();
                gerador.geradorDoDocumento(listaComODocumentoASerGerado);
            } catch (JRException e) {
                lbPushMsgTelaPesquisar.setText("Erro ao gerar documento!");
                hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
                hbPushMsgTelaPesquisar.setVisible(true);
            }
        }else{
            lbPushMsgTelaPesquisar.setText("Selecione um documento!");
            hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaPesquisar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaPesquisar.setVisible(false);
                });
            }).start();
        }
    }

    @FXML
    public void confirmarDelecaoDeDocumento(){
        Documento documentoSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedItem();
        if(documentoSelecionado != null){
            ScreenManager sm = ScreenManager.getInstance();
            Scene scene = sm.getTelaDelecaoScene();
            Button simBtn = sm.getTelaDelecaoController().getBtnSimTelaDelecao();
            Button naoBtn = sm.getTelaDelecaoController().getBtnNaoTelaDelecao();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Excluir documento?");
            dialogStage.getIcons().add(new Image("file:" + "src/main/resources/org/example/bzreboques/icon-16x16.png"));
            dialogStage.setScene(scene);

            simBtn.setOnAction(e -> {
                this.deletarDocumento();
                dialogStage.close();
            });

            naoBtn.setOnAction(e -> {
                dialogStage.close();
            });

            dialogStage.showAndWait();
        }else{
            lbPushMsgTelaPesquisar.setText("Selecione um documento!");
            hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaPesquisar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaPesquisar.setVisible(false);
                });
            }).start();
        }
    }

    public void deletarDocumento(){
        Documento documentoSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedItem();
        if(documentoSelecionado != null){
            try {
                int indexV = documentoSelecionado.getFotos().get(0).indexOf("-v");
                String versao = documentoSelecionado.getFotos().get(0).substring(indexV + 1);
                int index = documentoSelecionado.getFotos().get(0).indexOf(".");
                String ft1Extensao = documentoSelecionado.getFotos().get(0).substring(index + 1);
                String foto1Caminho = String.format("%s/%s-%s-ft1-v%s.%s", app.getServidor().getNuvem().getCaminhoDaPasta(), documentoSelecionado.getEquipamento().toLowerCase(), documentoSelecionado.getPlaca().toLowerCase(), versao, ft1Extensao);
                index = documentoSelecionado.getFotos().get(1).indexOf(".");
                String ft2Extensao = documentoSelecionado.getFotos().get(1).substring(index + 1);
                String foto2Caminho = String.format("%s/%s-%s-ft2-v%s.%s", app.getServidor().getNuvem().getCaminhoDaPasta(), documentoSelecionado.getEquipamento().toLowerCase(), documentoSelecionado.getPlaca().toLowerCase(), versao, ft2Extensao);
                File foto1 = new File(foto1Caminho);
                File foto2 = new File(foto2Caminho);
                if(foto1.exists()) foto1.delete();
                if(foto2.exists()) foto2.delete();

                app.getServidor().deletarDocumento(documentoSelecionado);
            } catch (DocumentoDeletadoComSucessoException e) {
                int idSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedIndex();
                tvDocumentosTelaPesquisar.getItems().remove(idSelecionado);
                listaDeDocumentos.remove(documentoSelecionado);

                lbPushMsgTelaPesquisar.setText(e.getMessage());
                hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-success");
                hbPushMsgTelaPesquisar.setVisible(true);

                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ev) {
                        ev.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        lbResultadosEncontradosTelaPesquisar.setText(String.format("%d resultado(s) encontrado(s)", tvDocumentosTelaPesquisar.getItems().size()));
                        hbPushMsgTelaPesquisar.setVisible(false);
                    });
                }).start();

            } catch (DocumentoNaoExisteException | FalhaDeConexaoDbException | DocumentoNuloException e) {
                lbPushMsgTelaPesquisar.setText(e.getMessage());
                hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
                hbPushMsgTelaPesquisar.setVisible(true);
            }
        }else{
            lbPushMsgTelaPesquisar.setText("Selecione um documento!");
            hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaPesquisar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaPesquisar.setVisible(false);
                });
            }).start();
        }
    }

    @FXML
    public void detalharDocumento(){
        Documento documentoSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedItem();
        if(documentoSelecionado != null){
            ScreenManager sm = ScreenManager.getInstance();
            sm.getTelaDetalharController().resetarTela();
            sm.getTelaDetalharController().setarDocumentoDetalhado(documentoSelecionado);
            sm.getTelaDetalharController().setarCampos();
            sm.mudarScene("tela-detalhar.fxml","Bezerra Reboques - Detalhamento");

        }else{
            lbPushMsgTelaPesquisar.setText("Selecione um documento!");
            hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaPesquisar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaPesquisar.setVisible(false);
                });
            }).start();
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
        lbDataHoraTelaPesquisar.setText(dataHoraFormatada);
    }

    public void inicializarTabela(){
        tvDocumentosTelaPesquisar.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tcEquipamentoTelaPesquisar.setCellValueFactory(new PropertyValueFactory<Documento,String>("equipamento"));
        tcPlacaTelaPesquisar.setCellValueFactory(new PropertyValueFactory<Documento,String>("placa"));
        tcNumeracaoTelaPesquisar.setCellValueFactory(new PropertyValueFactory<Documento,String>("numeracao"));
        tcDataHoraTelaPesquisar.setCellValueFactory(cellData -> {
            LocalDateTime dateTime = cellData.getValue().getDataHora();
            DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            return new javafx.beans.property.SimpleStringProperty(dateTime.format(formatoDataHora));
        });

        try (Connection conn = ConnectionFactory.getConnection()){
            lbConexaoNavTelaPesquisar.setText("Conectado");
            try {
                tvDocumentosTelaPesquisar.setItems(this.atualizarTabela());
                lbResultadosEncontradosTelaPesquisar.setText(String.format("%d resultado(s) encontrado(s)", tvDocumentosTelaPesquisar.getItems().size()));
                this.limparCampos();
            } catch (FalhaDeConexaoDbException e) {
                lbPushMsgTelaPesquisar.setText(e.getMessage());
                hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
                hbPushMsgTelaPesquisar.setVisible(true);
            }
        } catch (SQLException | FalhaDeConexaoDbException e) {
            lbConexaoNavTelaPesquisar.setText("Desconectado");
            for (int i = 0; i < tvDocumentosTelaPesquisar.getItems().size(); i++) {
                tvDocumentosTelaPesquisar.getItems().clear();
            }
            lbResultadosEncontradosTelaPesquisar.setText(String.format("%d resultado(s) encontrado(s)", tvDocumentosTelaPesquisar.getItems().size()));
            this.limparCampos();
        }
    }

    public ObservableList<Documento> atualizarTabela() throws FalhaDeConexaoDbException {
        listaDeDocumentos = FXCollections.observableArrayList(app.getServidor().listarTodosDocumentos());
        return listaDeDocumentos;
    }

    private ObservableList<Documento> buscarDocumento(){
        ObservableList<Documento> documentosDaPesquisa = FXCollections.observableArrayList();
        for(Documento documento : listaDeDocumentos){
            if(documento.getPlaca().toLowerCase().contains(tfPesquisarTelaPesquisar.getText().toLowerCase()) ||
                    documento.getNumeracao().toLowerCase().contains(tfPesquisarTelaPesquisar.getText().toLowerCase())){
                documentosDaPesquisa.add(documento);
            }
        }
        return documentosDaPesquisa;
    }

    public void selecionarESetarDocumento(){
        Documento documentoSelecionado = tvDocumentosTelaPesquisar.getSelectionModel().getSelectedItem();
        if(documentoSelecionado != null){
            btnEsquerdaTelaPesquisar.setDisable(true);
            btnDireitaTelaPesquisar.setDisable(false);

            imgEquipamentosTelaPesquisar.setImage(new Image("file:" + documentoSelecionado.getFotos().get(0)));
            Rectangle clip = new Rectangle(imgEquipamentosTelaPesquisar.getFitWidth(), imgEquipamentosTelaPesquisar.getFitHeight());
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            imgEquipamentosTelaPesquisar.setClip(clip);

            lbEquipamentoTelaPesquisar.setText(documentoSelecionado.getEquipamento());
            lbDovTelaPesquisar.setText(documentoSelecionado.getDov());
            lbNomeClienteTelaPesquisar.setText(documentoSelecionado.getNomeCliente());
            lbNumeracaoTelaPesquisar.setText(documentoSelecionado.getNumeracao());
            lbProcessoTelaPesquisar.setText(documentoSelecionado.getProcesso());
            lbPlacaTelaPesquisar.setText(documentoSelecionado.getPlaca());
            lbRenavamTelaPesquisar.setText(documentoSelecionado.getRenavam());
            lbCorTelaPesquisar.setText(documentoSelecionado.getCor());
            lbMarcaModeloTelaPesquisar.setText(documentoSelecionado.getMarcaModelo());
            lbAnoFabricacaoTelaPesquisar.setText(documentoSelecionado.getAnoFabricacao().toString());
            lbAnoModeloTelaPesquisar.setText(documentoSelecionado.getMarcaModelo().toString());
        }else{
            lbPushMsgTelaPesquisar.setText("Selecione um documento!");
            hbPushMsgTelaPesquisar.getStyleClass().setAll("push-msg-error");
            hbPushMsgTelaPesquisar.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ev) {
                    ev.printStackTrace();
                }
                Platform.runLater(() -> {
                    hbPushMsgTelaPesquisar.setVisible(false);
                });
            }).start();

        }
    }

    public void limparCampos(){
        imgEquipamentosTelaPesquisar.setImage(new Image("file:"));
        lbEquipamentoTelaPesquisar.setText("");
        lbDovTelaPesquisar.setText("");
        lbNomeClienteTelaPesquisar.setText("");
        lbNumeracaoTelaPesquisar.setText("");
        lbProcessoTelaPesquisar.setText("");
        lbPlacaTelaPesquisar.setText("");
        lbRenavamTelaPesquisar.setText("");
        lbCorTelaPesquisar.setText("");
        lbMarcaModeloTelaPesquisar.setText("");
        lbAnoFabricacaoTelaPesquisar.setText("");
        lbAnoModeloTelaPesquisar.setText("");
    }

    public void resetarTela(){
        primeiraFoto = true;
        btnEsquerdaTelaPesquisar.setDisable(true);
        btnDireitaTelaPesquisar.setDisable(false);
        hbPushMsgTelaPesquisar.setVisible(false);
        imgEquipamentosTelaPesquisar.setImage(new Image("file:"));
        lbEquipamentoTelaPesquisar.setText("");
        lbDovTelaPesquisar.setText("");
        lbNomeClienteTelaPesquisar.setText("");
        lbNumeracaoTelaPesquisar.setText("");
        lbProcessoTelaPesquisar.setText("");
        lbPlacaTelaPesquisar.setText("");
        lbRenavamTelaPesquisar.setText("");
        lbCorTelaPesquisar.setText("");
        lbMarcaModeloTelaPesquisar.setText("");
        lbAnoFabricacaoTelaPesquisar.setText("");
        lbAnoModeloTelaPesquisar.setText("");
        tfPesquisarTelaPesquisar.setText("");
        this.inicializarTabela();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgTelaPesquisar.setVisible(false);
        this.limparCampos();
        this.inicializarTabela();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> atualizarRelogio()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        tfPesquisarTelaPesquisar.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue){
                hbPesquisarTelaPesquisar.getStyleClass().setAll("shadow","input-box");
            }else{
                hbPesquisarTelaPesquisar.getStyleClass().remove("shadow");
            }
        });

        tfPesquisarTelaPesquisar.setOnKeyReleased((KeyEvent event) -> {
            tvDocumentosTelaPesquisar.setItems(this.buscarDocumento());
            lbResultadosEncontradosTelaPesquisar.setText(String.format("%d resultado(s) encontrado(s)", tvDocumentosTelaPesquisar.getItems().size()));
        });

        tvDocumentosTelaPesquisar.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()){
                case UP, DOWN -> {
                    this.selecionarESetarDocumento();
                    this.setaEsquerda();
                }
                case LEFT -> this.setaEsquerda();
                case RIGHT -> this.setaDireita();
                case D -> this.confirmarDelecaoDeDocumento();
                case E -> this.editarDocumento();
                case I -> this.detalharDocumento();
                case G -> this.gerarDocumento();
            }
        });

        tvDocumentosTelaPesquisar.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                this.selecionarESetarDocumento();
                this.setaEsquerda();
            }
        });
    }
}
