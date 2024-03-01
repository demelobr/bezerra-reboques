package org.example.bzreboques;

import java.io.*;

public class ManipulacaoDasConfiguracoes {
    public static void salvarConfiguracoes(Settings settings) throws ConfiguracoesSalvasComSucessoException, FalhaDeSalvamentoDasConfiguracoesException {
        try {
            FileOutputStream arquivoBinario = new FileOutputStream("src/main/resources/org/example/bzreboques/settings.bin");
            ObjectOutputStream objetoSaida = new ObjectOutputStream(arquivoBinario);
            objetoSaida.writeObject(settings);
            objetoSaida.close();
            arquivoBinario.close();

            throw new ConfiguracoesSalvasComSucessoException();

        } catch (IOException e) {
            throw new FalhaDeSalvamentoDasConfiguracoesException();
        }
    }

    public static Settings carregarConfiguracoes() throws FalhaDeCarregamentoDasConfiguracoesException {
        Settings settings = null;
        try {
            FileInputStream arquivoBinario = new FileInputStream("src/main/resources/org/example/bzreboques/settings.bin");
            ObjectInputStream objetoEntrada = new ObjectInputStream(arquivoBinario);
            settings = (Settings) objetoEntrada.readObject();
            objetoEntrada.close();
            arquivoBinario.close();
        } catch (IOException e) {
            throw new FalhaDeCarregamentoDasConfiguracoesException();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return settings;
    }

}
