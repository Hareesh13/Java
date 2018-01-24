import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane.*;

public class LogInDemo extends JFrame implements ActionListener
{
JLabel l1,l2,l3;
JTextField t1;
JPasswordField t2;
JButton b1,b2;
JFrame f;
LogInDemo()
{
f = new JFrame("Image Steganographic System");
f.setSize(800,200);
f.setLayout(new BorderLayout());
f.setLocation(400,300);
JPanel p=new JPanel();

JPanel m=new JPanel();

JPanel p1=new JPanel();
JPanel p2=new JPanel();
JPanel p3=new JPanel();
setLayout( new BoxLayout( getContentPane( ), BoxLayout.Y_AXIS ) );
l1=new JLabel("Image Steganographic System");
l2=new JLabel("Enter UserName");
t1=new JTextField(15);
l3= new JLabel("Enter Password");
t2=new JPasswordField(15);
b1=new JButton("LogIn");
b2=new JButton("Close");
b1.addActionListener(this);
b2.addActionListener(this);
b1.setForeground( Color.red );
b2.setForeground( Color.red );
l1.setForeground( Color.magenta );
l1.setFont( new Font( "Verdana", Font.BOLD,20 )); 
l2.setFont( new Font( "Verdana", Font.BOLD,14 )); 
l3.setFont( new Font( "Verdana", Font.BOLD,14 )); 
b1.setHorizontalTextPosition( JButton.LEFT );
b1.setFont( new Font( "Verdana", Font.BOLD,16 )); 
b2.setHorizontalTextPosition( JButton.RIGHT );
b2.setFont( new Font( "Verdana", Font.BOLD,16 )); 
b2.setToolTipText("Do you want to Exit");
b1.setToolTipText("Click Here");
p.add(l1);
p1.add(l2);
p1.add(t1);
p1.add(l3);
p1.add(t2);
p3.add(b1);
p3.add(b2);
f.add(p,BorderLayout.NORTH);
f.add(p1,BorderLayout.CENTER);
f.add(p2,BorderLayout.EAST);
f.add(p3,BorderLayout.SOUTH);
f.show();
}
public void actionPerformed(ActionEvent ae)
{
String s=ae.getActionCommand();
try
{
	if(s.equals("LogIn"))
	{
		if(t1.getText().equals("123") && t2.getText().equals("123"))
		{
	  		new MainDemo().setVisible(true);
			f.setVisible(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Invalid Username / Password");
			t1.setText("");
			t2.setText("");
		}
	}
 else if(s.equals("Close"))
	   {
	System.exit(0);     
	     }
}
catch(Exception e)
{
System.out.println(e);
}
}
public static void main(String a[])
{
new LogInDemo();
}
}
