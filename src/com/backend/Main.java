package com.backend;

import org.apache.log4j.Logger;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        // El nombre de la clase al que corresponde el log ⬇︎
        Logger logger = Logger.getLogger(Main.class);

        Connection connection = null;

        String create = "DROP TABLE IF EXISTS ANIMALES; CREATE TABLE ANIMALES(ID INT AUTO_INCREMENT PRIMARY KEY, NOMBRE VARCHAR(20) NOT NULL, TIPO VARCHAR(20) NOT NULL)";
        String insert = "INSERT INTO ANIMALES(NOMBRE, TIPO) VALUES ('Firulais', 'Perro'), ('Lola','Vaca'), ('Homero','Perro'), ('Pepe','Sapo'), ('Tuki','Loro')";
        String select = "SELECT * FROM ANIMALES";
        try{
            // establecer conexion
           connection = getConnection();
           // crear la tabla
            Statement statement = connection.createStatement();
            statement.execute(create);
            // insertar los valores
            statement.execute(insert);
            //obtener resultados
            ResultSet resultSet = statement.executeQuery(select);

            while (resultSet.next()){
                logger.info("Animal: " + resultSet.getInt(1) + " - " + resultSet.getString("tipo"));

            }
            //Eliminar registro
            statement.execute("DELETE FROM ANIMALES WHERE ID= 1 ");

            //Seleccionar todos
            resultSet = statement.executeQuery(select);
            while (resultSet.next()){
                logger.info("Animal: " + resultSet.getInt(1) + " - " + resultSet.getString("tipo"));

            }

        }catch (Exception exception){
            exception.printStackTrace();
            logger.error(exception.getClass() + " : " + exception.getMessage());
        }
        finally {
            try{
                connection.close();
            }catch (Exception exception){
                logger.error(exception.getMessage());
            }
        }
    }

    //Disponibilizar el metodo para la conexión
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Indicar drive a usar, su ruta.
        Class.forName("org.h2.Driver");
        //Url base de datos
        return DriverManager.getConnection("jdbc:h2:~/clase8c1b2", "sa", "sa");

    }
}