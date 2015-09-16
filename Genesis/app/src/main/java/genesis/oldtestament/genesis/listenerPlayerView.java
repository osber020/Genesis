package genesis.oldtestament.genesis;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import constants.bundleKeys;

public class listenerPlayerView extends ActionBarActivity {
    
    private static Intent intent;
    private final static String TAG = "PLAYER_TAG";
    static private Player mPlayer;
    static listenerPlayerView.PlaySpotifySong spotifyPlaySong;
    private static String trackName;
    private static String nowPlayingHash;
    private static boolean hasTrackInfo = false;
    private static boolean updateCurrentSong = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener_stream_view_player);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            spotifyPlaySong = new listenerPlayerView.PlaySpotifySong();
        }
        Log.d(TAG, "onCreate");
    }
    // 5/25 If I recall correctly, I used many of these to learn the android activity lifecycle, these logs are quite useful in the debugger going from frame to frame
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onStop(){
        super.onStop();
        hasTrackInfo = false;
        trackName = "";
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Spotify.destroyPlayer(this);
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }





    ConnectionStateCallback playerCallback = new ConnectionStateCallback() {
        @Override
        public void onLoggedIn() {
            Log.d("MainActivity", "User logged in");
        }

        @Override
        public void onLoggedOut() {
            Log.d("MainActivity", "User logged out");
        }

        @Override
        public void onLoginFailed(Throwable error) {
            Log.d("MainActivity", "Login failed");
        }

        @Override
        public void onTemporaryError() {
            Log.d("MainActivity", "Temporary error occurred");
        }

        @Override
        public void onConnectionMessage(String message) {
            Log.d("MainActivity", "Received connection message: " + message);
        }

    };

    PlayerNotificationCallback notificationCallback = new PlayerNotificationCallback() {
        @Override
        public void onPlaybackEvent(PlayerNotificationCallback.EventType eventType, PlayerState playerState) {
            Log.d("MainActivity", "Playback event received: " + eventType.name());
            switch (eventType) {
                // Handle event type as necessary
                default:
                    break;
            }
        }

        @Override
        public void onPlaybackError(ErrorType errorType, String errorDetails) {
            Log.d("MainActivity", "Playback error received: " + errorType.name());
            switch (errorType) {
                // Handle error type as necessary
                default:
                    break;
            }
        }
    };
            
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listener_stream_view_player, container, false);
            intent = getActivity().getIntent();
            spotifyPlaySong.execute();
            while(!hasTrackInfo){}//Pause before populating Text TODO: Add timeout
            ((TextView) rootView.findViewById(R.id.listener_view_text)).setText("Track playing: " + trackName);

            return rootView;
        }
    }

    public class PlaySpotifySong extends AsyncTask<Void,Void,Void> {



        @Override
        protected Void doInBackground(Void... params){

            Bundle args = intent.getBundleExtra(String.valueOf(bundleKeys.SPOTIFY_TOKENS));
            final String newSongHash = intent.getStringExtra(String.valueOf(bundleKeys.SONG_HASH));
            if (!newSongHash.equals(nowPlayingHash)){
                nowPlayingHash = newSongHash;
                updateCurrentSong = true;
            }
            else{
                updateCurrentSong = false;
            }
            Log.v("SONG HASH PLAYING:", nowPlayingHash);
            String responseType = (String)args.get(String.valueOf(bundleKeys.RESPONSE_TYPE));
            String responseAccessToken = (String)args.get(String.valueOf(bundleKeys.RESPONSE_ACCESS_TOKEN));
            String CLIENT_ID = (String)args.get(String.valueOf(bundleKeys.CLIENT_ID));
            trackName = intent.getStringExtra(String.valueOf(bundleKeys.TRACK_NAME));
            hasTrackInfo = true;
            Context currentContext = listenerPlayerView.this.getApplicationContext();
            if (updateCurrentSong) {
                if (responseType.equals(AuthenticationResponse.Type.TOKEN.toString())) {
                    Config playerConfig = new Config(currentContext, responseAccessToken, CLIENT_ID);
                    mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                        @Override
                        public void onInitialized(Player player) {
                        /*These are the series of calls needed to make a song play via spotify*/
                            mPlayer.addConnectionStateCallback(playerCallback);
                            mPlayer.addPlayerNotificationCallback(notificationCallback);
                            mPlayer.play("spotify:track:" + nowPlayingHash);
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                        }
                    });
                }
            }
            return null;
        }

    }


}
