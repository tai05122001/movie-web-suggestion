package sparkminds.demo.movieapp.service;

import java.util.List;

public interface HuggingFaceService {
    String calculateSimilarStrings(String description, List<String> calculateSimilarStrings);
    String calculateSimilarStringsLocal(String description, List<String> calculateSimilarStrings);
}
