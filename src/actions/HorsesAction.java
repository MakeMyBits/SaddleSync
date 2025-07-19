package actions;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Horse;
import panels.HorsesPanel;
import windows.MainWindow;

import java.awt.event.ActionEvent;

public class HorsesAction extends AbstractAction {

	private MainWindow mWindow;
	
	public HorsesAction(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		super.putValue(Action.NAME, "Horses");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		java.util.List<Horse> list = mWindow.horsesDao.getAllHorses();
		fillTable(list);
		
		mWindow.showPanel("horses");
		
	}	
	
	private void fillTable(java.util.List<Horse> list) {
		
		int compIndex = mWindow.jPanelMainView.getComponentCount();
		
		for (int i = 0; i < compIndex; i++) {
			
			Component comp = mWindow.jPanelMainView.getComponent(i);
			
			if (comp instanceof HorsesPanel) {
				
				HorsesPanel panel = (HorsesPanel) comp;
				DefaultTableModel model = 
					(DefaultTableModel) panel.jTableHorseIndex.getModel();
				
				model.setRowCount(0);
				
				for (int j = 0; j < list.size(); j++) {
					
					Horse horse = list.get(j);
					String name = horse.getName();
					String breed = horse.getBreed();
					int age = horse.getAge();
					String box = horse.getBox();
					String gender = horse.getGender();
					
					Object[] rowData = new Object[] {
							
						name,breed,age,box,gender
							
					};					
					
					model.addRow(rowData);
					model.fireTableDataChanged();
					
				}
			}
		}		
	}	
}
