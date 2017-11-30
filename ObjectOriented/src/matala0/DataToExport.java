package matala0;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataToExport {

	public static final String SEPERATOR = ",";

	private long id;
	private Date time;
	private double lat;
	private double lon;
	private double alt;
	private List<WifiNetworkExport> wifiNetworks = new ArrayList<>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getAlt() {
		return alt;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	public List<WifiNetworkExport> getWifiNetworks() {
		return wifiNetworks;
	}
	public void setWifiNetworks(List<WifiNetworkExport> wifiNetworks) {
		this.wifiNetworks = wifiNetworks;
	}
	
	public static List<DataToExport> buildDataToExportList(List<WifiNetworkImport> wifiNetworkImportList) {
		List<DataToExport> dataToExportList = new ArrayList<>();
		int i = 1;
		for (WifiNetworkImport wifiNetworkImport : wifiNetworkImportList) {
			WifiNetworkExport wifiNetworkExport = WifiNetworkExport.buildWifinetworkToExport(wifiNetworkImport);
			boolean flag = true;
			for (DataToExport dataToExport : dataToExportList) {
				if (dataToExport.getTime().equals(wifiNetworkImport.getFirstSeen())){
					dataToExport.getWifiNetworks().add(wifiNetworkExport);
					flag = false;
					break;
				}
			}
			if (flag){
				DataToExport dataToExport = buildDataToExport(wifiNetworkImport, wifiNetworkExport,i++);
				dataToExportList.add(dataToExport);
			}
		}
		return dataToExportList;
	}

	public static DataToExport buildDataToExport(WifiNetworkImport wifiNetworkImport,
			WifiNetworkExport wifiNetworkExport, int index) {
		DataToExport dataToExport = new DataToExport();
		dataToExport.setId(index);
		dataToExport.setAlt(wifiNetworkImport.getAltitudeMeters());
		dataToExport.setLat(wifiNetworkImport.getCurrentLatitude());
		dataToExport.setLon(wifiNetworkImport.getCurrentLongitude());
		dataToExport.setTime(wifiNetworkImport.getFirstSeen());
		dataToExport.getWifiNetworks().add(wifiNetworkExport);
		return dataToExport;
	}
	
	public static String getRowFromDataToExport(DataToExport dataToExport) {
		StringBuilder row = new StringBuilder();
		row.append(dataToExport.getTime()).append(SEPERATOR);
		row.append(dataToExport.getId()).append(SEPERATOR);
		row.append(dataToExport.getLat()).append(SEPERATOR);
		row.append(dataToExport.getLon()).append(SEPERATOR);
		row.append(dataToExport.getAlt()).append(SEPERATOR);
		List<WifiNetworkExport> wifiNetworksList = dataToExport.getWifiNetworks();
		int sizeUpToTen = getSizeUpToTen(wifiNetworksList.size());
		row.append("" + sizeUpToTen).append(SEPERATOR);
		for (int i = 0; i < sizeUpToTen; i++) {
			WifiNetworkExport wifiNetworkExport = wifiNetworksList.get(i);
			row.append(wifiNetworkExport.getSSID()).append(SEPERATOR);
			row.append(wifiNetworkExport.getMAC()).append(SEPERATOR);
			row.append(wifiNetworkExport.getFreuncy()).append(SEPERATOR);
			row.append(wifiNetworkExport.getSignal()).append(SEPERATOR);
		}
		return row.toString();
	}

	public static int getSizeUpToTen(int size) {
		if(size <= 10){
			return size;
		}
		return 10;
	}

	public static String buildCSVData(List<DataToExport> DataToExportList) {
		StringBuilder csvStringBuilder = buildHeadRow();
		csvStringBuilder.append("\n");
		for (DataToExport dataToExport : DataToExportList) {
			String rowFromDataToExport = getRowFromDataToExport(dataToExport);
			csvStringBuilder.append(rowFromDataToExport).append("\n");
		}
		String csvString = csvStringBuilder.toString();
		return csvString;
	}
		
	public static StringBuilder buildHeadRow() {
		StringBuilder headRow = new StringBuilder();
		headRow.append("Time").append(SEPERATOR);
		headRow.append("ID").append(SEPERATOR);
		headRow.append("Lat").append(SEPERATOR);
		headRow.append("Lon").append(SEPERATOR);
		headRow.append("Alt").append(SEPERATOR);
		headRow.append("#WiFi networks").append(SEPERATOR);
		for (int i = 1; i < 11; i++) {
			headRow.append("SSID" + i).append(SEPERATOR);
			headRow.append("MAC" + i).append(SEPERATOR);
			headRow.append("Frequncy" + i).append(SEPERATOR);
			headRow.append("Signal" + i).append(SEPERATOR);
		}
		return headRow;
	}

	//https://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
	private static double distance(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = (float) (earthRadius * c);

	    return dist;
	    }
	
	public static List<DataToExport> SortListByC(List<DataToExport> dataToExportList, double lat, double lon, double radius){
		List<DataToExport> sortList = new ArrayList<>();
		for(DataToExport a : dataToExportList){
			if(distance(a.lat, a.lon, lat, lon)/1000<=radius){
				sortList.add(a);
			}
		}
		return sortList;
	}
	
	public static List<DataToExport> SortListByT(List<DataToExport> dataToExportList, String time){
		
		List<DataToExport> sortList = new ArrayList<>();
		for(DataToExport a : dataToExportList){
			if(a.getTime().toString().equals(time)){
				sortList.add(a);
			}
		}
		return sortList;
	}
	
}
