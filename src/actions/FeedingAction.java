package actions;

import java.awt.*;
import javax.swing.*;

import windows.MainWindow;

import java.awt.event.ActionEvent;

public class FeedingAction extends AbstractAction {

	private MainWindow mWindow;
	
	public FeedingAction(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		super.putValue(Action.NAME, "Feeding");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		mWindow.showPanel("feeding");
		
	}	
}
