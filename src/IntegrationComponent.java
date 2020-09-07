import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class IntegrationComponent extends JComponent{
    private Integration integ;
    private Function f;
    
    public IntegrationComponent(double a, double b, FunctionComponent func){
        integ = new Integration(a, b, func);
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        integ.draw(g2);
    }
}
