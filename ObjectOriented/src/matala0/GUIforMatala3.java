package matala0;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.JTextPane;

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
	private JTextField txtEnterDatatoexport;

	private Label lblMessage;
	private String filePath;
	private Label label_8;
	private Label label_2;
	private int sum;
	private Label label;
	private Label label_10;
	private Label label_12;
	private Label labelLa;
	private Label labelLo;
	private Label labelAl;
	
	private double alt1;
	private double lat1;
	private double lon1;

	private double alt2;
	private double lat2;
	private double lon2;

	private List<File> csvFiles = new ArrayList<>();
	private List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();

	String filters = "  ";
	List<DataToExport> file = new ArrayList<>();
	private JTextField txtEnterMac;
	private JTextField txtEnterMac_1;
	private JTextField txtEnterSignal;
	private JTextField txtEnterMac_2;
	private JTextField txtEnterSignal_1;
	private JTextField txtEnterMac_3;
	private JTextField txtEnterSignal_2;

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
		txtEnterFolderPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		/**
		 * this function connects between the code and the button of upload wiggle files. this button let you 
		 * add folder contains files of Wiggle wifi and enter them to databases of WifiNetworkImport and DataToExport
		 */

		txtEnterFolderPath.setText("Enter Folder Of Wigle files");
		txtEnterFolderPath.setBounds(41, 28, 267, 22);
		panel.add(txtEnterFolderPath);
		txtEnterFolderPath.setColumns(10);

		JButton btnEnter = new JButton("Wigle Folder Upload");
		btnEnter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				filePath = txtEnterFolderPath.getText();
				lblMessage.setText(filePath);
				myThread t1 = new myThread("wigle");
				t1.start();
				
				


				csvFiles = WifiNetworkImport.getFilesListForNetworkImport(filePath);


				for (File file : csvFiles) {
					List<WifiNetworkImport> convertCsvToWifiNetworkImport = WifiNetworkImport.convertCsvToWifiNetworkImport(file);
					wifiNetworkImportList.addAll(convertCsvToWifiNetworkImport);
				}
				csvFiles = null;

				List<DataToExport> temp = DataToExport.buildDataToExportList(wifiNetworkImportList); 

				for (DataToExport dataToExport : temp) {
					List<WifiNetworkExport> sortWifiNetworksBySignal = WifiNetworkExport.sortWifiNetworksBySignal(dataToExport.getWifiNetworks());
					dataToExport.setWifiNetworks(sortWifiNetworksBySignal);
				}

				for (DataToExport a : temp) {
					file.add(a);					
				}

				label_8.setText(Integer.toString(file.size()));
			}
		});
		btnEnter.setBounds(317, 26, 152, 23);
		panel.add(btnEnter);

		/**
		 * this function connects between the code and the button of upload combo file. this button let you add
		 * a new file of DataToExport (what we call combo file) to your exist database of DataToExport
		 */

		txtEnterCsvPath = new JTextField();
		txtEnterCsvPath.setText("Enter Comb File Name");
		txtEnterCsvPath.setBounds(40, 102, 267, 20);
		panel.add(txtEnterCsvPath);
		txtEnterCsvPath.setColumns(10);

		JButton btnAddCsv = new JButton("Comb File Upload"); //Upload comb file
		btnAddCsv.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				filePath = txtEnterCsvPath.getText();
				lblMessage.setText(filePath);
				myThread t = new myThread("csv");
				t.start();
				
				
				List<DataToExport> NewListToAdd = new ArrayList<>();

				List<File> csvFiles3 = DataToExport.getFilesListForDataToExport(filePath);

				for (File file : csvFiles3) {
					List<DataToExport> convertCsvToDataToExport = DataToExport.convertCsvToDataToExportfromcombofile(file);
					NewListToAdd.addAll(convertCsvToDataToExport);
				}

				for (DataToExport a : NewListToAdd) {
					file.add(a);					
				}

				label_8.setText(Integer.toString(file.size()));
			}
		});
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
		btnFilterNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lat1 = Double.parseDouble(txtEnterLat.getText());				
				lon1 = Double.parseDouble(textField.getText());
				alt1 = Double.parseDouble(textField_1.getText());

				lat2 = Double.parseDouble(textField_2.getText());
				lon2 = Double.parseDouble(textField_3.getText());
				alt2 = Double.parseDouble(textField_4.getText());

				double radius = (DataToExport.distance(lat1, lon1, lat2, lon2)/1000)/2;
				double centerLat = (lat1+lat2)/2;
				double centerLon = (lon1+lon2)/2;

				List<DataToExport> temp = new ArrayList<>();
				temp = DataToExport.SortListByC(file, centerLat, centerLon, radius);

				file = temp;
				label_8.setText(Integer.toString(file.size()));
				label_2.setText(filters+"\nCenter- lat:"+centerLat+", lon:"+centerLon+", Radius:"+radius+", ");
			}
		});
		btnFilterNow.setBounds(214, 219, 99, 23);
		panel_4.add(btnFilterNow);

		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Filter By Device", null, panel_5, null);
		panel_5.setLayout(null);

		JLabel lblFilterByName = new JLabel("Filter By Device");
		lblFilterByName.setBounds(27, 55, 129, 24);
		panel_5.add(lblFilterByName);

		txtEnterNameTo = new JTextField();
		txtEnterNameTo.setText("Enter Name To Filter");
		txtEnterNameTo.setColumns(10);
		txtEnterNameTo.setBounds(112, 57, 184, 22);
		panel_5.add(txtEnterNameTo);

		JButton button_1 = new JButton("Apply");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //filter by device

				String device = txtEnterNameTo.getText();

				for(DataToExport a : file){
					
				}

			}
		});
		button_1.setBounds(332, 59, 99, 23);
		panel_5.add(button_1);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Export", null, panel_1, null);
		panel_1.setLayout(null);
		
		
