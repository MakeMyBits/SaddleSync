package panels;

import java.awt.*;
import javax.swing.*;

import components.BasicTextField;
import components.DaoComboBox;
import components.NumericSpinner;
import windows.MainWindow;

public class HorseDetailsPanel extends JPanel {

	private boolean mode;
	private MainWindow mWindow;
	
	private JLabel jLabelName;
	private JLabel jLabelBreed;
	private JLabel jLabelAge;
	private JLabel jLabelBox;
	private JLabel jLabelGender;
	
	public JTextField jTextFieldName;
	public JTextField jTextFieldBreed;
	public JTextField jTextFieldBox;
	
	public JComboBox<String> jComboBoxGender;
	
	public JSpinner jSpinnerAge;
	
	private Box horizontalBox;
	
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	
	public HorseDetailsPanel(boolean mode, 
		MainWindow mWindow) {
		
		this.mode = mode;
		this.mWindow = mWindow;
		
		this.initialize();
		this.configJPanel();
		this.createLayout();
		
	}
	
	private void initialize() {
		
		this.jLabelName = new JLabel("Name");
		this.jLabelBreed = new JLabel("Breed");
		this.jLabelAge = new JLabel("Age");
		this.jLabelBox = new JLabel("Box");
		this.jLabelGender = new JLabel("Gender");
		
		this.jTextFieldName = new BasicTextField(0);
		this.jTextFieldBreed = new BasicTextField(0);
		this.jSpinnerAge = new NumericSpinner(mode,150,0,0,Integer.MAX_VALUE,1);
		this.jTextFieldBox = new BasicTextField(0);
		this.jComboBoxGender = new DaoComboBox("Genders","name",0);
				
		this.horizontalBox = Box.createHorizontalBox();
		
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(gbl);
		
	}
	
	private void createLayout() {
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,10,6,0);
        gbl.setConstraints(jLabelName,  gbc);
        this.add(jLabelName);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,10,6,10);
        gbl.setConstraints(jTextFieldName,  gbc);
        this.add(jTextFieldName);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,10,6,0);
        gbl.setConstraints(jLabelBreed,  gbc);
        this.add(jLabelBreed);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,10,6,10);
        gbl.setConstraints(jTextFieldBreed,  gbc);
        this.add(jTextFieldBreed);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,10,6,0);
        gbl.setConstraints(jLabelAge,  gbc);
        this.add(jLabelAge);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,10,6,10);
        gbl.setConstraints(jSpinnerAge,  gbc);
        this.add(jSpinnerAge);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,10,6,0);
        gbl.setConstraints(jLabelBox,  gbc);
        this.add(jLabelBox);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,10,6,10);
        gbl.setConstraints(jTextFieldBox,  gbc);
        this.add(jTextFieldBox);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,10,6,0);
        gbl.setConstraints(jLabelGender,  gbc);
        this.add(jLabelGender);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,10,6,10);
        gbl.setConstraints(jComboBoxGender,  gbc);
        this.add(jComboBoxGender);
	
        gbc.gridx = 0;
        gbc.gridy = 50;
        gbc.gridwidth = 50;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,0,0);
        gbl.setConstraints(horizontalBox,  gbc);
        this.add(horizontalBox);
        
	}	
	
	public void resetComponents() {
		
		this.jTextFieldName.setText(null);
		this.jTextFieldBreed.setText(null);
		this.jSpinnerAge.setValue(0);
		this.jTextFieldBox.setText(null);
		this.jComboBoxGender.setSelectedIndex(0);
		
	}
	
}
