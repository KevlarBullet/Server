package me.silver.server.util;

public class Angle {

    private double angle;

    public Angle(double angle) {
        this.angle = angle;
        this.normalize();
    }

    public Angle add(Angle angle) {
        return add(angle.angle);
    }

    public Angle add(double angle) {
        this.angle += angle;
        return this.normalize();
    }

    public Angle subtract(Angle angle) {
        return add(angle.angle);
    }

    public Angle subtract(double angle) {
        this.angle -= angle;
        return this.normalize();
    }

    public double getOffset(Angle angle) {
        return getOffset(angle.angle);
    }

    public double getOffset(double angle) {
        return (angle - this.angle) % 360;
    }

    private Angle normalize() {
        this.angle %= 360;

        if (this.angle < 0) {
            this.angle += 360;
        }

        return this;
    }

    public double getAngle() {
        return this.angle;
    }

}
