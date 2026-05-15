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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertarPanel extends JPanel {
	
    private static final long serialVersionUID = 1L;
    private EmpleadoDAO empleadoDAO;
    private static ConexionBD conexionBD = new ConexionBD();
    private JTextField txtNombre, txtApellido, txtDni, txtEmail, txtTelefono, txtSalario, txtFechaNac, txtFechaCon;
    private JComboBox<String> cbDepartamento, cbPuesto, cbSede;
    private JCheckBox chkActivo;
    
    // ── PALETA ───────────────────────────────────────────────────
    private static final Color BG         = new Color(26, 28, 46);
    private static final Color BG_DARK    = new Color(20, 22, 39);
    private static final Color SURFACE    = new Color(36, 39, 64);
    private static final Color BORDER_C   = new Color(39, 42, 66);
    private static final Color FIELD_BG   = new Color(36, 39, 64);
    private static final Color FIELD_BOR  = new Color(70, 80, 140);
    private static final Color FIELD_BOR_FOCUS = new Color(100, 140, 255);
    private static final Color ACCENT     = new Color(80, 70, 200);
    private static final Color ACCENT_HOV = new Color(96, 86, 216);
    private static final Color BLUE       = new Color(60, 120, 255);
    private static final Color BLUE_HOV   = new Color(80, 140, 255);
    private static final Color TEXT_LABEL = new Color(200, 210, 255);
    private static final Color TEXT_INPUT = new Color(230, 235, 255);
    private static final Color TEXT_SUB   = new Color(130, 145, 200);
    private static final Color TEXT_TITLE = Color.WHITE;
    private static final Color COMBO_BG   = new Color(30, 33, 55);
    private static final Color COMBO_BOR  = new Color(70, 80, 140);
    
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
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG);
        form.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 10, 7, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
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
        
        cargarCombos();
        
        agregarFilaDoble(form, gbc, 0, "Nombre", txtNombre, "Apellido", txtApellido);
        agregarFilaDoble(form, gbc, 1, "DNI", txtDni, "Email", txtEmail);
        agregarFilaDoble(form, gbc, 2, "Teléfono", txtTelefono, "Salario", txtSalario);
        agregarFilaDoble(form, gbc, 3, "F. nacimiento (yyyy-MM-dd)", txtFechaNac, "F. contratación (yyyy-MM-dd)", txtFechaCon);
        agregarFilaDoble(form, gbc, 4, "Departamento", cbDepartamento, "Puesto", cbPuesto);
        agregarFilaDoble(form, gbc, 5, "Sede", cbSede, "", chkActivo);
        
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
        
        add(form, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }
    
    // ── HELPERS DE LAYOUT ────────────────────────────────────────
    private void agregarFilaDoble(JPanel p, GridBagConstraints gbc, int fila, String lbl1, JComponent field1, String lbl2, JComponent field2) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0;
        p.add(makeLabel(lbl1), gbc);
        
        gbc.gridx = 1; gbc.weightx = 0.5;
        p.add(field1, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0;
        p.add(Box.createHorizontalStrut(24), gbc);
        
        gbc.gridx = 3; gbc.weightx = 0;
        p.add(makeLabel(lbl2), gbc);
        
        gbc.gridx = 4; gbc.weightx = 0.5;
        p.add(field2, gbc);
    }
    
    private JLabel makeLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(TEXT_LABEL);
        return l;
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
    
    @SuppressWarnings("serial")
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
                
                int cx = getWidth() - 16;
                int cy = getHeight() / 2;
                int[] xs = {cx - 4, cx + 4, cx};
                int[] ys = {cy - 2, cy - 2, cy + 3};
                g2.setColor(BLUE);
                g2.fillPolygon(xs, ys, 3);
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
        	public Component getListCellRendererComponent(JList<?> list, Object value,
        	        int index, boolean sel, boolean foc) {
        	    super.getListCellRendererComponent(list, value, index, sel, foc);
        	    if (sel) {
        	        setBackground(new Color(60, 70, 120));
        	        setForeground(Color.WHITE);
        	    } else {
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

    @SuppressWarnings("serial")
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
    	        
    	        GradientPaint gp = new GradientPaint(0, 0, colorInicio, getWidth(), 0, colorFin);
    	        g2.setPaint(gp);
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

    @SuppressWarnings("serial")
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

    // ── LÓGICA ──────────────────────────────────────────────────
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al insertar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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