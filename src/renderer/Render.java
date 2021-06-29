package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

public class Render {
    ImageWriter _imageWriter = null;

    Camera _camera = null;
    RayTracerBase _rayTracerBase=null;
    int sample;
    int amountRays;

    public int getAmountRays() {
        return amountRays;
    }

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
     * constructor that creates a ray for every pixel and return the color of every pixel
     */
//    public void renderImage() {
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
//            int nX= _imageWriter.getNx();
//            int nY= _imageWriter.getNy();
//            for(int i=0;i<nY;i++) {
//                for (int j = 0; j < nX; j++) {
//                    Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
//                    Color pixelColor=_rayTracerBase.traceRay(ray);
//                    _imageWriter.writePixel(j,i,pixelColor);
//                }
//            }
//
//        }catch(MissingResourceException e){
//            throw new UnsupportedOperationException("Not implemented yet "+e.getClassName());
//        }
//    }

    public void printGrid(int interval, Color color) {

        if(_imageWriter==null){
            throw new MissingResourceException("empty field","ImageWriter","_imageWriter");
        }
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * constructor checks if the imagewriter is null else call the constructor
     */
    public void writeToImage(){
        if(_imageWriter==null){
            throw new MissingResourceException("empty field","ImageWriter","_imageWriter");
        }
        _imageWriter.writeToImage();

    }



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
//            int nX= _imageWriter.getNx();
//            int nY= _imageWriter.getNy();
//            List<Ray> ListRays=new LinkedList<>();
//            Color color=Color.BLACK;
//            for(int i=0;i<nY;i++) {
//                for (int j = 0; j < nX; j++) {
//                    ListRays= _camera.constructSeveralRayThroughPixel(nX, nY, j, i,amountRays);
//                    for (Ray ray:ListRays) {
//                        color=color.add(_rayTracerBase.traceRay(ray));
//
//                    }
//                   // Color pixelColor=_rayTracerBase.traceRay(ray);
//                    //_imageWriter.writePixel(j,i,pixelColor);
//                    for(int l=0;l<150;l++){
//                    color=color.add(_rayTracerBase.traceRay(ListRays.get(0)));}//le premier rau cest le rayon prinicpale
//
//                    color=color.reduce(ListRays.size()+150);// moyenne des couleurs des rayons lances
//                    _imageWriter.writePixel(j,i,color);
//                }
//            }
//
//        }catch(MissingResourceException e){
//            throw new UnsupportedOperationException("Not implemented yet "+e.getClassName());
//        }
//    }



   // package renderer;

    /**
     * Renderer class is responsible for generating pixel color map from a graphic
     * scene, using ImageWriter class
     *
     * @author Dan
     */
   // public class Render {
        // ...........
        private int _threads = 1;
        private final int SPARE_THREADS = 2;
        private boolean _print = false;

        /**
         * Pixel is an internal helper class whose objects are associated with a Render object that
         * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
         * its progress.<br/>
         * There is a main follow up object and several secondary objects - one in each thread.
         * @author Dan
         *
         */
        private class Pixel {
            private long _maxRows = 0;
            private long _maxCols = 0;
            private long _pixels = 0;
            public volatile int row = 0;
            public volatile int col = -1;
            private long _counter = 0;
            private int _percents = 0;
            private long _nextCounter = 0;

            /**
             * The constructor for initializing the main follow up Pixel object
             * @param maxRows the amount of pixel rows
             * @param maxCols the amount of pixel columns
             */
            public Pixel(int maxRows, int maxCols) {
                _maxRows = maxRows;
                _maxCols = maxCols;
                _pixels = maxRows * maxCols;
                _nextCounter = _pixels / 100;
                if (Render.this._print) System.out.printf("\r %02d%%", _percents);
            }

            /**
             *  Default constructor for secondary Pixel objects
             */
            public Pixel() {}

            /**
             * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
             * critical section for all the threads, and main Pixel object data is the shared data of this critical
             * section.<br/>
             * The function provides next pixel number each call.
             * @param target target secondary Pixel object to copy the row/column of the next pixel
             * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
             * finished, any other value - the progress percentage (only when it changes)
             */
            private synchronized int nextP(Pixel target) {
                ++col;
                ++_counter;
                if (col < _maxCols) {
                    target.row = this.row;
                    target.col = this.col;
                    if (_counter == _nextCounter) {
                        ++_percents;
                        _nextCounter = _pixels * (_percents + 1) / 100;
                        return _percents;
                    }
                    return 0;
                }
                ++row;
                if (row < _maxRows) {
                    col = 0;
                    if (_counter == _nextCounter) {
                        ++_percents;
                        _nextCounter = _pixels * (_percents + 1) / 100;
                        return _percents;
                    }
                    return 0;
                }
                return -1;
            }

            /**
             * Public function for getting next pixel number into secondary Pixel object.
             * The function prints also progress percentage in the console window.
             * @param target target secondary Pixel object to copy the row/column of the next pixel
             * @return true if the work still in progress, -1 if it's done
             */
            public boolean nextPixel(Pixel target) {
                int percents = nextP(target);
                if (percents > 0)
                    if (Render.this._print) System.out.printf("\r %02d%%", percents);
                if (percents >= 0)
                    return true;
                if (Render.this._print) System.out.printf("\r %02d%%", 100);
                return false;
            }
        }

        /**
         * This function renders image's pixel color map from the scene included with
         * the Renderer object
         */
        public void renderImage() {
            final Color color=Color.BLACK;
            final Camera camera =this._camera ;///_scene.getCamera();
            final int nX = _imageWriter.getNx();
            final int nY = _imageWriter.getNy();
            final double dist = camera.get_Distance();    //_rayTracerBase._scene.getDistance();
            final double width =  camera.get_width();//_imageWriter.
            final double height = _camera.get_height();//_imageWriter.getHeight();
           // final Camera camera =this._camera ///_scene.getCamera();

            final Pixel thePixel = new Pixel(nY, nX);

            // Generate threads
            Thread[] threads = new Thread[_threads];
            for (int i = _threads - 1; i >= 0; --i) {
                threads[i] = new Thread(() -> {
                    Pixel pixel = new Pixel();
                    while (thePixel.nextPixel(pixel)) {
                        List<Ray> rays = camera.constructSeveralRayThroughPixel(nX, nY, pixel.col, pixel.row,amountRays //
                               );
                        for (Ray ray:rays) {
                            color.add(_rayTracerBase.traceRay(ray));

                        }
                        // Color pixelColor=_rayTracerBase.traceRay(ray);
                        //_imageWriter.writePixel(j,i,pixelColor);
                        for(int l=0;l<150;l++){
                            color.add(_rayTracerBase.traceRay(rays.get(0)));}//le premier rau cest le rayon prinicpale

                        color.reduce(rays.size()+150);// moyenne des couleurs des rayons lances
                        _imageWriter.writePixel(pixel.col, pixel.row, color);
                    }
                });
            }

            // Start threads
            for (Thread thread : threads) thread.start();

            // Wait for all threads to finish
            for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
            if (_print) System.out.printf("\r100%%\n");
        }

        /**
         * Set multithreading <br>
         * - if the parameter is 0 - number of coress less 2 is taken
         *
         * @param threads number of threads
         * @return the Render object itself
         */
        public Render setMultithreading(int threads) {
            if (threads < 0)
                throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
            if (threads != 0)
                _threads = threads;
            else {
                int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
                if (cores <= 2)
                    _threads = 1;
                else
                    _threads = cores;
            }
            return this;
        }

        /**
         * Set debug printing on
         *
         * @return the Render object itself
         */
        public Render setDebugPrint() {
            _print = true;
            return this;
        }
    }

//// Example for setting multithreading and debug print in test files:
//        ......
//    ImageWriter imageWriter = new ImageWriter("teapot", 200, 200, 800, 800);
//    Render render = new Render(imageWriter, scene) //
//            .setMultithreading(3) //
//            .setDebugPrint();
//
//		render.renderImage();
//    // render.printGrid(50, java.awt.Color.YELLOW);
//		render.writeToImage();

//}