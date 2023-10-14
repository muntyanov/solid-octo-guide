package ru.tinkoff.edu.seminar.lesson2.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import ru.tinkoff.edu.seminar.lesson2.domain.AbstractLink;
import ru.tinkoff.edu.seminar.lesson2.service.ShortLinkService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s")
public class ShortLinkRedirectController {

    private final ShortLinkService shortLinkService;

    @GetMapping("{short-link}")
    public ModelAndView shortLinkRedirect(
            @PathVariable("short-link") String shortLink
    ) {
        AbstractLink link = shortLinkService.find(shortLink);
        if (link == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        return new ModelAndView(String.format("redirect:%s", link.getFullUrl()));
    }
}
