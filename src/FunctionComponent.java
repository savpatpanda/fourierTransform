import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class FunctionComponent extends JComponent{
    
    private Function func;
    
    public FunctionComponent(String s){
        func = new Function(s);
    }
    
    public Function getFunction(){
        return func;
    }
    
    @Override
    public void paintComponent(Graphics g){
      Graphics2D g2 = (Graphics2D) g;
      func.draw(g2);
    }
}
