package com.galvanize.playlist.services;

import com.galvanize.playlist.model.Playlist;
import com.galvanize.playlist.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTests {

    @Mock
    PlaylistRepository playlistRepository;

    @InjectMocks
    private PlaylistService playlistService;

    @Test
    public void createEmptyPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Playlist 2");
        playlist.setId(1L);

        when(playlistRepository.save(any())).thenReturn(playlist);
        when(playlistRepository.findAll()).thenReturn(Collections.emptyList());
        String actualString = playlistService.createPlaylist(playlist);
        verify(playlistRepository).save(playlist);

        when(playlistRepository.findById(any())).thenReturn(Optional.of(playlist));
        Playlist playlistRetrieved = playlistService.getPlaylistById(1L);
        verify(playlistRepository).findById(any());

        assertTrue(playlistRetrieved.getSongList().isEmpty());
        assertEquals("Playlist was created successfully.", actualString);
    }

    @Test
    public void existingPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Playlist 1");
        playlist.setId(1L);
        playlistService.createPlaylist(playlist);
        when(playlistRepository.findAll()).thenReturn(Collections.singletonList(playlist));
        verify(playlistRepository).findAll();
        String actualString = playlistService.createPlaylist(playlist);
        assertEquals("Playlist name already exists. Please use another name.", actualString);
    }

    @Test
    public void emptyPlaylistName() {
        Playlist playlist = new Playlist();
        when(playlistRepository.findAll()).thenReturn(Collections.singletonList(playlist));
        String actualString = playlistService.createPlaylist(playlist);
        verify(playlistRepository).findAll();
        assertEquals("Playlist name requried.", actualString);
    }

    @Test
    public void addSongToPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Playlist 2");
        playlist.setId(1L);
        when(playlistRepository.findById(any())).thenReturn(Optional.of(playlist));
        when(playlistRepository.save(any())).thenReturn(playlist);
        playlistService.addNewSong(playlist, "SONG1");
        Playlist playlistFounded = playlistService.getPlaylistById(1L);
        assertEquals("SONG1", playlistFounded.getSongList().get(0));
    }

    @Test
    public void removeSongFromPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Playlist 2");
        playlist.setId(1L);
        when(playlistRepository.findById(any())).thenReturn(Optional.of(playlist));
        when(playlistRepository.save(any())).thenReturn(playlist);
        playlistService.addNewSong(playlist, "SONG1");
        playlistService.addNewSong(playlist, "SONG2");
        playlistService.removeSong(playlist, "SONG2");
        Playlist playlistFounded = playlistService.getPlaylistById(1L);
        assertEquals(1, playlistFounded.getSongList().size());
        assertEquals("SONG1", playlistFounded.getSongList().get(0));
    }

    @Test
    public void retrievePlaylist() {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Playlist 1");
        playlist.setId(1L);

        List<String> songsList = new ArrayList<>();
        songsList.add("Song1");
        songsList.add("Song2");
        playlist.setSongList(songsList);

        when(playlistRepository.findById(any())).thenReturn(Optional.of(playlist));
        when(playlistRepository.findAll()).thenReturn(Collections.emptyList());
        playlistService.createPlaylist(playlist);
        verify(playlistRepository).save(any());

        Playlist playlistFounded = playlistService.getPlaylistById(1L);
        assertEquals("Song1", playlistFounded.getSongList().get(0));
        assertEquals("Song2", playlistFounded.getSongList().get(1));


    }
}
