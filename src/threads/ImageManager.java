package threads;

import java.awt.*;
import javax.swing.*;

import panels.HorsesPanel;
import panels.ImagePanel;
import windows.MainWindow;

public class ImageManager extends Thread {

	private int horseId;
	private MainWindow mWindow;
	
	public ImageManager(int horseId, 
		MainWindow mWindow) {
		
		this.horseId = horseId;
		this.mWindow = mWindow;
		
	}
	
	@Override
	public void run() {
		
		Runnable rbl = new Runnable() {
			
			@Override
			public void run() {
				
				int index = mWindow.jPanelMainView.getComponentCount();
				
				for (int i = 0; i < index; i++) {
					
					Component formComp = mWindow.jPanelMainView.getComponent(i);
					
					if (formComp instanceof HorsesPanel) {
						
						HorsesPanel jPanelHorses = (HorsesPanel) formComp;
						JTabbedPane jTabbedPane = jPanelHorses.jTabbedInfoView;						
						int tabIndex = jTabbedPane.getTabCount();
						
						for (int j = 0; j < tabIndex; j++) {
							
							Component tabComp = jTabbedPane.getComponent(j);
														
							if (tabComp instanceof ImagePanel) {
								
								ImagePanel jPanelImage = (ImagePanel) tabComp;																
								
								if (jPanelHorses.jTableHorseIndex.getRowCount() == 1) {
									
									jPanelImage.setSelectedImage(horseId);
									
								}
								
								if (jPanelHorses.jTableHorseIndex.getRowCount() > 1) {
									
									jPanelImage.jEditorPaneImage.setText(null);
									jPanelImage.setSelectedImage(horseId);
									
								}
								
								jPanelImage.jEditorPaneImage.revalidate();
								jPanelImage.jEditorPaneImage.updateUI();
								
							}
						}						
					}					
				}				
			}			
		};
		
		SwingUtilities.invokeLater(rbl);
		
	}	
}
