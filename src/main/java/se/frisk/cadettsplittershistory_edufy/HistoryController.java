package se.frisk.cadettsplittershistory_edufy;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/test")
    public String test() {
        return "History service upp and running!";
    }

    @PostMapping
    public HistoryEntity addHistory(@RequestParam Long userId, @RequestParam HistoryEntity.ItemType itemType,
                                          @RequestParam Long itemId) {
        return historyService.addHistory(userId, itemType, itemId);
    }

    @GetMapping("/{userId}/{itemType}")
    public List<HistoryEntity> getRecentHistory(@PathVariable Long userId, @PathVariable HistoryEntity.ItemType itemType,
                                                @RequestParam(defaultValue = "18") int limit) {
        return historyService.getHistoryByType(userId, itemType, limit);
    }

    @GetMapping("/count/{itemType}/{itemId}")
    public long getPlayCount(@PathVariable HistoryEntity.ItemType itemType, @PathVariable Long itemId) {
        return historyService.getPlayCountForItem(itemType, itemId);
    }
}
