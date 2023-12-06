/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.herramientas;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import ud2_ejer2.gui.componentes.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Clase para dibujar polilineas. Su funcionamiento es: Cuando se suelta el
 * boton del raton se registra una nueva posicion en la lista de puntos que
 * forman la polilinea. 
 * -Si es el primero ademas guarda en el buffer temporal el estado del lienzo 
 * -Si es el ultimo limpia la lista de puntos que forman la polilinea
 *
 * Cuando se mueve el raton se redibuja sobre el lienzo lo almacenado en el
 * buffer temporal y se superpone la polilinea formada por los puntos
 * almacenados en la lista mas el punto donde está actualmente el raton. Eso
 * permite ir viendo donde se va a ir dibujando la polilinea El dibujado solo
 * sucede si hay puntos en la lista.
 *
 * @see Herramienta
 * @author Jose Javier BO
 */
public class Polilinea extends Herramienta {

    /**
     * Lista de puntos que forman la polilinea
     */
    private ArrayList<Point> puntos = new ArrayList<Point>();

    /**
     * Cantidad de puntos que componen la polilinea
     */
    private int puntosTotales = 2;

    /**
     * Cantidad de puntos que quedan para completar la polilinea
     */
    private int puntosRestantes = 0;

    /**
     * Constructor
     *
     * @param ventanaPrincipal Referencia a la ventana principal
     * @param lienzo Referencia al lienzo en el que dibujar
     */
    public Polilinea(VentanaPrincipal ventanaPrincipal, Lienzo lienzo) {
        super(ventanaPrincipal, lienzo);
        actualizaTextoPuntos();
    }

    /**
     * Al moverse el raton, si hay algun punto ya agregado, se redibuja en el
     * lienzo el buffer actual y se superpone la polilinea compuesta por los
     * puntos almacenados en el array de puntos
     *
     * @param punto posicoin actual del raton
     */
    @Override
    public void mouseMoved(Point punto) {
        //si ya se ha marcado algun punto dibujamos lo que ya hay
        if (puntosRestantes<puntosTotales && getParametros()) {
            pintarBufferTemporalEnLienzo();
            //preparar arrays
            int[] px = new int[puntos.size() + 1];
            int[] py = new int[puntos.size() + 1];
            for (int i = 0; i < puntos.size(); i++) {
                px[i] = puntos.get(i).x;
                py[i] = puntos.get(i).y;
            }
            px[puntos.size()] = punto.x;
            py[puntos.size()] = punto.y;

            //actualizar puntos gradiente
            x1=px[0];
            y1=py[0];
            x2=px[1];
            y2=py[1];
            //coger coordenadas mas extremas para el gradiente
            for (int i=0;i<px.length;i++){
            if (px[i]<x1)
                x1=px[i];
            if (px[i]>x2)
                x2=px[i];
            if (py[i]<y1)
                y1=py[i];
            if (py[i]>y2)
                y2=py[i];
            }
            
            //Pintar polilinea
            Graphics2D g = lienzo.getBufferG2D();
            setParametrosDibujo(g);
            g.drawPolyline(px, py, puntos.size() + 1);
            lienzo.repaint();

        }

    }

    /**
     * Al arrastrarse se actua como al moverse
     *
     * @param punto posicion del raton
     */
    @Override
    public void mouseDragged(Point punto) {
        mouseMoved(punto);
    }



    /**
     * Al soltarse el boton del raton se registra la posicion como punto que forma parte
     * de la polilinea. Ademas:
     * - Si es el primer punto se guarda el estado del lienzo en el buffer temporal
     * - Si es el ultimo punto se resetea la cantidad de puntos restantes
     * 
     * @param punto posicion actual del raton
     */
    @Override
    public void mouseReleased(Point punto) {
        if (!getParametros()) {
            return;
        }
        Graphics2D g = lienzo.getBufferG2D();
        setParametrosDibujo(g);

        //si es el primer punto almacenamos en el buffer temporal la imagen original
        if (puntosRestantes == puntosTotales) {
            guardarLienzoEnBufferTemporal();
             puntos.clear();
        }
        puntos.add(punto);
        puntosRestantes--;

        //si ha sido el ultimo punto igualamos los restantes a los totales
        if (puntosRestantes == 0) {
           
            puntosRestantes = puntosTotales;
        }
        actualizaTextoPuntos();
    }

    
     /**
     * Ademas de los parametros comunes de dibujado desde la vista principal 
     * recoge tambien el parametro de puntos que componen la polilinea
     * @return True si los ha podido recoger, False si no los ha podido recoger
     */
    @Override
    protected boolean getParametros() {
        if (!super.getParametros()) {
            return false;
        }

        if (puntosRestantes == 0) {
            try {
                int puntosrecogidos = Integer.parseInt(vp.inputPolilineaNumPuntos.getText());
                if (puntosrecogidos < 1) {
                    throw new NumberFormatException();
                }
                puntosTotales = puntosrecogidos;
                puntosRestantes = puntosTotales;
            } catch (NumberFormatException ex) {
                vp.msgError("El numero de puntos de la polilínea no es válido");
                vp.inputPoligonoNumPuntos.setText("3");
                return false;
            }
        }
        return true;

    }

     /**
     * Actualiza las cajas de texto de los puntos
     */
    private void actualizaTextoPuntos() {
        vp.inputPuntosRestantes.setText("" + ((puntosRestantes == puntosTotales) ? 0 : puntosRestantes));
        String textoPuntos = "";
        for (int i = 0; i < puntos.size(); i++) {
            textoPuntos += "Punto " + i + ": (" + puntos.get(i).x + " , " + puntos.get(i).y + ")\n";
        }
        vp.textArea.setText(textoPuntos);
    }
    
    
    
     /**
     * Al desactivarse limpia la informacion de puntos en la ventana principal
     */
    @Override
    public void activar() {
        getParametros();
        actualizaTextoPuntos();
    }

    
    
    /**
     * Actualiza las cajas de texto de los puntos
     */    
    @Override
    public void desactivar() {
        vp.inputPuntosRestantes.setText("");
        vp.textArea.setText("");
        this.puntosRestantes = 0;
        this.tmpBuffer = new BufferedImage(lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

}
