package matala0;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class GUIforMatala3 {

	private JFrame frame;
	private JTextField txtEnterAFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIforMatala3 window = new GUIforMatala3();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIforMatala3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEnterwigleFolder = new JButton("For Wigle File");
		btnEnterwigleFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEnterwigleFolder.setBounds(272, 66, 139, 23);
		frame.getContentPane().add(btnEnterwigleFolder);
		
		JButton btnEnterComboFolder = new JButton("For Comb File");
		btnEnterComboFolder.setBounds(272, 100, 139, 23);
		frame.getContentPane().add(btnEnterComboFolder);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(233, 85, -222, 4);
		frame.getContentPane().add(textPane);
		
		TextField textField = new TextField();
		textField.setBounds(203, 85, -171, 33);
		frame.getContentPane().add(textField);
		
		Panel panel = new Panel();
		panel.setBounds(206, 90, -174, 28);
		frame.getContentPane().add(panel);
		
		TextField textField_1 = new TextField();
		textField_1.setBounds(17, 85, 233, 23);
		frame.getContentPane().add(textField_1);
		
		txtEnterAFile = new JTextField();
		txtEnterAFile.setColumns(10);
		txtEnterAFile.setEnabled(false);
		txtEnterAFile.setEditable(false);
		txtEnterAFile.setText("Enter a file name ");
		txtEnterAFile.setBounds(17, 50, 131, 20);
		frame.getContentPane().add(txtEnterAFile);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
	}
}
