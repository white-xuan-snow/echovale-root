package com.echovale.login.infrastructure.captcha.config;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.resource.CrudResourceStore;
import cloud.tianai.captcha.resource.ResourceStore;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.common.model.dto.ResourceMap;
import cloud.tianai.captcha.resource.impl.provider.ClassPathResourceProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2026/1/11 21:37
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCaptchaResourceConfiguration {

    private final ResourceStore resourceStore;
    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @PostConstruct
    public void init() throws IOException {
        CrudResourceStore crudResourceStore = (CrudResourceStore) resourceStore;

        // 1. 自动加载模板 (META-INF/captcha-tmp-img)
        loadTemplates(crudResourceStore);

        // 2. 自动加载背景图 (captcha-img/bg_xxxx.jpg)
        loadBackgroundResources(crudResourceStore);
    }

    private void loadTemplates(CrudResourceStore store) throws IOException {
        // 1. 扫描所有 png 资源
        org.springframework.core.io.Resource[] resources =
                resolver.getResources("classpath*:captcha-tmp-img/*.png");

        // 用于配对的临时 Map: groupKey -> [滑块路径, 底图路径]
        Map<String, String[]> pairMap = new HashMap<>();

        for (org.springframework.core.io.Resource res : resources) {
            String filename = res.getFilename();
            if (filename == null) {
                continue;
            }

            // 提取前缀（例如 "aa-滑块.png" 提取出 "aa"）
            String prefix = filename.replace("-滑块.png", "").replace("-底图.png", "");

            // 获取父目录名 (如 "1", "2")
            String path = res.getURL().getPath();
            String[] parts = path.split("/");
            if (parts.length < 2) {
                continue;
            }
            String folderName = parts[parts.length - 2];

            // 组合唯一标识：目录+前缀
            String groupKey = folderName + ":" + prefix;
            String internalPath = folderName + "/" + filename;

            String[] paths = pairMap.computeIfAbsent(groupKey, k -> new String[2]);

            if (filename.contains("滑块")) {
                paths[0] = internalPath;
            } else if (filename.contains("底图")) {
                paths[1] = internalPath;
            }
        }

        // 2. 统计计数器
        AtomicInteger sliderCount = new AtomicInteger(0);
        AtomicInteger rotateCount = new AtomicInteger(0);

        // 3. 遍历并添加
        pairMap.forEach((groupKey, paths) -> {
            String activePath = paths[0];
            String fixedPath = paths[1];

            if (activePath != null && fixedPath != null) {
                String folderName = groupKey.split(":")[0];
                ResourceMap resourceMap = new ResourceMap("group-" + groupKey, 2);
                resourceMap.put("active.png", new Resource(ClassPathResourceProvider.NAME, activePath));
                resourceMap.put("fixed.png", new Resource(ClassPathResourceProvider.NAME, fixedPath));

                // 判断类型并累加计数
                if ("3".equals(folderName)) {
                    store.addTemplate(CaptchaTypeConstant.ROTATE, resourceMap);
                    rotateCount.incrementAndGet();
                } else {
                    store.addTemplate(CaptchaTypeConstant.SLIDER, resourceMap);
                    sliderCount.incrementAndGet();
                }
            }
        });

        // 4. 只打印最后的统计结果
        log.info("验证码模板加载完成：共添加 {} 个滑动模板，{} 个旋转模板。",
                sliderCount.get(), rotateCount.get());
    }

    private void loadBackgroundResources(CrudResourceStore store) throws IOException {
        // 扫描符合 bg_*.jpg 的资源
        org.springframework.core.io.Resource[] resources =
                resolver.getResources("classpath*:captcha-bg-img/bg_*.jpg");

        for (org.springframework.core.io.Resource res : resources) {
            String filename = res.getFilename();
            // 构建路径：captcha-img/bg_xxxx.jpg
            String internalPath = "captcha-bg-img/" + filename;

            // 添加背景图资源
            store.addResource(CaptchaTypeConstant.SLIDER,
                    new Resource(ClassPathResourceProvider.NAME, internalPath, "default"));
        }
        log.info("已自动加载背景图数量: {}", resources.length);
    }
}