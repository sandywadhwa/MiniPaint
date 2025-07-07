import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import static java.lang.Math.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.logging.Logger;
import java.util.logging.Level;

abstract class Shape {
    int x1, y1, x2, y2;
    Color c;

    public Shape(int x1, int y1, int x2, int y2, Color c) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.c = c;
    }

    abstract void draw(Graphics g);

}

class RectangleShape extends Shape {

    int tlX, tlY, w, h;
    boolean isFill;

    public RectangleShape(int x1, int y1, int x2, int y2, Color c, boolean isFill) {
        super(x1, y1, x2, y2, c);
        w = abs(x2 - x1);
        h = Math.abs(y2 - y1);
        tlX = Math.min(x1, x2);
        tlY = Math.min(y1, y2);
        this.isFill = isFill;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(c);
        if (isFill) {
            g.fillRect(tlX, tlY, w, h);
        } else {
            g.drawRect(tlX, tlY, w, h);
        }
    }
}

class OvalShape extends Shape {

    int tlX, tlY, w, h;
    boolean isFill;

    public OvalShape(int x1, int y1, int x2, int y2, Color c, boolean isFill) {
        super(x1, y1, x2, y2, c);
        w = abs(x2 - x1);
        h = Math.abs(y2 - y1);
        tlX = Math.min(x1, x2);
        tlY = Math.min(y1, y2);
        this.isFill = isFill;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(c);
        if (isFill) {
            g.fillOval(tlX, tlY, w, h);
        } else {
            g.drawOval(tlX, tlY, w, h);
        }
    }
}

class LineShape extends Shape {

    public LineShape(int x1, int y1, int x2, int y2, Color c) {
        super(x1, y1, x2, y2, c);
    }

    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(x1, y1, x2, y2);
        g2d.setStroke(new BasicStroke(1));

    }
}

public class BeingZeroMiniPaint extends javax.swing.JFrame {

    Color color = Color.red;
    String selectedShape = "Line";
    DrawPanel drawPanel = new DrawPanel();
    ArrayList<Shape> shapes = new ArrayList<>();
    Stack<Shape> undo = new Stack<>();
    Stack<Shape> redo = new Stack<>();
    int x1, y1, x2, y2;
    Shape tempShape;
    boolean isFill;

