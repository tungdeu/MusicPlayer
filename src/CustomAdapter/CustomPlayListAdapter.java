package CustomAdapter;

import java.util.List;

import CustomAdapter.CustomSongListAdapter.ViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tung.Entities.OfflineSong;
import com.tung.Entities.PlayListE;
import com.tung.musicplayer.R;

public class CustomPlayListAdapter extends BaseAdapter {

	List<PlayListE> Playlist;
	Context context;
	PlayListE plist;

	public CustomPlayListAdapter(Context context, List<PlayListE> playlist) {
		super();
		Playlist = playlist;
		this.context = context;

	}

	public class ViewHolder {
		public TextView tview;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Playlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if (arg1 == null) {
			arg1 = LayoutInflater.from(context).inflate(R.layout.playlist_item,
					arg2, false);
			viewHolder = new ViewHolder();
			viewHolder.tview = (TextView) arg1
					.findViewById(R.id.song_item_textViewSong);
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}

		plist = Playlist.get(arg0);
		viewHolder.tview.setText(plist.getPlayListName());
		return arg1;

	}

	public void setSoure(List<PlayListE> playlist2) {
		Playlist = playlist2;
		
	}

}
