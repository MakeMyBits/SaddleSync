package engine;

import java.awt.SystemColor;
import javax.swing.UIManager;

import dao.Database;
import dao.HorsesDao;
import model.Horse;
import windows.MainWindow;

public class MainClass {
	
	public static void main(String[] args) {
		
		Database.initializeDatabase();
		
		UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.cancelButtonText", "Cancel");
        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.closeButtonText", "Close");
		
		UIManager.put("ComboBox.disabledText", SystemColor.textText);
		UIManager.put("ComboBox.disabledForeground", SystemColor.textText);
		UIManager.put("CheckBox.disabledText", SystemColor.textText);
		UIManager.put("CheckBox.disabledForeground", SystemColor.textText);
		UIManager.put("CheckBox.foreground", SystemColor.textText);
				
		MainWindow mWindow = new MainWindow("SaddleSync");
		mWindow.setVisible(true);
		
	}
}
