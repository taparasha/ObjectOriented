package matala0;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	

	public static WifiNetworkExport buildWifinetworkToExport(WifiNetworkImport wifiNetworkImport) {
		WifiNetworkExport wifiNetworkExport = new WifiNetworkExport();
		wifiNetworkExport.setSSID(wifiNetworkImport.getSSID());
		wifiNetworkExport.setSignal(wifiNetworkImport.getRSSI());
		wifiNetworkExport.setMAC(wifiNetworkImport.getMAC());
		wifiNetworkExport.setFreuncy(wifiNetworkImport.getAccuracyMeters());
		return wifiNetworkExport;
	}
	
	
	

}
