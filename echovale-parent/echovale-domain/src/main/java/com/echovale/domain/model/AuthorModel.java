package com.echovale.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/11 0:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorModel {
    private Long id;
    private String name;
    private Long neteaseId;
    private String transName;
    private String alias;
    private Integer musicSize;
    private Integer albumSize;
    private Integer mvSize;
    private String coverUrl;
    private String avatarUrl;
    private String description;
    private String identify;
}
