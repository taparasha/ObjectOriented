package matala0;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataToExportWithPI {


	private long id;
	private Date time;
	private double lat;
	private double lon;
	private double alt;
	private double PI;
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
	public void setPI(double PI) {
		this.PI = PI;
	}
	public double getPI() {
		return PI;
	}
	
	/*	
	public static List<DataToExport> Algo2(List<DataToExport> combo,List<DataToExport> input){
		List<DataToExport> answer = new ArrayList<>();
		for(DataToExport a : input){
			DataToExport temp = new DataToExport();
			temp = CheckSimilarity(a,combo);
			answer.add(temp);
		}
		return answer;
	}
	
	
	public static DataToExport CheckSimilarity(DataToExport a, List<DataToExport> combo){
		List<DataToExportWithPI> SimilPI = new ArrayList<>();
		for(DataToExport b : combo){
			DataToExportWithPI temp = new DataToExportWithPI();
			temp.setAlt(b.getAlt());
			temp.setId(b.getId());
			temp.setLat(b.getLat());
			temp.setLon(b.getLon());
			temp.setTime(b.getTime());
			temp.setWifiNetworks(b.getWifiNetworks());
			
			temp.setPI(createPI(a.getWifiNetworks(),b.getWifiNetworks()));
			SimilPI.add(temp);
		}
		
		List<DataToExportWithPI> sortSimilPI = new ArrayList<>();
		sortSimilPI = sortByPI(SimilPI);
		
		List<DataToExportWithPI> OnlyThree = new ArrayList<>();
		for(int i=0;i<3;i++){
			OnlyThree.add(sortSimilPI.get(i));
		}
		
		DataToExport answer = new DataToExport();
		answer = Algo1(OnlyThree);
		
		return answer;
	}*/
	
}
