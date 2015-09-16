package model;

/**
 * Created by John Osberg on 5/25/2015.
 */
public class StationDJ {

    private String name;
    private String bio;
    private int score;
    private int numActiveListeners;
    private SpotifyProfile spotifyProfile;
    private String djHash;

    public StationDJ(){}

    public SpotifyProfile getSpotifyProfile() {
        return spotifyProfile;
    }

    public int getNumActiveListeners() {
        return numActiveListeners;
    }

    public int getScore() {
        return score;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumActiveListeners(int numActiveListeners) {
        this.numActiveListeners = numActiveListeners;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSpotifyProfile(SpotifyProfile spotifyProfile) {
        this.spotifyProfile = spotifyProfile;
    }

    public String getDjHash() {
        return djHash;
    }

    public void setDjHash(String djHash) {
        this.djHash = djHash;
    }
}
