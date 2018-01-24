//EmbedMessage.java
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;


public class EncryptFile extends JFrame implements ActionListener
{
	JFrame f=new JFrame();
	JButton open = new JButton("Browse Image"), embed = new JButton("Browse File"),
	save = new JButton("Save into new file"), reset = new JButton("Reset"),
	close = new JButton("Close");
	//JTextArea message = new JTextArea(10,3);
	JTextField message = new JTextField(50);
	BufferedImage sourceImage = null, embeddedImage = null;
	JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JScrollPane originalPane = new JScrollPane(),
	embeddedPane = new JScrollPane();
	//JPasswordField t1=new JPasswordField(8);
	//JPasswordField t2=new JPasswordField(8);
	//JLabel ll=new JLabel("Enter Public Key:");
	//JLabel l2=new JLabel("Enter Private Key:");

	public EncryptFile() 
	{
		super("Encrypt Text File into Steganographic Image");
		assembleInterface();
	
	this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
	this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().
	getMaximumWindowBounds());
	this.setVisible(true);
	sp.setDividerLocation(0.5);
	this.validate();
	}

	private void assembleInterface() 
	{
	JPanel p = new JPanel(new FlowLayout());
	p.add(open);
	p.add(embed);
	p.add(save); 
	p.add(reset);
	//p.add(ll);
	//p.add(t1);
                 	//p.add(l2);
	//p.add(t2);
	f.add(p);
	p.add(close);
	f.add(p);
	this.getContentPane().add(p, BorderLayout.SOUTH);
	open.addActionListener(this);
	embed.addActionListener(this);
	save.addActionListener(this); 
	reset.addActionListener(this);
	close.addActionListener(this);
	open.setMnemonic('O');
	embed.setMnemonic('E');
	save.setMnemonic('S');
	reset.setMnemonic('R');
	close.setMnemonic('C');

	save.setToolTipText("Click Here");
	close.setToolTipText("Do you want to Exit");
	//t1.setToolTipText("Enter Public Key");
	//t2.setToolTipText("Enter Private Key");


	p = new JPanel(new GridLayout(1,1));
	p.add(new JScrollPane(message));
	message.setFont(new Font("Arial",Font.BOLD,20));
	p.setBorder(BorderFactory.createTitledBorder("Text File to be embedded"));
	this.getContentPane().add(p, BorderLayout.NORTH);

	sp.setLeftComponent(originalPane);
	sp.setRightComponent(embeddedPane);
	originalPane.setBorder(BorderFactory.createTitledBorder("Original Image"));
	embeddedPane.setBorder(BorderFactory.createTitledBorder("Steganography Image"));
	this.getContentPane().add(sp, BorderLayout.CENTER);
}

	public void actionPerformed(ActionEvent ae) 
	{
		Object o = ae.getSource();
		if(o == open)
		{	
			openImage();
		}
		else if(o == embed)
		{
			openFile();
			//embedMessage();
			//JOptionPane.showMessageDialog(this,"Encryption is Successfully");
		}
		else if((o == save)) 
		{
			saveImage();
			
		}
	 	
		else if(o == reset) 
		{		
			resetInterface();
		}
		else if(o==close)
		{
			new EncryptMain().setVisible(true);
			this.setVisible(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
}

	private java.io.File showFileDialog(final boolean open) 
	{
		JFileChooser fc = new JFileChooser("Open an image");
		javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() 
		{
			public boolean accept(java.io.File f) 
			{
				String name = f.getName().toLowerCase();
				if(open)
					return f.isDirectory() || name.endsWith(".jpg") || name.endsWith(".jpeg") ||name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".tiff") || name.endsWith(".bmp") || name.endsWith(".dib");
					return f.isDirectory() || name.endsWith(".png") || name.endsWith(".bmp");
			}

			public String getDescription() 
			{
				if(open)
					return "Image (*.jpg, *.jpeg, *.png, *.gif, *.tiff, *.bmp, *.dib)";
				return "All Files (*.*)";
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
			sourceImage = ImageIO.read(f);
			JLabel l = new JLabel(new ImageIcon(sourceImage));
			originalPane.getViewport().add(l);
			this.validate();
		} 
		catch(Exception ex) 
		{ ex.printStackTrace(); }
	}

	private java.io.File showFileOpenDialog(final boolean open) 
	{
		JFileChooser fc = new JFileChooser("Open an image");
		javax.swing.filechooser.FileFilter ff = new javax.swing.filechooser.FileFilter() 
		{
			public boolean accept(java.io.File f) 
			{
				String name = f.getName().toLowerCase();
				if(open)
					return f.isDirectory() || name.endsWith(".txt") || name.endsWith(".doc") ||name.endsWith(".docx") ;
					return f.isDirectory() || name.endsWith(".png") || name.endsWith(".bmp");
			}

			public String getDescription() 
			{
				if(open)
					return "Files(*.txt, *.doc, *.docx)";
				return "All Files (*.*)";
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
	private void openFile() 
	{
		java.io.File f = showFileOpenDialog(true);
		try 
		{
			message.setText(f.getPath()); 
			//originalPane.getViewport().add(l);
			this.validate();
		} 
		catch(Exception ex) 
		{ ex.printStackTrace(); }
	}


	private void embedMessage() 
	{
		String mess = message.getText();
		embeddedImage = sourceImage.getSubimage(0,0,sourceImage.getWidth(),sourceImage.getHeight());
		embedMessage(embeddedImage, mess);
		//embedMessage(embeddedImage,"d:\\Test\\a.txt");
		JLabel l = new JLabel(new ImageIcon(embeddedImage));
		embeddedPane.getViewport().add(l);
		this.validate();
	}

	private void embedMessage(BufferedImage img, String mess) 
	{

		try
		{
			File file = new File(mess);
	 
		        FileInputStream fis = new FileInputStream(file);
		
			//int messageLength = mess.length();
			  int messageLength = (int)file.length();
			int imageWidth = img.getWidth(), imageHeight = img.getHeight(),imageSize = imageWidth * imageHeight;
		
			if(messageLength * 8 + 32 > imageSize) 
			{
				JOptionPane.showMessageDialog(this, "Message is too long for the chosen image",	"Message too long!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			embedInteger(img, messageLength, 0, 0);
			//in this outputdtream the buffer size will create 32 bytes
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		       	 byte[] buf = new byte[10240];
			int j=0;
			for (int readNum; (readNum = fis.read(buf)) != -1;) 
			{
        	 	       		bos.write(buf, 0, readNum); //no doubt here is 0
				System.out.println(buf[j]);
				j++;
              		  	//Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                		System.out.println("read " + readNum + " bytes,");
		        }
			//byte b[] = mess.getBytes();

			for(int i=0; i<buf.length; i++)
			{
				//System.out.println(b[i]);
				//embedByte(img, b[i], i*8+32, 0);
				embedByte(img, buf[i], i*8+32, 0);
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	private void embedInteger(BufferedImage img, int n, int start, int storageBit) 
	{
		int maxX = img.getWidth(), maxY = img.getHeight(),startX = start/maxY, startY = start - startX*maxY, count=0;
		for(int i=startX; i<maxX && count<32; i++) 
		{
			for(int j=startY; j<maxY && count<32; j++) 
			{
				int rgb = img.getRGB(i, j), bit = getBitValue(n, count);
				rgb = setBitValue(rgb, storageBit, bit);
				img.setRGB(i, j, rgb);
				count++;
			}
		}
	}

	private void embedByte(BufferedImage img, byte b, int start, int storageBit) 
	{
		int maxX = img.getWidth(), maxY = img.getHeight(),startX = start/maxY, startY = start - startX*maxY, count=0;
		for(int i=startX; i<maxX && count<8; i++) 
		{
			for(int j=startY; j<maxY && count<8; j++) 
			{
				int rgb = img.getRGB(i, j), bit = getBitValue(b, count);
				rgb = setBitValue(rgb, storageBit, bit);
				img.setRGB(i, j, rgb);
				count++;
			}
		}
	}

	private void saveImage() 
	{
		embedMessage();
		if(embeddedImage == null) 
		{
			JOptionPane.showMessageDialog(this, "No message has been embedded!", "Nothing to save", JOptionPane.ERROR_MESSAGE);
			return;
		}
		java.io.File f = showFileDialog(false);
		String name = f.getName();
		String ext = name.substring(name.lastIndexOf(".")+1).toLowerCase();
		if(!ext.equals("png") && !ext.equals("bmp") && !ext.equals("dib")) 
		{
			ext = "png";
			f = new java.io.File(f.getAbsolutePath()+".png");
		}
		try 
		{
			if(f.exists()) f.delete();
			ImageIO.write(embeddedImage, ext.toUpperCase(), f);
		}	 
		catch(Exception ex) 
		{ ex.printStackTrace(); }
	}

	private void resetInterface() 
	{
		message.setText("");
		originalPane.getViewport().removeAll();
		embeddedPane.getViewport().removeAll();
		sourceImage = null;
		embeddedImage = null;
		sp.setDividerLocation(0.5);
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
		new EncryptFile();
	}
}