import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class App {

    static String token;

    static List<Map<String, String>> getJson(String url, String token) throws Exception {
        String urlComToken = url + token;
        URI endereco = URI.create(urlComToken);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        
        var parser = new JsonParser();
        List<Map<String, String>> bodyRequest = parser.parse(body);

        return bodyRequest;
    }

    static void listaTop250Filmes() throws Exception {
        
        //List<Map<String, String>> body = getJson("https://imdb-api.com/en/API/Top250Movies/", token);
        List<Map<String, String>> body = getJson("https://api.mocki.io/v2/549a5d8b", "");

        var geradorFigurinhas = new GeradorFigurinhas();

        for (Map<String, String> filme : body) {
            String urlImagem = filme.get("image");
            InputStream inputStream = new URL(urlImagem).openStream();

            String nomeArquivo = filme.get("title");

            
            System.out.println("Titulo: " + filme.get("title"));
            System.out.println("Imagem: " + filme.get("image"));
            
            int rating = Math.round(Float.parseFloat(filme.get("imDbRating")));
            System.out.print("Avaliacao: ");
            String texto = "";
            for (int star = 0; star < rating; star++) {
                System.out.print("⭐"); 
                texto = texto + "⭐";
                               
            }

            geradorFigurinhas.criar(inputStream, nomeArquivo, texto);

            System.out.println();
            System.out.println("-".repeat(30));
        }
    }

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("src/properties/config.properties");
        props.load(file);
        token = props.getProperty("prop.token");

        listaTop250Filmes();
    }
}

//TO-DO
//- Mudar jsonParser para uma biblioteca jackson ou gson
//- Criar forma de avaliar
//-- Buscar de um arquivo de config ou digitado