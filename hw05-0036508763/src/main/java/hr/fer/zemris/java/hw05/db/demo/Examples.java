package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Examples {
    public static void main(String[] args) {
        // db init
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            //
        }
        //String[] inputString = {"0000000001 Akšamović Marin 2", "0000000002 Bakamović Petra 3"};
        StudentDatabase db = new StudentDatabase(lines);

        // getters
        StudentRecord record = db.forJMBAG("0000000001");
        System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
        System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
        System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));

        // comparison
        IComparisonOperator oper1 = ComparisonOperators.LESS;
        System.out.println(oper1.satisfied("Ana", "Jasna"));  // true, since Ana < Jasna

        IComparisonOperator oper2 = ComparisonOperators.LIKE;
        System.out.println(oper2.satisfied("Zagreb", "Aba*"));  // false
        System.out.println(oper2.satisfied("AAA", "AA*AA"));    // false
        System.out.println(oper2.satisfied("AAAA", "AA*AA"));   // true

        // expression
        ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*", ComparisonOperators.LIKE);
        StudentRecord record2 = db.forJMBAG("0000000004");
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),  // returns lastName from given record
                expr.getStringLiteral()             // returns "Bos*"
                );
    }
}
