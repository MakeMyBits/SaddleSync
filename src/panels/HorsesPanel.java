package panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.HorsesDao;
import model.Horse;
import threads.ImageManager;
import windows.MainWindow;

public class HorsesPanel extends JPanel {

	private MainWindow mWindow;
	private HorsesDao dao;
	
	public JTable jTableHorseIndex;
	private JScrollPane jSpHorseIndex;
	
	private Box horizontalBox;
	
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	
	public JTabbedPane jTabbedInfoView;
	private JSplitPane jSplitPaneMain;
	
	public HorsesPanel(MainWindow mWindow) {
		
		this.mWindow = mWindow;
		
		this.initialize();
		this.configJPanel();
		this.configJTable();
		this.configJTabbedPane();
		this.configJSplitPane();
		this.registerEvents();
		this.createLayout();
		
	}
	
	private void initialize() {
		
		this.dao = new HorsesDao();
		this.horizontalBox = Box.createHorizontalBox();
		
		this.jTableHorseIndex = new JTable();
		this.jSpHorseIndex = new JScrollPane(jTableHorseIndex);
		
		this.jTabbedInfoView = new JTabbedPane(JTabbedPane.TOP);
		
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
		this.jSplitPaneMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
	}
	
	private void configJSplitPane() {
		
		this.jSplitPaneMain.setBorder(null);
		this.jSplitPaneMain.setContinuousLayout(true);
		this.jSplitPaneMain.setOneTouchExpandable(true);
		this.jSplitPaneMain.setDividerLocation(200);
		this.jSplitPaneMain.setDividerSize(10);
		this.jSplitPaneMain.setTopComponent(jSpHorseIndex);
		this.jSplitPaneMain.setBottomComponent(jTabbedInfoView);
		
	}
	
	private void configJPanel() {
		
		this.setBorder(null);
		this.setLayout(gbl);
				
	}
	
	public void configJTable() {
				
		String[] columnNames = { "Name","Breed",
			"Age","Box","Gender"
		};
				
		DefaultTableModel model = new DefaultTableModel(columnNames,0);
		this.jTableHorseIndex.setModel(model);
		this.jTableHorseIndex.setDefaultEditor(Object.class, null);
		this.jTableHorseIndex.setRowHeight(25);		
		
		java.util.List<Horse> horseList = dao.getAllHorses();
		
		for (int i = 0; i < horseList.size(); i++) {
			
			Horse horse = horseList.get(i);
			String name = horse.getName();
			String breed = horse.getBreed();
			int age = horse.getAge();
			String box = horse.getBox();
			String gender = horse.getGender();
			
			Object[] obj = new Object[] { name,breed,age,box,gender };
			model.addRow(obj);			
			
		}		
		
		model.fireTableDataChanged();
		
	}
	
	private void configJTabbedPane() {
		
		HorseDetailsPanel jPanelHorseDetails = 
			new HorseDetailsPanel(false,mWindow);
		
		CommentsPanel jPanelComments = new CommentsPanel(mWindow);
		JScrollPane jSpHorseDetails = new JScrollPane(jPanelHorseDetails);
		
		ImagePanel jPanelImage = new ImagePanel(false, mWindow);
				
		jSpHorseDetails.setBorder(null);
		jSpHorseDetails.setViewportBorder(null);
		
		this.jTabbedInfoView.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		this.jTabbedInfoView.addTab("Horses", jSpHorseDetails);
		this.jTabbedInfoView.addTab("Comments", jPanelComments);
		this.jTabbedInfoView.addTab("Image", jPanelImage);
		
		for (int i = 0; i < jTabbedInfoView.getComponentCount(); i++) {
			
			Component tabComp = jTabbedInfoView.getComponent(i);
			this.setComponentMode(tabComp, false);
			
		}		
	}
	
