import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Classroom {
    private int students;
    private int grades;
    private int[] weights;

    /**
     * No parameters are designed to be passed to
     * Classroom() constructor.  It makes sense to
     * bundle all of Classroom's instance methods
     * together within the class itself.
     */

    public Classroom() {                    // replaces the default constructor
        students = inputStudents();
        grades = inputGrades();
        inputWeights();                 // inputWeights must modify the array (sequentially) by itself
    }

    public Classroom(int students, int grades, int[] weights) {
        this.students = students;               // all processing and typecasting
        this.grades = grades;                   // of data is done in IO and then
        this.weights = weights;                 // explicitly passed to Classroom
        // (int, int, int[])
    }

    public int inputStudents() {
        int students;            // declared here to hide private int students
        System.out.println("Enter # of students in the class:");
        System.out.println("Minimum 1 student, maximum 25 students.");

        while (true) {
            Scanner in = new Scanner(System.in);
            try {
                students = in.nextInt();
                if (students >= 1 && students <= 25) {          // assume that input is correct first
                    return students;                            // this is the first statement evaluated
                }
                else {
                    System.out.println("Value out of range.  Please re-enter:");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input.  Please re-enter:");
            }
        }
    }

    public int inputGrades() {
        int grades;             // declared here to hide private int grades
        System.out.println("Enter # of grades per student:");
        System.out.println("Minimum 4 grades, maximum 10 grades.");

        while (true) {
            Scanner in = new Scanner(System.in);
            try {
                grades = in.nextInt();
                if (grades >= 4 && grades <= 10) {          // again, assume that the user's input is acceptable initially...
                    return grades;
                }
                else {                                      // ...but provide a fallback if user input is out of range
                    System.out.println("Value out of range.  Please re-enter:");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input.  Please re-enter:");
            }
        }
    }

    /**
     * Find better names for inputloop, currentcheck,
     * and totalcheck; specifically, inputloop?
     */
    public void inputWeights() {
        weights = new int[grades];
        inputloop: while (true) {
            for (int i = 0; i < grades; i++) {
                currentcheck: while (true) {
                    int check = 0;                                  // this check ensures that the value entered is
                    // between 1 and 99, inclusive...
                    try {
                        System.out.println("Enter weight of Grade " + (i + 1) + ":");
                        Scanner in = new Scanner(System.in);
                        check = in.nextInt();
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid input.  Please re-enter:");
                    }
                    if (check >= 1 && check <= 99) {
                        weights[i] = check;
                        break currentcheck;
                    }
                    else {
                        System.out.println("Value out of range.  Please re-enter:");    // include instructions above?
                    }                                                                   // with the loop?
                }
            }
            totalcheck: while (true) {
                int check = 0;                          // ...this check ensures that all values entered add up to 100
                for (int i = 0; i < grades; i++) {      // each check is hidden from the other
                    check += weights[i];
                }
                if (check == 100) {
                    break inputloop;
                }
                else {
                    System.out.println("Weights entered to not add up to 100.  Please re-enter:");
                }
                break totalcheck;
            }
        }
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public int getStudents() {
        return students;
    }

    public void setGrades(int grades) {
        this.grades = grades;
    }

    public int getGrades() {
        return grades;
    }

    /**
     * for setWeights() and getWeights() below, array
     * position reference parameter "i" must be passed
     * first.
     */
    public void setWeights(int i, int weights) {
        this.weights[i] = weights;
    }

    public int getWeights(int i) {
        return weights[i];
    }
}