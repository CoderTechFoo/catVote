package org.csf.cat.service;

import org.csf.cat.dao.CatDao;
import org.csf.cat.model.Cat;
import org.csf.cat.spi.CatImagesProviderService;
import org.csf.cat.spi.model.AtelierDataSpiResponse;
import org.csf.cat.spi.model.CatImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CatFeederTest {
    @InjectMocks
    @Spy
    private CatFeeder catFeeder;

    @Mock
    private CatDao catDao;

    @Mock
    private CatImagesProviderService catImagesProviderService;

    @Test
    void shouldNotInvokeUpsertCats_WhenSpiReturnsNullAsResponse () {
        // Given
        Mockito.doReturn(null).when(catImagesProviderService).getImages();

        // When
        catFeeder.feed();

        // Then
        Mockito.verify(catFeeder, Mockito.never()).upsertCats(Mockito.any());
    }

    @Test
    void shouldCreateACat_WhenSpiReturnOneCatThatDoesNotExistInDatabase () {
        // Given
        AtelierDataSpiResponse response = new AtelierDataSpiResponse();
        String catId = "id1";
        String url = "https://bzz.png";
        response.setImages(Collections.singletonList(CatImage.builder().id(catId).url(url).build()));
        Mockito.doReturn(response).when(catImagesProviderService).getImages();
        Mockito.doReturn(Optional.empty()).when(catDao).findById(catId);

        // When
        catFeeder.feed();

        // Then
        ArgumentCaptor<Cat> captor = ArgumentCaptor.forClass(Cat.class);
        Mockito.verify(catDao, Mockito.times(1)).save(captor.capture());
        Assertions.assertEquals(catId, captor.getValue().getId());
        Assertions.assertEquals(url, captor.getValue().getImageUrl());
        Assertions.assertEquals(0, captor.getValue().getVoteCount());
    }

    @Test
    void shouldUpdateACat_WhenSpiReturnOneCatThatAlreadyExistsInDatabase () {
        // Given
        AtelierDataSpiResponse response = new AtelierDataSpiResponse();
        String catId = "idex1";
        String formerUrl = "https://old.png";
        String newUrl = "https://new.png";
        response.setImages(Collections.singletonList(CatImage.builder().id(catId).url(newUrl).build()));
        Mockito.doReturn(response).when(catImagesProviderService).getImages();
        int votesCount = 15;
        Cat existingCat = Cat.builder().voteCount(votesCount).id(catId).imageUrl(formerUrl).build();
        Mockito.doReturn(Optional.of(existingCat)).when(catDao).findById(catId);

        // When
        catFeeder.feed();

        // Then
        ArgumentCaptor<Cat> captor = ArgumentCaptor.forClass(Cat.class);
        Mockito.verify(catDao, Mockito.times(1)).save(captor.capture());
        Assertions.assertEquals(catId, captor.getValue().getId());
        Assertions.assertEquals(newUrl, captor.getValue().getImageUrl());
        Assertions.assertEquals(votesCount, captor.getValue().getVoteCount());
    }
}
