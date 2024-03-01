package org.example.bzreboques;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public interface IControladorDocumentos {
    void inserirDocumento(Documento documento) throws FalhaDeConexaoDbException, DocumentoInseridoComSucessoException, DocumentoNuloException, DocumentoInvalidoException;
    void atualizarDocumento(Documento documento, String nomeCliente, String dov, String equipamento, String numeracao, String processo, String placa, String renavam, String cor, String marcaModelo, Year anoFabricacao, Year anoModelo, String foto1, String foto2, LocalDateTime dataHora) throws FalhaDeConexaoDbException, DocumentoAtualizadoComSucessoException, DocumentoNuloException, DocumentoNaoExisteException, DocumentoExisteException;
    void deletarDocumento(Documento documento) throws DocumentoDeletadoComSucessoException, FalhaDeConexaoDbException, DocumentoNaoExisteException, DocumentoNuloException;
    boolean checarDadosDoDocumento(Documento documento);
    boolean existeDocumento(String equipamento, String placa) throws FalhaDeConexaoDbException;
    List<Documento> listarTodosDocumentos() throws FalhaDeConexaoDbException;
    List<Documento> listarDocumentosPorPlaca(String placa) throws FalhaDeConexaoDbException;
}

