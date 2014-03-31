package client;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class loginPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public JButton OkButton;
	public JTextField text;
	public JLabel label;
	public loginPanel(JFrame frame){
		frame.setSize(300,100);
		text=new JTextField(15);
		OkButton = new JButton("Submit");		
		label=new JLabel("OfficerID: ");
		
		add(label, BorderLayout.WEST);  //change the positions after
		add(text, BorderLayout.EAST);		//change the positions after
		add(OkButton, BorderLayout.NORTH);	//change the positions after
	}
	

}
