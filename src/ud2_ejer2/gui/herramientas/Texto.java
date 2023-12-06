/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import ud2_ejer2.gui.ventanas.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Herramienta para dibujar texto. 
 * Su funcionameinto es:
 * -Al pulsarse el raton se guarda la imagen actual del lienzo en el buffer temporal y se lanza el dibujado
 * -Al arrastrase el ratón se redibuja el bufer temporal en el lienzo y se superpone 
 *  el dibujo del texto permitiendo ver de forma actualizada donde se va a colocar 
 * 
 * @see Herramienta
 * @author Jose Javier BO
 */
public class Texto extends Herramienta {

    /**
     * String a dibujar
     */
    String texto;
    
    /**
     * Fuente a utilizar (es calculada en el metodo getParametros())
     */
    Font fuente;
 

     /**
     * Constructor
     * @param ventanaPrincipal Referencia a la ventana princiapl
     * @param lienzo Referencia al lienzo donde dibujar
     */
    public Texto(VentanaPrincipal ventanaPrincipal, Lienzo lienzo) {
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
     * Al pulsar el boton se guarda el lienzo en el buffer temporal y se dibuja el texto
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
     * en el buffer temporal y luego superpone el texto
     * 
     * @param punto posicion del raton
     */
    private void dibujar(Point punto) {
        
        //coordenadas para el gradiente
        x1=punto.x;
        y1=(int) (punto.y-fuente.getSize()/1.5);
        x2=x1+fuente.getSize()*5;
        y2=punto.y;
        
        //imagen original
        pintarBufferTemporalEnLienzo();
        
        //dibujar texto
        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);
        //dibujar texto
        g.drawString(texto, punto.x, punto.y); 

        lienzo.repaint();
    }

    
    /**
     * Ademas de los parametros comunes de dibujado recoge tambien los parametros
     * especificos del texto(fuente, estilo tamaño, texto a escribir...)
     * @return True si los ha recogido, False si no los ha recogido
     */
    @Override
    protected boolean getParametros() {
        if (!super.getParametros()) {
            return false;
        }
        texto = vp.inputTexto.getText();
        if (texto.length()<1){
            vp.msgError("No se puede dejar el texto vacío");
            return false;
        }
        int tamano =1;
        try {
            tamano = Integer.parseInt(vp.inputTextoTamano.getText());
            if (tamano<1){
                vp.msgError("El tamaño debe ser al menos 1");
                return false;
            }
        } catch (NumberFormatException ex) {
            vp.msgError("Tamaño de texto no válido");
            return false;
        }
            
        String estiloStr= vp.inputTextoEstilo.getSelectedItem().toString();
        String nombreFuente= vp.inputTextoFuente.getSelectedItem().toString();
        
        
        int estilo;
        switch (estiloStr) {
            case "Italica" -> estilo=Font.ITALIC;
            case "Negrita" -> estilo=Font.BOLD;
            case "Italica y Negrita"-> estilo = Font.BOLD | Font.ITALIC;
            default -> estilo=Font.PLAIN;
        }
        
        /**
         * Instanciar fuente
         */
        fuente = new Font(nombreFuente, estilo, tamano);
        
        
        return true;
    }

    /**
     * Ademas de estalbecer los parametros comunes etablece el tipo de letra
     * @param g 
     */
    @Override
    protected void setParametrosDibujo(Graphics2D g) {
        super.setParametrosDibujo(g);
        g.setFont(fuente);
    }
    
    
    
}
