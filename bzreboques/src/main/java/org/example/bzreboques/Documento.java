package org.example.bzreboques;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Documento {
    private String nomeCliente;
    private String dov;
    private String equipamento;
    private String numeracao;
    private String processo;
    private String placa;
    private String renavam;
    private String cor;
    private String marcaModelo;
    private Year anoFabricacao;
    private Year anoModelo;
    private List<String> fotos;
    private LocalDateTime dataHora;

    public Documento(String nomeCliente, String dov, String equipamento, String numeracao, String processo, String placa, String renavam, String cor, String marcaModelo, Year anoFabricacao, Year anoModelo, List<String> fotos, LocalDateTime dataHora){
        this.nomeCliente = nomeCliente;
        this.dov = dov;
        this.equipamento = equipamento;
        this.numeracao = numeracao;
        this.processo = processo;
        this.placa = placa;
        this.renavam = renavam;
        this.cor = cor;
        this.marcaModelo = marcaModelo;
        this.anoFabricacao = anoFabricacao;
        this.anoModelo = anoModelo;
        this.fotos = fotos;
        this.dataHora = dataHora;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getDov() {
        return dov;
    }

    public void setDov(String dov) {
        this.dov = dov;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(String numeracao) {
        this.numeracao = numeracao;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
    public String getMarcaModelo() {
        return marcaModelo;
    }
    public void setMarcaModelo(String marcaModelo) {
        this.marcaModelo = marcaModelo;
    }
    public Year getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Year anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Year getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Year anoModelo) {
        this.anoModelo = anoModelo;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return String.format("<Documento>\nCliente: %s\nDOV: %s\nEquipamento: %s\nNumeraço: %s\nProcesso: %s\n" +
                        "Placa: %s\nRenavam: %s\nCor: %s\nMarca/Modelo: %s\nAno Fabricaço: %s\n" +
                        "Ano Modelo: %s\nFoto 1: %s\nFoto 2: %s\nData e Hora: %s\n==============================",
                nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, anoFabricacao.toString(),
                anoModelo.toString(), fotos.get(0), fotos.get(1), dataHora.format(formatoDataHora));
    }

}
