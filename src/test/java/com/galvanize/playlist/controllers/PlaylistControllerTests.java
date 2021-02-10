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

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        String playlistString = objectMapper.writeValueAsString(new Playlist());
        mockMvc.perform(post("/playlist").content(playlistString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
