package sparkminds.demo.movieapp.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sparkminds.demo.movieapp.entity.History;
import sparkminds.demo.movieapp.service.dto.request.CreateHistoryRequestDto;
import sparkminds.demo.movieapp.service.dto.request.UpdateHistoryRequestDto;
import sparkminds.demo.movieapp.service.dto.response.HistoryResponseDto;

@Component
public class HistoryMapper {
    public History toHistory(UpdateHistoryRequestDto updateHistoryRequestDto) {
        History history = History.builder().build();
        BeanUtils.copyProperties(updateHistoryRequestDto, history);
        return history;
    }

//    public History toHistory(CreateHistoryRequestDto createHistoryRequestDto) {
//        History history = History.builder().build();
//        BeanUtils.copyProperties(createHistoryRequestDto, history);
//        return history;
//    }

    public HistoryResponseDto toHistoryResponseDto(History history) {
        HistoryResponseDto historyResponseDto = HistoryResponseDto.builder().build();
        BeanUtils.copyProperties(history, historyResponseDto);
        return historyResponseDto;
    }

}
