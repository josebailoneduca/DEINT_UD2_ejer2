/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.logica.herramientas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import ud2_ejer2.gui.componentes.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Clase básica de herramienta. El resto de herramientas extienden de esta.
 *
 * Contiene variables y métodos para recopilar los parametros comunes de dibujo
 * y aplicarlos al dibujar.
 * 
 * Tambien tiene metodos a conectar con los eventos recibidos del ratón por un listener
 *
 * @author Jose Javier BO
 */
abstract public class Herramienta {

    /**
     * Referencia al lienzo donde eibujar
     */
    protected Lienzo lienzo;

    /**
     * Referencia a la ventana principal de la que coger los parametros de
     * dibujado
     */
    protected VentanaPrincipal vp;

    /**
     * Imagen en memoria para usarse como buffer temporal si la herramienta lo
     * necesita
     */
    BufferedImage tmpBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    //parametros generales
    public static final int LINEAL = 0;
    public static final int RADIAL = 1;

    MultipleGradientPaint gradiente;//gradiente actaul
    BufferedImage imgTextura;//imagen usada como textura
    TexturePaint textura; // textura actual
    String tipoPintura = "color"; // tipo de pintura (color, gradiente, textura)
    Color color; // color de dibujado
    int tipoGradiente;//tipo de gradiente
    //colores del gradiente
    Color colorGradiente1;
    Color colorGradiente2;
    Color colorGradiente3;

    //coordenadas en las que basar el gradiente y el desfase de las texturas
    //cada herramienta debe actualizarlo a segun sus necesidades
    //Estas coordenadas son usadas por el generador de gradientes
    //para hacer un gradiente inscrito en un rectangulo definido por estas
    //coordenadas. Tambien son usadas por el generador de texturas.
    int x1 = 0;
    int y1 = 0;
    int x2 = 1;
    int y2 = 1;

    float grosor; // grosor de trazo a usar
    float[] estiloLinea = {1};//estilo de linea(por defecto continua)
    int fase = 0;// fase del estilo de linea
    Stroke trazo;// objeto trazo preparado para usar
    int ancho;// ancho comun para dibujar figuras
    int alto;// alto comun para dibujar fituras
    boolean soloBorde;// si es solo borde o relleno

    /**
     * Constructor
     *
     * @param vp Referencia a la ventana principal de la que coger los
     * parametros de dibujado
     * @param lienzo Referencia al lienzo en el que dibujar
     */
    public Herramienta(VentanaPrincipal vp, Lienzo lienzo) {
        this.vp = vp;
        this.lienzo = lienzo;
        x2 = lienzo.getWidth();
        y2 = lienzo.getHeight();

    }

    //Metodos receptores de ordenes relativas a acciones tipicas del ratón
    public void mouseMoved(Point punto) {
    }

    public void mouseClicked(Point punto) {
    }

    public void mouseDragged(Point punto) {
    }

    public void mousePressed(Point punto) {
    }

    public void mouseReleased(Point punto) {
    }

    public void mouseEntered(Point punto) {
    }

    public void mouseExited(Point point) {
    }

    /**
     * Recoge los parametros comunes de dibujado desde la ventana principal
     *
     * @return True si los ha recogido False si no los ha recogido
     */
    protected boolean getParametros() {
        //pintura
        tipoPintura = vp.buttonGroupPintura.getSelection().getActionCommand();
        color = vp.panelColorSeleccionado.getBackground();
        tipoGradiente = vp.inputTipoGradiente.getSelectedIndex();
        colorGradiente1 = vp.panelGradColor1.getBackground();
        colorGradiente2 = vp.panelGradColor2.getBackground();
        colorGradiente3 = vp.panelGradColor3.getBackground();
        imgTextura = vp.textura;
        
        //trazo
        try {
            grosor = Float.parseFloat(vp.inputComunGrosor.getText());
            int idEstilo = vp.inputComunEstiloLinea.getSelectedIndex();
            estiloLinea = new float[vp.estilosLineas[idEstilo].length];
            for (int i = 0; i < vp.estilosLineas[idEstilo].length; i++) {
                estiloLinea[i] = vp.estilosLineas[idEstilo][i] * grosor / 4;
            }
            fase = (Integer)vp.inputComunEstiloLineaFase.getValue();
            trazo = new BasicStroke(grosor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, estiloLinea, fase);
        } catch (NumberFormatException ex) {

        } catch (IllegalArgumentException ex) {
            vp.msgError("Grosor no válido");
            return false;
        }
        try {
            ancho = Integer.parseInt(vp.inputComunAncho.getText());
        } catch (NumberFormatException ex) {
            vp.msgError("Ancho no válido");
            return false;
        }
        try {
            alto = Integer.parseInt(vp.inputComunAlto.getText());
        } catch (NumberFormatException ex) {
            vp.msgError("Alto no válido");
            return false;
        }
        soloBorde = vp.buttonGroupRelleno.getSelection().getActionCommand().equals("soloborde");

        return true;
    }

