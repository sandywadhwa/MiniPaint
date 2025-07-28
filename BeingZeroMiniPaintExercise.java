import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import static java.lang.Math.*;


// MainWindow has
//  Menubar
//  Toolbar
//  DrawingCanvas
//  StatusBar

class BeingZeroMainWindow extends JFrame
{
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
        JComboBox<String> cmbShape = new JComboBox<>(new String[]{ "Line", "Oval", "Rectangle" });
        // cmbShape.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Line", "Oval", "Rectangle" }));
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
    
    /* DRAWING RELATED COMPONENTS */
    private int x1, y1, x2, y2;
    private boolean isFill;
    private Color color = Color.red;
    private String selectedShape = "Line";
    ArrayList<Shape> completedShapes = new ArrayList<>();
    Shape currentShape;
    
    /* UI COMPONENTS */
    private JMenuBar mnuBarMainMenuBar;
    private JToolBar tbarMainToolBar;
    private JPanel pnlDrawingCanvas;
    private JLabel lblMousePos;
    private JPanel pnlColorChooser;
    
        // sir but the shape will be visible only when we release right
        class DrawingCanvas extends JPanel {
    
            @Override
            public void paint(Graphics g) {
                super.paint(g);   /* Clears up the Panel */
    
                /* Draw Previous Shapes */
                // TODO:  loop over completedShapes collection and draw each shape
    
                /* Draw Current Shape */
                if (currentShape != null)
                    currentShape.draw(g);
            }
    
            public DrawingCanvas() {
                setBackground(Color.white);
    
                this.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        // TODO:  Display Mouse Coordinates in JLabel lblMousePos

                    }
    
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        x2 = e.getX();
                        y2 = e.getY();
                        switch (selectedShape) {
                            case "Line":
                                // TODO:  Create Line object and assign to currentShape
                                break;
                            case "Oval":
                                // TODO:  Create Oval object and assign to currentShape
                                break;
                            case "Rectangle":
                                // TODO:  Create Rectangle object and assign to currentShape
                                break;
                        }
                        repaint();
                    }
    
                });
                this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        // TODO:  Capture startX and startY coordinates in x1 y1 variables

                    }
    
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        x2 = e.getX();
                        y2 = e.getY();
                        Shape s = null;
                        switch (selectedShape) {
                            case "Line":
                                // TODO:  Create Line object and assign to s
                                break;
                            case "Oval":
                                // TODO:  Create Oval object and assign to s
                                break;
                            case "Rectangle":
                                // TODO:  Create Rectangle object and assign to s
                                break;
                            }
                            completedShapes.add(s);
                            currentShape = null;
                            repaint();
                    }
    
                });
    
            }
        }    
}

interface IShape{
    void draw(Graphics g);
}

abstract class Shape implements IShape {
    int x1, y1, x2, y2;
    Color c;
    boolean isFill;

    public Shape(int x1, int y1, int x2, int y2, Color c, boolean isFill) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.c = c;
        this.isFill = true;
    }

    public abstract void draw(Graphics g);

}

class RectangleShape extends Shape {

    public RectangleShape(int x1, int y1, int x2, int y2, Color c, boolean isFill) {
        super(x1, y1, x2, y2, c, isFill);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(c);
        if (isFill) {
            // TODO:  Draw filled rectangle
        } else {
            // TODO:  Draw rectangle
        }
    }
}

class OvalShape extends Shape {

    public OvalShape(int x1, int y1, int x2, int y2, Color c, boolean isFill) {
        super(x1, y1, x2, y2, c, isFill);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(c);
        if (isFill) {
            // TODO:  Draw filled oval
        } else {
            // TODO:  Draw oval
        }
    }
}

class LineShape extends Shape {

    public LineShape(int x1, int y1, int x2, int y2, Color c) {
        super(x1, y1, x2, y2, c, false);
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


public class BeingZeroMiniPaintExercise {
    public static void main(String args[]) throws Exception {
        BeingZeroMainWindow w = new BeingZeroMainWindow();
        w.setVisible(true);
    }
}
