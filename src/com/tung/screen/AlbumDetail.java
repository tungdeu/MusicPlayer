package com.tung.screen;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import CustomAdapter.CustomSongListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.object.BitmapProcess;

public class AlbumDetail extends Activity {

	public ImageView img;
	public TextView txtViewArtist;
	public TextView txtViewAlbum;
	public ArrayList<OfflineSong> Songs;
	public Intent intentPlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_detail);

		Intent intent = getIntent();
		String artist = intent.getStringExtra("artist");
		String album = intent.getStringExtra("album");
		String path = intent.getStringExtra("path");

		txtViewAlbum = (TextView) findViewById(R.id.album_detail_txtViewAlbum);
		txtViewAlbum.setText(album);

		txtViewArtist = (TextView) findViewById(R.id.album_detail_txtViewArtist);
		txtViewArtist.setText(artist);

		img = (ImageView) findViewById(R.id.album_detail_imgView);
		MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
		metaRetriver.setDataSource(path);
		byte[] bitmap = metaRetriver.getEmbeddedPicture();
		if (bitmap != null)
			img.setImageBitmap(BitmapProcess.decodeSampledBitmap(bitmap, 500,
					500));
		else
			img.setImageResource(R.drawable.default_artwork);

		Cursor cursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				MediaStore.Audio.Media.ALBUM + " ='" + album + "'", null,
				"LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
		Songs = new ArrayList<OfflineSong>();
		String tmp_ext = "";
		String tmp = "";
		String extension = "mp3";

		cursor.moveToFirst();
		do {
			tmp = cursor
					.getString(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
			tmp_ext = tmp.substring(tmp.length() - 3);
			tmp = tmp.substring(0, tmp.length() - 4);
			if (tmp_ext.compareTo(extension) == 0
					&& !cursor
							.getString(
									cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
							.contains("Ringtone")) {

				OfflineSong song = new OfflineSong();

				song.setAlbum(album);
				song.setPath(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));

				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.MediaColumns.TITLE));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
				if (tmp.isEmpty())
					tmp = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
				song.setTitle(tmp);
				Songs.add(song);
			}
		} while (cursor.moveToNext());
		Collections.sort(Songs, new TitleComparator());
		ListView lst = (ListView) findViewById(R.id.album_detail_lstView);
		CustomSongListAdapter adapter = new CustomSongListAdapter(this, Songs);
		lst.setAdapter(adapter);

		intentPlay = new Intent(this, PlaySong.class);
		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String path = Songs.get(arg2).getPath();
				String album = Songs.get(arg2).getAlbum();
				intentPlay.putExtra("album", album);
				intentPlay.putExtra("path", path);
				intentPlay.putExtra("flag", 3);
				startActivity(intentPlay);

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.album_detail, menu);
		return true;
	}

	public class TitleComparator implements Comparator<OfflineSong> {
		@Override
		public int compare(OfflineSong o1, OfflineSong o2) {
			Locale vietnam = new Locale("vi_VN");
			Collator vietnamCollator = Collator.getInstance(vietnam);
			return vietnamCollator.compare(o1.getTitle(), o2.getTitle());
		}
	}

}
