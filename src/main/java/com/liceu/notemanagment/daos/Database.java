package com.liceu.notemanagment.daos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    static Connection connection;

    static Connection getConnection() {

        try {
            //jdbc:sqlite:C:/sqlite/db/chinook.db
            //"jdbc:sqlite:D:\\testdb.db"
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:E:\\Fp informatica\\CFGS 2n any REP\\PracticasEntornServidor\\NotesManagment\\databaseManagement.db";
            if (connection == null) {
                connection = DriverManager.getConnection(url);
            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

