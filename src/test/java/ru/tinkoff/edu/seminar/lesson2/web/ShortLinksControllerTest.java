package ru.tinkoff.edu.seminar.lesson2.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.edu.seminar.lesson2.service.ShortLinkHolder;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class ShortLinksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortLinkHolder holder;

    @BeforeEach
    public void beforeEach(){
        holder.clear();
    }

    @Test
    void should_return_bad_request_when_full_link_not_set() throws Exception {
        mockMvc.perform(post("/link")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void should_return_not_found_when_short_link_not_found() throws Exception {
        mockMvc.perform(get("/s/12e2")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void must_create_and_return_short_link() throws Exception {
        var result = mockMvc.perform(
                post("/link")
                        .content("https://ya.ru")
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
                        .stringValues("Location", "https://ya.ru"));
        mockMvc.perform(get("/link"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").hasJsonPath())
                .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void must_create_and_return_two_short_link() throws Exception {
        mockMvc.perform(
                        post("/link")
                                .content("https://ya.ru")
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/link")
                                .content("https://www.google.com")
                )
                .andExpect(status().isOk());
        mockMvc.perform(get("/link"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").hasJsonPath())
                .andExpect(jsonPath("$[1]").hasJsonPath())
                .andExpect(jsonPath("$[2]").doesNotExist());
    }
}