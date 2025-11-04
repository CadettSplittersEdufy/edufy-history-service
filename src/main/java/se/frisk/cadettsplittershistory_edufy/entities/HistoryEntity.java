package se.frisk.cadettsplittershistory_edufy.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "history", indexes = {
        @Index(name = "idx_history_user_playedat", columnList = "userId, playedAt"),
        @Index(name = "idx_history_item", columnList = "itemType, itemId")
})
public class HistoryEntity {

    public enum ItemType { MUSIC, POD, VIDEO }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false, length = 20)
    private ItemType itemType;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "played_at", nullable = false)
    private Instant playedAt = Instant.now();

    public HistoryEntity() {}

    public HistoryEntity(String userId, ItemType itemType, Long itemId, Instant playedAt) {
        this.userId = userId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.playedAt = playedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public Instant getPlayedAt() { return playedAt; }
    public void setPlayedAt(Instant playedAt) { this.playedAt = playedAt; }
}
