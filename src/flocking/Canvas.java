package flocking;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JComponent {
    Polygon tri = new Polygon ();
    boid[] boids;

    Canvas(boid[] boids1) {
        boids = boids1;
        tri.npoints = 3;
        tri.addPoint (0, 0);
        tri.addPoint (-5, 20);
        tri.addPoint (5, 20);
    }

    double x, y, angle;

    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        for (int i = 0; i < boids.length; i++) {
            g.setColor (boids[i].color);
            x = boids[i].pos.x;
            y = boids[i].pos.y;
            vec2d v = boids[i].vel;
            double dp = Math.abs (boids[i].vel.dotProductfrom0 ());

            double angle = Math.asin (-1 * v.y / v.mag);

            if (v.x < 0 && v.y < 0)
                angle = Math.PI - angle;
            else if (v.x < 0 && v.y > 0)
                angle = Math.PI - angle;
            else if (v.x > 0 && v.y > 0)
                angle = (2 * Math.PI) + angle;

            angle = ((Math.PI / 2.0) - angle);

            g.translate (x, y);
            g.rotate (angle, 0, 10);
            g.fill (tri);
            g.rotate (-angle, 0, 10);
            g.translate (-x, -y);
        }
    }
}
