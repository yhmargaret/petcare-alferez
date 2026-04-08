/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;
import java.awt.*;
import java.awt.print.*;
import javax.swing.JPanel;

public class PanelPrinter implements Printable {

    private JPanel panelToPrint;

    public PanelPrinter(JPanel panelToPrint) {
        this.panelToPrint = panelToPrint;
    }

@Override
public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
    if (pageIndex > 0) {
        return Printable.NO_SUCH_PAGE;
    }

    Graphics2D g2d = (Graphics2D) graphics;
    
    // Set page format to bond paper (8.5 x 11 inches)
    pageFormat.setOrientation(PageFormat.PORTRAIT);
    Paper paper = new Paper();
    double width = 8.5 * 72; // 612 points
    double height = 11 * 72; // 792 points
    paper.setSize(width, height);
    paper.setImageableArea(36, 36, width - 72, height - 72); // 1-inch margins (36pt)
    pageFormat.setPaper(paper);

    // Get printable area size
    double imageableWidth = pageFormat.getImageableWidth();
    double imageableHeight = pageFormat.getImageableHeight();

    // Get panel size
    double panelWidth = panelToPrint.getWidth();
    double panelHeight = panelToPrint.getHeight();

    // Compute scale to fit panel inside printable area
    double scaleX = imageableWidth / panelWidth;
    double scaleY = imageableHeight / panelHeight;
    double scale = Math.min(scaleX, scaleY); // Maintain aspect ratio

    // Translate to the top-left corner of the imageable area
    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

    // Scale the panel
    g2d.scale(scale, scale);

    // Render the panel
    panelToPrint.printAll(g2d);

    return Printable.PAGE_EXISTS;
}


    public void printPanel() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

}