/**
 * connecting the code to the button of create csv
 */
		
		JButton btnMakeCsvFile = new JButton("Create CSV File");
		btnMakeCsvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String csvString = DataToExport.buildCSVData(file);		
				KMLandCSVbuild.saveToCsvFile(csvString);
			}
		});
		btnMakeCsvFile.setBounds(234, 208, 123, 23);
		panel_1.add(btnMakeCsvFile);

		JButton btnCreateKmlFile = new JButton("Create KML File");
		btnCreateKmlFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KMLandCSVbuild.saveTokmlFile(file);
			}
		});
		btnCreateKmlFile.setBounds(367, 208, 123, 23);
		panel_1.add(btnCreateKmlFile);

		JButton btnClearData = new JButton("CLEAR DATA");
		btnClearData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Are you sure you want to CLEAR?");
				file.clear();
				label_8.setText(Integer.toString(file.size()));
				label_2.setText("");
				lblMessage.setText("Name of file last uploaded");
			}
		});
		btnClearData.setForeground(Color.RED);
		btnClearData.setBounds(24, 273, 123, 23);
		panel_1.add(btnClearData);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 242, 590, 2);
		panel_1.add(separator);

		lblMessage = new Label("Name of file last uploaded");//show the name of the uploaded file 
		lblMessage.setBackground(UIManager.getColor("Button.light"));
		lblMessage.setBounds(24, 10, 256, 23);
		panel_1.add(lblMessage);

		Label label_1 = new Label("Filters");
		label_1.setBounds(23, 75, 37, 32);
		panel_1.add(label_1);

		label_2 = new Label(" None");
		label_2.setBackground(SystemColor.controlHighlight);
		label_2.setBounds(61, 81, 500, 109);
		panel_1.add(label_2);

		Label label_4 = new Label("Lines");
		label_4.setBounds(22, 39, 43, 32);
		panel_1.add(label_4);

		label_8 = new Label("0");
		label_8.setBackground(SystemColor.controlHighlight);
		label_8.setBounds(65, 43, 101, 23);
		panel_1.add(label_8);

		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Algo 1", null, panel_6, null);
		panel_6.setLayout(null);

		JLabel lblEnterSpecificMac = new JLabel("Enter MAC you want to improve:");
		lblEnterSpecificMac.setBounds(29, 33, 184, 24);
		panel_6.add(lblEnterSpecificMac);

		txtEnterMac = new JTextField();
		txtEnterMac.setText("Enter MAC");
		txtEnterMac.setColumns(10);
		txtEnterMac.setBounds(29, 54, 184, 22);
		panel_6.add(txtEnterMac);

		JButton button = new JButton("Apply");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String MAC = txtEnterMac.getText();
				MacImprove temp = new MacImprove();

				List<MacImprove> macImproveList = MacImprove.buildMacImproveList(wifiNetworkImportList);
				List<MacImprove> d = new ArrayList<>();
				List<MacImprove> r = new ArrayList<>();

				d=MacImprove.reduceMacImproveList(macImproveList);
				r=MacImprove.Algo1(d);

				for(MacImprove a: r){
					if(a.getMAC()!=null){
						if(a.getMAC().equals(MAC)){
							temp=a;
							break;
						}
					}
				}

				label.setText(Double.toString(temp.getCurrentLatitude()));
				label_10.setText(Double.toString(temp.getCurrentLongitude()));
				label_12.setText(Double.toString(temp.getAltitudeMeters()));
			}
		});
		button.setBounds(234, 54, 99, 23);
		panel_6.add(button);

		label = new Label("");
		label.setBackground(SystemColor.controlHighlight);
		label.setBounds(57, 152, 101, 23);
		panel_6.add(label);

		Label label_9 = new Label("Lat");
		label_9.setBounds(29, 152, 43, 32);
		panel_6.add(label_9);

		label_10 = new Label("");
		label_10.setBackground(SystemColor.controlHighlight);
		label_10.setBounds(209, 156, 101, 23);
		panel_6.add(label_10);

		Label label_11 = new Label("Lon");
		label_11.setBounds(182, 152, 43, 32);
		panel_6.add(label_11);

		label_12 = new Label("");
		label_12.setBackground(SystemColor.controlHighlight);
		label_12.setBounds(369, 156, 101, 23);
		panel_6.add(label_12);

		Label label_13 = new Label("Alt");
		label_13.setBounds(343, 152, 43, 32);
		panel_6.add(label_13);

		JPanel panel_7 = new JPanel();
		tabbedPane.addTab("Algo 2", null, panel_7, null);
		panel_7.setLayout(null);
		
		txtEnterDatatoexport = new JTextField();
		txtEnterDatatoexport.setText("Enter line to locate");
		txtEnterDatatoexport.setColumns(10);
		txtEnterDatatoexport.setBounds(30, 50, 536, 22);
		panel_7.add(txtEnterDatatoexport);
		
		JLabel lblEnterDatatoexportYou = new JLabel("Enter Line of Combo File you want to Locate:");
		lblEnterDatatoexportYou.setBounds(30, 29, 266, 24);
		panel_7.add(lblEnterDatatoexportYou);
		
		JButton button_2 = new JButton("Apply");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String datatoexport;
				datatoexport = txtEnterDatatoexport.getText();
				
				DataToExport line = new DataToExport();
				String [] entries = datatoexport.split(",");
				line.setLat(Double.parseDouble(entries[2]));
				line.setLon(Double.parseDouble(entries[3]));
				line.setAlt(Double.parseDouble(entries[4]));
				

				for(int q=6; q<=entries.length - 3; q+=4){
					WifiNetworkExport temp = new WifiNetworkExport();
					temp.setMAC(entries[q+1]);
					temp.setSignal(Integer.parseInt(entries[q+3]));

					line.getWifiNetworks().add(temp);
				}
				
				DataToExport t = new DataToExport();
				t = DataToExportWithPI.checkSimilarity(line, file);
				
				String Lat = Double.toString(t.getLat());
				String Lon = Double.toString(t.getLon());
				String Alt = Double.toString(t.getAlt());
				System.out.println("lat:"+Lat+", lon:"+Lon+", alt:"+Alt);
				labelLa.setText(Lat);
				labelLo.setText(Lon);
				labelAl.setText(Alt);
			}
		});
		button_2.setBounds(467, 83, 99, 23);
		panel_7.add(button_2);
		
		Label labelLa = new Label("");
		labelLa.setBackground(SystemColor.controlHighlight);
		labelLa.setBounds(59, 291, 139, 23);
		panel_7.add(labelLa);
		
		Label label_15 = new Label("Lat");
		label_15.setBounds(30, 291, 43, 32);
		panel_7.add(label_15);
		
		Label label_16 = new Label("Lon");
		label_16.setBounds(204, 282, 43, 32);
		panel_7.add(label_16);
		
		Label labelLo = new Label("");
		labelLo.setBackground(SystemColor.controlHighlight);
		labelLo.setBounds(235, 291, 137, 23);
		panel_7.add(labelLo);
		
		Label label_18 = new Label("Alt");
		label_18.setBounds(378, 282, 43, 32);
		panel_7.add(label_18);
		
		Label labelAl = new Label("");
		labelAl.setBackground(SystemColor.controlHighlight);
		labelAl.setBounds(404, 286, 115, 23);
		panel_7.add(labelAl);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 258, 590, 2);
		panel_7.add(separator_1);
		
		JLabel lblMacadrees = new JLabel("MacAdrees 1");
		lblMacadrees.setBounds(30, 117, 129, 24);
		panel_7.add(lblMacadrees);
		
		txtEnterMac_1 = new JTextField();
		txtEnterMac_1.setText("Enter MAC 1");
		txtEnterMac_1.setColumns(10);
		txtEnterMac_1.setBounds(112, 118, 184, 22);
		panel_7.add(txtEnterMac_1);
		
		txtEnterSignal = new JTextField();
		txtEnterSignal.setText("Enter Signal 1");
		txtEnterSignal.setColumns(10);
		txtEnterSignal.setBounds(312, 119, 184, 22);
		panel_7.add(txtEnterSignal);
		
		JLabel lblMacadrees_1 = new JLabel("MacAdrees 2");
		lblMacadrees_1.setBounds(30, 152, 129, 24);
		panel_7.add(lblMacadrees_1);
		
		txtEnterMac_2 = new JTextField();
		txtEnterMac_2.setText("Enter MAC 2");
		txtEnterMac_2.setColumns(10);
		txtEnterMac_2.setBounds(112, 153, 184, 22);
		panel_7.add(txtEnterMac_2);
		
		txtEnterSignal_1 = new JTextField();
		txtEnterSignal_1.setText("Enter Signal 2");
		txtEnterSignal_1.setColumns(10);
		txtEnterSignal_1.setBounds(312, 154, 184, 22);
		panel_7.add(txtEnterSignal_1);
		
		JLabel lblMacadrees_2 = new JLabel("MacAdrees 3");
		lblMacadrees_2.setBounds(30, 187, 129, 24);
		panel_7.add(lblMacadrees_2);
		
		txtEnterMac_3 = new JTextField();
		txtEnterMac_3.setText("Enter MAC 3");
		txtEnterMac_3.setColumns(10);
		txtEnterMac_3.setBounds(112, 188, 184, 22);
		panel_7.add(txtEnterMac_3);
		
		txtEnterSignal_2 = new JTextField();
		txtEnterSignal_2.setText("Enter Signal 3");
		txtEnterSignal_2.setColumns(10);
		txtEnterSignal_2.setBounds(312, 189, 184, 22);
		panel_7.add(txtEnterSignal_2);
		
		JButton button_3 = new JButton("Apply");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String MAC1,MAC2,MAC3;
				int signal1,signal2,signal3;
				MAC1 = txtEnterMac_1.getText();
				MAC2 = txtEnterMac_2.getText();
				MAC3 = txtEnterMac_3.getText();
				
				signal1 = Integer.parseInt(txtEnterSignal.getText());
				signal2 = Integer.parseInt(txtEnterSignal_1.getText());
				signal3 = Integer.parseInt(txtEnterSignal_2.getText());
				
				DataToExport location = new DataToExport();
				WifiNetworkExport a = new WifiNetworkExport();
				a.setMAC(MAC1);
				a.setSignal(signal1);
				
				WifiNetworkExport b = new WifiNetworkExport();
				b.setMAC(MAC2);
				b.setSignal(signal2);
				
				WifiNetworkExport c = new WifiNetworkExport();
				c.setMAC(MAC3);
				c.setSignal(signal3);
				
				List<WifiNetworkExport> list = new ArrayList<>();
				list.add(a);list.add(b);list.add(c);
				
				location.setWifiNetworks(list);
				
				DataToExport t = new DataToExport();
				t = DataToExportWithPI.checkSimilarity(location, file);
				
				String Lat = Double.toString(t.getLat());
				String Lon = Double.toString(t.getLon());
				String Alt = Double.toString(t.getAlt());
				System.out.println("lat:"+Lat+", lon:"+Lon+", alt:"+Alt);
				labelLa.setText("    "+Lat);
				labelLo.setText("    "+Lon);
				labelAl.setText("    "+Alt);
			}
		});
		button_3.setBounds(467, 222, 99, 23);
		panel_7.add(button_3);
		//


	}
}
