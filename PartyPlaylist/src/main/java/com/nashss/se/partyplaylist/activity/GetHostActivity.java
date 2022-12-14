package com.nashss.se.partyplaylist.activity;

import com.nashss.se.partyplaylist.activity.requests.GetHostRequest;
import com.nashss.se.partyplaylist.activity.results.GetHostResult;
import com.nashss.se.partyplaylist.dynamodb.PlaylistDao;
import com.nashss.se.partyplaylist.dynamodb.models.Playlist;
import com.nashss.se.partyplaylist.exceptions.HostNotFoundException;
import com.nashss.se.partyplaylist.models.UserModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetGuestActivity for the PartyPlaylist GetGuest API.
 *
 * This API allows the host to get one of the guests at the party.
 */
public class GetHostActivity {

    private final Logger log = LogManager.getLogger();

    private final PlaylistDao playlistDao;

    /**
     * Instantiates a new GetHostActivity object.
     *
     * @param playlistDao to access the playlist table.
     */
    @Inject
    public GetHostActivity(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    //CHECKSTYLE:OFF:LeftCurly
    /**
     * This method handles the incoming request by retrieving the host from the database.
     * <p>
     * It then returns the host.
     * <p>
     * If the host does not exist, this should throw a UserNotFoundException.
     *
     * @param getHostRequest request object containing the userId
     * @return getHostResult result object containing the API defined {@link UserModel }
     */
    public GetHostResult handleRequest(final GetHostRequest getHostRequest) {
        log.info("Received GetHostRequest {}", getHostRequest);

        String playlistName = getHostRequest.getPlaylistName();
        String hostName = getHostRequest.getHostName();
        String hostNameWithoutSpace = hostName.replaceAll("\\s+","");

        Playlist playlist = playlistDao.getPlaylistWithPlaylistName(playlistName);

        String playlistHostNameWithoutSpace = playlist.getHost().replaceAll("\\s+","");

        if (!hostNameWithoutSpace.equalsIgnoreCase(playlistHostNameWithoutSpace)) {
            throw new HostNotFoundException(
                    String.format("Cannot find host associated with '%s'. Please try again.", playlistName));
        }

        return GetHostResult.builder()
                .withPlaylistId(playlist.getPlaylistId())
                .build();
    }
}
