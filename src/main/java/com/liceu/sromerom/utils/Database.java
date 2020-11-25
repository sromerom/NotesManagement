package com.liceu.sromerom.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Connection connection;

    public static Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            //#### Windows ####//
            String url = "jdbc:sqlite:E:\\Fp informatica\\CFGS 2n any REP\\PracticasEntornServidor\\NotesManagement\\databaseManagement.db";
            //#### Linux ####//
            //String url = "jdbc:sqlite:/home/superior/sromerom/Practiques Entorn Servidor/NotesManagment/databaseManagement.db";
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

