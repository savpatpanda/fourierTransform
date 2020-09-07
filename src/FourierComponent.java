import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class FourierComponent extends JComponent{
    
    private FourierSeries f;
    
    public FourierComponent(FourierSeries f1){
      f = f1;
    }
    
    @Override
    public void paintComponent(Graphics g){
      Graphics2D g2 = (Graphics2D) g;
      f.draw(g2);
    }
}
