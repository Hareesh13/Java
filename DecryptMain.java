import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DecryptMain extends JApplet implements ActionListener
{
	JButton b1,b2,b3;
	JFrame f;
DecryptMain()
{
	f=new JFrame("Menu Page");
	f.setSize(400,150);
	f.setLayout(new BorderLayout());
	f.setLocation(400,300);
	JPanel p=new JPanel();
	JPanel p2=new JPanel();

	b1=new JButton("Decrypt Message");
	b2=new JButton("Decrypt File");
	b3=new JButton("Exit");
	b1.addActionListener(this);
	b2.addActionListener(this);
	b3.addActionListener(this);
	b1.setFont( new Font( "Times New Roman", Font.BOLD,16 )); 
	b2.setFont( new Font( "Times New Roman", Font.BOLD,16 )); 
	b3.setFont( new Font( "Times New Roman", Font.BOLD,18 )); 
	b3.setToolTipText("Do you want to Exit");
	p.add(b1);
	p.add(b2);
	p2.add(b3);
	f.add(p,BorderLayout.CENTER);
	f.add(p2,BorderLayout.SOUTH);
	f.show();
}
	public void actionPerformed(ActionEvent ae)
	{
		String s=ae.getActionCommand();
	if(s.equals("Decrypt Message"))
	{
	new DecryptMessage().setVisible(true);
	f.setVisible(false);
	//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	else if(s.equals("Decrypt File"))
	{
		new DecryptFile().setVisible(true);
	f.setVisible(false);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	else if(s.equals("Exit"))
	{
		new MainDemo().setVisible(true);
		f.setVisible(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
	public static void main(String a[])
	{
		new DecryptMain();
	}
}