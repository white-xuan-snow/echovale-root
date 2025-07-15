package com.echovale.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/7/15 10:49
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LyricVO {
    private Long id;
    private String neteaseLrc;
    private String neteaseTlrc;
    private String neteaseYrc;
    private String neteaseRomalyc;
    private String neteaseKlrc;
    private String amllTtml;
    private String echoTtml;
}
