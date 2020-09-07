import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Font;

public class Integration {
    private double a,b,integ;
    private Function f;
    
    public Integration(double a1, double b1, FunctionComponent funcComp){
        f = funcComp.getFunction();
        a = a1;
        b = b1;
        integ = f.integrateSimpson(a, b);
    }
    
    public double convertToPixel(double value){
        double pixelX = 0;
        pixelX = 500 + 50*value;
        return pixelX;
    }
    
    public double convertToPixelY(double value){
        double pixelX = 0;
        pixelX = 500 - 50*value;
        return pixelX;
    }
    
    public double convertToX(double value){
        double x_val = (value-500)/50;
        return x_val;
    }
    
    public void draw(Graphics2D g2){
        double left = convertToPixel(a);
        double right = convertToPixel(b);
        g2.setColor(Color.BLUE);
        for(double i = left; i<right; i+=0.1){
            Line2D.Double line = new Line2D.Double(i,500,i,convertToPixelY(f.getValueAt(convertToX(i))));
            g2.draw(line);
        }
        
        Font f = new Font(Font.SERIF,Font.BOLD,25);
        g2.setFont(f);
        g2.setColor(Color.BLACK);
        g2.drawString("Area: "+Double.toString(integ),650,30);
    }
}
