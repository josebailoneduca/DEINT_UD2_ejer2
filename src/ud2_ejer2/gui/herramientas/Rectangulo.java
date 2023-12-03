/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;

import java.awt.Graphics2D;
import java.awt.Point;
import ud2_ejer2.gui.ventanas.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Herramienta para dibujar rectangulo. 
 * Su funcionameinto es:
 * -Al pulsarse el raton se guarda la imagen actual del lienzo en el buffer temporal y se lanza el dibujado
 * -Al arrastrase el ratón se redibuja el bufer temporal en el lienzo y se superpone el dibujo del rectangulo
 *  permitiendo ver de forma actualizada donde se va a colocar el rectangulo
 * 
 * @see Herramienta
 * @author Jose Javier BO
 */
public class Rectangulo extends Herramienta {


    /**
     * Constructor
     * @param ventanaPrincipal Referencia a la ventana princiapl
     * @param lienzo Referencia al lienzo donde dibujar
     */
    public Rectangulo(VentanaPrincipal ventanaPrincipal ,Lienzo lienzo) {
        super(ventanaPrincipal,lienzo);
    }

     /**
     * Al arrastrar se redibuja el buffer temporal en el lienzo y se superpone el rectangulo
     * @param punto posicion del raton
     */
    @Override
    public void mouseDragged(Point punto) {
        if (!getParametros())
            return;
        
        dibujar(punto);
    }

    
    /**
     * Al pulsar el boton se guarda el lienzo en el buffer temporal y se dibuja el rectangulo
     * @param punto posicion del raton
     */
    @Override
    public void mousePressed(Point punto) {
        if (!getParametros()) {
            return;
        }
        guardarLienzoEnBufferTemporal();
        dibujar(punto);
    }

    
     /**
     * Ejecuta el dibujado. Primero limpia el lienzo. Redibuja sobre el lo almacenado 
     * en el buffer temporal y luego superpone el rectangulo.
     * 
     * @param punto posicion del raton
     */
    private void dibujar(Point punto) {
        pintarBufferTemporalEnLienzo();
        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);
        //dibujar rectangulo
        if (soloBorde) {
            g.drawRect(punto.x-ancho/2, punto.y-alto/2, ancho, alto);
        } else {
            g.fillRect(punto.x-ancho/2, punto.y-alto/2, ancho, alto);
        }
        lienzo.repaint();    }
 

}
