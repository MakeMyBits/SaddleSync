package windows;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import dao.HorsesDao;
import panels.CommandPanel;
import panels.FeedingPanel;
import panels.HomePanel;
import panels.HorsesPanel;

import javax.swing.*;

import actions.AddHorseAction;
import actions.DeleteHorseAction;
import actions.EditHorseAction;
import actions.ExportAction;
import actions.QuitAction;
import actions.SearchHorseAction;

public class MainWindow extends JFrame {
	
	private String title;
	
	public ActionMap actionMap;
	
	private JMenuItem jMenuItemExport;
	private JMenuItem jMenuItemQuit;
	
	private JMenuItem jMenuItemAddHorse;
	public JMenuItem jMenuItemEditHorse;
	public JMenuItem jMenuItemDeleteHorse;
	private JMenuItem jMenuItemSearchHorses;
	
	private JMenu jMenuFile;
	private JMenu jMenuHorses;
		
	private JMenuBar jMenuBarView;
	
	public HorsesDao horsesDao;
		
	private GridBagConstraints gbc;
	private GridBagLayout gbl;
	public CardLayout card;	
	
	private JPanel jPanelTreeView;
	public JPanel jPanelMainView;
	public CommandPanel jPanelCommand;
	
	private JSplitPane jSplitPaneMain;
	
	private JPanel jPanelContainer;
	
	public MainWindow(String title) {
		
		this.title = title;
		
		this.initialize();
		this.configJMenuBar();
		this.configJFrame();
		this.configJTree();
		this.createLayout();
		this.registerEvents();
		
	}
	
	private void initialize() {
		
		this.actionMap = new ActionMap();
		
		this.jMenuItemExport = createJMenuItem(new ExportAction(this));
		this.jMenuItemQuit = createJMenuItem(new QuitAction(this));
	
		this.jMenuItemAddHorse = createJMenuItem(new AddHorseAction(this));
		this.jMenuItemEditHorse = createJMenuItem(new EditHorseAction(this));
		this.jMenuItemDeleteHorse = createJMenuItem(new DeleteHorseAction(this));
		this.jMenuItemSearchHorses = createJMenuItem(new SearchHorseAction(this));
				
		this.jMenuFile = new JMenu("File");
		this.jMenuHorses = new JMenu("Horses");
		
		this.jMenuBarView = new JMenuBar();
		
		this.horsesDao = new HorsesDao();
			
		this.gbc = new GridBagConstraints();
		this.gbl = new GridBagLayout();
		
		this.card = new CardLayout();
		
		this.jPanelTreeView = new JPanel(gbl);
		this.jPanelMainView = new JPanel(card);
		this.jPanelCommand = new CommandPanel(this);
		this.jPanelContainer = new JPanel(gbl);
		
	}
	
	private void configJMenuBar() {
		
		this.jMenuFile.add(jMenuItemExport);
		this.jMenuFile.add(new JSeparator(JSeparator.HORIZONTAL));
		this.jMenuFile.add(jMenuItemQuit);
		this.jMenuBarView.add(jMenuFile);
		
		this.jMenuHorses.add(new JSeparator(JSeparator.HORIZONTAL));
		this.jMenuBarView.add(jMenuHorses);		
						
		jMenuItemExport.setAccelerator(KeyStroke.getKeyStroke("control E"));
		jMenuItemQuit.setAccelerator(KeyStroke.getKeyStroke("control Q"));

		jMenuItemAddHorse.setAccelerator(KeyStroke.getKeyStroke("control N"));        
		jMenuItemEditHorse.setAccelerator(KeyStroke.getKeyStroke("control M"));       
		jMenuItemDeleteHorse.setAccelerator(KeyStroke.getKeyStroke("control D"));     
		jMenuItemSearchHorses.setAccelerator(KeyStroke.getKeyStroke("control F"));   

		jMenuHorses.add(jMenuItemAddHorse);
		jMenuHorses.add(jMenuItemEditHorse);
		jMenuHorses.add(jMenuItemDeleteHorse);
		this.jMenuHorses.add(new JSeparator(JSeparator.HORIZONTAL));
		jMenuHorses.add(jMenuItemSearchHorses);
		
	}
	
	private void configJFrame() {
		
		this.setTitle(title);
		this.setSize(800,600);
		this.setJMenuBar(jMenuBarView);
		this.setLocationRelativeTo(null);
		this.setContentPane(jPanelContainer);
		
	}
	
	private void configJTree() {
		
		
	}
	
	private void createLayout() {
		
		FeedingPanel jPanelFeeding = new FeedingPanel(this);
		JScrollPane jSpFeedingView = new JScrollPane(jPanelFeeding);
		
		jSpFeedingView.setBorder(null);
		jSpFeedingView.setViewportBorder(null);
				
		this.jPanelMainView.add(new HomePanel(this), "home");
		this.jPanelMainView.add(new HorsesPanel(this),"horses");
		this.jPanelMainView.add(jSpFeedingView, "feeding");
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.NONE;
        gbc.insets = new Insets(3,3,3,0);
        gbl.setConstraints(jPanelCommand,  gbc);
        this.jPanelContainer.add(jPanelCommand);
		
		gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = GridBagConstraints.BOTH;
        gbc.weighty = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,0,0);
        gbl.setConstraints(jPanelMainView,  gbc);
        this.jPanelContainer.add(jPanelMainView);
		
	}
	
	private void registerEvents() {
		
		this.addWindowListener(new WindowAdapter() {
						
			@Override
			public void windowClosing(WindowEvent e) {
				
				MainWindow.this.dispose();
				System.exit(0);
				
			}			
		});
	}
	
	public void showPanel(String panelName) {
		
        this.card.show(jPanelMainView, panelName);
    
	}
	
	private void initAddHorseDialog() {
		
		AddHorseDialog dialog = new AddHorseDialog(this);
		dialog.setVisible(true);
		
	}
					
	private JMenuItem createJMenuItem(Action action) {
		
		String value = (String) action.getValue(Action.NAME);
		
		Dimension dim = new Dimension(150,33);
		JMenuItem item = new JMenuItem();
		
		item.setFocusable(false);
		item.setAction(action);
		this.actionMap.put(value,action);
		
		item.setMinimumSize(dim);
		item.setPreferredSize(dim);
		item.setMaximumSize(dim);
				
		return item;
		
	}
	
}
