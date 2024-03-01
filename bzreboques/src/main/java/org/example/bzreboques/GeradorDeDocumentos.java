package org.example.bzreboques;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GeradorDeDocumentos {
    public GeradorDeDocumentos(){}
    public void geradorDoDocumento(List<Documento> lista) throws JRException {
        Documento documento = lista.get(0);
        String logo = "src/main/resources/org/example/bzreboques/logo.png";
        String qrcode = "src/main/resources/org/example/bzreboques/qrcode.png";
        String nomeCliente = documento.getNomeCliente().toUpperCase();
        String dov = documento.getDov().toUpperCase();
        String equipamento = documento.getEquipamento().toUpperCase();
        String numeracao = documento.getNumeracao().toUpperCase();
        String processo = documento.getProcesso().toUpperCase();
        String placa = documento.getPlaca().toUpperCase();
        String renavam = documento.getRenavam().toUpperCase();
        String cor = documento.getCor().toUpperCase();
        String marcaModelo = documento.getMarcaModelo().toUpperCase();
        String anoFabricacao = documento.getAnoFabricacao().toString().toUpperCase();
        String anoModelo = documento.getAnoModelo().toString().toUpperCase();
        String foto1 = documento.getFotos().get(0);
        String foto2 = documento.getFotos().get(1);
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        String dataHora = documento.getDataHora().format(formatoDataHora);
        Date data = new Date();
        SimpleDateFormat formatoDataDoDia = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        String dataDoDia = formatoDataDoDia.format(data).toUpperCase();

        Application app = new Application();
        String gestorDetran = app.getServidor().getConfiguracoes().getResponsavelDetran().toUpperCase();
        String razaoSocial = app.getServidor().getConfiguracoes().getRazaoSocial().toUpperCase();
        String cnpj = app.getServidor().getConfiguracoes().getCnpj().toUpperCase();
        String inscricaoMunicipal = app.getServidor().getConfiguracoes().getInscricaoMunicipal().toUpperCase();
        String endereco = app.getServidor().getConfiguracoes().getEndereco().toUpperCase();
        String telefone = app.getServidor().getConfiguracoes().getTelefone().toUpperCase();

        JasperDesign jasperDesign = null;
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        InputStream entradaArquivo = null;

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("LOGO", logo);
        parameters.put("QRCODE", qrcode);
        parameters.put("GESTOR_DETRAN",gestorDetran);
        parameters.put("NOME_CLIENTE", nomeCliente);
        parameters.put("DOV", dov);
        parameters.put("EQUIPAMENTO", equipamento);
        parameters.put("NUMERACAO", numeracao);
        parameters.put("PROCESSO",processo);
        parameters.put("RENAVAM",renavam);
        parameters.put("PLACA",placa);
        parameters.put("COR",cor);
        parameters.put("MARCA_MODELO",marcaModelo);
        parameters.put("ANO_FABRICACAO",anoFabricacao);
        parameters.put("ANO_MODELO",anoModelo);
        parameters.put("FOTO_1", foto1);
        parameters.put("FOTO_2", foto2);
        parameters.put("RAZAO_SOCIAL", razaoSocial);
        parameters.put("CNPJ",cnpj);
        parameters.put("INSCRICAO_MUNICIPAL", inscricaoMunicipal);
        parameters.put("ENDERECO", endereco);
        parameters.put("TELEFONE", telefone);
        parameters.put("DATA_DO_DIA", dataDoDia);

        String pathReport = "src/main/resources/org/example/bzreboques/documento.jrxml";

        try{
            entradaArquivo = new FileInputStream(pathReport);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try{
            jasperDesign = JRXmlLoader.load(entradaArquivo);
        } catch (JRException e) {
            e.printStackTrace();
        }

        try{
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
        } catch (JRException e) {
            e.printStackTrace();
        }

        try{
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        } catch (JRException e) {
            e.printStackTrace();
        }

        JasperViewer.viewReport(jasperPrint, false);

    }
}
