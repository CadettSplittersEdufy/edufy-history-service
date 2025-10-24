package se.frisk.cadettsplittershistory_edufy;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    List<HistoryEntity> findByUserIdOrderByPlayedAtDesc(Long userId, Pageable pageable);
    List<HistoryEntity> findByUserIdAndItemTypeOrderByPlayedAtDesc(Long userId, HistoryEntity.ItemType itemType, Pageable pageable);
    long countByItemTypeAndItemId(HistoryEntity.ItemType itemType, Long itemId);
}
