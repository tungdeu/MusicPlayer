package CustomAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DownloadManager.Query;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tung.Entities.OfflineSong;
import com.tung.Entities.PlayListE;
import com.tung.musicplayer.R;
import com.tung.object.LoadImage;

public class CustomSongListAdapter extends BaseAdapter {
	List<OfflineSong> Song;
	Context context;
	OfflineSong song;

	Bitmap bitmap;

	public CustomSongListAdapter(Context context, List<OfflineSong> song) {
		super();
		Song = song;
		this.context = context;

	}

	public class ViewHolder {
		public TextView tview;
		public ImageView spinner;
		public LinearLayout toolbar;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return (Song != null) ? Song.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.song_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tview = (TextView) convertView
					.findViewById(R.id.song_item_textViewSong);
			viewHolder.spinner = (ImageView) convertView
					.findViewById(R.id.btn_spinner);
			viewHolder.toolbar = (LinearLayout) convertView
					.findViewById(R.id.toolbar);
			// this.toolbar = (LinearLayout)
			// convertView.findViewById(R.id.toolbar);
			viewHolder.spinner.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_add_song_to_list,null);
					TextView tview= (TextView)view1.findViewById(R.id.dialog_add_song_to_list_txtCreatNew);
					ListView lstView = (ListView)view1.findViewById(R.id.dialog_add_song_to_list_lst);
					AlertDialog.Builder dialogBuilder = new Builder(context);
					dialogBuilder.setTitle("Select Playlist");
					dialogBuilder.setView(view1);
					dialogBuilder.setCancelable(true);
					dialogBuilder.setNegativeButton("Cancel", null);
					
					final String[] projection = { MediaStore.Audio.Playlists._ID,
							MediaStore.Audio.Playlists.NAME };
					final Cursor cursor = context.getContentResolver().query(
							MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, projection,
							null, null, MediaStore.Audio.Playlists.NAME);
					final ArrayList<PlayListE> Playlist = new ArrayList<PlayListE>();
					
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
					
					final CustomPlayListAdapter adapter = new CustomPlayListAdapter(context, Playlist);
					lstView.setAdapter(adapter);
					final Dialog dialogA = dialogBuilder.create();
					dialogA.show();
					tview.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v1) {
							// TODO Auto-generated method stub
							View view2 = LayoutInflater.from(context).inflate(R.layout.dialog_add_song_to_list_sub,null);
							final AlertDialog.Builder dialogBuilder1 = new Builder(context);
							dialogBuilder1.setTitle("Select Playlist");
							dialogBuilder1.setView(view2);
							dialogBuilder1.setCancelable(true);
							dialogBuilder1.setNegativeButton("Cancel", null);
							
							final EditText txt = (EditText)view2.findViewById(R.id.dialog_add_song_to_list_sub_edittext);
							dialogBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									if(!txt.getText().toString().isEmpty()){
								        ContentResolver cr = context.getContentResolver();
								        
								        String playlistUri = "content://media/external/audio/playlists/";
								        
								        ContentValues cv = new ContentValues(1);
			
										Uri urr = Uri.parse(playlistUri);
										cv.put(MediaStore.Audio.Playlists.NAME, txt.getText().toString());
										
										cr.insert(urr, cv);
										PlayListE newplaylist = new PlayListE();
										newplaylist.setPlayListName(txt.getText().toString());
			
										Playlist.add(newplaylist);
										int playlistID = getPlaylistId(txt.getText().toString());
										
										
										int pos = (Integer)v.getTag();
										long audioID = Song.get(pos).getAudioId();
										
										String playlistUri1 = "content://media/external/audio/playlists/" + playlistID + "/members";
								        Uri urr1 = Uri.parse(playlistUri1);
								        ContentValues cv1 = new ContentValues(3);
								        
								        cv1.put(MediaStore.Audio.Playlists.Members.PLAYLIST_ID, playlistID + "");
								        cv1.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioID + "");
								        cv1.put(MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER, "0");
								        cv1.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, "0");
								        
								        context.getContentResolver().insert(urr1, cv1);
								        dialogA.dismiss();
									}
									else dialog.dismiss();
								}

							});
							final Dialog dialog1 = dialogBuilder1.create();
							dialog1.show();
						}
					});
					
					lstView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							long playlistID = Playlist.get(arg2).getPlayListId();
							int pos = (Integer)v.getTag();
							long audioID = Song.get(pos).getAudioId();
							
							String playlistUri = "content://media/external/audio/playlists/" + playlistID + "/members";
					        Uri urr = Uri.parse(playlistUri);
					        ContentValues cv = new ContentValues(3);
					        
					        cv.put(MediaStore.Audio.Playlists.Members.PLAYLIST_ID, playlistID + "");
					        cv.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioID + "");
					        cv.put(MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER, "0");
					        cv.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, "0");
					        
					        context.getContentResolver().insert(urr, cv);
					        dialogA.dismiss();
						}
					});
					

				}
			});
			
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		song = Song.get(position);
		viewHolder.spinner.setTag(position);
		viewHolder.tview.setText(song.getTitle());
		return convertView;

	}


	public void setSource(List<OfflineSong> Songs) {
		this.Song = Songs;
		if ((Songs != null)) {
			LoadImage loadImage = new LoadImage(this);
			loadImage.execute(0);
		}
	}

	public List<OfflineSong> getSource() {
		return Song;
	}
	

	int getPlaylistId(String playlistName)
	{
		Cursor c = context.getContentResolver().query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
				new String[]
				{ MediaStore.Audio.Playlists._ID },
				MediaStore.Audio.Playlists.NAME + "=?", 
				new String[]
				{ playlistName }, 
				MediaStore.Audio.Playlists.NAME);
		int id = intFromCursor(c);
		return id;
	}
	private static int intFromCursor(Cursor c)
	{
		int id = -1;
		if (c != null)
		{
			c.moveToFirst();
			if (!c.isAfterLast())
			{
				id = c.getInt(0);
			}
		}

		c.close();
		return id;
	} 
}
