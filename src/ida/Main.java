package ida;

import ida.gui.Gui;

import javax.swing.JFrame;

/**
 * Main class - calls and builds the GUI
 *
 */
public class Main {
	
	public static void main(String[] argv)
	{
		JFrame frame = new JFrame("IDA4D2");
		frame.getContentPane().add(new Gui());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

		
	}

}
