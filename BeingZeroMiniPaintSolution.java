
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import static java.lang.Math.*;


class BeingZeroMainWindow extends JFrame
{
    private JMenuBar mnuBarMainMenuBar;
    private JToolBar tbarMainToolBar;
    private JPanel pnlDrawingCanvas;
    private JLabel lblMousePos;
    private JPanel pnlColorChooser;
    private int x1, y1, x2, y2;
    private boolean isFill;
    private Color color = Color.red;
    private String selectedShape = "Line";

    ArrayList<Shape> completedShapes = new ArrayList<>();
    Shape currentShape;

    class DrawingCanvas extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g); /* Clears up the Panel */

            /* Draw Previous Shapes */
            for (Shape completedShape : completedShapes) {
                completedShape.draw(g);
            }

            /* Draw Current Shape */
            if (currentShape != null)
                currentShape.draw(g);
        }

        public DrawingCanvas() {
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
                        currentShape = new LineShape(x1, y1, x2, y2, color);
                        break;
                    case "Oval":
                        currentShape = new OvalShape(x1, y1, x2, y2, color, isFill);
                        break;
                    case "Rectangle":
                        currentShape = new RectangleShape(x1, y1, x2, y2, color, isFill);
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
                    completedShapes.add(s);
                    currentShape = null;
                    repaint();
                }

            });

        }
    }
    
    public BeingZeroMainWindow() throws Exception {
        initComponents();
        setSize(500, 350);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Being Zero - Mini Paint");
        
    }
    

    private void initComponents() throws Exception {
        setNimbusLookAndFeel();
        createAndAddMainMenu();
        createAndAddToolbar();
        pnlDrawingCanvas  = new DrawingCanvas();
        getContentPane().add(pnlDrawingCanvas);
        JPanel pnlStatus = new JPanel();
        lblMousePos = new JLabel("(x,y)");
        pnlStatus.add(lblMousePos);
        getContentPane().add(pnlStatus, BorderLayout.PAGE_END);
        pack();
    }

    private void createAndAddComboBox(){
        JComboBox cmbShape = new JComboBox();
        cmbShape.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Line", "Oval", "Rectangle" }));
        cmbShape.setMaximumSize(new java.awt.Dimension(80, 30));
        // TODO:  Add Item Listener
        cmbShape.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                selectedShape = cmbShape.getSelectedItem().toString();
            }
        });

        tbarMainToolBar.add(cmbShape);
    }
    private void createAndAddMainMenu(){
        mnuBarMainMenuBar = new JMenuBar();

        JMenu  jFileMenu = new JMenu("File");
        
        JMenuItem mnuItemFileOpen = new JMenuItem("Open");
        jFileMenu.add(mnuItemFileOpen);
        
        jFileMenu.addSeparator();
        
        JMenuItem mnuItemFileQuit = new JMenuItem("Quit");
        jFileMenu.add(mnuItemFileQuit);

        mnuBarMainMenuBar.add(jFileMenu);

        JMenu  jHelpMenu = new JMenu("Help");

        mnuBarMainMenuBar.add(jHelpMenu);

        setJMenuBar(mnuBarMainMenuBar);
    }

    private void createAndAddToolbar(){
        tbarMainToolBar = new JToolBar();
        tbarMainToolBar.setRollover(true);

        pnlColorChooser = new JPanel();
        pnlColorChooser.setBackground(Color.red);
        pnlColorChooser.setMaximumSize(new java.awt.Dimension(50, 50));
        pnlColorChooser.setPreferredSize(new java.awt.Dimension(30, 30));
        pnlColorChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Color c = JColorChooser.showDialog(rootPane, null, Color.yellow);
                pnlColorChooser.setBackground(c);
                color = c;
            }
        });
        tbarMainToolBar.add(pnlColorChooser);

        JToggleButton tglBtnFilled = new JToggleButton("Filled");
        tglBtnFilled.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    isFill = true;
                } else
                    isFill = false;
            }
        });
        tbarMainToolBar.add(tglBtnFilled);

        createAndAddComboBox();

        getContentPane().add(tbarMainToolBar, BorderLayout.PAGE_START);

    }

    private void setNimbusLookAndFeel() throws Exception{
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    }
}

interface IShape{
    void draw(Graphics g);
}

abstract class Shape implements IShape {
    int x1, y1, x2, y2;
    Color c;

    public Shape(int x1, int y1, int x2, int y2, Color c) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.c = c;
    }

    public abstract void draw(Graphics g);

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
    public void draw(Graphics g) {
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
    public void draw(Graphics g) {
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
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(x1, y1, x2, y2);
        g2d.setStroke(new BasicStroke(1));
    }
}


public class BeingZeroMiniPaintSolution {
    public static void main(String args[]) throws Exception {
        BeingZeroMainWindow w = new BeingZeroMainWindow();
        w.setVisible(true);
    }
}
