package matala0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	private static String PATH = "C:\\Users\\נגה\\Desktop\\אוניברסיטה\\שנה ב\\מונחה עצמים\\מטלה 0\\27.10\\Lenovo";

	public static void main(String[] args) {

		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();
		
		List<File> csvFiles = getFilesList();
		
		for (File file : csvFiles) {
			List<WifiNetworkImport> convertCsvToWifiNetworkImport = convertCsvToWifiNetworkImport(file);
			wifiNetworkImportList.addAll(convertCsvToWifiNetworkImport);
		}

	}


	private static List<WifiNetworkImport> convertCsvToWifiNetworkImport(File file){
		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;

			br.readLine();
			br.readLine();

			while ((line = br.readLine()) != null) {
				System.out.println(line);
				WifiNetworkImport wifiNetworkImport = new WifiNetworkImport();
				String[] entries = line.split(",");
				wifiNetworkImport.setMAC(entries[0]);
				wifiNetworkImport.setSSID(entries[1]);
				wifiNetworkImport.setAuthMode(entries[2]);
				wifiNetworkImport.setFirstSeen(getDateFromString(entries[3]));
				wifiNetworkImport.setChannel(Integer.parseInt(entries[4]));
				wifiNetworkImport.setRSSI(Integer.parseInt(entries[5]));
				wifiNetworkImport.setCurrentLatitude(Double.parseDouble(entries[6]));
				wifiNetworkImport.setCurrentLongitude(Double.parseDouble(entries[7]));
				wifiNetworkImport.setAltitudeMeters(Integer.parseInt(entries[8]));
				wifiNetworkImport.setAccuracyMeters(Integer.parseInt(entries[9]));
				wifiNetworkImport.setType(entries[10]);
				
				if (wifiNetworkImport != null){
					wifiNetworkImportList.add(wifiNetworkImport);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return wifiNetworkImportList;
	}


	private static Date getDateFromString(String stringDate) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			date = formatter.parse(stringDate);
		} catch (ParseException e) {
			System.out.println("Failed to convert string to date for string: " + stringDate);
			e.printStackTrace();
		} 
		return date;
	}



	private static List<File> getFilesList() {
		Stream<Path> paths = null;
		List<File> csvFiles = new ArrayList<>();
		try {
			paths = Files.walk(Paths.get(PATH));
		} catch (IOException e) {
			System.err.println("Cano't find files in path");
			e.printStackTrace();
		}

		Stream<Path> filter = paths.filter(Files::isRegularFile);
		Stream<File> map = filter.map(Path::toFile);
		List<File> regularFiles = map.collect(Collectors.toList());

		for (File file : regularFiles) {
			String fileName = file.getName().toLowerCase();

			if (fileName.endsWith(".csv"))
			{
				csvFiles.add(file);
			}
		}
		return csvFiles;
	}

}
