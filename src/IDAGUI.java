import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IDAGUI extends JPanel {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		JFrame frame = new JFrame("IDA4D2");
		frame.getContentPane().add(new IDAGUI());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public IDAGUI() {
		JPanel conversationPanel = new JPanel();
		JPanel entryPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		this.setBorder(new EmptyBorder(10, 50, 10, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		conversationPanel.setLayout(new BoxLayout(conversationPanel,
				BoxLayout.X_AXIS));
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JMenuBar optionsList = new JMenuBar();
		ButtonGroup options = new ButtonGroup();
		JMenuItem save = new JMenuItem("Save Log");
		JMenuItem clear = new JMenuItem("Clear Log");
		JMenuItem color = new JMenuItem("Color Scheme");
		options.add(save);
		options.add(clear);
		options.add(color);

		optionsList.setBorder(new EmptyBorder(10, 10, 10, 10));
		try {
			BufferedImage logo = ImageIO.read(new File("IDALOGO.gif"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			logoLabel.setAlignmentX(0.5f);
			add(logoLabel);
		} catch (Exception ex) {

		}

		conversationPanel.setPreferredSize(new Dimension(300, 300));
		optionsList.add(save);
		optionsList.add(clear);
		optionsList.add(color);
		optionsList.setBackground(Color.BLUE);
		add(optionsList);
		optionsList.setPreferredSize(new Dimension(50, 50));
		JTextField logField = new JTextField();
		logField.setEditable(false);
		JScrollPane conversationLog = new JScrollPane(logField);// figure out
																// how to set an
																// exact amount
																// of row
																// letters

		JPanel imageBox = new JPanel();// WIll have to be changed later for
										// image with notes from below
		JLabel image = new JLabel("IDA IMAGE BOX");
		imageBox.add(image);

		add(conversationLog);
		conversationPanel.add(conversationLog);
		imageBox.add(image);
		add(imageBox);
		conversationPanel.add(imageBox);
		add(conversationPanel);

		JButton submit = new JButton("Submit");
		JTextField submissionField = new JTextField();
		entryPanel.add(submit);
		entryPanel.add(submissionField);
		add(entryPanel);

		JButton think = new JButton("Think for me");
		JButton quit = new JButton("Quit");
		JButton log = new JButton("Full Text Log");
		buttonPanel.add(think);
		buttonPanel.add(log);// Will pop up a JOptionPane that contains the full
								// log window at the moment.(Kind of just an
								// idea IDK if we should implement this or not.)
		buttonPanel.add(quit);
		add(buttonPanel);

		conversationLog.setBorder(new EmptyBorder(10, 10, 10, 10));
		entryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		conversationLog.setBackground(Color.RED);
		entryPanel.setBackground(Color.BLUE);
		buttonPanel.setBackground(Color.BLUE);
		imageBox.setBackground(Color.RED);
		this.setBackground(Color.BLACK);

	}

}

// possible way to implement our pictures of IDA4D2

/*
 * class ImagePanel extends JPanel { Image img;
 * 
 * public ImagePanel(){ // make sure the parent JPanel constructor is called //
 * so it does whatever it needs to do first. super();
 * 
 * img = loadImage("whatever.file"); }
 * 
 * public void paintComponent(Graphics g) { super.paintComponent(g); if(img !=
 * null) { g.drawImage(img, 0, 0, this); } }
 * 
 * public Image loadImage(File f) { Image image =
 * getToolkit().getImage(f.getPath()); return image; } }
 */