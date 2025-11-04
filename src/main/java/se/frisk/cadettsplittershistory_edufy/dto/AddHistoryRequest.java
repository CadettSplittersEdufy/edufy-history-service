package se.frisk.cadettsplittershistory_edufy.dto;


import jakarta.validation.constraints.NotNull;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;


public record AddHistoryRequest(
        @NotNull String userId,
        @NotNull HistoryEntity.ItemType itemType,
        @NotNull Long itemId
) {}