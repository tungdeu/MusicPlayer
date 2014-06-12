//package com.tung.screen;
//
//import com.tung.musicplayer.R;
//import com.tung.musicplayer.R.id;
//import com.tung.musicplayer.R.layout;
//import com.tung.musicplayer.R.menu;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.TextView;
//
//public class WelcomeScreen extends Activity {
//	
//	Intent intent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_welcome_screen);
//        TextView txt = (TextView)findViewById(R.id.welcomeTxt);
//        intent = new Intent(this, SongList.class);
//        txt.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				startActivity(intent);
//			}
//		});
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.welcome_screen, menu);
//        return true;
//    }
//    
//}
