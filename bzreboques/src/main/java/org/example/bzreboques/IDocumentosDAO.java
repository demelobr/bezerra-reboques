package org.example.bzreboques;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public interface IDocumentosDAO {
    void inserir(Documento documento) throws FalhaDeConexaoDbException, DocumentoInseridoComSucessoException;
    void atualizar(Documento documento, String nomeCliente, String dov, String equipamento, String numeracao, String processo, String placa, String renavam, String cor, String marcaModelo, Year anoFabricacao, Year anoModelo, String foto1, String foto2, LocalDateTime dataHora) throws FalhaDeConexaoDbException, DocumentoAtualizadoComSucessoException;
    void deletar(Documento documento) throws FalhaDeConexaoDbException, DocumentoDeletadoComSucessoException;
    boolean existeDocumento(String equipamento, String placa) throws FalhaDeConexaoDbException;
    List<Documento> listarTodos() throws FalhaDeConexaoDbException;
    List<Documento> listarPorPlaca(String placa) throws FalhaDeConexaoDbException;
}
