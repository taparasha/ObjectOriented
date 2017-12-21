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
	
		
	public static List<DataToExport> Algo2(List<DataToExport> combo,List<DataToExport> input){
		List<DataToExport> answer = new ArrayList<>();
		for(DataToExport a : input){
			DataToExport temp = new DataToExport();
			temp = CheckSimilarity(a,combo);
			a.setAlt(temp.getAlt());
			a.setLat(temp.getLat());
			a.setLon(temp.getLon());
			answer.add(a);
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
	}
	
	
	public static double createPI(List<WifiNetworkExport> input, List<WifiNetworkExport> combo){
		double inputSig, dataSig, diff, sigDiff=0.4;
		int i=0, norm=10000;
		double weight=1;
		
		for(WifiNetworkExport a: input){
			if(a.getSignal()==0)
			{inputSig=(-120);}
			
			inputSig=a.getSignal();
			dataSig=combo.get(i).getSignal();
			diff=inputSig-dataSig;
			weight=weight*(norm/(Math.pow(diff, sigDiff)*Math.pow(inputSig, 2)));
			i++;
		}
		return weight;
	}
	
	public static  DataToExport Algo1 (List<DataToExportWithPI> OnlyThree){
		DataToExport finala = new DataToExport();
		double sumLat=0, sumLon=0, sumAlt=0;
		double sumWeight=0;

		for(DataToExportWithPI a: OnlyThree){
			double weigth=a.getPI();

			sumAlt=+(a.getAlt()*weigth);
			sumLon=+(a.getLon()*weigth);                          
			sumLat=+(a.getLat()*weigth);   	
			sumWeight=+weigth;
		}
		finala.setAlt(sumAlt/sumWeight);
		finala.setLat(sumLat/sumWeight);
		finala.setLon(sumLon/sumWeight);
		return finala;
	}
	
	public static List<DataToExportWithPI> sortByPI(List<DataToExportWithPI> sortSimilPi){
		if (sortSimilPi.size() > 0) {
			Collections.sort(sortSimilPi, new Comparator<DataToExportWithPI>() {
				@Override
				public int compare(final DataToExportWithPI object1, final DataToExportWithPI object2) {
					return ((Integer)(int)((object1.getPI()*1000000))).compareTo(((Integer)(int)((object2.getPI()*1000000))));
				}
			});
		}
		return sortSimilPi;
	}
	
	
}
