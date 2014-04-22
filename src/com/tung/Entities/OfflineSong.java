package com.tung.Entities;

import android.graphics.Bitmap;

public class OfflineSong {
	private String path;
	private String title;
	private String artist;
	private String album;
	private String lyric;
	private Bitmap artwork;
	
	public Bitmap getAlbumArt() {
		return artwork;
	}

	public void setAlbumArt(Bitmap albumArt) {
		this.artwork = albumArt;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getLyric(){
		return lyric;
	}
	
	public void setLyric(String lyric){
		this.lyric= lyric;
	}
}
