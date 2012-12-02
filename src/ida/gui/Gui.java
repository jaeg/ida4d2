package ida.gui;

import ida.Logger;
import ida.ai.Ida;

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Gui extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFileChooser saveChoice;
	private JTextField submissionField;
	private Ida ida;
	private static final int TA_ROWS = 20;
	private static final int TA_COLS = 35;
	public static JTextArea logField = new JTextArea(TA_ROWS, TA_COLS);

	public Gui() {
		new Logger();

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		JPanel menuPanel = new JPanel();
		JPanel conversationPanel = new JPanel();
		JPanel entryPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		saveChoice = new JFileChooser();
		ida = new Ida();

		this.setBorder(new EmptyBorder(10, 50, 10, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		conversationPanel.setLayout(new BoxLayout(conversationPanel, BoxLayout.X_AXIS));
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JButton save = new JButton("Save Chat");

		/**
		 * TODO: Implement saving chat log ((into a .txt format.))
		 */
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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

		JButton clear = new JButton("Clear Chat");

		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logField.setText("");
			}
		});

		menuPanel.add(save);
		menuPanel.add(clear);

		try {
			BufferedImage logo = ImageIO.read(new File("IDALOGO.gif"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			logoLabel.setAlignmentX(0.5f);
			add(logoLabel);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		conversationPanel.setPreferredSize(new Dimension(365, 365));
		add(menuPanel);
		menuPanel.setPreferredSize(new Dimension(50, 50));

		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitAction();
			}
		});

		logField = new JTextArea();
		logField.setWrapStyleWord(true);
		logField.setLineWrap(true);

		/**
		 * TODO: Set limit on user input length.
		 */
		JScrollPane conversationLog = new JScrollPane(logField);

		add(conversationLog);
		conversationPanel.add(conversationLog);

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

		JButton log = new JButton("Full Text Log");

		log.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, logField.getText());

			}
		});

		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		JButton idaLog = new JButton("IDA's Thoughts");

		idaLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Logger.toggleLog();
			}
		});

		buttonPanel.add(idaLog);
		buttonPanel.add(quit);
		add(buttonPanel);

		conversationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		entryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		menuPanel.setBackground(new Color(69, 69, 69));
		conversationPanel.setBackground(Color.GRAY);
		entryPanel.setBackground(new Color(69, 69, 69));
		buttonPanel.setBackground(new Color(69, 69, 69));

		this.setBackground(new Color(69, 69, 69));
	}

	private void submitAction() {
		Logger.log("\n");
		String userText = submissionField.getText();
		Logger.log("User sent: " + userText + "\n");
		if (!userText.contains("My name is")) {
			ida.learn(userText);
		}
		ida.respondTo(userText);
		submissionField.setText("");
	}

}
