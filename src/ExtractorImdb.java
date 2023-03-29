import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractorImdb implements Extrator {
    public List<Content> transform(String response){
        var jsonToList = new JsonToListMap();
        List<Map<String, String>> list = jsonToList.parse(response);
        List<Content> conteudos = new ArrayList<>();
        for (Map<String,String> item : list) {
            String title = item.get("title")
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll(" ", "_");
            String regex = "._V1.*$";
            String urlImage = item.get("image").replaceAll(regex,"._V1_UX512_CR0,3,512,704_AL_.jpg");            
            String font = "IMDB";
            double rank = Double.parseDouble(item.get("rank"));
            String description = classification(rank);
            var conteudo = new Content(title, urlImage, font, description);
            conteudos.add(conteudo);           
        }
        return conteudos;
    }

    private String classification(double rank) {
        String description;
        if(rank < (250*1/3) ) {            
            description = Description.MUITO_BOM.getDesc();
        } else if (rank < (250*2/3) ) {
            description = Description.MAIS_OU_MENOS.getDesc();
        } else {
            description = Description.EHHHHHH.getDesc();
        }
        return description;
    }
}