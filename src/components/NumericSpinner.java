package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumericSpinner extends JSpinner {

	private boolean mode;
	private int width;
	
    public NumericSpinner(boolean mode, 
    	int width,int initialValue, 
    	int min, int max, int step) {
        
    	super(new SpinnerNumberModel(initialValue, min, max, step));
        
    	this.mode = mode;
    	this.width = width;
        configureEditor();
        
    }

    private void configureEditor() {
    	
        // Förväntar sig att editorn är en NumberEditor
        if (getEditor() instanceof NumberEditor) {
        	
            NumberEditor editor = (NumberEditor) getEditor();
            JTextField textField = editor.getTextField();
            
            if (!mode) {
            
            	textField.setBorder(BorderFactory.createEmptyBorder(0,0,0,3));
            
            }
            
            if (mode) {
            	
            	textField.setBorder(BorderFactory.createEmptyBorder(0,6,0,3));
            	
            }
            
            textField.setDisabledTextColor(SystemColor.textText);
            
            // Vänsterjustera texten
            textField.setHorizontalAlignment(JTextField.LEFT);

            // Lägg till key listener för att tillåta endast siffror
            textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    
                	char c = e.getKeyChar();
                    
                	if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && 
                    	c != KeyEvent.VK_DELETE) {
                        
                    	e.consume(); // Ignorera ogiltiga tecken
                    
                    }
                }
            });
        }
    }
    
    @Override
    public Dimension getMinimumSize() {
    	
    	Dimension dim = new Dimension();
    	
    	if (width != 0) {
    		
    		dim.width = width;
    		
    	}
    	
    	dim.height = 33;
    	
    	return dim;
    	
    }
    
    @Override
    public Dimension getPreferredSize() {
    	
    	Dimension dim = new Dimension();
    	
    	if (width != 0) {
    		
    		dim.width = width;
    		
    	}
    	
    	dim.height = 33;
    	
    	return dim;
    	
    }
    
    @Override
    public Dimension getMaximumSize() {
    	
    	Dimension dim = new Dimension();
    	
    	if (width != 0) {
    		
    		dim.width = width;
    		
    	}
    	
    	dim.height = 33;
    	
    	return dim;
    	
    }
    
}
