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
import panels.ImagePanel;
import threads.HorseInfoKeeper;
import threads.HorseInfoLoader;
import components.DaoComboBox;

public class AddHorseDialog extends JDialog {
	
	public MainWindow mWindow;
		
	private JButton jButtonCancel;
	private JButton jButtonAdd;
	
	private Box horizontalBox;
	
	public HorseDetailsPanel jPanelHorseDetails;
	public CommentsPanel jPanelComments;
	public ImagePanel jPanelImage;
	
	private JScrollPane jSpHorseDetails;
	
	private JTabbedPane jTabbedView;
	
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
		
	private JPanel jPanelCommand;
	private JPanel jPanelContainer;
	
	public AddHorseDialog(MainWindow mWindow) {
		
		super(mWindow);
		
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
		this.jPanelImage = new ImagePanel(true, mWindow);
		
		this.jSpHorseDetails = new JScrollPane(jPanelHorseDetails);
		
		this.jButtonCancel = createJButton(95,33,"Cancel");
		this.jButtonAdd = createJButton(95,33,"Add");
		
		this.jTabbedView = new JTabbedPane(JTabbedPane.TOP);
		
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
		this.jPanelCommand = new JPanel(gbl);
		this.jPanelContainer = new JPanel(gbl);
		
	}	
	
	private void configJDialog() {
		
		this.setTitle("Add horse");
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
		this.jTabbedView.addTab("Image", jPanelImage);
		
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
		jPanelButtonView.add(jButtonAdd);
		
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
				
				AddHorseDialog.this.dispose();
				
			}						
		});
		
		this.jButtonAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = jPanelHorseDetails.jTextFieldName.getText().trim();
		        String breed = jPanelHorseDetails.jTextFieldBreed.getText().trim();
		        String ageText = jPanelHorseDetails.jSpinnerAge.getValue().toString();
		        String box = jPanelHorseDetails.jTextFieldBox.getText().trim();
		        String gender = (String) jPanelHorseDetails.jComboBoxGender.getSelectedItem();
		        String comments = jPanelComments.getText();

		        if (name.isEmpty()) {
		            
		        	JOptionPane.showMessageDialog(AddHorseDialog.this, 
		            "Please enter the horse's name.", "Missing Name", 
		            JOptionPane.WARNING_MESSAGE);
		            return;
		        
		        }

		        if (gender == null || gender.isEmpty()) {
		        
		        	JOptionPane.showMessageDialog(AddHorseDialog.this, 
		        	"Please select a gender.", "Missing Gender", 
		        	JOptionPane.WARNING_MESSAGE);
		            return;
		        
		        }

		        int age = -1;
		        
		        try {
		         age = Integer.parseInt(ageText);
		         if (age < 0) throw new NumberFormatException();
		        } catch (NumberFormatException ex) {
		            
		        	JOptionPane.showMessageDialog(AddHorseDialog.this, 
		            "Please enter a valid non-negative age.", "Invalid Age", 
		            JOptionPane.WARNING_MESSAGE);
		            return;
		        
		        }				
		        		        
		        Horse horse = new Horse();
		        horse.setName(name);
		        horse.setBreed(breed);
		        horse.setAge(age);
		        horse.setBox(box);
		        horse.setGender(gender);
		        horse.setComments(comments);
		        
		        HorseInfoKeeper keeper = 
		        	new HorseInfoKeeper(horse, 
		        	AddHorseDialog.this);
		        
		        keeper.start();
		        
		        if (keeper.isAlive()) {
		        	
		        	HorseInfoLoader loader = 
		        		new HorseInfoLoader(mWindow);
		        	
		        	loader.start();
		        	loader = null;
		        	
		        }
		        
		        keeper = null;
		        
			}						
		});
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				AddHorseDialog.this.dispose();
						
			}			
		});
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
