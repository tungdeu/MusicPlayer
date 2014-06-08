package com.tung.object;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class PlaylistWorker {

	private Context mContext;
    private String mFilePath;
    private String mPlaylistName;
	
	public PlaylistWorker(Context context)
	{
		mContext = context;
	}
	
    public void setContext(Context context)
    {
        mContext = context;
    }

	public void addSongToPlaylist(String playlistName, String filePath)
	{
//		mContext.sendBroadcast(
//				new Intent(
//						Intent.ACTION_MEDIA_MOUNTED,
//						Uri.parse("file://" + folder)));
//
        mFilePath = filePath;
        mPlaylistName = playlistName;

        new MediaScannerNotifier(mContext, filePath, "audio/*", this);
	}

    public void addSongToPlaylist() throws NullPointerException
    {
        int playlistId = getPlaylistId(mPlaylistName);

        if (playlistId == -1)
        {
            createPlaylist(mPlaylistName);
            playlistId = getPlaylistId(mPlaylistName);

            if (playlistId == -1)
            {
                Log.e("PlaylistWorker", "Playlist didn't created");
                throw new NullPointerException("Playlist didn't created");
            }
        }

        int audioId = getAudioId(mFilePath);

        if (audioId == -1)
        {
            Log.e("PlaylistWorker", "audioId not found");
            throw new NullPointerException("Audio file not found");
        }

        String playlistUri = "content://media/external/audio/playlists/" + playlistId + "/members";
        ContentResolver cr = mContext.getContentResolver();
        ContentValues cv = new ContentValues(3);

        Uri urr = Uri.parse(playlistUri);
        cv.put(MediaStore.Audio.Playlists.Members.PLAYLIST_ID, playlistId + "");
        cv.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioId + "");
        cv.put(MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER, "0");
        cv.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, "0");

        cr.insert(urr, cv);
    }

	int getPlaylistId(String playlistName)
	{
		Cursor c = query(mContext, MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
				new String[]
				{ MediaStore.Audio.Playlists._ID },
				MediaStore.Audio.Playlists.NAME + "=?", 
				new String[]
				{ playlistName }, 
				MediaStore.Audio.Playlists.NAME);
		int id = intFromCursor(c);
		return id;
	}

	int getAudioId(String filePath)
	{
		Cursor c = query(mContext, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[]
				{ MediaStore.Audio.Media._ID }, 
				MediaStore.Audio.Media.DATA + "=?",
				new String[] { filePath }, 
				MediaStore.Audio.Media.DATA);
		
		return intFromCursor(c);
	}

	int getLastAudioId(int playlistId)
	{
		Uri newSss = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId);
		
		Cursor c = query(
				mContext, 
				newSss,
				new String[] { MediaStore.Audio.Playlists.Members.AUDIO_ID },
				null, 
				null, 
				MediaStore.Audio.Playlists.Members.AUDIO_ID);
		
		int id = intFromCursor(c);
		return id;
	}
	
	public static Cursor query(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder,
			int limit)
	{
		try
		{
			ContentResolver resolver = context.getContentResolver();
			if (resolver == null)
			{
				return null;
			}
			if (limit > 0)
			{
				uri = uri.buildUpon().appendQueryParameter("limit", "" + limit)
						.build();
			}
			return resolver.query(uri, projection, selection, selectionArgs,
					sortOrder);
		} catch (UnsupportedOperationException ex)
		{
			return null;
		}
	}

	public static Cursor query(Context context, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder)
	{
		return query(context, uri, projection, selection, selectionArgs,
				sortOrder, 0);
	}

	private static int intFromCursor(Cursor c)
	{
		int id = -1;
		if (c != null)
		{
			c.moveToFirst();
			if (!c.isAfterLast())
			{
				id = c.getInt(0);
			}
		}

		c.close();
		return id;
	} 
	
	private void createPlaylist(String playlistName)
	{
        ContentResolver cr = mContext.getContentResolver();
        
        String playlistUri = "content://media/external/audio/playlists/";
        
        ContentValues cv = new ContentValues(1);

		Uri urr = Uri.parse(playlistUri);
		////cv.put(MediaStore.Audio.Playlists._ID, 1 + "");
		cv.put(MediaStore.Audio.Playlists.NAME, playlistName);
		
		cr.insert(urr, cv);
	}

    private class MediaScannerNotifier implements
            MediaScannerConnection.MediaScannerConnectionClient
    {
        private MediaScannerConnection mConnection;
        private String mPath;
        private String mMimeType;
        private PlaylistWorker mPlaylistWorker;

        public MediaScannerNotifier(
                Context context,
                String path,
                String mimeType,
                PlaylistWorker playlistWorker)
        {
            mPlaylistWorker = playlistWorker;
            mContext = context;
            mPath = path;
            mMimeType = mimeType;
            mConnection = new MediaScannerConnection(context, this);
            mConnection.connect();
        }

        public void onMediaScannerConnected()
        {
            mConnection.scanFile(mPath, mMimeType);
        }

        public void onScanCompleted(String path, Uri uri)
        {
            // OPTIONAL: scan is complete, this will cause the viewer to render it
            try
            {
                if (uri != null)
                {
                    mPlaylistWorker.addSongToPlaylist();
                }
            }
            finally
            {
                mConnection.disconnect();
                mContext = null;
            }
        }
    }
}
