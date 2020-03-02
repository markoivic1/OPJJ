package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.math.Vector3;

/**
 * This class models Sphere object with it's unique way of detecting closest intersection.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Sphere extends GraphicalObject {
    /**
     * Center of a sphere
     */
    private Point3D center;
    /**
     * Radius of a sphere.
     */
    private double radius;
    /**
     * Diffuse coefficient of color red.
     */
    private double kdr;
    /**
     * Diffuse coefficient of color gree.
     */
    private double kdg;
    /**
     * Diffuse coefficient of color blue.
     */
    private double kdb;
    /**
     * Specular coefficient of color red.
     */
    private double krr;
    /**
     * Specular coefficient of color green.
     */
    private double krg;
    /**
     * Specular coefficient of color blue.
     */
    private double krb;
    /**
     * Indicates specular dependency of a ray which is coming to the observer from the point on object.
     */
    private double krn;

    /**
     * Default constructor which calls super() constructor and passes arguments.
     * @param center Center of a sphere
     * @param radius radius of a sphere
     * @param kdr Diffuse coefficient of color red.
     * @param kdg Diffuse coefficient of color green.
     * @param kdb Diffuse coefficient of color blue.
     * @param krr Specular coefficient of color red.
     * @param krg Specular coefficient of color green.
     * @param krb Specular coefficient of color blue.
     * @param krn Indicates specular dependency of a ray which is coming to the observer from the point on object.
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb, double krn) {
        super();
        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    /**
     * Finds the closest ray intersection with this object.
     * @param ray Ray which is being checked for intersection.
     * @return Returns new {@link RayIntersection} if an intersection exists, returns null otherwise.
     */
    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {
        Point3D D = ray.direction;
        Point3D O = ray.start;
        Point3D C = center;
        double a = D.scalarProduct(D);
        double b = 2 * D.scalarProduct(O.sub(C));
        double c = O.sub(C).scalarProduct(O.sub(C)) - radius * radius;
        double disc = b * b - 4 * a * c;
        if (disc < 0) {
            return null;
        }

        double t0 = (-1 * b + Math.sqrt(disc)) / (2 * a);
        double t1 = (-1 * b - Math.sqrt(disc)) / (2 * a);
        boolean outer = true;

        if (Math.abs(t0) - 0.00001 < 0.0001) {
            t0 = Math.abs(t0);
        }

        if (Math.abs(t1) - 0.00001 < 0.0001) {
            t1 = Math.abs(t1);
        }

        if (t0 < 0 && t1 < 0) {
            return null;
        }

        if (Math.min(t0, t1) < 0 && Math.max(t0, t1) >= 0) {
            outer = false;
        }

        Point3D intersection1;
        Point3D intersection2;

        if (t0 < 0) {
            intersection1 = ray.start.add(ray.direction.scalarMultiply(t1));
            intersection2 = intersection1;
        } else if (t1 < 0) {
            intersection1 = ray.start.add(ray.direction.scalarMultiply(t0));
            intersection2 = intersection1;
        } else {
            intersection1 = ray.start.add(ray.direction.scalarMultiply(t0));
            intersection2 = ray.start.add(ray.direction.scalarMultiply(t1));
        }

        Point3D closestIntersection;
        if (!outer) {
            closestIntersection = ray.start.add(ray.direction.scalarMultiply(Math.max(t0, t1)));
        } else {
            closestIntersection = intersection1.sub(ray.start).norm() < intersection2.sub(ray.start).norm() ? intersection1 : intersection2;
        }
        Point3D normal = closestIntersection.sub(center).modifyNormalize();
        return new RayIntersection(closestIntersection, closestIntersection.difference(closestIntersection, ray.start).norm(), outer) {

            /**
             * Gets normal which is located on the surface of an object.
             * @return Returns normal
             */
            @Override
            public Point3D getNormal() {
                return normal;
            }

            /**
             * Diffuse coefficient of color red.
             * @return Returns coefficient.
             */
            @Override
            public double getKdr() {
                return kdr;
            }
            /**
             * Diffuse coefficient of color green.
             * @return Returns coefficient.
             */
            @Override
            public double getKdg() {
                return kdg;
            }
            /**
             * Diffuse coefficient of color blue.
             * @return Returns coefficient.
             */
            @Override
            public double getKdb() {
                return kdb;
            }

            /**
             * Specular coefficient of color red.
             * @return returns coefficient.
             */
            @Override
            public double getKrr() {
                return krr;
            }
            /**
             * Specular coefficient of color green.
             * @return returns coefficient.
             */
            @Override
            public double getKrg() {
                return krg;
            }
            /**
             * Specular coefficient of color blue.
             * @return returns coefficient.
             */
            @Override
            public double getKrb() {
                return krb;
            }

            /**
             * Indicates specular dependency of a ray which is coming to the observer from the point on object.
             * @return returns index n.
             */
            @Override
            public double getKrn() {
                return krn;
            }
        };
    }
}
