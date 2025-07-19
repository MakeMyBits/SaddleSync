package actions;

import java.awt.*;
import javax.swing.*;

import windows.AddHorseDialog;
import windows.MainWindow;

import java.awt.event.ActionEvent;

public class AddHorseAction extends AbstractAction {

	private MainWindow mWindow;
	
	public AddHorseAction(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		super.putValue(Action.NAME, "Add horse");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		super.putValue("enabled", Boolean.FALSE);
		
		AddHorseDialog dialog = new AddHorseDialog(mWindow);
		dialog.setVisible(true);
		
		super.putValue("enabled", Boolean.TRUE);
		
		
	}	
}
