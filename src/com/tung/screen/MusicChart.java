package com.tung.screen;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tung.musicplayer.R;


public class MusicChart extends Activity{
	private ListView lst;
	private ArrayList<String> arrCate = new ArrayList<String>();
	private ArrayAdapter<String> adapter = null;
	private static final String SOAP_ACTION = "http://tempuri.org/GetListTopSong";
    private static final String METHOD_NAME = "GetListTopSong";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://tungconcobebe.somee.com/WebService.asmx?WSDL";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_layout);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrCate);
		
		Thread networkThread = new Thread() {
	        public void run() {
	            try {
	               SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);         
	               SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	               envelope.setOutputSoapObject(request);
	               HttpTransportSE ht = new HttpTransportSE(URL);
	               ht.call(SOAP_ACTION, envelope);
	               SoapObject soapArray=(SoapObject) envelope.getResponse();
	               arrCate.clear();
	               for(int i=0; i<soapArray.getPropertyCount(); i++)
	               {
	               //(SoapObject) soapArray.getProperty(i) get item at position i
	               //final SoapObject soapItem =(SoapObject) soapArray.getProperty();
	               //soapItem.getProperty("CateId") get value of CateId property
	               final String artist=soapArray.getProperty(i).toString();
	               arrCate.add(artist);
	               }
	               runOnUiThread (new Runnable(){ 
	                   public void run() {
	                	   		lst = (ListView)findViewById(R.id.chart_layout_lstView);
	                	   		lst.setAdapter(adapter);
	                	   		adapter.notifyDataSetChanged();
	                         }
	                     });
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	            }
		}
		};
		
	        networkThread.start();
	        
	        lst.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String Title = arrCate.get(arg2);
				}
			});
	}
	
	
}

