package actions;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Horse;
import panels.CommentsPanel;
import panels.HorseDetailsPanel;
import panels.HorsesPanel;
import windows.MainWindow;

import java.awt.event.ActionEvent;

public class DeleteHorseAction extends AbstractAction {

	private MainWindow mWindow;
	
	public DeleteHorseAction(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		super.putValue(Action.NAME, "Delete horse");
		super.putValue("enabled", Boolean.FALSE);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		super.putValue("enabled", Boolean.FALSE);
		
		Component targetComp = mWindow.jPanelMainView.getComponent(1);
		int selectedRow = ((HorsesPanel) targetComp).jTableHorseIndex.getSelectedRow();
		if (selectedRow == -1) return;
		
		String title = "Delete selected horse";
		String message = "Do you wish to delete the selected horse?";
		
		int option = JOptionPane.showConfirmDialog(mWindow, 
		message, title, JOptionPane.YES_NO_OPTION);
		
		if (option == JOptionPane.YES_OPTION) {
			
			int compIndex = mWindow.jPanelMainView.getComponentCount();
			
			for (int i = 0; i < compIndex; i++) {
				
				Component comp = mWindow.jPanelMainView.getComponent(i);
								
				if (comp instanceof HorsesPanel) {
					
					java.util.List<Horse> list = mWindow.horsesDao.getAllHorses();
					HorsesPanel panel = (HorsesPanel) comp;
					
					int row = panel.jTableHorseIndex.getSelectedRow();
					if (row == -1) return;
					
					Horse horse = list.get(row);
					int id = horse.getId();
										
					this.mWindow.horsesDao.deleteHorse(id);
					list.remove(horse);
										
					DefaultTableModel model = 
						(DefaultTableModel) panel.jTableHorseIndex.getModel();
					
					model.setRowCount(0);
					panel.configJTable();
					model.fireTableDataChanged();
					
					int tabIndex = panel.jTabbedInfoView.getTabCount();
					
					for (int j = 0; j < tabIndex; j++) {
						
						Component tabComp = panel.jTabbedInfoView.getComponent(j);
						
						if (tabComp instanceof JScrollPane) {
							
							JScrollPane scrollPane = (JScrollPane) tabComp;
							Component viewComp = scrollPane.getViewport().getView();
							
							if (viewComp instanceof HorseDetailsPanel) {
								
								HorseDetailsPanel jPanelHorseDetails = (HorseDetailsPanel) viewComp;
								jPanelHorseDetails.resetComponents();
								
							}
							
							if (tabComp instanceof CommentsPanel) {
								
								CommentsPanel jPanelComments = (CommentsPanel) tabComp;
								jPanelComments.jTextAreaComments.setText(null);
								
							}
						}
					}					
				}				
			}			
		}		
		
		super.putValue("enabled", Boolean.TRUE);
		
	}	
}
