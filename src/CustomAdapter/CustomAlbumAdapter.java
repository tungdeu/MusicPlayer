package CustomAdapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;

import com.tung.object.BitmapProcess;
import com.tung.object.MemoryLruCache;

public class CustomAlbumAdapter extends BaseAdapter {

	List<OfflineSong> Album;
	Context context;
	OfflineSong album;
	MemoryLruCache memoryLruCache;
	Bitmap bitmap;

	public CustomAlbumAdapter(Context context, List<OfflineSong> album) {
		super();
		Album = album;
		this.context = context;
		memoryLruCache = new MemoryLruCache();
	}

	public class ViewHolder {
		public int position;
		public ImageView cover;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Album.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.album_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.cover = (ImageView) convertView
					.findViewById(R.id.album_item_cover);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.position = position;

		album = Album.get(position);
		if (Album.get(position).getAlbumArt() != null) {

			viewHolder.cover.setImageBitmap(Album.get(position).getAlbumArt());
		} else {
			bitmap = memoryLruCache.getBitmapFromMemCache("default_artwork");
			if (bitmap == null) {
				bitmap = BitmapProcess.decodeSampledBitmapFromResource(
						context.getResources(), R.drawable.default_artwork,
						100, 100);
				memoryLruCache
						.addBitmapToMemoryCache("default_artwork", bitmap);
			}
			viewHolder.cover.setImageBitmap(bitmap);
		}
		return convertView;
	}
}
