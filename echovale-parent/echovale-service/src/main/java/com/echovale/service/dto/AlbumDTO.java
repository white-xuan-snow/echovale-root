package com.echovale.service.dto;

import com.echovale.domain.po.AlbumPO;
import com.echovale.domain.po.AuthorPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/6 21:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private AlbumPO album;
    private List<AuthorPO> authors;
}
