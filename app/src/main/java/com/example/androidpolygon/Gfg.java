package com.example.androidpolygon;

import java.util.List;

public class Gfg {
    public static class Point {
        public float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Line {
        public Point p1, p2;

        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    static int onLine(Line l1, Point p) {
        // Check whether p is on the line or not
        if (p.x <= Math.max(l1.p1.x, l1.p2.x)
                && p.x >= Math.min(l1.p1.x, l1.p2.x)
                && (p.y <= Math.max(l1.p1.y, l1.p2.y)
                && p.y >= Math.min(l1.p1.y, l1.p2.y)))
            return 1;

        return 0;
    }

    static int direction(Point a, Point b, Point c) {
        float val = (b.y - a.y) * (c.x - b.x)
                - (b.x - a.x) * (c.y - b.y);

        if (val == 0)

            // Collinear
            return 0;

        else if (val < 0)

            // Anti-clockwise direction
            return 2;

        // Clockwise direction
        return 1;
    }

    static int isIntersect(Line l1, Line l2) {
        // Four direction for two lines and points of other
        // line
        int dir1 = direction(l1.p1, l1.p2, l2.p1);
        int dir2 = direction(l1.p1, l1.p2, l2.p2);
        int dir3 = direction(l2.p1, l2.p2, l1.p1);
        int dir4 = direction(l2.p1, l2.p2, l1.p2);

        // When intersecting
        if (dir1 != dir2 && dir3 != dir4)
            return 1;

        // When p2 of line2 are on the line1
        if (dir1 == 0 && onLine(l1, l2.p1) == 1)
            return 1;

        // When p1 of line2 are on the line1
        if (dir2 == 0 && onLine(l1, l2.p2) == 1)
            return 1;

        // When p2 of line1 are on the line2
        if (dir3 == 0 && onLine(l2, l1.p1) == 1)
            return 1;

        // When p1 of line1 are on the line2
        if (dir4 == 0 && onLine(l2, l1.p2) == 1)
            return 1;

        return 0;
    }

    static int checkInside(Point[] poly, int n, Point p) {

        // When polygon has less than 3 edge, it is not
        // polygon

        if (n < 3)
            return 0;

        // Create a point at infinity, y is same as point p
        Point pt = new Point(9999, p.y);
        Line exline = new Line(p, pt);
        int count = 0;
        int i = 0;
        do {

            // Forming a line from two consecutive points of
            // poly
            Line side
                    = new Line(poly[i], poly[(i + 1) % n]);
            if (isIntersect(side, exline) == 1) {

                // If side is intersects exline
                if (direction(side.p1, p, side.p2) == 0)
                    return onLine(side, p);
                count++;
            }
            i = (i + 1) % n;
        } while (i != 0);

        // When count is odd
        return count & 1;
    }


    static void invoke(Point[] polygon, Point p) {
//        Point[] polygon
//                = { new Point(1, 0), new Point(8, 3),
//                new Point(8, 8), new Point(1, 5) };
//        Point p = new Point(3, 5);
        int n = polygon.length;
        if (checkInside(polygon, n, p) == 1)
            System.out.println("Point is Inside");
        else
            System.out.println("Point is Outside");
    }

    static Boolean _checkIfValidMarker(Point tap, List<Point> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    static  Boolean rayCastIntersect(Point tap, Point vertA, Point vertB) {
        double aY = vertA.y;
        double bY = vertB.y;
        double aX = vertA.x;
        double bX = vertB.x;
        double pY = tap.y;
        double pX = tap.x;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY) || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }

}
