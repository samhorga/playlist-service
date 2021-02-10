package com.galvanize.playlist.controllers;

import com.galvanize.playlist.model.Playlist;
import com.galvanize.playlist.services.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.createPlaylist(playlist);
    }

    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable Long id) {
        return playlistService.getPlaylistById(id);
    }

    @PutMapping("/{nameOfTheSong}")
    public void addNewSong(@RequestBody Playlist playlist, @PathVariable String nameOfTheSong) {
         playlistService.addNewSong(playlist, nameOfTheSong);
    }
}
