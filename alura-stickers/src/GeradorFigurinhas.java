import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradorFigurinhas {

    public void criar(InputStream inputStream, String nomeArquivo, String texto) throws Exception {
        
        //InputStream inputStream = new FileInputStream(new File("src/img/shawshankRedemption.jpg"));
        //InputStream inputStream = new URL("https://imersao-java-apis.s3.amazonaws.com/TopMovies_1.jpg").openStream();
        
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();

        int novaAltura = altura + 200;

        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, imagemOriginal.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);
        
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 64);
        graphics.setColor(Color.red);
        graphics.setFont(fonte);

        graphics.drawString("Classificação:", (int) (largura * 0.2), novaAltura - 100);
        graphics.drawString(texto, (int) (largura * 0.4), novaAltura - 50);

        
        // Stroke strokeOriginal = graphics.getStroke();
        graphics.setColor(Color.yellow);
        graphics.setStroke(new BasicStroke(10));
        graphics.drawRect(0, 0, largura, novaAltura);
        // graphics.setStroke(strokeOriginal);

        ImageIO.write(novaImagem, "png", new File("src/imgTratadas/" + nomeArquivo + ".png"));
    }
}
