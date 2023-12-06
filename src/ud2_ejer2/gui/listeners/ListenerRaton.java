/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Clase listener del rat�n. Se encarga de escuchar las acciones del rat�n y enviarlas
 * a la herramienta activa en este momento en la ventana principal.
 * Adem�s actualiza el parametro de grosor comun en la ventana principal cuando 
 * se gira la rueda del rat�n.
 * 
 * 
 * @author Jose Javier BO
 */
public class ListenerRaton implements MouseMotionListener, MouseListener, MouseWheelListener {
    //ATRIBUTOS
    /**
     * Referencia a la ventana principal.
     */
    VentanaPrincipal vp;

    
    //METODOS
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
        
        //Enviar mensaje a herramienta actual
        if (vp.getHerramientaActual() != null) {
            vp.getHerramientaActual().mouseDragged(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //actualizar campos x e y de coordenadas en ventana principal
        vp.actualizaCoordenadas(e.getX(), e.getY());
        
        //Enviar mensaje a herramienta actual
        if (vp.getHerramientaActual() != null) {
            vp.getHerramientaActual().mouseMoved(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Enviar mensaje a herramienta actual
        if (vp.getHerramientaActual() != null) {
            vp.getHerramientaActual().mouseClicked(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Enviar mensaje a herramienta actual
        if (vp.getHerramientaActual() != null) {
            vp.getHerramientaActual().mousePressed(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Enviar mensaje a herramienta actual
        if (vp.getHerramientaActual() != null) {
            vp.getHerramientaActual().mouseReleased(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Enviar mensaje a herramienta actual
        if (vp.getHerramientaActual() != null) {
            vp.getHerramientaActual().mouseEntered(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Enviar mensaje a herramienta actual
        if (vp.getHerramientaActual() != null) {
            vp.getHerramientaActual().mouseExited(new Point(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()){
        //enviar informacion de rueda del raton para cambiar la fase del trazo
        vp.cambiarFaseTrazo(-e.getWheelRotation());
        }
        else{
        //enivar informacion de rueda del raton para cambiar el grosor del trazo
        vp.cambiaGrosorTrazo(-e.getWheelRotation());
        }
        
        //acviar el evento mouseDragged que es usado por las herramientas para 
        //refrescar el estado
        mouseDragged(e);
    }

}
