package com.tung.musicplayer;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MusicChartFragment extends Fragment {
	private ListView lst;
	private String URL = "http://tosproxy.tk/MpServices.asmx?WSDL";
	private ArrayList<String> arrCate = new ArrayList<String>();
	private ArrayAdapter<String> adapter = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chart_layout, container, false);
		lst = (ListView) view.findViewById(R.id.chart_layout_lstView);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getList();
		adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrCate);
		lst.setAdapter(adapter);
		
		return view;
	}

	public void getList() {
		try {
			final String NAMESPACE = "http://tempuri.org/";
			final String METHOD_NAME = "getListArtist";
			final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			MarshalFloat marshal = new MarshalFloat();
			marshal.register(envelope);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject soapArray = (SoapObject) envelope.getResponse();
			arrCate.clear();

			for (int i = 0; i < soapArray.getPropertyCount(); i++) {
				// (SoapObject) soapArray.getProperty(i) get item at position i
				SoapObject soapItem = (SoapObject) soapArray.getProperty(i);

				String cateId = soapItem.getProperty("ArtistID").toString();
				String cateName = soapItem.getProperty("ArtistID").toString();

				arrCate.add(cateId + " - " + cateName);
			}

			adapter.notifyDataSetChanged();
		} catch (Exception e) {
		}
	}
}
