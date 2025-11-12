package se.frisk.cadettsplittershistory_edufy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import se.frisk.cadettsplittershistory_edufy.dto.AddHistoryResponse;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import se.frisk.cadettsplittershistory_edufy.services.HistoryService;
import se.frisk.cadettsplittershistory_edufy.dto.AddHistoryRequest;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    
    private final HistoryService historyService;
    public HistoryController(HistoryService historyService) { this.historyService = historyService; }

    @GetMapping("/test")
    public String test() { return "History service upp and running!"; }

    @PostMapping("/addHistory")
    public ResponseEntity<AddHistoryResponse> addHistory(@RequestBody @Valid AddHistoryRequest req) {

        HistoryEntity savedHistory = historyService.addHistory(req.userId(), req.itemType(), req.itemId());

        var body = new AddHistoryResponse(
                "History saved",
                savedHistory.getId(),
                savedHistory.getUserId(),
                savedHistory.getItemType().name(),
                savedHistory.getItemId(),
                savedHistory.getPlayedAt()
        );

        return ResponseEntity
                .created(URI.create("/api/history/"+savedHistory.getUserId() + "/" + savedHistory.getItemType().name()))
                .body(body);
    }

    @GetMapping("/historyByType/{userId}/{itemType}")
    public List<HistoryEntity> getHistoryForType(@PathVariable String userId,
                                                 @PathVariable HistoryEntity.ItemType itemType,
                                                 @RequestParam(defaultValue = "100") int limit) {
        return historyService.getHistoryByType(userId, itemType, limit);
    }

    @GetMapping("/userhistory/{userId}")
    public List<HistoryEntity> getHistoryForUser(@PathVariable String userId,
                                                 @RequestParam(defaultValue = "100") int limit) {
        return  historyService.getRecentHistory(userId, limit);
    }

    @DeleteMapping("deleteUserHistory/{userId}")
    public ResponseEntity<Map<String, Object>> deleteHistoryForUser(@PathVariable String userId) {
        long deletedCount = historyService.deleteHistoryForUser(userId);

        Map<String, Object> body = new HashMap<>();
        body.put("message", "History deleted: ");
        body.put("userId", userId);
        body.put("deletedCount", deletedCount);

        if (deletedCount == 0) {
            body.put("message", "No history found for user");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        return ResponseEntity.ok(body);
    }
}
