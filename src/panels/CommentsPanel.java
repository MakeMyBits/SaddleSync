package panels;

import java.awt.*;
import javax.swing.*;

import components.BasicTextField;
import components.DaoComboBox;
import components.NumericSpinner;
import windows.MainWindow;

public class CommentsPanel extends JPanel {

	private MainWindow mWindow;
	
	public JTextArea jTextAreaComments;
	private JScrollPane jSpCommenstView;
	
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	
	public CommentsPanel(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		this.initialize();
		this.configJTextArea();
		this.configJPanel();
		this.createLayout();
		
	}
	
	private void initialize() {
		
		this.jTextAreaComments = new JTextArea();
		this.jSpCommenstView = new JScrollPane(jTextAreaComments);
		
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
	}
	
	private void configJTextArea() {
		
		this.jTextAreaComments.setMargin(new Insets(6,6,6,6));
		this.jTextAreaComments.setFont(new Font("Arial",Font.PLAIN,16));
		this.jTextAreaComments.setLineWrap(true);
		this.jTextAreaComments.setTabSize(1);
		this.jTextAreaComments.setWrapStyleWord(true);
		this.jTextAreaComments.setDisabledTextColor(SystemColor.textText);
		
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(gbl);
		
	}
	
	private void createLayout() {
		
		this.jSpCommenstView.setBorder(null);
		this.jSpCommenstView.setViewportBorder(null);
		
	    gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,0,0);
        gbl.setConstraints(jSpCommenstView,  gbc);
        this.add(jSpCommenstView);
        
	}	
	
	public String getText() {
		
		return jTextAreaComments.getText();
	
	}
}
