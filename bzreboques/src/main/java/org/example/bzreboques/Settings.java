package org.example.bzreboques;

import java.io.Serializable;

public class Settings implements Serializable {
    private static final long serialVersionID = 1L;
    private String razaoSocial;
    private String cnpj;
    private String inscricaoMunicipal;
    private String endereco;
    private String telefone;
    private String responsavelDetran;
    private String urlDB;
    private String userDB;
    private String passwordDB;

    public Settings(String razaoSocial, String cnpj, String inscricaoMunicipal, String endereco, String telefone, String responsavelDetran, String urlDB, String userDB, String passwordDB){
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.inscricaoMunicipal = inscricaoMunicipal;
        this.endereco = endereco;
        this.telefone = telefone;
        this.responsavelDetran = responsavelDetran;
        this.urlDB = urlDB;
        this.userDB = userDB;
        this.passwordDB = passwordDB;
    }


    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getResponsavelDetran() {
        return responsavelDetran;
    }

    public void setResponsavelDetran(String responsavelDetran) {
        this.responsavelDetran = responsavelDetran;
    }

    public String getUrlDB() {
        return urlDB;
    }

    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public void setUserDB(String userDB) {
        this.userDB = userDB;
    }

    public String getPasswordDB() {
        return passwordDB;
    }

    public void setPasswordDB(String passwordDB) {
        this.passwordDB = passwordDB;
    }

    public String toString(){
        return String.format("<Configurações>\nRazão Social: %s\nCnpj: %s\nInscrição Municipal: %s\nEndereço: %s\n" +
                        "Telefone: %s\nResponsável Detran: %s\nURL DB: %s\nUser DB: %s\nPassword DB: %s\n" +
                        "==============================", razaoSocial, cnpj, inscricaoMunicipal, endereco, telefone,
                responsavelDetran, urlDB, userDB, passwordDB);
    }

}
