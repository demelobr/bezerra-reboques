package org.example.bzreboques;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TelaDelecaoController {
    private Application app;

    @FXML
    private Button btnNaoTelaDelecao;

    @FXML
    private Button btnSimTelaDelecao;

    public TelaDelecaoController(){
        this.app = new Application();
    }

    public Button getBtnNaoTelaDelecao() {
        return btnNaoTelaDelecao;
    }

    public void setBtnNaoTelaDelecao(Button btnNaoTelaDelecao) {
        this.btnNaoTelaDelecao = btnNaoTelaDelecao;
    }

    public Button getBtnSimTelaDelecao() {
        return btnSimTelaDelecao;
    }

    public void setBtnSimTelaDelecao(Button btnSimTelaDelecao) {
        this.btnSimTelaDelecao = btnSimTelaDelecao;
    }
}
