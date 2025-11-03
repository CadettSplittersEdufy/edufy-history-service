package se.frisk.cadettsplittershistory_edufy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import se.frisk.cadettsplittershistory_edufy.dto.AddHistoryResponse;
import se.frisk.cadettsplittershistory_edufy.entities.HistoryEntity;
import se.frisk.cadettsplittershistory_edufy.services.HistoryService;
import se.frisk.cadettsplittershistory_edufy.dto.AddHistoryRequest;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/{userId}/{itemType}")
    public List<HistoryEntity> getHistoryForType(@PathVariable String userId,
                                                 @PathVariable HistoryEntity.ItemType itemType,
                                                 @RequestParam(defaultValue = "18") int limit) {
        return historyService.getHistoryByType(userId, itemType, limit);
    }
}
