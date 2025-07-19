package windows;

import java.sql.*;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import components.BasicTextField;
import components.NumericSpinner;
import components.TimeSpinner;
import dao.Database;
import model.Feeding;
import model.Horse;
import model.HorseItem;
import panels.CommentsPanel;
import panels.FeedingPanel;
import panels.HorseDetailsPanel;
import threads.HorseInfoKeeper;
import threads.HorseInfoLoader;
import components.DaoComboBox;

public class AddFeedingDialog extends JDialog 
	implements ActionListener {
	
	private FeedingPanel jPanelOwner;
			
	private JLabel jLabelHorse;
    private JLabel jLabelFeedType;
    private JLabel jLabelAmount;
    private JLabel jLabelTime;

    public JComboBox<HorseItem> jComboBoxHorse;
    public JTextField jTextFieldFeedType;
    public JTextField jTextFieldAmount;
    
    public JSpinner jSpinnerTime;

    private JScrollPane jScrollPaneComments;
    private JScrollPane jSpFeedingHistory;
    
	private Box horizontalBox;
	
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	
	private JTextArea jTextAreaComments;
	private JScrollPane jSpComments;
	
	private JButton jButtonCancel;
	private JButton jButtonAdd;
	
	private JPanel jPanelFeeding;
	private JPanel jPanelCommand;
	private JPanel jPanelContainer;
	private JScrollPane jSpFeedingInfo;
	
	private JTabbedPane jTabbedPaneMain;
	
	private Feeding result;
	
	public AddFeedingDialog(FeedingPanel jPanelOwner) {
		
		super(jPanelOwner.mWindow);
		
		this.jPanelOwner = jPanelOwner;
		
		this.initialize();
		this.configJDialog();
		this.createLayout();
		this.registerEvents();
		
	}
	
	private void initialize() {
		
		this.jLabelHorse = new JLabel("Horse");
        this.jLabelFeedType = new JLabel("Feed type");
        this.jLabelAmount = new JLabel("Amount (kg)");
        this.jLabelTime = new JLabel("Time");

        this.jComboBoxHorse = createJComboBox();
        this.jTextFieldFeedType = new BasicTextField(0);
        this.jTextFieldAmount = new BasicTextField(0);

        this.jTextAreaComments = new JTextArea();
        this.jSpComments = new JScrollPane(jTextAreaComments);
        
        this.jSpinnerTime = new TimeSpinner();
   		this.horizontalBox = Box.createHorizontalBox();
					
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
	
		this.jButtonCancel = createJButton("Cancel");
		this.jButtonAdd = createJButton("Add");
		
		this.jPanelFeeding = new JPanel(gbl);
		this.jSpFeedingInfo = new JScrollPane(jPanelFeeding);
		
		this.jPanelCommand = new JPanel(gbl);
		
		this.jPanelContainer = new JPanel(gbl);
		
		this.jTabbedPaneMain = new JTabbedPane(JTabbedPane.TOP);
		
	}	
	
	private void configJDialog() {
		
		this.setTitle("Add feeding");
		this.setSize(585,365);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setContentPane(jPanelContainer);
						
	}
	
	private void createLayout() {
		
		this.configJTextArea();
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,6,6,0);
        gbl.setConstraints(jLabelHorse,  gbc);
        this.jPanelFeeding.add(jLabelHorse);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,16,6,10);
        gbl.setConstraints(jComboBoxHorse,  gbc);
        this.jPanelFeeding.add(jComboBoxHorse);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,6,6,0);
        gbl.setConstraints(jLabelFeedType,  gbc);
        this.jPanelFeeding.add(jLabelFeedType);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,16,6,10);
        gbl.setConstraints(jTextFieldFeedType,  gbc);
        this.jPanelFeeding.add(jTextFieldFeedType);
		
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,6,6,0);
        gbl.setConstraints(jLabelAmount,  gbc);
        this.jPanelFeeding.add(jLabelAmount);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,16,6,10);
        gbl.setConstraints(jTextFieldAmount,  gbc);
        this.jPanelFeeding.add(jTextFieldAmount);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,6,6,0);
        gbl.setConstraints(jLabelTime,  gbc);
        this.jPanelFeeding.add(jLabelTime);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,16,6,10);
        gbl.setConstraints(jSpinnerTime,  gbc);
        this.jPanelFeeding.add(jSpinnerTime);
                
	    gbc.gridx = 0;
        gbc.gridy = 50;
        gbc.gridwidth = 50;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(6,10,10,10);
        gbl.setConstraints(horizontalBox,  gbc);
        this.jPanelFeeding.add(horizontalBox);        
       
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3,10,3,10);
        gbl.setConstraints(jTabbedPaneMain,  gbc);
        this.jPanelContainer.add(jTabbedPaneMain);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(3,0,3,0);
        gbl.setConstraints(jPanelCommand,  gbc);
        this.jPanelContainer.add(jPanelCommand);
        
        this.populateJComboBox();
        this.populateJTabbedPane();
        this.populateJPanelCommand();
                
	}
	
	private void populateJComboBox() {
		
	    jComboBoxHorse.removeAllItems();

	    Database.loadDriver();
	    
	    try (Connection conn = DriverManager.getConnection(Database.DB_URL);
	         
	    	Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT id, name FROM Horses " + 
	        "ORDER BY name ASC")) {

	        while (rs.next()) {
	        
	        	int id = rs.getInt("id");
	            String name = rs.getString("name");
	            HorseItem item = new HorseItem(id,name);	            
	            jComboBoxHorse.addItem(item);
	        
	        }

	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this,
	            "Failed to load horses: " + ex.getMessage(),
	            "Database Error",
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void populateJTabbedPane() {
		
		this.jSpFeedingInfo.setBorder(null);
		this.jSpFeedingInfo.setViewportBorder(null);
		
		this.jSpComments.setBorder(null);
		this.jSpComments.setViewportBorder(null);
		
		this.jTabbedPaneMain.addTab("Feeding", jSpFeedingInfo);
		this.jTabbedPaneMain.addTab("Comments", jSpComments);
		
	}
	
	private void populateJPanelCommand() {
		
		GridLayout layout = new GridLayout(0,2,3,3);
		JPanel jPanelButtonView = new JPanel(layout);
		jPanelButtonView.add(jButtonCancel);
		jPanelButtonView.add(jButtonAdd);
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(3,0,6,10);
        gbl.setConstraints(jPanelButtonView,  gbc);
        this.jPanelCommand.add(jPanelButtonView);
		
	}
	
	private void configJTextArea() {

    	jTextAreaComments.setLineWrap(true);
        jTextAreaComments.setWrapStyleWord(true);
        jTextAreaComments.setMargin(new Insets(6, 6, 6, 6));
        jTextAreaComments.setTabSize(1);
        jTextAreaComments.setFont(new Font("Arial", Font.PLAIN, 16));
    
    }
	
	private void registerEvents() {
						
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				AddFeedingDialog.this.dispose();
						
			}			
		});
		
		this.jButtonCancel.addActionListener(this);
		this.jButtonAdd.addActionListener(this);

	}
	
	private void jButtonAddPressed() {

	    HorseItem selectedHorse = (HorseItem) jComboBoxHorse.getSelectedItem();

	    if (selectedHorse == null) {
	        
	    	JOptionPane.showMessageDialog(this, 
	        "No horse selected!", "Error", 
	        JOptionPane.WARNING_MESSAGE);
	        return;
	    
	    }

	    String feedType = jTextFieldFeedType.getText().trim();
	    String amountStr = jTextFieldAmount.getText().trim();
	    Object timeObj = jSpinnerTime.getValue();  // OBS: Object, ej java.sql.Time
	    String comments = jTextAreaComments.getText().trim();

	    if (feedType.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Feed type cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    if (amountStr.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Amount cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    double amount;
	    try {
	        amount = Double.parseDouble(amountStr);
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Invalid amount format.", "Validation Error", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    java.sql.Time sqlTime;
	    if (timeObj instanceof java.util.Date) {
	        // Konvertera java.util.Date till java.sql.Time (TIME utan datum)
	        sqlTime = new java.sql.Time(((java.util.Date) timeObj).getTime());
	    } else {
	        // Fallback: sätt tid till null om något är fel
	        sqlTime = null;
	    }

	    int horseId = selectedHorse.getId();
	    String horseName = selectedHorse.getName();

	    Database.loadDriver();

	    try (Connection conn = DriverManager.getConnection(Database.DB_URL)) {
	        String sql = "INSERT INTO feeding " + 
	        "(horse_id, horse_name, feed_type, amount, " + 
	        "feed_time, comments) VALUES (?, ?, ?, ?, ?, ?)";

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, horseId);
	            ps.setString(2, horseName);
	            ps.setString(3, feedType);
	            ps.setDouble(4, amount);

	            if (sqlTime != null) {
	                ps.setTime(5, sqlTime);
	            } else {
	                ps.setNull(5, java.sql.Types.TIME);
	            }

	            ps.setString(6, comments.isEmpty() ? null : comments);

	            ps.executeUpdate();
	        }

	        if (jPanelOwner != null) {
	            jPanelOwner.refreshFeedingList();
	        }

	        this.dispose();

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this,
	            "Error saving feeding: " + ex.getMessage(),
	            "Database Error",
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void jButtonCancelPressed() {
		
		this.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource().equals(this.jButtonAdd)) {
			
			jButtonAddPressed();			
		
		}
		
		if (e.getSource().equals(this.jButtonCancel)) {
			
			jButtonCancelPressed();			
			
		}		
	}
		
	private JScrollPane createJScrollPane(int height, 
		JComponent comp, String title) {
        
    	Dimension dim = new Dimension();
    	dim.height = 33;
    	
    	EmptyBorder emptyBorder = new EmptyBorder(0, 6, 6, 6);
        EtchedBorder etchedBorder = new EtchedBorder();
        TitledBorder titledBorder = new TitledBorder(etchedBorder, title);
        CompoundBorder compBorder = new CompoundBorder(titledBorder, emptyBorder);
        JScrollPane sp = new JScrollPane(comp);
        sp.setViewportBorder(etchedBorder);
        sp.setPreferredSize(dim);
        sp.setBorder(compBorder);
        
        return sp;
    
	}
	
	private JButton createJButton(String label) {
		
		Dimension dim = new Dimension();
		dim.width = 85;
		dim.height = 33;
		
		JButton button = new JButton();
		button.setFocusable(false);
		button.setText(label);
		button.setPreferredSize(dim);		
		
		return button;
		
	}
	
	private JComboBox<HorseItem> createJComboBox() {
	    
    	Dimension dim = new Dimension();
    	dim.height = 33;
    	
    	JComboBox<HorseItem> c = new JComboBox<>();
        c.setFocusable(false);
        c.setPreferredSize(dim);
        
        return c;
    
	}
}
