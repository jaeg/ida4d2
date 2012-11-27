package ida;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Logger extends JPanel
{
	private static final long serialVersionUID = 1L;
	static JTextArea log = new JTextArea();
	public Logger()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//this.setPreferredSize(new Dimension(300,200));
		JScrollPane logPane = new JScrollPane(log);
		logPane.setPreferredSize(new Dimension(370,500));
		
		//logPane.add(log);
		add(logPane);
		
	}
	
	public static void log(String message)
	{
		log.append(message);
	}

}
