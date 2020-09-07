import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ArcLengthComponent extends JComponent{
    private ArcLength arc;
    private Function f;
    
    public ArcLengthComponent(double a, double b, FunctionComponent func){
        arc = new ArcLength(a, b, func);
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        arc.draw(g2);
    }
}
