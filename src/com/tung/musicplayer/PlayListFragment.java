package com.tung.musicplayer;

import java.util.ArrayList;
import java.util.List;

import CustomAdapter.CustomPlayListAdapter;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tung.Entities.PlayListE;
import com.tung.object.PlaylistWorker;
import com.tung.screen.PlayListDetail;

public class PlayListFragment extends Fragment {
	PlayListFragment fragment;
	List<PlayListE> Playlist;
	Intent intent;
	TextView txtAdd;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragment = this;
		View view = inflater
				.inflate(R.layout.playlist_layout, container, false);
		txtAdd = (TextView)view.findViewById(R.id.playlist_layout_txtView);

		final String[] projection = { MediaStore.Audio.Playlists._ID,
				MediaStore.Audio.Playlists.NAME };
		final Cursor cursor = getActivity().getContentResolver().query(
				MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, projection,
				null, null, MediaStore.Audio.Playlists.NAME);
		
		Playlist = new ArrayList<PlayListE>();
		
		if (cursor.getCount()!=0){
			cursor.moveToFirst();
			do {
				
				long playListId = cursor.getLong(0);
				String playListName = cursor.getString(1);
				
				PlayListE plist = new PlayListE();
				plist.setPlayListId(playListId);
				plist.setPlayListName(playListName);
				
				Playlist.add(plist);
				
			}while (cursor.moveToNext());
		}
		intent = new Intent(getActivity(),PlayListDetail.class);
		final ListView lst = (ListView)view.findViewById(R.id.playlist_layout_lstView);
		final CustomPlayListAdapter adapter = new CustomPlayListAdapter(getActivity(), Playlist);
		lst.setAdapter(adapter);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				long playListId = Playlist.get(arg2).getPlayListId();
				String name = Playlist.get(arg2).getPlayListName();
				intent.putExtra("name", name);
				intent.putExtra("id", playListId);
				startActivity(intent);
				
			}
		});
		
		txtAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(getActivity(),R.style.cust_dialog);
				dialog.setContentView(R.layout.dialog_add_playlist);
				dialog.setTitle("Type name of playlist");
				
				final Button ok_btn = (Button)dialog.findViewById(R.id.dialog_add_ok);
				final Button cancel_btn = (Button)dialog.findViewById(R.id.dialog_add_cancel);
				final EditText input_txt = (EditText)dialog.findViewById(R.id.dialog_add_editBox);
				
				cancel_btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				ok_btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(input_txt.getText().toString()!=null){
					        ContentResolver cr = getActivity().getContentResolver();
					        
					        String playlistUri = "content://media/external/audio/playlists/";
					        
					        ContentValues cv = new ContentValues(1);

							Uri urr = Uri.parse(playlistUri);
							////cv.put(MediaStore.Audio.Playlists._ID, 1 + "");
							cv.put(MediaStore.Audio.Playlists.NAME, input_txt.getText().toString());
							
							cr.insert(urr, cv);
							PlayListE newplaylist = new PlayListE();
							newplaylist.setPlayListName(input_txt.getText().toString());

							Playlist.add(newplaylist);
							adapter.setSoure(Playlist);
							adapter.notifyDataSetChanged();
//							lst.setAdapter(adapter);
							dialog.dismiss();
							getActivity().recreate();
						}
						else dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		return view;
	}

}
