package matala0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;
import de.micromata.opengis.kml.v_2_2_0.TimeSpan;
import de.micromata.opengis.kml.v_2_2_0.TimeStamp;



public class Main {

	public static String BASE_PATH = "C:\\tmp";
	public static String CSV_FILE_NAME = "\\exportData.csv";
	public static String KML_FILE_NAME = "\\exportData.kml";
	public static final String SEPERATOR = ",";


	public static void main(String[] args) {
		
		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();

		List<File> csvFiles = getFilesList();

		for (File file : csvFiles) {
			List<WifiNetworkImport> convertCsvToWifiNetworkImport = WifiNetworkImport.convertCsvToWifiNetworkImport(file);
			wifiNetworkImportList.addAll(convertCsvToWifiNetworkImport);
		}

		List<DataToExport> dataToExportList = DataToExport.buildDataToExportList(wifiNetworkImportList);


		for (DataToExport dataToExport : dataToExportList) {
			List<WifiNetworkExport> sortWifiNetworksBySignal = sortWifiNetworksBySignal(dataToExport.getWifiNetworks());
			dataToExport.setWifiNetworks(sortWifiNetworksBySignal);
		}

		String csvString = DataToExport.buildCSVData(dataToExportList);
		saveToCsvFile(csvString);
		saveTokmlFile(dataToExportList);

	}

	
	
	//https://stackoverflow.com/questions/8432581/how-to-sort-a-listobject-alphabetically-using-object-name-field
	public static List<WifiNetworkExport> sortWifiNetworksBySignal(List<WifiNetworkExport> wifiNetworkExport){
		if (wifiNetworkExport.size() > 0) {
			Collections.sort(wifiNetworkExport, new Comparator<WifiNetworkExport>() {
				@Override
				public int compare(final WifiNetworkExport object1, final WifiNetworkExport object2) {
					return ((Integer)((object1.getSignal()) * (-1))).compareTo(((Integer)((object2.getSignal() * (-1)))));
				}
			});
		}
		return wifiNetworkExport;
	}
	
	
	
	// building kml file
	
	//https://labs.micromata.de/projects/jak/quickstart.html                             jar FILES
	//https://developers.google.com/kml/documentation/kml_tut#placemarks                 KML FORMAT
	//http://freegeographytools.com/2007/putting-time-data-into-a-kml-file               TIMELINE
	
	
	public static void saveTokmlFile(List<DataToExport> dataToExportList) {
		Kml kml = new Kml();
		Folder kmlFolder = kml.createAndSetFolder();
		Document kmlDocument = kml.createAndSetDocument();
		List<StyleSelector> styleSelectorList = getStyleSelectorList();
		kmlDocument.setStyleSelector(styleSelectorList);
		for (DataToExport dataToExport : dataToExportList) {
			List<WifiNetworkExport> wifiNetworks = dataToExport.getWifiNetworks();
			
			Coordinate cord = new Coordinate(dataToExport.getLon(),dataToExport.getLat());
			List<Coordinate> coordinates = new ArrayList<>();
			coordinates.add(cord);
						
			String lineDescription = buildLineDescription(dataToExport);
			for (WifiNetworkExport wifiNetwork : wifiNetworks) {
				String description = buildDescription(wifiNetwork, lineDescription);
				String styleURL = getStyleURL(wifiNetwork);
				Placemark placemark = new Placemark();
				placemark.createAndSetTimeStamp().setWhen(convertToKMLDate(dataToExport.getTime()));
				placemark.withName(wifiNetwork.getSSID()).withDescription(description).withStyleUrl(styleURL);
				placemark.createAndSetPoint().withCoordinates(coordinates);
						
				kmlFolder.addToFeature(placemark);
			}
		}
		kmlDocument.addToFeature(kmlFolder);
		try {
			kml.marshal(new File(BASE_PATH + KML_FILE_NAME));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<StyleSelector> getStyleSelectorList() {
		List<StyleSelector> styleSelectorList = new ArrayList<>();
		Style redStyle = getStyleByColorAndHref("red", "http://maps.google.com/mapfiles/ms/icons/red-dot.png");
		Style yellowStyle = getStyleByColorAndHref("yellow", "http://maps.google.com/mapfiles/ms/icons/yellow-dot.png");
		Style greenStyle = getStyleByColorAndHref("green", "http://maps.google.com/mapfiles/ms/icons/green-dot.png");
		Style blueStyle = getStyleByColorAndHref("blue", "http://maps.google.com/mapfiles/ms/icons/blue-dot.png");
		Style orangeStyle = getStyleByColorAndHref("orange", "http://maps.google.com/mapfiles/ms/icons/orange-dot.png");
		styleSelectorList.add(redStyle);
		styleSelectorList.add(yellowStyle);
		styleSelectorList.add(greenStyle);
		styleSelectorList.add(blueStyle);
		styleSelectorList.add(orangeStyle);
		return styleSelectorList;
	}

	public static Style getStyleByColorAndHref(String color, String href) {
		Style redStyle = new Style();
		redStyle.setId(color);
		IconStyle iconStyle = new IconStyle();
		iconStyle.withScale(1.0);
		iconStyle.createAndSetIcon().withHref(href);
		redStyle.setIconStyle(iconStyle);
		return redStyle;
	}
	
	public static String getStyleURL(WifiNetworkExport wifiNetwork) {
		String styleURL = "";
		if(wifiNetwork.getSignal() > -67 ) styleURL = "#red";
		else if(wifiNetwork.getSignal() > -70 ) styleURL = "#orange";
		else if(wifiNetwork.getSignal() > -80 ) styleURL = "#blue";
		else if(wifiNetwork.getSignal() > -90 ) styleURL = "#green";
		else styleURL = "#yellow";
		return styleURL;
	}

	public static String buildLineDescription(DataToExport dataToExport) {
		StringBuilder ret = new StringBuilder();
		ret.append("Id: " + dataToExport.getId() + "\n");
		ret.append("Time: " + dataToExport.getTime() + "\n");
		ret.append("Alt: " + dataToExport.getAlt() + "\n\n");
		return ret.toString();
	}

	public static String buildDescription(WifiNetworkExport wifiNetwork, String lineDescription) {
		StringBuilder ret = new StringBuilder();
		ret.append(lineDescription);
		ret.append("Freuncy: " + wifiNetwork.getFreuncy() + "\n");
		ret.append("MAC: " + wifiNetwork.getMAC() + "\n");
		ret.append("Signal: " + wifiNetwork.getSignal() + "\n");
		return ret.toString();
	}

	public static void saveToCsvFile(String csvString) {
		try(  PrintWriter out = new PrintWriter(BASE_PATH + CSV_FILE_NAME)  ){
			out.println(csvString);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to save CSV file");
			e.printStackTrace();
		}
	}


	private static String convertToKMLDate(Date date) {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String time = dateformat.format(date);
		return time.replace("+0000", "Z");
	}


	private static List<File> getFilesList() {
		Stream<Path> paths = null;
		List<File> csvFiles = new ArrayList<>();
		try {
			paths = Files.walk(Paths.get(BASE_PATH));
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
