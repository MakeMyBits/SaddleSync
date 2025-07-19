package components;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public class TimeSpinner extends JSpinner {
	
	private Calendar cal;
	private Date date;
	private SpinnerDateModel model;
	private JSpinner.DateEditor editor;
	
	public TimeSpinner() {
		
		this.initialize();
		this.configJSpinner();
		
	}
	
	private void initialize() {
		
		this.cal = createCalendar();
		this.date = cal.getTime();
		
        this.model = new SpinnerDateModel(date, null, null, Calendar.MINUTE);
        this.setModel(model);
        
        this.editor = new JSpinner.DateEditor(this, "HH:mm");
        		
	}
	
	private void configJSpinner() {
		
		this.setEditor(editor);
		
		JTextField jTextFieldView = editor.getTextField();
		jTextFieldView.setBorder(BorderFactory.createEmptyBorder(0,6,0,6));
		
	}
	
	private Calendar createCalendar() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal;
		
	}
	
	@Override
	public Dimension getMinimumSize() {
		
		Dimension dim = new Dimension();		
		dim.width = 150;
		dim.height = 33;
		
		return dim;
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		
		Dimension dim = new Dimension();
		dim.width = 150;
		dim.height = 33;
		
		return dim;
		
	}
	
	@Override
	public Dimension getMaximumSize() {
		
		Dimension dim = new Dimension();		
		dim.width = 150;
		dim.height = 33;
		
		return dim;
		
	}
	
}
