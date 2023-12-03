/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.ventanas;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Clase listener del ratón. Se encarga de escuchar las acciones del ratón y enviarlas
 * a la herramienta activa en este momento en la ventana principal.
 * Además actualiza el parametro de grosor comun en la ventana principal cuando 
 * se gira la rueda del ratón.
 * 
 * 
 * @author Jose Javier BO
 */
public class ListenerRaton implements MouseMotionListener, MouseListener, MouseWheelListener {

    /**
     * Referencia a la ventana principal.
     */
    VentanaPrincipal vp;

    /**
     * Constructor
     * 
     * @param vp  Referencia a la ventana principal desde la que acceder a  la 
     * herramienta activa.
     */
    public ListenerRaton(VentanaPrincipal vp) {
        this.vp = vp;
    }

    
    //LOS EVENTOS SE ESCUCHAN Y SE ENVIA A LA HERRAMIENTA ACTIVA LA POSICION
    //DEL RATON
    
    
    @Override
    public void mouseDragged(MouseEvent e) {
        //actualizar campos x e y de coordenadas en ventana principal
        vp.actualizaCoordenadas(e.getX(), e.getY());
        if (vp.herramientaActual != null) {
            vp.herramientaActual.mouseDragged(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //actualizar campos x e y de coordenadas en ventana principal
        vp.actualizaCoordenadas(e.getX(), e.getY());
        if (vp.herramientaActual != null) {
            vp.herramientaActual.mouseMoved(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vp.herramientaActual != null) {
            vp.herramientaActual.mouseClicked(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (vp.herramientaActual != null) {
            vp.herramientaActual.mousePressed(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (vp.herramientaActual != null) {
            vp.herramientaActual.mouseReleased(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (vp.herramientaActual != null) {
            vp.herramientaActual.mouseEntered(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (vp.herramientaActual != null) {
            vp.herramientaActual.mouseExited(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //enivar informacion de rueda del raton para cambiar el grosor del trazo
        vp.cambiaGrosorTrazo(-e.getWheelRotation());
    }

}
