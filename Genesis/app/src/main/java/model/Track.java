package model;

import android.os.Parcelable;

/**
 * Created by John Osberg on 5/25/2015.
 */
public class Track {

    private String trackName;
    private String albumName;
    private String artistName;
    private double trackLength;

    public Track(){}



    public Track(String trackName){
        this.trackName = trackName;
    }

    public void setTrackName(String trackName){
        this.trackName = trackName;
    }

    public void setAlbumName(String albumName){
        this.albumName = albumName;
    }

    public void setArtistName(String artistName){
        this.artistName = artistName;
    }

    public void setTrackLength(double length){
        this.trackLength = length;
    }

    public String getTrackName(){
        return trackName;
    }

    public String getAlbumName(){
        return albumName;
    }

    public String getArtistName(){
        return artistName;
    }

    public double getTrackLength(){
        return trackLength;
    }
}
