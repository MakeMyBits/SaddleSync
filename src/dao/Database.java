package dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public abstract class Database {

    private static final Path DB_PATH;
    public static String DB_URL;
    private static Connection connection;

    static {
        Path userHome = Paths.get(System.getProperty("user.home"));
        DB_PATH = userHome.resolve("saddlesync").resolve("saddlesync.db");
        DB_URL = "jdbc:sqlite:" + DB_PATH.toString();
    }

    public static void initializeDatabase() {
        try {
            prepareDatabaseFile();
            connect();

            if (!tablesExist()) {
                runSqlFile("main.sql");
                System.out.println("Database has been created.");
            } else {
                System.out.println("Tables already exist.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void prepareDatabaseFile() throws Exception {
        Path parentDir = DB_PATH.getParent();
        if (!Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
    }

    public static void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        if (connection == null) {
            try {
                loadDriver();
                connection = DriverManager.getConnection(DB_URL);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void runSqlFile(String resourcePath) {
        try (
            InputStream is = Database.class.getClassLoader().getResourceAsStream(resourcePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Statement stmt = connection.createStatement()
        ) {
            if (is == null) {
                System.err.println("Could not locate sql file: " + resourcePath);
                return;
            }

            StringBuilder sqlBuilder = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("//")) continue;

                sqlBuilder.append(line).append(" ");
                if (line.endsWith(";")) {
                    String sql = sqlBuilder.toString();
                    stmt.execute(sql);
                    sqlBuilder.setLength(0);
                }
            }

        } catch (Exception e) {
            System.err.println("Error running sql file:");
            e.printStackTrace();
        }
    }

    private static boolean tablesExist() throws SQLException {
        String checkSql = "SELECT name FROM sqlite_master " +
                          "WHERE type='table' AND name='Horses';";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            return rs.next();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
