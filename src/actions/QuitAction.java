package actions;

import java.awt.*;
import javax.swing.*;

import windows.MainWindow;

import java.awt.event.ActionEvent;

public class QuitAction extends AbstractAction {

	private MainWindow mWindow;
	
	public QuitAction(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		super.putValue(Action.NAME, "Exit");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		mWindow.dispose();
		System.exit(0);
		
	}	
}
