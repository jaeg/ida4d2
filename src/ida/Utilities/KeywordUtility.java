package ida.Utilities;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;

/**
 * A development utility built to easily enter phrases 
 * and keywords into the response database.
 * 
 *
 */
public class KeywordUtility extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextArea keywordInput;
	private JTextArea messageInput;

	public static void main(String[] args) {

		JFrame frame = new JFrame("IDA4D2 - Keyword Utility");
		frame.getContentPane().add(new KeywordUtility());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public KeywordUtility() {
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		keywordInput = new JTextArea(5, 20);
		messageInput = new JTextArea(5, 20);

		JScrollPane keywordPane = new JScrollPane(keywordInput);
		keywordPane.setLayout(new ScrollPaneLayout());
		keywordPane.setPreferredSize(new Dimension(200, 100));

		JScrollPane messagePane = new JScrollPane(messageInput);
		messagePane.setLayout(new ScrollPaneLayout());
		messagePane.setPreferredSize(new Dimension(200, 100));

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String keywords[] = keywordInput.getText().split("\n");
				String messages[] = messageInput.getText().split("\n");

				try {
					XMLWriter.writeResponseToFile("responses.xml", keywords, messages);
					JOptionPane.showMessageDialog(null, "Keywords added succesfully!");
					keywordInput.setText("");
					messageInput.setText("");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}

		});

		add(new JLabel("Keywords"));
		add(keywordPane);
		add(new JLabel("Messages"));
		add(messagePane);
		add(add);

	}

}
