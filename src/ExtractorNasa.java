import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractorNasa implements Extrator {
    public List<Content> transform(String response){
        var jsonToList = new JsonToListMap();
        List<Map<String, String>> list = jsonToList.parse(response);
        List<Content> conteudos = new ArrayList<>();
        for (Map<String,String> item : list) {
            String title = item.get("title")
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll(" ", "_");
            String urlImage = item.get("url");            
            String font = "NASA :" + item.get("copyright") ;
            String  description = item.get("date");
            var conteudo = new Content(title, urlImage, font, description);
            conteudos.add(conteudo);           
        }
        return conteudos;
    }
}