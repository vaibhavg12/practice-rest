package vb.practice.rest.spring.boot.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import vb.practice.rest.spring.boot.model.Target;
import vb.practice.rest.spring.boot.service.TargetService;
import vb.practice.rest.spring.boot.utils.MessageUtil;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link TargetController} class.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class TargetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TargetService mockTargetService;

    private static String createDataSize() {
        int msgSize = 5 * 1024 * 1024;
        StringBuilder sb = new StringBuilder(msgSize);
        for (int i = 0; i < msgSize; i++) {
            sb.append('a');
        }
        return sb.toString();
    }

    @Before
    public void setUp() {
        Mockito.reset(mockTargetService);
    }

    /**
     * Test the happy path scenario for {@link TargetController#retrieveTarget(Long)}.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testRetrieveTarget() throws Exception {
        Target target = new Target("Mock", 30);
        // when
        when(mockTargetService.findTarget(1L)).thenReturn(target);

        // then
        mockMvc.perform(get("/target/retrieve/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Mock")))
                .andExpect(jsonPath("age", is(30)))
                .andExpect(jsonPath("information", is(nullValue())));
    }

    /**
     * Test for {@link TargetController#retrieveTarget(Long)}, when target service return null target.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testRetrieveTargetReturnsNull() throws Exception {
        // when
        when(mockTargetService.findTarget(1L)).thenReturn(null);

        // then
        mockMvc.perform(get("/target/retrieve/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(status().reason(equalToIgnoringWhiteSpace(MessageUtil.NO_CONTENT)));
    }

    /**
     * Test for {@link TargetController#retrieveTarget(Long)}, when id to be searched is less than one.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test()
    public void testRetrieveTargetException() throws Exception {
        mockMvc.perform(get("/target/retrieve/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(status().reason(equalToIgnoringWhiteSpace(MessageUtil.PRECONDITION_FAILED)));
    }

    /**
     * Test for {@link TargetController#retrieveTarget(Long)}, when the request URL is malformed.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testRetrieveTargetNoApi() throws Exception {
        mockMvc.perform(get("/target/retrieve").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test the happy path scenario for {@link TargetController#updateTarget(Long, Map)}.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testUpdateTarget() throws Exception {

        // given
        String requestBody = "{\"information\":\"information\"}";

        // when
        when(mockTargetService.updateTarget(anyLong(), new byte[]{anyByte()})).thenReturn(1);

        // then
        mockMvc.perform(post("/target/update/2")
                .content(requestBody)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test {@link TargetController#updateTarget(Long, Map)} when id is less than 1.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testUpdateTargetInvalidParam() throws Exception {

        // given
        String requestBody = "{\"information\":\"information\"}";

        // when
        when(mockTargetService.updateTarget(anyLong(), new byte[]{anyByte()})).thenReturn(1);

        // then
        mockMvc.perform(post("/target/update/0")
                .content(requestBody)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(status().reason(equalToIgnoringWhiteSpace(MessageUtil.PRECONDITION_FAILED)));
    }

    /**
     * Test {@link TargetController#updateTarget(Long, Map)} when no information is passed in request body.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testUpdateTargetNoInformation() throws Exception {

        // given
        String requestBody = "{\"info\":\"infomation\"}";


        // when
        when(mockTargetService.updateTarget(anyLong(), new byte[]{anyByte()})).thenReturn(1);

        // then
        mockMvc.perform(post("/target/update/1")
                .content(requestBody)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(status().reason(equalToIgnoringWhiteSpace(MessageUtil.PRECONDITION_FAILED)));
    }

    /**
     * Test {@link TargetController#updateTarget(Long, Map)} when payload is huge.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testUpdateTargetLargePayload() throws Exception {

        String requestBody = "{\"information\":\"" + createDataSize() + "\"}";

        // when
        when(mockTargetService.updateTarget(anyLong(), new byte[]{anyByte()})).thenReturn(1);

        // then
        mockMvc.perform(post("/target/update/1")
                .content(requestBody)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(status().reason(equalToIgnoringWhiteSpace(MessageUtil.LARGE_PAYLOAD)));
    }

    /**
     * Test for {@link TargetController#updateTarget(Long, Map)}, when the request URL is malformed.
     *
     * @throws Exception if any exception occurs during test execution
     */
    @Test
    public void testUpdateTargetNoApi() throws Exception {
        mockMvc.perform(get("/target/update").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