    class DrawPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (Shape s : shapes) {
                s.draw(g);
            }
            if (tempShape != null)
                tempShape.draw(g);
        }

        public DrawPanel() {
            setBackground(Color.white);
            this.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    lblMousePos.setText(e.getX() + "," + e.getY() + " px");
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    x2 = e.getX();
                    y2 = e.getY();
                    switch (selectedShape) {
                    case "Line":
                        tempShape = new LineShape(x1, y1, x2, y2, color);
                        break;
                    case "Oval":
                        tempShape = new OvalShape(x1, y1, x2, y2, color, isFill);
                        break;
                    case "Rectangle":
                        tempShape = new RectangleShape(x1, y1, x2, y2, color, isFill);
                        break;
                    }
                    repaint();
                }

            });
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    x1 = e.getX();
                    y1 = e.getY();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    x2 = e.getX();
                    y2 = e.getY();
                    Shape s = null;
                    switch (selectedShape) {
                    case "Line":
                        s = new LineShape(x1, y1, x2, y2, color);
                        break;
                    case "Oval":
                        s = new OvalShape(x1, y1, x2, y2, color, isFill);
                        break;
                    case "Rectangle":
                        s = new RectangleShape(x1, y1, x2, y2, color, isFill);
                        break;
                    }
                    shapes.add(s);
                    undo.push(s);
                    repaint();
                }

            });
        }
    }

    public BeingZeroMiniPaint() {
        initComponents();
        setSize(500, 350);
        getContentPane().add(drawPanel);
        pnlColor.setBackground(Color.red);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        pnlStatus = new javax.swing.JPanel();
        lblMousePos = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        pnlColor = new javax.swing.JPanel();
        cbShape = new javax.swing.JComboBox();
        tglBtnFilled = new javax.swing.JToggleButton();
        jbtnRedo = new javax.swing.JButton();
        jbtnUndo = new javax.swing.JButton();
        jbtnClear = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Being Zero - Mini Paint");

        pnlStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlStatus.setPreferredSize(new Dimension (649, 25));

        javax.swing.GroupLayout pnlStatusLayout = new javax.swing.GroupLayout(pnlStatus);
        pnlStatus.setLayout(pnlStatusLayout);
        pnlStatusLayout.setHorizontalGroup(pnlStatusLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlStatusLayout.createSequentialGroup().addComponent(lblMousePos,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 517, Short.MAX_VALUE)));
        pnlStatusLayout.setVerticalGroup(pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblMousePos, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE));

        getContentPane().add(pnlStatus,BorderLayout.PAGE_END);

        jToolBar1.setRollover(true);

        pnlColor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlColor.setMaximumSize(new Dimension (50, 50));
        pnlColor.setPreferredSize(new Dimension (30, 30));
        pnlColor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                pnlColorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlColorLayout = new javax.swing.GroupLayout(pnlColor);
        pnlColor.setLayout(pnlColorLayout);
        pnlColorLayout.setHorizontalGroup(pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 50, Short.MAX_VALUE));
        pnlColorLayout.setVerticalGroup(pnlColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 30, Short.MAX_VALUE));

        jToolBar1.add(pnlColor);

        cbShape.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Line", "Oval", "Rectangle" }));
        cbShape.setMaximumSize(new Dimension (80, 30));
        cbShape.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                cbShapeItemStateChanged(evt);
            }
        });
        jToolBar1.add(cbShape);

        tglBtnFilled.setText("FILLED");
        tglBtnFilled.setFocusable(false);
        tglBtnFilled.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tglBtnFilled.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tglBtnFilled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                tglBtnFilledItemStateChanged(evt);
            }
        });
        tglBtnFilled.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent  evt) {
                tglBtnFilledActionPerformed(evt);
            }
        });
        jToolBar1.add(tglBtnFilled);

        jbtnRedo.setText("Redo");
        jbtnRedo.setFocusable(false);
        jbtnRedo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnRedo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jbtnRedo);

        jbtnUndo.setText("Undo");
        jbtnUndo.setFocusable(false);
        jbtnUndo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnUndo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jbtnUndo);

        jbtnClear.setText("Clear");
        jbtnClear.setFocusable(false);
        jbtnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent  evt) {
                jbtnClearActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnClear);

        getContentPane().add(jToolBar1,BorderLayout.PAGE_START);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>

    private void pnlColorMouseClicked(MouseEvent evt) {
        Color c = JColorChooser.showDialog(rootPane, null, Color.yellow);
        pnlColor.setBackground(c);
        color = c;
    }

    private void cbShapeItemStateChanged(ItemEvent evt) {
        selectedShape = cbShape.getSelectedItem().toString();
    }

    private void tglBtnFilledActionPerformed(ActionEvent evt) {

    }

    private void tglBtnFilledItemStateChanged(ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            isFill = true;
        } else
            isFill = false;
    }

    private void jbtnClearActionPerformed(ActionEvent  evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BeingZeroMiniPaint.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(BeingZeroMiniPaint.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BeingZeroMiniPaint.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BeingZeroMiniPaint.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
       EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BeingZeroMiniPaint().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JComboBox cbShape;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbtnClear;
    private javax.swing.JButton jbtnRedo;
    private javax.swing.JButton jbtnUndo;
    private javax.swing.JLabel lblMousePos;
    private javax.swing.JPanel pnlColor;
    private javax.swing.JPanel pnlStatus;
    private javax.swing.JToggleButton tglBtnFilled;
    // End of variables declaration
}
