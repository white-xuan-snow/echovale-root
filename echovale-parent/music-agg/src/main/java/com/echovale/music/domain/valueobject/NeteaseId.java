package com.echovale.music.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AlbumResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.AuthorDetailResult;
import com.netease.music.api.autoconfigure.configuration.pojo.result.MusicDetailResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/11/17 23:12
 */
@Getter
@Value
@AllArgsConstructor
public class NeteaseId {
    @JsonValue
    Long id;

    public static List<Long> values(List<NeteaseId> neteaseIds) {
        return neteaseIds.stream()
                .map(NeteaseId::getId)
                .toList();
    }

    public static List<NeteaseId> byAuthorsDetailResult(List<AuthorDetailResult> distinctAuthorDetailResults) {
        return distinctAuthorDetailResults.stream()
                .map(AuthorDetailResult::getId)
                .map(NeteaseId::byLong)
                .toList();
    }

    public static List<NeteaseId> byAlbumResult(List<AlbumResult> nonexistentAlbumResult) {
        return nonexistentAlbumResult.stream()
                .map(AlbumResult::getId)
                .map(NeteaseId::byLong)
                .toList();
    }

    public static List<NeteaseId> byLongList(List<Long> neteaseIds) {
        return neteaseIds.stream()
                .map(NeteaseId::byLong)
                .toList();
    }

    public String getNeteaseIdStr() {
        return String.valueOf(id);
    }

    public NeteaseId() {
        this.id = 0L;
    }


    public Boolean isValid() {
        return id != null && id != 0L;
    }

    public static List<Long> getLongList(List<NeteaseId> neteaseIdList) {
        return neteaseIdList.stream()
                .map(NeteaseId::getId)
                .toList();
    }


    public static NeteaseId byLong(Long id) {
        if (id == null) {
            return null;
        }
        return new NeteaseId(id);
    }


    public static List<NeteaseId> byLong(List<MusicDetailResult> tracks) {
        return tracks.stream()
                .map(MusicDetailResult::getId)
                .map(NeteaseId::byLong)
                .toList();
    }


    public static NeteaseId byLong(MusicDetailResult track) {
        return new NeteaseId(track.getId());
    }

}
