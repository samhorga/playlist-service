package com.galvanize.playlist.services;

import com.galvanize.playlist.model.Playlist;
import com.galvanize.playlist.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
        playlist.setPlaylistName("Playlist 1");
        playlist.setId(1L);
        when(playlistRepository.save(any())).thenReturn(playlist);
        String actualString = playlistService.createPlaylist(playlist);

        when(playlistRepository.findById(any())).thenReturn(Optional.of(playlist));
        Playlist playlistRetrieved = playlistService.getPlaylistById(1L);

        assertTrue(playlistRetrieved.getSongList().isEmpty());
        assertEquals("Playlist was created successfully.", actualString);
    }
}
