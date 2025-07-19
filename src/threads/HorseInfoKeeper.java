package threads;

import java.awt.Component;
import java.sql.*;
import java.time.LocalDate;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dao.Database;
import model.Horse;
import panels.HomePanel;
import panels.HorsesPanel;
import windows.AddHorseDialog;

public class HorseInfoKeeper extends Thread {

    private Horse horse;
    private AddHorseDialog dialog;

    public HorseInfoKeeper(Horse horse, AddHorseDialog dialog) {
        
    	this.horse = horse;
        this.dialog = dialog;
    
    }

    @Override
    public void run() {

    	int horseId = getHorseId();
        
        if (horseId > 0) {
        
        	insertImage(horseId);
        
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            	dialog.dispose();
            	doUpdatePanels();
            
            }
        });
    }

    private int getHorseId() {
        
    	int id = -1;
        
    	Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            
        	Database.loadDriver();

            conn = DriverManager.getConnection(Database.DB_URL);
            
            String sql = "INSERT INTO horses (name, breed, age, " + 
            "box, gender, comments) VALUES (?, ?, ?, ?, ?, ?)";

            stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stat.setString(1, horse.getName());
            stat.setString(2, horse.getBreed());
            stat.setInt(3, horse.getAge());
            stat.setString(4, horse.getBox());
            stat.setString(5, horse.getGender());
            stat.setString(6, horse.getComments());

            stat.executeUpdate();

            rs = stat.getGeneratedKeys();
            
            if (rs.next()) {
            
            	id = rs.getInt(1);
            
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (stat != null) stat.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return id;
    }
    
    private void insertImage(int horseId) {
    	
    	try {
    		
    	String sql = "INSERT INTO HorseImage " + 
    	"(horse_id,image_path) VALUES (?,?)";
    	
    	String imagePath = dialog.jPanelImage.jTextFieldPath.getText().trim();
    	
    	Database.loadDriver();
    	Connection conn = DriverManager.getConnection(Database.DB_URL);

    	PreparedStatement stat = conn.prepareStatement(sql);
    	stat.setInt(1, horseId);
    	stat.setString(2, imagePath);
    	stat.executeUpdate();
    	    	
    	stat.close();
    	conn.close();
    	
    	} catch (SQLException ex) {
            ex.printStackTrace();
    	}    	
    }
    
    private void doUpdatePanels() {
		
		int index = dialog.mWindow.jPanelMainView.getComponentCount();
		
		for (int i = 0; i < index; i++) {
			
			Component comp = dialog.mWindow.jPanelMainView.getComponent(i);
			
			if (comp instanceof HomePanel) {
				
				HomePanel panel = (HomePanel) comp;
				
				panel.jEditorPaneOverview.setText(null);
				panel.updateDashboard();
				
			}
			
			if (comp instanceof HorsesPanel) {
				
				HorsesPanel panel = (HorsesPanel) comp;
				
				DefaultTableModel model = 
					(DefaultTableModel) panel.jTableHorseIndex.getModel();
				
				model.setRowCount(0);				
				panel.configJTable();
				
			}			
		}		
	}
}
