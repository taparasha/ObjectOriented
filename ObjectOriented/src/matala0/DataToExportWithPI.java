package matala0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
		
	public static List<DataToExport> algo2(List<DataToExport> combo, List<DataToExport> input){
		List<DataToExport> ret = new ArrayList<>();
		for(DataToExport dataToExport : input){
			DataToExport temp = checkSimilarity(dataToExport,combo);
			dataToExport.setAlt(temp.getAlt());
			dataToExport.setLat(temp.getLat());
			dataToExport.setLon(temp.getLon());
			ret.add(dataToExport);
			
		}
		return ret;
	}
	
	
	public static DataToExport checkSimilarity(DataToExport input, List<DataToExport> combo){
		List<DataToExportWithPI> similPI = new ArrayList<>();
		for(DataToExport comboDataToExport : combo){
			DataToExportWithPI temp = buildDataToExportWithPI(comboDataToExport);
			double pi = createPI(input.getWifiNetworks(),comboDataToExport.getWifiNetworks());
			temp.setPI(pi);
			similPI.add(temp);
		}
		similPI = sortByPI(similPI);
		
		
		
		return algo1withpi(similPI);
	}
	
	
	private static DataToExportWithPI buildDataToExportWithPI(DataToExport dataToExport) {
		DataToExportWithPI ret = new DataToExportWithPI();
		ret.setAlt(dataToExport.getAlt());
		ret.setId(dataToExport.getId());
		ret.setLat(dataToExport.getLat());
		ret.setLon(dataToExport.getLon());
		ret.setTime(dataToExport.getTime());
		ret.setWifiNetworks(dataToExport.getWifiNetworks());
		return ret;
	}
	
	
	public static double createPI(List<WifiNetworkExport> input, List<WifiNetworkExport> combo){
		double inputLineSig, comboLineSig, diff, sigDiff = 0.4;
		int norm = 10000;
		double weight = 1;
		
		for(WifiNetworkExport wifiNetworkExport: input){
			String mac1 = wifiNetworkExport.getMAC();
			comboLineSig = -120;
			inputLineSig = wifiNetworkExport.getSignal();
			diff = 100;

			for (WifiNetworkExport comboWifiNetworkExport : combo) {
				String mac2 = comboWifiNetworkExport.getMAC();	
				if (mac1.equals(mac2)) {
					comboLineSig = comboWifiNetworkExport.getSignal();
					diff = inputLineSig - comboLineSig;
					continue;
				}
			}
			double pow1 = Math.pow(diff, sigDiff);
			double pow2 = Math.pow(inputLineSig, 2);
			weight = weight * (norm/(pow1*pow2));
		}
		return weight;
	}
	
	public static  DataToExport algo1withpi (List<DataToExportWithPI> similPI){
		DataToExport finala = new DataToExport();
		double sumLat=0, sumLon=0, sumAlt=0, g=0, t=0, e=0;
		double sumWeight=0;
		
		if (similPI.isEmpty()) return finala;
		for (int i = 0; i < 3; i++) {
			DataToExportWithPI dataToExportWithPI = similPI.get(i);
			double weigth = dataToExportWithPI.getPI();
			g=dataToExportWithPI.getAlt();
			t=dataToExportWithPI.getLon();
			e=dataToExportWithPI.getLat();
			
			
			sumAlt += (dataToExportWithPI.getAlt() * weigth);
			sumLon += (dataToExportWithPI.getLon() * weigth);                          
			sumLat += (dataToExportWithPI.getLat() * weigth);   	
			sumWeight += weigth;
		}
		finala.setAlt(sumAlt/sumWeight);
		finala.setLat(sumLat/sumWeight);
		finala.setLon(sumLon/sumWeight);
		return finala;
	}
	
	
	
	public static List<DataToExportWithPI> sortByPI(List<DataToExportWithPI> similPI){
		if (!similPI.isEmpty() && similPI.size() > 0) {
			Collections.sort(similPI, new Comparator<DataToExportWithPI>() {
				@Override
				public int compare(final DataToExportWithPI object1, final DataToExportWithPI object2) {
					return ((Double)(object1.getPI())).compareTo(((Double)(object2.getPI())));
				}
			});
		}
		Collections.reverse(similPI);
		return similPI;
	}


	
	
}
