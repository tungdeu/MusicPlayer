package CustomAdapter;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;

public class CustomListSongListAdapter extends BaseAdapter {
	List<OfflineSong> Song;
	Context context;
	boolean isEdit;
	OfflineSong song;
	
	public CustomListSongListAdapter(Context context, List<OfflineSong> song, boolean isEdit) {
		super();
		Song = song;
		this.context = context;
		this.isEdit = isEdit;

	}
	
	public class ViewHolder {
		public TextView tview;
		public ImageView delete;
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
					R.layout.playlist_song_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tview = (TextView) convertView
					.findViewById(R.id.playlist_song_item_textViewSong);
			viewHolder.delete = (ImageView) convertView
					.findViewById(R.id.playlist_song_item_btn_spinner);
			
			if(isEdit){
				viewHolder.delete.setVisibility(View.VISIBLE);
			}
			
			viewHolder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int playlistID = 149959;
					int pos = (Integer) v.getTag();
					long audioID = Song.get(pos).getAudioId();
			        String[] projection = new String[] {"count(*)"};
			        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistID);
			        Cursor cur = context.getContentResolver().query(uri, projection, null, null, null);
			        cur.moveToFirst();
			        final int base = cur.getInt(0);
			        cur.close();
			        ContentValues values = new ContentValues();

			        context.getContentResolver().delete(uri, MediaStore.Audio.Playlists.Members.AUDIO_ID +" = "+audioID, null);
			        Song.remove(pos);
			        notifyDataSetChanged();
			        
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// viewHolder.position = position;
		song = Song.get(position);

		viewHolder.tview.setText(song.getTitle());
		viewHolder.delete.setTag(position);
		return convertView;

	}
	}


