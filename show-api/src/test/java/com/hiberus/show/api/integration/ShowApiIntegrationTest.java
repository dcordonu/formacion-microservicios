package com.hiberus.show.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.show.api.ShowApiApplication;
import com.hiberus.show.api.config.ShowApiConfig;
import com.hiberus.show.api.domain.dto.ShowDto;
import com.hiberus.show.api.domain.entity.Show;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {ShowApiApplication.class, ShowApiConfig.class})
public class ShowApiIntegrationTest {

    private static final String SHOWS_COLLECTION = "shows";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mongoTemplate.dropCollection(SHOWS_COLLECTION);
        mongoTemplate.save(Show.builder().name("Tenet").availablePlatforms(new String[]{"HBO"}).identifier("1").build(), SHOWS_COLLECTION);
    }

    @Test
    public void testFindAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/show/all")).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(new ShowDto[]{ShowDto.builder().title("Tenet").availablePlatforms(new String[]{"HBO"}).identifier("1").build()}));
    }

    @Test
    public void testFindById() throws Exception {
        MvcResult result = mockMvc.perform(get("/show/1")).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(ShowDto.builder().title("Tenet").availablePlatforms(new String[]{"HBO"}).identifier("1").build()));

        mockMvc.perform(get("/show/2")).andExpect(status().isNotFound()).andReturn();
    }
}
