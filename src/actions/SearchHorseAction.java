package actions;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Horse;
import panels.HorsesPanel;
import threads.ImageManager;
import windows.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchHorseAction extends AbstractAction {

	private MainWindow mWindow;
	
	public SearchHorseAction(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		super.putValue(Action.NAME, "Search horse");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		super.putValue("enabled", Boolean.FALSE);
		
		String searchName = JOptionPane.showInputDialog(mWindow, 
		"Enter name of horse");
		
		if (searchName == null) {
			
			super.putValue("enabled", Boolean.TRUE);
			
		}
		        
		if (searchName == null 
			|| searchName.trim().isEmpty()) return;
		        
		    java.util.List<Horse> horseList = new ArrayList<Horse>();
		    java.util.List<Horse> allHorses = mWindow.horsesDao.getAllHorses();
		        
		    for (int i = 0; i < allHorses.size(); i++) {
		        	
		    	Horse horse = allHorses.get(i);
		        		    	
		        if (horse.getName().equalsIgnoreCase(searchName.trim())) {
		        	
		        	int horseId = horse.getId();
		        	horseList.add(horse);
		        	
		        	Timer timer = new Timer(20, new ActionListener() {
		        		
		        		@Override
		        		public void actionPerformed(ActionEvent e) {
		        			
		        			ImageManager manager = 
		        				new ImageManager(horseId,mWindow);
				        	
		        			manager.start();
				        	
				        	((Timer) e.getSource()).stop();
		        			
		        		}		        		
		        	});
		        		
		        	timer.start();	        	
		        		        	
		        }        	
		    }
		        
		    this.fillTable(horseList);
		    
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
