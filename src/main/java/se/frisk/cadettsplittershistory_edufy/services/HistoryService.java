package se.frisk.cadettsplittershistory_edufy.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import se.frisk.cadettsplittershistory_edufy.repositories.HistoryRepository;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Transactional
    public HistoryEntity addHistory(String userId, HistoryEntity.ItemType itemType, Long itemId) {
        var entity = new HistoryEntity(userId, itemType, itemId, Instant.now());
        return historyRepository.save(entity);
    }

    public List<HistoryEntity> getRecentHistory(String userId, int limit) {
        return historyRepository.findByUserIdOrderByPlayedAtDesc(userId, PageRequest.of(0, limit));
    }

    public List<HistoryEntity> getHistoryByType(String userId, HistoryEntity.ItemType itemType, int limit) {
        return historyRepository.findByUserIdAndItemTypeOrderByPlayedAtDesc(userId, itemType, PageRequest.of(0, limit));
    }

    public long getPlayCountForItem(HistoryEntity.ItemType itemType, Long itemId) {
        return historyRepository.countByItemTypeAndItemId(itemType, itemId);
    }

    @Transactional
    public long deleteHistoryForUser(String userId) {
        return historyRepository.deleteByUserId(userId);
    }
}
