package threads;

import java.sql.*;

import java.awt.*;
import dao.Database;
import panels.HomePanel;
import windows.EditHorseDialog;

public class HorseInfoSecretary extends Thread {
	
	private EditHorseDialog jDialogEditHorse;
	
	public HorseInfoSecretary(EditHorseDialog jDialogEditHorse) {
		
		this.jDialogEditHorse = jDialogEditHorse;
		
	}
	
	@Override
	public void run() {
		
		int id = jDialogEditHorse.horse.getId();
		
		try {
		
		this.saveHorseInfo(id);
		this.saveCareNotes(id);
		this.updateDashboard();
		
		} catch (SQLException ex) { ex.printStackTrace(); }
	}
	
	private void saveHorseInfo(int id) throws SQLException {
		
		String name = jDialogEditHorse.jPanelHorseDetails.jTextFieldName.getText().trim();
		String breed = jDialogEditHorse.jPanelHorseDetails.jTextFieldBreed.getText().trim();
		int age = (int) jDialogEditHorse.jPanelHorseDetails.jSpinnerAge.getValue();
		String box = jDialogEditHorse.jPanelHorseDetails.jTextFieldBox.getText().trim();
		String gender = (String) jDialogEditHorse.jPanelHorseDetails.jComboBoxGender.getSelectedItem();
		String comments = jDialogEditHorse.jPanelComments.jTextAreaComments.getText();
		
		Database.loadDriver();
		
		Connection conn = DriverManager.getConnection(Database.DB_URL);
		
		String sql = "UPDATE horses SET name = ?,breed = ?,age = ?," + 
		"box = ?,gender = ?,comments = ? WHERE id = ?";
		
		PreparedStatement stat = conn.prepareStatement(sql);
		stat.setString(1, name);
		stat.setString(2, breed);
		stat.setInt(3, age);
		stat.setString(4, box);
		stat.setString(5, gender);
		stat.setString(6, comments);
		stat.setInt(7, id);
		
		stat.executeUpdate();
		stat.close();
		conn.close();
		
	}
	
	private void saveCareNotes(int id) throws SQLException {
		
		Database.loadDriver();
		Connection conn = DriverManager.getConnection(Database.DB_URL);
		
		String comments = 
			jDialogEditHorse.jPanelComments.jTextAreaComments.getText();
		
		String sql = "UPDATE CareNotes SET note = ? WHERE horse_id = ?";
		PreparedStatement stat = conn.prepareStatement(sql);
		
		stat.setString(1, comments);
		stat.setInt(2, id);
		stat.executeUpdate();
		stat.close();
		conn.close();
		
	}
	
	private void updateDashboard() {
		
		int index = jDialogEditHorse.mWindow.jPanelMainView.getComponentCount();
		
		for (int i = 0; i < index; i++) {
			
			Component comp = jDialogEditHorse.mWindow.jPanelMainView.getComponent(i);
			
			if (comp instanceof HomePanel) {
				
				HomePanel panel = (HomePanel) comp;
				
				panel.jEditorPaneOverview.setText(null);
				panel.updateDashboard();
				
			}			
		}		
	}
}
