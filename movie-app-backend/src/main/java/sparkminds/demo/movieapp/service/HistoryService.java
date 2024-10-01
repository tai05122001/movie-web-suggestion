package sparkminds.demo.movieapp.service;

import sparkminds.demo.movieapp.entity.Movie;
import sparkminds.demo.movieapp.service.dto.request.CreateHistoryRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateHistoryRequestDto;
import sparkminds.demo.movieapp.service.dto.response.HistoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface HistoryService {
    HistoryResponseDto getHistoryByIdUser(UUID idUser);
    List<HistoryResponseDto> getAllHistory();
    void addHistory(CreateHistoryRequestDto history);
    void updateHistory(UpdateHistoryRequestDto history);
    void deleteHistoryById(UUID idUser);
    void deleteAllHistory();
}
