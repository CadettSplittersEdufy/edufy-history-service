package se.frisk.cadettsplittershistory_edufy.controllers;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.frisk.cadettsplittershistory_edufy.dto.AddHistoryRequest;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import se.frisk.cadettsplittershistory_edufy.services.HistoryService;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoryController.class)
@ExtendWith(MockitoExtension.class)
public class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HistoryService historyService;

    @TestConfiguration
    static class MockConfig{

        @Bean
        public HistoryService historyService(){
            return mock(HistoryService.class);
        }
    }

    @Test
    void addHistory_returnsCreatedAndBody() throws Exception {
        AddHistoryRequest request = new AddHistoryRequest("1", HistoryEntity.ItemType.MUSIC, 50L);
        HistoryEntity saved = new HistoryEntity("1", HistoryEntity.ItemType.MUSIC, 50L, Instant.now());
        saved.setId(1L);
        when(historyService.addHistory("1", HistoryEntity.ItemType.MUSIC, 50L))
                .thenReturn(saved);

        mockMvc.perform(post("/history/addHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("History saved"))
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.itemType").value("MUSIC"))
                .andExpect(jsonPath("$.itemId").value(50))
                .andExpect(jsonPath("$.id").value(1));

        verify(historyService).addHistory("1", HistoryEntity.ItemType.MUSIC, 50L);
    }

    @Test
    void getHistoryForUser_returnsHistory() throws Exception {
        HistoryEntity history = new HistoryEntity("abc", HistoryEntity.ItemType.VIDEO, 54L, Instant.now());
        history.setId(1L);
        when(historyService.getRecentHistory("abc", 100)).thenReturn(List.of(history));

        mockMvc.perform(get("/history/userhistory/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].userId").value("abc"))
                .andExpect(jsonPath("$.[0].itemType").value("VIDEO"))
                .andExpect(jsonPath("$.[0].itemId").value(54));
        verify(historyService).getRecentHistory("abc", 100);
    }

    @Test
    void getHistoryForType_retiurnsHistory() throws Exception {
        HistoryEntity history = new HistoryEntity("1", HistoryEntity.ItemType.POD, 100L, Instant.now());
        history.setId(11L);
        when(historyService.getHistoryByType("1", HistoryEntity.ItemType.POD, 100)).thenReturn(List.of(history));

        mockMvc.perform(get("/history/historyByType/1/POD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].userId").value("1"))
                .andExpect(jsonPath("$.[0].itemType").value("POD"))
                .andExpect(jsonPath("$.[0].itemId").value(100));

        verify(historyService).getHistoryByType("1", HistoryEntity.ItemType.POD, 100);
    }

    @Test
    void deleteHistoryForUser_returnsOk() throws Exception {
        when(historyService.deleteHistoryForUser("user-7")).thenReturn(5);

        mockMvc.perform(delete("/history/deleteUserHistory/user-7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("History deleted"))
                .andExpect(jsonPath("$.userId").value("user-7"))
                .andExpect(jsonPath("$.deletedCount").value(5));

        verify(historyService).deleteHistoryForUser("user-7");
    }
}
