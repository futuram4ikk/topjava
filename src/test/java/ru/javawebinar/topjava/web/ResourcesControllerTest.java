package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourcesControllerTest extends AbstractControllerTest {

    @Test
    public void checkResources() throws Exception {
        mockMvc.perform(get("/resources/css/style.css"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/css"));
    }
}
