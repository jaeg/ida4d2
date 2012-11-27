package ida.gui;

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
import javax.swing.JMenuItem;
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
		JPanel menuPanel = new JPanel();
		JPanel conversationPanel = new JPanel();
		JPanel entryPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		saveChoice = new JFileChooser();
		ida = new Ida();
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		this.setBorder(new EmptyBorder(10, 50, 10, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		conversationPanel.setLayout(new BoxLayout(conversationPanel,
				BoxLayout.X_AXIS));
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		JButton save = new JButton("Save Chat");

		/**
		 * TODO: Implement saving chat log.
		 */
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (saveChoice.showDialog(null, "Save") == JFileChooser.APPROVE_OPTION) {
					try {
						FileWriter fstream = new FileWriter(saveChoice
								.getSelectedFile());
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(logField.getText());
						out.close();
						JOptionPane.showMessageDialog(null,
								"Your chat log has been saved.");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null,
								"Error saving file!");
					}
				}
			}
		});

		JButton clear = new JButton("Clear Chat");

		/**
		 * TODO: Implement "Clear Log", in which the entire conversation is
		 * cleared. Should the conversation restart?
		 */
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logField.setText("");
			}
		});

		JButton idaLog = new JButton("IDA's Thoughts");

		/**
		 * TODO: Implement toggling for ida's thought log.
		 */
		// idaLog.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });

		menuPanel.add(save);

		menuPanel.add(clear);

		menuPanel.add(idaLog);

		menuPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		try {
			BufferedImage logo = ImageIO.read(new File("IDALOGO.gif"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			logoLabel.setAlignmentX(0.5f);
			add(logoLabel);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		conversationPanel.setPreferredSize(new Dimension(300, 300));
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

		/**
		 * TODO: Implement "Full Text Log", a pop-up window containing a full
		 * log of conversation.
		 */
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
		buttonPanel.add(log);
		buttonPanel.add(quit);
		add(buttonPanel);

		conversationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		entryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		menuPanel.setBackground(Color.DARK_GRAY);
		conversationPanel.setBackground(Color.GRAY);
		entryPanel.setBackground(Color.DARK_GRAY);
		buttonPanel.setBackground(Color.DARK_GRAY);
		
		this.setBackground(Color.DARK_GRAY);
	}

	private void submitAction() {
		ida.learn(submissionField.getText());
		ida.respondTo(submissionField.getText());
		submissionField.setText("");
	}

}
