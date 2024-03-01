package org.example.bzreboques;

public class FalhaDeConexaoDbException extends Exception{
    public FalhaDeConexaoDbException(){
        super("Falha na conexão com o db!");
    }
}
