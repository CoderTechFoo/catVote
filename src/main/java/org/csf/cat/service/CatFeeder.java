package org.csf.cat.service;

import jakarta.annotation.PostConstruct;
import org.csf.cat.dao.CatDao;
import org.csf.cat.model.Cat;
import org.csf.cat.spi.CatImagesProviderService;
import org.csf.cat.spi.model.AtelierDataSpiResponse;
import org.csf.cat.spi.model.CatImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CatFeeder {

    private final CatImagesProviderService catImagesProviderService;

    private final CatDao catDao;

    @Autowired
    CatFeeder (CatImagesProviderService catImagesProviderService, CatDao catDao) {
        this.catImagesProviderService = catImagesProviderService;
        this.catDao = catDao;
    }

    @PostConstruct
    public void initAtStartup() {
        feed();
    }

    @Scheduled(cron = "0 */5 * * * *") // Each 5 minutes
    public void feed() {
        AtelierDataSpiResponse images = catImagesProviderService.getImages();
        Optional.ofNullable(images)
                .ifPresent(this::upsertCats);
    }

    protected void upsertCats (AtelierDataSpiResponse images) {
        Optional.ofNullable(images.getImages())
                .ifPresent(img -> {
                    img.forEach(this::upsertCat);
                });
    }

    protected void upsertCat (CatImage image) {
        catDao.findById(image.getId())
                .ifPresentOrElse(cat -> {
                    cat.setImageUrl(image.getUrl());
                    catDao.save(cat);
                }, () -> {
                    Cat cat = Cat.builder()
                            .id(image.getId())
                            .imageUrl(image.getUrl())
                            .voteCount(0)
                            .build();
                    catDao.save(cat);
                });
    }
}
