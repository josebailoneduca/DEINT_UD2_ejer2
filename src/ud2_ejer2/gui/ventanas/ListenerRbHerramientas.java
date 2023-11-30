/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.ventanas;

import ud2_ejer2.gui.herramientas.DibujoLibre;
import ud2_ejer2.gui.herramientas.Herramienta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import ud2_ejer2.gui.herramientas.Arco;
import ud2_ejer2.gui.herramientas.Linea;
import ud2_ejer2.gui.herramientas.Ovalo;
import ud2_ejer2.gui.herramientas.Poligono;
import ud2_ejer2.gui.herramientas.Rectangulo;
import ud2_ejer2.gui.herramientas.RectanguloRedondo;

/**
 *
 * @author Jose Javier BO
 */
public class ListenerRbHerramientas implements ActionListener{
    
    VentanaPrincipal ventanaPrincipal;
    
    public ListenerRbHerramientas(VentanaPrincipal ventana) {
        this.ventanaPrincipal=ventana;
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String accion=((JRadioButton)e.getSource()).getActionCommand();
        Herramienta herramienta = null;
        switch (accion) {
            case "dibujolibre" -> ventanaPrincipal.setHerramienta(new DibujoLibre(ventanaPrincipal));
            case "linea" -> ventanaPrincipal.setHerramienta(new Linea(ventanaPrincipal));
            case "rectangulo" -> ventanaPrincipal.setHerramienta(new Rectangulo(ventanaPrincipal));
            case "ovalo" -> ventanaPrincipal.setHerramienta(new Ovalo(ventanaPrincipal));
            case "rectanredondo" -> ventanaPrincipal.setHerramienta(new RectanguloRedondo(ventanaPrincipal));
            case "arco" -> ventanaPrincipal.setHerramienta(new Arco(ventanaPrincipal));
            case "poligono" -> ventanaPrincipal.setHerramienta(new Poligono(ventanaPrincipal));
            default -> throw new AssertionError();
            
        }
        
        
    }
    
}
