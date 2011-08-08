import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class jGrade {

    public static void main(String[] args) {
        int choice = 0;

        System.out.println();                                       // puts the first line of jGrade one line
        System.out.println("Welcome to jGrade.");                   // down on the console for readability
        System.out.println("Enter 1 to create a new file.");
        System.out.println("Enter 2 to use existing data.");

        choiceloop: while (true) {
            while (true) {
                try {
                    Scanner in = new Scanner(System.in);
                    choice = in.nextInt();
                    break;
                }
                catch (InputMismatchException e) {
                    System.out.println("Not an option.  Please re-enter:");
                }
            }

            switch (choice) {
                case 1: newData();
                break choiceloop;
                case 2: oldData();
                break choiceloop;
                default: System.out.println("Not an option.  Please re-enter:");
            }
        }
        System.out.println("All operations completed.");
        System.out.println("Thank you for using jGrade.");
        System.out.println();               // again, to offset the last line in the console
        // Ask to run again?
    }

    public static void newData() {
        System.out.println("Enter complete location for new .dat file:");
        System.out.println("Include filepath, filename and .dat extension.");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();                        // accomplished here in one step.  no need for increased scope

        Classroom room = new Classroom();
        Student[] peers = new Student[room.getStudents()];        // must avoid NullPointerException
        for (int i = 0; i < room.getStudents(); i++) {
            peers[i] = new Student(i, room);            // construct and initialize Students
        }
        // pass object room and object array peers[] to Gradebook
        Gradebook book = new Gradebook(room, peers);

        IO.writeFile(path, room, peers);                // IO.writeFile or FileIO.write?  Consider IO's other content, if any

        System.out.println();                           // As displayData() displays end-result computations for the
        displayData(room, peers, book);                 // program, it is appropriate to separate displayData()'s output
        System.out.println();                           // from the surrounding text with line breaks before and after.
    }

    public static void oldData() {
        Classroom room = null;
        Student[] peers = null;
        Gradebook book = null;

        System.out.println("Enter complete location of existing .dat file:");
        System.out.println("Include filepath, filename and .dat extension.");

        while (true) {
            try {
                Scanner in = new Scanner(System.in);
                String path = in.nextLine();                    // accomplished here in one step.  no need for increased scope
                // assign room, peers, and book return value from IO
                // to the static variables in this class
                // then, call displayData()

                room = IO.readFileClassroom(path);        // keep a local copy of the objects
                peers = IO.readFileStudent(path, room);
                book = new Gradebook(room, peers);        // book is constructed with raw data from room and peers
                break;                                    // IO must export book here, as the system must display averages
            }
            catch (NumberFormatException e) {
                System.out.println();
                System.out.println("The file you specified does not exist.");
                System.out.println("Please re-enter:");
            }

            catch (NullPointerException e) {
                System.out.println();
                System.out.println("The file you specified is corrupted.");
                System.out.println("Please choose another file:");
            }
        }

        System.out.println();                           // As displayData() displays end-result computations for the
        displayData(room, peers, book);                 // program, it is appropriate to separate displayData()'s output
        System.out.println();                           // from the surrounding text with line breaks before and after.
    }

    /**
     * Remember, System.out.printf() needs a call
     * to System.out.println() immediately after
     * it to advance to the next line.  For the
     * sake of readability, string literals and
     * their accompanying non-formatted number
     * outputs below are coded sequentially, even
     * when concatenation is possible.
     */

    public static void displayData(Classroom room, Student[] peers, Gradebook book) {
        // is there a one-step println formatted method?
        System.out.printf("Class Average - %.2f", book.getClassAverage());
        System.out.println();
        for (int i = 0; i < room.getStudents(); i++) {
            System.out.printf(peers[i].getName() + " - " + "%.2f", book.getStudentAverages(i));
            System.out.println();
        }
        for (int i = 0; i < room.getGrades(); i++) {
            System.out.print("For Grade " + (i + 1) + " - Average is ");
            System.out.printf("%.2f", book.getGradeAverages(i));
            System.out.print(", Maximum is ");
            System.out.print(book.getGradeMaximums(i));
            System.out.print(", Minimum is ");
            System.out.print(book.getGradeMinimums(i));
            System.out.println();
        }
    }
}