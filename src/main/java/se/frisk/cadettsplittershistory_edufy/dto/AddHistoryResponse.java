package se.frisk.cadettsplittershistory_edufy.dto;

import java.time.Instant;

public record AddHistoryResponse (
    String message,
    Long id,
    String userId,
    String itemType,
    Long itemId,
    Instant playedAt
){}
