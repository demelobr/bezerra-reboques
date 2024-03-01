package org.example.bzreboques;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Nuvem implements Serializable {
    private static final long serialVersionID = 2L;
    private String caminhoDaPasta;
    private String periodicidade;
    private LocalDateTime ultimoUpload;

    public Nuvem(String caminhoDaPasta, String periodicidade, LocalDateTime ultimoUpload){
        this.caminhoDaPasta = caminhoDaPasta;
        this.periodicidade = periodicidade;
        this.ultimoUpload = ultimoUpload;
    }

    public String getCaminhoDaPasta() {
        return caminhoDaPasta;
    }

    public void setCaminhoDaPasta(String caminhoDaPasta) {
        this.caminhoDaPasta = caminhoDaPasta;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public LocalDateTime getUltimoUpload() {
        return ultimoUpload;
    }

    public void setUltimoUpload(LocalDateTime ultimoUpload) {
        this.ultimoUpload = ultimoUpload;
    }

    public String toString(){
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return String.format("<Nuvem>\nPasta de Upload: %s\nPeriodicidade: %s\nÚltimo upload: %s", caminhoDaPasta, periodicidade, ultimoUpload.format(formatoDataHora));
    }

}
