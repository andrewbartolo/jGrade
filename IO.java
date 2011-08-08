import java.io.*;

/**
 * IO's methods are all static.  IO should
 * export all processed data, both to
 * the console and to the .dat file.
 * This will simplify newFile() and
 * oldFile() in jGrade.  IO's main-
 * like method will only need objects
 * room and peers passed to it.  Then,
 * it can write all the data it needs
 * to the .dat file.  Paths must be first.
 * IO must read and write to .dat file.
 */

/**
 * Because else...if is evaluated sequentially, logic
 * checks for low <= x <= high are not necessary.  Simply
 * evaluating the remaining cases allows jGrade to
 * determine the amount of zeroes to be prefixed.  Because
 * each student's score is not evaluated for "between"
 * algebraically, the code is less verbose, and fewer
 * arguments are ultimately passed to the machine, increasing
 * efficiency.
 */

/**
 * Preconditions of method writeFile() specify that weights must
 * be between 1 and 99, inclusive.  This should be added as an
 * official JavaDoc comments precondition statement (@).  Check
 * functionality must be added to inputWeights() in class Gradebook
 * as well.
 */

/** All placeholder zero logic is contained in class IO.
 */

public class IO {
    // writeFile employs PrintWriter, an efficient way to
    // print string and integer values to a file in human-
    // readable form

    public static void writeFile(String path, Classroom room, Student[] peers) {
        try {
            FileWriter fw = new FileWriter(path);
            PrintWriter pw = new PrintWriter(fw, true);
            pw.println(room.getStudents());
            pw.println(room.getGrades());
            for (int i = 0; i < room.getGrades(); i++) {
                if (room.getWeights(i) <= 0) {
                    pw.print("00");
                }
                else if (room.getWeights(i) <= 9) {
                    pw.print("0" + Integer.toString(room.getWeights(i)));
                }
                else {
                    pw.print(Integer.toString(room.getWeights(i)));
                }
            }
            for (int x = 0; x < room.getStudents(); x++) {
                pw.println();
                pw.print(peers[x].getName() + "      ");
                for (int y = 0; y < room.getGrades(); y++) {
                    if (peers[x].getScores(y) <= 0) {
                        pw.print("000");
                    }
                    else if (peers[x].getScores(y) <= 9) {
                        pw.print("00" + Integer.toString(peers[x].getScores(y)));
                    }
                    else if (peers[x].getScores(y) <= 99 ) {
                        pw.print("0" + Integer.toString(peers[x].getScores(y)));
                    }
                    else {
                        pw.print(Integer.toString(peers[x].getScores(y)));
                    }
                }
                for (int i = room.getStudents(); i < 10; i++) {                             // this loop "fills" rows of empty grades with zeroes
                    pw.print("000");                                                       // for formatting purposes
                }
            }
            pw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructing and wrapping a reader simply for the
     * purpose of instantiating the array is inefficient.
     * Change in future.
     */
    public static Classroom readFileClassroom(String path) throws NumberFormatException, NullPointerException {
        Classroom room;                                 // Constructor injection provides a very elegant
        int instantiateStudents = 0;                    // Solution, employing arrays.  This is good style.
        // Employed below.
        try {
            FileInputStream fstream = new FileInputStream(path);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            instantiateStudents = Integer.parseInt(br.readLine());                             // Get the number of students in advance
            in.close();                                                                        // So that the array size may be specified
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        String[] data = new String[(instantiateStudents + 3)];                                // 3 is added to instantiateStudents to accomodate
        try {                                                                                 // rows for students, grades and weights[]
            FileInputStream fstream = new FileInputStream(path);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            // read file line by line, and store strings read into data[]
            for (int i = 0; i < 3; i++) {
                data[i] = br.readLine();
            }
            in.close();
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        int students = Integer.parseInt(data[0]);                   // students = instantiateStudents is possible, but
        int grades = Integer.parseInt(data[1]);                     // data[0] is parsed here for comprehensibility
        int[] weights = new int[grades];
        StringBuffer buffer = new StringBuffer();                   // only one instance of StringBuffer is created
        for (int x = 0; x < (grades * 2);) {
            for (int y = 0; y < 2; y++) {
                buffer.append(data[2].charAt(x));
                x++;
            }
            weights[((x / 2) - 1)] = Integer.parseInt(buffer.toString());
            buffer.setLength(0);                                   // buffer is cleared, in preparation for next weight value
        }

        room = new Classroom(students, grades, weights);            // room must be constructed after all data has been gathered
        return room;                                                // return room to jGrade
    }

    /**
     * readFileStudent needs access to room to instantiate
     * its array, peers[].  Pass room to readFileStudent as
     * a parameter to address this, or, increase scope of
     * room and peers[] by declaring them in the class-only
     * layer.  Use subclasses for readFileClassroom and
     * ReadFileStudent?  No.
     */
    public static Student[] readFileStudent(String path, Classroom room) throws NumberFormatException {
        String[] data = new String[room.getStudents()];
        Student[] peers = new Student[room.getStudents()];
        try {
            FileInputStream fstream = new FileInputStream(path);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            for (int i = 0; i < 3; i++) {           // read and discard the already-accounted-for
                br.readLine();                      // rows comprising students, grades, and weights[]
            }
            // read file line by line, and store strings read into data[]
            // note that data[] is zero-indexed, and data[0] is used initially
            for (int i = 0; ((line = br.readLine()) != null); i++) {
                data[i] = line;
            }
            in.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        // student data from .dat file has been stored into data[].
        // now, begin processing each line from data[] and construct
        // Classroom room when finished
        int spaceCount;
        String name;
        String gradeSet;
        StringBuffer buffer = new StringBuffer();
        for (int x = 0; x < room.getStudents(); x++) {              // loop to process all students
            int[] scores = new int[room.getGrades()];
            spaceCount = 0;
            name = null;                                            // reset all the variables
            gradeSet = null;
            for (int y = 0; y < data[x].length(); y++) { // Necessary to use length()?  Use getGrades()*3?
                if (data[x].charAt(y) == ' ') {                     // Use of == for comparison is acceptable
                    if (data[x].charAt(y + 1) == ' ' || data[x].charAt(y - 1) == ' ') {
                        spaceCount++;                               // here, as only individual characters are
                    }                                               // being compared
                }                                                   // Note that jGrade must encounter two spaces
                // in a row before it begins logging for the
                if (spaceCount == 6) {                              // six-space separator in the .dat file
                    name = data[x].substring(0, (y - 5));           // be wary of an off by one error here
                    gradeSet = data[x].substring((y + 1), data[x].length() - 1);
                    break;
                }
            }
            for (int z = 0; z < (room.getGrades() * 3);) {
                for (int a = 0; a < 3; a++) {
                    buffer.append(gradeSet.charAt(z));
                    z++;
                }
                scores[(z / 3) - 1] = Integer.parseInt(buffer.toString());
                buffer.setLength(0);
            }
            peers[x] = new Student(name, x, scores);                // construct a student, and reference it in peers[]
        }                                                           // end loop to process all students
        return peers;
    }
}