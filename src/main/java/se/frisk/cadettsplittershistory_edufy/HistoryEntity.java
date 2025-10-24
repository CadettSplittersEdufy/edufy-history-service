package se.frisk.cadettsplittershistory_edufy;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "history", indexes = {
        @Index(name = "idx_history_user_playedat", columnList = "userId, playedAt"),
        @Index(name = "idx_history_item", columnList = "itemType, itemId")
})
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ItemType itemType;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Instant playedAt = Instant.now();

    public enum ItemType {MUSIC, POD, VIDEO}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HistoryEntity(Long userId, ItemType itemType, Long itemId, Instant playedAt) {
        this.userId = userId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.playedAt = playedAt;
    }

    public HistoryEntity() {
    }
}
