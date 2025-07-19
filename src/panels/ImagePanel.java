package panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.sql.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.Database;
import dao.HorsesDao;
import model.Horse;
import components.BasicTextField;
import windows.MainWindow;

public class ImagePanel extends JPanel {

	private boolean mode;
	private MainWindow mWindow;
	
	public JEditorPane jEditorPaneImage;
	private JScrollPane jSpImageView;
	
	public JLabel jLabelInfo;
	public JTextField jTextFieldPath;
	private JButton jButtonFile;
	
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	
	private JPanel jPanelImageChooser;
	
	public ImagePanel(boolean mode, 
		MainWindow mWindow) {
		
		this.mode = mode;
		this.mWindow = mWindow;
		
		this.initialize();
		this.configJPanel();
		this.configJEditorPane();
		this.createLayout();
		
	}
	
	private void initialize() {
						
		this.jEditorPaneImage = new JEditorPane();
		this.jSpImageView = new JScrollPane(jEditorPaneImage);
		
		this.jLabelInfo = new JLabel("Selected image");
		this.jTextFieldPath = new BasicTextField(0);
		this.jButtonFile = createJButton("Browse");
		
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
		this.jPanelImageChooser = new JPanel(gbl);	
		
	}
	
	private void configJPanel() {
	
		this.jPanelImageChooser.setVisible(mode);
		this.setBorder(null);
		this.setLayout(gbl);
		
	}
	
	private void configJEditorPane() {
				
		this.jEditorPaneImage.setContentType("text/html");
		this.jEditorPaneImage.setEditable(false);
		this.jEditorPaneImage.setCaretColor(Color.white);
		
	}
	
	private void createLayout() {
		
		this.jSpImageView.setBorder(null);
		this.jSpImageView.setViewportBorder(BorderFactory.createEtchedBorder());

		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,0,0,0);
        gbl.setConstraints(jPanelImageChooser,  gbc);
        this.add(jPanelImageChooser);
		
	    gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,3,3,3);
        gbl.setConstraints(jSpImageView,  gbc);
        this.add(jSpImageView);
        
        this.populateJPanelImageChooser();
        
	}
	
	private void populateJPanelImageChooser() {
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(6,6,6,0);
        gbl.setConstraints(jLabelInfo,  gbc);
        this.jPanelImageChooser.add(jLabelInfo);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(6,16,6,3);
        gbl.setConstraints(jTextFieldPath,  gbc);
        this.jPanelImageChooser.add(jTextFieldPath);
		
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(6,0,6,4);
        gbl.setConstraints(jButtonFile,  gbc);
        this.jPanelImageChooser.add(jButtonFile);
        
	}

	private void chooseImage() {
		
		JFileChooser chooser = new JFileChooser();
	    chooser.setDialogTitle("Choose an image");
	    chooser.setFileFilter(new FileNameExtensionFilter(
	    "Image files (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"));

	    int result = chooser.showOpenDialog(this);
	    
	    if (result == JFileChooser.APPROVE_OPTION) {
	    
	    	File selectedFile = chooser.getSelectedFile();
	        this.jTextFieldPath.setText(selectedFile.getAbsolutePath());

	        String html = "<html><body><img src='file:///" 
	        + selectedFile.getAbsolutePath().replace("\\", "/") 
	        + "'></body></html>";
	        
	        this.jEditorPaneImage.setText(html);
	        	        
	    }	
	}
	
	public void setSelectedImage(int horseId) {
				
		try {
			
		Database.loadDriver();
		Connection conn = DriverManager.getConnection(Database.DB_URL);
		
		String sql = "SELECT * FROM HorseImage WHERE horse_id = ?";
		PreparedStatement stat = conn.prepareStatement(sql);
		stat.setInt(1, horseId);
		
		ResultSet rs = stat.executeQuery();
								
		if (rs.next()) {
			
			String path = rs.getString("image_path");
			int len = path.length();
									
			String html = "<html><body><img src='file:///" 
			+ path.replace("\\", "/") + "'></body></html>";
			
			this.jEditorPaneImage.setText(html);
			this.jEditorPaneImage.revalidate();
			this.jEditorPaneImage.updateUI();
			
		}		
		
		stat.close();
		rs.close();
		conn.close();
			
		} catch (SQLException ex) { ex.printStackTrace(); }
		
	}
	
	private JButton createJButton(String text) {
		
		Dimension dim = new Dimension(90,33);
		JButton button = new JButton();
		
		button.setPreferredSize(dim);
		button.setFocusable(false);
		button.setText(text);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				button.setEnabled(false);
				chooseImage();
				button.setEnabled(true);
				
			}			
		});
		
		return button;
		
	}
}
