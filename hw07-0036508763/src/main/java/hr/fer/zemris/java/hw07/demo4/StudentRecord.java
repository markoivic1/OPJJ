package hr.fer.zemris.java.hw07.demo4;

/**
 * Record of a single student.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class StudentRecord {
    /**
     * jmbag of a student.
     */
    private String jmbag;
    /**
     * name of a student.
     */
    private String ime;
    /**
     * last name of a student
     */
    private String prezime;
    /**
     * score on midterm
     */
    private double brBodovaNaMI;
    /**
     * score on final
     */
    private double brBodovaNaZI;
    /**
     * score on laboratory exercises.
     */
    private double brBodovaLab;
    /**
     * grade
     */
    private int ocjena;

    /**
     * Constructor for which maps values from a given arguments.
     * @param line String which contains data used in {@link StudentRecord}
     */
    public StudentRecord(String line) {
        String[] split = line.split("\t");
        this.jmbag = split[0];
        this.ime = split[1];
        this.prezime = split[2];
        this.brBodovaNaMI = Double.parseDouble(split[3]);
        this.brBodovaNaZI = Double.parseDouble(split[4]);
        this.brBodovaLab = Double.parseDouble(split[5]);
        this.ocjena = Integer.parseInt(split[6]);
    }

    /**
     * Formatted in a same way the arguments have been given to the constructor.
     * @return
     */
    @Override
    public String toString() {
        return jmbag + "\t"
                + prezime + "\t"
                + ime + "\t"
                + brBodovaNaMI + "\t"
                + brBodovaNaZI + "\t"
                + brBodovaLab + "\t"
                + ocjena;
    }

    /**
     * Getter for jmbag
     * @return returns jmbag
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Getter for name.
     * @return returns name.
     */
    public String getIme() {
        return ime;
    }

    /**
     * Getter for last name.
     * @return Returns last name.
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Getter for score on midterm.
     * @return Returns score on midterm.
     */
    public double getBrBodovaNaMI() {
        return brBodovaNaMI;
    }

    /**
     * Getter for score on final
     * @return returns score on final.
     */
    public double getBrBodovaNaZI() {
        return brBodovaNaZI;
    }

    /**
     * Getter for score on laboratory exercises.
     * @return Returns score on laboratory exercises.
     */
    public double getBrBodovaLab() {
        return brBodovaLab;
    }

    /**
     * Getter for grade.
     * @return Returns grade,
     */
    public int getOcjena() {
        return ocjena;
    }
}
