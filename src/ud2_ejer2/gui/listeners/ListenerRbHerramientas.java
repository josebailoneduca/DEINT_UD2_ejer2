/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.listeners;

import ud2_ejer2.gui.herramientas.DibujoLibre;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import ud2_ejer2.gui.herramientas.Arco;
import ud2_ejer2.gui.herramientas.Linea;
import ud2_ejer2.gui.herramientas.Ovalo;
import ud2_ejer2.gui.herramientas.Poligono;
import ud2_ejer2.gui.herramientas.Polilinea;
import ud2_ejer2.gui.herramientas.Rectangulo;
import ud2_ejer2.gui.herramientas.RectanguloRedondo;
import ud2_ejer2.gui.herramientas.Texto;
import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 * Escucha el pulsado de los radiobuttons de las herramientas. Cuando recibe
 * un evento recoge el ActionCommand del componente pulsado y si esta en la lista
 * actua en consecuencia enviando a la ventana principal un mensaje para que 
 * active una herramienta basada en la clase que le envía.
 * @author Jose Javier BO
 */
public class ListenerRbHerramientas implements ActionListener{
    
    /**
     * Referencia a la ventana principal
     */
    VentanaPrincipal ventanaPrincipal;
    
    /**
     * Constructor
     * @param ventana  Referncia a la ventana principal
     */
    public ListenerRbHerramientas(VentanaPrincipal ventana) {
        this.ventanaPrincipal=ventana;
    }
    
    /**
     * Recoge el ActionCommand del evento y envia a la ventana principal la clase
     * con la que tiene que construir la herramienta a usar
     * 
     * @param e  El evento recibido
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String accion=((JRadioButton)e.getSource()).getActionCommand();
         switch (accion) {
            case "dibujolibre" -> ventanaPrincipal.setHerramienta(DibujoLibre.class);
            case "linea" -> ventanaPrincipal.setHerramienta(Linea.class);
            case "rectangulo" -> ventanaPrincipal.setHerramienta(Rectangulo.class);
            case "ovalo" -> ventanaPrincipal.setHerramienta(Ovalo.class);
            case "rectanredondo" -> ventanaPrincipal.setHerramienta(RectanguloRedondo.class);
            case "arco" -> ventanaPrincipal.setHerramienta(Arco.class);
            case "poligono" -> ventanaPrincipal.setHerramienta(Poligono.class);
            case "polilinea" -> ventanaPrincipal.setHerramienta(Polilinea.class);
            case "texto" -> ventanaPrincipal.setHerramienta(Texto.class);
            default -> throw new AssertionError();
        }
    }
}
