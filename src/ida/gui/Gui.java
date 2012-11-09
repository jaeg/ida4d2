package ida.gui;

import ida.responses.Response;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Gui extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFileChooser saveChoice;
	private JTextArea logField;
	private JTextField submissionField;

	public Gui() {
		JPanel conversationPanel = new JPanel();
		JPanel entryPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		saveChoice = new JFileChooser();

		this.setBorder(new EmptyBorder(10, 50, 10, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		conversationPanel.setLayout(new BoxLayout(conversationPanel, BoxLayout.X_AXIS));
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JMenuBar optionsList = new JMenuBar();
		ButtonGroup options = new ButtonGroup();
		JMenuItem save = new JMenuItem("Save Log");

		/**
		 * TODO: Implement saving chat log.
		 */
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedWriter writer;
				if (saveChoice.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
					try {
						FileWriter fstream = new FileWriter(saveChoice.getSelectedFile());
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(logField.getText());
						out.close();
						JOptionPane.showMessageDialog(null, "Your chat log has been saved.");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Error saving file!");
					}
				}
			}
		});

		JMenuItem clear = new JMenuItem("Clear Log");

		/**
		 * TODO: Implement "Clear Log", in which the entire conversation is
		 * cleared. Should the conversation restart?
		 */
		// clear.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });

		JMenuItem color = new JMenuItem("Color Scheme");

		/**
		 * TODO: Implement changing of color scheme.
		 */
		// color.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });

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
			System.out.println(ex);
		}

		conversationPanel.setPreferredSize(new Dimension(300, 300));
		optionsList.add(save);
		optionsList.add(clear);
		optionsList.add(color);
		optionsList.setBackground(Color.BLUE);
		add(optionsList);
		optionsList.setPreferredSize(new Dimension(50, 50));

		JPanel imageBox = new JPanel();
		JLabel image = new JLabel("IDA IMAGE BOX");
		imageBox.add(image);

		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitAction();
			}
		});

		logField = new JTextArea();

		/**
		 * TODO: Set limit on user input length.
		 */
		JScrollPane conversationLog = new JScrollPane(logField);

		add(conversationLog);
		conversationPanel.add(conversationLog);
		imageBox.add(image);
		add(imageBox);
		conversationPanel.add(imageBox);
		add(conversationPanel);

		submissionField = new JTextField();
		submissionField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					submitAction();
				}
			}
		});
		entryPanel.add(submit);
		entryPanel.add(submissionField);
		add(entryPanel);

		JButton think = new JButton("Think for me");

		/**
		 * TODO: Implemement "Think For Me", in which Ida will response to
		 * herself.
		 */
		// think.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// }
		// });

		JButton log = new JButton("Full Text Log");

		/**
		 * TODO: Implement "Full Text Log", a pop-up window containing a full
		 * log of conversation.
		 */
		// log.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });

		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
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

	private void submitAction() {
		Response response = new Response(submissionField.getText());
		logField.append("\nME: " + submissionField.getText());
		Voice.sayIt(response);
		submissionField.setText("");
	}

}
