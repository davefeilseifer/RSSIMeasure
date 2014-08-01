package edu.unrrrl.rssimeasure;

import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class RSSIDisplay extends Activity {
	private static String IP = "192.168.1.126";
	private static String url_create_measurement = "http://" + IP + "/rssi/create_measurement.php";
	
    HttpClient httpclient = new DefaultHttpClient();
	
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
	                    Thread.sleep(500);
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

	
	private void checkWifi() {
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
	            Date d =new Date();
	            int time = d.getMinutes()*60+d.getSeconds();
	            String params = url_create_measurement + "?rssi=" + rssi + "&time=" + time;
	            new MeasureTask().execute(params);
	        //Need to get wifi strength
	        } 
	    }
	}

}
