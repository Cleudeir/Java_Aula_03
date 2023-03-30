import java.util.List;

public interface Extractor {
    List<Content> transform(String response);
}
