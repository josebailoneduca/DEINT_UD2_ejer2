/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.ventanas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jose Javier BO
 */
public class Lienzo extends javax.swing.JPanel {
    BufferedImage buffer;
    /**
     * Creates new form Lienzo
     */
    public Lienzo() {
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        //listener para refrescar el buffer en el resize
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                    ((Lienzo)e.getComponent()).actualizaBufferedImage();
              }
        });

    }

    /**
     * Actualiza el buffer al tamaño superior, bien sea el actual del panel o el que ya tiene el buffer
     */
    public void actualizaBufferedImage(){
 
        //si el panel no es mas grande que el buffer volvemos
        if (this.getWidth()<=buffer.getWidth()&&this.getHeight()<=buffer.getHeight())
            return;
       
       //en caso contrario agrandamos el buffer al nuevo tamaño quedandonos con las dimensiones mas altas
       int ancho=buffer.getWidth();
       int alto=buffer.getHeight();
       if (this.getWidth()>buffer.getWidth())
           ancho=this.getWidth();
       if (this.getHeight()>buffer.getHeight())
           alto=this.getHeight();
       BufferedImage nueva =new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
       nueva.createGraphics().drawImage(buffer, 0, 0,null);
       buffer=nueva;
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public Graphics2D getBufferG2D(){
        return (Graphics2D) buffer.getGraphics();
    }
    /**
     * Repintar el buffer
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(buffer, 0, 0, this);
    }

}
