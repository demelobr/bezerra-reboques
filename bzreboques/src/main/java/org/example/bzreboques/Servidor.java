package org.example.bzreboques;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public class Servidor {
    private static Servidor instance;
    private IControladorDocumentos controladorDocumentos;
    private Settings configuracoes;
    private Nuvem nuvem;

    public Servidor() {
        this.controladorDocumentos = ControladorDocumentos.getInstance();
    }

    public static Servidor getInstance() {
        if(instance == null){
            instance = new Servidor();
        }
        return instance;
    }

    public Settings getConfiguracoes() {
        return configuracoes;
    }

    public Nuvem getNuvem() {
        return nuvem;
    }

    public void setConfiguracoes(Settings configuracoes) {
        this.configuracoes = configuracoes;
    }

    public void setNuvem(Nuvem nuvem) {
        this.nuvem = nuvem;
    }
    public void salvarConfiguracoes(Settings settings) throws FalhaDeSalvamentoDasConfiguracoesException, ConfiguracoesSalvasComSucessoException {
        ManipulacaoDasConfiguracoes.salvarConfiguracoes(settings);
    }

    public Settings carregarConfiguracoes() throws FalhaDeCarregamentoDasConfiguracoesException {
        return ManipulacaoDasConfiguracoes.carregarConfiguracoes();
    }

    public void salvarConfiguracoesCloud(Nuvem nuvem) throws ConfiguracoesCloudSalvasComSucessoException, FalhaDeSalvamentoDasConfiguracoesDeCloudException {
        ManipulacaoCloud.salvarConfiguracoesCloud(nuvem);
    }

    public Nuvem carregarConfiguracoesCloud() throws FalhaDeSalvamentoDasConfiguracoesDeCloudException {
        return ManipulacaoCloud.carregarConfiguracoesCloud();
    }

    public void inserirDocumento(Documento documento) throws FalhaDeConexaoDbException, DocumentoNuloException, DocumentoInvalidoException, DocumentoInseridoComSucessoException {
        controladorDocumentos.inserirDocumento(documento);
    }

    public void atualizarDocumento(Documento documento, String nomeCliente, String dov, String equipamento, String numeracao, String processo, String placa, String renavam, String cor, String marcaModelo, Year anoFabricacao, Year anoModelo, String foto1, String foto2, LocalDateTime dataHora) throws DocumentoNaoExisteException, FalhaDeConexaoDbException, DocumentoNuloException, DocumentoExisteException, DocumentoAtualizadoComSucessoException {
        controladorDocumentos.atualizarDocumento(documento, nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, anoFabricacao, anoModelo, foto1, foto2, dataHora);
    }

    public void deletarDocumento(Documento documento) throws DocumentoDeletadoComSucessoException, DocumentoNaoExisteException, FalhaDeConexaoDbException, DocumentoNuloException {
        controladorDocumentos.deletarDocumento(documento);
    }

    public boolean checarDadosDoDocumento(Documento documento){
        return controladorDocumentos.checarDadosDoDocumento(documento);
    }

    public boolean existeDocumento(String equipamento, String placa) throws FalhaDeConexaoDbException {
        return controladorDocumentos.existeDocumento(equipamento, placa);
    }

    public List<Documento> listarTodosDocumentos() throws FalhaDeConexaoDbException {
        return controladorDocumentos.listarTodosDocumentos();
    }

    public List<Documento> listarDocumentosPorPlaca(String placa) throws FalhaDeConexaoDbException {
        return controladorDocumentos.listarDocumentosPorPlaca(placa);
    }

}

