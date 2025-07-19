package panels;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import dao.Database;
import windows.AddFeedingDialog;
import windows.EditFeedingDialog;
import windows.MainWindow;

public class FeedingPanel extends JPanel implements ActionListener {

    String[] columnNames = { "ID", "Horse Name", "Feed Type", 
    "Amount (kg)", "Time" };

    public MainWindow mWindow;

    private JLabel jLabelHorse;
    private JComboBox<String> jComboBoxHorse;           
    private Map<String,Integer> horseMap = new HashMap<>(); 
    private int selectedHorseId = -1;

    private DefaultTableModel feedingTableModel;
    private JTable jTableFeedingHistory;

    private JButton jButtonAddFeeding;
    private JButton jButtonRemoveFeeding;
    private JButton jButtonEditFeeding;

    private JTextArea jTextAreaComments;
    private JScrollPane jSpCommentsView;
    
    private JScrollPane jSpFeedingHistory;
    private JPanel jPanelCommand;
    private JTabbedPane jTabbedPaneMain;

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    public FeedingPanel(MainWindow mWindow) {
        this.mWindow = mWindow;
        
        initialize();
        
        try {
        
        loadHorses();
        refreshFeedingList();
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        configJPanel();
        registerEvents();
        createLayout();
    
    }

    private void initialize() {
    	
    	this.gbl = new GridBagLayout();
    	this.gbc = new GridBagConstraints();
    	
    	this.jLabelHorse = new JLabel("Select horse");
    	this.jComboBoxHorse = new JComboBox<>(); 
    	this.jButtonAddFeeding = createJButton("Add");
    	this.jButtonRemoveFeeding = createJButton("Remove");
    	this.jButtonEditFeeding = createJButton("Edit");
    	
    	this.jTextAreaComments = new JTextArea();
    	this.jSpCommentsView = new JScrollPane(jTextAreaComments);
    	
    	this.feedingTableModel = new DefaultTableModel(columnNames, 0);
    	this.jTableFeedingHistory = new JTable(feedingTableModel);
    	this.jSpFeedingHistory = new JScrollPane(jTableFeedingHistory);

    	this.jPanelCommand = new JPanel(gbl);
    	this.jTabbedPaneMain = new JTabbedPane(JTabbedPane.TOP);
    
    }

    private void configJPanel() {
    	
    	this.setBorder(null);
        this.setLayout(gbl);
    
    }

    private void registerEvents() {
        
    	jComboBoxHorse.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			
    			try {
                jComboBoxHorseSelected();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }    			
    		}    		
    	});    	

        jTableFeedingHistory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
            	if (!e.getValueIsAdjusting()) {
                	
            		int row = jTableFeedingHistory.getSelectedRow();
            		if (row == -1) return;
            		
            		int feedingId = (int) jTableFeedingHistory.getValueAt(row, 0);
            		
            		try {            			
            		loadComments(feedingId);            			
            		} catch (SQLException ex) { ex.printStackTrace(); }
            		
                }
            }
        });
    }
    
    private void loadComments(int id) throws SQLException {
    	
    	this.jTextAreaComments.setText(null);
    	
    	String sql = "SELECT horse_id,comments " + 
    	"FROM feeding WHERE horse_id = ?";
    	
    	Database.loadDriver();
    	Connection conn = DriverManager.getConnection(Database.DB_URL);
    	
    	PreparedStatement stat = conn.prepareStatement(sql);
    	stat.setInt(1, id);
    	
    	ResultSet rs = stat.executeQuery();
    	
    	if (rs.next()) {
    		
    		String comments = rs.getString("comments");
    		this.jTextAreaComments.setText(comments);
    		
    	}
    	
    	rs.close();
    	stat.close();
    	conn.close();
    	    	
    }

    private void createLayout() {
        
    	configJTable();
    	configJTextArea();
        populateJTabbedPane();
        populateJPanelCommand();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,9,6,0);
        gbl.setConstraints(this.jLabelHorse,  gbc);
        this.add(jLabelHorse);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,16,6,9);
        gbl.setConstraints(this.jComboBoxHorse,  gbc);
        this.add(jComboBoxHorse);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = GridBagConstraints.NONE;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,3,6,3);
        gbl.setConstraints(this.jPanelCommand,  gbc);
        this.add(jPanelCommand);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(6,6,6,6);
        gbl.setConstraints(jTabbedPaneMain,  gbc);
        this.add(jTabbedPaneMain);
        
    }

    private void configJTable() {
        
    	jTableFeedingHistory.setDefaultEditor(Object.class, null);
        jTableFeedingHistory.setRowHeight(25);

        // dölj första kolumnen (ID)
        TableColumn idColumn = jTableFeedingHistory.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setPreferredWidth(0);
        idColumn.setResizable(false);
    
    }
    
    private void configJTextArea() {
    	
    	this.jTextAreaComments.setEnabled(false);
    	this.jTextAreaComments.setDisabledTextColor(SystemColor.textText);
    	this.jTextAreaComments.setMargin(new Insets(6,6,6,6));
    	this.jTextAreaComments.setFont(new Font("Arial",Font.PLAIN,16));
    	this.jTextAreaComments.setLineWrap(true);
    	this.jTextAreaComments.setWrapStyleWord(true);
    	
    }

    private void populateJTabbedPane() {
    	
    	this.jSpFeedingHistory.setBorder(null);
    	this.jSpFeedingHistory.setViewportBorder(null);
    	
    	this.jSpCommentsView.setBorder(null);
    	this.jSpCommentsView.setViewportBorder(null);
        
    	this.jTabbedPaneMain.addTab("Feeding", jSpFeedingHistory);
    	this.jTabbedPaneMain.addTab("Comments", jSpCommentsView);
    }

    private void populateJPanelCommand() {
    	
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(6,6,0,0);
        gbl.setConstraints(jButtonAddFeeding, gbc);
        jPanelCommand.add(jButtonAddFeeding);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(6,3,0,0);
        gbl.setConstraints(jButtonRemoveFeeding, gbc);
        jPanelCommand.add(jButtonRemoveFeeding);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(6,3,0,0);
        gbl.setConstraints(jButtonEditFeeding, gbc);
        jPanelCommand.add(jButtonEditFeeding);
        
    }

    // 👇 Ladda in hästar till comboboxen
    public void loadHorses() throws SQLException {
        
    	horseMap.clear();
        jComboBoxHorse.removeAllItems();

        Database.loadDriver();
        try (Connection conn = DriverManager.getConnection(Database.DB_URL);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT id,name FROM Horses ORDER BY name")) {

            while (rs.next()) {
            	
                int id = rs.getInt("id");
                String name = rs.getString("name");
                
                horseMap.put(name, id);
                jComboBoxHorse.addItem(name);
            
            }
        }
    }

    // 👇 Körs när du väljer häst
    private void jComboBoxHorseSelected() throws SQLException {
        String horseName = (String) jComboBoxHorse.getSelectedItem();
        
        if (horseName != null && horseMap.containsKey(horseName)) {
        
        	selectedHorseId = horseMap.get(horseName);
            refreshFeedingList();
        
        }
    }

    // 👇 Läs in feeding till JTable
    public void refreshFeedingList() {
    	
        feedingTableModel.setRowCount(0);

        String sql = "SELECT id, horse_name, feed_type, " + 
        "amount, feed_time FROM feeding";
        
        if (selectedHorseId > 0) {
        
            sql = "SELECT id, horse_name, " + 
            "feed_type, amount, feed_time FROM " + 
            "feeding WHERE horse_id=" + selectedHorseId;
        
        }
        
        sql += " ORDER BY feed_time DESC";

        try (Connection conn = DriverManager.getConnection(Database.DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                
            	java.sql.Time time = rs.getTime("feed_time");
            	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            	String timeFormatted = sdf.format(time);
            	
            	Object[] row = {
                            			
            		rs.getInt("id"),
                    rs.getString("horse_name"),
                    rs.getString("feed_type"),
                    rs.getDouble("amount"),
                    timeFormatted
                
            	};
                
                feedingTableModel.addRow(row);
            
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading feeding data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButtonAddFeedingPressed(ActionEvent e) {
    	
        jButtonAddFeeding.setEnabled(false);
        
        AddFeedingDialog dialog = new AddFeedingDialog(this);
        dialog.setVisible(true);
        
        jButtonAddFeeding.setEnabled(true);
        refreshFeedingList(); // 👈 uppdatera efter add
    
    }

    private void jButtonRemoveFeedingPressed() {
        int selectedRow = jTableFeedingHistory.getSelectedRow();
        if (selectedRow == -1) return;

        int id = (int) feedingTableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Remove selected feeding?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            
        	try (Connection conn = DriverManager.getConnection(Database.DB_URL);
            
            	PreparedStatement ps = conn.prepareStatement("DELETE FROM " + 
                "feeding WHERE id=?")) {
            	
                ps.setInt(1, id);
                ps.executeUpdate();
                refreshFeedingList();
            
            } catch (SQLException ex) {
            
            	ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Could not remove feeding: " + ex.getMessage());
            
            }
        }
    }
    
    private void jButtonEditFeedingPressed() {
    	
        int selectedRow = jTableFeedingHistory.getSelectedRow();
        if (selectedRow == -1) return;
        	
        int feedingId = (int) feedingTableModel.getValueAt(selectedRow, 0);
        if (feedingId == -1) return;
            
        EditFeedingDialog dialog = new EditFeedingDialog(feedingId,this);
        dialog.setVisible(true);
            	
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    	Object src = e.getSource();
        
        if (src == jButtonAddFeeding) {
         
        	jButtonAddFeedingPressed(e);
        
        }
        
        if (src == jButtonRemoveFeeding) {
            jButtonRemoveFeedingPressed();
        }
        
        if (src == jButtonEditFeeding) {
        	
        	jButtonEditFeedingPressed();
        	
        }
    }

    private JButton createJButton(String label) {
        JButton b = new JButton(label);
        b.setFocusable(false);
        b.setPreferredSize(new Dimension(80, 33));
        b.addActionListener(this);
        return b;
    }
}
