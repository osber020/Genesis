package model;

/**
 * Created by John Osberg on 5/25/2015.
 */
public class User {

    private String bio;
    private String userHash;
    private SpotifyProfile spotifyProfile;
    private int score;
    private Station currentStation;
    private Role currentRole;
    private String userName;

    public User(){}

    public void setBio(String bio){
        this.bio = bio;
    }

    public void setUserHash(String userHash){
        this.userHash = userHash;
    }

    public void setSpotifyProfile(SpotifyProfile spotifyProfile){
        this.spotifyProfile = spotifyProfile;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setCurrentStation(Station station){
        this.currentStation = station;
    }

    public void setCurrentRole(Role role){
        this.currentRole = role;
    }

    public String getBio(){
        return bio;
    }

    public String getUserHash(){
        return userHash;
    }

    public int getScore(){
        return  score;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public Role getCurrentRole() {
        return currentRole;
    }

    public SpotifyProfile getSpotifyProfile() {
        return spotifyProfile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
