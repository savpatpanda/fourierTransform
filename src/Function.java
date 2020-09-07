import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.*;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JComponent;

public class Function{
    private String funcExpression;
    public List<Point2D.Double> values = new ArrayList<Point2D.Double>();
    public static double num = 10E-3;
    
    public Function(String s){
        funcExpression = s;
        Expression e = new ExpressionBuilder(s).variables("x").build();
        for(double i = -10; i<=10; i+=num){
            e.setVariable("x",i);
            Point2D.Double point = new Point2D.Double(i,e.evaluate());
            values.add(point);
        }
    }
    
    //returns the static variable of precision
    public double getPrecision(){
        return num;
    }
    
    public void setPrecision(double x){
        num = x;
    }
 
    //converts the spacing to the index in the values array
    public int index(double x){
        return (int)((x+10)/num);
    }
    
    //returns the function in String notation
    public String getExpression(){
        return funcExpression;
    }
   
    //simple evaluation
    public double getValueAt(double x){
        return values.get(this.index(x)).getY();
    }
    
    //uses secant line approximation to give the approximate the value of the tangent line slope
    public double differentiate(double t){
        double slope = (values.get(this.index(t)+1).getY()-values.get(this.index(t)-1).getY())/(2*num);
        return slope;
    }
    
    //not implemented yet - will be in future versions
    public String concavity(double t){
        double second = ((differentiate(t+num))-(differentiate(t-num)))/(2*num);
        if(Math.abs(second)<num){
            return "Zero";
        }else if(second>0){
            return "Positive/Up";
        }else{
            return "Negative/Down";
        }
    }
    
    //does not use Simpson's rule - only left rectangle approximation
    public double integrate(double t1, double t2){
        double area = 0;
        for(int i = index(t1); i<index(t2); i++){
            area += values.get(i).getY()*num;
        }
        return area;
    }
    
    //Simpson's rule with the construction of parabolas and their respective areas
    public double integrateSimpson(double t1, double t2){
        double sum = 0;
        for(int i = index(t1); i<index(t2); i=i+2){
            sum += values.get(i).getY()+4*values.get(i+1).getY()+values.get(i+2).getY();
        }
        return sum*num/3;
    }
    
    //utilizes pythagorean's theorem at a small scale to sum the regions
    public double arcLength(double t1, double t2){
        double output = 0;
        for(int i = index(t1); i<index(t2); i++){
            output += Math.sqrt((Math.pow((values.get(i).getY()-values.get(i+1).getY()),2))+Math.pow(num,2));
        }
        return output;
    }
 
    //draws all graph components
    public void draw(Graphics2D g2){
        //Construct axes and title
        Line2D.Double y = new Line2D.Double(500, 0, 500, 1000);
        Line2D.Double x = new Line2D.Double(0, 500, 1000, 500);
        for(int i = -10; i <= 10; i ++){
            g2.draw(new Line2D.Double(50*i+500, 495, 50*i+500, 505));
            g2.draw(new Line2D.Double(495, 50*i+500, 505, 50*i+500));
            if(Math.abs(i)!=10){
                //labels for axes
                g2.drawString(Integer.toString(i),50*i+505 , 520);
                g2.drawString(Integer.toString(i), 480, 50*i+500);   
            }
        }
        g2.draw(y);
        g2.draw(x);

        //graphs points with circles
        for(Point2D.Double p : values){
            g2.setColor(Color.BLACK);
            g2.fill(new Ellipse2D.Double(500 + (50 * p.x), 500 - (50*p.y), 3, 3));
        }      
   }
}
