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
public class Linea extends Herramienta {
    private ArrayList<Point> puntos=new ArrayList<Point>();
    private int puntosTotales=2;
    private int puntosRestantes=2;
    BufferedImage tmpBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    public Linea(VentanaPrincipal ventanaPrincipal) {
            super(ventanaPrincipal);
            actualizaTextoPuntos();
    }

    @Override
    public void mouseMoved(Point punto) {
        if (puntosRestantes<puntosTotales&&getParametros()){
            //limpiar lienzo
            Graphics2D g= lienzo.getBufferG2D();
            setParametrosDibujo(g);
            g.setBackground(new Color(255, 255, 255, 0));
            g.clearRect(0, 0, lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight());
            
            //dibujar bufferTemporal que tiene la imagen original
            g.drawImage(tmpBuffer, 0, 0,null);
            //dibujar linea
            g.drawLine(puntos.get(0).x, puntos.get(0).y, punto.x, punto.y);
            lienzo.repaint();
        }
      
    }

    @Override
    public void mouseDragged(Point punto) {

    }

    @Override
    public void mouseClicked(Point punto) {
        if(!getParametros())
            return;
        Graphics2D g= lienzo.getBufferG2D();
        setParametrosDibujo(g);

        //si es el primer punto almacenamos en el buffer temporal la imagen original
        if (puntosRestantes==puntosTotales){
            tmpBuffer = new BufferedImage(lienzo.getBuffer().getWidth(),lienzo.getBuffer().getHeight(),BufferedImage.TYPE_INT_ARGB);
            tmpBuffer.getGraphics().drawImage(lienzo.getBuffer(), 0, 0, null);
            puntos.clear();
        }
        puntos.add(punto);
        puntosRestantes--;
        
        //si ha sido el ultimo punto
        if(puntosRestantes==0){
            g.drawLine(puntos.get(0).x, puntos.get(0).y, puntos.get(1).x, puntos.get(1).y);
            puntosRestantes=puntosTotales;
            lienzo.repaint();
        }
        actualizaTextoPuntos();
    }

    /**
     * Actualiza las cajas de texto de los puntos
     */
    private void actualizaTextoPuntos(){
        vp.inputPuntosRestantes.setText(""+puntosRestantes);
        String textoPuntos="";
        for (int i=0;i<puntos.size();i++) {
            textoPuntos+="Punto "+i+": ("+puntos.get(i).x+" , "+puntos.get(i).y+")\n";
        }
        vp.textArea.setText(textoPuntos);
    }
    
    @Override
    public void mousePressed(Point punto) {
    }

    @Override
    public void mouseReleased(Point punto) {

    }

  

    @Override
    public void activar() {
        actualizaTextoPuntos();
    }

    @Override
    public void desactivar() {
        vp.inputPuntosRestantes.setText("");
        vp.textArea.setText("");
    }

 

   
    
}
