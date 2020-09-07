import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.Hashtable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FunctionViewer extends JFrame{
     
    private FunctionComponent funcComp;
    private JTextField functionInput, xVal, from, to, arclengthTo, arclengthFrom;
    private JButton functionButton, differentiate, integrate, fourier, arclengthButton;
    private JPanel pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11;
    private TangentLineComponent tanComp;
    private IntegrationComponent intComp;
    private JLabel diff_output = new JLabel(" ");
    private JLabel integ_output = new JLabel(" ");
    private JLabel arc_output = new JLabel("");
    final private JLabel blank = new JLabel("     ");
    private JSlider precision;
    private JComboBox dropDown;
    
    public FunctionViewer(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Desmos 2.0");
        setLayout(new GridLayout(1,2));
        assemble();
    }
        
    public void assemble(){
        pane1 = new JPanel();
        pane1.setSize(1000,1000);
        pane1.setBounds(0,0,1100,1000);
        
        //establishing grid layout for each pane
        pane2 = new JPanel();
        pane2.setSize(1000,this.getWidth()-1000);
        pane2.setBounds(1100,0,this.getWidth()-1100,1000);
        pane2.setLayout(new GridLayout(7,1));
        add(pane1);
        add(pane2);
        pane3 = new JPanel();
        pane4 = new JPanel();
        pane5 = new JPanel();
        pane6 = new JPanel();
        pane7 = new JPanel();
        pane8 = new JPanel();
        pane9 = new JPanel();
        pane2.add(pane3);
        pane2.add(pane7);
        pane2.add(pane8);
        pane2.add(pane4);
        pane2.add(pane5);
        pane2.add(pane9);
        pane2.add(pane6);
        
        //scroll bar for precision - interacts with static class variable in Function class
        precision = new JSlider(JSlider.HORIZONTAL,0,150,0);
        precision.setMajorTickSpacing(50);
        precision.setPaintLabels(true);
        precision.setPaintTicks(true);        
        Hashtable<Integer, JLabel> marks = new Hashtable<Integer, JLabel>();
        marks.put(0,new JLabel("10E-3"));
        marks.put(50,new JLabel("10E-4"));
        marks.put(100,new JLabel("10E-5"));
        marks.put(150,new JLabel("10E-6"));
        precision.setLabelTable(marks);
        precision.setSnapToTicks(true);
        precision.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                if(precision.getValue()<=25){
                    precision.setValue(0);
                    Function.num = 10E-3;
                }else if(precision.getValue()>25&&precision.getValue()<=75){
                    precision.setValue(50);
                    Function.num = 10E-4;
                }else if(precision.getValue()>75&&precision.getValue()<=125){
                    precision.setValue(100);
                    Function.num = 10E-5;
                }else if(precision.getValue()>125){
                    precision.setValue(150);
                    Function.num = 10E-6;
                }
            }
        });
        precision.setEnabled(true);
        precision.setMajorTickSpacing(1);
       
        Font labelFont = new Font(Font.SERIF, Font.PLAIN, 20);
        JLabel enter = new JLabel("Enter your function here: ");
        enter.setFont(labelFont);
        pane3.add(enter);
        
        functionInput = new JTextField();
        functionInput.setPreferredSize(new Dimension(300,30));
        labelFont = new Font(Font.SERIF, Font.PLAIN, 16);
        functionInput.setFont(labelFont);
        pane3.add(functionInput);

        //calls the FunctionComponent that was created in order to graph it on the left of the screen
        functionButton = new JButton("Graph");
        functionButton.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        functionButton.setSize(200, 15);
        functionButton.setLocation(1100,200);
        functionButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent ae){
                if(functionInput.getText().isEmpty()){
                    funcComp = new FunctionComponent("1");
                }else{
                    funcComp = new FunctionComponent(functionInput.getText());
                }
                funcComp.setSize(1000,1000);
                pane1.add(funcComp);
                repaint();
                functionInput.setEditable(false);
                functionButton.setEnabled(false);
                dropDown.setEnabled(true);
                precision.setEnabled(false);
            }
        });        
        pane7.add(functionButton);   
        pane3.add(precision);
       
        //drop down menu to give option of type of operation
        String[] options = {"","Differentiate","Integrate","Arc Length","Fourier Series"};
        dropDown = new JComboBox(options);
        dropDown.setFont(new Font(Font.SERIF,Font.PLAIN,20));
        dropDown.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(dropDown.getSelectedItem().equals("Differentiate")){
                      differentiate.setEnabled(true);
                      xVal.setEditable(true);
                      from.setEditable(false);
                      to.setEditable(false);
                      integrate.setEnabled(false);
                      fourier.setEnabled(false);
                      arclengthFrom.setEditable(false);
                      arclengthTo.setEditable(false);
                      arclengthButton.setEnabled(false);
                }else if(dropDown.getSelectedItem().equals("Integrate")){
                      differentiate.setEnabled(false);
                      xVal.setEditable(false);
                      from.setEditable(true);
                      to.setEditable(true);
                      integrate.setEnabled(true);
                      fourier.setEnabled(false);
                      arclengthFrom.setEditable(false);
                      arclengthTo.setEditable(false);
                      arclengthButton.setEnabled(false);
                }else if(dropDown.getSelectedItem().equals("Fourier Series")){
                      differentiate.setEnabled(false);
                      xVal.setEditable(false);
                      from.setEditable(false);
                      to.setEditable(false);
                      integrate.setEnabled(false);
                      fourier.setEnabled(true);
                      arclengthFrom.setEditable(false);
                      arclengthTo.setEditable(false);
                      arclengthButton.setEnabled(false);
                }else if(dropDown.getSelectedItem().equals("Arc Length")){
                      differentiate.setEnabled(false);
                      xVal.setEditable(false);
                      from.setEditable(false);
                      to.setEditable(false);
                      integrate.setEnabled(false);
                      fourier.setEnabled(false);
                      arclengthFrom.setEditable(true);
                      arclengthTo.setEditable(true);
                      arclengthButton.setEnabled(true);
                }
            }
        });
        pane8.add(dropDown);
        
        //calls the TangentLine class in order to graph the corresponding slope and the tangent line itself
        differentiate = new JButton("Differentiate");
        differentiate.setSize(200,15);
        differentiate.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        differentiate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                    tanComp = new TangentLineComponent(Double.parseDouble(xVal.getText()),funcComp);
                    tanComp.setSize(1000,1000);
                    pane1.add(tanComp);
                    pane1.repaint();
            }
        });
        
        //calls the IntegrationComponent class in order to shade the region under the curve that belongs to the integral in question
        integrate = new JButton("Integrate");
        integrate.setSize(200,15);
        integrate.setFont(new Font(Font.SERIF, Font.PLAIN, 20)); 
        integrate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                    intComp = new IntegrationComponent(Double.parseDouble(from.getText()),Double.parseDouble(to.getText()),funcComp);
                    intComp.setSize(1000,1000);
                    pane1.add(intComp);
                    System.out.print(Double.toString(funcComp.getFunction().integrate(Double.parseDouble(from.getText()),Double.parseDouble(to.getText()))));                    
                    repaint();
            }
        });
        
        //UI for the user to input values for the differentiating function
        JLabel at = new JLabel("Differentiate at  ");
        at.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        xVal = new JTextField(5);
        xVal.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JLabel from1 = new JLabel("From x = ");
        from1.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        from = new JTextField(5);
        from.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JLabel to1 = new JLabel("   To x = ");
        to1.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        to = new JTextField(5);
        to.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        pane4.add(at);
        pane4.add(xVal);
        pane4.add(differentiate);
        pane4.add(diff_output);
        pane5.add(from1);
        pane5.add(from);
        pane5.add(to1);
        pane5.add(to);
        pane5.add(blank);
        pane5.add(integrate);
        pane5.add(integ_output);
        
        //Calls the Fourier Series class in order to create the function for the approximation of the fourier series for the function
        fourier = new JButton("Graph Fourier Series");
        fourier.setSize(200,15);
        fourier.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Function f = funcComp.getFunction();
                FourierSeries fs = new FourierSeries(f);
                FourierComponent fourComp = new FourierComponent(fs);
                fourComp.setSize(1000,1000);
                funcComp.add(fourComp);
                funcComp.repaint();
            }
        });
        fourier.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        pane6.add(fourier);
        
        JLabel arcLengthFrom1 = new JLabel("From x = ");
        arcLengthFrom1.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        arclengthFrom = new JTextField(5);
        arclengthFrom.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JLabel arcLengthTo1 = new JLabel("   To x = ");
        arcLengthTo1.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        arclengthTo = new JTextField(5);
        arclengthTo.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        
        //Creates an arclength component that repaints the curve in the region in question
        arclengthButton = new JButton("Calculate Arc Length");
        arclengthButton.setSize(200,15);
        arclengthButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                ArcLengthComponent ar = new ArcLengthComponent(Double.parseDouble(arclengthFrom.getText()), Double.parseDouble(arclengthTo.getText()),funcComp);    
                ar.setSize(1000,1000);
                funcComp.add(ar);
                repaint();
                System.out.print(funcComp.getFunction().arcLength(Double.parseDouble(arclengthFrom.getText()),Double.parseDouble(arclengthTo.getText())));
            }
        });
        arclengthButton.setFont(new Font(Font.SERIF, Font.PLAIN, 22));    
        pane9.add(arcLengthFrom1);
        pane9.add(arclengthFrom);
        pane9.add(arcLengthTo1);
        pane9.add(arclengthTo);
        pane9.add(arclengthButton);
        pane9.add(arc_output);
        
        //ensures the default setting of all buttons and fields so that it is appropriate for the given situation
        dropDown.setEnabled(false);
        differentiate.setEnabled(false);
        xVal.setEditable(false);
        from.setEditable(false);
        to.setEditable(false);
        integrate.setEnabled(false);
        arclengthFrom.setEditable(false);
        arclengthTo.setEditable(false);
        arclengthButton.setEnabled(false);
        fourier.setEnabled(false);
    }
    
    public static void main(String[] args){
        new FunctionViewer().setVisible(true);   
    }
}