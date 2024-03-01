package org.example.bzreboques;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class TelaDetalharController implements Initializable {
    private Application app;
    private Documento documentoDetalhado;
    private boolean primeiraFoto;

    @FXML
    private HBox btnDireitaTelaDetalhar;

    @FXML
    private HBox btnEsquerdaTelaDetalhar;

    @FXML
    private HBox btnFecharPushMsgTelaDetalhar;

    @FXML
    private HBox btnVoltarTelaDetalhar;

    @FXML
    private HBox hbPushMsgTelaDetalhar;

    @FXML
    private ImageView imgEquipamentoTelaDetalhar;

    @FXML
    private Label lbAnoFabricacaoTelaDetalhar;

    @FXML
    private Label lbAnoModeloTelaDetalhar;

    @FXML
    private Label lbCorTelaDetalhar;

    @FXML
    private Label lbDataEHoraTelaDetalhar;

    @FXML
    private Label lbDataHoraTelaDetalhar;

    @FXML
    private Label lbDovTelaDetalhar;

    @FXML
    private Label lbEquipamentoTelaDetalhar;

    @FXML
    private Label lbMarcaModeloTelaDetalhar;

    @FXML
    private Label lbNomeClienteTelaDetalhar;

    @FXML
    private Label lbNumeracaoTelaDetalhar;

    @FXML
    private Label lbPlacaTelaDetalhar;

    @FXML
    private Label lbProcessoTelaDetalhar;

    @FXML
    private Label lbPushMsgTelaDetalhar;

    @FXML
    private Label lbRenavamTelaDetalhar;

    public TelaDetalharController(){
        this.app = new Application();
        this.primeiraFoto = true;
    }

    @FXML
    public void fecharPushMsg(MouseEvent event){
        hbPushMsgTelaDetalhar.setVisible(false);
    }

    @FXML
    public void setaEsquerda(){
        btnEsquerdaTelaDetalhar.setDisable(true);
        btnDireitaTelaDetalhar.setDisable(false);
        if(!primeiraFoto){
            primeiraFoto = true;
            imgEquipamentoTelaDetalhar.setImage(new Image("file:" + documentoDetalhado.getFotos().get(0)));
        }
    }

    @FXML void setaDireita(){
        btnEsquerdaTelaDetalhar.setDisable(false);
        btnDireitaTelaDetalhar.setDisable(true);
        if(primeiraFoto){
            primeiraFoto = false;
            imgEquipamentoTelaDetalhar.setImage(new Image("file:" + documentoDetalhado.getFotos().get(1)));
        }
    }

    @FXML
    public void trocarParaTelaPesquisar(){
        this.resetarTela();
        ScreenManager sm = ScreenManager.getInstance();
        sm.getTelaPesquisarController().inicializarTabela();
        sm.mudarScene("tela-pesquisar.fxml","Bezerra Reboques - Pesquisar");
    }

    public void setarDocumentoDetalhado(Documento documentoDetalhado){
        this.documentoDetalhado = documentoDetalhado;
    }

    public void setarCampos(){
        imgEquipamentoTelaDetalhar.setImage(new Image("file:" + documentoDetalhado.getFotos().get(0)));
        Rectangle clip = new Rectangle(imgEquipamentoTelaDetalhar.getFitWidth(), imgEquipamentoTelaDetalhar.getFitHeight());
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imgEquipamentoTelaDetalhar.setClip(clip);

        lbEquipamentoTelaDetalhar.setText(documentoDetalhado.getEquipamento());
        lbDovTelaDetalhar.setText(documentoDetalhado.getDov());
        lbNomeClienteTelaDetalhar.setText(documentoDetalhado.getNomeCliente());
        lbNumeracaoTelaDetalhar.setText(documentoDetalhado.getNumeracao());
        lbProcessoTelaDetalhar.setText(documentoDetalhado.getProcesso());
        lbPlacaTelaDetalhar.setText(documentoDetalhado.getPlaca());
        lbRenavamTelaDetalhar.setText(documentoDetalhado.getRenavam());
        lbCorTelaDetalhar.setText(documentoDetalhado.getCor());
        lbMarcaModeloTelaDetalhar.setText(documentoDetalhado.getMarcaModelo());
        lbAnoFabricacaoTelaDetalhar.setText(documentoDetalhado.getAnoFabricacao().toString());
        lbAnoModeloTelaDetalhar.setText(documentoDetalhado.getAnoModelo().toString());
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        lbDataEHoraTelaDetalhar.setText(documentoDetalhado.getDataHora().format(formatoDataHora));
    }

    private void atualizarRelogio() {
        LocalDateTime dateTimeAtual = LocalDateTime.now();
        Locale locale = new Locale("pt", "BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", locale);
        String dataHoraFormatada = dateTimeAtual.format(formatter);
        lbDataHoraTelaDetalhar.setText(dataHoraFormatada);
    }

    public void resetarTela(){
        primeiraFoto = true;
        btnEsquerdaTelaDetalhar.setDisable(true);
        btnDireitaTelaDetalhar.setDisable(false);
        hbPushMsgTelaDetalhar.setVisible(false);
        imgEquipamentoTelaDetalhar.setImage(new Image("file:"));
        lbEquipamentoTelaDetalhar.setText("");
        lbDovTelaDetalhar.setText("");
        lbNomeClienteTelaDetalhar.setText("");
        lbNumeracaoTelaDetalhar.setText("");
        lbProcessoTelaDetalhar.setText("");
        lbPlacaTelaDetalhar.setText("");
        lbRenavamTelaDetalhar.setText("");
        lbCorTelaDetalhar.setText("");
        lbMarcaModeloTelaDetalhar.setText("");
        lbAnoFabricacaoTelaDetalhar.setText("");
        lbAnoModeloTelaDetalhar.setText("");
        lbDataEHoraTelaDetalhar.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbPushMsgTelaDetalhar.setVisible(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> atualizarRelogio()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        this.resetarTela();
    }
}
