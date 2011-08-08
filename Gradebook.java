public class Gradebook {
    private double studentAverages[];
    private double classAverage;
    private double gradeAverages[];
    private int gradeMaximums[];
    private int gradeMinimums[];

    /**
     * Type double used rather than float for all
     * real numbers.  Double precision can be faster 
     * on modern processors implementing enhanced 
     * instruction sets.  Small optimization.
     */

    public Gradebook(Classroom room, Student[] peers) {
        // a simple listing of Gradebook's methods should go here.
        // to be fully constructed and initialized,
        // Gradebook will need the data.
        // then, if need be, the data may be accessed or modified
        // through other methods
        // accessor and mutator methods must be provided for all
        // appropriate variables.
        
        // Entire objects are passed as parameters - this may be inefficient
        // However, multiple variables from the same object are needed and used
        // Possibly some optimization to be done here

        studentAverages(room, peers);
        classAverage = classAverage(room, peers);
        gradeAverages(room, peers);
        gradeMaximums(room, peers);
        gradeMinimums(room, peers);
    }

    /**
     * Average or Avg... or Mean?
     * Max or Maximum?  Min or Minimum?
     * Should be easily modifiable.
     * This applies to variables, too.
     * Search by name.  Singular/Plural?
     * Also, once again, arrays must be
     * modified by calling instance
     * methods from the constructor.
     * To avoid constructor clutter, the
     * recursive code necessary for
     * initialization of the arrays is
     * included in the instance method
     * itself.
     */
    public void studentAverages(Classroom room, Student[] peers) {
        studentAverages = new double[room.getStudents()];       // instantiate the array first
        double sum;                                             // declared here, then...
        for (int x = 0; x < room.getStudents(); x++) {
            sum = 0;                                            // ...initialized here for efficiency
            for (int y = 0; y < room.getGrades(); y++) {
                sum += (peers[x].getScores(y)) * ((room.getWeights(y)) * 0.01);
            }
            studentAverages[x] = sum;
        }
    }

    public double classAverage(Classroom room, Student[] peers) {
        double sum = 0;                                             // this variable needs no reset
        for (int i = 0; i < room.getStudents(); i++) {
            sum += studentAverages[i];
        }
        return (sum / room.getStudents());
    }

    public void gradeAverages(Classroom room, Student[] peers) {
        gradeAverages = new double[room.getGrades()];           // instantiate the array first
        double sum;                                             // declared here, then...
        for (int x = 0; x < room.getGrades(); x++) {
            sum = 0;                                            // ...initialized here for efficiency
            for (int y = 0; y < room.getStudents(); y++) {
                sum += peers[y].getScores(x);
            }
            gradeAverages[x] = (sum / room.getStudents());
        }
    }

    /**
     * Combine gradeMaximums() and gradeMinimums()
     * into a single gradeMaxMin()?  Too complex?
     */

    public void gradeMaximums(Classroom room, Student[] peers) {
        gradeMaximums = new int[room.getGrades()];                          // instantiate the array first
        int maximum;
        for (int x = 0; x < room.getGrades(); x++) {
            maximum = peers[0].getScores(x);                                // 0 should not be used, but this is safest
            for (int y = 0; y < room.getStudents(); y++) {                  // again, slightly inefficient, but safest (y = 0)
                if (peers[y].getScores(x) > maximum) {
                    maximum = peers[y].getScores(x);
                }
            }
            gradeMaximums[x] = maximum;
        }
    }

    public void gradeMinimums(Classroom room, Student[] peers) {
        gradeMinimums = new int[room.getGrades()];                          // instantiate the array first
        int minimum;
        for (int x = 0; x < room.getGrades(); x++) {
            minimum = peers[0].getScores(x);                                // must not be initialized 0, as 0 may not be used
            for (int y = 0; y < room.getStudents(); y++) {                  // again, slightly inefficient, but safest (y = 0)
                if (peers[y].getScores(x) < minimum) {
                    minimum = peers[y].getScores(x);
                }
            }
            gradeMinimums[x] = minimum;
        }
    }

    public void setStudentAverages(int i, int averages) {
        this.studentAverages[i] = averages;
    }

    public double getStudentAverages(int i) {
        return studentAverages[i];
    }

    public void setClassAverage(int average) {
        this.classAverage = average;
    }

    public double getClassAverage() {
        return classAverage;
    }

    public void setGradeAverages(int i, int averages) {
        this.gradeAverages[i] = averages;
    }

    public double getGradeAverages(int i) {
        return gradeAverages[i];
    }

    public void setGradeMaximums(int i, int maximums) {
        this.gradeMaximums[i] = maximums;
    }

    public int getGradeMaximums(int i) {
        return gradeMaximums[i];
    }

    public void setGradeMinimums(int i, int minimums) {
        this.gradeMinimums[i] = minimums;
    }

    public int getGradeMinimums(int i) {
        return gradeMinimums[i];
    }
}