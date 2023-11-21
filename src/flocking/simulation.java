package flocking;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class simulation {

    static public boid[] boids = new boid[1000];

    static Double cForce = 0.01, aForce = 0.01, sForce = 0.01;
    static double minspeed = 0, maxspeed = 10;
    static double cradius = 50, aradius = 50, sradius = 50;

    void start() {
        while (true) {
            boid.boids = copy (boids);
            for (int i = 0; i < boids.length; i++) {
                boids[i].applyforces ();
                boids[i].update ();
            }
            f.repaint ();
            canvas.repaint ();
            //Wait ();
        }
    }

    long timestep = 10000000L;

    void Wait() {
        long goal = System.nanoTime () + timestep;
        while (System.nanoTime () < goal)
            Thread.yield ();
    }

    boid[] copy(boid[] b) {
        boid[] copy = new boid[b.length];
        for (int i = 0; i < b.length; i++) {
            copy[i] = new boid (cForce, aForce, sForce);
            copy[i].pos = b[i].pos.copy ();
            copy[i].vel = b[i].vel.copy ();
            //copy[i].acc = b[i].acc.copy ();
        }
        return copy;
    }

    public static void main(String[] args) {
        simulation s = new simulation ();
        s.window ();
        s.start ();
    }


    public JPanel panel;
    public JSlider align;
    public JSlider cohesion;
    public JSlider separation;
    public JSlider min_speed;
    public JSlider max_speed;
    public JSlider sr;
    public JSlider cr;
    public JSlider ar;
    public JPanel Aslider;
    public JPanel Sslider;
    public JPanel Cslider;
    public JPanel Aradius;
    public JPanel Sradius;
    public JPanel Cradius;
    public JPanel Align;
    public JPanel Separation;
    public JPanel Cohesion;
    private Canvas canvas;
    private JTabbedPane tpane;
    JFrame f = new JFrame ();

    simulation() {
        for (int i = 0; i < boids.length; i++)
            boids[i] = new boid (cForce, aForce, sForce);
        align.setMaximum (100000);
        cohesion.setMaximum (100000);
        separation.setMaximum (100000);

        ar.setMaximum (600);
        sr.setMaximum (600);
        cr.setMaximum (600);

        align.setUI (new customSlider (align));
        cohesion.setUI (new customSlider (cohesion));
        separation.setUI (new customSlider (separation));
        ar.setUI(new customSlider (ar));
        cr.setUI(new customSlider (cr));
        sr.setUI(new customSlider (sr));
        max_speed.setUI(new customSlider(max_speed));
        min_speed.setUI(new customSlider(min_speed));
        tpane.setUI(new customTPane ());


        align.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                aForce = (double) align.getValue () / 1000.0;
                boid.aForce = aForce;
            }
        });
        cohesion.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                cForce = (double) cohesion.getValue () / 1000.0;
                boid.cForce = cForce;
            }
        });
        separation.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                sForce = (double) separation.getValue () / 1000.0;
                boid.sForce = sForce;
            }
        });
        min_speed.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                minspeed = min_speed.getValue ();
                boid.minspeed = minspeed;
            }
        });
        max_speed.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                maxspeed = max_speed.getValue ();
                boid.maxspeed = maxspeed;
            }
        });
        sr.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                sradius = sr.getValue ();
                boid.sradius = sradius;
            }
        });
        ar.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                aradius = ar.getValue ();
                boid.aradius = aradius;
            }
        });
        cr.addChangeListener (new ChangeListener () {
            @Override
            public void stateChanged(ChangeEvent e) {
                cradius = cr.getValue ();
                boid.cradius = cradius;
            }
        });
    }

    public void createUIComponents() {
        // TODO: place custom component creation code here
        canvas = new Canvas (boids);
    }

    void window() {
        f.setExtendedState (JFrame.MAXIMIZED_BOTH);
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        f.setContentPane (new simulation ().panel);

        f.setVisible (true);
    }


    class customSlider extends BasicSliderUI {

        public customSlider(JSlider slider) {
            super (slider);
        }

        @Override
        public void paintThumb(Graphics gg) {
            Graphics2D g = (Graphics2D) gg;
            g.setColor (Color.decode ("#a705ff"));
            Rectangle t = thumbRect;
            g.fill (t);
        }

        @Override
        public void paintTrack(Graphics gg) {
            Graphics2D g = (Graphics2D) gg;
            g.setColor (Color.decode ("#000034"));
            Rectangle2D t = new Rectangle2D.Double (trackRect.x, trackRect.y + 5 + 2.5, trackRect.width, 5);
            g.fill (t);
        }

        @Override
        public void paintFocus(Graphics g) {
            //super.paintFocus (g);
        }
    }

    class customTPane extends BasicTabbedPaneUI{

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {

        }

        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {

        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            //super.paintContentBorder (g, tabPlacement, selectedIndex);
        }


    }

}
