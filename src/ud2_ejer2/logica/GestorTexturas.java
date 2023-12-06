/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.logica;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class GestorTexturas {

    ArrayList<BufferedImage> texturas = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> miniaturas = new ArrayList<BufferedImage>();
    ArrayList<String> nombresTexturas = new ArrayList<String>();

    public GestorTexturas() {
        texturas.add(null);
        miniaturas.add(null);
        nombresTexturas.add("Cargar archivo..");
        crearTexturas();
    }

    public int addTextura(BufferedImage imagen, String nombre) {
        texturas.add(imagen);
        nombresTexturas.add(nombre);
        miniaturas.add(getMiniatura(imagen));
        return texturas.size() - 1;
    }

    public ArrayList<BufferedImage> getTexturas() {
        return texturas;
    }

    public ArrayList<String> getNombresTexturas() {
        return nombresTexturas;
    }

    public BufferedImage getMiniatura(int i) {
        return miniaturas.get(i);
    }

    private void crearTexturas() {
        //cuadrados
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 50, 50);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 25, 25);
        g.fillRect(26, 26, 50, 50);
        addTextura(img, "Cuadrados");

        //puntos
        BufferedImage imgp = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gp = (Graphics2D) imgp.createGraphics();
        gp.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        gp.setColor(Color.BLACK);
        gp.fillRect(0, 0, 50, 50);
        gp.setColor(Color.WHITE);
        gp.fillOval(-10, -10, 20, 20);
        gp.fillOval(-10, 40, 20, 20);
        gp.fillOval(40, 40, 20, 20);
        gp.fillOval(40, -10, 20, 20);
        gp.fillOval(15, 15, 20, 20);

        addTextura(imgp, "Puntos");
    }

    private BufferedImage getMiniatura(BufferedImage imagen) {

        BufferedImage miniatura = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) miniatura.createGraphics();

        float ancho = imagen.getWidth();
        float alto = imagen.getHeight();
        float anchoFinal = ancho;
        float altoFinal = alto;
        float proporcion = 1;
        if (anchoFinal > altoFinal) {
            proporcion = 32 / ancho;
        } else {
            proporcion = 32 / alto;
        }
        altoFinal = (int) (alto * proporcion);
        anchoFinal = (int) (ancho * proporcion);

        int x = (int) ((32 - anchoFinal) / 2);
        int y = (int) ((32 - altoFinal) / 2);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 32, 32);
        g.drawImage(imagen, x, y, (int) anchoFinal, (int) altoFinal, null);
        return miniatura;
    }

    public BufferedImage getTextura(int i) {
        return texturas.get(i);
    }

    public int cargarArchivo(File rutaArchivo) {
        try {
            BufferedImage img = ImageIO.read(rutaArchivo);
            return addTextura(img, rutaArchivo.getName());
        } catch (IOException ex) {
            return -1;
        }
    }
}//end GestorTexturas

