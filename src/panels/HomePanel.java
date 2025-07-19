package panels;

import java.awt.*;
import javax.swing.*;

import dao.HorsesDao;
import model.Horse;
import windows.MainWindow;

public class HomePanel extends JPanel {

	private MainWindow mWindow;
	
	private Box horizontalBox;
	
	public JEditorPane jEditorPaneOverview;
	private JScrollPane jSpOverview;
	
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	
	public HomePanel(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		this.initialize();
		this.configJPanel();
		this.configJEditorPane();
		this.updateDashboard();
		this.createLayout();
		
	}
	
	private void initialize() {
				
		this.horizontalBox = Box.createHorizontalBox();
		
		this.jEditorPaneOverview = new JEditorPane();
		this.jSpOverview = new JScrollPane(jEditorPaneOverview);
		
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(gbl);
		
	}
	
	private void configJEditorPane() {
		
		this.jEditorPaneOverview.setContentType("text/html");
		this.jEditorPaneOverview.setEditable(false);
		this.jEditorPaneOverview.setBackground(SystemColor.info);
		this.jEditorPaneOverview.setCaretColor(SystemColor.info);
		
	}
	
	private void createLayout() {
		
		this.jSpOverview.setBorder(null);
		this.jSpOverview.setViewportBorder(null);
		
	    gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,0,0);
        gbl.setConstraints(jSpOverview,  gbc);
        this.add(jSpOverview);
        
	}
	
	public void updateDashboard() {
	 
		this.jEditorPaneOverview.setText(null);
		
		HorsesDao horsesDao = new HorsesDao();
	    java.util.List<Horse> horses = horsesDao.getAllHorses();
	    Horse latestHorse = horsesDao.getLatestHorse();
	    
	    StringBuilder html = new StringBuilder();
	    html.append("<html><body style='font-family:Arial; padding:10px; color:#333;'>");

	    // Rubrik
	    html.append("<h1 style='color:#2c3e50; margin-top:0;'>SaddleSync Dashboard</h1>");

	    // Antal hästar
	    int index = horses.size();
	    html.append("<p style='font-size:14px; margin:5px 0;'>Total horses: <b>")
	        .append(index)
	        .append("</b></p>");

	    // Senast tillagda
	    if (latestHorse != null) {
	        html.append("<div style='padding: 0; margin-bottom:12px;'>");
	        html.append("<h2 style='margin:0 0 6px 0; color:#1a73e8;'>Latest Horse Added</h2>");
	        html.append("<p style='margin:0; font-weight:bold; font-size:16px;'>")
	            .append(latestHorse.getName())
	            .append("</p>");
	        html.append("</div>");
	    }

	    // Lista på alla hästar
	    html.append("<h2 style='color:#2c3e50; margin:12px 0 6px;'>All Horses</h2>");
	    html.append("<ul style='list-style-type:none; padding-left: 0; margin:0;'>");

	    for (Horse h : horses) {
	        if (h != null) {
	            html.append("<li style='margin-bottom:10px; padding:8px; border:1px solid #ddd; border-radius:4px;'>");
	            html.append("<ul style='list-style-type:none; padding-left: 0; margin:0;'>");

	            // Namnet ska alltid med
	            html.append("<li style='font-weight:bold; font-size:15px; color:#000;'>")
	                .append(h.getName())
	                .append("</li>");

	            if (h.getBreed() != null && !h.getBreed().isEmpty()) {
	                html.append("<li style='margin-top:2px;'>Breed: ")
	                    .append(h.getBreed())
	                    .append("</li>");
	            }
	            if (h.getGender() != null && !h.getGender().isEmpty()) {
	                html.append("<li style='margin-top:2px;'>Gender: ")
	                    .append(h.getGender())
	                    .append("</li>");
	            }
	            if (h.getAge() > 0) {
	                html.append("<li style='margin-top:2px;'>Age: ")
	                    .append(h.getAge())
	                    .append("</li>");
	            }
	            if (h.getBox() != null && !h.getBox().isEmpty()) {
	                html.append("<li style='margin-top:2px;'>Box: ")
	                    .append(h.getBox())
	                    .append("</li>");
	            }

	            html.append("</ul>");
	            // horisontell linje under varje häst
	            html.append("<hr style='border:none; border-top:1px solid #ddd; margin:8px 0 0;'>");
	            html.append("</li>");
	        }
	    }

	    html.append("</ul>");
	    html.append("</body></html>");

	    this.jEditorPaneOverview.setText(html.toString());
	
	}
}
