package com.galvanize.playlist.services;

import com.galvanize.playlist.model.Playlist;
import com.galvanize.playlist.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
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
}
