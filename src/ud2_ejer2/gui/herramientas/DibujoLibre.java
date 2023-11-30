/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;
import java.awt.Graphics2D;
import java.awt.Point;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 *
 * @author Jose Javier BO
 */
public class DibujoLibre extends Herramienta {
    private Point antes;
    
    public DibujoLibre(VentanaPrincipal ventanaPrincipal) {
            super(ventanaPrincipal);
    }

    @Override
    public void mouseMoved(Point punto) {
        this.antes=punto;
    }

    @Override
    public void mouseDragged(Point punto) {
        if(!getParametros())
            return;
        Graphics2D g= lienzo.getBufferG2D();
        setParametrosDibujo(g);
        g.drawLine(antes.x, antes.y, punto.x, punto.y);
        this.antes=punto;
        lienzo.repaint();
    }

    @Override
    public void mouseClicked(Point punto) {
        if(!getParametros())
            return;
        Graphics2D g= lienzo.getBufferG2D();
        setParametrosDibujo(g);
        g.fillOval((int)(punto.x-grosor/2), (int)(punto.y-grosor/2), (int)(grosor+1), (int)(grosor+1));

        lienzo.repaint();
    }

    @Override
    public void mousePressed(Point punto) {
    }

    @Override
    public void mouseReleased(Point punto) {
    }
 

 

   
    
}
