package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Defines single student's data.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class StudentRecord {
    /**
     * JMBAG of the student.
     */
    private String jmbag;
    /**
     * First name of the student.
     */
    private String firstName;
    /**
     * Last name of the student.
     */
    private String lastName;
    /**
     * Final grade of the student.
     */
    private int finalGrade;

    /**
     * Constructor takes student info and stores it in its variables.
     * @param jmbag JMBAG of a student.
     * @param firstName First name of a student.
     * @param lastName Last name of a student.
     * @param grade Grade of a student.
     */
    public StudentRecord(String jmbag, String firstName, String lastName, int grade) {
        this.jmbag = jmbag;
        this.firstName = firstName;
        this.lastName = lastName;
        this.finalGrade = grade;
    }

    /**
     * Method used for comparing two {@link StudentRecord}s. Students are equal if their JMBAGs are equal.
     * @param o Other Object which is being compared.
     * @return Returns true of the JMBAGs are the same, return false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    /**
     * Getter for JMBAG
     * @return Returns JMBAG
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Getter for first name.
     * @return Return first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for the last name
     * @return Returns last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for the grade.
     * @return Returns grade.
     */
    public int getFinalGrade() {
        return finalGrade;
    }

    /**
     * Hash code which uses JMBAG in it's hashing function.
     * @return Returns hash code of a student.
     */
    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
