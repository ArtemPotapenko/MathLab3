package result;

import exception.InfAnswerException;
import exception.NoAnswerException;
import matrix.Matrix;
import matrix.MatrixLinSystem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.EnumSet.range;

public class Result{

    public static double distance(List<Double> first, List<Double> second,int n){
        if (first.size() != n || second.size() !=n){
            throw new IndexOutOfBoundsException();
        }
        double sum = 0;
        for (int i = 0; i < n; i++){
            sum += Math.pow(first.get(i) - second.get(i),2);
        }
        return Math.pow(sum,0.5);
    }
    public static List<Double> getAnswer(int system_id, int number_of_unknowns, List<Double> initial_approximations){
        List<Function<List<Double>, Double>> functions = SNAEFunctions.get_functions(system_id);
        Matrix yakobi = MatrixYakoby.getYakobi(system_id, number_of_unknowns, initial_approximations);
        Matrix ans = new Matrix(number_of_unknowns,1);
        for (int i = 0; i < number_of_unknowns; i++){
            ans.setElement(i,0,-functions.get(i).apply(initial_approximations));
        }
        MatrixLinSystem matrixLinSystem = new MatrixLinSystem(yakobi, ans);
        try {
            matrixLinSystem.standardGaussSolution();
            double[] answerMatrix = matrixLinSystem.getAnswer();
            List<Double> result = new ArrayList<>();
            for (int i = 0; i < number_of_unknowns; i++){
                result.add(answerMatrix[i] + initial_approximations.get(i));
            }
            return result;
        } catch (NoAnswerException e) {
            throw new RuntimeException("Не возможно найти решение");
        } catch (InfAnswerException e) {
            return initial_approximations;
        }

    }
    /*
     * Complete the 'solve_by_fixed_point_iterations' function below.
     *
     * The function is expected to return a DOUBLE_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER system_id
     *  2. INTEGER number_of_unknowns
     *  3. DOUBLE_ARRAY initial_approximations
     */
    public static List<Double> solve_by_fixed_point_iterations(int system_id, int number_of_unknowns, List<Double> initial_approximations) {
        if (system_id<=0 || system_id >4 || number_of_unknowns != SNAEFunctions.get_functions(system_id).size() || number_of_unknowns != initial_approximations.size()){
            return initial_approximations;
        }

        List<Double> answer;
        List<Double> curAnswer = getAnswer(system_id,number_of_unknowns,initial_approximations);
        do{
            answer = curAnswer;
            curAnswer = getAnswer(system_id,number_of_unknowns,answer);
        }while (distance(answer,curAnswer,number_of_unknowns) > 0.000005);
        return answer;
    }
}