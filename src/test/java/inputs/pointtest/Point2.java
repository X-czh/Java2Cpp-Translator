package inputs.pointtest;

import inputs.point.Point;

/**
 * An immutable four-dimensional point.
 */
public class Point2 {

    /** The dimensions. */
    public static final int DIMENSIONS = 4;

    /** The origin. */
    public static final Point ORIGIN = new Point();

    /** Attempt at encapsulation. */
    private final double[] coordinates;

    public Point2() {
        this(0,0,0,0);
    }

    /**
     * Create a new point.
     *
     * @param c1 The first coordinate.
     * @param c2 The second coordinate.
     * @param c3 The third coordinate.
     * @param c4 The fourth coordinate.
     */
    public Point2(double c1, double c2, double c3, double c4) {
        coordinates = new double[] { c1, c2, c3, c4 };
    }

    /**
     * Get the specified coordinate.
     *
     * @param idx The index.
     * @return The coordinate.
     * @throws IndexOutOfBoundsException Signals an invalid index.
     */
    public double getCoordinate(int idx) {
        try {
            return coordinates[idx];
        } catch (ArrayIndexOutOfBoundsException x) {
            // ...actually our encapsulation is a little leaky
            // and this is kind of unsafe, we can have runtime errors!
            throw new IndexOutOfBoundsException("Index: " + idx);
        }
    }

    /**
     * Determine the distance from the specified point.
     *
     * The distances is equal to the square root of the sum
     * of the squares of the differences of each point. Pythagoras!
     *
     * @param p The other point.
     * @return The distance.
     */
    public double getDistanceFrom(Point p) {
        double sumSquares = 0;

        for (int i = 0; i < DIMENSIONS; i++) {
            double diff = this.getCoordinate(i) - p.getCoordinate(i);
            sumSquares += diff * diff;
        }

        return Math.sqrt(sumSquares);
    }

    /**
     * Get a string representation for this point.
     *
     * @return The string representation.
     */
    public String toString() {
        return "Point(" +
               getCoordinate(0) + ", " +
               getCoordinate(1) + ", " +
               getCoordinate(2) + ", " +
               getCoordinate(3) + ")";
    }

}

