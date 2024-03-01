package org.example.bzreboques;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DocumentosDAO implements IDocumentosDAO{
    private static IDocumentosDAO instance;
    public DocumentosDAO(){

    }

    public static IDocumentosDAO getInstance(){
        if(instance == null){
            instance = new DocumentosDAO();
        }
        return instance;
    }

    @Override
    public void inserir(Documento documento) throws FalhaDeConexaoDbException, DocumentoInseridoComSucessoException {
        try (Connection conn = ConnectionFactory.getConnection()){
            DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            String sql = "INSERT INTO documentos (nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, anoFabricacao, anoModelo, foto1, foto2, dataHora) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            Application app = new Application();

            int index = documento.getFotos().get(0).indexOf(".");
            String ft1Extensao = documento.getFotos().get(0).substring(index + 1);
            String foto1Copia = String.format("%s/%s-%s-ft1-%s.%s", app.getServidor().getNuvem().getCaminhoDaPasta(), documento.getEquipamento().toLowerCase(), documento.getPlaca().toLowerCase(), documento.getDataHora().format(formatoDataHora), ft1Extensao);

            index = documento.getFotos().get(1).indexOf(".");
            String ft2Extensao = documento.getFotos().get(1).substring(index + 1);
            String foto2Copia = String.format("%s/%s-%s-ft2-%s.%s", app.getServidor().getNuvem().getCaminhoDaPasta(), documento.getEquipamento().toLowerCase(), documento.getPlaca().toLowerCase(), documento.getDataHora().format(formatoDataHora), ft2Extensao);

            ps.setString(1, documento.getNomeCliente().toUpperCase());
            ps.setString(2, documento.getDov().toUpperCase());
            ps.setString(3, documento.getEquipamento().toUpperCase());
            ps.setString(4, documento.getNumeracao().toUpperCase());
            ps.setString(5, documento.getProcesso().toUpperCase());
            ps.setString(6, documento.getPlaca().toUpperCase());
            ps.setString(7, documento.getRenavam().toUpperCase());
            ps.setString(8, documento.getCor().toUpperCase());
            ps.setString(9, documento.getMarcaModelo().toUpperCase());
            ps.setString(10, documento.getAnoFabricacao().toString());
            ps.setString(11, documento.getAnoModelo().toString());

            File caminho = new File(app.getServidor().getNuvem().getCaminhoDaPasta());
            if(caminho.exists() && caminho.isDirectory()){
                ps.setString(12, foto1Copia);
                ps.setString(13, foto2Copia);
                try {
                    copiarFoto(new File(documento.getFotos().get(0)), new File(foto1Copia));
                    copiarFoto(new File(documento.getFotos().get(1)), new File(foto2Copia));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                ps.setString(12, documento.getFotos().get(0));
                ps.setString(13, documento.getFotos().get(1));
            }

            formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            ps.setString(14, documento.getDataHora().format(formatoDataHora));
            ps.executeUpdate();

            throw new DocumentoInseridoComSucessoException();

        } catch (SQLException e) {
            throw new FalhaDeConexaoDbException();
        }
    }

    @Override
    public void atualizar(Documento documento, String nomeCliente, String dov, String equipamento, String numeracao, String processo, String placa, String renavam, String cor, String marcaModelo, Year anoFabricacao, Year anoModelo, String foto1, String foto2, LocalDateTime dataHora) throws FalhaDeConexaoDbException, DocumentoAtualizadoComSucessoException {
        try (Connection conn = ConnectionFactory.getConnection()){
            Application app = new Application();
            DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            DateTimeFormatter formatoDataHoraBancoDeDados = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

            String sql = "UPDATE documentos SET nomeCliente = ?, dov = ?, equipamento = ?, numeracao = ?, processo = ?, placa = ?, renavam = ?, cor = ?, marcaModelo = ?, anoFabricacao = ?, anoModelo = ?, foto1 = ?, foto2 = ?, dataHora = ? WHERE equipamento  = ? AND placa  = ? AND dataHora = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            int index = foto1.indexOf(".");
            String ft1Extensao = foto1.substring(index + 1);
            String foto1Copia = String.format("%s/%s-%s-ft1-%s.%s", app.getServidor().getNuvem().getCaminhoDaPasta(), equipamento.toLowerCase(), documento.getPlaca().toLowerCase(), documento.getDataHora().format(formatoDataHora), ft1Extensao);

            index = foto2.indexOf(".");
            String ft2Extensao = foto2.substring(index + 1);
            String foto2Copia = String.format("%s/%s-%s-ft2-%s.%s", app.getServidor().getNuvem().getCaminhoDaPasta(), equipamento.toLowerCase(), documento.getPlaca().toLowerCase(), documento.getDataHora().format(formatoDataHora), ft2Extensao);

            ps.setString(1, nomeCliente.toUpperCase());
            ps.setString(2, dov.toUpperCase());
            ps.setString(3, equipamento.toUpperCase());
            ps.setString(4, numeracao.toUpperCase());
            ps.setString(5, processo.toUpperCase());
            ps.setString(6, placa.toUpperCase());
            ps.setString(7, renavam.toUpperCase());
            ps.setString(8, cor.toUpperCase());
            ps.setString(9, marcaModelo.toUpperCase());
            ps.setString(10, anoFabricacao.toString());
            ps.setString(11, anoModelo.toString());

            File caminho = new File(app.getServidor().getNuvem().getCaminhoDaPasta());
            if(caminho.exists() && caminho.isDirectory() &&
                    ((!documento.getFotos().get(0).equals(foto1) &&
                            !documento.getFotos().get(1).equals(foto2)) ||
                            (!documento.getFotos().get(0).equals(foto1Copia) &&
                                    !documento.getFotos().get(1).equals(foto2Copia)))){
                ps.setString(12, foto1Copia);
                ps.setString(13, foto2Copia);
                try {
                    copiarFoto(new File(foto1), new File(foto1Copia));
                    copiarFoto(new File(foto2), new File(foto2Copia));
                    deletarFoto(documento.getFotos().get(0));
                    deletarFoto(documento.getFotos().get(1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                ps.setString(12, foto1);
                ps.setString(13, foto2);
            }

            ps.setString(14, dataHora.format(formatoDataHoraBancoDeDados));
            ps.setString(15, documento.getEquipamento());
            ps.setString(16, documento.getPlaca());
            ps.setString(17, dataHora.format(formatoDataHoraBancoDeDados));
            ps.executeUpdate();

            throw new DocumentoAtualizadoComSucessoException();

        } catch (SQLException e) {
            throw new FalhaDeConexaoDbException();
        }
    }

    @Override
    public void deletar(Documento documento) throws FalhaDeConexaoDbException, DocumentoDeletadoComSucessoException {
        try (Connection conn = ConnectionFactory.getConnection()){
            DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            String sql = "DELETE FROM documentos WHERE equipamento  = ? AND placa  = ? AND dataHora = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, documento.getEquipamento());
            ps.setString(2, documento.getPlaca());
            ps.setString(3, documento.getDataHora().format(formatoDataHora));
            ps.executeUpdate();

            deletarFoto(documento.getFotos().get(0));
            deletarFoto(documento.getFotos().get(1));

            throw new DocumentoDeletadoComSucessoException();

        } catch (SQLException e) {
            throw new FalhaDeConexaoDbException();
        }
    }

    @Override
    public boolean existeDocumento(String equipamento, String placa) throws FalhaDeConexaoDbException {
        boolean existeDocumento = false;
        try (Connection conn = ConnectionFactory.getConnection()){
            String sql = "SELECT id, nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, anoFabricacao, anoModelo, foto1, foto2, dataHora FROM documentos WHERE equipamento LIKE ? AND placa LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, equipamento);
            ps.setString(2, placa);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next())
                count++;
            if(count > 0){
                existeDocumento = true;
            }
        } catch (SQLException e) {
            throw new FalhaDeConexaoDbException();
        }
        return existeDocumento;
    }

    @Override
    public List<Documento> listarTodos() throws FalhaDeConexaoDbException {
        List<Documento> listaDeDocumentos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()){
            DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            String sql = "SELECT id, nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, anoFabricacao, anoModelo, foto1, foto2, dataHora FROM documentos";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String nomeCliente = rs.getString("nomeCliente");
                String dov = rs.getString("dov");
                String equipamento = rs.getString("equipamento");
                String numeracao = rs.getString("numeracao");
                String processo = rs.getString("processo");
                String placa = rs.getString("placa");
                String renavam = rs.getString("renavam");
                String cor = rs.getString("cor");
                String marcaModelo = rs.getString("marcaModelo");
                String anoFabricacao = rs.getString("anoFabricacao");
                String anoModelo = rs.getString("anoModelo");
                List<String> fotos = new ArrayList<>();
                fotos.add(rs.getString("foto1"));
                fotos.add(rs.getString("foto2"));
                LocalDateTime dataHora = LocalDateTime.parse(rs.getString("dataHora"), formatoDataHora);
                listaDeDocumentos.add(new Documento(nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, Year.of(Integer.parseInt(anoFabricacao)), Year.of(Integer.parseInt(anoModelo)), fotos, dataHora));
            }

        } catch (SQLException e) {
            throw new FalhaDeConexaoDbException();
        }
        return listaDeDocumentos;
    }

    @Override
    public List<Documento> listarPorPlaca(String plc) throws FalhaDeConexaoDbException {
        List<Documento> listaDeDocumentosPorPlaca = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()){
            DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            String sql = "SELECT id, nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, anoFabricacao, anoModelo, foto1, foto2, dataHora FROM documentos WHERE placa LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, plc);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String nomeCliente = rs.getString("nomeCliente");
                String dov = rs.getString("dov");
                String equipamento = rs.getString("equipamento");
                String numeracao = rs.getString("numeracao");
                String processo = rs.getString("processo");
                String placa = rs.getString("placa");
                String renavam = rs.getString("renavam");
                String cor = rs.getString("cor");
                String marcaModelo = rs.getString("marcaModelo");
                String anoFabricacao = rs.getString("anoFabricacao");
                String anoModelo = rs.getString("anoModelo");
                List<String> fotos = new ArrayList<>();
                fotos.add(rs.getString("foto1"));
                fotos.add(rs.getString("foto2"));
                LocalDateTime dataHora = LocalDateTime.parse(rs.getString("dataHora"), formatoDataHora);
                listaDeDocumentosPorPlaca.add(new Documento(nomeCliente, dov, equipamento, numeracao, processo, placa, renavam, cor, marcaModelo, Year.of(Integer.parseInt(anoFabricacao)), Year.of(Integer.parseInt(anoModelo)), fotos, dataHora));
            }

        } catch (SQLException e) {
            throw new FalhaDeConexaoDbException();
        }
        return listaDeDocumentosPorPlaca;
    }

    public static void copiarFoto(File source, File destination) throws IOException {
        if (destination.exists()) destination.delete();

        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;

        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen())
                sourceChannel.close();
            if (destinationChannel != null && destinationChannel.isOpen())
                destinationChannel.close();
        }
    }

    public static void deletarFoto(String foto) {
        Application app = new Application();
        File caminho = new File(app.getServidor().getNuvem().getCaminhoDaPasta());
        int index = foto.indexOf("backup/");
        foto = foto.substring(index + 7);
        if(caminho.exists() && caminho.isDirectory()){
            File[] arquivos = caminho.listFiles();
            if(arquivos != null){
                for(File arquivo : arquivos){
                    if(arquivo.getName().contains(foto)){
                        arquivo.delete();
                        break;
                    }
                }
            }
        }
    }

}
