package sparkminds.demo.movieapp.controller.common;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparkminds.demo.movieapp.service.HandleDataExcelFileService;

@RestController
@RequestMapping("/api/common/excel")
@RequiredArgsConstructor
public class HandleDataExcelFileController {
    private final HandleDataExcelFileService handleDataExcelFileService;

    @GetMapping
    public ResponseEntity<Boolean> handleDataExcelFile() {
        handleDataExcelFileService.handleDataExcelFile("xlsx/data.xlsx");
        return ResponseEntity.ok().build();
    }
}
