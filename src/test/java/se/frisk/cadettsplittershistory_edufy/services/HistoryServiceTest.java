package se.frisk.cadettsplittershistory_edufy.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import se.frisk.cadettsplittershistory_edufy.repositories.HistoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryService historyService;


    @Test
    void addHistory_returnsSavedHistory() {
        String userId = "user-1";
        HistoryEntity.ItemType itemType = HistoryEntity.ItemType.MUSIC;
        Long itemId = 42L;

        HistoryEntity saved = new HistoryEntity();
        saved.setId(10L);
        saved.setUserId(userId);
        saved.setItemType(itemType);
        saved.setItemId(itemId);

        when(historyRepository.save(any(HistoryEntity.class))).thenReturn(saved);

        HistoryEntity result = historyService.addHistory(userId, itemType, itemId);

        verify(historyRepository).save(any(HistoryEntity.class));

        assertEquals(10L, result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(itemType, result.getItemType());
        assertEquals(itemId, result.getItemId());
        assertNotNull(result.getPlayedAt());

        ArgumentCaptor<HistoryEntity> captor = ArgumentCaptor.forClass(HistoryEntity.class);
        verify(historyRepository).save(captor.capture());
        HistoryEntity sent = captor.getValue();
        assertEquals(userId, sent.getUserId());
        assertEquals(itemType, sent.getItemType());
        assertEquals(itemId, sent.getItemId());
        assertNotNull(sent.getPlayedAt());
    }

    @Test
    void getRecentHistory_returnsListFromRepository() {
        String userId = "user-2";
        int limit = 5;

        List<HistoryEntity> repoResult = List.of(new HistoryEntity(), new HistoryEntity());
        when(historyRepository.findByUserIdOrderByPlayedAtDesc(eq(userId), any()))
                .thenReturn(repoResult);

        List<HistoryEntity> result = historyService.getRecentHistory(userId, limit);

        assertSame(repoResult, result);
        verify(historyRepository).findByUserIdOrderByPlayedAtDesc(eq(userId), any());
    }

    @Test
    void getRecentHistory_throwsWhenLimitNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> historyService.getRecentHistory("3", 0));
        assertThrows(IllegalArgumentException.class, () -> historyService.getRecentHistory("3", -1));
        verifyNoInteractions(historyRepository);
    }

    @Test
    void getHistoryByType_returnsListFromRepository() {
        String userId = "4";
        HistoryEntity.ItemType itemType = HistoryEntity.ItemType.POD;
        int limit = 3;

        List<HistoryEntity> repoResult = List.of(new HistoryEntity());
        when(historyRepository.findByUserIdAndItemTypeOrderByPlayedAtDesc(eq(userId), eq(itemType), any()))
                .thenReturn(repoResult);

        List<HistoryEntity> result = historyService.getHistoryByType(userId, itemType, limit);
        assertSame(repoResult, result);
        verify(historyRepository).findByUserIdAndItemTypeOrderByPlayedAtDesc(eq(userId), eq(itemType), any());
    }

    @Test
    void getHistoryByType_throwsLimitNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> historyService
                .getHistoryByType("5", HistoryEntity.ItemType.VIDEO, 0));
        verifyNoInteractions(historyRepository);
    }

    @Test
    void getPlayCountForItem_returnsCountFromRepository() {
        HistoryEntity.ItemType itemType = HistoryEntity.ItemType.MUSIC;
        Long itemId = 6L;
        when(historyRepository.countByItemTypeAndItemId(itemType, itemId)).thenReturn(7L);

        long result = historyService.getPlayCountForItem(itemType, itemId);

        assertEquals(7L, result);
        verify(historyRepository).countByItemTypeAndItemId(itemType, itemId);
    }

    @Test
    void deleteHistory_returnsDeletedCount() {
        String userId = "8";
        when(historyRepository.deleteByUserId(userId)).thenReturn(8);

        long result = historyService.deleteHistoryForUser(userId);

        assertEquals(8L, result);
        verify(historyRepository).deleteByUserId(userId);
    }
}
