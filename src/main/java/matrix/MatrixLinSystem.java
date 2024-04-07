package matrix;

import exception.InfAnswerException;
import exception.NoAnswerException;
import exception.SquareException;

public class MatrixLinSystem {
    public double[] getAnswer() {
        return answer;
    }

    private Matrix coefficients;
    private Matrix results;
    private double[] answer;

    public MatrixLinSystem(int columLength, int stringLength) {
        coefficients = new Matrix(columLength, stringLength);
        results = new Matrix(columLength, 1);

    }

    public MatrixLinSystem(Matrix coefficients, Matrix results) {
        this.coefficients = coefficients;
        this.results = results;
    }

    public MatrixLinSystem(Matrix mergeMatrix) {
        coefficients = new Matrix(mergeMatrix.getColumLength(), mergeMatrix.getStringLength() - 1);
        results = new Matrix(mergeMatrix.getColumLength(), 1);
        for (int i = 0; i < mergeMatrix.getColumLength(); i++) {
            for (int j = 0; j < mergeMatrix.getStringLength() - 1; j++) {
                coefficients.setElement(i, j, mergeMatrix.getElement(i, j));
            }
        }
        for (int i = 0; i < mergeMatrix.getColumLength(); i++) {
            results.setElement(i, 0, mergeMatrix.getElement(i, mergeMatrix.getStringLength() - 1));
        }
    }

    public Matrix mergeMatrix() {
        Matrix merge = new Matrix(coefficients.getColumLength(), coefficients.getStringLength() + 1);
        for (int i = 0; i < coefficients.getColumLength(); i++) {
            for (int j = 0; j < coefficients.getStringLength(); j++) {
                merge.setElement(i, j, coefficients.getElement(i, j));
            }
        }
        for (int i = 0; i < coefficients.getColumLength(); i++) {
            merge.setElement(i, merge.getStringLength() - 1, results.getElement(i, 0));
        }
        return merge;
    }

    public Matrix getCoefficients() {
        return coefficients;
    }

    public Matrix getResults() {
        return results;
    }

    public MatrixLinSystem triangleMatrix() {
        return new MatrixLinSystem(mergeMatrix().triangleMatrix());
    }

    public int rank() {
        return mergeMatrix().rank();
    }

    public void standardGaussSolution() throws NoAnswerException, InfAnswerException {
        int coefficientsRank = coefficients.rank();
        int mergeRank = rank();
        if (coefficientsRank < mergeRank) {
            throw new NoAnswerException("Решений нет");
        }
        if (coefficientsRank == mergeRank) {
            if (coefficientsRank != coefficients.getStringLength()) {
                throw new InfAnswerException("Решений беспонечно много");
            } else {
                MatrixLinSystem matrix = triangleMatrix();
                answer = new double[mergeRank];
                for (int i = mergeRank - 1; i >= 0; i--) {
                    double temp = matrix.results.getElement(i, 0);
                    answer[i] = matrix.results.getElement(i, 0);
                    for (int j = i + 1; j < mergeRank; j++) {
                        answer[i] -= matrix.coefficients.getElement(i, j) * answer[j];
                    }
                    answer[i] /= matrix.coefficients.getElement(i, i);

                }
            }

        }
    }
}