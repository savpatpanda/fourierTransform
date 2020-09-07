import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;

public class ArcLength {
    private double a,b,length;
    private Function f;
    
    public ArcLength(double a1, double b1, FunctionComponent funcComp){
        f = funcComp.getFunction();
        a = a1;
        b = b1;
        length = f.arcLength(a1,b1);
    }
    
    public int index(double x){
        return (int)((x+10)/Function.num);
    }
    
    public void draw(Graphics2D g2){
        int left = index(a);
        int right = index(b);
        g2.setColor(Color.BLACK);
        for(int i = left; i<right; i++){
            g2.setColor(Color.MAGENTA);
            g2.fill(new Ellipse2D.Double(500 + (50 * f.values.get(i).getX()), 500 - (50*f.values.get(i).getY()), 5, 5));
        }
        
        Font f = new Font(Font.SERIF,Font.BOLD,25);
        g2.setFont(f);
        g2.setColor(Color.BLACK);
        g2.drawString("Arc Length: "+Double.toString(length),650,30);
    }
}
