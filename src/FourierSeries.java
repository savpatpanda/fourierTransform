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

public class FourierSeries {
    private Function FourierFunc;
    private String funcExpression;
    private List<Point2D.Double> values = new ArrayList<Point2D.Double>();
    private double num = 10E-5;
    private int terms = 10;
    
    public FourierSeries(Function f1){
        FourierFunc = create(f1);
        funcExpression = FourierFunc.getExpression();
        Expression e1 = 
            new ExpressionBuilder(funcExpression).variables("x").build();
        for(double i = -10; i<=10; i+=num){
            e1.setVariable("x",i);
            Point2D.Double point = new Point2D.Double(i,e1.evaluate());
            values.add(point);
        }
    }
    
    public Function create(Function RelatedFunc){
        List<Double> a = new ArrayList<Double>();
        List<Double> b = new ArrayList<Double>();        
        double a0 = 
            RelatedFunc.integrateSimpson(-Math.PI,Math.PI) / (2*Math.PI);
        for(int i = 1; i<=terms; i++){
            String func = "("+RelatedFunc.getExpression()+")*cos("+i+"x)";
            Function f = new Function(func);
            double coeff = f.integrateSimpson(-Math.PI,Math.PI) / Math.PI;
            a.add(coeff);            
        }
        for(int i = 1; i<=terms; i++){
            String func = "("+RelatedFunc.getExpression()+")*sin("+i+"x)";
            Function f = new Function(func);
            double coeff = f.integrateSimpson(-Math.PI,Math.PI) / Math.PI;
            b.add(coeff);
        }
        String output ="";
        if(Math.abs(a0)>0.01){
            output+= Double.toString(a0);
        }
        for(int i = 0; i<terms; i++){
            if(Math.abs(a.get(i))>0.01){
            if(a.get(i)<0){
                output+= "-"+Math.abs(a.get(i))+"cos("+(i+1)+"x)";
            }else{
                if(output.equals("")){
                    output+=Math.abs(a.get(i))+"cos("+(i+1)+"x)";
                }else{
                output+= "+"+Math.abs(a.get(i))+"cos("+(i+1)+"x)";                
                }
            }
            }
        }
        for(int i = 0; i<terms; i++){
            if(Math.abs(b.get(i))>0.01){
            if(b.get(i)<0){
                output+= "-"+Math.abs(b.get(i))+"sin("+(i+1)+"x)";
            }else{
                if(output.equals("")){
                output+= Math.abs(b.get(i))+"sin("+(i+1)+"x)";
                }else{
                output+= "+"+Math.abs(b.get(i))+"sin("+(i+1)+"x)";    
                }
            }
            }
        }
        Function f = new Function(output);
        System.out.print(f.getExpression());
        return f;
    }   
    
    public Function getFourierSeries(){
        return FourierFunc;
    }
    
    public void draw(Graphics2D g2){
        for(Point2D.Double p : values){
          g2.setColor(new Color(15,200,15));
          g2.fill(new Ellipse2D.Double(500 + (50 * p.x), 500 - (50*p.y), 3, 3));
        }  
    }
}
