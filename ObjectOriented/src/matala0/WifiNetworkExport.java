package matala0;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * 
 * the object WifiNetworkExport got 4 units from the WifiNetworkImport.
 * the units present each wifi networt in specific spot.
 *
 */
public class WifiNetworkExport {

	private String SSID;
	private String MAC;
	private int freuncy;
	private int signal;
	
	
	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mAC) {
		MAC = mAC;
	}
	public int getFreuncy() {
		return freuncy;
	}
	public void setFreuncy(int freuncy) {
		this.freuncy = freuncy;
	}
	public int getSignal() {
		return signal;
	}
	public void setSignal(int signal) {
		this.signal = signal;
	}
	
/**
 * 
 * @param wifiNetworkImport
 * @return an object WifiNetworkExport with the 4 correct units
 */
	public static WifiNetworkExport buildWifinetworkToExport(WifiNetworkImport wifiNetworkImport) {
		WifiNetworkExport wifiNetworkExport = new WifiNetworkExport();
		wifiNetworkExport.setSSID(wifiNetworkImport.getSSID());
		wifiNetworkExport.setSignal(wifiNetworkImport.getRSSI());
		wifiNetworkExport.setMAC(wifiNetworkImport.getMAC());
		wifiNetworkExport.setFreuncy(wifiNetworkImport.getAccuracyMeters());
		return wifiNetworkExport;
	}
	
	/**
	 * sortBySignal
	 * the function take the wifiNetworkExport and sort it by signal.
	 * at the writing, the program will write only the first 10
	 * 
	 * https://stackoverflow.com/questions/8432581/how-to-sort-a-listobject-alphabetically-using-object-name-field
	 */
	
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

	

}
