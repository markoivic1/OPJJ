package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class which defines single-threaded ray caster.
 * Running this class will produce still image.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class RayCaster {
    /**
     * Main used to show the scene.
     *
     * @param args This method doesn't take arguments.
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
    }

    /**
     * Gets IRayTracerProducer which is used to calculate rays for a given scene.
     *
     * @return Returns IRayTracerProducer with implemented method produce.
     */
    private static IRayTracerProducer getIRayTracerProducer() {
        return new IRayTracerProducer() {
            /**
             * @param eye position of human observer
             * @param view position that is observed
             * @param viewUp specification of view-up vector which is used to determine y-axis for screen
             * @param horizontal horizontal width of observed space
             * @param vertical vertical height of observed space
             * @param width number of pixels per screen row
             * @param height number of pixel per screen column
             * @param requestNo used internally and must be passed on to GUI observer with rendered image
             * @param observer GUI observer that will accept and display image this producer creates
             * @param cancel object used to cancel rendering of image; is set to <code>true</code>, rendering should be canceled
             */
            @Override
            public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
                System.out.println("Započinjem izračune...");
                short[] red = new short[width * height];
                short[] green = new short[width * height];
                short[] blue = new short[width * height];
                Point3D zAxis = new Point3D(0, 0, 0); //TODO
                viewUp.modifyNormalize();
                Point3D OG = view.sub(eye).modifyNormalize();
                double OGDotViewUp = OG.scalarProduct(viewUp);
                Point3D OGxViewUp = OG.scalarMultiply(OGDotViewUp);
                Point3D yAxis = viewUp.sub(OGxViewUp).modifyNormalize();
                Point3D xAxis = OG.vectorProduct(yAxis).modifyNormalize();
                Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));
                Scene scene = RayTracerViewer.createPredefinedScene();
                short[] rgb = new short[3];
                int offset = 0;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((double) x / (width - 1) * horizontal)).sub(yAxis.scalarMultiply((double) y / (height - 1) * vertical));
                        Ray ray = Ray.fromPoints(eye, screenPoint);
                        tracer(scene, ray, rgb);
                        red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                        green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                        blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
                        offset++;
                    }
                }
                System.out.println("Izračuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }
        };
    }

    /**
     * Method which defines how the intersections will be treated.
     * @param scene Scene in which these calculations take place.
     * @param ray Ray which is tested for intersection.
     * @param rgb Array of colors.
     */
    protected static void tracer(Scene scene, Ray ray, short[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        RayIntersection closest = getRayIntersection(scene, ray);
        if (closest == null) {
            return;
        }
        if (!closest.isOuter()) {
            return;
        }
        determineColorFor(scene, closest, ray, rgb);
    }

    /**
     * Calculates closest intersection.
     * @param scene Scene in which this is caluculated.
     * @param ray Ray which is used to calculate intersections with objects.
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
     * @param scene Scene in which this will be calculated.
     * @param rayIntersection Holds data of the surface point in which the intersection occured.
     * @param observer Human observer.
     * @param rgb Array of colors.
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
     * @param scene Scene in which these calculations take place.
     * @param ray Ray which is used to find intersections.
     * @param rgb Array of colors.
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
