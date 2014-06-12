package com.tung.screen;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import CustomAdapter.CustomOnlineSongAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.tung.Entities.OnlineSong;
import com.tung.musicplayer.R;
import com.tung.object.CreateList;

public class SearchSong extends Activity {
	private EditText txtsearch;
	private ImageView img;
	private ListView lst;
	private ArrayList<OnlineSong> Song = new ArrayList<OnlineSong>();
	private CustomOnlineSongAdapter adapter = null;
	private static final String SOAP_ACTION = "http://tempuri.org/GetListTopSong";
	private static final String METHOD_NAME = "SearchResult";
	private static final String METHOD_NAME_1 ="getDirectLink";
	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = "http://tungconcobebe.somee.com/WebService.asmx?WSDL";
private String link;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_song);
		txtsearch = (EditText) findViewById(R.id.search_song_txtSearch);
		img = (ImageView) findViewById(R.id.search_song_imgsearch);
		lst = (ListView) findViewById(R.id.search_song_lst);
		adapter = new CustomOnlineSongAdapter(this, Song);
		final Intent intent = new Intent(this, PlaySong.class);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!txtsearch.getText().toString().isEmpty()) {
					Thread networkThread = new Thread() {
						public void run() {
							try {
								SoapObject request = new SoapObject(NAMESPACE,
										METHOD_NAME);
								request.addProperty("key", txtsearch.getText()
										.toString());
								SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
										SoapEnvelope.VER11);
								envelope.setOutputSoapObject(request);
								HttpTransportSE ht = new HttpTransportSE(URL);
								ht.call(SOAP_ACTION, envelope);
								SoapObject soapArray = (SoapObject) envelope
										.getResponse();
								Song.clear();
								OnlineSong song = new OnlineSong();
								// bước này lấy cả file XML
								soapArray = (SoapObject) envelope.getResponse();
					               for(int i=0; i<soapArray.getPropertyCount(); i++)
					               {
					            	   song.setTitle(soapArray.getProperty(i).toString());
//					            	   song.setSource(soapArray.getProperty(i).);
					               //(SoapObject) soapArray.getProperty(i) get item at position i
					               //final SoapObject soapItem =(SoapObject) soapArray.getProperty();
					               //soapItem.getProperty("CateId") get value of CateId property
					               
					               Song.add(song);
					               }

								runOnUiThread(new Runnable() {
									public void run() {

										lst.setAdapter(adapter);
										adapter.notifyDataSetChanged();
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};

					networkThread.start();
				}
			}

		});
		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				final String key = Song.get(arg2).getTitle();
				if (!key.isEmpty()) {
					Thread networkThread = new Thread() {
						public void run() {
							try {
								SoapObject request = new SoapObject(NAMESPACE,
										METHOD_NAME_1);
								request.addProperty("key", key);
								SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
										SoapEnvelope.VER11);
								envelope.setOutputSoapObject(request);
								HttpTransportSE ht = new HttpTransportSE(URL);
								ht.call(SOAP_ACTION, envelope);
								SoapObject soapArray = (SoapObject) envelope
										.getResponse();
								// bước này lấy cả file XML
								soapArray = (SoapObject) envelope.getResponse();
					               for(int i=0; i<soapArray.getPropertyCount(); i++)
					               {
					            	   link = soapArray.getProperty(i).toString();
					               }

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};

					networkThread.start();
				}
				intent.putExtra("link", link);
				intent.putExtra("flag", 9);
				intent.putExtra("title", key);
				String link1 = "http://mp3.zing.vn/xml/load-song/MjAxMSUyRjA2JTJGMTQlMkZhJTJGMSUyRmExYzJmYzFiOThjYjZmMzgxNWUyODM3ZDE2NWI5NzYzLm1wMyU3QzI";
				startActivity(intent);
				CreateList.getInstance().playSong(link1);
			}
			
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_song, menu);
		return true;
	}

}
