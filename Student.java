import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Are in and in2 good solutions?
 * Should there be one solution?
 */

public class Student {
    private String name;
    private int number;
    private int[] scores;

    public Student(int number, Classroom room) {
        this.number = number;   // now the student itself knows its position
        scores = new int[room.getGrades()];     // note that number is zero-indexed

        System.out.println("Enter name for Student " + (number + 1) + ":");
        Scanner in = new Scanner(System.in);
        name = in.nextLine();

        gradeloop: for (int i = 0; i < room.getGrades(); i++) {
            checkloop: while (true) {
                try {
                    System.out.println("Enter " + name + "'s grade on Assignment " + (i + 1) + ":");
                    while (true) {
                        int check = 0;
                        Scanner in2 = new Scanner(System.in);
                        check = in2.nextInt();
                        if (check >= 0 && check <= 100) {   // again, assuming the user's initial input is acceptable
                            scores[i] = check;
                            break checkloop;
                        }
                        else {
                            System.out.println("Value out of range.  Please re-enter:");
                        }
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input.  Please re-enter:");
                }
            }
        }
    }

    /**
     * Mutators and Accessors, in that order, below.
     * (Setter methods and getter methods.)
     */

    public Student(String name, int number, int[] scores) {
        this.name = name;
        this.number = number;
        this.scores = scores;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setScores(int i, int scores) {
        this.scores[i] = scores;
    }

    public int getScores(int i) {
        return scores[i];
    }
}