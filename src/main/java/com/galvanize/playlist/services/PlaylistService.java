package com.galvanize.playlist.services;

import com.galvanize.playlist.model.Playlist;
import com.galvanize.playlist.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {
    private PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist getPlaylistById(Long id) {
       return playlistRepository.findById(id).get();
    }

    public String createPlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
        return "Playlist was created successfully.";
    }
}
