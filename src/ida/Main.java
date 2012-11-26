package ida;

import javax.swing.JFrame;

import ida.ai.Ida;
import ida.gui.Gui;

public class Main {
	
	public static void main(String[] argv)
	{
//		Ida ida = new Ida();
		JFrame frame = new JFrame("IDA4D2");
		frame.getContentPane().add(new Gui());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JFrame logFrame = new JFrame("Log window");
		logFrame.getContentPane().add(new Logger());
		logFrame.pack();
		logFrame.setVisible(true);
		logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

}
