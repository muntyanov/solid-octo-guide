package ru.tinkoff.edu.seminar.lesson2.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.edu.seminar.lesson2.domain.Link;
import ru.tinkoff.edu.seminar.lesson2.service.ShortLinkHolder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class ShortLinksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortLinkHolder holder;

    private final String url1 = "https://ya.ru";

    private final String url2 = "https://google.com";

    @BeforeEach
    public void beforeEach() {
        holder.clear();
    }

    @Test
    void should_return_bad_request_when_full_link_not_set() throws Exception {
        mockMvc.perform(post("/link"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_not_found_when_short_link_not_found() throws Exception {
        mockMvc.perform(get("/s/12e2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void must_create_and_return_short_link() throws Exception {
        var result = mockMvc.perform(
                        post("/link")
                                .content(url1)
                )
                .andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(get(
                        String.format("/s/%s",
                                result
                                        .getResponse()
                                        .getContentAsString()
                                        .replaceAll(".*shortUrl\":\"", "")
                                        .replaceAll("\".*", ""))))
                .andExpect(status().is3xxRedirection())
                .andExpect(header()
                        .stringValues("Location", url1));
        mockMvc.perform(get("/link"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").hasJsonPath())
                .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void must_create_and_return_probability_link() throws Exception {
        mockMvc.perform(
                        post("/link")
                                .content(String.format("{\"%s\":1}", url1))
                )
                .andExpect(status().isOk());
    }

    @Test
    void must_return_404_if_not_contains_links() throws Exception {
        mockMvc.perform(
                        get("/link")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void must_return_404_if_delete_not_contains_links() throws Exception {
        mockMvc.perform(
                        delete("/link/1")
                )
                .andExpect(status().isNotFound());
    }



    @Test
    void must_delete_contains_links_on_delete_request() throws Exception {
        holder.save(new Link(url1, "1"));
        mockMvc.perform(
                        delete("/link/1")
                )
                .andExpect(status().isOk());
        assertFalse(holder.exists("1"));
    }

    @Test
    void must_return_link_if_contains_links() throws Exception {
        holder.save(new Link(url1, "1"));
        mockMvc.perform(
                        get("/link/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath(".shortUrl")
                        .value("1"));
    }

    @Test
    void must_return_not_found_if_not_contains_links() throws Exception {
        mockMvc.perform(
                        get("/link/1")
                )
                .andExpect(status().isNotFound());
    }
    @Test
    void must_return_bad_request_if_not_contains_body() throws Exception {
        mockMvc.perform(
                        post("/link/probability")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void must_return_bad_request_when_request_contains_short_base_url() throws Exception {
        mockMvc.perform(
                        post("/link/probability")
                                .header("Content-Type", "application/json")
                                .content(String.format("{\"probability\":{\"a\":2" +
                                        ",\"%s\":2}}", url1, url2))
                ).andExpect(status().isBadRequest());
    }

    @Test
    void must_return_bad_request_on_zero_probability_request() throws Exception {
        mockMvc.perform(
                        post("/link/probability")
                                .header("Content-Type", "application/json")
                                .content(String.format("{\"probability\":{\"%s\":2" +
                                        ",\"%s\":0}}", url1, url2))
                ).andExpect(status().isBadRequest());
    }

    @Test
    void must_create_and_return_two_short_link() throws Exception {
        mockMvc.perform(
                        post("/link")
                                .content(url1)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/link")
                                .content(url2)
                )
                .andExpect(status().isOk());
        mockMvc.perform(get("/link"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").hasJsonPath())
                .andExpect(jsonPath("$[1]").hasJsonPath())
                .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    void must_create_and_return_two_short_link_when_one_is_probability() throws Exception {
        mockMvc.perform(
                        post("/link")
                                .content(url1)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/link/probability")
                                .header("Content-Type", "application/json")
                                .content(String.format("{\"probability\":{\"%s\":1, \"%s\":2}}", url1, url2))
                )
                .andExpect(status().isOk());
        mockMvc.perform(get("/link"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").hasJsonPath())
                .andExpect(jsonPath("$[1]").hasJsonPath())
                .andExpect(jsonPath("$[2]").doesNotExist());
    }
}