package flocking;

class vec2d {

    double x, y, mag;

    vec2d(double x, double y) {
        this.x = x;
        this.y = y;
        mag = Math.sqrt ((x * x) + (y * y));
    }

    static vec2d add(vec2d a, vec2d b) {
        return new vec2d (a.x + b.x, a.y + b.y);
    }

    static vec2d sub(vec2d a, vec2d b) {
        return new vec2d (a.x - b.x, a.y - b.y);
    }

    void add(vec2d a) {
        x += a.x;
        y += a.y;
    }

    void sub(vec2d a) {
        x -= a.x;
        y -= a.y;
    }

    void mult(double n) {
        x *= n;
        y *= n;
    }

    void div(double n) {
        if (n != 0) {
            x /= n;
            y /= n;
        }
    }

    void setMag(double new_mag) {
        if (mag != 0) {
            y = ((y * new_mag) / mag);
            x = ((x * new_mag) / mag);
            mag = Math.sqrt ((x * x) + (y * y));
        }
    }

    double getMag() {
        mag = Math.sqrt ((x * x) + (y * y));
        return mag;
    }

    double dotProductfrom0() {
        return (x);
    }

    public vec2d copy() {
        return new vec2d (x, y);
    }

}