package vista.admin;

import dao.EmpleadoDAO;
import dao.DepartamentoDAO;
import dao.PuestoDAO;
import dao.SedeDAO;
import modelo.Empleado;
import conexionBD.ConexionBD;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InsertarPanel extends JPanel {
	
    private static final long serialVersionUID = 1L;
    private EmpleadoDAO empleadoDAO;
    private static ConexionBD conexionBD = new ConexionBD();
    private JTextField txtNombre, txtApellido, txtDni, txtEmail, txtTelefono, txtSalario, txtFechaNac, txtFechaCon;
    private JComboBox<String> cbDepartamento, cbPuesto, cbSede;
    private JCheckBox chkActivo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    
    // ── PALETA ───────────────────────────────────────────────────
    private static final Color BG           = new Color(26, 28, 46);
    private static final Color BG_DARK      = new Color(20, 22, 39);
    private static final Color SURFACE      = new Color(36, 39, 64);
    private static final Color BORDER_C     = new Color(39, 42, 66);
    private static final Color FIELD_BG     = new Color(36, 39, 64);
    private static final Color FIELD_BOR    = new Color(70, 80, 140);
    private static final Color FIELD_BOR_FOCUS = new Color(100, 140, 255);
    private static final Color ACCENT       = new Color(80, 70, 200);
    private static final Color ACCENT_HOV   = new Color(96, 86, 216);
    private static final Color BLUE         = new Color(60, 120, 255);
    private static final Color BLUE_HOV     = new Color(80, 140, 255);
    private static final Color TEXT_LABEL   = new Color(200, 210, 255);
    private static final Color TEXT_INPUT   = new Color(230, 235, 255);
    private static final Color TEXT_SUB     = new Color(130, 145, 200);
    private static final Color TEXT_TITLE   = Color.WHITE;
    private static final Color TEXT_HEAD    = new Color(90, 93, 136);
    private static final Color COMBO_BG     = new Color(30, 33, 55);
    private static final Color COMBO_BOR    = new Color(70, 80, 140);
    private static final Color TABLE_SEL    = new Color(60, 70, 130);
    private static final Color TABLE_ROW     = new Color(32, 35, 58);
    private static final Color TABLE_ROW_ALT = new Color(28, 31, 52);
    private static final Color TABLE_GRID    = new Color(45, 50, 80);
    private static final Color THUMB_COL    = new Color(120, 120, 170, 150);
    private static final Color THUMB_HOV    = new Color(120, 120, 170, 220);
    
    public InsertarPanel(EmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
        setLayout(new BorderLayout());
        setBackground(BG);
        
        initComponents();
    }
    
    private void initComponents() {
        // ── HEADER ──────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);
        header.setBorder(new EmptyBorder(22, 28, 18, 28));
        
        JLabel lblTitulo = new JLabel("Insertar empleado");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitulo.setForeground(TEXT_TITLE);
        
        JLabel lblSub = new JLabel("Rellena los datos para registrar un nuevo empleado");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(TEXT_SUB);
        
        JPanel headerText = new JPanel();
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.setOpaque(false);
        headerText.add(lblTitulo);
        headerText.add(Box.createVerticalStrut(4));
        headerText.add(lblSub);
        header.add(headerText, BorderLayout.WEST);
        
        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.setOpaque(false);
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(sep(), BorderLayout.SOUTH);
        add(headerWrap, BorderLayout.NORTH);
        
        // ── FORMULARIO ──────────────────────────────────────────
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(BG);
        form.setBorder(new EmptyBorder(16, 28, 16, 28));
        
        txtNombre   = styledField();
        txtApellido = styledField();
        txtDni      = styledField();
        txtEmail    = styledField();
        txtTelefono = styledField();
        txtSalario  = styledField();
        txtFechaNac = styledField();
        txtFechaCon = styledField();
        
        cbDepartamento = styledCombo();
        cbPuesto       = styledCombo();
        cbSede         = styledCombo();
        
        chkActivo = new JCheckBox("Activo");
        chkActivo.setSelected(true);
        chkActivo.setOpaque(false);
        chkActivo.setForeground(TEXT_LABEL);
        chkActivo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        chkActivo.setFocusPainted(false);
        chkActivo.setBackground(BG);
        chkActivo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cargarCombos();
        
        String[] labels = {
    	    "Nombre", "Apellido", "DNI", "Email", "Teléfono", "Salario",
    	    "F. nacimiento (yyyy-MM-dd)", "F. contratación (yyyy-MM-dd)",
    	    "Departamento", "Puesto", "Sede"
    	};
        
    	JComponent[] fields = {
    	    txtNombre, txtApellido, txtDni, txtEmail, txtTelefono, txtSalario,
    	    txtFechaNac, txtFechaCon, cbDepartamento, cbPuesto, cbSede
    	};
    	
    	for (int i = 0; i < labels.length; i++) {
    	    form.add(formRow(labels[i], fields[i]));
    	    form.add(Box.createVerticalStrut(8));
    	}
    	form.add(chkActivo);
    	
        JScrollPane scrollForm = new JScrollPane(form);
        scrollForm.setBorder(BorderFactory.createEmptyBorder());
        scrollForm.getViewport().setBackground(BG);
        estilizarScrollbar(scrollForm.getVerticalScrollBar());
        
        // ── TABLA DE EMPLEADOS ───────────────────────────────────
        modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Nombre", "Apellido", "DNI", "Departamento", "Puesto", "Sede"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        
        tabla.setBackground(TABLE_ROW);
        tabla.setForeground(TEXT_INPUT);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(34);
        tabla.setGridColor(TABLE_GRID);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setSelectionBackground(TABLE_SEL);
        tabla.setSelectionForeground(TEXT_TITLE);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setFillsViewportHeight(true);
        tabla.setAutoCreateRowSorter(true);
        
        JTableHeader th = tabla.getTableHeader();
        th.setBackground(BG_DARK);
        th.setForeground(TEXT_HEAD);
        th.setFont(new Font("SansSerif", Font.BOLD, 11));
        th.setPreferredSize(new Dimension(0, 36));
        th.setBorder(BorderFactory.createEmptyBorder());
        th.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                lbl.setBackground(BG_DARK);
                lbl.setForeground(TEXT_HEAD);
                lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
                lbl.setBorder(new EmptyBorder(0, 14, 0, 14));
                if (val != null) lbl.setText(val.toString().toUpperCase());
                return lbl;
            }
        });
        
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                setFont(new Font("SansSerif", Font.PLAIN, 13));
                if (sel) {
                    setBackground(TABLE_SEL);
                    setForeground(TEXT_TITLE);
                } else {
                	Color colorFila;
                	if (row % 2 == 0) colorFila = TABLE_ROW;
                	else colorFila = TABLE_ROW_ALT;
                	setBackground(colorFila);
                    setForeground(TEXT_INPUT);
                }
                return this;
            }
        });
        
        tabla.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JPanel cell = new JPanel(new GridBagLayout());
                
                String texto = "";
                if (value != null) texto = value.toString();
                JLabel badge = new JLabel(texto);
                
                badge.setFont(new Font("SansSerif", Font.BOLD, 11));
                badge.setForeground(new Color(200, 210, 255));
                badge.setOpaque(true);
                badge.setBackground(new Color(37, 40, 66));
                badge.setBorder(new EmptyBorder(2, 10, 2, 10));
                cell.setOpaque(true);
                
                if (isSelected) {
                    cell.setBackground(TABLE_SEL);
                } else {
                	if (row % 2 == 0) cell.setBackground(TABLE_ROW);
                	else cell.setBackground(TABLE_ROW_ALT);
                }
                cell.add(badge);
                return cell;
            }
        });
        
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.getViewport().setBackground(BG);
        estilizarScrollbar(scrollTabla.getVerticalScrollBar());
        cargarTabla();
        
        // ── SPLIT PANE ───────────────────────────────────────────
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollForm, scrollTabla);
        split.setDividerLocation(340);
        split.setBackground(BG);
        split.setBorder(null);
        split.setDividerSize(6);
        split.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(BORDER_C);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
            }
        });
        
        // ── FOOTER ──────────────────────────────────────────────
        JButton btnInsertar = blueBtn("Insertar empleado");
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertar();
            }
        });
        
        JButton btnLimpiar = ghostBtn("Limpiar");
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        footer.setBackground(BG_DARK);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_C));
        footer.add(btnLimpiar);
        footer.add(btnInsertar);
        
        add(split, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }
    
    // ── HELPERS DE LAYOUT ────────────────────────────────────────
    private JPanel formRow(String labelTxt, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lbl = new JLabel(labelTxt);
        lbl.setForeground(TEXT_SUB);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setPreferredSize(new Dimension(200, 20));
        
        row.add(lbl, BorderLayout.NORTH);
        row.add(field, BorderLayout.CENTER);
        return row;
    }
    
    // ── HELPERS DE ESTILO ────────────────────────────────────────
    private JSeparator sep() {
        JSeparator s = new JSeparator();
        s.setForeground(BORDER_C);
        s.setBackground(BORDER_C);
        return s;
    }
    
    private JTextField styledField() {
        JTextField f = new JTextField(20);
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT_INPUT);
        f.setCaretColor(Color.WHITE);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(FIELD_BOR, 1, true),
                new EmptyBorder(7, 10, 7, 10)));
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(FIELD_BOR_FOCUS, 2, true),
                        new EmptyBorder(6, 9, 6, 9)));
            }
            @Override public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(FIELD_BOR, 1, true),
                        new EmptyBorder(7, 10, 7, 10)));
            }
        });
        return f;
    }
    
    private JComboBox<String> styledCombo() {
        JComboBox<String> cb = new JComboBox<String>() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COMBO_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(COMBO_BOR);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 6, 6);
                String sel = (String) getSelectedItem();
                if (sel != null) {
                    g2.setColor(TEXT_INPUT);
                    g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(sel, 10, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                }
                int cx = getWidth() - 16, cy = getHeight() / 2;
                g2.setColor(BLUE);
                g2.fillPolygon(new int[]{cx - 4, cx + 4, cx}, new int[]{cy - 2, cy - 2, cy + 3}, 3);
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height = 34;
                return d;
            }
        };
        cb.setOpaque(false);
        cb.setBorder(BorderFactory.createEmptyBorder());
        cb.setFocusable(false);
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean sel, boolean foc) {
                super.getListCellRendererComponent(list, value, index, sel, foc);
                if (sel) {
                	setBackground(new Color(60, 70, 120));
                	setForeground(Color.WHITE);
                }
                else {
                	setBackground(COMBO_BG);
                	setForeground(TEXT_INPUT);
                }
                setFont(new Font("SansSerif", Font.PLAIN, 13));
                setBorder(new EmptyBorder(6, 10, 6, 10));
                return this;
            }
        });
        cb.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                Object popup = cb.getUI().getAccessibleChild(cb, 0);
                if (popup instanceof JPopupMenu) {
                    JScrollPane sp = (JScrollPane)((JPopupMenu)popup).getComponent(0);
                    sp.getViewport().getView().setBackground(COMBO_BG);
                }
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
        return cb;
    }
    
    private JButton blueBtn(String txt) {
        JButton b = new JButton(txt) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color colorInicio;
                Color colorFin;
                if (getModel().isRollover()) {
                    colorInicio = BLUE_HOV;
                    colorFin = ACCENT_HOV;
                } else {
                    colorInicio = BLUE;
                    colorFin = ACCENT;
                }
                
                g2.setPaint(new GradientPaint(0, 0, colorInicio, getWidth(), 0, colorFin));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 26, 8, 26));
        return b;
    }
    
    private JButton ghostBtn(String txt) {
        JButton b = new JButton(txt) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color colorFondo;
                if (getModel().isRollover()) colorFondo = SURFACE;
                else colorFondo = new Color(0, 0, 0, 0);
                
                g2.setColor(colorFondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(FIELD_BOR);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setForeground(TEXT_SUB);
        b.setFont(new Font("SansSerif", Font.PLAIN, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 20, 8, 20));
        return b;
    }
    
    private void estilizarScrollbar(JScrollBar bar) {
        bar.setPreferredSize(new Dimension(12, 0));
        bar.setOpaque(false);
        bar.setUI(new BasicScrollBarUI() {
            @Override protected JButton createDecreaseButton(int o) { return emptyBtn(); }
            @Override protected JButton createIncreaseButton(int o) { return emptyBtn(); }
            private JButton emptyBtn() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(12, 8));
                b.setMinimumSize(new Dimension(12, 8));
                b.setMaximumSize(new Dimension(12, 8));
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                return b;
            }
            @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                if (r.isEmpty()) return;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int barW = 3, x = r.x + (r.width - barW) / 2;
                
                Color colorThumb;
                if (isThumbRollover()) colorThumb = THUMB_HOV;
                else colorThumb = THUMB_COL;
                g2.setColor(colorThumb);
                
                g2.fillRoundRect(x, r.y, barW, r.height, barW, barW);
                g2.dispose();
            }
        });
    }
    
    // ── LÓGICA ──────────────────────────────────────────────────
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Empleado> lista = empleadoDAO.obtenerTodosDesc();
        for (Empleado e : lista) {
            String[] fila = new String[7];
            fila[0] = String.valueOf(e.getIdEmpleado());
            fila[1] = e.getNombre();
            fila[2] = e.getApellido();
            fila[3] = e.getDni();
            fila[4] = e.getDepartamento().getNombreDepartamento();
            fila[5] = e.getPuesto().getNombrePuesto();
            fila[6] = e.getSede().getNombreSede();
            modeloTabla.addRow(fila);
        }
    }
    
    private void insertar() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNac = sdf.parse(txtFechaNac.getText());
            Date fechaCon = sdf.parse(txtFechaCon.getText());
            
            int idDep  = new DepartamentoDAO(conexionBD).obtenerTodos()
                            .get(cbDepartamento.getSelectedIndex()).getIdDepartamento();
            int idPue  = new PuestoDAO(conexionBD).obtenerTodos()
                            .get(cbPuesto.getSelectedIndex()).getIdPuesto();
            int idSede = new SedeDAO(conexionBD).obtenerTodos()
                            .get(cbSede.getSelectedIndex()).getIdSede();
            
            Empleado e = new Empleado(0,
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtDni.getText(),
                    txtEmail.getText(),
                    txtTelefono.getText(),
                    fechaNac,
                    fechaCon,
                    Double.parseDouble(txtSalario.getText()),
                    idDep,
                    idPue,
                    idSede,
                    chkActivo.isSelected());
            
            empleadoDAO.insertarEmpleado(e);
            JOptionPane.showMessageDialog(this, "Empleado insertado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al insertar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtSalario.setText("");
        txtFechaNac.setText("");
        txtFechaCon.setText("");
        cbDepartamento.setSelectedIndex(0);
        cbPuesto.setSelectedIndex(0);
        cbSede.setSelectedIndex(0);
        chkActivo.setSelected(true);
    }
    
    private void cargarCombos() {
        DepartamentoDAO departamentoDAO = new DepartamentoDAO(conexionBD);
        PuestoDAO puestoDAO = new PuestoDAO(conexionBD);
        SedeDAO sedeDAO = new SedeDAO(conexionBD);
        
        for (int i = 0; i < departamentoDAO.obtenerTodos().size(); i++) {
        	cbDepartamento.addItem(departamentoDAO.obtenerTodos().get(i).getNombreDepartamento());
        }
        
        for (int i = 0; i < puestoDAO.obtenerTodos().size(); i++) {
        	cbPuesto.addItem(puestoDAO.obtenerTodos().get(i).getNombrePuesto());
        }
        
        for (int i = 0; i < sedeDAO.obtenerTodos().size(); i++) {
        	cbSede.addItem(sedeDAO.obtenerTodos().get(i).getNombreSede());
        }
    }
}