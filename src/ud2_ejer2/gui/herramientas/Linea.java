/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import ud2_ejer2.gui.componentes.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Herramienta para dibujar una linea.
 * Su funcionamiento es:
 * - Al soltarse el botón del ratón se registra la posicion y se agrega a una lista.
 * - Si es el primer punto de la lista se guarda en el buffer temporal el estado del lienzo
 * - Si es el ultimo punto se resetea la lista.
 * 
 * En función de esa lista, cuando se mueve el ratón, se dibuja la linea:
 *  -Si no hay puntos no dibuja nada
 *  -Si hay uno dibuja en el lienzo lo almacenado en el buffer(imagen original) y se dibuja la linea
 * 
 * 
 * @see Herramienta
 * @author Jose Javier BO
 */
public class Linea extends Herramienta {
    //lista de puntos que componen la linea
    private ArrayList<Point> puntos=new ArrayList<Point>();
    
    //puntos totales
    private int puntosTotales=2;
    
    //puntos que quedan por marcar
    private int puntosRestantes=2;
    
    
    /**
     * Constructor 
     * @param ventanaPrincipal Referencia a la ventana principal
     * @param lienzo  Referencia al lienzo en el que dibujar
     */
    public Linea(VentanaPrincipal ventanaPrincipal,Lienzo lienzo) {
            super(ventanaPrincipal,lienzo);
            actualizaTextoPuntos();
    }

    /**
     * Solo hace algo si ya se ha agregado un punto en la lista de puntos. En ese caso 
     * pinta el buffer temporal en el lienzo y dibuja una línea desde el punto existente
     * hasta la posicion del raton. Esto hace que al ir moviendo el raton se vea una referencia
     * de donde se va  a dibujar la linea
     * 
     * @param punto Posicion actual del raton
     */
    @Override
    public void mouseMoved(Point punto) {
        //si se ha agregado un solo punto es que hay que dibujar la linea
        //hasta el segundo punto recogido de la posicion del raton
        if (puntos.size()==1&&getParametros()){
            //actualizar puntos gradiente
            x1=puntos.get(0).x;
            y1=puntos.get(0).y;
            x2=punto.x;
            y2=punto.y;

            //pintar imagen original guardada en buffer
            pintarBufferTemporalEnLienzo();
            //dibujar linea
            Graphics2D g= lienzo.getBufferG2D();
            setParametrosDibujo(g);
            g.drawLine(puntos.get(0).x, puntos.get(0).y, punto.x, punto.y);
            lienzo.repaint();
        }
      
    }

    /**
     * Redirige al comportamiento de mouseMoved
     * 
     * @param punto Posicion actual del raton
     * @see Linea#mouseMoved(java.awt.Point) 
     */
    @Override
    public void mouseDragged(Point punto) {
        mouseMoved(punto);
    }
    
    
    
 
    /**
     * Al soltarse el raton se guarda el estado del lienzo en el buffer temporal.
     * Y se limpia la lista de puntos haciendo que se desactive la referencia de dibujo
     * 
     * @param punto Punto en el que esta el raton
     */
    @Override
    public void mouseReleased(Point punto) {
        if(!getParametros())
            return;

        //si es el primer punto almacenamos en el buffer temporal la imagen original
        if (puntosRestantes==puntosTotales){
            guardarLienzoEnBufferTemporal();
            puntos.clear();
        }
        //agregamos un punto a la lista de puntos
        puntos.add(punto);
        puntosRestantes--;
        
        //si ha sido el ultimo punto reseteamos el numero de puntos restantes
        if(puntosRestantes==0){
            puntosRestantes=puntosTotales;
        }
        actualizaTextoPuntos();
    }

  

    /**
     * Al activarse actualiza el texto informativo de puntos en la ventana principal
     */
    @Override
    public void activar() {
        actualizaTextoPuntos();
    }

    /**
     * Al desactivarse limpia el texto informativo de puntos en la ventana principal
     */
    @Override
    public void desactivar() {
        vp.inputPuntosRestantes.setText("");
        vp.textArea.setText("");
    }
    
    
  /**
     * Actualiza las cajas de texto de los puntos
     */
    private void actualizaTextoPuntos(){
        vp.inputPuntosRestantes.setText(""+((puntosRestantes==puntosTotales)?0:puntosRestantes));
        String textoPuntos="";
        for (int i=0;i<puntos.size();i++) {
            textoPuntos+="Punto "+i+": ("+puntos.get(i).x+" , "+puntos.get(i).y+")\n";
        }
        vp.textArea.setText(textoPuntos);
    }
}
