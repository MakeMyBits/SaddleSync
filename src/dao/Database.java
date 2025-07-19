package dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

public abstract class Database {

    public static final String DB_URL = "jdbc:sqlite:saddlesync.db";
    private static Connection connection;

    public static void initializeDatabase() {
        
    	try {
        	
            connect();

            if (!tablesExist()) {
            	
                runSqlFile("main.sql"); 
                System.out.println("Database has been created.");
            
            } else {
                System.out.println("Tables already exists");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
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
                    sqlBuilder.setLength(0);  // Nollställ för nästa kommando
                }
            }

        } catch (Exception e) {
            System.err.println("Error at running of sql file:");
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
