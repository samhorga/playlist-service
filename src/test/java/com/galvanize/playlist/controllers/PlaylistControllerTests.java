package com.galvanize.playlist.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.playlist.model.Playlist;
import com.galvanize.playlist.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PlaylistControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PlaylistRepository playlistRepository;

    @Test
    public void createEmptyPlaylist() throws Exception {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Playlist 1");
        String playlistString = objectMapper.writeValueAsString(playlist);
        String actual = mockMvc.perform(post("/playlist").content(playlistString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Playlist playlistAdded = playlistRepository.save(new Playlist());

        assertTrue(playlistAdded.getSongList().isEmpty());
        assertEquals("Playlist was created successfully.", actual);
    }

    @Test
    public void existingPlaylist() throws Exception {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Playlist 1");

        playlistRepository.save(playlist);

        Playlist playlist2 = new Playlist();
        playlist2.setPlaylistName("Playlist 1");
        String playlist2String = objectMapper.writeValueAsString(playlist2);

        String actual = mockMvc.perform(post("/playlist").content(playlist2String).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        assertEquals("Playlist name already exists. Please use another name.", actual);
    }

    @Test
    public void requiredPlaylistName() throws Exception {
        Playlist playlist = new Playlist();
        String playlist2String = objectMapper.writeValueAsString(playlist);

        String actual = mockMvc.perform(post("/playlist").content(playlist2String).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        assertEquals("Playlist name requried.", actual);
    }

    @Test
    public void addSongToPlaylist() throws Exception {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("PLAYLIST 1");
        Playlist saved = playlistRepository.save(playlist);

        mockMvc.perform(put("/playlist/add" + "/SONG1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(saved))).andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get("/playlist" + "/" + saved.getId())).andExpect(status().isOk()).andReturn();

        Playlist playlistResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Playlist.class);

        assertEquals("SONG1", playlistResponse.getSongList().get(0));
    }


    @Test
    public void removeSongFromPlaylist() throws Exception {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("PLAYLIST 1");

        List<String> songsList = new ArrayList<>();
        songsList.add("Song1");
        songsList.add("Song2");
        playlist.setSongList(songsList);
        Playlist saved = playlistRepository.save(playlist);
        mockMvc.perform(put("/playlist/remove/" + "Song1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(saved))).andExpect(status().isOk());
        MvcResult mvcResult = mockMvc.perform(get("/playlist" + "/" + saved.getId())).andExpect(status().isOk()).andReturn();
        Playlist playlistResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Playlist.class);

        assertEquals(1, playlistResponse.getSongList().size());
        assertEquals("Song2",playlistResponse.getSongList().get(0));
    }

    @Test
    public void retrieveSongsofPlaylist() throws Exception {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Playlist 1");

        List<String> songsList = new ArrayList<>();
        songsList.add("Song1");
        songsList.add("Song2");

        playlist.setSongList(songsList);
        Playlist saved = playlistRepository.save(playlist);

        Playlist playlist2 = new Playlist();
        playlist2.setPlaylistName("Playlist 1");
        MvcResult mvcResult = mockMvc.perform(get("/playlist" + "/" + saved.getId())).andExpect(status().isOk()).andReturn();
        Playlist playlistResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Playlist.class);

        assertEquals(2,playlistResponse.getSongList().size());
        assertEquals("Song1", playlistResponse.getSongList().get(0));
        assertEquals("Song2", playlistResponse.getSongList().get(1));
    }
}
