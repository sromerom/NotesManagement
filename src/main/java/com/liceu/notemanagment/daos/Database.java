package com.liceu.notemanagment.daos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Connection connection;

    static Connection getConnection() {

        try {
            //jdbc:sqlite:C:/sqlite/db/chinook.db
            //"jdbc:sqlite:D:\\testdb.db"
            Class.forName("org.sqlite.JDBC");
            //Windows
            //String url = "jdbc:sqlite:E:\\Fp informatica\\CFGS 2n any REP\\PracticasEntornServidor\\NotesManagment\\databaseManagement.db";
            //Linux
            String url = "jdbc:sqlite:/home/superior/sromerom/Practiques Entorn Servidor/NotesManagment/databaseManagement.db";
            if (connection == null) {
                connection = DriverManager.getConnection(url);
                connection.createStatement().execute("PRAGMA foreign_keys = ON");
                connection.createStatement().execute("PRAGMA encoding = 'UTF-16'");
            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

