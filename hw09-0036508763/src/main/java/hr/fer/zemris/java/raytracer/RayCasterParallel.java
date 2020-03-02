package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;
import hr.fer.zemris.math.Vector3;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class which produces still image of a predefined scene.
 * It uses multiple threads to calculate its data.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class RayCasterParallel {
    /**
     * Main used to show the scene.
     *
     * @param args This method doesn't take arguments.
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
    }

    /**
     * Class which defines recursive calculation.
     * It is used to break workload into sufficiently small chunks.
     * Only then are the tasks executed.
     */
    public static class Calculate extends RecursiveAction {
        short[] rgb;
        Point3D eye;
        Point3D view;
        Point3D viewUp;
        double horizontal;
        double vertical;
        int xMax;
        int yMax;
        long requestNo;
        IRayTracerResultObserver observer;
        AtomicBoolean atomicBoolean;
        int offset;
        Point3D screenCorner;
        Scene scene;
        Point3D xAxis;
        Point3D yAxis;
        Point3D zAxis;
        short[] red;
        short[] green;
        short[] blue;
        int xMin;
        int yMin;
        int treshold;
        int width;
        int height;

        /**
         * Calls super() constructor and stores given values.
         *
         * @param eye          position of human observer
         * @param view         position that is observed
         * @param viewUp       specification of view-up vector which is used to determine y-axis for screen
         * @param horizontal   horizontal width of observed space
         * @param vertical     vertical height of observed space
         * @param height       number of pixel per screen column
         * @param requestNo    used internally and must be passed on to GUI observer with rendered image
         * @param observer     GUI observer that will accept and display image this producer creates
         * @param yMin         row of pixels from which the calculation for a given task starts.
         * @param xMin         column of pixels from which the calculation for a given task starts.
         * @param height       Used to calculculate screen point.
         * @param xAxis        Point3D which defines x axis.
         * @param yAxis        Point3D which defines y axis.
         * @param zAxis        Point3D which defines z axis.
         * @param screenCorner Point3D which defines the corner of a screen.
         * @param scene        Given scene.
         */
        public Calculate(Point3D eye, Point3D view, Point3D viewUp,
                         double horizontal, double vertical, int xMax, int yMax,
                         long requestNo, IRayTracerResultObserver observer, AtomicBoolean atomicBoolean,
                         short[] red, short[] green, short[] blue, int yMin, int xMin, int height,
                         Point3D xAxis, Point3D yAxis, Point3D zAxis, Point3D screenCorner, Scene scene) {
            super();
            this.rgb = new short[3];
            this.eye = eye;
            this.view = view;
            this.viewUp = viewUp;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.yMax = yMax;
            this.xMax = xMax;
            this.requestNo = requestNo;
            this.observer = observer;
            this.atomicBoolean = atomicBoolean;
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.zAxis = zAxis;
            this.screenCorner = screenCorner;
            this.scene = scene;
            this.red = red;
            this.blue = blue;
            this.green = green;
            this.xMin = xMin;
            this.yMin = yMin;
            this.treshold = 16;
            this.width = xMax + 1;
            this.height = height;
        }

        /**
         * Method which calculates task if the chunk is sufficiently small.
         * Otherwise it will break task to even smaller tasks.
         */
        @Override
        protected void compute() {
            if (yMax - yMin - 1 <= treshold) {
                computeDirect();
                return;
            }
            invokeAll(
                    new Calculate(eye, view, viewUp, horizontal, vertical, xMax, yMin + (yMax - yMin) / 2,
                            requestNo, observer, atomicBoolean, red, green, blue, yMin, xMin, height, xAxis, yAxis, zAxis, screenCorner, scene),
                    new Calculate(eye, view, viewUp, horizontal, vertical, xMax, yMax,
                            requestNo, observer, atomicBoolean, red, green, blue, yMin + (yMax - yMin) / 2 + 1, xMin, height, xAxis, yAxis, zAxis, screenCorner, scene)
            );
        }

        /**
         * Computes data for a given values.
         */
        public void computeDirect() {
            int offset = yMin * width;
            for (int y = yMin; y < yMax + 1; y++) {
                for (int x = xMin; x < width; x++) {
                    Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((double) x / (width - 1) * horizontal)).sub(yAxis.scalarMultiply((double) y / (height - 1) * vertical));
                    Ray ray = Ray.fromPoints(eye, screenPoint);
                    tracer(scene, ray, rgb);
                    red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                    green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                    blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
                    offset++;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * @return
     */
    private static IRayTracerProducer getIRayTracerProducer() {
        return new IRayTracerProducer() {
            /**
             * {@inheritDoc}
             * @param eye
             * @param view
             * @param viewUp
             * @param horizontal
             * @param vertical
             * @param width
             * @param height
             * @param requestNo
             * @param observer
             * @param atomicBoolean
             */
            @Override
            public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean atomicBoolean) {
                System.out.println("Započinjem izračune...");
                short[] red = new short[width * height];
                short[] green = new short[width * height];
                short[] blue = new short[width * height];

                ForkJoinPool pool = new ForkJoinPool();
                Point3D zAxis = new Point3D(0, 0, 0); //TODO
                viewUp.modifyNormalize();
                Point3D OG = view.sub(eye).modifyNormalize();
                double OGDotViewUp = OG.scalarProduct(viewUp);
                Point3D OGxViewUp = OG.scalarMultiply(OGDotViewUp);
                Point3D yAxis = viewUp.sub(OGxViewUp).modifyNormalize();
                Point3D xAxis = OG.vectorProduct(yAxis).modifyNormalize();
                Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));
                Scene scene = RayTracerViewer.createPredefinedScene();

                pool.invoke(new Calculate(eye, view, viewUp, horizontal, vertical, width - 1, height - 1,
                        requestNo, observer, atomicBoolean, red, green, blue, 0, 0, height,
                        xAxis, yAxis, zAxis, screenCorner, scene));

                pool.shutdown();

                System.out.println("Izračuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }
        };
    }

    /**
     * Method which defines how the intersections will be treated.
     *
     * @param scene Scene in which these calculations take place.
     * @param ray   Ray which is tested for intersection.
     * @param rgb   Array of colors.
     */
    protected static void tracer(Scene scene, Ray ray, short[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        RayIntersection closest = getRayIntersection(scene, ray);
        if (closest == null) {
            return;
        }
        determineColorFor(scene, closest, ray, rgb);
    }

    /**
     * Calculates closest intersection.
     *
     * @param scene Scene in which this is caluculated.
     * @param ray   Ray which is used to calculate intersections with objects.
     * @return Returns {@link RayIntersection} if the ray intersects some object, return null otherwise.
     */
    private static RayIntersection getRayIntersection(Scene scene, Ray ray) {
        RayIntersection closest = null;
        for (GraphicalObject object : scene.getObjects()) {
            RayIntersection rayIntersection = object.findClosestRayIntersection(ray);
            if (rayIntersection == null) {
                continue;
            }
            closest = rayIntersection;
            break;
        }
        if (closest == null) {
            return null;
        }
        for (int i = 0; i < scene.getObjects().size(); i++) {
            RayIntersection rayIntersection = scene.getObjects().get(i).findClosestRayIntersection(ray);
            if (rayIntersection == null) {
                continue;
            }
            if (rayIntersection.getDistance() < closest.getDistance()) {
                closest = scene.getObjects().get(i).findClosestRayIntersection(ray);
            }
        }
        return closest;
    }

    /**
     * Determines the color for some {@link RayIntersection}.
     *
     * @param scene           Scene in which this will be calculated.
     * @param rayIntersection Holds data of the surface point in which the intersection occured.
     * @param observer        Human observer.
     * @param rgb             Array of colors.
     */
    private static void determineColorFor(Scene scene, RayIntersection rayIntersection, Ray observer,
                                          short[] rgb) {
        rgb[0] = 15;
        rgb[1] = 15;
        rgb[2] = 15;

        for (LightSource ls : scene.getLights()) {
            Ray lsRay = Ray.fromPoints(ls.getPoint(), rayIntersection.getPoint());
            RayIntersection closest = getRayIntersection(scene, lsRay);
            if (closest == null) {
                continue;
            }
            double diff = closest.getPoint().sub(ls.getPoint()).norm() - rayIntersection.getPoint().sub(ls.getPoint()).norm();
            if (Math.abs(diff) > 0.00001 && diff < 0) {
                continue;
            }
            Point3D l = ls.getPoint().sub(rayIntersection.getPoint()).modifyNormalize();
            Point3D n = rayIntersection.getNormal();

            double lDotN = l.scalarProduct(n);
            lDotN = lDotN > 0 ? lDotN : 0;

            Point3D r = l.sub(n.scalarMultiply(2 * lDotN)).modifyNormalize();
            Point3D v = rayIntersection.getPoint().sub(observer.start).modifyNormalize();

            double rDotV = r.scalarProduct(v);
            rDotV = rDotV > 0 ? rDotV : 0;

            rgb[0] += ls.getR() * rayIntersection.getKdr() * lDotN + ls.getR() * rayIntersection.getKrr() * Math.pow(rDotV, rayIntersection.getKrn());
            rgb[1] += ls.getG() * rayIntersection.getKdg() * lDotN + ls.getG() * rayIntersection.getKrg() * Math.pow(rDotV, rayIntersection.getKrn());
            rgb[2] += ls.getB() * rayIntersection.getKdb() * lDotN + ls.getB() * rayIntersection.getKrb() * Math.pow(rDotV, rayIntersection.getKrn());
        }
    }

    /**
     * Used in first attempt to calculate intersections.
     *
     * @param scene Scene in which these calculations take place.
     * @param ray   Ray which is used to find intersections.
     * @param rgb   Array of colors.
     */
    protected static void tracerBlackAndWhite(Scene scene, Ray ray, short[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        RayIntersection closest = getRayIntersection(scene, ray);
        if (closest == null) {
            rgb[0] = 0;
            rgb[1] = 0;
            rgb[2] = 0;
            return;
        }
        rgb[0] = 255;
        rgb[1] = 255;
        rgb[2] = 255;
    }

}
