package CustomAdapter;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tung.Entities.PlayListE;
import com.tung.musicplayer.R;
import com.tung.screen.PlayListDetail;

public class CustomPlayListAdapter extends BaseAdapter {

	List<PlayListE> Playlist;
	Context context;
	PlayListE plist;
	boolean isEdit;

	public CustomPlayListAdapter(Context context, List<PlayListE> playlist, boolean isEdit) {
		super();
		Playlist = playlist;
		this.context = context;
		this.isEdit=isEdit;

	}
	public CustomPlayListAdapter(Context context, List<PlayListE> playlist) {
		super();
		Playlist = playlist;
		this.context = context;


	}

	public class ViewHolder {
		public TextView tview;
		public ImageView imgView;
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
					.findViewById(R.id.playlist_item_textView);
			
			viewHolder.imgView = (ImageView)arg1.findViewById(R.id.playlist_item_btndel);
			if(isEdit){
				viewHolder.imgView.setVisibility(View.VISIBLE);
			}
			arg1.setTag(viewHolder);
			viewHolder.imgView.setTag(arg0);
			
			viewHolder.imgView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(final View v) {
					// TODO Auto-generated method stub

					AlertDialog.Builder dialogBuilder = new Builder(context);
					//dialogBuilder.setTitle("Are you sure to delete");
					dialogBuilder.setMessage("Are you sure to delete ?");
					dialogBuilder.setCancelable(false);
					dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
					dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							int pos = (Integer) v.getTag();
							long playListID = Playlist.get(pos).getPlayListId();
							ContentResolver resolver = context.getContentResolver();
							resolver.delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, MediaStore.Audio.Playlists._ID + "= "+playListID, null);
							
							Playlist.remove(pos);
							notifyDataSetChanged();
							dialog.dismiss();
						}
					});
					AlertDialog dialog1 = dialogBuilder.create();
					dialog1.show();
				}
			});
			
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
