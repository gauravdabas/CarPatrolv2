package client;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class loginPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public JButton OkButton;
	public JTextField text;
	public JLabel officerlabel;
	public JLabel provincelabel;
	public JComboBox comboBox;
	public loginPanel(JFrame frame){
		frame.setSize(300,100);
		text=new JTextField(15);
		OkButton = new JButton("Submit");		
		officerlabel=new JLabel("OfficerID: ");
		provincelabel = new JLabel("Select Province");
		String items[] = {"province1", "province2", "province3"};
		comboBox = new JComboBox(items);
		add(officerlabel, BorderLayout.WEST);  //change the positions after
		add(text, BorderLayout.EAST);		//change the positions after
		add(comboBox, BorderLayout.AFTER_LINE_ENDS);
		add(OkButton, BorderLayout.AFTER_LAST_LINE);	//change the positions after
		
	}
	

}
