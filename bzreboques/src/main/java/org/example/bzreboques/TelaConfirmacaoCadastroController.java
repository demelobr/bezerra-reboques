package org.example.bzreboques;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TelaConfirmacaoCadastroController {
    private Application app;

    @FXML
    private Button btnNaoTelaConfirmacaoCadastro;

    @FXML
    private Button btnSimTelaConfirmacaoCadastro;

    public TelaConfirmacaoCadastroController(){
        this.app = new Application();
    }

    public Button getBtnNaoTelaConfirmacaoCadastro() {
        return btnNaoTelaConfirmacaoCadastro;
    }

    public void setBtnNaoTelaConfirmacaoCadastro(Button btnNaoTelaConfirmacaoCadastro) {
        this.btnNaoTelaConfirmacaoCadastro = btnNaoTelaConfirmacaoCadastro;
    }

    public Button getBtnSimTelaConfirmacaoCadastro() {
        return btnSimTelaConfirmacaoCadastro;
    }

    public void setBtnSimTelaConfirmacaoCadastro(Button btnSimTelaConfirmacaoCadastro) {
        this.btnSimTelaConfirmacaoCadastro = btnSimTelaConfirmacaoCadastro;
    }
}
