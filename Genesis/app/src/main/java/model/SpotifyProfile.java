package model;

/**
 * Created by John Osberg on 5/25/2015.
 */
public class SpotifyProfile {

    private String responseAccessToken;
    private String clientID;

    public SpotifyProfile(){}

    public SpotifyProfile(String ID){
        this.clientID = ID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setResponseAccessToken(String responseAccessToken) {
        this.responseAccessToken = responseAccessToken;
    }

    public String getResponseAccessToken() {
        return responseAccessToken;
    }
}
