package matrix;

import exception.SquareException;

public class Matrix {
    private int columLength;
    private int stringLength;
    private double[][] elements;

    public Matrix(int columLength, int stringLength) {
        this.columLength = columLength;
        this.stringLength = stringLength;
        elements = new double[columLength][stringLength];
    }

    public int getColumLength() {
        return columLength;
    }

    public int getStringLength() {
        return stringLength;
    }

    public void setElement(int stringNumber, int columNumber, double value) {
        elements[stringNumber][columNumber] = value;

    }

    public double getElement(int stringNumber, int columNumber) {
        return elements[stringNumber][columNumber];
    }

    public Matrix triangleMatrix() {
        Matrix matrix = copy();
        for (int i = 0; i < getColumLength() && i < getStringLength(); i++) {
            if (matrix.getElement(i, i) == 0) {
                for (int j = i + 1; j < getColumLength(); j++) {
                    if (matrix.getElement(j, i) != 0) {
                        matrix = matrix.swapString(i, j);
                        break;
                    }
                }
                continue;
            }

            for (int j = i + 1; j < getColumLength(); j++) {
                matrix = matrix.sumString(j, i, -matrix.getElement(j, i) / matrix.getElement(i, i));
                matrix.setElement(j, i, 0);
            }
        }
        return matrix;

    }

    private Matrix copy() {
        return swapString(0, 0);
    }

    public boolean isNull() {
        for (int i = 0; i < getColumLength(); i++) {
            for (int j = 0; j < getStringLength(); j++) {
                if (getElement(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int rank() {
        int rank = 0;
        if (isNull()) {
            return 0;
        }
        Matrix matrix = triangleMatrix();
        for (int i = 0; i < matrix.getColumLength(); i++) {
            for (int j = 0; j < matrix.getStringLength(); j++) {
                if (matrix.getElement(i, j) != 0) {
                    rank++;
                    break;
                }
            }
        }
        return rank == 0 ? 1 : rank;
    }

    public boolean isSquare() {
        return getColumLength() == getStringLength();
    }

    public Matrix swapString(int firstString, int secondString) {
        Matrix matrix = new Matrix(getColumLength(), getStringLength());
        for (int i = 0; i < getColumLength(); i++) {
            for (int j = 0; j < getStringLength(); j++) {
                if (i != firstString && i != secondString) {
                    matrix.setElement(i, j, getElement(i, j));
                } else if (i == firstString) {
                    matrix.setElement(i, j, getElement(secondString, j));
                } else {
                    matrix.setElement(i, j, getElement(firstString, j));
                }

            }
        }
        return matrix;
    }

    public Matrix sumString(int firstString, int secondString, double prod) {
        Matrix matrix = new Matrix(getColumLength(), getStringLength());
        for (int i = 0; i < getColumLength(); i++) {
            for (int j = 0; j < getStringLength(); j++) {
                if (i != firstString) {
                    matrix.setElement(i, j, getElement(i, j));
                } else {
                    matrix.setElement(i, j, getElement(i, j) + prod * getElement(secondString, j));
                }
            }
        }
        return matrix;
    }

}
