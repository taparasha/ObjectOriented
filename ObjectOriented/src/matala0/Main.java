package matala0;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author OriMol, TaParasha, YoavHenig
 * @category main
 */

public class Main {
	public static String BASE_PATH3 = "C:\\objectoriented\\algo2input";
	public static String BASE_PATH4 = "C:\\objectoriented\\algo2comb";
	public static void main(String[] args) {

		/**
		 * this code take csv files and put the data into objects of WifiNetworkImport (for matala 1)
		**/
		
		List<WifiNetworkImport> wifiNetworkImportList = new ArrayList<>();
		List<File> csvFiles = WifiNetworkImport.getFilesListForNetworkImport();

		for (File file : csvFiles) {
			List<WifiNetworkImport> convertCsvToWifiNetworkImport = WifiNetworkImport.convertCsvToWifiNetworkImport(file);
			wifiNetworkImportList.addAll(convertCsvToWifiNetworkImport);
		}
		csvFiles = null;

		List<DataToExport> dataToExportList = DataToExport.buildDataToExportList(wifiNetworkImportList);         
		
		for (DataToExport dataToExport : dataToExportList) {
			List<WifiNetworkExport> sortWifiNetworksBySignal = WifiNetworkExport.sortWifiNetworksBySignal(dataToExport.getWifiNetworks());
			dataToExport.setWifiNetworks(sortWifiNetworksBySignal);
		}
		
		DataToExport.SortByUser(dataToExportList);
		
		String csvString = DataToExport.buildCSVData(dataToExportList);		
		KMLandCSVbuild.saveToCsvFile(csvString);
		KMLandCSVbuild.saveTokmlFile(dataToExportList);


		/**
		 * this code take csv files and put the data into objects of DataToExport (for matala 2)
		 **/
	
		List<DataToExport> comb = dataToExportList;
		List<DataToExport> input = new ArrayList<>();
		
		List<File> csvFiles3 = DataToExport.getFilesListForDataToExport(BASE_PATH3);

		for (File file : csvFiles3) {
			List<DataToExport> convertCsvToDataToExport = DataToExport.convertCsvToDataToExport(file);
			input.addAll(convertCsvToDataToExport);
		}
		csvFiles3 = null;

		
		List<MacImprove> macImproveList = MacImprove.buildMacImproveList(wifiNetworkImportList);
		wifiNetworkImportList = null;
		List<MacImprove> d = new ArrayList<>();
		List<MacImprove> r = new ArrayList<>();
		
		d=MacImprove.reduceMacImproveList(macImproveList);
		macImproveList = null;
	    r=MacImprove.Algo1(d);
		
		MacImprove.saveToCsvFile(r);
		
		List<DataToExport> algo2 = DataToExportWithPI.algo2(comb,input);
		
		String newCsv = DataToExport.buildCSVData(algo2);
		KMLandCSVbuild.saveToCsvFile2(newCsv);

	}
	
}
