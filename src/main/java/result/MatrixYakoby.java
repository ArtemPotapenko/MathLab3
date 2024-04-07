package result;
import matrix.Matrix;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.tan;


public class MatrixYakoby {

    public static Matrix getYakobi(int index, int n, List<Double> args){

        switch (index) {
            case (1) -> {
                Matrix matrix = new Matrix(n, n);
                matrix.setElement(0, 0, Math.cos(args.get(0)));
                matrix.setElement(0, 1, 0);
                matrix.setElement(1, 0, args.get(1) / 2);

                matrix.setElement(1, 1, args.get(0) / 2);
                return matrix;
            }
            case (2) -> {
                Matrix matrix = new Matrix(n, n);
                matrix.setElement(0, 0, args.get(1) / pow(Math.cos(args.get(0) * args.get(1) + 0.4), 2) - 2 * args.get(0));
                matrix.setElement(0, 1, args.get(0) / pow(Math.cos(args.get(0) * args.get(1) + 0.4), 2));
                matrix.setElement(1, 0, 1.8 * args.get(0));

                matrix.setElement(1, 1, 4 * args.get(1));
                return matrix;
            }
            case (3) -> {
                Matrix matrix = new Matrix(n, n);
                matrix.setElement(0, 0, args.get(1) / pow(Math.cos(args.get(0) * args.get(1)), 2) - 2 * args.get(0));
                matrix.setElement(0, 1, args.get(0) / pow(Math.cos(args.get(0) * args.get(1)), 2));
                matrix.setElement(1, 0, args.get(0));

                matrix.setElement(1, 1, 4 * args.get(1));
                return matrix;
            }
            case (4) -> {
                Matrix matrix = new Matrix(n, n);
                matrix.setElement(0, 0, 2*args.get(0));
                matrix.setElement(0, 1, 2*args.get(1));
                matrix.setElement(0, 2, 2*args.get(2));
                matrix.setElement(1, 0, 4 * args.get(0));
                matrix.setElement(1, 1, 2 * args.get(1));
                matrix.setElement(1, 2, -4);
                matrix.setElement(2, 0, 6 * args.get(0));
                matrix.setElement(2, 1, -4);
                matrix.setElement(2, 2, 2*args.get(2));
                return matrix;
            }
            default -> {
                return new Matrix(0, 0);
            }
        }
    }

}
