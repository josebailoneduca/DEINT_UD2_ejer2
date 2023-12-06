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
import ud2_ejer2.gui.componentes.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Controla la herramienta de dibujado libre
 * Su funcionamiento es:
 * - Al entrar el raton sobre el lienzo se guarda en el buffer temporal el dibujo actual del lienzo
 * - Al moverse el raton se dibuja en el lienzo lo que hay en el buffer mas una circunferencia de referencia del grosor de trazo
 * - Al hacerse click se dibuja un óvalo en el lienzo  y se guarda el resultado en el buffer (permitiendo hacer puntos)
 * - En el mousepressed se guarda la posición de donde esta el raton y cuango se hace 
 *    un mousedrag se pinta en el lienzo lo que hay en el buffertemporal y una linea desde la posición anterior a la nueva
 * - Al soltarse boton del raton se guarda el estado actual del lienzo en el buffer temporal
 * - Cuando el raton sale del lienzo se repinta en el lienzo el contenido del buffer temporal por si hay que borrar la referncia de grosor
 * 
 * @see Herramienta
 * @author Jose Javier BO
 */
public class DibujoLibre extends Herramienta {

    /**
     * Almacena la ultima coordenada de dibujo usada
     */
    private Point antes;
    
    /**
     * True si se esta dibujando, falso si no se esta dibujando
     */
    private boolean pintando = false;

    /**
     * Constructor
     * @param ventanaPrincipal Referencia a la ventana principal
     * @param lienzo Refrencia al lienzo en el que dibujar
     */
    public DibujoLibre(VentanaPrincipal ventanaPrincipal,Lienzo lienzo) {
        super(ventanaPrincipal,lienzo);
    }

    /**
     * Cuando el boton se mueve se hacen las siguienes acciones:
     * - Redibujar en el lienzo la imagen guardada en el buffer termporal
     * - Dibujar el circulo de referencia del grosor de dibujado
     * 
     * @param punto Posicion del raton en este momento. Se usa para dibujar la 
     * referencia de grosor
     */
    @Override
    public void mouseMoved(Point punto) {
        antes = punto;
        if (!getParametros()) {
            return;
        }
        //poner imagen anterior
        pintarBufferTemporalEnLienzo();

        //dibujar referencia de grosor
        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.GRAY);
        g.drawOval(punto.x - (int) (grosor / 2), punto.y - (int) (grosor / 2), (int) grosor, (int) grosor);
        lienzo.repaint();
    }

    /**
     * Cuando se arrastra se dibuja realmente en el lienzo.
     * Hace las siguiente acciones:
     * - Actualiza los parametros comunes de dibujado
     * - Pinta la linea de la seccion entre el punto anterior y el actual sobre el lienzo
     * @param punto 
     */
    @Override
    public void mouseDragged(Point punto) {
        if (!getParametros()) {
            return;
        }
        
        //dibujar trazo con el movimiento del raton
        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);
        g.drawLine(antes.x, antes.y, punto.x, punto.y);
        lienzo.repaint();
        //guardar nueva posicion de punto anterior
        this.antes = punto;
    }

    /**
     * Cuando se hace click en el ratón dibuja un circulo del grosor que se tiene
     * seleccionado en este momento y guarda el nuevo dibujo en el buffer temporal
     * @param punto 
     */
    @Override
    public void mouseClicked(Point punto) {
        if (!getParametros()) 
            return;
        
        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);
        g.fillOval((int) (punto.x - grosor / 2), (int) (punto.y - grosor / 2), (int) (grosor + 1), (int) (grosor + 1));
        lienzo.repaint();
        guardarLienzoEnBufferTemporal();
    }

    /**
     * Cuando se pulsa el raton se redibuja en el lienzo el contenido del buffer temporal
     * para borrar la marca de grosor de la herramienta
     * @param punto  Punto en el que se encuentra el raton
     */
    @Override
    public void mousePressed(Point punto) {
        pintando = true;
        //resetear el lienzo a la imagen original
        pintarBufferTemporalEnLienzo();
        lienzo.repaint();
    }

    /**
     * Cada vez que se suelta el botón del ratón se guarda en el buffer temporal
     * el digujo que hay en el lienzo y se desactiva el booleano de "pintando"
     * @param punto Punto en el que esta el raton
     */
    @Override
    public void mouseReleased(Point punto) {
        pintando = false;
        guardarLienzoEnBufferTemporal();
    }

    /**
     * Cuando el ratón entra en el lienzo se guarda en el buffer temporal 
     * el dibujo que tiene ahora mismo.
     * @param punto 
     */
    @Override
    public void mouseEntered(Point punto) {
        //guardar el estado de la imagen cuando el raton entre en el panel
        guardarLienzoEnBufferTemporal();
    }

    /**
     * Si el ratón sale del lienzo se repinta en el lienzo el buffer temporal
     * para quitar la marca de referencia de grosor si es necesario.
     * @param point 
     */
    @Override
    public void mouseExited(Point point) {
        if (pintando) {
            return;
        }
        if (!getParametros()) {
            return;
        }
        pintarBufferTemporalEnLienzo();
        lienzo.repaint();
    }

}
