package matala0;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MacImprove {

	/** 
	 * this class was created in order of taking 3 mac points and improve them to an averaged new point  	
	 */
	public static String BASE_PATH = "C:\\tmp";
	public static String CSV_FILE_NAME = "\\exportMacImprove.csv";
	public static final String SEPERATOR = ",";

	private double currentLatitude;
	private double currentLongitude;
	private double altitudeMeters;
	private String MAC;
	private int RSSI;

	public double getCurrentLatitude() {
		return currentLatitude;
	}
	public void setCurrentLatitude(double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}
	public double getCurrentLongitude() {
		return currentLongitude;
	}
	public void setCurrentLongitude(double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}
	public double getAltitudeMeters() {
		return altitudeMeters;
	}
	public void setAltitudeMeters(double altitudeMeters) {
		this.altitudeMeters = altitudeMeters;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mAC) {
		MAC = mAC;
	}
	public int getRSSI() {
		return RSSI;
	}
	public void setRSSI(int rSSI) {
		RSSI = rSSI;
	}

	/**
	 * this function take data from WifiNetworkImport object and enter it to MacImprove object.
	 * @param wifiNetworkImport
	 * @return MacImprove object
	 */

	public static MacImprove buildMacImprove(WifiNetworkImport wifiNetworkImport) {
		MacImprove MacImprove = new MacImprove();
		MacImprove.setAltitudeMeters(wifiNetworkImport.getAltitudeMeters());
		MacImprove.setRSSI(wifiNetworkImport.getRSSI());
		MacImprove.setMAC(wifiNetworkImport.getMAC());
		MacImprove.setCurrentLatitude(wifiNetworkImport.getCurrentLatitude());
		MacImprove.setCurrentLongitude(wifiNetworkImport.getCurrentLongitude());		

		return MacImprove;
	}

	/**
	 * this function take WifiNetworkImportList and build MacImproveList
	 * @param wifiNetworkImportList
	 * @return
	 */

	public static List<MacImprove> buildMacImproveList(List<WifiNetworkImport> wifiNetworkImportList) {
		List<MacImprove> MacImproveList = new ArrayList<>();
		for (WifiNetworkImport wifiNetworkImport : wifiNetworkImportList) {
			MacImprove MacImprove = buildMacImprove(wifiNetworkImport);
			MacImproveList.add(MacImprove);
		}
		return MacImproveList;
	}

	/**
	 * this function sort a list of MacImprove by RSSI
	 * @param MacImproveList
	 * @return MacImproveList
	 */

	public static List<MacImprove> SortMacImproveList(List<MacImprove> MacImproveList){
		if (MacImproveList.size() > 0) {
			Collections.sort(MacImproveList, new Comparator<MacImprove>() {
				@Override
				public int compare(final MacImprove object1, final MacImprove object2) {
					return ((Integer)((object1.getRSSI()) * (-1))).compareTo(((Integer)((object2.getRSSI() * (-1)))));
				}
			});
		}
		return MacImproveList;
	}

	/**
	 * this function take all the Mac addresses and orgenize them to be in list by groups of each Mac addresse 
	 * @param MacImproveList
	 * @return MacImproveList
	 */
	public static List <MacImprove> OrgenizeMacImproveList(List <MacImprove> MacImproveList){
		List<MacImprove> temp = new ArrayList<>();
		List<String> temp2 = new ArrayList<>();

		for(MacImprove a: MacImproveList){
			if(temp2.contains(a.getMAC()))
				continue;
			for(MacImprove b: MacImproveList){
				if (a.getMAC().equals(b.getMAC())){
					temp.add(a);
					temp2.add(a.getMAC());}
			}
		}	
		return temp;
	}	

	/**
	 * this function take every group of similar Mac address and reduce the group to Maximum 3 Mac addresses that have the most RSSI value
	 * @param MacImproveList
	 * @return MacImproveList
	 */
	public static List <MacImprove> ReduceMacImproveList(List <MacImprove> MacImproveList){
		List<MacImprove> temp = new ArrayList<>();
		List<MacImprove> temp2 = new ArrayList<>();
		List<MacImprove> pelet = new ArrayList<>();
		int i=1;
		String t="";

		temp=OrgenizeMacImproveList(MacImproveList);

		for(MacImprove a: temp){
			if (a.getMAC().equals(t) && i%3!=0 ){
				temp2.add(a);	
				temp2=SortMacImproveList(temp2);
				t=a.getMAC();
				i++;
			}
			else if (a.getMAC().equals(t) && i%3==0 )
				continue;

			else  { 
				for(MacImprove b: temp2){
					pelet.add(b);}
				temp2.clear();
				temp2.add(a);	
				t=a.getMAC();
				i=1;
			}					
		}			
		return pelet;
	}

	public static List<MacImprove> Algo1 (List<MacImprove> MacImproveList){
		List<MacImprove> ready = new ArrayList<>();
		List<String> ready2 = new ArrayList<>();
		List<MacImprove> answer = new ArrayList<>();

		for(MacImprove a: MacImproveList){
			if(ready2.contains(a.getMAC())){
				ready.add(a);
				ready2.add(a.getMAC());
			}
			else{
				answer.add(calc(ready));
				ready.clear();
				ready2.clear();
				ready.add(a);
				ready2.add(a.getMAC());
			}
		}
		answer.add(calc(ready));

		return answer;
	}				


	/**
	 * this function take a list of Mac addresses and create a averaged lat lon and alt from them.
	 * @param WifiMacList
	 * @return
	 */
	public static MacImprove calc(List<MacImprove> MacImproveList){
		MacImprove finala = new MacImprove();
		double sumLat=0, sumLon=0, sumAlt=0;
		double sumWeight=0;

		for(MacImprove a: MacImproveList){
			int signal=a.getRSSI();
			double weigth=1/Math.pow(signal, 2);

			sumAlt=+(a.getAltitudeMeters()*weigth);
			sumLon=+(a.getCurrentLongitude()*weigth);                          
			sumLat=+(a.getCurrentLatitude()*weigth);   	
			sumWeight=+weigth;
			finala.setMAC(a.getMAC());
		}
		finala.setAltitudeMeters(sumAlt/sumWeight);
		finala.setCurrentLatitude(sumLat/sumWeight);
		finala.setCurrentLongitude(sumLon/sumWeight);
		return finala;
	}



	public static void saveToCsvFile(List<MacImprove> MacImproveList){
		StringBuilder ret = new StringBuilder();
		int i=0;
		for(MacImprove a: MacImproveList){
			ret.append(i+SEPERATOR);
			ret.append(a.getMAC()+SEPERATOR);
			ret.append(a.getCurrentLatitude()+SEPERATOR);
			ret.append(a.getCurrentLongitude()+SEPERATOR);
			ret.append(a.getAltitudeMeters()+"\n");
			i++;
		}
		saveToCsv(ret.toString());
	}


	private static void saveToCsv(String csvString) {
		try(  PrintWriter out = new PrintWriter(BASE_PATH + CSV_FILE_NAME)  ){
			out.println(csvString);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to save CSV file");
			e.printStackTrace();
		}
	}

}
