package com.galvanize.playlist.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String playlistName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Song> songList;

    public Playlist() {
        songList = new ArrayList<Song>();
    }


}
