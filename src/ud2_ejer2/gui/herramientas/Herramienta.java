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
import java.awt.RenderingHints;
import java.awt.Stroke;
import ud2_ejer2.gui.ventanas.Lienzo;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 *
 * @author Jose Javier BO
 */
abstract public class Herramienta {
   protected Lienzo lienzo;
   protected VentanaPrincipal vp;
   //parametros generales
   Color color;
   float grosor;
   Stroke trazo;
   int ancho;
   int alto;
   boolean soloBorde;
   
   
    public Herramienta(VentanaPrincipal vp) {
        this.vp = vp;
    }
   
   
    /**
     * Devuelve el lienzo asignado a la herramienta
     * @return El lienzo
     */
    public Lienzo getLienzo() {
        return lienzo;
    }

    /**
     * Define el lienzo sobre el que debe dibujar la herramienta
     * @param lienzo El lienzo
     */
    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
        
    }

    /**
     * Recoge los parametros principales
     * @return True si los ha recogido False si no los ha recogido
     */
    protected boolean getParametros(){
        
        color=vp.panelColorSeleccionado.getBackground();
        try{
        grosor=Float.parseFloat(vp.inputComunGrosor.getText());
        trazo=new BasicStroke(grosor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        }catch(NumberFormatException ex){
        
        }catch(IllegalArgumentException ex){
                vp.msgError("Grosor no válido");
                    return false;
        }
        try{
        ancho= Integer.parseInt(vp.inputComunAncho.getText());
        }catch(NumberFormatException ex){
            vp.msgError("Ancho no válido");
                    return false;
        }
        try{
        alto= Integer.parseInt(vp.inputComunAlto.getText());
        }catch(NumberFormatException ex){
            vp.msgError("Alto no válido");
                    return false;
        }
        soloBorde = vp.buttonGroupRelleno.getSelection().getActionCommand().equals("soloborde");
        return true;
    }
    
    protected void setParametrosDibujo(Graphics2D g){
        g.setColor(color);
        g.setStroke(trazo);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
     }
    
    abstract public void mouseMoved(Point punto);
    
    abstract public void mouseDragged(Point punto);

    abstract public void mouseClicked(Point punto);

    abstract public void mousePressed(Point punto);
    
    abstract public void mouseReleased(Point punto);


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
}
