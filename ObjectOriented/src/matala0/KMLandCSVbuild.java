package matala0;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;

/**
 * 
 * class for the csv and kml function.
 *
 */
public class KMLandCSVbuild {

	public static String BASE_PATH1 = "C:\\objectoriented\\matala1";
	public static String BASE_PATH2 = "C:\\objectoriented\\algo1";
	public static String BASE_PATH3 = "C:\\objectoriented\\algo2input";
	public static String EXPORT_PATH = "C:\\objectoriented\\export";
	public static String CSV_FILE_NAME1 = "\\exportData1.csv";
	public static String CSV_FILE_NAME2 = "\\exportData2.csv";
	public static String KML_FILE_NAME = "\\exportData.kml";
	public static final String SEPERATOR = ",";

	
		// building kml file
	
		//https://labs.micromata.de/projects/jak/quickstart.html                             jar FILES
		//https://developers.google.com/kml/documentation/kml_tut#placemarks                 KML FORMAT
		//http://freegeographytools.com/2007/putting-time-data-into-a-kml-file               TIMELINE
		
		/**
		 * 
		 * @param dataToExportList 
		 * read each object from the list and convert it to placmark in kml object
		 * @return kml file
		 */
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
				kml.marshal(new File(EXPORT_PATH + KML_FILE_NAME));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * build the URL line at the folder in kml file that "provide" the singal icon type
		 * @return
		 */
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
		/**
		 * 
		 * @param dataToExport 
		 * @return
		 */
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
			try(  PrintWriter out = new PrintWriter(EXPORT_PATH + CSV_FILE_NAME1)  ){
				out.println(csvString);
			} catch (FileNotFoundException e) {
				System.out.println("Failed to save CSV file");
				e.printStackTrace();
			}
		}

		public static void saveToCsvFile2(String csvString) {
			try(  PrintWriter out = new PrintWriter(EXPORT_PATH + CSV_FILE_NAME2)  ){
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


}
