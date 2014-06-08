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
import com.tung.object.CreateList;

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
		String[] projection = { MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.ARTIST, 
				MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.TITLE 
				};
		Cursor cursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
				MediaStore.Audio.Media.ALBUM + " ='" + album + "'", null,
				"LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
		Songs = new ArrayList<OfflineSong>();

		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			do {
				OfflineSong song = new OfflineSong();
				song.setPath(cursor.getString(0));
				song.setArtist(cursor.getString(1));
				song.setAlbum(cursor.getString(2));
				song.setTitle(cursor.getString(3));

				Songs.add(song);

			} while (cursor.moveToNext());
		}
			Collections.sort(Songs, new TitleComparator());
			CreateList.getInstance().setSongList(Songs);
			ListView lst = (ListView) findViewById(R.id.album_detail_lstView);
			CustomSongListAdapter adapter = new CustomSongListAdapter(this,
					Songs);
			lst.setAdapter(adapter);

			intentPlay = new Intent(this, PlaySong.class);
			lst.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					String path = Songs.get(arg2).getPath();
					String album = Songs.get(arg2).getAlbum();
					int audioID = arg2;
					intentPlay.putExtra("id", audioID);
					intentPlay.putExtra("album", album);
					intentPlay.putExtra("path", path);
					//intentPlay.putExtra("flag", 1);
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
