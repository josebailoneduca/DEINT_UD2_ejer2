/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.logica;

import ud2_ejer2.gui.ventanas.VentanaPrincipal;

/**
 *Programa para dibujado.
 * 
 * Esta clase Logica es el punto de entrada hacia el programa. Se encarga de instanciar 
 * la VentanaPrincipal.
 * 
 * La clase VentanaPrincipal cotrola los elementos de configuración almacenando
 * los parametros de qué herramienta hay seleccionada, los grosores, colores etc...
 * 
 * Esta ventana principal genera un listener en los radiobutton de seleccion de herramienta
 * y otro listener para las acciones del ratón.
 * 
 * - Cuando el listener de botones de herramientas detecta la selección de
 * una nueva herramienta el lístener informa a la ventana principal de qué herramienta
 * ha sido la elegida para que la instancie y la establezca como
 * "herramienta actual" que será con la que se dibuje.
 *
 * - Cuando el listener de ratón recibe algun evento entoncesle manda mensajes a 
 * la herramienta almacenada en el atributo "herramientaActual" de la ventana principal
 * Este listener de raton tambien escucha la rueda del raton y con ella
 * controla el grosor del trazo y la fase del trazo (si se pulsa Control mientras se gira la rueda)
 *
 * - Las herramientas durante su actividad recogen los datos de
 * configuracion de dibujado desde VentanaPrincipal.
 * Ademas algunas herramientas actualizan información de algunos componentes
 * de la interfaz como los puntos restantes o los puntos dibujados que aparecen
 * en la seeción inferior derecha de Lista de puntos.
 * 
 * Todas las herramientas (paquete ud2_ejer2.logica.herramientas) heredan de la clas
 * Herramienta que tiene la base de todas ellas.
 * 
 * El dibujado sigue la siguiente estructura:
 * El componente "lienzo" de VentanaPincipal se usa como panel de dibujo.
 * Es una clase extendida de JPanel que tiene sobreescrito el método paintComponent.
 * En ese paintComponent lo que se hace es dibujar el contenido de un BufferedImage
 * sobre el panel.
 * Las herramientas lo que hacen es pintar sobre ese BufferedImage.
 * 
 * 
 * 
 * USO DEL PROGRAMA:
 * 
 * En el programa hay un panel a la izquierda que es el lienzo de dibujo y otro
 * panel a la derecha con unas opciones de dibujado. 
 * Se pueden elegir herramientas y definir caracteristicas de colores grosores,
 * cantidad de puntos tamaños....
 * 
 * Las herramientas tienen unas opciones comunes:
 * 
 *  - Pintura: Se puede elegir un color plano, una textura o un gradiente. Se usa para
 *             definir el color de lo que se pinta, bien sea solo borde o relleno.
 *  - Trazo: Define el grosor y punteado de lasl líneas. El grosor se puede cambiar
 *          mientras se dibuja girando la rueda del raton. La fase del punteado
 *         Se puede cambiar mientras se dibuja pulsando control+girar rueda raton.
 *  - Relleno: Elegir si se quiere solo el borde de la figura o rellenarla
 *  - Tamaño de la figura: A rectangulos, ovalo, arco se les puede definir el ancho y el alto
 * 
 * 
 * Las herramientas:
 * 
 * -Dibujo libre: Pulsar y arrastrar para pintar como con un pincel
 * -Linea: Hacer click para marcar el inicio y otro click para marcar el fin
 * -Rectangulo: Click y arrastrar para ver una previa antes de colocarlo.
 * -Ovalo: Click y arrastrar para ver una previa antes de colocarlo.
 * -Arco: Click y arrastrar para ver una previa antes de colocarlo.
 * -Poligono: Hacer clic para ir colocando los vertice del poligono.
 * -Polilinea: Hacer clic para ir colocando los vertice de la polilinea.
 * -Texto: Click y arrastrar para ver una previa antes de colocarlo.
 * 
 * @author Jose Javier BO
 */
public class Logica {
        /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}
