package org.example.bzreboques;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

public class ControladorDocumentos implements IControladorDocumentos {
    private static IControladorDocumentos instance;
    private IDocumentosDAO repositorioDocumentos;

    public ControladorDocumentos(){
        this.repositorioDocumentos = DocumentosDAO.getInstance();
    }

    public static IControladorDocumentos getInstance(){
        if(instance == null){
            instance = new ControladorDocumentos();
        }
        return instance;
    }

    @Override
    public void inserirDocumento(Documento documento) throws FalhaDeConexaoDbException, DocumentoInseridoComSucessoException, DocumentoNuloException, DocumentoInvalidoException {
        if(documento != null){
            if(this.checarDadosDoDocumento(documento)){
                repositorioDocumentos.inserir(documento);
            }else{
                throw new DocumentoInvalidoException();
            }
        }else{
            throw new DocumentoNuloException();
        }
    }

    @Override
    public void atualizarDocumento(Documento documento, String nomeCliente, String dov, String equipamento, String numeracao, String processo, String placa, String renavam, String cor, String marcaModelo, Year anoFabricacao, Year anoModelo, String foto1, String foto2, LocalDateTime dataHora) throws FalhaDeConexaoDbException, DocumentoAtualizadoComSucessoException, DocumentoNuloException, DocumentoNaoExisteException, DocumentoExisteException {
        if(documento != null){
            if(this.existeDocumento(documento.getEquipamento(), documento.getPlaca())){
                if(nomeCliente.isEmpty() || documento.getNomeCliente().equals(nomeCliente)){
                    nomeCliente = documento.getNomeCliente();
                }
                if(dov.isEmpty() || documento.getDov().equals(dov)){
                    dov = documento.getDov();
                }
                if(equipamento.isEmpty() || documento.getEquipamento().equals(equipamento)){
                    equipamento = documento.getEquipamento();
                }
                if(numeracao.isEmpty() || documento.getNumeracao().equals(numeracao)){
                    numeracao = documento.getNumeracao();
                }
                if(processo.isEmpty() || documento.getProcesso().equals(processo)){
                    processo = documento.getProcesso();
                }
                if(placa.isEmpty() || documento.getPlaca().equals(placa)){
                    placa = documento.getPlaca();
                }
                if(renavam.isEmpty() || documento.getRenavam().equals(renavam)){
                    renavam = documento.getRenavam();
                }
                if(cor.isEmpty() || documento.getCor().equals(cor)){
                    cor = documento.getCor();
                }
                if(marcaModelo.isEmpty() || documento.getMarcaModelo().equals(marcaModelo)){
                    marcaModelo = documento.getMarcaModelo();
                }
                if(anoFabricacao.isAfter(Year.now())){
                    anoFabricacao = documento.getAnoFabricacao();
                }
                if(anoModelo.isAfter(Year.now())){
                    anoModelo = documento.getAnoModelo();
                }
                if(foto1.isEmpty() || documento.getFotos().get(0).equals(foto1)){
                    foto1 = documento.getFotos().get(0);
                }
                if(foto2.isEmpty() || documento.getFotos().get(1).equals(foto2)){
                    foto2 = documento.getFotos().get(1);
                }
                repositorioDocumentos.atualizar(documento, nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, anoFabricacao, anoModelo, foto1, foto2, dataHora);
                throw new DocumentoAtualizadoComSucessoException();
            }else{
                throw new DocumentoNaoExisteException();
            }
        }else{
            throw new DocumentoNuloException();
        }
    }

    @Override
    public void deletarDocumento(Documento documento) throws DocumentoDeletadoComSucessoException, FalhaDeConexaoDbException, DocumentoNaoExisteException, DocumentoNuloException {
        if(documento != null){
            if(this.existeDocumento(documento.getEquipamento(), documento.getPlaca())){
                repositorioDocumentos.deletar(documento);
            }else{
                throw new DocumentoNaoExisteException();
            }
        }else{
            throw new DocumentoNuloException();
        }
    }

    @Override
    public boolean checarDadosDoDocumento(Documento documento) {
        boolean documentoValido = false;
        if(!documento.getNomeCliente().isEmpty() && !documento.getDov().isEmpty() && !documento.getEquipamento().isEmpty() &&
                !documento.getNumeracao().isEmpty() && !documento.getProcesso().isEmpty() && !documento.getPlaca().isEmpty() &&
                !documento.getRenavam().isEmpty() && !documento.getCor().isEmpty() && !documento.getMarcaModelo().isEmpty() &&
                documento.getAnoFabricacao() != null && documento.getAnoModelo() != null && documento.getFotos().size() == 2){
            if(documento.getEquipamento().equals("MOTOR") || documento.getEquipamento().equals("CHASSI")){
                if(documento.getPlaca().length() == 7){
                    if(!documento.getDataHora().isAfter(LocalDateTime.now())){
                        documentoValido = true;
                    }
                }
            }
        }
        return documentoValido;
    }

    @Override
    public boolean existeDocumento(String equipamento, String placa) throws FalhaDeConexaoDbException {
        return repositorioDocumentos.existeDocumento(equipamento, placa);
    }

    @Override
    public List<Documento> listarTodosDocumentos() throws FalhaDeConexaoDbException {
        return repositorioDocumentos.listarTodos();
    }

    @Override
    public List<Documento> listarDocumentosPorPlaca(String placa) throws FalhaDeConexaoDbException {
        return repositorioDocumentos.listarPorPlaca(placa);
    }

}
