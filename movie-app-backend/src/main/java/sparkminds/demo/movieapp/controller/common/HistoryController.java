package sparkminds.demo.movieapp.controller.common;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparkminds.demo.movieapp.service.HistoryService;
import sparkminds.demo.movieapp.service.dto.request.CreateHistoryRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateHistoryRequestDto;
import sparkminds.demo.movieapp.service.dto.response.HistoryResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/common/history")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/{idHistory}")
    public ResponseEntity<HistoryResponseDto> getHistoryById(@PathVariable UUID idHistory) {
        return ResponseEntity.ok(historyService.getHistoryByIdUser(idHistory));
    }

    @GetMapping
    public ResponseEntity<List<HistoryResponseDto>> getAllHistories() {
        return ResponseEntity.ok(historyService.getAllHistory());
    }

    @PostMapping
    public ResponseEntity<Void> addHistory(@Valid @RequestBody CreateHistoryRequestDto requestDto) {
        historyService.addHistory(requestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateHistory(@Valid @RequestBody UpdateHistoryRequestDto updateHistoryRequestDto) {
        historyService.updateHistory(updateHistoryRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idHistory}")
    public ResponseEntity<Void> deleteHistoryById(@Valid @PathVariable UUID idHistory) {
        historyService.deleteHistoryById(idHistory);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/all")
    public ResponseEntity<Void> deleteAllHistories() {
        historyService.deleteAllHistory();
        return ResponseEntity.noContent().build();
    }

}
