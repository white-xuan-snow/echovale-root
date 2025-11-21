package com.echovale.music.appliaction.dto;

import com.echovale.music.domain.aggregate.Album;
import com.echovale.music.domain.aggregate.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/20 15:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private Album album;
    private List<Author> authorList;
}
