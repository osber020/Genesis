package genesis.oldtestament.genesis.GenesisToolkit;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by John Osberg on 5/26/2015.
 */
public class JSONParser {

    //TODO: Figure out how to toast in this class, or throw exceptions
    /**
     *
     * @param djJsonStr the returned JSON from the DB
     * @return An array of DJ handle strings
     * @throws JSONException if the server returns no DJs; it which case it returns an array of one empty element
     */
    public String[] getDJsFromJson(String djJsonStr)
            throws JSONException{
        try {
            final String OWM_LIST = "DJs";
            final String OWM_HANDLE = "handle";

            JSONObject djJson = new JSONObject(djJsonStr);
            JSONArray djsArray = djJson.getJSONArray(OWM_LIST);

            String[] resultStrs = new String[djsArray.length()];
            for (int i = 0; i < djsArray.length(); i++) {
                JSONObject DJ = djsArray.getJSONObject(i);
                String DJ_Handle = DJ.getString(OWM_HANDLE);
                resultStrs[i] = DJ_Handle;
            }

            return resultStrs;
        }
        catch (JSONException j){
            String[] badResult = new String[1];
            badResult[0] = "";
            throw j;
        }
    }

    /**
     *
     * @param djJsonStr the returned JSON from the DB
     * @return An array of DJ handle strings
     * @throws JSONException if the server returns no DJs; it which case it returns an array of one empty element
     */
    public String[] getNumListenersFromJson(String djJsonStr)
            throws JSONException{
        try {
            final String OWM_LIST = "DJs";
            final String OWM_NUM_LISTENERS = "numListeners";

            JSONObject djJson = new JSONObject(djJsonStr);
            JSONArray djsArray = djJson.getJSONArray(OWM_LIST);

            String[] resultStrs = new String[djsArray.length()];
            for (int i = 0; i < djsArray.length(); i++) {
                JSONObject DJ = djsArray.getJSONObject(i);
                String DJ_Handle = DJ.getString(OWM_NUM_LISTENERS);
                resultStrs[i] = DJ_Handle;
            }

            return resultStrs;
        }
        catch (JSONException j){
            String[] badResult = new String[1];
            badResult[0] = "";
            throw j;
        }
    }

    /**
     *
     * @param djJsonStr the returned JSON from the DB
     * @return An array of song hash strings for corresponding DJs strings
     * @throws JSONException if the server returns no DJs; it which case it returns an array of one empty element
     */
    public String[] getSongFromDJ(String djJsonStr)
            throws JSONException{
        try {
            final String OWM_LIST = "DJs";
            final String OWM_SONG_HASH = "songHash";

            JSONObject djJson = new JSONObject(djJsonStr);
            JSONArray djsArray = djJson.getJSONArray(OWM_LIST);

            String[] resultStrs = new String[djsArray.length()];
            for (int i = 0; i < djsArray.length(); i++) {
                JSONObject DJ = djsArray.getJSONObject(i);
                String songHash = DJ.getString(OWM_SONG_HASH);
                resultStrs[i] = songHash;
            }

            return resultStrs;
        }
        catch (JSONException j){
            throw j;
        }
    }
}
