package dao;

import model.Horse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorsesDao extends Database {

    // Lägg till en ny häst
	
    public boolean addHorse(Horse horse) {
    
    	String sql = "INSERT INTO Horses (name, breed, " + 
    	"age,box,gender,comments) VALUES (?, ?, ?, ?, ?,?)";

        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
        
        	pstmt.setString(1, horse.getName());
            pstmt.setString(2, horse.getBreed());
            pstmt.setInt(3, horse.getAge());
            pstmt.setString(4, horse.getBox());
            pstmt.setString(5, horse.getGender());
            pstmt.setString(6, horse.getComments());

            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            
        	System.err.println("Kunde inte lägga till häst: " + e.getMessage());
            return false;
        
        }
    }

    // Hämta alla hästar
    public List<Horse> getAllHorses() {
        
    	List<Horse> horses = new ArrayList<>();
        String sql = "SELECT * FROM Horses";
        
        try {
        
        Database.loadDriver();
        
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            
        	Horse horse = new Horse(rs.getInt("id"),
            rs.getString("name"),
            rs.getString("breed"),
            rs.getInt("age"),
            rs.getString("box"),
            rs.getString("gender"),
            rs.getString("comments"));
            	
            horses.add(horse);
            
        }

        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av hästar: " + e.getMessage());
        }

        return horses;
    
	}

    // Ta bort häst baserat på ID
    public boolean deleteHorse(int horseId) {
        
    	String sql = "DELETE FROM Horses WHERE id = ?";
    	
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, horseId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Kunde inte ta bort häst: " + e.getMessage());
            return false;
        }
    }
    
    public Horse getLatestHorse() {
     
    	Horse horse = null;
        
    	try {
        
    		Database.loadDriver();
            Connection conn = DriverManager.getConnection(Database.DB_URL);
            Statement stmt = conn.createStatement();
            // Hämta senaste hästen baserat på högst id
            ResultSet rs = stmt.executeQuery(
            
            		"SELECT id, name, breed, age, box, gender, comments FROM Horses ORDER BY id DESC LIMIT 1"
            
            );

            if (rs.next()) {
            
            	horse = new Horse();
                horse.setId(rs.getInt("id"));
                horse.setName(rs.getString("name"));
                horse.setBreed(rs.getString("breed"));
                horse.setAge(rs.getInt("age"));
                horse.setBox(rs.getString("box"));
                horse.setGender(rs.getString("gender"));
                horse.setComments(rs.getString("comments"));
            
            }

            rs.close();
            stmt.close();
            conn.close();
        
    	} catch (SQLException ex) {
            ex.printStackTrace();
        }
    	
        return horse;
    }

    
    public java.util.List<String> getAllHorseNames() {
        
    	java.util.List<String> names = new java.util.ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(Database.DB_URL);
            
        	Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM horses")) {
        
        	while (rs.next()) {
                names.add(rs.getString("name"));
            }
        	
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return names;
    
    }
    
    public int getTotalHorses() {
        int total = 0;
        try {
            Database.loadDriver();
            Connection conn = DriverManager.getConnection(Database.DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total_horses FROM Horses");
            if (rs.next()) {
                total = rs.getInt("total_horses");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public String getLatestHorseName() {
        
    	String name = "(none)";
        
    	try {
            		
    		Database.loadDriver();
        
    		Connection conn = DriverManager.getConnection(Database.DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM Horses " + 
            "ORDER BY id DESC LIMIT 1");
            
            if (rs.next()) {
            
            	name = rs.getString("name");
            
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}
