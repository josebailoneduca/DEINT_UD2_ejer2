/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
Lista de paquetes:
 */
package ud2_ejer2.gui.ventanas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import ud2_ejer2.gui.herramientas.DibujoLibre;
import ud2_ejer2.gui.herramientas.Herramienta;

/**
 * Clase central del ejercicio UI2_2
 *
 * Ademas de mostrar la interfaz grafica se encarga de almacenar el estado
 * respecto a qué herramienta de dibujo es la activa y qué valores de dibujado
 * deben usarse (color, grosor, tamaños...)
 *
 *
 *
 *
 *
 * @author Jose Javier BO
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    public Herramienta herramientaActual;

    /**
     * Constructor. Inicia los componentes de la GUI, asigna el listener para
     * escuchar los clicks en los radiobuttons de herramientas y la escucha de
     * raton sobre el panel de dibujo que es de clase Lienzo.
     *
     * - Cuando el listener de botones de herramientas detecta la selección de
     * una nueva herramienta el lístener informa a esta clase de qué herramienta
     * ha sido la elegida para que la instancie y la establezca como
     * "herramienta actual" que será con la que se dibuje.
     *
     * - Cuando el listener de ratón recibe algun evento recoge la herramienta
     * actual definida en esta clase y le manda mensajes a la herramienta según
     * el evento recibido.
     *
     * - Las herramientas durante su actividad recogen los datos de
     * configuracion para dibujar de los componentes de GUI de esta clase.
     * Ademas algunas actualizan información de algunos componentes.
     *
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
    void actualizaCoordenadas(int x, int y) {

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
     * Pone los valores por defecto en las interfaz gráfica
     */
    public void resetValores() {
        inputRectRedAnchoCirculo.setText("20");
        inputRectRedAltoCirculo.setText("20");
        inputArcoInicio.setText("0");
        inputArcoAngulo.setText("135");
        inputPoligonoNumPuntos.setText("3");
        inputPolilineaNumPuntos.setText("3");
        inputTexto.setText("Escribe un texto");
        inputTextoTamano.setText("40");
        inputComunGrosor.setText("2");
        inputComunAncho.setText("200");
        inputComunAlto.setText("250");
    }

    /**
     * Agrega el valor recibido al grosor de trazo actual. Tiene una limitación inferior
     * de 1
     * 
     * @param i El valor a agregar. Negativo para reducir el trazo y positivo para aumentarlo
     */
    void cambiaGrosorTrazo(int i) {
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
        lbColor = new javax.swing.JLabel();
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
        panelColorSeleccionado = new javax.swing.JPanel();
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
        lienzo = new ud2_ejer2.gui.ventanas.Lienzo();

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

        panTipoGrafico1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Rectángulo redondo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

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

        panTipoGrafico2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Arco", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        buttonGroupHerramientas.add(rbArco);
        rbArco.setText("Arco");
        rbArco.setActionCommand("arco");

        lbInicio.setText("Inicio:");

        lbAngulo.setText("Ángulo");

        inputArcoInicio.setText("0");

        inputArcoAngulo.setText("180");

        javax.swing.GroupLayout panTipoGrafico2Layout = new javax.swing.GroupLayout(panTipoGrafico2);
        panTipoGrafico2.setLayout(panTipoGrafico2Layout);
        panTipoGrafico2Layout.setHorizontalGroup(
            panTipoGrafico2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panTipoGrafico2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbArco)
                .addGap(18, 18, Short.MAX_VALUE)
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
                        .addComponent(inputArcoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panTipoGrafico3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Polígono", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

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

        panTipoGrafico4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Polilínea", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

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

        panTipoGrafico5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Texto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

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

        lbColor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbColor.setText("Color");

        lbGrosor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbGrosor.setText("Grosor");

        inputComunGrosor.setText("1");
        inputComunGrosor.setToolTipText("");

        lbComunTamano.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbComunTamano.setText("Tamaño");

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
            .addGap(0, 103, Short.MAX_VALUE)
        );
        panelColorSeleccionadoLayout.setVerticalGroup(
            panelColorSeleccionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panOpcionescomunesLayout = new javax.swing.GroupLayout(panOpcionescomunes);
        panOpcionescomunes.setLayout(panOpcionescomunesLayout);
        panOpcionescomunesLayout.setHorizontalGroup(
            panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                        .addComponent(lbColor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelColorSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbGrosor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComunGrosor, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                        .addComponent(lbComunTamano)
                        .addGap(18, 18, 18)
                        .addComponent(lbComunAncho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComunAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbComunAlto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputComunAlto, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                        .addComponent(lbComunRelleno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbSoloBorde)
                        .addGap(17, 17, 17)
                        .addComponent(rbComunrelleno)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panOpcionescomunesLayout.setVerticalGroup(
            panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOpcionescomunesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbColor)
                        .addComponent(lbGrosor)
                        .addComponent(inputComunGrosor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelColorSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbComunTamano)
                    .addComponent(lbComunAncho)
                    .addComponent(inputComunAncho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbComunAlto)
                    .addComponent(inputComunAlto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panOpcionescomunesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbComunRelleno)
                    .addComponent(rbSoloBorde)
                    .addComponent(rbComunrelleno))
                .addContainerGap())
        );

        panelCoordenadas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Coordenadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lbRaton.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lbRaton.setText("Raton");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
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

        panelListaPuntos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Lista Puntos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

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
                        .addGroup(panGeneralHerramientasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelCoordenadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panOpcionescomunes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap())
        );

        scrollHeramientas.setViewportView(panGeneralHerramientas);

        lienzo.setBackground(new java.awt.Color(255, 255, 255));
        lienzo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout lienzoLayout = new javax.swing.GroupLayout(lienzo);
        lienzo.setLayout(lienzoLayout);
        lienzoLayout.setHorizontalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 656, Short.MAX_VALUE)
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
                .addComponent(scrollHeramientas, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lienzo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    /**
     * Limpia el lienzo y resetea la herramienta actual
     *
     * @param evt
     */
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        //limpiar el lienzo
        Graphics2D g = lienzo.getBufferG2D();
        g.setColor(Color.white);
        g.fillRect(0, 0, lienzo.buffer.getWidth(), lienzo.buffer.getHeight());
        lienzo.repaint();

        //refrescar la herramienta actual
        herramientaActual.desactivar();
        setHerramienta(herramientaActual.getClass());
    }//GEN-LAST:event_btnLimpiarActionPerformed

