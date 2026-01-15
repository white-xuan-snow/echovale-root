package com.echovale.playlist.domain.repository;


import com.echovale.playlist.domain.aggregate.Playlist;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistRepository {
    Playlist save(Playlist playlist);
}
