package org.example.bzreboques;

public class DocumentoExisteException extends Exception{
    public DocumentoExisteException(){
        super("Documento já existe!");
    }
}
