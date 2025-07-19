package panels;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import actions.AddHorseAction;
import actions.DeleteHorseAction;
import actions.EditHorseAction;
import actions.FeedingAction;
import actions.HomeAction;
import actions.HorsesAction;
import actions.SearchHorseAction;
import dao.Database;
import model.Horse;
import windows.AddHorseDialog;
import windows.EditHorseDialog;
import windows.MainWindow;

public class CommandPanel extends JPanel {

	private MainWindow mWindow;
	
	private String[] mainButtonText = new String[] {
			
		"Add","Edit","Delete",
		"Search","Home","Horses","Feeding"
			
	};
	
	private Action[] actionList;
		
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	private ButtonGroup btnGroup;
	
	public CommandPanel(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		this.initialize();
		this.configJPanel();
		this.createLayout();
		
	}
	
	private void initialize() {
						
		this.actionList = new AbstractAction[] {
				
			new AddHorseAction(mWindow),
			new EditHorseAction(mWindow),
			new DeleteHorseAction(mWindow),
			new SearchHorseAction(mWindow),
			new HomeAction(mWindow),
			new HorsesAction(mWindow),
			new FeedingAction(mWindow)
				
		};
		
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
		this.btnGroup = new ButtonGroup();
		
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(gbl);
		
	}
	
	private void createLayout() {
		
		GridLayout layout = new GridLayout(0,mainButtonText.length,3,3);
		JPanel jPanelLeftLayout = new JPanel(layout);
		
		for (int i = 0; i < mainButtonText.length; i++) {
			
			String label = mainButtonText[i];
			
			Action action = actionList[i];
			JButton button = createJButton(90,33,action,label);
			
			this.btnGroup.add(button);			
			jPanelLeftLayout.add(button);
			
		}
		
	    gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,0,0);
        gbl.setConstraints(jPanelLeftLayout,  gbc);
        this.add(jPanelLeftLayout);
        
	}
		
	public void setEnabledAt(String label, 
		boolean mode) {
		
		Enumeration<AbstractButton> enr = btnGroup.getElements();
		
		while (enr.hasMoreElements()) {
			
			AbstractButton button = enr.nextElement();
			String text = button.getText();
			
			if (text.equals(label)) {
				
				button.setEnabled(mode);
				
			}			
		}		
	}
	
	private JButton createJButton(int width, 
		int height, Action action, String label) {
		
		Dimension dim = new Dimension(width,height);
		JButton jButtonComponent = new JButton();
		jButtonComponent.setMinimumSize(dim);
		jButtonComponent.setPreferredSize(dim);
		jButtonComponent.setMaximumSize(dim);
		jButtonComponent.setFocusable(false);
		
		action.putValue(Action.NAME, label);
		jButtonComponent.setAction(action);
				
		return jButtonComponent;
		
	}
}
