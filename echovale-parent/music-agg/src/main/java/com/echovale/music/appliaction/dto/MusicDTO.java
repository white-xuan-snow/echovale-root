package com.echovale.music.appliaction.dto;

import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import com.echovale.music.domain.aggregate.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/20 14:42
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicDTO {
    private Music music;
    private List<Author> authorList;
    private Album album;
}
