package panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import dao.RecuperarPasswordDAO;
import conexionBD.ConexionBD;

public class RecuperarPasswordPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
    private static ConexionBD conexionBD = new ConexionBD();
    private JTextField txtEmail;
    private JPasswordField txtPass;
    private JPasswordField txtConfirm;
    
    // ── PALETA ───────────────────────────────────────────────────
    private static final Color BG_DARK      = new Color(34, 37, 54);
    private static final Color PANEL_BG     = new Color(51, 54, 74);
    private static final Color ACCENT       = new Color(80, 70, 200);
    private static final Color ACCENT_HOVER = new Color(100, 90, 220);
    private static final Color FIELD_BG     = new Color(35, 37, 58);
    private static final Color FIELD_BORDER = new Color(74, 77, 102);
    private static final Color TEXT_PRIMARY = new Color(232, 233, 245);
    private static final Color TEXT_LABEL   = new Color(200, 202, 223);
    private static final Color TEXT_MUTED   = new Color(160, 163, 190);
	
	/**
	 * Create the panel.
	 */
	public RecuperarPasswordPanel() {
		setLayout(new GridBagLayout());
        setBackground(BG_DARK);
        setPreferredSize(new Dimension(480, 500));
        
        initComponents();
	}
	
	private void initComponents() {
        RoundedPanel panel = new RoundedPanel(18);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(340, 450));
        panel.setBackground(PANEL_BG);
        add(panel);

        JLabel avatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int size = 68;
                BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                Graphics2D ig = img.createGraphics();
                ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ig.setColor(ACCENT);
                ig.fillOval(0, 0, size, size);
                ig.setColor(new Color(235, 236, 248));
                ig.fillOval(21, 10, 26, 26);
                ig.fillOval(9, 42, 50, 38);
                ig.dispose();
                BufferedImage masked = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                Graphics2D mg = masked.createGraphics();
                mg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                mg.setColor(Color.WHITE);
                mg.fillOval(0, 0, size, size);
                mg.setComposite(AlphaComposite.SrcIn);
                mg.drawImage(img, 0, 0, null);
                mg.dispose();
                g2.drawImage(masked, 0, 0, null);
                g2.dispose();
            }
            @Override
            public Dimension getPreferredSize() { return new Dimension(68, 68); }
        };
        avatar.setBounds(136, 20, 68, 68);
        panel.add(avatar);

        JLabel lblSubtitle = new JLabel("RECUPERAR CONTRASEÑA", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblSubtitle.setForeground(TEXT_MUTED);
        lblSubtitle.setBounds(0, 100, 340, 20);
        panel.add(lblSubtitle);

        JSeparator sep = new JSeparator();
        sep.setForeground(FIELD_BORDER);
        sep.setBackground(FIELD_BORDER);
        sep.setBounds(30, 128, 280, 1);
        panel.add(sep);

        JLabel lblEmail = new JLabel("Correo electrónico");
        lblEmail.setBounds(30, 148, 280, 16);
        lblEmail.setForeground(TEXT_LABEL);
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 11));
        panel.add(lblEmail);

        txtEmail = new JTextField();
        styleField(txtEmail);
        txtEmail.setBounds(30, 168, 280, 38);
        panel.add(txtEmail);

        JLabel lblPass = new JLabel("Nueva contraseña");
        lblPass.setBounds(30, 218, 280, 16);
        lblPass.setForeground(TEXT_LABEL);
        lblPass.setFont(new Font("SansSerif", Font.PLAIN, 11));
        panel.add(lblPass);

        txtPass = new JPasswordField();
        styleField(txtPass);
        txtPass.setBounds(30, 238, 280, 38);
        panel.add(txtPass);

        JLabel lblConfirm = new JLabel("Confirmar contraseña");
        lblConfirm.setBounds(30, 288, 280, 16);
        lblConfirm.setForeground(TEXT_LABEL);
        lblConfirm.setFont(new Font("SansSerif", Font.PLAIN, 11));
        panel.add(lblConfirm);

        txtConfirm = new JPasswordField();
        styleField(txtConfirm);
        txtConfirm.setBounds(30, 308, 280, 38);
        panel.add(txtConfirm);

        JButton btnCambiar = new JButton("Cambiar contraseña") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) g2.setColor(ACCENT_HOVER);
                else g2.setColor(ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnCambiar.setBounds(30, 364, 280, 42);
        btnCambiar.setForeground(Color.WHITE);
        btnCambiar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnCambiar.setFocusPainted(false);
        btnCambiar.setBorderPainted(false);
        btnCambiar.setContentAreaFilled(false);
        btnCambiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarPassword();
            }
        });
        panel.add(btnCambiar);

        JLabel lblVolver = new JLabel("← Volver al inicio de sesión", SwingConstants.CENTER);
        lblVolver.setBounds(0, 420, 340, 16);
        lblVolver.setForeground(TEXT_MUTED);
        lblVolver.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblVolver.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                SwingUtilities.getWindowAncestor(RecuperarPasswordPanel.this).dispose();
            }
            @Override public void mouseEntered(MouseEvent e) { lblVolver.setForeground(ACCENT_HOVER); }
            @Override public void mouseExited(MouseEvent e)  { lblVolver.setForeground(TEXT_MUTED); }
        });
        panel.add(lblVolver);
    }

    // ── LÓGICA ────────────────────────────────────────────────────
    private void cambiarPassword() {
        String email   = txtEmail.getText().trim();
        String pass    = new String(txtPass.getPassword());
        String confirm = new String(txtConfirm.getPassword());

        String error = null;
        if (email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            error = "Por favor, completa todos los campos";
        } else if (!pass.equals(confirm)) {
            error = "Las contraseñas no coinciden";
        } else if (pass.length() < 4) {
            error = "La contraseña debe tener al menos 4 caracteres";
        } else {
            RecuperarPasswordDAO dao = new RecuperarPasswordDAO(conexionBD);
            if (!dao.existeEmail(email)) {
                error = "No existe un usuario con ese correo electrónico";
            } else {
                if (dao.actualizarPassword(email, pass)) {
                    JOptionPane.showMessageDialog(this,
                        "¡Contraseña actualizada correctamente!",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.getWindowAncestor(this).dispose();
                    return;
                } else {
                    error = "Error al actualizar la contraseña en la base de datos";
                }
            }
        }
        if (error != null)
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ── HELPERS DE ESTILO ────────────────────────────────────────
    private void styleField(JTextField field) {
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(FIELD_BORDER, 1, true),
            new EmptyBorder(0, 10, 0, 10)
        ));
    }

    // ── PANEL REDONDEADO ─────────────────────────────────────────
    @SuppressWarnings("serial")
    static class RoundedPanel extends JPanel {
        private final int radius;
        RoundedPanel(int radius) { this.radius = radius; setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius * 2, radius * 2));
        }
    }
}