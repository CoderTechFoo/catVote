package org.csf.cat.spi;

import io.github.resilience4j.retry.annotation.Retry;
import org.csf.cat.spi.model.AtelierDataSpiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "latelier-cat-api", url = "https://conseil.latelier.co/data")
public interface CatImagesProviderService {
    @Retry(name = "externalAtelierApiRetry")
    @GetMapping("/cats.json")
    AtelierDataSpiResponse getImages();
}
