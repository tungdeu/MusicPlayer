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
import com.tung.musicplayer.R;
import com.tung.object.BitmapProcess;
import com.tung.object.MemoryLruCache;

public class CustomArtistListAdapter extends BaseAdapter {
	List<OfflineSong> Song;
	Context context;
	OfflineSong song;
	MemoryLruCache memoryLruCache;
	Bitmap bitmap;

	public CustomArtistListAdapter(Context context, List<OfflineSong> song) {
		super();
		Song = song;
		this.context = context;
		memoryLruCache = new MemoryLruCache();

	}

	public class ViewHolder {
		public ImageView artwork;
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
					R.layout.artist_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tview = (TextView) convertView
					.findViewById(R.id.artist_item_txtView);
			viewHolder.artwork = (ImageView) convertView
					.findViewById(R.id.artist_item_imgView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.position = position;

		song = Song.get(position);
		if (Song.get(position).getAlbumArt() != null) {

			viewHolder.artwork.setImageBitmap(Song.get(position).getAlbumArt());
		} else {
			bitmap = memoryLruCache.getBitmapFromMemCache("default_artwork");
			if (bitmap == null) {
				bitmap = BitmapProcess.decodeSampledBitmapFromResource(
						context.getResources(), R.drawable.default_artwork,
						100, 100);
				memoryLruCache
						.addBitmapToMemoryCache("default_artwork", bitmap);
			}
			viewHolder.artwork.setImageBitmap(bitmap);
		}

		viewHolder.tview.setText(song.getArtist());

		return convertView;

	}
}
