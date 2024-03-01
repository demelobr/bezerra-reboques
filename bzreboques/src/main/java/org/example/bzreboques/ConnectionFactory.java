package org.example.bzreboques;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws FalhaDeConexaoDbException {
        try {
            Servidor servidor = Servidor.getInstance();
            Settings settings = servidor.getConfiguracoes();
            return DriverManager.getConnection(settings.getUrlDB(), settings.getUserDB(), settings.getPasswordDB());
        } catch (SQLException e) {
            throw new FalhaDeConexaoDbException();
        }
    }
}
