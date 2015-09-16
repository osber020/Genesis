package genesis.oldtestament.genesis;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.bundleKeys;
import genesis.oldtestament.genesis.GenesisToolkit.JSONParser;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by John Osberg on 3/16/2015.
 *
 */

public class DJListView extends Fragment {

    private ArrayAdapter<String> mDJListAdapter;
    private ArrayAdapter<String> mDJListenersAdapter;
    private SimpleAdapter adapter;
    private List<Map<String,String>> djListenerList;
    SpotifyApi api = new SpotifyApi();
    public DJListView() {

    }
    public Toast networkErrorToast;
    public Toast emptyJSONToast;

    private String[] songHashes = null;
    private String[] songTitles = null;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Context context = getActivity().getApplicationContext();
        String networkConnectivityText = "Unable to refresh feed, check network connectivity";
        String emptyJSONText = "There are currnetly no DJs in your area, try broadcasting instead";
        int duration = Toast.LENGTH_LONG;
        networkErrorToast = Toast.makeText(context,networkConnectivityText,duration);
        emptyJSONToast = Toast.makeText(context,emptyJSONText,duration);

        String responseAccessToken = (String)getArguments().get(String.valueOf(bundleKeys.RESPONSE_ACCESS_TOKEN));
        api.setAccessToken(responseAccessToken);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.live_djs_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        String LOG_TAG = "BUTTON_SELECTED:";
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                return true;
            case R.id.action_logout:
                Log.d(LOG_TAG, "Logout button selected");
                AuthenticationClient.logout(getActivity().getApplicationContext());
                super.onCreate(null);//TODO: This case successfully logs out, but offers no login prompt again. Need to essentially restart app
                djListenerList.clear();
                adapter.notifyDataSetChanged();
            default:
                Log.d(LOG_TAG, "this is the default case for buttons");
                return true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        djListenerList = new ArrayList<>();
        FetchLiveDJsTask DJTask = new FetchLiveDJsTask();
        DJTask.execute();
        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        adapter = new SimpleAdapter(getActivity(), djListenerList, R.layout.list_item_djs, new String[] { "DJ", "listeners", "Song" }, new int[] { R.id.list_item_djs_textview, R.id.numListeners, R.id.currentTrack });
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_djs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = adapter.getItem(position).toString();
                String songTitle = songTitles[position];
                String songHash = songHashes[position];
                Intent intent = new Intent(getActivity(), listenerPlayerView.class).putExtra(Intent.EXTRA_TEXT, text).putExtra(String.valueOf(bundleKeys.SPOTIFY_TOKENS), getArguments()).putExtra(String.valueOf(bundleKeys.TRACK_NAME), songTitle).putExtra(String.valueOf(bundleKeys.SONG_HASH), songHash);
                startActivity(intent);
            }
        });

        return rootView;
    }




    /**
     * FetchLiveDJsTask performs the background operation, doInBackGround.
     * */
    public class FetchLiveDJsTask extends AsyncTask<Void, Void, Map<String, String[]>> {

        private final String LOG_TAG = FetchLiveDJsTask.class.getSimpleName();
        private final String baseURL = "http://76.17.206.227/scripts/"; //TODO: Update this to live URL in the cloud


        @Override
        protected Map<String, String[]> doInBackground(Void... params) {
            //Define the URL for this request and create HTTP Client and Post Header
            String scriptURL = "get_dj_for_listener.php"; //Params: xLocation,zLocation,limit,offset
            String _URL = baseURL + scriptURL;
            Log.v(LOG_TAG, "The URL: " + _URL);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(_URL);
            String json;
            Map<String, String[]> result = new HashMap<>();
            try{
                //Build the list of parameters to pass in the POST request
                List<NameValuePair> parameters = new ArrayList<>(4);
                Bundle args = getArguments();
                parameters.add(new BasicNameValuePair("xlocation", args.getString(String.valueOf(bundleKeys.LATITUDE))));
                parameters.add(new BasicNameValuePair("zlocation", args.getString(String.valueOf(bundleKeys.LONGITUDE))));
                parameters.add(new BasicNameValuePair("limit", "20"));
                parameters.add(new BasicNameValuePair("offset", "0"));
                httppost.setEntity(new UrlEncodedFormEntity(parameters));

                //Execute the post request
                HttpResponse response = httpClient.execute(httppost);
                json = EntityUtils.toString(response.getEntity());
            }
            catch (ClientProtocolException e){
                //Do something with it, log?
                Log.e(LOG_TAG, e.getMessage());
                networkErrorToast.show();
                return null;
            }
            catch (IOException e){
                Log.e(LOG_TAG, "Here's the message ", e);
                networkErrorToast.show();
                return null;
            }
                JSONParser theParser = new JSONParser();
                String[] parsedDJSJSON,parsedListenersJSON;
                try{
                    parsedDJSJSON = theParser.getDJsFromJson(json);
                    parsedListenersJSON = theParser.getNumListenersFromJson(json);
                    songHashes = theParser.getSongFromDJ(json);
                    songTitles = new String[songHashes.length];
                    for (int i = 0; i <songHashes.length; i++) {
                        songTitles[i] = getSongFromHash(songHashes[i]);
                    }
                    result.put(String.valueOf(bundleKeys.LIST_DJ), parsedDJSJSON);
                    result.put(String.valueOf(bundleKeys.LIST_NUM_LISTENERS), parsedListenersJSON);
                    result.put(String.valueOf(bundleKeys.LIST_PLAYINGTRACKS),songTitles);
                }
                catch (JSONException j){
                    emptyJSONToast.show();
                    return null;
                }
            return result;
        }

        /*
        * This method is run on the UI thread after doInBackground returns* */
        @Override
        protected void onPostExecute(Map<String, String[]> result){
            adapter.notifyDataSetChanged();
            if (result != null){
                String[] DJs = result.get(String.valueOf(bundleKeys.LIST_DJ));
                String[]numListeners = result.get(String.valueOf(bundleKeys.LIST_NUM_LISTENERS));
                String[]playingTrack = result.get(String.valueOf(bundleKeys.LIST_PLAYINGTRACKS));
                Map<String, String> djListenerMap;
                for (int i = 0; i < DJs.length; i++) {
                    djListenerMap = new HashMap<>();
                    djListenerMap.put("DJ", DJs[i]);
                    djListenerMap.put("listeners",numListeners[i]);
                    djListenerMap.put("Song", playingTrack[i]);
                    djListenerList.add(djListenerMap);
                }
            }

        }

    }

    private String getSongFromHash(String hash){
        SpotifyService spotify = api.getService();
        return spotify.getTrack(hash).name;
    }

}

