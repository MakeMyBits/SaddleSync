package actions;

import java.awt.*;
import javax.swing.*;

import windows.MainWindow;

import java.awt.event.ActionEvent;

public class HomeAction extends AbstractAction {

	private MainWindow mWindow;
	
	public HomeAction(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		super.putValue(Action.NAME, "Home");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		mWindow.showPanel("home");
		
	}	
}
