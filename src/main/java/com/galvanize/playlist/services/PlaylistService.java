package com.galvanize.playlist.services;

import com.galvanize.playlist.model.Playlist;
import com.galvanize.playlist.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
        List<Playlist> list = playlistRepository.findAll();

            for (Playlist pl : list) {
                if (pl.getPlaylistName() != null) {
                    if (pl.getPlaylistName().equals(playlist.getPlaylistName())) {
                        return "Playlist name already exists. Please use another name.";
                    }
                }
        }
        if (playlist.getPlaylistName() == null) {
            return "Playlist name requried.";
        }
        playlistRepository.save(playlist);
        return "Playlist was created successfully.";
    }

    public void addNewSong(Playlist playlist, String nameOfTheSong) {
        Playlist playlistFounded = playlistRepository.findById(playlist.getId()).get();
        playlistFounded.addSong(nameOfTheSong);
        playlistRepository.save(playlistFounded);
    }
}
