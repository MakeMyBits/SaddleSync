package windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import components.BasicTextField;
import components.NumericSpinner;
import model.Horse;
import panels.CommentsPanel;
import panels.HorseDetailsPanel;
import threads.HorseInfoKeeper;
import threads.HorseInfoLoader;
import threads.HorseInfoSecretary;
import components.DaoComboBox;

public class EditHorseDialog extends JDialog {
	
	public Horse horse;
	public MainWindow mWindow;
		
	private JButton jButtonCancel;
	private JButton jButtonSave;
	
	private Box horizontalBox;
	
	public HorseDetailsPanel jPanelHorseDetails;
	public CommentsPanel jPanelComments;
	
	private JScrollPane jSpHorseDetails;
	
	private JTabbedPane jTabbedView;
	
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
		
	private JPanel jPanelCommand;
	private JPanel jPanelContainer;
	
	public EditHorseDialog(Horse horse, 
		MainWindow mWindow) {
		
		super(mWindow);
		
		this.horse = horse;
		this.mWindow = mWindow;
		
		this.initialize();
		this.configJDialog();
		this.configJTabbedPane();
		this.createLayout();
		this.registerEvents();
		
	}
	
	private void initialize() {
		
		this.horizontalBox = Box.createHorizontalBox();
		
		this.jPanelHorseDetails = new HorseDetailsPanel(true,mWindow);
		this.jPanelComments = new CommentsPanel(mWindow);
		
		this.jSpHorseDetails = new JScrollPane(jPanelHorseDetails);
		
		this.jButtonCancel = createJButton(95,33,"Cancel");
		this.jButtonSave = createJButton(95,33,"Save");
		
		this.jTabbedView = new JTabbedPane(JTabbedPane.TOP);
		
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
		this.jPanelCommand = new JPanel(gbl);
		this.jPanelContainer = new JPanel(gbl);
		
	}	
	
	private void configJDialog() {
		
		this.setTitle("Edit horse");
		this.setSize(585,365);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setContentPane(jPanelContainer);
						
	}
		
	private void configJTabbedPane() {
		
		this.jSpHorseDetails.setBorder(null);
		this.jSpHorseDetails.setViewportBorder(null);
				
		this.jTabbedView.setBorder(null);
		this.jTabbedView.addTab("Details", jSpHorseDetails);
		this.jTabbedView.addTab("Comments", jPanelComments);
		
	}
	
	private void createLayout() {
		
	    gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(6,10,10,10);
        gbl.setConstraints(jTabbedView,  gbc);
        this.jPanelContainer.add(jTabbedView);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,0,0,0);
        gbl.setConstraints(jPanelCommand,  gbc);
        this.jPanelContainer.add(jPanelCommand);
				
        this.populateJPanelCommand();
        
	}
	
	private void populateJPanelCommand() {
		
		GridLayout layout = new GridLayout(0,2,3,3);
		JPanel jPanelButtonView = new JPanel(layout);
		jPanelButtonView.add(jButtonCancel);
		jPanelButtonView.add(jButtonSave);
		
		gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,0,10,10);
        gbl.setConstraints(jPanelButtonView,  gbc);
        this.jPanelCommand.add(jPanelButtonView);
		
	}
	
	private void registerEvents() {
		
		this.jButtonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				EditHorseDialog.this.dispose();
				
			}						
		});
		
		this.jButtonSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = jPanelHorseDetails.jTextFieldName.getText().trim();
		        String breed = jPanelHorseDetails.jTextFieldBreed.getText().trim();
		        String ageText = jPanelHorseDetails.jSpinnerAge.getValue().toString();
		        String box = jPanelHorseDetails.jTextFieldBox.getText().trim();
		        String gender = (String) jPanelHorseDetails.jComboBoxGender.getSelectedItem();
		        String comments = jPanelComments.getText();

		        if (name.isEmpty()) {
		            
		        	JOptionPane.showMessageDialog(EditHorseDialog.this, 
		            "Please enter the horse's name.", "Missing Name", 
		            JOptionPane.WARNING_MESSAGE);
		            return;
		        
		        }

		        if (gender == null || gender.isEmpty()) {
		        
		        	JOptionPane.showMessageDialog(EditHorseDialog.this, 
		        	"Please select a gender.", "Missing Gender", 
		        	JOptionPane.WARNING_MESSAGE);
		            return;
		        
		        }

		        int age = -1;
		        
		        try {
		         age = Integer.parseInt(ageText);
		         if (age < 0) throw new NumberFormatException();
		        } catch (NumberFormatException ex) {
		            
		        	JOptionPane.showMessageDialog(EditHorseDialog.this, 
		            "Please enter a valid non-negative age.", "Invalid Age", 
		            JOptionPane.WARNING_MESSAGE);
		            return;
		        
		        }		        
		        
		        HorseInfoSecretary secretary = 
		        	new HorseInfoSecretary(EditHorseDialog.this);
		        
		        secretary.start();
		        
		        if (secretary.isAlive()) {
		        	
		        	HorseInfoLoader loader = 
		        		new HorseInfoLoader(mWindow);
		        	
		        	loader.start();
		        	loader = null;
		        	
		        }
		        
		        secretary = null;
		        EditHorseDialog.this.dispose();		        
		        
			}						
		});
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				EditHorseDialog.this.dispose();
						
			}			
			
			@Override
			public void windowOpened(WindowEvent e) {
				
				EditHorseDialog.this.performEditEvent();
				
			}			
		});
	}
	
	private void performEditEvent() {
		
		String name = horse.getName();
		String breed = horse.getBreed();
		int age = horse.getAge();
		String box = horse.getBox();
		String gender = horse.getGender();
		String comments = horse.getComments();
		
		this.jPanelHorseDetails.jTextFieldName.setText(name);
		this.jPanelHorseDetails.jTextFieldBreed.setText(breed);
		this.jPanelHorseDetails.jSpinnerAge.setValue(age);
		this.jPanelHorseDetails.jTextFieldBox.setText(box);
		this.jPanelHorseDetails.jComboBoxGender.setSelectedItem(gender);
		this.jPanelComments.jTextAreaComments.setText(comments);
		
	}
	
	private JButton createJButton(int width, 
		int height, String label) {
		
		Dimension dim = new Dimension(width,height);
		JButton button = new JButton();
		button.setFocusable(false);
		button.setText(label);
		button.setMinimumSize(dim);
		button.setPreferredSize(dim);
		button.setMaximumSize(dim);
		
		return button;
		
	}
}
