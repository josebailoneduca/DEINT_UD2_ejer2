/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 *
 * @author Jose Javier BO
 */
public class Ovalo extends Herramienta {
    BufferedImage tmpBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    public Ovalo(VentanaPrincipal ventanaPrincipal) {
            super(ventanaPrincipal);
    }

    @Override
    public void mouseMoved(Point punto) {
    }

    @Override
    public void mouseDragged(Point punto) {
            //limpiar lienzo
            Graphics2D g= lienzo.getBufferG2D();
            setParametrosDibujo(g);
            g.setBackground(new Color(255, 255, 255, 0));
            g.clearRect(0, 0, lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight());
            
            //dibujar bufferTemporal que tiene la imagen original
            g.drawImage(tmpBuffer, 0, 0,null);
            
            if (soloBorde)
                g.drawOval(punto.x, punto.y, ancho, alto);
            else
                g.fillOval(punto.x, punto.y, ancho, alto);
            lienzo.repaint();
    }

    @Override
    public void mouseClicked(Point punto) {

    }

    
    @Override
    public void mousePressed(Point punto) {
        if(!getParametros())
            return;
        Graphics2D g= lienzo.getBufferG2D();
        setParametrosDibujo(g);
            tmpBuffer = new BufferedImage(lienzo.getBuffer().getWidth(),lienzo.getBuffer().getHeight(),BufferedImage.TYPE_INT_ARGB);
            tmpBuffer.getGraphics().drawImage(lienzo.getBuffer(), 0, 0, null);
            if (soloBorde)
                g.drawOval(punto.x, punto.y, ancho, alto);
            else
                g.fillOval(punto.x, punto.y, ancho, alto);
            lienzo.repaint();
    }

    @Override
    public void mouseReleased(Point punto) {

    }
 

    @Override
    public void activar() {
 
    }

    @Override
    public void desactivar() {
 
    }

 

   
    
}
