package ida.gui;

import ida.Logger;
import ida.ai.Ida;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
import javax.swing.filechooser.FileFilter;

/**
 * Graphic user interface.
 * 
 */
public class Gui extends JPanel {

	private static final long serialVersionUID = -149812648732649812L;

	private JFileChooser saveChoice;
	private JTextField submissionField;
	private Ida ida;
	private JPanel menuPanel, conversationPanel, entryPanel, buttonPanel;
	private JButton save, clear, submit, quit, idaLog;
	private JScrollPane conversationLog;

	public static JTextArea logField;

	public Gui() {
		new Logger();

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			Logger.log("LookAndFeel did not work, but that's okay.\n");
		}

		menuPanel = new JPanel();
		conversationPanel = new JPanel();
		entryPanel = new JPanel();
		buttonPanel = new JPanel();
		saveChoice = new JFileChooser();
		ida = new Ida();

		setLayout();

		prepareButtons();

		menuPanel.add(save);
		menuPanel.add(clear);

		displayLogo();

		prepareTextField();

		entryPanel.add(submit);
		entryPanel.add(submissionField);
		add(entryPanel);

		buttonPanel.add(idaLog);
		buttonPanel.add(quit);
		add(buttonPanel);

		style();
		
		openingMessage();
	}

	private void setLayout() {
		this.setBorder(new EmptyBorder(10, 50, 10, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		conversationPanel.setLayout(new BoxLayout(conversationPanel, BoxLayout.X_AXIS));
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	}

	private void prepareButtons() {
		// SAVE button
		save = new JButton("Save Chat");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveChoice.addChoosableFileFilter(new FileFilter() {
					String description = "Text File (*.txt)";
					String extension = "txt";

					public String getDescription() {
						return description;
					}

					public boolean accept(File f) {
						if (f == null)
							return false;
						if (f.isDirectory())
							return true;
						return f.getName().toLowerCase().endsWith(extension);
					}
				});
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

		// CLEAR button
		clear = new JButton("Clear Chat");
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logField.setText("");
			}
		});

		// SUBMIT button
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitAction();
			}
		});

		// QUIT button
		quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		// IDALOG button
		idaLog = new JButton("IDA's Thoughts");
		idaLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Logger.toggleLog();
			}
		});
	}

	private void displayLogo() {
		try {
			BufferedImage logo = ImageIO.read(new File("IDALOGO.gif"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			logoLabel.setAlignmentX(0.5f);
			add(logoLabel);
		} catch (Exception ex) {
			Logger.log("EXCEPTION THROWN FINDING IDA LOGO");
			ex.printStackTrace();
			System.exit(1);
		}
	}

	private void prepareTextField() {
		conversationPanel.setPreferredSize(new Dimension(365, 365));
		add(menuPanel);
		menuPanel.setPreferredSize(new Dimension(50, 50));

		logField = new JTextArea();
		logField.setWrapStyleWord(true);
		logField.setLineWrap(true);
		logField.setEditable(false);

		conversationLog = new JScrollPane(logField);

		add(conversationLog);

		conversationLog.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// Set an arbitrarily high logField height
				logField.select(logField.getHeight() + 100000, 0);
			}
		});
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
	}

	private void style() {
		conversationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		entryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		menuPanel.setBackground(new Color(69, 69, 69));
		conversationPanel.setBackground(Color.GRAY);
		entryPanel.setBackground(new Color(69, 69, 69));
		buttonPanel.setBackground(new Color(69, 69, 69));

		this.setBackground(new Color(69, 69, 69));
	}
	
	private void openingMessage(){
		logField.append("Hello, my name is Ida4D2. I am an interactive developmental android.\n");
		logField.append("You may talk to me about anything. Simply write a message or question in the field below.\n");
		logField.append("And don't mistake this message for me being polite.\n\n");
	}

	private void submitAction() {
		Logger.log("\n");
		String userText = submissionField.getText();
		Logger.log("User sent: " + userText + "\n");

		ida.respondTo(userText);
		String lastResponse = ida.getLastResponse().toString();
		if (!userText.contains("My name is") && !ida.getQuestionAsked() && !lastResponse.contains("Now I know")
				&& !lastResponse.contains("Fine be that way")) {
			// Learning currently disabled
			// ida.learn(userText);
		}
		submissionField.setText("");
	}

}
