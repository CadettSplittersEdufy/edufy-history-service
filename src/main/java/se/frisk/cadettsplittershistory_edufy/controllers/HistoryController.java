package se.frisk.cadettsplittershistory_edufy.controllers;

import org.springframework.web.bind.annotation.*;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import se.frisk.cadettsplittershistory_edufy.services.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/test")
    public String test() { return "History service upp and running!"; }

    @PostMapping("/addHistory")
    public HistoryEntity addHistory(@RequestParam Long userId,
                                    @RequestParam HistoryEntity.ItemType itemType,
                                    @RequestParam Long itemId) {
        return historyService.addHistory(userId, itemType, itemId);
    }

    @GetMapping("/{userId}/{itemType}")
    public List<HistoryEntity> getHistoryForType(@PathVariable Long userId,
                                                 @PathVariable HistoryEntity.ItemType itemType,
                                                 @RequestParam(defaultValue = "18") int limit) {
        return historyService.getHistoryByType(userId, itemType, limit);
    }
}
