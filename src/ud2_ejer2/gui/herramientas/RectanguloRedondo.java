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
import java.awt.image.BufferedImage;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 *
 * @author Jose Javier BO
 */
public class RectanguloRedondo extends Herramienta {

    BufferedImage tmpBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    int anchoCirculo=0;
    int altoCirculo=0;
    public RectanguloRedondo(VentanaPrincipal ventanaPrincipal) {
        super(ventanaPrincipal);
    }

    @Override
    public void mouseMoved(Point punto) {
    }

    @Override
    public void mouseDragged(Point punto) {
        //limpiar lienzo
        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);
        g.setBackground(new Color(255, 255, 255, 0));
        g.clearRect(0, 0, lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight());

        //dibujar bufferTemporal que tiene la imagen original
        g.drawImage(tmpBuffer, 0, 0, null);

        //dibujar rectangulo
        if (soloBorde) {
            g.drawRoundRect(punto.x, punto.y, ancho, alto,anchoCirculo,altoCirculo);
        } else {
            g.fillRoundRect(punto.x, punto.y, ancho, alto,anchoCirculo,altoCirculo);
        }
        lienzo.repaint();
    }

    
    
    
    @Override
    public void mouseClicked(Point punto) {

    }

    @Override
    public void mousePressed(Point punto) {
        if (!getParametros()) {
            return;
        }
        //guardar original en buffer temporal
        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);
        tmpBuffer = new BufferedImage(lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight(), BufferedImage.TYPE_INT_ARGB);
        tmpBuffer.getGraphics().drawImage(lienzo.getBuffer(), 0, 0, null);

        //dibujar rectangulo
        if (soloBorde) {
            g.drawRoundRect(punto.x, punto.y, ancho, alto,anchoCirculo,altoCirculo);
        } else {
            g.fillRoundRect(punto.x, punto.y, ancho, alto,anchoCirculo,altoCirculo);
        }
        lienzo.repaint();
    }

    @Override
    protected boolean getParametros() {
        if (!super.getParametros()) 
            return false;
        try{
        anchoCirculo= Integer.parseInt(vp.inputRectRedAnchoCirculo.getText());
        }catch(NumberFormatException ex){
            vp.msgError("Ancho de circulo no v�lido");
                    return false;
        }
        
        try{
        altoCirculo= Integer.parseInt(vp.inputRectRedAltoCirculo.getText());
        }catch(NumberFormatException ex){
            vp.msgError("Alto de circulo no v�lido");
                    return false;
        }         
        return true;
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