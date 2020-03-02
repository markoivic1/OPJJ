package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class which is used to show Newton-Raphson interation-based fractal.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Newton {
    /**
     * Main reads complex numbers and uses them to calculate the fractal.
     *
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line;
        int width = 1;
        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
        List<Complex> complexList = new ArrayList<>();
        while (true) {
            System.out.print("Root " + width + "> ");
            line = sc.nextLine();
            if (line.equals("done")) {
                break;
            }
            complexList.add(Complex.parse(line));
            width++;
        }
        System.out.println("Image of fractal will appear shortly. Thank you.");
        Complex[] complexArray = new Complex[complexList.size()];
        complexArray = complexList.toArray(complexArray);
        FractalViewer.show(new MojProducer(complexArray));
    }

    /**
     * Class which defines single task.
     */
    public static class PosaoIzracuna implements Callable<Void> {
        int xmin;
        int xmax;
        int ymin;
        int ymax;
        double remin;
        double remax;
        double immin;
        double immax;
        short[] data;
        int maxiter;
        double rootTreshold;
        ComplexRootedPolynomial f;
        int height;
        int width;
        double convergenceTreshold;
        int offset;

        /**
         * @param xmin                Coordinate from which x starts.
         * @param xmax                Coordinate for which the x ends.
         * @param ymin                Coordinate from which y starts.
         * @param ymax                Coordinate for which the y ends.
         * @param remin               Minimum value of a real part.
         * @param remax               Maximum value of a real part.
         * @param immin               Minimum value of a imaginary part.
         * @param immax               Minimum value of a imaginary part.
         * @param data                Data in which the calculatio is written.
         * @param maxiter             Maximum number of iterations
         * @param rootTreshold        Treshold of a root.
         * @param f                   Polynomial
         * @param height              Height of a screen.
         * @param width               Width of a screen.
         * @param convergenceTreshold Convergence treshold
         * @param offset              Offset which is used when writing data.
         */
        public PosaoIzracuna(int xmin, int xmax, int ymin, int ymax,
                             double remin, double remax, double immin, double immax,
                             short[] data, int maxiter, double rootTreshold, ComplexRootedPolynomial f,
                             int height, int width, double convergenceTreshold, int offset) {
            super();
            this.xmin = xmin;
            this.xmax = xmax;
            this.ymin = ymin;
            this.ymax = ymax;
            this.remin = remin;
            this.remax = remax;
            this.immin = immin;
            this.immax = immax;
            this.data = data;
            this.maxiter = maxiter;
            this.rootTreshold = rootTreshold;
            this.f = f;
            this.width = width;
            this.height = height;
            this.convergenceTreshold = convergenceTreshold;
            this.offset = offset;
        }

        /**
         * Method which is used to calculate fractal for given values.
         *
         * @return Always returns null.
         * @throws Exception Exception is thrown if the fractal couldn't be calculated.
         */
        @Override
        public Void call() throws Exception {
            PosaoIzracuna.calculate(xmin, xmax, ymin, ymax, remin, remax, immin, immax, data, maxiter, rootTreshold, f, height, width, convergenceTreshold, offset);
            return null;
        }

        /**
         * Class which is used to calculate fractal for the given values.
         *
         * @param xmin                Coordinate from which x starts.
         * @param xmax                Coordinate for which the x ends.
         * @param ymin                Coordinate from which y starts.
         * @param ymax                Coordinate for which the y ends.
         * @param remin               Minimum value of a real part.
         * @param remax               Maximum value of a real part.
         * @param immin               Minimum value of a imaginary part.
         * @param immax               Minimum value of a imaginary part.
         * @param data                Data in which the calculatio is written.
         * @param maxiter             Maximum number of iterations
         * @param rootTreshold        Treshold of a root.
         * @param height              Height of a screen.
         * @param width               Width of a screen.
         * @param convergenceTreshold Convergence treshold
         * @param offset              Offset which is used when writing data.
         * @return Returns Void
         */
        private static Void calculate(int xmin, int xmax, int ymin, int ymax, double remin, double remax, double immin, double immax, short[] data, int maxiter, double rootTreshold, ComplexRootedPolynomial polynomial, int height, int width, double convergenceTreshold, int offset) {
            double module;
            ComplexPolynomial derived = polynomial.toComplexPolynom().derive();
            for (int y = ymin; y < ymax; y++) {
                for (int x = xmin; x < xmax; x++) {
                    double real = (double) (((double) x / (width - 1)) * (remax - remin)) + remin;
                    double imaginary = (double) ((((double) height - 1 - y) / (height - 1)) * (immax - immin)) + immin;
                    Complex c = new Complex(real, imaginary);
                    Complex zn = c;
                    Complex znold;
                    int iter = 0;
                    do {
                        Complex numerator = polynomial.apply(zn);
                        Complex denominator = derived.apply(zn);
                        znold = zn;
                        Complex fraction = numerator.divide(denominator);
                        zn = zn.sub(fraction);
                        module = znold.sub(zn).module();
                        iter++;
                    } while ((iter < maxiter) && (module > convergenceTreshold));
                    int index = polynomial.indexOfClosestRootFor(zn, rootTreshold);
                    data[offset++] = (short) (index + 1);
                }
            }
            return null;
        }
    }

    /**
     * Class which defines producers for this fractal.
     */
    public static class MojProducer implements IFractalProducer {
        /**
         * Pool of threads.
         */
        private ExecutorService pool;
        /**
         * Polynomial for which the fractal is calculated
         */
        private ComplexRootedPolynomial polynomial;
        /**
         * Maximum number of iterations.
         */
        private static final int MAXITER = 16 * 16 * 16;
        /**
         * Root treshold
         */
        private static final double ROOT_TRESHOLD = 0.002;
        /**
         * Convergence treshold
         */
        private static final double CONVERGENCE_TRESHOLD = 1e-03;

        /**
         * Class which creates daemon threads.
         */
        class DaemonicThreadFactory implements ThreadFactory {
            @Override
            public Thread newThread(Runnable r) {
                Thread newThread = new Thread(r);
                newThread.setDaemon(true);
                return newThread;
            }
        }

        /**
         * Constructor which takes complex number read from the user and creates a polynomial out of it.
         *
         * @param complexes
         */
        public MojProducer(Complex[] complexes) {
            polynomial = new ComplexRootedPolynomial(Complex.ONE, complexes);
            DaemonicThreadFactory daemonicThreadFactory = new DaemonicThreadFactory();
            pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), daemonicThreadFactory);
        }

        /**
         * Method which is used
         *
         * @param reMin     Minimum real part.
         * @param reMax     Maximum real part.
         * @param imMin     Minimum imaginary part.
         * @param imMax     Maximum imaginary part.
         * @param width     Width of a screen.
         * @param height    Height of a screen.
         * @param requestNo Request number.
         * @param observer  Observer of a fractal.
         * @param cancel    Cancel calculation.
         */
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            System.out.println("Zapocinjem izracun");
            short[] data = new short[width * height];
            int brojTraka = (8 * Runtime.getRuntime().availableProcessors());
            int brojYPoTraci = height / brojTraka;

            List<Future<Void>> rezultati = new ArrayList<>();

            for (int j = 0; j < brojTraka; j++) {
                int yMin = j * brojYPoTraci;
                int yMax = (j + 1) * brojYPoTraci;
                if (j == brojTraka - 1) {
                    yMax = height;
                }
                PosaoIzracuna posao = new PosaoIzracuna(0, width, yMin, yMax, reMin, reMax, imMin, imMax, data, MAXITER, ROOT_TRESHOLD, polynomial, width, height, CONVERGENCE_TRESHOLD, width * brojYPoTraci * j);
                rezultati.add(pool.submit(posao));
            }
            for (Future<Void> posao : rezultati) {
                try {
                    posao.get();
                } catch (InterruptedException | ExecutionException e) {
                }
            }
            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
        }
    }
}
