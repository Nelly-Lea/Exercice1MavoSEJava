package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

public class Render {

    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase tracer;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";



    ImageWriter _imageWriter = null;


    Camera _camera = null;
    RayTracerBase _rayTracerBase = null;
    int amountRays;
    int level_adaptive_supersampling=0;

    /**
     * setter level for the adaptive supersampling improvement
     * @param level
     */
    public void setLevel_adaptive_supersampling(int level) {
        level_adaptive_supersampling = level;

    }





    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage


    /**
     * setter amount rays for supersampling imrpovement
     * @param amountRays
     */
    public void setAmountRays(int amountRays) {
        this.amountRays = amountRays;
    }

    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracerBase = rayTracer;

        return this;
    }


    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param step  grid's step
     * @param color grid's color
     */
    public void printGrid(int step, Color color) {
        if (_imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    _imageWriter.writePixel(j, i, color);
    }


    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (_imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        _imageWriter.writeToImage();
    }

    /**
     * Cast ray from camera in order to color a pixel
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = _camera.constructRayThroughPixel(nX, nY, col, row);
        Color color = _rayTracerBase.traceRay(ray);
        _imageWriter.writePixel(col, row, color);
    }




    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Color pixelColor=Color.BLACK;
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    if (level_adaptive_supersampling == 0)
                        castRay(nX, nY, pixel.col, pixel.row);
                    else {


                        _imageWriter.writePixel(pixel.col, pixel.row, AdpativeSuperSampling(nX, nY, pixel.row, pixel.col, _rayTracerBase));
                    }
                }
                   // castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (_imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (_camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (_rayTracerBase== null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);
        Color pixelColor=Color.BLACK;
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    if(level_adaptive_supersampling==0)
                        castRay(nX, nY, j, i);
                    else {

                        pixelColor = AdpativeSuperSampling(nX, nY, i, j, _rayTracerBase);
                        _imageWriter.writePixel(j, i, pixelColor);
                    }
        else
            renderImageThreaded();
    }

//
//    public void renderImage2() {
//        //we check that all fields are not null
//        try {
//            if (_imageWriter == null) {
//                throw new MissingResourceException("missing ressource", ImageWriter.class.getName(), "");
//            }
//            if (_camera == null) {
//                throw new MissingResourceException("missing ressource", Camera.class.getName(), "");
//            }
//            if (_rayTracerBase == null) {
//                throw new MissingResourceException("missing ressource", RayTracerBase.class.getName(), "");
//            }
//
//            //rendering the image
//            int nX = _imageWriter.getNx();
//            int nY = _imageWriter.getNy();
//            List<Ray> ListRays = new LinkedList<>();
//            Color color = Color.BLACK;
//            for (int i = 0; i < nY; i++) {
//                for (int j = 0; j < nX; j++) {
//                    ListRays = _camera.constructSeveralRayThroughPixel(nX, nY, j, i, amountRays);
//                    for (Ray ray : ListRays) {
//                        color = color.add(_rayTracerBase.traceRay(ray));
//
//                    }
//                    // Color pixelColor=_rayTracerBase.traceRay(ray);
//                    //_imageWriter.writePixel(j,i,pixelColor);
//                    for (int l = 0; l < 150; l++) {
//                        color = color.add(_rayTracerBase.traceRay(ListRays.get(0)));
//                    }//le premier rau cest le rayon prinicpale
//
//                    color = color.reduce(ListRays.size() + 150);// moyenne des couleurs des rayons lances
//                    _imageWriter.writePixel(j, i, color);
//                }
//            }
//
//        } catch (MissingResourceException e) {
//            throw new UnsupportedOperationException("Not implemented yet " + e.getClassName());
//        }
//    }



    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     *
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         *         if it is -1 - the task is finished, any other value - the progress
         *         percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }



    /**
     *
     * @param nX is number of pixels in x axis
     * @param nY is number of pixel in y axis
     * @param i is the row's number of the current pixel
     * @param j is the column's number of the current pixel
     * @param rayTracerBase
     * @return
     */
    public Color AdpativeSuperSampling(int nX, int nY, int i, int j, RayTracerBase rayTracerBase) {

        double Ry = _camera.get_height() / nY;// Ry is length of pixel
        double Rx = _camera.get_width() / nX; //Rx is width of pixel
        Point3D Pc = _camera.getP0().add(_camera.getvTo().scale(_camera.get_Distance()));// Pc is the interscetion point on the center of the viewplane
        Point3D PcornerLT = Pc.add(_camera.getvRight().scale(-_camera.get_height() / 2).add(_camera.getvUp().scale(_camera.get_width() / 2))); //PcornerLT is the point on the top corner left of the viewplane
        Point3D PLT;

        if (j == 0 && i == 0)//if the current pixel is the first pixel at the top corner left
            PLT = PcornerLT;
        else {
            if (j == 0 && i != 0)// if the current pixel is in the first column
                PLT = PcornerLT.add(_camera.getvUp().scale(-Rx * i));
            else if (i == 0 && j != 0)//if the current pixel is in the first row
                PLT = PcornerLT.add(_camera.getvRight().scale(Ry * j));
            else
                PLT = PcornerLT.add(_camera.getvUp().scale(-Rx * i)).add(_camera.getvRight().scale(Ry * j));// if the current pixel is other
        }
        Point3D PRT = PLT.add(_camera.getvRight().scale(Ry));// PRT is the point at the top right of the current pixel
        Point3D PBR = PLT.add(_camera.getvRight().scale(Ry).add(_camera.getvTo().scale(-Rx)));// PBR is the point at the bottom right of the current pixel
        Point3D PBL = PLT.add(_camera.getvUp().scale(-Rx));// PBL is the point at the bottom left of the current pixel
        Color color;
        color = AdpativeSuperSamplingCalcCol(Rx, Ry, PLT, PRT, PBR, PBL, rayTracerBase);
        return color;


    }

    public Color AdpativeSuperSamplingCalcCol(double Rx, double Ry, Point3D p0, Point3D p1, Point3D p2, Point3D p3, RayTracerBase rayTracerBase) {
        Ray raylt = new Ray(_camera.getP0(), p0.subtract(_camera.getP0()));//
        Color clt = rayTracerBase.traceRay(raylt);
        Ray rayrt = new Ray(_camera.getP0(), p1.subtract(_camera.getP0()));
        Color crt = rayTracerBase.traceRay(rayrt);
        Ray rayrb = new Ray(_camera.getP0(), p2.subtract(_camera.getP0()));
        Color crb = rayTracerBase.traceRay(rayrb);
        Ray raylb = new Ray(_camera.getP0(), p3.subtract(_camera.getP0()));
        Color clb = rayTracerBase.traceRay(raylb);
        Color color=Color.BLACK;

        if (clt.equal(crt) && crt.equal(crb) && crb.equal(clb))// if the 4 corners of the current pixel have a similar color return the average of the 4 colors
            return color.add(clt,crt,crb,clb).reduce(4);

        int level = level_adaptive_supersampling;
        Color color1 = Color.BLACK;
        color = AdaptiveSuperSamplingRecursive(Rx, Ry, p0, p1, p2, p3, rayTracerBase, clt, crt, crb, clb, level, color1);
        return color;

    }

    public Color AdaptiveSuperSamplingRecursive(double Rx, double Ry, Point3D p0, Point3D p1, Point3D p2, Point3D p3, RayTracerBase rayTracerBase, Color c0, Color c1, Color c2, Color c3, int level, /*double prop,*/ Color color1) {
        if (level == 0) //if level=0  we stop the recursive and we return the color
            return color1;
        Rx = Rx / 2; //Rx is the half the width of the pixel
        Ry = Ry / 2;//Ry is the half the length of the pixel
        Point3D p5 = p0.add(_camera.getvRight().scale(Ry)); //p5 is the midpoint between the top left point and the the top right point
        Point3D p6 = p1.add(_camera.getvUp().scale(-Rx));//p6 is the midpoint between the top right point and the the bottom right point
        Point3D p7 = p2.add(_camera.getvRight().scale(-Ry));//p7 is the midpoint between the bottom right point and the the bottom left point
        Point3D p8 = p0.add(_camera.getvUp().scale(-Rx));//p8 is the midpoint between the bottom left point and the the top left point
        Point3D p4 = p0.add(_camera.getvRight().scale(Ry).add(_camera.getvUp().scale(-Rx)));//p4 is the point in the center of the pixel
        Ray ray5 = new Ray(_camera.getP0(), p5.subtract(_camera.getP0()));//ray5 is the ray that passes through p5
        Color c5 = rayTracerBase.traceRay(ray5); //c5 is the color of ray5
        Ray ray6 = new Ray(_camera.getP0(), p6.subtract(_camera.getP0()));
        Color c6 = rayTracerBase.traceRay(ray6);
        Ray ray7 = new Ray(_camera.getP0(), p7.subtract(_camera.getP0()));
        Color c7 = rayTracerBase.traceRay(ray7);
        Ray ray8 = new Ray(_camera.getP0(), p8.subtract(_camera.getP0()));
        Color c8 = rayTracerBase.traceRay(ray8);
        Ray ray4 = new Ray(_camera.getP0(), p4.subtract(_camera.getP0()));
        Color c4 = rayTracerBase.traceRay(ray4);

        //we pass on the 4 sub pixels formed
        if (c0.equal(c5) && c5.equal(c4) && c4.equal(c8)) //if the 4 colors of the top left sub pixel are similar we return the average of the 4 colors
        {
            color1 = color1.add(c0,c5,c4,c8);
            color1=color1.reduce(4);}
        else {
            if (level - 1 == 0) { //if the next time we call the function is the last time as we calculate the final color of the sub pixel
                color1 = c0.add(c5).add(c4).add(c8);
                color1 = color1.reduce(4);
            }


            color1 = color1.add(AdaptiveSuperSamplingRecursive(Rx, Ry, p0, p5, p4, p8, rayTracerBase, c0, c5, c4, c8, level - 1,color1)).reduce(2);

        }

        if (c5.equal(c1) && c1.equal(c6) && c6.equal(c4)) {//if the 4 colors of the top right sub pixel are similar we return the average of the 4 colors
            color1 = color1.add(c5,c1,c6,c4);
            color1 = color1.reduce(5);
        } else {
            if (level - 1 == 0) {
                color1 = c5.add(c1).add(c6).add(c4);
                color1 = color1.reduce(4);
            }

            color1 = color1.add(AdaptiveSuperSamplingRecursive(Rx, Ry, p5, p1, p6, p4, rayTracerBase, c5, c1, c6, c4, level - 1,color1)).reduce(2);

        }
        if (c4.equal(c6) && c6.equal(c2) && c2.equal(c7)) {//if the 4 colors of the bottom right sub pixel are similar we return the average of the 4 colors
            color1 = color1.add(c4,c6,c2,c7);
            color1 = color1.reduce(5);
        } else {
            if (level - 1 == 0) {
                color1 = c4.add(c6).add(c2).add(c7);
                color1 = color1.reduce(4);
            }

            color1 = color1.add(AdaptiveSuperSamplingRecursive(Rx, Ry, p4, p6, p2, p7, rayTracerBase, c4, c6, c2, c7, level - 1, color1)).reduce(2);
        }
        if (c8.equal(c4) && c4.equal(c7) && c7.equal(c3))//if the 4 colors of the bottom left sub pixel are similar we return the average of the 4 colors
        {
            color1 = color1.add(c8,c4,c7,c3);
            color1=color1.reduce(5);}
        else {
            if (level - 1 == 0) {
                color1 = c8.add(c4).add(c7).add(c3);
                color1=color1.reduce(4);}
            color1 = color1.add(AdaptiveSuperSamplingRecursive(Rx, Ry, p8, p4, p7, p3, rayTracerBase, c8, c4, c7, c3, level - 1,color1)).reduce(2);
        }
        return color1;
    }


}
