package org.example.bzreboques;

import java.io.*;

public class ManipulacaoCloud {
    public static void salvarConfiguracoesCloud(Nuvem nuvem) throws ConfiguracoesCloudSalvasComSucessoException, FalhaDeSalvamentoDasConfiguracoesDeCloudException {
        try {
            FileOutputStream arquivoBinario = new FileOutputStream("src/main/resources/org/example/bzreboques/cloud.bin");
            ObjectOutputStream objetoSaida = new ObjectOutputStream(arquivoBinario);
            objetoSaida.writeObject(nuvem);
            objetoSaida.close();
            arquivoBinario.close();

            throw new ConfiguracoesCloudSalvasComSucessoException();

        } catch (IOException e) {
            throw new FalhaDeSalvamentoDasConfiguracoesDeCloudException();
        }
    }

    public static Nuvem carregarConfiguracoesCloud() throws FalhaDeSalvamentoDasConfiguracoesDeCloudException {
        Nuvem nuvem = null;
        try {
            FileInputStream arquivoBinario = new FileInputStream("src/main/resources/org/example/bzreboques/cloud.bin");
            ObjectInputStream objetoEntrada = new ObjectInputStream(arquivoBinario);
            nuvem = (Nuvem) objetoEntrada.readObject();
            objetoEntrada.close();
            arquivoBinario.close();
        } catch (IOException e) {
            throw new FalhaDeSalvamentoDasConfiguracoesDeCloudException();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return nuvem;
    }

}
