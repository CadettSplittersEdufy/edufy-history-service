package se.frisk.cadettsplittershistory_edufy.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import se.frisk.cadettsplittershistory_edufy.repositories.HistoryRepository;

import java.time.Instant;
import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public HistoryEntity addHistory(Long userId, HistoryEntity.ItemType itemType, Long itemId) {
        var entity = new HistoryEntity(userId, itemType, itemId, Instant.now());
        return historyRepository.save(entity);
    }

    public List<HistoryEntity> getRecentHistory(Long userId, int limit) {
        return historyRepository.findByUserIdOrderByPlayedAtDesc(userId, PageRequest.of(0, limit));
    }

    public List<HistoryEntity> getHistoryByType(Long userId, HistoryEntity.ItemType itemType, int limit) {
        return historyRepository.findByUserIdAndItemTypeOrderByPlayedAtDesc(userId, itemType, PageRequest.of(0, limit));
    }

    public long getPlayCountForItem(HistoryEntity.ItemType itemType, Long itemId) {
        return historyRepository.countByItemTypeAndItemId(itemType, itemId);
    }
}
