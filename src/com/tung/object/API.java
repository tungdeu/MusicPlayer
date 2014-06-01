package com.tung.object;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class API {
	String songTitle;
	String artist;
	String path;
	int listenTimes;
	ArrayList<String> lst;
	
	public ArrayList<String> getListArtist(){
        final String SOAP_ACTION = "http://tempuri.org/getListArtist";
        final String METHOD_NAME = "getListArtist";
        final String NAMESPACE = "http://tempuri.org/";
        final String URL = "http://tungconcobebe.somee.com/WebService.asmx";
        //những cái trên nếu webservice của bạn tên khác thì phải đổi


        SoapObject table = null;                        // Cái này chứa table của dataset trả về thông qua SoapObject
        SoapObject client = null;                        // Its the client pettition to the web service(đoan này mình cũng chưa rõ)
        SoapObject tableRow = null;                        // Chứa hàng của bảng
        SoapObject responseBody = null;                    // Chứa nội dung XML của dataset
        HttpTransportSE transport = null;            // cái này để gọi webservice
        SoapSerializationEnvelope sse = null;
        //cái này trong tut viết thế, mình lười đổi tên


        sse = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
        sse.addMapping(NAMESPACE, "API", this.getClass());
        //chú ý nếu class(ở trên đầu í) ko phải là phim thì đổi
        sse.dotNet = true; // .NET thì chọn true
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        API api = new API();
        try 
        {
            client = new SoapObject(NAMESPACE, METHOD_NAME);
            sse.setOutputSoapObject(client);
            sse.bodyOut = client;
            androidHttpTransport.call(SOAP_ACTION, sse);
            
            // bước này lấy cả file XML
            responseBody = (SoapObject) sse.getResponse();
            api.lst  = new ArrayList<String>();
            for (int i=0;i<responseBody.getPropertyCount();i++){
            	api.lst.add(responseBody.getProperty(i).toString());
            }

            return api.lst;
            
        } catch (Exception e) 
        {
            api.lst.add(e.toString());
            return api.lst;
        }
	}
	
	//public API getListSong()
}
