package interfaces;


import java.util.Queue;

import model.Station;
import model.Track;
import model.User;

/**
 * Created by John Osberg on 5/26/2015.
 * This is the interface for the DJ side of the app. It is to update the database based on the DJ's action.
 * It updates the database with the following information:
 *      -The current track playing
 *      -The next track in the queue (Builds Queue for DJ)
 *      -The current time in the track (as accurate as spotify allows for)
 *
 */
public interface StationDatabaseIntf {

    /**+
     * Updates the current track playing for the DJ
     * Called when DJ presses skip, voids the queue (plays song on their own),
     * @param track
     */
    public void updateCurrentTrack(Track track);

    /**+
     * Adds track to the queue, upon DJ doing so. Queue is updated in the DB for
     * listeners to grab from
     * @param track
     */
    public void addTrackToQueue(Track track);

    /**+
     * Removes a given track from queue if DJ decids so
     * @param track
     */
    public void removeTrackFromQueue(Track track);

    /**+
     * Updates the queue order if DJ changes it.
     * @param newQueue the queue with the new order or removed tracks.
     */
    public void updateQueueOrder(Queue newQueue);

    /**+
     * If the DJ fast forwards or the new time needs updating. Needs frequent calling
     * @param time
     */
    public void updateCurrentTrackTime(int time);//TODO: Decide how to implement time. Is time even real?

    /**+
     * Updates the next track to be played. Bumps track to the front of the queue
     * @param track
     */
    public void updateNextTrack(Track track);

    /**+
     * Should be called when the user taps into the station and starts listening to update the count
     * @param user
     * @param station
     */
    public void addListenerToStation(User user, Station station);
}
