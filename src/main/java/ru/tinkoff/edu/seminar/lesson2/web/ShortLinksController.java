package ru.tinkoff.edu.seminar.lesson2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;
import ru.tinkoff.edu.seminar.lesson2.service.ShortLinkService;

import java.util.Collection;

@RestController
public class ShortLinksController {

	private ShortLinkService shortLinkService;

	@Autowired
	public ShortLinksController(ShortLinkService shortLinkService) {
		this.shortLinkService = shortLinkService;
	}

	@RequestMapping("/s/{sl}")
	public ModelAndView shortLinkRedirect(
			@PathVariable("sl") String shortLink
	) {
		Link link = shortLinkService.find(shortLink);
		if(link == null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		return new ModelAndView(String.format("redirect:%s", link.getFullUrl()));
	}

	@PostMapping("/link")
	public Link shortLinkCreate(@RequestBody String fullPath) {
		return shortLinkService.create(fullPath);
	}

	@GetMapping("/link")
	public Collection<Link> shortLinks() {
		if(false)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		return shortLinkService.findAll();
	}

	@ExceptionHandler(HttpClientErrorException.class)
	ResponseEntity<String> handleFileUploadError(final HttpClientErrorException e) {
		return new ResponseEntity(e.getMessage(), e.getStatusCode());
	}
}
