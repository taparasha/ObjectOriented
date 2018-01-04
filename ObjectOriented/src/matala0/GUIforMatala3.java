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
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Panel;
import java.awt.SystemColor;

public class GUIforMatala3 {

	private JFrame frame;
	private JTextField txtEnterFolderPath;
	private JTextField txtEnterCsvPath;
	private JTextField txtEnterLat;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField txtEnterNameTo;

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
		frame.setBounds(100, 100, 631, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 595, 361);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Uploads", null, panel, null);
		tabbedPane.setEnabledAt(0, true);
		panel.setLayout(null);
		
		txtEnterFolderPath = new JTextField();
		txtEnterFolderPath.setText("Enter Folder Of Wigle files");
		txtEnterFolderPath.setBounds(41, 28, 267, 22);
		panel.add(txtEnterFolderPath);
		txtEnterFolderPath.setColumns(10);
		
		JButton btnEnter = new JButton("Wigle Folder Upload");
		btnEnter.setBounds(317, 26, 152, 23);
		panel.add(btnEnter);
		
		txtEnterCsvPath = new JTextField();
		txtEnterCsvPath.setText("Enter Comb File Name");
		txtEnterCsvPath.setBounds(40, 102, 267, 20);
		panel.add(txtEnterCsvPath);
		txtEnterCsvPath.setColumns(10);
		
		JButton btnAddCsv = new JButton("Comb File Upload");
		btnAddCsv.setBounds(317, 99, 154, 23);
		panel.add(btnAddCsv);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Filters", null, panel_2, null);
		panel_2.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 570, 311);
		panel_2.add(tabbedPane_1);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Filter By Time", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblStartTime = new JLabel("From");
		lblStartTime.setBounds(10, 11, 115, 28);
		panel_3.add(lblStartTime);
		
		JLabel lblEndedTime = new JLabel("To");
		lblEndedTime.setBounds(10, 147, 69, 14);
		panel_3.add(lblEndedTime);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"}));
		comboBox_2.setBounds(97, 199, 123, 24);
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
		
		JButton btnFilterNow_1 = new JButton("Apply");
		btnFilterNow_1.setBounds(432, 200, 105, 23);
		panel_3.add(btnFilterNow_1);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"}));
		comboBox_4.setBounds(97, 59, 123, 24);
		panel_3.add(comboBox_4);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBox.setBounds(97, 15, 48, 24);
		panel_3.add(comboBox);
		
		JLabel lblDay = new JLabel("Day");
		lblDay.setBounds(63, 11, 115, 28);
		panel_3.add(lblDay);
		
		JLabel lblMonth = new JLabel("Month");
		lblMonth.setBounds(159, 4, 115, 28);
		panel_3.add(lblMonth);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBox_1.setBounds(204, 13, 48, 24);
		panel_3.add(comboBox_1);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setBounds(262, 5, 115, 28);
		panel_3.add(lblYear);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"2016", "2017", "2018"}));
		comboBox_3.setBounds(296, 13, 61, 24);
		panel_3.add(comboBox_3);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(63, 57, 115, 28);
		panel_3.add(lblTime);
		
		JLabel label_5 = new JLabel("Day");
		label_5.setBounds(63, 143, 115, 28);
		panel_3.add(label_5);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBox_5.setBounds(97, 148, 48, 24);
		panel_3.add(comboBox_5);
		
		JLabel label_6 = new JLabel("Month");
		label_6.setBounds(164, 147, 115, 28);
		panel_3.add(label_6);
		
		JLabel label_7 = new JLabel("Year");
		label_7.setBounds(275, 141, 115, 28);
		panel_3.add(label_7);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBox_6.setBounds(204, 147, 48, 28);
		panel_3.add(comboBox_6);
		
		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setModel(new DefaultComboBoxModel(new String[] {"2016", "2017", "2018"}));
		comboBox_7.setBounds(307, 148, 58, 24);
		panel_3.add(comboBox_7);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Filter By Place", null, panel_4, null);
		panel_4.setLayout(null);
		
		JLabel lblLat = new JLabel("Lat");
		lblLat.setBounds(10, 57, 129, 24);
		panel_4.add(lblLat);
		
		JLabel lblLon = new JLabel("Lon");
		lblLon.setBounds(10, 106, 129, 24);
		panel_4.add(lblLon);
		
		JLabel lblAlt = new JLabel("Alt");
		lblAlt.setBounds(10, 160, 129, 24);
		panel_4.add(lblAlt);
		
		JLabel lblMax = new JLabel("MAX");
		lblMax.setBounds(53, 11, 92, 29);
		panel_4.add(lblMax);
		
		JLabel label_3 = new JLabel("MIN");
		label_3.setBounds(292, 11, 92, 29);
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
		
		JButton btnFilterNow = new JButton("Apply");
		btnFilterNow.setBounds(214, 219, 99, 23);
		panel_4.add(btnFilterNow);
		
		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Filter By Name", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblFilterByName = new JLabel("Filter By Name");
		lblFilterByName.setBounds(27, 55, 129, 24);
		panel_5.add(lblFilterByName);
		
		txtEnterNameTo = new JTextField();
		txtEnterNameTo.setText("Enter Name To Filter");
		txtEnterNameTo.setColumns(10);
		txtEnterNameTo.setBounds(112, 57, 184, 22);
		panel_5.add(txtEnterNameTo);
		
		JButton button_1 = new JButton("Apply");
		button_1.setBounds(332, 59, 99, 23);
		panel_5.add(button_1);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Export", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnMakeCsvFile = new JButton("make CSV File");
		btnMakeCsvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMakeCsvFile.setBounds(288, 94, 123, 23);
		panel_1.add(btnMakeCsvFile);
		
		JButton button = new JButton("make KML File");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(418, 93, 123, 23);
		panel_1.add(button);
		
		JButton btnClearData = new JButton("CLEAR DATA");
		btnClearData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Are you sure you want to CLEAR?");
			}
		});
		btnClearData.setForeground(Color.RED);
		btnClearData.setBounds(24, 182, 123, 23);
		panel_1.add(btnClearData);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 127, 590, 2);
		panel_1.add(separator);
		
		Label label = new Label("name of file");//show the name of the uploaded file 
		label.setBackground(UIManager.getColor("Button.light"));
		label.setBounds(24, 10, 256, 23);
		panel_1.add(label);
		
		Label label_1 = new Label("Filters");
		label_1.setBounds(23, 75, 43, 32);
		panel_1.add(label_1);
		
		Label label_2 = new Label(" None");
		label_2.setBackground(SystemColor.controlHighlight);
		label_2.setBounds(61, 81, 101, 23);
		panel_1.add(label_2);
		
		Label label_4 = new Label("Lines");
		label_4.setBounds(22, 39, 43, 32);
		panel_1.add(label_4);
		
		Label label_8 = new Label("0");
		label_8.setBackground(SystemColor.controlHighlight);
		label_8.setBounds(65, 43, 101, 23);
		panel_1.add(label_8);
		//
		
		
	}
}
