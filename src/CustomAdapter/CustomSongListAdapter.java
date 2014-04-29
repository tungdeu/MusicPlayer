package CustomAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.object.LoadImage;

public class CustomSongListAdapter extends BaseAdapter implements Filterable {
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
				public void onClick(View v) {
					if (viewHolder.toolbar.getVisibility() == View.GONE)
						viewHolder.toolbar.setVisibility(View.VISIBLE);
					else
						viewHolder.toolbar.setVisibility(View.GONE);
					// CustomAnimation expandAni = new
					// CustomAnimation(viewHolder.toolbar, 500);
					// viewHolder.toolbar.startAnimation(expandAni);
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// viewHolder.position = position;
		song = Song.get(position);

		viewHolder.tview.setText(song.getTitle());
		return convertView;

	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return new CustomFilter();
	}

	public class CustomFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			List<OfflineSong> tempList = new ArrayList<OfflineSong>();
			// constraint is the result from text you want to filter against.
			// objects is your data set you will filter from
			if (constraint != null && constraint.length() != 0) {
				int length = Song.size();
				int i = 0;
				Locale locale = new Locale("vi_VN");
				constraint = constraint.toString().toLowerCase(locale);
				while (i < length) {
					OfflineSong item = Song.get(i);
					if ((item.getTitle().toLowerCase(locale))
							.contains(constraint))
						tempList.add(item);
					i++;
				}
				// following two lines is very important
				// as publish result can only take FilterResults objects
				filterResults.values = tempList;
				filterResults.count = tempList.size();
			}
			// else
			// {
			// filterResults.values = OriginalSongs;
			// filterResults.count = OriginalSongs.size();
			// }
			return filterResults;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			setSource((List<OfflineSong>) results.values);
			if (Song != null)
				Collections.sort(Song,
						new SearchResultComparator(constraint.toString()));
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
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
	
	public class SearchResultComparator implements Comparator<OfflineSong> {
		String query;
		Locale vietnam;

		public SearchResultComparator(String query) {
			vietnam = new Locale("vi_VN");
			this.query = query.toLowerCase(vietnam);
		}

		@Override
		public int compare(OfflineSong o1, OfflineSong o2) {
			String title1, title2;
			title1 = o1.getTitle().toLowerCase(vietnam);
			title2 = o2.getTitle().toLowerCase(vietnam);
			if (title1.indexOf(query) == title2.indexOf(query))
				return (title1.length() < title2.length()) ? -1 : 1;
			return (title1.indexOf(query) < title2.indexOf(query)) ? -1 : 1;
		}
	}
}
