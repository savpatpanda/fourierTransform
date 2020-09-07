import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Font;

public class TangentLine {
    private double x,y,m;
    
    public TangentLine(double x1, double y1, double m1){
        x = x1;
        y = y1;
        m = m1;
    }
    
    public void draw(Graphics2D g2){
        double finalx1 = 500+50*x;
        double finaly1 = 500-50*y;
        while(finalx1>0 && (finaly1<1000||finaly1>0)){
            finalx1--;
            finaly1+=m;
        }
        
        double finalx2 = 500+50*x;
        double finaly2 = 500-50*y;
        while(finalx2<1000 && (finaly2<1000||finaly2>0)){
            finalx2++;
            finaly2-=m;
        }
        Line2D.Double line = new Line2D.Double(finalx1,finaly1,finalx2, finaly2);
        g2.setColor(Color.RED);
        g2.draw(line);
        Font f = new Font(Font.SERIF,Font.BOLD,25);
        g2.setFont(f);
        g2.setColor(Color.BLACK);
        g2.drawString("Slope: "+Double.toString(m),650,30);
    }
}
