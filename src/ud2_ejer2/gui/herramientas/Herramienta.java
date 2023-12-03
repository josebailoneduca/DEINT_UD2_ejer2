/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import ud2_ejer2.gui.ventanas.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Clase básica de herramienta. El resto de herramientas extienden de esta.
 * 
 * Contiene variables y métodos para recopilar los parametros comunes de dibujo.
 * 
 * 
 * @author Jose Javier BO
 */
abstract public class Herramienta {

    /**
     * Referencia al lienzo donde eibujar
     */
    protected Lienzo lienzo;
    
    /**
     * Referencia a la ventana principal de la que coger los parametros de dibujado
     */
    protected VentanaPrincipal vp;
    
    /**
     * Imagen en memoria para usarse como buffer temporal si la herramienta lo necesita
     */
    BufferedImage tmpBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    //parametros generales
    Color color; // color de dibujado
    float grosor; // grosor de trazo a usar
    Stroke trazo;// objeto trazo preparado para usar
    int ancho;// ancho comun para dibujar figuras
    int alto;// alto comun para dibujar fituras
    boolean soloBorde;

    /**
     * Constructor
     * @param vp Referencia a la ventana principal de la que coger los parametros de dibujado
     * @param lienzo  Referencia al lienzo en el que dibujar
     */
    public Herramienta(VentanaPrincipal vp, Lienzo lienzo) {
        this.vp = vp;
        this.lienzo=lienzo;
    }

 
  
    /**
     * Recoge los parametros comunes de dibujado desde la ventana principal
     *
     * @return True si los ha recogido False si no los ha recogido
     */
    protected boolean getParametros() {

        color = vp.panelColorSeleccionado.getBackground();
        try {
            grosor = Float.parseFloat(vp.inputComunGrosor.getText());
            trazo = new BasicStroke(grosor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        } catch (NumberFormatException ex) {

        } catch (IllegalArgumentException ex) {
            vp.msgError("Grosor no válido");
            return false;
        }
        try {
            ancho = Integer.parseInt(vp.inputComunAncho.getText());
        } catch (NumberFormatException ex) {
            vp.msgError("Ancho no válido");
            return false;
        }
        try {
            alto = Integer.parseInt(vp.inputComunAlto.getText());
        } catch (NumberFormatException ex) {
            vp.msgError("Alto no válido");
            return false;
        }
        soloBorde = vp.buttonGroupRelleno.getSelection().getActionCommand().equals("soloborde");

        return true;
    }

    /**
     * Actualiza los parametros color y pincel de dibujado del Graphics2D.
     * Tambien activa el antialiasing en el contexto grafico.
     *
     * @param g Graphics2D a usar
     */
    protected void setParametrosDibujo(Graphics2D g) {
        g.setColor(color);
        g.setStroke(trazo);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    
    
    
    //receptores de ordenes relativas a acciones tipicas del ratón
    public void mouseMoved(Point punto) {
    }

    public void mouseClicked(Point punto) {
    }

    public void mouseDragged(Point punto) {
    }

    public void mousePressed(Point punto) {
    }

    public void mouseReleased(Point punto) {
    }

    public void mouseEntered(Point punto) {
    }

    public void mouseExited(Point point) {
    }

    
    
    /**
     * Guarda el dibujo actual del lienzo en el buffer temporal
     */
    public void guardarLienzoEnBufferTemporal() {
        //guardar imagen original en buffer temporal
        tmpBuffer = new BufferedImage(lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight(), BufferedImage.TYPE_INT_ARGB);
        tmpBuffer.getGraphics().drawImage(lienzo.getBuffer(), 0, 0, null);
    }

    /**
     * Dibuja el contenido del buffer temporal en el lienzo
     */
    public void pintarBufferTemporalEnLienzo() {
        Graphics2D g = lienzo.getBufferG2D();
        //limpiar lienzo
        g.setBackground(new Color(255, 255, 255, 0));
        g.clearRect(0, 0, lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight());
        //dibujar la imagen original en el lienzo
        g.drawImage(tmpBuffer, 0, 0, null);
    }

    
    
    
    /**
     * Realiza las acciones necesarias cuando se desactiva la herramienta
     */
    public void desactivar() {
    }

    /**
     * Realiza las acciones necesarias cuando se activa la herramienta
     */
    public void activar() {
    }

}
