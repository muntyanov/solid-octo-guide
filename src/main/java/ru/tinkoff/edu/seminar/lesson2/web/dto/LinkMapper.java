package ru.tinkoff.edu.seminar.lesson2.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;
import ru.tinkoff.edu.seminar.lesson2.domain.ProbabilityBaseUrlLink;

import java.util.Collection;
import java.util.Collections;

import static ru.tinkoff.edu.seminar.lesson2.web.dto.ResponseDto.*;

@Mapper(imports = Collections.class)
public interface LinkMapper {

    LinkMapper INSTANCE = Mappers.getMapper(LinkMapper.class);

    @Mapping(source = "fullUrl", target = "fullUrl")
    @Mapping(source = "shortUrl", target = "shortUrl")
    LinkCreateResponseDto linkToLinkCreateDto(AbstractLink link);

    @Mapping(expression = "java(link.getFullUrlCollection())", target = "fullUrl")
    @Mapping(source = "shortUrl", target = "shortUrl")
    ProbabilityLinkCreateResponseDto linkToLinkListCreateDto(ProbabilityBaseUrlLink link);

    @Mapping(expression = "java(LinkMapper.fullPathOfLink(link))", target = "fullUrl")
    @Mapping(source = "shortUrl", target = "shortUrl")
    LinkListResponseDto linkToListLinkListDto(AbstractLink link);


    static Collection<String> fullPathOfLink(AbstractLink link){
        if(link instanceof Link){
            return Collections.singletonList(link.getFullUrl());
        }
        if(link instanceof ProbabilityBaseUrlLink){
            return ((ProbabilityBaseUrlLink) link).getFullUrlCollection();
        }
        return Collections.emptyList();
    }
}