package edu.unrrrl.rssimeasure;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class RSSIDisplay extends Activity {

	WifiManager wifiManager;
	Handler mHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	            // TODO Auto-generated method stub
	            while (true) {
	                try {
	                    Thread.sleep(1000);
	                    mHandler.post(new Runnable() {

	                        @Override
	                        public void run() {
	                            // TODO Auto-generated method stub
	                            // Write your code here to update the UI.
	                        	checkWifi();
	                        }
	                    });
	                } catch (Exception e) {
	                    // TODO: handle exception
	                }
	            }
	        }
	    }).start();
		
	}

	
	private void checkWifi(){
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo Info = cm.getActiveNetworkInfo();
	    if (Info == null || !Info.isConnectedOrConnecting()) {
	        Log.i("WIFI CONNECTION", "No connection");
	    } else {
	        int netType = Info.getType();
	        int netSubtype = Info.getSubtype();

	        if (netType == ConnectivityManager.TYPE_WIFI) {
	            wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
	            int linkSpeed = wifiManager.getConnectionInfo().getLinkSpeed();
	            int rssi = wifiManager.getConnectionInfo().getRssi();
	            Log.i("WIFI CONNECTION", "Wifi connection speed: "+linkSpeed + " rssi: "+rssi);


	        //Need to get wifi strength
	        } 
	    }
	}

}
