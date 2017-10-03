package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.createWithExceed;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    private MealService service;
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MATCHER.contentListMatcher(MEAL1));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(EXCEED_MATCHER.contentListMatcher(EXCEEDED_MEALS));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print());
        MATCHER.assertCollectionEquals(service.getAll(USER_ID), MEALS.subList(0, MEALS.size() -1));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = new Meal(MEAL1);
        meal.setCalories(300);
        meal.setDescription("updated");
        mockMvc.perform(put(REST_URL + MEAL1_ID).contentType(APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(meal, service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(null, LocalDateTime.now(), "new Meal", 350);

        ResultActions actions = mockMvc.perform(post(REST_URL)
                .contentType(APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isCreated());

        Meal created = MATCHER.fromJsonAction(actions);
        meal.setId(created.getId());

        MATCHER.assertEquals(meal, created);
        MATCHER.assertCollectionEquals(Arrays.asList(meal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1),
                service.getAll(USER_ID));

    }

    @Test
    public void testFilter() throws Exception {
        LocalDate startDate = MEAL4.getDate();
        LocalTime startTime = MEAL4.getTime();
        LocalDate endDate = MEAL6.getDate();
        LocalTime endTime = MEAL6.getTime();
        mockMvc.perform(get(REST_URL + "/filter?startDate=" + startDate + "&startTime=" + startTime +
        "&endDate=" + endDate + "&endTime=" + endTime ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(EXCEED_MATCHER.contentListMatcher(createWithExceed(MEAL6, true), createWithExceed(MEAL5, true), createWithExceed(MEAL4, true)));
    }

    @Test
    public void testFilterWithNull() throws Exception {
        LocalDate startDate = MEAL6.getDate();
        LocalDate endDate = MEAL5.getDate();

        mockMvc.perform(get(REST_URL + "/filter?startDate=" + startDate + "&endDate=" + endDate))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(EXCEED_MATCHER.contentListMatcher(createWithExceed(MEAL6, true), createWithExceed(MEAL5, true), createWithExceed(MEAL4, true)));
    }
 }