	private void registerEvents() {
		
		this.jTableHorseIndex.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if (!e.getValueIsAdjusting()) {
					
					int selectedRow = jTableHorseIndex.getSelectedRow();
					if (selectedRow == -1) return;
					
					Action actionEdit = mWindow.jMenuItemEditHorse.getAction();
					Action actionDelete = mWindow.jMenuItemDeleteHorse.getAction();
					
					actionEdit.setEnabled(true);
					actionDelete.setEnabled(true);
										
					mWindow.jPanelCommand.setEnabledAt("Edit", true);					
					mWindow.jPanelCommand.setEnabledAt("Delete", true);
					
					java.util.List<Horse> list = dao.getAllHorses();
					Horse horse = list.get(selectedRow);					
					
					int index = jTabbedInfoView.getTabCount();
					
					for (int i = 0; i < index; i++) {
						
						Component tabComp = jTabbedInfoView.getComponent(i);
						
						if (tabComp instanceof JScrollPane) {
							
							JScrollPane jSpComponent = (JScrollPane) tabComp;
							Component viewComp = jSpComponent.getViewport().getView();														
														
							if (viewComp instanceof HorseDetailsPanel) {
								
								HorseDetailsPanel panel = (HorseDetailsPanel) viewComp;
								
								String name = horse.getName().trim();
								String breed = horse.getBreed().trim();
								int age = horse.getAge();
								String box = horse.getBox().trim();
								String gender = horse.getGender();								
								
								panel.jTextFieldName.setText(name);
								panel.jTextFieldBreed.setText(breed);
								panel.jSpinnerAge.setValue(age);
								panel.jTextFieldBox.setText(box);
								panel.jComboBoxGender.setSelectedItem(gender);
								
							}							
						}
						
						if (tabComp instanceof CommentsPanel) {
							
							String comments = horse.getComments();
							CommentsPanel jPanelComments = (CommentsPanel) tabComp;							
							jPanelComments.jTextAreaComments.setText(comments);
							
						}
						
						if (tabComp instanceof ImagePanel) {
							
							int horseId = horse.getId();
														
							if (jTableHorseIndex.getRowCount() == 1) {
								
								ImageManager manager = 
									new ImageManager(horseId,mWindow);
							
								manager.start();
								
							}
							
							if (jTableHorseIndex.getRowCount() >= 1) {
								
								ImageManager manager = 
									new ImageManager(horseId,mWindow);
							
								manager.start();
								
							}
						}						
					}					
				}				
			}
		});		
	}
	
	private void setComponentMode(Component comp, 
		boolean mode) {
		
		LineBorder lineBorder = 
			new LineBorder(SystemColor.controlShadow);
		
		EmptyBorder textBorder = new EmptyBorder(6,10,6,6);
		EmptyBorder emptyBorder = new EmptyBorder(0,6,0,6);
		
		CompoundBorder compBorder = 
			new CompoundBorder(lineBorder,emptyBorder);
		
		if (comp instanceof JScrollPane) {
				
			JScrollPane scrollPane = (JScrollPane) comp;
			Component scrollComp = scrollPane.getViewport().getView();
				
			if (scrollComp instanceof HorseDetailsPanel) {
												
				HorseDetailsPanel panel = (HorseDetailsPanel) scrollComp;
				int compIndex = panel.getComponentCount();
					
				for (int i = 0; i < compIndex; i++) {
						
					Component formComp = panel.getComponent(i);
						
					if (formComp instanceof JTextField) {
						
						((JTextField) formComp).setCaretColor(Color.white);
						((JTextField) formComp).setEnabled(mode);
						
						if (!formComp.isEnabled()) {
							
							((JTextField) formComp).setBorder(compBorder);
						
						}						
						
						if (formComp.isEnabled()) {
							
							((JTextField) formComp).setBorder(emptyBorder);
							
						}						
					}					
						
					if (formComp instanceof JSpinner) {
						
						((JSpinner) formComp).setBorder(compBorder);
						((JSpinner) formComp).setEnabled(mode);
							
					}
						
					if (formComp instanceof JComboBox<?>) {
							
						((JComboBox<?>) formComp).setEnabled(mode);
							
					}
				}				
			}				
		}		
		
		if (comp instanceof CommentsPanel) {
			
			((CommentsPanel) comp).jTextAreaComments.setBorder(textBorder);
			((CommentsPanel) comp).jTextAreaComments.setEnabled(mode);
			
		}
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
        gbc.insets = new Insets(0,0,0,0);
        gbl.setConstraints(jSplitPaneMain,  gbc);
        this.add(jSplitPaneMain);
        
	}	
}
