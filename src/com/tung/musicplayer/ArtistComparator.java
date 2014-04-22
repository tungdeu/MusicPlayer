package com.tung.musicplayer;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.tung.Entities.OfflineSong;

public class ArtistComparator implements Comparator<OfflineSong> {
	@Override
	public int compare(OfflineSong o1, OfflineSong o2) {
		Locale vietnam = new Locale("vi_VN");
		Collator vietnamCollator = Collator.getInstance(vietnam);
		return vietnamCollator.compare(o1.getArtist(), o2.getArtist());
	}
}