// <editor-fold defaultstate="collapsed" desc="Variables generadas"> 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.ButtonGroup buttonGroupHerramientas;
    public javax.swing.ButtonGroup buttonGroupRelleno;
    public javax.swing.JTextField inputArcoAngulo;
    public javax.swing.JTextField inputArcoInicio;
    public javax.swing.JTextField inputComunAlto;
    public javax.swing.JTextField inputComunAncho;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAltoCirculo;
    private javax.swing.JLabel lbAnchoCirculo;
    private javax.swing.JLabel lbAngulo;
    private javax.swing.JLabel lbColor;
    private javax.swing.JLabel lbComunAlto;
    private javax.swing.JLabel lbComunAncho;
    private javax.swing.JLabel lbComunRelleno;
    private javax.swing.JLabel lbComunTamano;
    private javax.swing.JLabel lbEstilo;
    private javax.swing.JLabel lbFuente;
    private javax.swing.JLabel lbGrosor;
    private javax.swing.JLabel lbInicio;
    private javax.swing.JLabel lbNPuntos;
    private javax.swing.JLabel lbPuntosRestantes;
    private javax.swing.JLabel lbRaton;
    private javax.swing.JLabel lbTamano;
    private javax.swing.JLabel lbX;
    private javax.swing.JLabel lbY;
    private javax.swing.JLabel lbnpuntos;
    private ud2_ejer2.gui.ventanas.Lienzo lienzo;
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
    private javax.swing.JPanel panelListaPuntos;
    private javax.swing.JRadioButton rbArco;
    private javax.swing.JRadioButton rbComunrelleno;
    private javax.swing.JRadioButton rbDibujoLibre;
    private javax.swing.JRadioButton rbLinea;
    private javax.swing.JRadioButton rbOvalo;
    private javax.swing.JRadioButton rbPoligono;
    private javax.swing.JRadioButton rbPolilinea;
    private javax.swing.JRadioButton rbRectangulo;
    private javax.swing.JRadioButton rbRectanguloRedondo;
    private javax.swing.JRadioButton rbSoloBorde;
    private javax.swing.JRadioButton rbTexto;
    private javax.swing.JScrollPane scrollHeramientas;
    public javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
//</editor-fold>
//</editor-fold>

}