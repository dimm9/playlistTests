package org.example;

import auth.AccountManager;
import database.DatabaseConnection;

import java.nio.file.Path;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect(Path.of("database.db"));
        AccountManager manager = new AccountManager(dbConnection);
        manager.init();
        System.out.println(manager.register("bob", "111"));
        System.out.println(manager.authenticate("bob", "111"));
        dbConnection.disconnect();
    }
}