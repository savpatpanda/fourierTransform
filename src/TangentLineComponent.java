import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TangentLineComponent extends JComponent{
    
    private TangentLine tang;
    private Function f;
    
    public TangentLineComponent(double x, FunctionComponent funcComp){
        f = funcComp.getFunction();
        tang = new TangentLine(x,f.getValueAt(x),f.differentiate(x));
    }
    
    @Override
    public void paintComponent(Graphics g){
      Graphics2D g2 = (Graphics2D) g;
      tang.draw(g2);
    }
}
