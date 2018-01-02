package matala0;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.Canvas;
import java.awt.Label;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;

public class gui {

	private JFrame frame;
	private JTextField txtEnterFolderPath;
	private JTextField txtEnterCsvPath;
	private JTextField txtEnterLat;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField txtEnterDeviceName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
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
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 631, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 595, 361);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("UPLOADS", null, panel, null);
		panel.setLayout(null);
		
		txtEnterFolderPath = new JTextField();
		txtEnterFolderPath.setText("Enter Folder Path");
		txtEnterFolderPath.setBounds(10, 27, 297, 20);
		panel.add(txtEnterFolderPath);
		txtEnterFolderPath.setColumns(10);
		
		JButton btnEnter = new JButton("enter");
		btnEnter.setBounds(317, 26, 89, 23);
		panel.add(btnEnter);
		
		txtEnterCsvPath = new JTextField();
		txtEnterCsvPath.setText("Enter CSV Path");
		txtEnterCsvPath.setBounds(10, 102, 297, 20);
		panel.add(txtEnterCsvPath);
		txtEnterCsvPath.setColumns(10);
		
		JButton btnAddCsv = new JButton("Add CSV");
		btnAddCsv.setBounds(317, 99, 89, 23);
		panel.add(btnAddCsv);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Export", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnMakeCsvFile = new JButton("make CSV File");
		btnMakeCsvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMakeCsvFile.setBounds(24, 49, 123, 23);
		panel_1.add(btnMakeCsvFile);
		
		JButton button = new JButton("make KML File");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(438, 49, 123, 23);
		panel_1.add(button);
		
		JButton button_1 = new JButton("CLEAR");
		button_1.setForeground(Color.RED);
		button_1.setBounds(24, 182, 123, 23);
		panel_1.add(button_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 127, 590, 2);
		panel_1.add(separator);
		
		Label label = new Label("name of file");//show the name of the uploaded file 
		label.setBackground(UIManager.getColor("Button.light"));
		label.setBounds(24, 10, 166, 19);
		panel_1.add(label);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Data Control", null, panel_2, null);
		panel_2.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 570, 311);
		panel_2.add(tabbedPane_1);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Filter By Time", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblStartTime = new JLabel("start time");
		lblStartTime.setBounds(10, 11, 115, 28);
		panel_3.add(lblStartTime);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(97, 15, 123, 24);
		panel_3.add(comboBox);
		comboBox.addItem("00:00");
		comboBox.addItem("00:30");
		comboBox.addItem("01:00");
		comboBox.addItem("01:30");
		comboBox.addItem("02:00");
		comboBox.addItem("02:30");
		comboBox.addItem("03:00");
		comboBox.addItem("03:30");
		comboBox.addItem("04:00");
		comboBox.addItem("04:30");
		comboBox.addItem("05:00");
		comboBox.addItem("05:30");
		comboBox.addItem("06:00");
		comboBox.addItem("06:30");
		comboBox.addItem("07:00");
		comboBox.addItem("07:30");
		comboBox.addItem("08:00");
		comboBox.addItem("08:30");
		comboBox.addItem("09:00");
		comboBox.addItem("09:30");
		comboBox.addItem("10:00");
		comboBox.addItem("10:30");
		comboBox.addItem("11:00");
		comboBox.addItem("11:30");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(230, 15, 52, 24);
		panel_3.add(comboBox_1);
		comboBox_1.addItem("am");
		comboBox_1.addItem("pm");
		
		JLabel lblEndedTime = new JLabel("ended time");
		lblEndedTime.setBounds(10, 82, 69, 14);
		panel_3.add(lblEndedTime);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(97, 79, 123, 24);
		panel_3.add(comboBox_2);
		comboBox_2.addItem("00:00");
		comboBox_2.addItem("00:30");
		comboBox_2.addItem("01:00");
		comboBox_2.addItem("01:30");
		comboBox_2.addItem("02:00");
		comboBox_2.addItem("02:30");
		comboBox_2.addItem("03:00");
		comboBox_2.addItem("03:30");
		comboBox_2.addItem("04:00");
		comboBox_2.addItem("04:30");
		comboBox_2.addItem("05:00");
		comboBox_2.addItem("05:30");
		comboBox_2.addItem("06:00");
		comboBox_2.addItem("06:30");
		comboBox_2.addItem("07:00");
		comboBox_2.addItem("07:30");
		comboBox_2.addItem("08:00");
		comboBox_2.addItem("08:30");
		comboBox_2.addItem("09:00");
		comboBox_2.addItem("09:30");
		comboBox_2.addItem("10:00");
		comboBox_2.addItem("10:30");
		comboBox_2.addItem("11:00");
		comboBox_2.addItem("11:30");
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(230, 79, 52, 24);
		panel_3.add(comboBox_3);
		comboBox_3.addItem("am");
		comboBox_3.addItem("pm");
		
		JButton btnFilterNow_1 = new JButton("FILTER NOW!");
		btnFilterNow_1.setBounds(390, 49, 105, 23);
		panel_3.add(btnFilterNow_1);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Filter By Place", null, panel_4, null);
		panel_4.setLayout(null);
		
		JLabel lblLat = new JLabel("LAT");
		lblLat.setBounds(10, 57, 129, 24);
		panel_4.add(lblLat);
		
		JLabel label_1 = new JLabel("LON");
		label_1.setBounds(10, 106, 129, 24);
		panel_4.add(label_1);
		
		JLabel label_2 = new JLabel("ALT");
		label_2.setBounds(10, 160, 129, 24);
		panel_4.add(label_2);
		
		JLabel lblMax = new JLabel("MAX");
		lblMax.setBounds(119, 11, 92, 29);
		panel_4.add(lblMax);
		
		JLabel label_3 = new JLabel("MIN");
		label_3.setBounds(302, 11, 92, 29);
		panel_4.add(label_3);
		
		txtEnterLat = new JTextField();
		txtEnterLat.setText("enter lat");
		txtEnterLat.setBounds(53, 59, 184, 22);
		panel_4.add(txtEnterLat);
		txtEnterLat.setColumns(10);
		
		textField = new JTextField();
		textField.setText("enter lon");
		textField.setColumns(10);
		textField.setBounds(53, 108, 184, 22);
		panel_4.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText("enter alt");
		textField_1.setColumns(10);
		textField_1.setBounds(53, 162, 184, 22);
		panel_4.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setText("enter lat");
		textField_2.setColumns(10);
		textField_2.setBounds(292, 59, 184, 22);
		panel_4.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("enter lon");
		textField_3.setColumns(10);
		textField_3.setBounds(292, 108, 184, 22);
		panel_4.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setText("enter alt");
		textField_4.setColumns(10);
		textField_4.setBounds(292, 162, 184, 22);
		panel_4.add(textField_4);
		
		JButton btnFilterNow = new JButton("FILTER NOW!");
		btnFilterNow.setBounds(214, 219, 99, 23);
		panel_4.add(btnFilterNow);
		
		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Filter By Name", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblEnterDeviceName = new JLabel("enter device name");
		lblEnterDeviceName.setBounds(10, 22, 132, 33);
		panel_5.add(lblEnterDeviceName);
		
		txtEnterDeviceName = new JTextField();
		txtEnterDeviceName.setText("enter device name");
		txtEnterDeviceName.setBounds(129, 28, 189, 20);
		panel_5.add(txtEnterDeviceName);
		txtEnterDeviceName.setColumns(10);
		
		JButton btnSubmit = new JButton("FILTER NOW!");
		btnSubmit.setBounds(361, 27, 110, 23);
		panel_5.add(btnSubmit);
		
		JPanel panel_6 = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel_6, null);
		
		
		
	}
}
