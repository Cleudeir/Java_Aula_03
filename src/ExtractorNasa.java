import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractorNasa implements Extractor {
    public List<Content> transform(String response){
        var jsonToList = new JsonToListMap();
        List<Map<String, String>> list = jsonToList.parse(response);
        List<Content> contents = new ArrayList<>();
        list.stream().forEach(item-> {
            String title = item.get("title")
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll(" ", "_");
            String urlImage = item.get("url");            
            String font = "NASA :" + item.get("copyright") ;
            String  description = item.get("date");
            var content = new Content(title, urlImage, font, description);
            contents.add(content);           
        });
        return contents;
    }
}