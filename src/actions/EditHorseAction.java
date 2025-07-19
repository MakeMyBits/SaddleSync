package actions;

import java.awt.*;
import javax.swing.*;

import model.Horse;
import panels.HorsesPanel;
import windows.EditHorseDialog;
import windows.MainWindow;

import java.awt.event.ActionEvent;

public class EditHorseAction extends AbstractAction {

	private MainWindow mWindow;
	
	public EditHorseAction(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		super.putValue(Action.NAME, "Edit horse");
		super.putValue("enabled", Boolean.FALSE);		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		super.putValue("enabled", Boolean.FALSE);
		
		java.util.List<Horse> list = mWindow.horsesDao.getAllHorses();
		int compIndex = mWindow.jPanelMainView.getComponentCount();
		
		for (int i = 0; i < compIndex; i++) {
			
			Component formComp = mWindow.jPanelMainView.getComponent(i);
			
			if (formComp instanceof HorsesPanel) {
				
				HorsesPanel panel = (HorsesPanel) formComp;
				
				int row = panel.jTableHorseIndex.getSelectedRow();				
				if (row == -1) return;
				
				Horse horse = list.get(row);
				
				if (horse != null) {
					
					EditHorseDialog dialog = 
						new EditHorseDialog(horse,mWindow);
				
					dialog.setVisible(true);
					super.putValue("enabled", Boolean.TRUE);
				
				}
				
				else if (horse == null) {
					
					super.putValue("enabled", Boolean.TRUE);
					
				}
				
			}			
		}				
	}	
}
