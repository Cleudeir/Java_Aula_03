import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractorImdb implements Extrator {
    public List<Conteudo> transform(String response){
        var jsonToList = new JsonToListMap();
        List<Map<String, String>> list = jsonToList.parse(response);
        List<Conteudo> conteudos = new ArrayList<>();
        for (Map<String,String> item : list) {
            String title = item.get("title")
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll(" ", "_");
            String regex = "._V1.*$";
            String urlImage = item.get("image").replaceAll(regex,"._V1_UX512_CR0,3,512,704_AL_.jpg");            
            String font = "IMDB";
            String description;
            double rank = Double.parseDouble(item.get("rank"));
            description = classificação(rank);
            var conteudo = new Conteudo(title, urlImage, font, description);
            conteudos.add(conteudo);           
        }
        return conteudos;
    }

    private String classificação(double rank) {
        String retorno;
        if(rank < (250*1/3) ) {            
            retorno = Descricao.MUITO_BOM.getDesc();
        } else if (rank < (250*2/3) ) {
            retorno = Descricao.MAIS_OU_MENOS.getDesc();
        } else {
            retorno = Descricao.EHHHHHH.getDesc();
        }
        return retorno;
    }
}