    /**
     * Actualiza los parametros color y pincel de dibujado del Graphics2D.
     * Tambien activa el antialiasing en el contexto grafico.
     *
     * @param g Graphics2D a usar
     */
    protected void setParametrosDibujo(Graphics2D g) {
        this.setPintura(g);
        g.setStroke(trazo);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

    }

    /**
     * Guarda el dibujo actual del lienzo en el buffer temporal
     */
    public void guardarLienzoEnBufferTemporal() {
        //guardar imagen original en buffer temporal
        tmpBuffer = new BufferedImage(lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight(), BufferedImage.TYPE_INT_ARGB);
        tmpBuffer.getGraphics().drawImage(lienzo.getBuffer(), 0, 0, null);
    }

    /**
     * Dibuja el contenido del buffer temporal en el lienzo
     */
    public void pintarBufferTemporalEnLienzo() {
        Graphics2D g = lienzo.getBufferG2D();
        //limpiar lienzo
        g.setBackground(new Color(255, 255, 255, 0));
        g.clearRect(0, 0, lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight());
        //dibujar la imagen original en el lienzo
        g.drawImage(tmpBuffer, 0, 0, null);
    }

    /**
     * Realiza las acciones necesarias cuando se desactiva la herramienta
     */
    public void desactivar() {
    }

    /**
     * Realiza las acciones necesarias cuando se activa la herramienta
     */
    public void activar() {
    }

    /**
     * Definicion de pintura a usar. Por defecto el color pero puede ser el
     * gradiente o una textura
     *
     * @param g Graphics al que aplicar la pintura
     */
    protected void setPintura(Graphics2D g) {
        switch (tipoPintura) {
            case "color" ->
                g.setPaint(color);
            case "gradiente" ->
                g.setPaint(getGradiente(x1, y1, x2, y2));
            case "textura" ->
                g.setPaint(getTextura(x1, y1));
            default ->
                g.setPaint(color);
        }
    }

    /**
     * Devuelve el gradiente inscrito en un rectangulo
     *
     * @param gx1 x superior izqueirda
     * @param gy1 y superior izauierda
     * @param gx2 x inferior derecha
     * @param gy2 y inferior derecha
     * @return El gradiente
     */
    protected Paint getGradiente(int gx1, int gy1, int gx2, int gy2) {
        //asegurar que hay diferencia entre puntos que definen el rectangulo
        if (gx1 == gx2) {
            gx2++;
        }
        if (gy1 == gy2) {
            gy2++;
        }
        //Asegurar menor xy1   y mayor xy2 para radial
        if (tipoGradiente == RADIAL) {
            if (gx1 > gx2) {
                int aux = gx1;
                gx1 = gx2;
                gx2 = aux;
            }
            if (gy1 > gy2) {
                int aux = gy1;
                gy1 = gy2;
                gy2 = aux;
            }
        }
        float[] pos = {0, 0.5f, 1};
        Color[] colores = {colorGradiente1, colorGradiente2, colorGradiente3};
        switch (tipoGradiente) {
            case RADIAL -> {

                return new RadialGradientPaint(
                        new Rectangle(new Point(gx1, gy1), new Dimension(gx2 - gx1, gy2 - gy1)),
                        pos,
                        colores,
                        MultipleGradientPaint.CycleMethod.REFLECT);
            }
            default -> {
                return new LinearGradientPaint(
                        new Point(gx1, gy1),
                        new Point(gx2, gy2),
                        pos,
                        colores,
                        MultipleGradientPaint.CycleMethod.REFLECT);
            }
        }//fin switch
    }//fin getgradiente
    
    
     /**
     * Devuelve la textura a usar
     *
     * @param tx x inicial superior izqueirda
     * @param ty y inicial superior izauierda
     * @return La textura
     */
    protected Paint getTextura(int tx, int ty) {
        return new TexturePaint(imgTextura, new Rectangle(tx,ty, imgTextura.getWidth(), imgTextura.getHeight()));
    }
}//fin clase
