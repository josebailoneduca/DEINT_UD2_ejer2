/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Arc2D;
import ud2_ejer2.gui.ventanas.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Herramienta para dibujar arco. 
 * Su funcionameinto es:
 * -Al pulsarse el raton se guarda la imagen actual del lienzo en el buffer temporal y se lanza el dibujado
 * -Al arrastrase el ratón se redibuja el bufer temporal en el lienzo y se superpone el dibujo del arco
 *  permitiendo ver de forma actualizada donde se va a colocar el arco
 *
 * @see Herramienta
 * @author Jose Javier BO
 */
public class Arco extends Herramienta {


    /**
     * Angulo inicial del arco
     */
    int inicio = 0;

    /**
     * Grados que abarca el angulo
     */
    int angulo = 0;
    
    int cierre = Arc2D.OPEN;
     
    /**
     * Constructor
     *
     * @param ventanaPrincipal Referencia a la ventana principal desde la que
     * coger los parametros
     * @param lienzo Referencia al lienzo de dibujo
     */
    public Arco(VentanaPrincipal ventanaPrincipal,Lienzo lienzo) {
        super(ventanaPrincipal,lienzo);
    }

    /**
     * Al arrastrar se lanza el dibujado
     * @param punto posicion del raton
     */
    @Override
    public void mouseDragged(Point punto) {
        //dibujar el arco
        dibujar(punto);
    }

    
    /**
     * Al pulsar se guarda en el buffer el dibujo original y se lanza el dibujado del arco
     * @param punto Punto con las coordenadas a usara en el dibujo
     */
    @Override
    public void mousePressed(Point punto) {
        //refrescar parametros
        if (!getParametros()) {
            return;
        }
      
        //guardar la imagen original en el buffer
        guardarLienzoEnBufferTemporal();
        //digujar el arco
        dibujar(punto);
    }



    /**
     * Dibuja el elemento limpiando el lienzo, redibujando la imagen original y luego dibujando el arco
     * @param punto Coordenadas para el dibujo
     */
    private void dibujar(Point punto) {
         // Actualizar los parametros de dibujado
        if (!getParametros()) {
            return;
        }
        
        //definir punto degradado en rectangulo que ocupa
        x1=punto.x-ancho/2;
        y1=punto.y-alto/2;
        x2=x1+ancho;
        y2=y1+alto;
        
        
        //limpiar lienzo
        Graphics2D g = lienzo.getBufferG2D();
        //Refrescar los parametros de dibujo en g
        setParametrosDibujo(g);
        
        //pintar la imagen original
        pintarBufferTemporalEnLienzo();
      
        //dibujar angulo
        Arc2D arco = new Arc2D.Double(x1, y1, ancho, alto, inicio, angulo,cierre);
        if (soloBorde) {
            g.draw(arco);
        } else {
            g.fill(arco);
        }
        lienzo.repaint();
    }
    
    
    /**
     * Ademas de recoger los parametros comunes recoge los parametros especificos del arco
     * @return True si se han podido coger los parametros. False si ha habido algun error
     */
    @Override
    protected boolean getParametros() {
        if (!super.getParametros()) {
            return false;
        }
        try {
            inicio = Integer.parseInt(vp.inputArcoInicio.getText());
        } catch (NumberFormatException ex) {
            vp.msgError("Inicio del arco no válido");
            return false;
        }

        try {
            angulo = Integer.parseInt(vp.inputArcoAngulo.getText());
        } catch (NumberFormatException ex) {
            vp.msgError("Angulo del arco no válido");
            return false;
        }
        switch(vp.inputArcoCierre.getSelectedIndex()){
            case 0 -> cierre=Arc2D.OPEN;
            case 1 -> cierre=Arc2D.CHORD;
            case 2 -> cierre=Arc2D.PIE;
    }
        
        return true;
    }
}
