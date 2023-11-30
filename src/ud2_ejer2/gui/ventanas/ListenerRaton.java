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

/**
 *
 * @author Jose Javier BO
 */
public class ListenerRaton implements MouseMotionListener, MouseListener {
    VentanaPrincipal vp ;
    
    
    public ListenerRaton(VentanaPrincipal vp) {
        this.vp = vp;
    }
    
    
    @Override
    public void mouseDragged(MouseEvent e) {
         vp.actualizaCoordenadas(e.getX(),e.getY());
        if (vp.herramientaActual!=null)
        vp.herramientaActual.mouseDragged(new Point(e.getX(),e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        vp.actualizaCoordenadas(e.getX(),e.getY());
        if (vp.herramientaActual!=null)
        vp.herramientaActual.mouseMoved(new Point(e.getX(),e.getY()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (vp.herramientaActual!=null)
        vp.herramientaActual.mouseClicked(new Point(e.getX(),e.getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) {
                if (vp.herramientaActual!=null)
        vp.herramientaActual.mousePressed(new Point(e.getX(),e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (vp.herramientaActual!=null)
        vp.herramientaActual.mouseReleased(new Point(e.getX(),e.getY()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().requestFocus();
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
