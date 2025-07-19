package threads;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import panels.FeedingPanel;
import panels.HorsesPanel;

import java.awt.*;
import windows.MainWindow;

public class HorseInfoLoader extends Thread {

	private MainWindow mWindow;
	
	public HorseInfoLoader(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
	}
	
	@Override
	public void run() {
		
		Runnable rbl = new Runnable() {
			
			@Override
			public void run() {
				
				int compIndex = mWindow.jPanelMainView.getComponentCount();
				
				for (int i = 0; i < compIndex; i++) {
					
					Component cardComp = mWindow.jPanelMainView.getComponent(i);
										
					if (cardComp instanceof JScrollPane) {
						
						JScrollPane scrollPane = (JScrollPane) cardComp;
						Component viewComp = scrollPane.getViewport().getView();
						
						if (viewComp instanceof FeedingPanel) {
							
							FeedingPanel panel = (FeedingPanel) viewComp;
													
							try {
							panel.loadHorses();
							} catch (java.sql.SQLException ex) { ex.printStackTrace(); }
							
						}					
					}
					
					
					if (cardComp instanceof HorsesPanel) {
						
						HorsesPanel panel = (HorsesPanel) cardComp;
						
						DefaultTableModel model = 
							(DefaultTableModel) panel.jTableHorseIndex.getModel();
						
						model.setRowCount(0);
						panel.configJTable();
												
						if (panel.jTableHorseIndex.getRowCount() != 0) {
							
							panel.jTableHorseIndex.setRowSelectionInterval(0,0);
							
						}						
					}					
				}				
			}			
		};
		
		SwingUtilities.invokeLater(rbl);
		
	}	
}
