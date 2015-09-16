package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John Osberg on 5/25/2015.
 */
public class Station {

    private StationDJ dj;
    private Track currentTrack;
    private Track nextTrack;
    private List<Track> backlog = new ArrayList<>();
    private double currentPosition;
    private double durationOfCurrentTrack;
    private String stationHash;

    Station(){}

    public double getCurrentPosition() {
        return currentPosition;
    }

    public double getDurationOfCurrentTrack() {
        return durationOfCurrentTrack;
    }

    public StationDJ getDj() {
        return dj;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public Track getNextTrack() {
        return nextTrack;
    }

    public List<Track> getBacklog() {
        return backlog;
    }

    public void setBacklog(List<Track> backlog) {
        this.backlog = backlog;
    }

    public void setCurrentPosition(double currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }

    public void setDj(StationDJ dj) {
        this.dj = dj;
    }

    public void setDurationOfCurrentTrack(double durationOfCurrentTrack) {
        this.durationOfCurrentTrack = durationOfCurrentTrack;
    }

    public void setNextTrack(Track nextTrack) {
        this.nextTrack = nextTrack;
    }

    public void addToBackLog(Track track){
        backlog.add(track);
    }

    public String getStationHash() {
        return stationHash;
    }

    public void setStationHash(String stationHash) {
        this.stationHash = stationHash;
    }
}
