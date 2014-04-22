package CustomAdapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;

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
					R.layout.song_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tview = (TextView) convertView
					.findViewById(R.id.song_item_textViewSong);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// viewHolder.position = position;

		song = Song.get(position);

		viewHolder.tview.setText(song.getTitle());
		return convertView;

	}
}
