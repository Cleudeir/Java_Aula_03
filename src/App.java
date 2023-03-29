import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        String urlImdb = "https://gist.githubusercontent.com/lucasfugisawa/cbb0d10ee3901bd0541468e431c629b3/raw/1fe1f3340dfe5b5876a209c0e8226d090f6aef10/Top250Movies.json";

        String urlNasa = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/NASA-APOD.json";

        var request = new ClienteHttp();
        String response = request.get(urlNasa);
        Extrator Extractor = new ExtractorNasa();
        List list = Extractor.transform(response);
        stickerGenerator gerador = new stickerGenerator();
        for (int i = 0; i < list.size(); i++) {
            Content conteudo = (Content) list.get(i);
            InputStream urlImage = new URL(conteudo.getUrlImage()).openStream();            
            gerador.create(urlImage, conteudo.getTitle(), conteudo.getFont(),  conteudo.getDescription());
            System.out.println(conteudo.getTitle() + " " + (i + 1) + "/" + list.size());
        }
    }
}