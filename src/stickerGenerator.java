import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class stickerGenerator {
    public void create(InputStream inputStream, String name , String font, String description ){
        try {
            // Imagem original
            BufferedImage origin = ImageIO.read(inputStream);
            int width = 512;
            int height = 512 * origin.getHeight() / origin.getWidth();
            double correction =  height  < width ? ((double) height ) / ((double) width) : 1.0;
            System.out.println(correction);
            final double Increment = height * 0.1;
            int heightIncrement = height + (int) Increment;

            // Imagem nova transparente
            BufferedImage newImage =  new BufferedImage(width, heightIncrement, BufferedImage.TRANSLUCENT);
            Graphics2D graphics = (Graphics2D) newImage.getGraphics();
            graphics.drawImage(origin, 0, 0,width, height, null);

            // Imagem marca d'agua Alura
            String imgAlura = "public/logo/Alura.png";
            BufferedImage logoAlura = ImageIO.read(new File(imgAlura));
            int widthAlura = (int) (width * correction  * 0.15 * 3.56);
            int heightAlura = (int) (width * correction * 0.15 * 1);
            graphics.drawImage(logoAlura, 0, 0,  widthAlura, heightAlura, null);

            // Imagem marca minha foto
            String imgEu = "public/logo/Eu.png";
            BufferedImage logoEu = ImageIO.read(new File(imgEu));
            int widthEu = (int) (width * correction  * 0.35 * 1);
            int heightEu = (int) (width * correction * 0.35 * 1.66);
            graphics.drawImage(logoEu, 0, height - heightEu,  widthEu, heightEu,null);

            // Inserção da descrição
            var fontDescription = new Font("Impact" ,Font.BOLD, (int) Increment);
            graphics.setFont(fontDescription);
            graphics.setColor(Color.GREEN);
            FontMetrics metricsDescription = graphics.getFontMetrics(fontDescription);
            int widthTextDescription = graphics.getFontMetrics().stringWidth(description);
            int heightTextDescription = metricsDescription.getHeight();
            int wordPositionXDescription  = width/2 - widthTextDescription/2;
            int wordPositionYDescription  = (int) (height + Increment/4 + heightTextDescription/2);
            graphics.drawString(description, wordPositionXDescription, wordPositionYDescription);

            // Inserção da fonte
            var fontFont = new Font("Comic Sans" ,Font.BOLD, (int) Increment * 3/5);
            graphics.setFont(fontFont);
            graphics.setColor(Color.CYAN);
            FontMetrics metricsFont = graphics.getFontMetrics(fontFont);
            int widthTextFont = graphics.getFontMetrics().stringWidth(font);
            int heightTextFont = metricsFont.getHeight();
            int wordPositionXFont = width - widthTextFont - 20;
            int wordPositionYFont = (int) heightTextFont;
            graphics.drawString(font, wordPositionXFont, wordPositionYFont);

            // Inserção Contorno no texto da descrição
            Outline(description, width, graphics, fontDescription, wordPositionXDescription, wordPositionYDescription);
 
            // Inserção Contorno no texto da Font
            Outline(font, width, graphics, fontFont, wordPositionXFont, wordPositionYFont);
  
            // Salvar Imagem
            String pathOutput = "public/output/";

            File file = new File(pathOutput);
            if(!file.exists()){
                Path path = Paths.get(pathOutput);
                Files.createDirectory(path);             
            }
            ImageIO.write(newImage, "png", new File( pathOutput + name + ".png")); 

        } catch (IOException e) {   
            e.printStackTrace(); 
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
        }
    }

    private void Outline(String text, int width, Graphics2D graphics, Font font,
        int wordPositionX, int wordPositionY) {
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(text, font, fontRenderContext);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(wordPositionX, wordPositionY);
        graphics.setTransform(transform);
        var outlineStroke = new BasicStroke(width * 0.003f);
        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);
    }
}
