package genesis.oldtestament.genesis;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import constants.bundleKeys;
import model.SpotifyProfile;
import model.Track;


public class MainActivity extends ActionBarActivity implements PlayerNotificationCallback, ConnectionStateCallback {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private static final String CLIENT_ID = "e74eee05a6f54e71b62defe1f485194e";
    private static final String REDIRECT_URI = "http://192.168.10.147/scripts/drop_dj.php"; //What does this do?

    // Request code that will be passed together with authentication result to the onAuthenticationResult callback
    // Can be any integer
    private static final int REQUEST_CODE = 1337;


    public AuthenticationRequest request = null;
    public Location loc;
    private SpotifyProfile spotifyProfile = new SpotifyProfile(CLIENT_ID);
    String log_tag = "5-25 DB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// TODO: Extract much of these calls to different places, this is called very often and is relatively resource intensive
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        request = builder.build();
        
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        Log.d(log_tag, "ON CREATE");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) { //TODO: Clean these internal calls to separate methods, maybe from a class?
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            String responseType = response.getType().toString();
            String responseAccessToken = response.getAccessToken();
            spotifyProfile.setResponseAccessToken(responseAccessToken);
            String Latitude = Double.toString(loc.getLatitude());
            String Longitude = Double.toString(loc.getLongitude());
            Bundle args = new Bundle();
            args.putString(String.valueOf(bundleKeys.RESPONSE_TYPE), responseType);
            args.putString(String.valueOf(bundleKeys.RESPONSE_ACCESS_TOKEN), spotifyProfile.getResponseAccessToken());
            args.putString(String.valueOf(bundleKeys.CLIENT_ID),spotifyProfile.getClientID());
            args.putString(String.valueOf(bundleKeys.LATITUDE), Latitude);
            args.putString(String.valueOf(bundleKeys.LONGITUDE), Longitude);
            DJListView theActivity = new DJListView();
            theActivity.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, theActivity)
                    .commit();
        }
        Log.d(log_tag, "ON activity result");
    }

    @Override
    public void onLoggedIn() {
        Log.d(log_tag, "ON logged in");
    }

    @Override
    public void onLoggedOut() {

        Log.d(log_tag, "ON logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d(log_tag, "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d(log_tag, "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d(log_tag, "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d(log_tag, "Playback event received: " + eventType.name());
        switch (eventType) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d(log_tag, "Playback error received: " + errorType.name());
        switch (errorType) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Log.v("Ref Count: ", "" + Spotify.getReferenceCount());
        Log.d(log_tag, "DESTROY ME");
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}

