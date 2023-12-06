/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.componentes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

/**
 * Clase que extiende de JPanel
 * Permite que se pueda reescalar el panel, minimizar, maximizar, sin perder la 
 * imagen y pudiendo dibujar sobre toda la superficie.
 * 
 * El funcionamiento es el siguiente:
 * Hay un atributo "buffer" que es una imagen BufferedImage. Cuando se ejecuta el paintComponent
 * de esta clase lo que se hace es pintar sobre el panel el contenido que tenga ese buffer
 * 
 * Así si se quiere dibujar sobre el panel basta con adquirir el Graphics 2D del buffer
 * y usarlo para ejecutar las operaciones de dibujo. La clase contiene métodos para objtener 
 * directamente el Graphics2D del buffer.
 * 
 * @author Jose Javier BO
 */
public class Lienzo extends javax.swing.JPanel {
    
    /**
     * Imagen que se dibujara en cada ejecución del paintComponent
     */
    BufferedImage buffer;
    
    
    /**
     * Constructor. Instancia el buffer y le agrega al panel un listener de reescalado.
     * Cuando el evento de reescalado se dispara se actualiza el tamaño del buffer
     * para adaptarse como mínimo al tamaño del panel.
     */
    public Lienzo() {
        //inicializa buffer
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        //listener para refrescar el buffer en el resize
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                    ((Lienzo)e.getComponent()).actualizaDimensionesBuffer();
              }
        });

    }

    /**
     * Actualiza el buffer al tamaño superior, bien sea el actual del panel o el 
     * que ya tiene el buffer
     */
    public void actualizaDimensionesBuffer(){
 
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
       
       //crear nuevo buffer con la imagen del anterior y las nuevas dimensiones
       BufferedImage nueva =new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
       nueva.createGraphics().drawImage(buffer, 0, 0,null);
       buffer=nueva;
    }

    /**
     * Devuelve una referencia al buffer
     * @return  El buffer
     */
    public BufferedImage getBuffer() {
        return buffer;
    }

    /**
     * Devuelve el objeto Graphics2D del buffer
     * @return El objeto Graphics2D
     */
    public Graphics2D getBufferG2D(){
        return (Graphics2D) buffer.getGraphics();
    }
    
    /**
     * Al pintarse el panel se repinta el contenido del buffer
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(buffer, 0, 0, this);
    }

    
}
