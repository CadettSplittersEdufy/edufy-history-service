package se.frisk.cadettsplittershistory_edufy.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "spring.sql.init.mode=never")
@ActiveProfiles("test")
public class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    @Test
    void findByUserIdOrderByPlayedAtDesc_returnList() {
        HistoryEntity olderHistory = new HistoryEntity("1", HistoryEntity.ItemType.MUSIC, 10L, Instant.now()
                .minusSeconds(60));
        HistoryEntity newerHistory = new HistoryEntity("1", HistoryEntity.ItemType.MUSIC,20L, Instant.now());
        historyRepository.save(olderHistory);
        historyRepository.save(newerHistory);

        var pageable  = PageRequest.of(0, 10);
        List<HistoryEntity> result = historyRepository.findByUserIdOrderByPlayedAtDesc("1", pageable);

        assertEquals(2, result.size());
        assertEquals(20L, result.get(0).getItemId());
        assertEquals(10L, result.get(1).getItemId());
    }

    @Test
    void countByItemTypeAndItemId_returnCount() {
        HistoryEntity history1 = new HistoryEntity("1", HistoryEntity.ItemType.MUSIC, 10L, Instant.now());
        HistoryEntity history2 = new HistoryEntity("2", HistoryEntity.ItemType.MUSIC, 10L, Instant.now());
        HistoryEntity history3 = new HistoryEntity("3", HistoryEntity.ItemType.POD, 10L, Instant.now());
        historyRepository.saveAll(List.of(history1, history2, history3));

        long count = historyRepository.countByItemTypeAndItemId(HistoryEntity.ItemType.MUSIC, 10L);

        assertEquals(2L, count, "Only two MUSIC items whit id 10 should be counted");
    }

    @Test
    void deleteByUserId_deletesAllHistoryForGivenUser() {
        HistoryEntity history1 = new HistoryEntity("1", HistoryEntity.ItemType.POD,   10L, Instant.now());
        HistoryEntity history2 = new HistoryEntity("1", HistoryEntity.ItemType.MUSIC, 20L, Instant.now());
        HistoryEntity history3 = new HistoryEntity("2", HistoryEntity.ItemType.VIDEO, 30L, Instant.now());
        historyRepository.saveAll(List.of(history1, history2, history3));

        historyRepository.deleteByUserId("1");

        var pageable = PageRequest.of(0, 10);
        var remainingForUser1 =
                historyRepository.findByUserIdOrderByPlayedAtDesc("1", pageable);
        assertTrue(remainingForUser1.isEmpty(), "User 1 should have no history left");

        var remainingForUser2 =
                historyRepository.findByUserIdOrderByPlayedAtDesc("2", pageable);
        assertEquals(1, remainingForUser2.size(), "User 2 should still have history");
    }
}
