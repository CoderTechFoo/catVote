package org.csf.cat.mapper;

import org.csf.cat.api.model.CatResponse;
import org.csf.cat.model.Cat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CatMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "voteCount", target = "voteCount")
    CatResponse toResponse(Cat cat);

    List<CatResponse> toResponseList(List<Cat> cats);
}
