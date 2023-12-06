/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;

import java.awt.Graphics2D;
import java.awt.Point;
import ud2_ejer2.gui.componentes.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Herramienta para dibujar rectangulo redondo. 
 * Su funcionameinto es:
 * -Al pulsarse el raton se guarda la imagen actual del lienzo en el buffer temporal y se lanza el dibujado
 * -Al arrastrase el ratón se redibuja el bufer temporal en el lienzo y se superpone 
 *  el dibujo del rectangulo redondo permitiendo ver de forma actualizada donde se va 
 *  a colocar el rectangulo redondo
 * 
 * @see Herramienta
 * @author Jose Javier BO
 */
public class RectanguloRedondo extends Herramienta {

    
    
    /**
     * Define si se esta dibujando o no
     */
    boolean dibujando=false;
    
    /**
     * Ancho del arco de la esquina del rectangulo
     */
    int anchoCirculo = 0;
    
    /**
     * Alto del arco de la esquina del rectangulo
     */
    int altoCirculo = 0;

     /**
     * Constructor
     * @param ventanaPrincipal Referencia a la ventana princiapl
     * @param lienzo Referencia al lienzo donde dibujar
     */
    public RectanguloRedondo(VentanaPrincipal ventanaPrincipal, Lienzo lienzo) {
        super(ventanaPrincipal, lienzo);
    }

    
     /**
     * Al arrastrar se lanza el dibujado
     * @param punto posicion del raton
     */
    @Override
    public void mouseDragged(Point punto) {
        if (!getParametros()) {
            return;
        }
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
        dibujando=true;
        guardarLienzoEnBufferTemporal();
        dibujar(punto);
    }

    
        /**
     * Al soltar el boton se marca que no se esta dibujando
     * @param punto 
     */
    @Override
    public void mouseReleased(Point punto) {
        dibujando=false;
    }

    /**
     * Ejecuta el dibujado. Primero limpia el lienzo. Redibuja sobre el lo almacenado 
     * en el buffer temporal y luego superpone el rectangulo redondo.
     * 
     * @param punto posicion del raton
     */
    private void dibujar(Point punto) {
        
        if (!dibujando)
            return;
        
        //coordenadas para el gradiente
        x1=punto.x-ancho/2;
        y1=punto.y-alto/2;
        x2=x1+ancho;
        y2=y1+alto;
        
        pintarBufferTemporalEnLienzo();

        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);
        //dibujar rectangulo
        if (soloBorde) {
            g.drawRoundRect(punto.x-ancho/2, punto.y-alto/2, ancho, alto, anchoCirculo, altoCirculo);
        } else {
            g.fillRoundRect(punto.x-ancho/2, punto.y-alto/2, ancho, alto, anchoCirculo, altoCirculo);
        }
        lienzo.repaint();
    }

    
    /**
     * Ademas de los parametros comunes de dibujado recoge tambien los parametros
     * especificos del rectángulo redondeado
     * @return True si los ha recogido, False si no los ha recogido
     */
    @Override
    protected boolean getParametros() {
        if (!super.getParametros()) {
            return false;
        }
        try {
            anchoCirculo = Integer.parseInt(vp.inputRectRedAnchoCirculo.getText());
        } catch (NumberFormatException ex) {
            vp.msgError("Ancho de circulo no válido");
            return false;
        }

        try {
            altoCirculo = Integer.parseInt(vp.inputRectRedAltoCirculo.getText());
        } catch (NumberFormatException ex) {
            vp.msgError("Alto de circulo no válido");
            return false;
        }
        return true;
    }
}
