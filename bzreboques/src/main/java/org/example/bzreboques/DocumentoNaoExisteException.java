package org.example.bzreboques;

public class DocumentoNaoExisteException extends Exception{
    public DocumentoNaoExisteException(){
        super("Documento não existe!");
    }
}
