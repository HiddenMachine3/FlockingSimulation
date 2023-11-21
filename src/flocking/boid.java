package flocking;

import java.awt.*;
import java.util.Random;

public class boid {

    static Double cForce, aForce, sForce;
    static double minspeed = 0, maxspeed = 1;
    static double cradius = 50, aradius = 50, sradius = 50;
    Random ra = new Random ();
    public static boid[] boids;

    Color color = new Color ((int) (Math.random () * 255), (int) (Math.random () * 255), (int) (Math.random () * 255));

    double n = 0.0001;
    vec2d pos = new vec2d (Math.random () * 1300, Math.random () * 600),
            vel = new vec2d (ra.nextGaussian () * n, ra.nextGaussian () * n),
            acc = new vec2d (0, 0);

    boid(Double cForce1, Double aForce1, Double sForce1) {
        cForce = cForce1;
        aForce = aForce1;
        sForce = sForce1;
    }

    void update() {
        vel.add (acc);

        if (vel.getMag () > maxspeed)
            vel.setMag (maxspeed);
        else if (vel.getMag () < minspeed)
            vel.setMag (minspeed);
        pos.add (vel);


        if (pos.x > 1300)
            pos.x = -10;
        else if (pos.x < -10)
            pos.x = 1300;
        if (pos.y > 580)
            pos.y = -20;
        else if (pos.y < -20)
            pos.y = 580;

        acc.mult (0);
    }

    double r = 50;

    vec2d alinment() {

        vec2d steer = new vec2d (0, 0);
        int total = 0;
        for (int i = 0; i < boids.length; i++) {
            if ((!(boids[i].equals (this))) && vec2d.sub (boids[i].pos, this.pos).getMag () < aradius) {
                steer.add (boids[i].vel);
                total++;
            }
        }

        if (total > 0)
            steer.div (total);

        return steer;
    }

    vec2d coehesion() {

        vec2d steer = new vec2d (0, 0);
        int total = 0;
        for (int i = 0; i < boids.length; i++) {
            if ((!(boids[i].equals (this))) && vec2d.sub (boids[i].pos, this.pos).getMag () < cradius) {
                steer.add (boids[i].pos);
                total++;
            }
        }

        if (total > 0)
            steer.div (total);

        return vec2d.sub (steer, this.pos);
    }

    vec2d separation() {

        vec2d steer = new vec2d (0, 0);
        int total = 0;
        for (int i = 0; i < boids.length; i++) {
            double d = vec2d.sub (boids[i].pos, pos).getMag ();
            if ((!(boids[i].equals (this))) && d < sradius) {
                vec2d repulsion = vec2d.sub (pos, boids[i].pos);
                if (d != 0)
                    repulsion.setMag (1 / d);

                steer.add (repulsion);
                total++;
            }
        }

        if (total > 0)
            steer.div (total);

        return steer;
    }


    void applyforces() {

        vec2d alignment = alinment ();
        if (alignment.getMag () > 0)
            alignment.setMag (aForce);


        vec2d coehesion = coehesion ();
        if (coehesion.getMag () > 0)
            coehesion.setMag (cForce);

        vec2d separation = separation ();
        if (separation.getMag () > 0)
            separation.setMag (sForce);

        acc.add (alignment);
        acc.add (coehesion);
        acc.add (separation);

    }


}
