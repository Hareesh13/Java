
//DecodeMessage.java
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.lang.*;
import java.io.*;

public class DecryptFile extends JFrame implements ActionListener
{
	JFrame f=new JFrame();
	JButton open = new JButton("Open"), decode = new JButton("Decode"),
	reset = new JButton("Reset"),close=new JButton("Close");
	JTextArea message = new JTextArea(10,3);
	BufferedImage image = null;
	JScrollPane imagePane = new JScrollPane();
	//JPasswordField t1=new JPasswordField(8);
	//JPasswordField t2=new JPasswordField(8);
	//JLabel ll=new JLabel("Enter Public Key:");
	//JLabel l2=new JLabel("Enter Private Key:");

public DecryptFile() 
{
	super("Decrypt Steganography Image into Text File");
	assembleInterface();

	this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
	this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().
	getMaximumWindowBounds());
	this.setVisible(true);
}

private void assembleInterface() 
{
	JPanel p = new JPanel(new FlowLayout());
	p.add(open);
	p.add(decode);
	p.add(reset);
	//p.add(ll);
	//p.add(t1);
                 	//p.add(l2);
	//p.add(t2);
	p.add(close);
	f.add(p);
	this.getContentPane().add(p, BorderLayout.NORTH);
	open.addActionListener(this);
	decode.addActionListener(this);
	reset.addActionListener(this);
	close.addActionListener(this);
	
	close.setMnemonic('C');
	open.setMnemonic('O');
	decode.setMnemonic('D');
	reset.setMnemonic('R');
	close.setToolTipText("Do you want to Exit");
	//t1.setToolTipText("Enter Public Key");
	//t2.setToolTipText("Enter Private Key");

	p = new JPanel(new GridLayout(1,1));
	p.add(new JScrollPane(message));
	message.setFont(new Font("Arial",Font.BOLD,20));
	p.setBorder(BorderFactory.createTitledBorder("Decoded message"));
	message.setEditable(false);
	this.getContentPane().add(p, BorderLayout.SOUTH);

	imagePane.setBorder(BorderFactory.createTitledBorder("Steganographed Image"));
	this.getContentPane().add(imagePane, BorderLayout.CENTER);
}

public void actionPerformed(ActionEvent ae) 
{
Object o = ae.getSource();
if(o == open)
openImage();
else if(o == decode)
{
decodeMessage();
JOptionPane.showMessageDialog(this,"Decode is Successfully");
}
else if(o == reset) 
resetInterface();
else if(o==close)
{
new DecryptMain().setVisible(true);
this.setVisible(false);
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}

}

private java.io.File showFileDialog(boolean open) 
{
JFileChooser fc = new JFileChooser("Open an image");
javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() 
{
public boolean accept(java.io.File f) 
{
String name = f.getName().toLowerCase();
return f.isDirectory() || name.endsWith(".png") || name.endsWith(".bmp");
}
public String getDescription() 
{
return "Image (*.png, *.bmp)";
}
};
fc.setAcceptAllFileFilterUsed(false);
fc.addChoosableFileFilter(ff);

java.io.File f = null;
if(open && fc.showOpenDialog(this) == fc.APPROVE_OPTION)
f = fc.getSelectedFile();
else if(!open && fc.showSaveDialog(this) == fc.APPROVE_OPTION)
f = fc.getSelectedFile();
return f;
}

private void openImage() 
{
	java.io.File f = showFileDialog(true);
	try 
	{ 
		image = ImageIO.read(f);
		JLabel l = new JLabel(new ImageIcon(image));
		imagePane.getViewport().add(l);
		this.validate();
	} 
	catch(Exception ex) 
	{ 
		ex.printStackTrace(); 
	}
}

private void decodeMessage() 
{	
	try
	{
		int len = extractInteger(image, 0, 0);
		byte b[] = new byte[len];
		//byte b[]=new byte[1024];
		for(int i=0; i<len; i++)
			b[i] = extractByte(image,i*8+32,0);
		message.setText(new String(b));
		File f=new File("E:\\steg.txt");
		FileWriter ous=new FileWriter(f);
		ous.write(message.getText());
		ous.close();
	}
	catch(Exception ex)
	{
		System.out.println(ex.getMessage());
	}
}

private int extractInteger(BufferedImage img, int start, int storageBit) 
{
int maxX = img.getWidth(), maxY = img.getHeight(),startX = start/maxY, startY = start - startX*maxY, count=0;
int length = 0;
for(int i=startX; i<maxX && count<32; i++) 
{
	for(int j=startY; j<maxY && count<32; j++) 
	{
		int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
		length = setBitValue(length, count, bit);
		count++;
	}
}
return length;
}

private byte extractByte(BufferedImage img, int start, int storageBit) 
{
	int maxX = img.getWidth(), maxY = img.getHeight(),startX = start/maxY, startY = start - startX*maxY,count=0;
	byte b = 0;
	for(int i=startX; i<maxX && count<32; i++) 
	{
		for(int j=startY; j<maxY && count<32; j++) 
		{
			int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
			b = (byte)setBitValue(b, count, bit);
			count++;
		}
	}
	return b;
}

private void resetInterface() 
{
message.setText("");
imagePane.getViewport().removeAll();
image = null;
this.validate();
}

private int getBitValue(int n, int location) 
{
int v = n & (int) Math.round(Math.pow(2, location));
return v==0?0:1;
}

private int setBitValue(int n, int location, int bit) 
{
int toggle = (int) Math.pow(2, location), bv = getBitValue(n, location);
if(bv == bit)
return n;
if(bv == 0 && bit == 1)
n |= toggle;
else if(bv == 1 && bit == 0)
n ^= toggle;
return n;
}

public static void main(String arg[]) 
{
new DecryptFile();
}
}
