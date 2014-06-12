package CustomAdapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tung.Entities.OfflineSong;
import com.tung.Entities.OnlineSong;
import com.tung.musicplayer.R;
import com.tung.object.BitmapProcess;
import com.tung.object.MemoryLruCache;

public class CustomOnlineSongAdapter extends BaseAdapter {

	List<OnlineSong> Song;
	Context context;
	OnlineSong song;
	MemoryLruCache memoryLruCache;
	Bitmap bitmap;

	public CustomOnlineSongAdapter(Context context, List<OnlineSong> song) {
		super();
		Song = song;
		this.context = context;
		

	}

	public class ViewHolder {
		
		public TextView tview;
		public int position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return Song.size();
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
					R.layout.online_song_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tview = (TextView) convertView
					.findViewById(R.id.online_song_item_textViewSong);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.position = position;

		song = Song.get(position);

		viewHolder.tview.setText(song.getTitle());

		return convertView;

	}
}
