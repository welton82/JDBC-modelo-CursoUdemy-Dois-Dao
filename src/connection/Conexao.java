package connection;

import exception.Excessao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    private static Connection cnn = null;

    private static Properties loadProperties(){
        Properties prop = new Properties();

        try(FileInputStream fs = new FileInputStream("coursejdbc.properties")){

            prop.load(fs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  prop;
    }

    public static Connection getConnection(){
        Properties prop = loadProperties();
        String url = prop.getProperty("url");
        try {
            cnn = DriverManager.getConnection(url,prop);

        } catch (SQLException e) {
            throw new Excessao("#ERRO DE CONEXÃO: " + e.getMessage());
        }
        return cnn;
    }
}
