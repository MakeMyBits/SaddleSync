package actions;

import java.awt.*;
import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import panels.HorsesPanel;
import windows.MainWindow;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class ExportAction extends AbstractAction {

    private MainWindow mWindow;

    public ExportAction(MainWindow mWindow) {
    	
        this.mWindow = mWindow;
        
        super.putValue(Action.NAME, "Export horses");
            
    }

    private void exportToPdf(JTable table, File file) {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);

            float marginLeft = 50;
            float startY = 750;
            float rowHeight = 20;
            float colWidth = 150;

            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 14);
            content.newLineAtOffset(marginLeft, startY);
            content.showText("Horses");
            content.endText();

            float y = startY - 30;
            for (int col = 0; col < table.getColumnCount(); col++) {
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 12); // font för rubriker
                content.newLineAtOffset(marginLeft + col * colWidth, y);
                content.showText(table.getColumnName(col));
                content.endText();
            }

            y -= rowHeight;
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Object cellValue = table.getValueAt(row, col);
                    String text = cellValue != null ? cellValue.toString() : "";
                    content.beginText();
                    content.setFont(PDType1Font.HELVETICA, 12); // font för celler
                    content.newLineAtOffset(marginLeft + col * colWidth, y);
                    content.showText(text);
                    content.endText();
                }
                
                y -= rowHeight;
                
            }

            content.close();
            doc.save(file);

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                mWindow,
                "Export error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    	super.putValue("enabled", Boolean.FALSE);
    	
    	JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export to PDF");

        if (chooser.showSaveDialog(mWindow) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".pdf")) {
                file = new File(file.getAbsolutePath() + ".pdf");
            }

            int index = mWindow.jPanelMainView.getComponentCount();
            for (int i = 0; i < index; i++) {
            
            	Component formComp = mWindow.jPanelMainView.getComponent(i);

                if (formComp instanceof HorsesPanel) {
                    
                	HorsesPanel panel = (HorsesPanel) formComp;
                    this.exportToPdf(panel.jTableHorseIndex, file);
                
                }
            }
        }
        
        super.putValue("enabled", Boolean.TRUE);
        
    }
}
