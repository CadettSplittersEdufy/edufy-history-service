package se.frisk.cadettsplittershistory_edufy.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    List<HistoryEntity> findByUserIdOrderByPlayedAtDesc(String userId, Pageable pageable);
    List<HistoryEntity> findByUserIdAndItemTypeOrderByPlayedAtDesc(String userId, HistoryEntity.ItemType itemType, Pageable pageable);
    long countByItemTypeAndItemId(HistoryEntity.ItemType itemType, Long itemId);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from HistoryEntity h where h.userId = :userId")
    int deleteByUserId(@Param("userId") String userId);
}

