/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.ventanas;

import ud2_ejer2.gui.componentes.Lienzo;
import ud2_ejer2.gui.listeners.ListenerRaton;
import ud2_ejer2.gui.listeners.ListenerRbHerramientas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import ud2_ejer2.logica.GestorTexturas;
import ud2_ejer2.logica.herramientas.DibujoLibre;
import ud2_ejer2.logica.herramientas.Herramienta;

/**
 * Clase central del ejercicio UI2_2
 *
 * Ademas de mostrar la interfaz grafica se encarga de almacenar el estado
 * respecto a qué herramienta de dibujo es la activa y qué valores de dibujado
 * deben usarse (color, grosor, tamaños, degradados, texturas...)
 *
 *
 *
 * @author Jose Javier BO
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private Herramienta herramientaActual;
    public float[][] estilosLineas = {{1, 0}, {15, 10}, {20, 10, 20, 40}, {15, 10, 2.5f, 10}};
    private GestorTexturas gestorTextura = new GestorTexturas();

    public BufferedImage textura;

    /**
     * Constructor. Inicia los componentes de la GUI, asigna el listener para
     * escuchar los clicks en los radiobuttons de herramientas y la escucha de
     * raton sobre el panel de dibujo que es de clase Lienzo.
     *
     * @see Lienzo
     * @see ListenerRaton
     * @see ListenerRbHerramientas
     * @see Herramienta
     */
    public VentanaPrincipal() {
        initComponents();
        initEventos();
        //inicializacion de valores
        lienzo.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        //coger miniatura
        seleccionarTextura(1);
        resetValores();
        setHerramienta(DibujoLibre.class);
    }

    /**
     * Inicializa y asigna los listner de ratón sobre el lienzo y el
     * Actionlistener de radiobutton de herramientas
     */
    private void initEventos() {
        //eventos de lienzo
        ListenerRaton lr = new ListenerRaton(this);
        lienzo.addMouseListener(lr);
        lienzo.addMouseMotionListener(lr);
        lienzo.addMouseWheelListener(lr);

        //eventos de selector de herramientas
        ListenerRbHerramientas listenerRbHerramientas = new ListenerRbHerramientas(this);
        rbDibujoLibre.addActionListener(listenerRbHerramientas);
        rbLinea.addActionListener(listenerRbHerramientas);
        rbRectangulo.addActionListener(listenerRbHerramientas);
        rbOvalo.addActionListener(listenerRbHerramientas);
        rbRectanguloRedondo.addActionListener(listenerRbHerramientas);
        rbArco.addActionListener(listenerRbHerramientas);
        rbPoligono.addActionListener(listenerRbHerramientas);
        rbPolilinea.addActionListener(listenerRbHerramientas);
        rbTexto.addActionListener(listenerRbHerramientas);
    }

    /**
     * Actualiza las cajas de coordenadas X e Y que muestran las coordenadas del
     * raton
     *
     * @param x Coordenada X
     * @param y Coordenada Y
     */
    public void actualizaCoordenadas(int x, int y) {

        outputX.setText("" + x);
        outputY.setText("" + y);
    }

    /**
     * Crea una herramienta basandose en una clase recibida y la asigna como
     * herramientaActual
     *
     * @param claseNuevaHerramienta Clase con la que construir la instancia.
     */
    public void setHerramienta(Class<?> claseNuevaHerramienta) {
        try {
            if (herramientaActual != null) {
                herramientaActual.desactivar();
            }

            Constructor<?> constructor = claseNuevaHerramienta.getConstructor(VentanaPrincipal.class, Lienzo.class);
            herramientaActual = (Herramienta) constructor.newInstance(this, lienzo);
            herramientaActual.activar();
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex) {
        }
    }

    /**
     * Devuelve la herramienta actual
     *
     * @return La herramienta
     */
    public Herramienta getHerramientaActual() {
        return herramientaActual;
    }

    /**
     * Pone los valores por defecto en las interfaz gráfica
     */
    public void resetValores() {
        inputRectRedAnchoCirculo.setText("20");
        inputRectRedAltoCirculo.setText("20");
        inputArcoInicio.setText("0");
        inputArcoAngulo.setText("270");
        inputPoligonoNumPuntos.setText("3");
        inputPolilineaNumPuntos.setText("3");
        inputTexto.setText("Escribe un texto");
        inputTextoTamano.setText("40");
        inputComunGrosor.setText("2");
        inputComunAncho.setText("200");
        inputComunAlto.setText("250");
    }

    /**
     * Agrega el valor recibido al grosor de trazo actual. Tiene una limitación
     * inferior de 1
     *
     * @param i El valor a agregar. Negativo para reducir el trazo y positivo
     * para aumentarlo
     */
    public void cambiaGrosorTrazo(int i) {
        try {
            int grosor = (Integer.parseInt(inputComunGrosor.getText())) + i;
            if (grosor < 1) {
                grosor = 1;
            }
            inputComunGrosor.setText("" + grosor);
        } catch (NumberFormatException ex) {
        }
    }

    /**
     * Agrega el valor recibido a la fase de trazo actual. Tiene una limitación
     * inferior de 0
     *
     * @param i El valor a agregar. Negativo para reducir el trazo y positivo
     * para aumentarlo
     */
    public void cambiarFaseTrazo(int i) {
        try {
            int fase = (Integer) (inputComunEstiloLineaFase.getValue()) + i;
            if (fase < 0) {
                fase = 0;
            }
            inputComunEstiloLineaFase.setValue(fase);
        } catch (NumberFormatException ex) {
        }
    }

    /**
     * Muestra un mensaje de error usando un JOptionPane
     *
     * @param msg El mensaje a mostrar
     */
    public void msgError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupHerramientas = new javax.swing.ButtonGroup();
        buttonGroupRelleno = new javax.swing.ButtonGroup();
        buttonGroupPintura = new javax.swing.ButtonGroup();
        scrollHeramientas = new javax.swing.JScrollPane();
        panGeneralHerramientas = new javax.swing.JPanel();
        panTipoGrafico = new javax.swing.JPanel();
        rbDibujoLibre = new javax.swing.JRadioButton();
        rbLinea = new javax.swing.JRadioButton();
        rbRectangulo = new javax.swing.JRadioButton();
        rbOvalo = new javax.swing.JRadioButton();
        panTipoGrafico1 = new javax.swing.JPanel();
        rbRectanguloRedondo = new javax.swing.JRadioButton();
        lbAnchoCirculo = new javax.swing.JLabel();
        lbAltoCirculo = new javax.swing.JLabel();
        inputRectRedAnchoCirculo = new javax.swing.JTextField();
        inputRectRedAltoCirculo = new javax.swing.JTextField();
        panTipoGrafico2 = new javax.swing.JPanel();
        rbArco = new javax.swing.JRadioButton();
        lbInicio = new javax.swing.JLabel();
        lbAngulo = new javax.swing.JLabel();
        inputArcoInicio = new javax.swing.JTextField();
        inputArcoAngulo = new javax.swing.JTextField();
        inputArcoCierre = new javax.swing.JComboBox<>();
        lbCierre = new javax.swing.JLabel();
        panTipoGrafico3 = new javax.swing.JPanel();
        rbPoligono = new javax.swing.JRadioButton();
        lbNPuntos = new javax.swing.JLabel();
        inputPoligonoNumPuntos = new javax.swing.JTextField();
        panTipoGrafico4 = new javax.swing.JPanel();
        rbPolilinea = new javax.swing.JRadioButton();
        lbnpuntos = new javax.swing.JLabel();
        inputPolilineaNumPuntos = new javax.swing.JTextField();
        panTipoGrafico5 = new javax.swing.JPanel();
        rbTexto = new javax.swing.JRadioButton();
        lbTamano = new javax.swing.JLabel();
        lbEstilo = new javax.swing.JLabel();
        inputTexto = new javax.swing.JTextField();
        inputTextoTamano = new javax.swing.JTextField();
        inputTextoEstilo = new javax.swing.JComboBox<>();
        lbFuente = new javax.swing.JLabel();
        inputTextoFuente = new javax.swing.JComboBox<>();
        panOpcionescomunes = new javax.swing.JPanel();
        lbGrosor = new javax.swing.JLabel();
        inputComunGrosor = new javax.swing.JTextField();
        lbComunTamano = new javax.swing.JLabel();
        lbComunAncho = new javax.swing.JLabel();
        inputComunAncho = new javax.swing.JTextField();
        lbComunAlto = new javax.swing.JLabel();
        inputComunAlto = new javax.swing.JTextField();
        lbComunRelleno = new javax.swing.JLabel();
        rbSoloBorde = new javax.swing.JRadioButton();
        rbComunrelleno = new javax.swing.JRadioButton();
        inputComunEstiloLinea = new javax.swing.JComboBox<>();
        panelTipoRelleno = new javax.swing.JPanel();
        rbColor = new javax.swing.JRadioButton();
        panelColorSeleccionado = new javax.swing.JPanel();
        rbTextura = new javax.swing.JRadioButton();
        inputTextura = new javax.swing.JComboBox<>();
        rbGradiente = new javax.swing.JRadioButton();
        inputTipoGradiente = new javax.swing.JComboBox<>();
        panelGradColor1 = new javax.swing.JPanel();
        panelGradColor2 = new javax.swing.JPanel();
        panelGradColor3 = new javax.swing.JPanel();
        lbTextura = new javax.swing.JLabel();
        lbGrosor1 = new javax.swing.JLabel();
        inputComunEstiloLineaFase = new javax.swing.JSpinner();
        panelCoordenadas = new javax.swing.JPanel();
        lbRaton = new javax.swing.JLabel();
        lbX = new javax.swing.JLabel();
        outputX = new javax.swing.JTextField();
        lbY = new javax.swing.JLabel();
        outputY = new javax.swing.JTextField();
        panelListaPuntos = new javax.swing.JPanel();
        lbPuntosRestantes = new javax.swing.JLabel();
        inputPuntosRestantes = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        btnLimpiar = new javax.swing.JButton();
        lienzo = new ud2_ejer2.gui.componentes.Lienzo();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        scrollHeramientas.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panTipoGrafico.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Tipo Gráfico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        buttonGroupHerramientas.add(rbDibujoLibre);
        rbDibujoLibre.setSelected(true);
        rbDibujoLibre.setText("Dibujo Libre");
        rbDibujoLibre.setActionCommand("dibujolibre");

        buttonGroupHerramientas.add(rbLinea);
        rbLinea.setText("Línea");
        rbLinea.setActionCommand("linea");

        buttonGroupHerramientas.add(rbRectangulo);
        rbRectangulo.setText("Rectángulo");
        rbRectangulo.setActionCommand("rectangulo");

        buttonGroupHerramientas.add(rbOvalo);
        rbOvalo.setText("Óvalo");
        rbOvalo.setActionCommand("ovalo");

        panTipoGrafico1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Rectángulo redondo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        buttonGroupHerramientas.add(rbRectanguloRedondo);
        rbRectanguloRedondo.setText("Rectáng. redondo");
        rbRectanguloRedondo.setActionCommand("rectanredondo");

        lbAnchoCirculo.setText("Ancho Circulo:");

        lbAltoCirculo.setText("Alto Circulo:");

        inputRectRedAnchoCirculo.setText("5");

        inputRectRedAltoCirculo.setText("5");

        javax.swing.GroupLayout panTipoGrafico1Layout = new javax.swing.GroupLayout(panTipoGrafico1);
        panTipoGrafico1.setLayout(panTipoGrafico1Layout);
        panTipoGrafico1Layout.setHorizontalGroup(
            panTipoGrafico1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panTipoGrafico1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbRectanguloRedondo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbAnchoCirculo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputRectRedAnchoCirculo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAltoCirculo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputRectRedAltoCirculo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panTipoGrafico1Layout.setVerticalGroup(
            panTipoGrafico1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoGrafico1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoGrafico1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panTipoGrafico1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbAltoCirculo)
                        .addComponent(inputRectRedAltoCirculo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panTipoGrafico1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbRectanguloRedondo)
                        .addComponent(lbAnchoCirculo)
                        .addComponent(inputRectRedAnchoCirculo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panTipoGrafico2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Arco", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        buttonGroupHerramientas.add(rbArco);
        rbArco.setText("Arco");
        rbArco.setActionCommand("arco");

        lbInicio.setText("Inicio:");

        lbAngulo.setText("Ángulo");

        inputArcoInicio.setText("0");

        inputArcoAngulo.setText("180");

        inputArcoCierre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Abierto", "Cuerda", "Pastel" }));

        lbCierre.setText("Cierre:");

        javax.swing.GroupLayout panTipoGrafico2Layout = new javax.swing.GroupLayout(panTipoGrafico2);
        panTipoGrafico2.setLayout(panTipoGrafico2Layout);
        panTipoGrafico2Layout.setHorizontalGroup(
            panTipoGrafico2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panTipoGrafico2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbArco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbCierre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputArcoCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputArcoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbAngulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputArcoAngulo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );
        panTipoGrafico2Layout.setVerticalGroup(
            panTipoGrafico2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoGrafico2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoGrafico2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panTipoGrafico2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbAngulo)
                        .addComponent(inputArcoAngulo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panTipoGrafico2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbArco)
                        .addComponent(lbInicio)
                        .addComponent(inputArcoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputArcoCierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbCierre)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panTipoGrafico3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Polígono", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        buttonGroupHerramientas.add(rbPoligono);
        rbPoligono.setText("Polígono");
        rbPoligono.setActionCommand("poligono");

        lbNPuntos.setText("Nº Puntos:");

        inputPoligonoNumPuntos.setText("3");

        javax.swing.GroupLayout panTipoGrafico3Layout = new javax.swing.GroupLayout(panTipoGrafico3);
        panTipoGrafico3.setLayout(panTipoGrafico3Layout);
        panTipoGrafico3Layout.setHorizontalGroup(
            panTipoGrafico3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panTipoGrafico3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbPoligono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbNPuntos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPoligonoNumPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panTipoGrafico3Layout.setVerticalGroup(
            panTipoGrafico3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoGrafico3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoGrafico3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbPoligono)
                    .addComponent(lbNPuntos)
                    .addComponent(inputPoligonoNumPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panTipoGrafico4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Polilínea", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        buttonGroupHerramientas.add(rbPolilinea);
        rbPolilinea.setText("Polilínea");
        rbPolilinea.setActionCommand("polilinea");

        lbnpuntos.setText("Nº Puntos:");

        inputPolilineaNumPuntos.setText("3");

        javax.swing.GroupLayout panTipoGrafico4Layout = new javax.swing.GroupLayout(panTipoGrafico4);
        panTipoGrafico4.setLayout(panTipoGrafico4Layout);
        panTipoGrafico4Layout.setHorizontalGroup(
            panTipoGrafico4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panTipoGrafico4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbPolilinea)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbnpuntos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPolilineaNumPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panTipoGrafico4Layout.setVerticalGroup(
            panTipoGrafico4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoGrafico4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoGrafico4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbPolilinea)
                    .addComponent(lbnpuntos)
                    .addComponent(inputPolilineaNumPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        panTipoGrafico5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Texto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        buttonGroupHerramientas.add(rbTexto);
        rbTexto.setText("Texto");
        rbTexto.setActionCommand("texto");

        lbTamano.setText("Tamaño:");

        lbEstilo.setText("Estilo:");

        inputTexto.setText("Escribe un texto");

        inputTextoTamano.setText("12");

        inputTextoEstilo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Italica", "Negrita", "Italica y Negrita" }));

        lbFuente.setText("Fuente:");

        inputTextoFuente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Arial", "Calibri", "Comic Sans MS", "Courier New", "Impact", "MS Gothic", "Times New Roman" }));

        javax.swing.GroupLayout panTipoGrafico5Layout = new javax.swing.GroupLayout(panTipoGrafico5);
        panTipoGrafico5.setLayout(panTipoGrafico5Layout);
        panTipoGrafico5Layout.setHorizontalGroup(
            panTipoGrafico5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoGrafico5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoGrafico5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbTamano)
                    .addComponent(rbTexto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTipoGrafico5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputTexto)
                    .addGroup(panTipoGrafico5Layout.createSequentialGroup()
                        .addComponent(inputTextoTamano, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbEstilo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputTextoEstilo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbFuente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputTextoFuente, 0, 138, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panTipoGrafico5Layout.setVerticalGroup(
            panTipoGrafico5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoGrafico5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoGrafico5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbTexto)
                    .addComponent(inputTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTipoGrafico5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panTipoGrafico5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbFuente)
                        .addComponent(inputTextoFuente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panTipoGrafico5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbTamano)
                        .addComponent(inputTextoTamano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbEstilo)
                        .addComponent(inputTextoEstilo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout panTipoGraficoLayout = new javax.swing.GroupLayout(panTipoGrafico);
        panTipoGrafico.setLayout(panTipoGraficoLayout);
        panTipoGraficoLayout.setHorizontalGroup(
            panTipoGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panTipoGrafico1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panTipoGrafico2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panTipoGraficoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbDibujoLibre)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(rbLinea)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(rbRectangulo)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(rbOvalo)
                .addGap(26, 26, 26))
            .addGroup(panTipoGraficoLayout.createSequentialGroup()
                .addGroup(panTipoGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panTipoGrafico5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panTipoGraficoLayout.createSequentialGroup()
                        .addComponent(panTipoGrafico3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panTipoGrafico4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panTipoGraficoLayout.setVerticalGroup(
            panTipoGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTipoGraficoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panTipoGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDibujoLibre)
                    .addComponent(rbLinea)
                    .addComponent(rbRectangulo)
                    .addComponent(rbOvalo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panTipoGrafico1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panTipoGrafico2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panTipoGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panTipoGrafico4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panTipoGrafico3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panTipoGrafico5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panOpcionescomunes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Opciones Comunes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lbGrosor.setText("Grosor:");

        inputComunGrosor.setText("1");
        inputComunGrosor.setToolTipText("Grosor del trazo");

        lbComunTamano.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbComunTamano.setText("Tamaño de la figura");

        lbComunAncho.setText("Ancho:");

        inputComunAncho.setText("20");

        lbComunAlto.setText("Alto:");

        inputComunAlto.setText("20");

        lbComunRelleno.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbComunRelleno.setText("Relleno");

        buttonGroupRelleno.add(rbSoloBorde);
        rbSoloBorde.setSelected(true);
        rbSoloBorde.setText("Sólo borde");
        rbSoloBorde.setActionCommand("soloborde");

        buttonGroupRelleno.add(rbComunrelleno);
        rbComunrelleno.setText("Relleno");
        rbComunrelleno.setActionCommand("relleno");

        inputComunEstiloLinea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contínuo", "-----------", "--   --   --", "- . - . - . - " }));
        inputComunEstiloLinea.setToolTipText("Tipo de trazo");

        panelTipoRelleno.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Pintura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        buttonGroupPintura.add(rbColor);
        rbColor.setSelected(true);
        rbColor.setText("Color");
        rbColor.setToolTipText("Pintar de color plano");
        rbColor.setActionCommand("color");
        rbColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbColorActionPerformed(evt);
            }
        });

        panelColorSeleccionado.setBackground(new java.awt.Color(0, 0, 0));
        panelColorSeleccionado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelColorSeleccionado.setToolTipText("Click para abrir selector de colores");
        panelColorSeleccionado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelColorSeleccionadoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelColorSeleccionadoLayout = new javax.swing.GroupLayout(panelColorSeleccionado);
        panelColorSeleccionado.setLayout(panelColorSeleccionadoLayout);
        panelColorSeleccionadoLayout.setHorizontalGroup(
            panelColorSeleccionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );
        panelColorSeleccionadoLayout.setVerticalGroup(
            panelColorSeleccionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        buttonGroupPintura.add(rbTextura);
        rbTextura.setText("Textura");
        rbTextura.setToolTipText("Pintar con textura");
        rbTextura.setActionCommand("textura");

        inputTextura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cuadros", "Puntos", "Archivo" }));
        inputTextura.setToolTipText("Tipo de textura");
        inputTextura.setMaximumSize(new java.awt.Dimension(77, 22));
        inputTextura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTexturaActionPerformed(evt);
            }
        });

        buttonGroupPintura.add(rbGradiente);
        rbGradiente.setText("Gradient.");
        rbGradiente.setToolTipText("Pintar con degradado");
        rbGradiente.setActionCommand("gradiente");

        inputTipoGradiente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lineal", "Radial" }));

        panelGradColor1.setBackground(new java.awt.Color(64, 224, 208));
        panelGradColor1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelGradColor1.setToolTipText("Primer color del gradiente");
        panelGradColor1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGradColor1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelGradColor1Layout = new javax.swing.GroupLayout(panelGradColor1);
        panelGradColor1.setLayout(panelGradColor1Layout);
        panelGradColor1Layout.setHorizontalGroup(
            panelGradColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        panelGradColor1Layout.setVerticalGroup(
            panelGradColor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        panelGradColor2.setBackground(new java.awt.Color(255, 140, 0));
        panelGradColor2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelGradColor2.setToolTipText("Segundo color del gradiente");
        panelGradColor2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGradColor2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelGradColor2Layout = new javax.swing.GroupLayout(panelGradColor2);
        panelGradColor2.setLayout(panelGradColor2Layout);
        panelGradColor2Layout.setHorizontalGroup(
            panelGradColor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        panelGradColor2Layout.setVerticalGroup(
            panelGradColor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        panelGradColor3.setBackground(new java.awt.Color(255, 0, 128));
        panelGradColor3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelGradColor3.setToolTipText("Tercer color del gradiente");
        panelGradColor3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGradColor3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelGradColor3Layout = new javax.swing.GroupLayout(panelGradColor3);
        panelGradColor3.setLayout(panelGradColor3Layout);
        panelGradColor3Layout.setHorizontalGroup(
            panelGradColor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        panelGradColor3Layout.setVerticalGroup(
            panelGradColor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        lbTextura.setBackground(new java.awt.Color(255, 255, 255));
        lbTextura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        lbTextura.setMaximumSize(new java.awt.Dimension(32, 32));
        lbTextura.setMinimumSize(new java.awt.Dimension(32, 32));
        lbTextura.setPreferredSize(new java.awt.Dimension(32, 32));

        javax.swing.GroupLayout panelTipoRellenoLayout = new javax.swing.GroupLayout(panelTipoRelleno);
        panelTipoRelleno.setLayout(panelTipoRellenoLayout);
        panelTipoRellenoLayout.setHorizontalGroup(
            panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTipoRellenoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTipoRellenoLayout.createSequentialGroup()
                        .addComponent(rbColor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelColorSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbTextura))
                    .addGroup(panelTipoRellenoLayout.createSequentialGroup()
                        .addComponent(rbGradiente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputTipoGradiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTipoRellenoLayout.createSequentialGroup()
                        .addComponent(panelGradColor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelGradColor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelGradColor3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTipoRellenoLayout.createSequentialGroup()
                        .addComponent(inputTextura, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTextura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTipoRellenoLayout.setVerticalGroup(
            panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTipoRellenoLayout.createSequentialGroup()
                .addGroup(panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTipoRellenoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(panelColorSeleccionado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(inputTextura, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rbTextura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(rbColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(panelTipoRellenoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbTextura, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelTipoRellenoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbGradiente)
                    .addComponent(panelGradColor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputTipoGradiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelGradColor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelGradColor3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lbGrosor1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbGrosor1.setText("Trazo");

        inputComunEstiloLineaFase.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        inputComunEstiloLineaFase.setToolTipText("Fase del tipo de trazo");

        javax.swing.GroupLayout panOpcionescomunesLayout = new javax.swing.GroupLayout(panOpcionescomunes);
        panOpcionescomunes.setLayout(panOpcionescomunesLayout);
        panOpcionescomunesLayout.setHorizontalGroup(
            panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                        .addComponent(lbGrosor1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbGrosor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComunGrosor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComunEstiloLinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComunEstiloLineaFase))
                    .addComponent(panelTipoRelleno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                        .addComponent(lbComunRelleno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbSoloBorde)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbComunrelleno))
                    .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                        .addComponent(lbComunTamano)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbComunAncho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComunAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbComunAlto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComunAlto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panOpcionescomunesLayout.setVerticalGroup(
            panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTipoRelleno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGrosor)
                    .addComponent(inputComunGrosor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputComunEstiloLinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbGrosor1)
                    .addComponent(inputComunEstiloLineaFase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbComunRelleno)
                    .addComponent(rbSoloBorde)
                    .addComponent(rbComunrelleno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbComunTamano)
                    .addComponent(lbComunAncho)
                    .addComponent(inputComunAncho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbComunAlto)
                    .addComponent(inputComunAlto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelCoordenadas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Coordenadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lbRaton.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lbRaton.setText("Ratón");

        lbX.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbX.setText("X");

        outputX.setPreferredSize(new java.awt.Dimension(60, 22));

        lbY.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbY.setText("Y");

        outputY.setPreferredSize(new java.awt.Dimension(60, 22));

        javax.swing.GroupLayout panelCoordenadasLayout = new javax.swing.GroupLayout(panelCoordenadas);
        panelCoordenadas.setLayout(panelCoordenadasLayout);
        panelCoordenadasLayout.setHorizontalGroup(
            panelCoordenadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoordenadasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbRaton)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lbX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputX, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputY, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelCoordenadasLayout.setVerticalGroup(
            panelCoordenadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoordenadasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCoordenadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbRaton)
                    .addComponent(lbX)
                    .addComponent(outputX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbY)
                    .addComponent(outputY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        panelListaPuntos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Lista de Puntos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lbPuntosRestantes.setText("Puntos restantes:");

        inputPuntosRestantes.setEditable(false);

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        javax.swing.GroupLayout panelListaPuntosLayout = new javax.swing.GroupLayout(panelListaPuntos);
        panelListaPuntos.setLayout(panelListaPuntosLayout);
        panelListaPuntosLayout.setHorizontalGroup(
            panelListaPuntosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaPuntosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListaPuntosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelListaPuntosLayout.createSequentialGroup()
                        .addComponent(lbPuntosRestantes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputPuntosRestantes)))
                .addContainerGap())
        );
        panelListaPuntosLayout.setVerticalGroup(
            panelListaPuntosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaPuntosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelListaPuntosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPuntosRestantes)
                    .addComponent(inputPuntosRestantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panGeneralHerramientasLayout = new javax.swing.GroupLayout(panGeneralHerramientas);
        panGeneralHerramientas.setLayout(panGeneralHerramientasLayout);
        panGeneralHerramientasLayout.setHorizontalGroup(
            panGeneralHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panGeneralHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panGeneralHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panTipoGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panGeneralHerramientasLayout.createSequentialGroup()
                        .addGroup(panGeneralHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panOpcionescomunes, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelCoordenadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelListaPuntos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panGeneralHerramientasLayout.setVerticalGroup(
            panGeneralHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panGeneralHerramientasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panTipoGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(panGeneralHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panGeneralHerramientasLayout.createSequentialGroup()
                        .addComponent(panOpcionescomunes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCoordenadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelListaPuntos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpiar)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        scrollHeramientas.setViewportView(panGeneralHerramientas);

        lienzo.setBackground(new java.awt.Color(255, 255, 255));
        lienzo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout lienzoLayout = new javax.swing.GroupLayout(lienzo);
        lienzo.setLayout(lienzoLayout);
        lienzoLayout.setHorizontalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );
        lienzoLayout.setVerticalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lienzo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollHeramientas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollHeramientas, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lienzo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Limpia el lienzo y resetea la herramienta actual
     *
     * @param evt
     */
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        //limpiar el lienzo
        Graphics2D g = lienzo.getBufferG2D();
        g.setColor(Color.white);
        g.fillRect(0, 0, lienzo.getBuffer().getWidth(), lienzo.getBuffer().getHeight());
        lienzo.repaint();

        //refrescar la herramienta actual
        herramientaActual.desactivar();
        setHerramienta(herramientaActual.getClass());
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void panelGradColor3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGradColor3MouseClicked
        Color colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", panelGradColor3.getBackground());
        if (colorSeleccionado != null)
            panelGradColor3.setBackground(colorSeleccionado);
    }//GEN-LAST:event_panelGradColor3MouseClicked

    private void panelGradColor2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGradColor2MouseClicked
        Color colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", panelGradColor2.getBackground());
        if (colorSeleccionado != null)
            panelGradColor2.setBackground(colorSeleccionado);
    }//GEN-LAST:event_panelGradColor2MouseClicked

    private void panelGradColor1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGradColor1MouseClicked
        Color colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", panelGradColor1.getBackground());
        if (colorSeleccionado != null)
            panelGradColor1.setBackground(colorSeleccionado);
    }//GEN-LAST:event_panelGradColor1MouseClicked

    /**
     * Abre el selector de color
     *
     * @param evt
     */
    private void panelColorSeleccionadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelColorSeleccionadoMouseClicked
        Color colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", panelColorSeleccionado.getBackground());
        if (colorSeleccionado != null)
            panelColorSeleccionado.setBackground(colorSeleccionado);
    }//GEN-LAST:event_panelColorSeleccionadoMouseClicked

    private void rbColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbColorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbColorActionPerformed

    private void inputTexturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTexturaActionPerformed
        seleccionarTextura(inputTextura.getSelectedIndex());
    }//GEN-LAST:event_inputTexturaActionPerformed

// <editor-fold defaultstate="collapsed" desc="Variables generadas"> 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.ButtonGroup buttonGroupHerramientas;
    public javax.swing.ButtonGroup buttonGroupPintura;
    public javax.swing.ButtonGroup buttonGroupRelleno;
    public javax.swing.JTextField inputArcoAngulo;
    public javax.swing.JComboBox<String> inputArcoCierre;
    public javax.swing.JTextField inputArcoInicio;
    public javax.swing.JTextField inputComunAlto;
    public javax.swing.JTextField inputComunAncho;
    public javax.swing.JComboBox<String> inputComunEstiloLinea;
    public javax.swing.JSpinner inputComunEstiloLineaFase;
    public javax.swing.JTextField inputComunGrosor;
    public javax.swing.JTextField inputPoligonoNumPuntos;
    public javax.swing.JTextField inputPolilineaNumPuntos;
    public javax.swing.JTextField inputPuntosRestantes;
    public javax.swing.JTextField inputRectRedAltoCirculo;
    public javax.swing.JTextField inputRectRedAnchoCirculo;
    public javax.swing.JTextField inputTexto;
    public javax.swing.JComboBox<String> inputTextoEstilo;
    public javax.swing.JComboBox<String> inputTextoFuente;
    public javax.swing.JTextField inputTextoTamano;
    private javax.swing.JComboBox<String> inputTextura;
    public javax.swing.JComboBox<String> inputTipoGradiente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAltoCirculo;
    private javax.swing.JLabel lbAnchoCirculo;
    private javax.swing.JLabel lbAngulo;
    private javax.swing.JLabel lbCierre;
    private javax.swing.JLabel lbComunAlto;
    private javax.swing.JLabel lbComunAncho;
    private javax.swing.JLabel lbComunRelleno;
    private javax.swing.JLabel lbComunTamano;
    private javax.swing.JLabel lbEstilo;
    private javax.swing.JLabel lbFuente;
    private javax.swing.JLabel lbGrosor;
    private javax.swing.JLabel lbGrosor1;
    private javax.swing.JLabel lbInicio;
    private javax.swing.JLabel lbNPuntos;
    private javax.swing.JLabel lbPuntosRestantes;
    private javax.swing.JLabel lbRaton;
    private javax.swing.JLabel lbTamano;
    private javax.swing.JLabel lbTextura;
    private javax.swing.JLabel lbX;
    private javax.swing.JLabel lbY;
    private javax.swing.JLabel lbnpuntos;
    private ud2_ejer2.gui.componentes.Lienzo lienzo;
    private javax.swing.JTextField outputX;
    private javax.swing.JTextField outputY;
    private javax.swing.JPanel panGeneralHerramientas;
    private javax.swing.JPanel panOpcionescomunes;
    private javax.swing.JPanel panTipoGrafico;
    private javax.swing.JPanel panTipoGrafico1;
    private javax.swing.JPanel panTipoGrafico2;
    private javax.swing.JPanel panTipoGrafico3;
    private javax.swing.JPanel panTipoGrafico4;
    private javax.swing.JPanel panTipoGrafico5;
    public javax.swing.JPanel panelColorSeleccionado;
    private javax.swing.JPanel panelCoordenadas;
    public javax.swing.JPanel panelGradColor1;
    public javax.swing.JPanel panelGradColor2;
    public javax.swing.JPanel panelGradColor3;
    private javax.swing.JPanel panelListaPuntos;
    private javax.swing.JPanel panelTipoRelleno;
    private javax.swing.JRadioButton rbArco;
    private javax.swing.JRadioButton rbColor;
    private javax.swing.JRadioButton rbComunrelleno;
    private javax.swing.JRadioButton rbDibujoLibre;
    private javax.swing.JRadioButton rbGradiente;
    private javax.swing.JRadioButton rbLinea;
    private javax.swing.JRadioButton rbOvalo;
    private javax.swing.JRadioButton rbPoligono;
    private javax.swing.JRadioButton rbPolilinea;
    private javax.swing.JRadioButton rbRectangulo;
    private javax.swing.JRadioButton rbRectanguloRedondo;
    private javax.swing.JRadioButton rbSoloBorde;
    private javax.swing.JRadioButton rbTexto;
    private javax.swing.JRadioButton rbTextura;
    private javax.swing.JScrollPane scrollHeramientas;
    public javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables

    private void seleccionarTextura(int i) {
        if (i == 0) {
            cargarTextura();
        } else {
            BufferedImage img = gestorTextura.getMiniatura(i);
            ImageIcon icono = new ImageIcon(img);
            lbTextura.setIcon(icono);
            ArrayList<String> listaTexturas = gestorTextura.getNombresTexturas();
            inputTextura.setModel(new DefaultComboBoxModel<String>(gestorTextura.getNombresTexturas().toArray(new String[0])));
            inputTextura.setSelectedIndex(i);
            textura = gestorTextura.getTextura(i);
        }

    }

//</editor-fold>
//</editor-fold>
    @Override
    public Insets insets() {
        return super.insets(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    private void cargarTextura() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./src/imagenes"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de imagen", "jpg", "png", "gif", "jpeg");

        fileChooser.setFileFilter(filter);
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            int i = gestorTextura.cargarArchivo(fileChooser.getSelectedFile());
            seleccionarTextura(i);
        }else{
        seleccionarTextura(1);
                }
    }

}
