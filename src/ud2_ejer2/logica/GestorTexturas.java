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
import javax.imageio.ImageIO;

/**
 * Clase que se encarga de gestionar imagenes, sus nombres y generar sus miniaturas
 * 
 * Usado para tener un listado de texturas a usar.
 * @author Jose Javier Bailon Ortiz
 */
public class GestorTexturas {

    /**
     * Lista de imagenes de texturas
     */
    ArrayList<BufferedImage> texturas = new ArrayList<BufferedImage>();
    
    /**
     * Lista de las miniaturas de las texturas
     */
    ArrayList<BufferedImage> miniaturas = new ArrayList<BufferedImage>();
    
    /**
     * Lista de nombres de las texturas
     */
    ArrayList<String> nombresTexturas = new ArrayList<String>();

    
    
    
    /**
     * Construxtor. Añade a la lista de texturas la opcion "Cargar archivo" como
     * primera opcion de la lista de texturas
     */
    public GestorTexturas() {
        texturas.add(null);
        miniaturas.add(null);
        nombresTexturas.add("Cargar archivo..");
        crearTexturas();
    }

    
    /**
     * Agrega una textura a la lista
     * @param imagen La imagen
     * @param nombre El nombre
     * @return  El indice de la lista de texturas que le corresponde
     */
    public int addTextura(BufferedImage imagen, String nombre) {
        texturas.add(imagen);
        nombresTexturas.add(nombre);
        miniaturas.add(getMiniatura(imagen));
        return texturas.size() - 1;
    }
 

    /**
     * Devuelve la lista de nombres de las texturas
     * @return  Los nombres
     */
    public ArrayList<String> getNombresTexturas() {
        return nombresTexturas;
    }

    /**
     * Devuelve la miniatura asociada a un indice de textura
     * @param i El indice de la textura
     * @return  La imagen miniatura
     */
    public BufferedImage getMiniatura(int i) {
        return miniaturas.get(i);
    }

    /**
     * Crea las texturas iniciales. Una textura de cuadros y otra de circulos
     */
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

    
    /**
     * Genera una miniatura de 32x32 a partir de una imagen
     * @param imagen La imagen a usar para generar la miniatura
     * @return La miniatura
     */
    private BufferedImage getMiniatura(BufferedImage imagen) {

        //Calcular las coordenadas y dimensiones para generar la iminiatura
        float ancho = imagen.getWidth();
        float alto = imagen.getHeight();
        float anchoFinal = ancho;
        float altoFinal = alto;
        float proporcion = 1;
        //calcular proporcion de la imagen
        if (anchoFinal > altoFinal) {
            proporcion = 32 / ancho;
        } else {
            proporcion = 32 / alto;
        }
        
        //calcular resolucion final
        altoFinal = (int) (alto * proporcion);
        anchoFinal = (int) (ancho * proporcion);

        //desfase para centrar
        int x = (int) ((32 - anchoFinal) / 2);
        int y = (int) ((32 - altoFinal) / 2);
    
        //generar la miniatura
        BufferedImage miniatura = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) miniatura.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 32, 32);
        g.drawImage(imagen, x, y, (int) anchoFinal, (int) altoFinal, null);
        return miniatura;
    }

    
    
    /**
     * Devuelve la textura asociada a un indice
     * @param i El indice de la textura
     * @return  La textura
     */
    public BufferedImage getTextura(int i) {
        return texturas.get(i);
    }

    /**
     * Carga un archivo de imagen a partir de una ruta. Si lo carga genera una textura 
     * y devuelve el indice de textura que le corresponede. Si no se puede cargar devuleve -1
     * @param rutaArchivo Ruta al archivo a cargar
     * @return  El indice que le corresponde a la textura cargada o -1 si no se carga
     */
    public int cargarArchivo(File rutaArchivo) {
        try {
            BufferedImage img = ImageIO.read(rutaArchivo);
            if (img!=null)
            return addTextura(img, rutaArchivo.getName());
        } catch (IOException ex) {
        }
            return -1;
    }
}//end GestorTexturas

