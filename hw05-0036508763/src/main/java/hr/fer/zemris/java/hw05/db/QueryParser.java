package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser used to parse tokens from {@link QueryLexer}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class QueryParser {
    /**
     * Query is the list in which the conditional expressions extracted from the lexer are stored.
     */
    private List<ConditionalExpression> query;
    /**
     * JMBAG is used for O(1) retrieval if the query is direct.
     */
    private String JMBAG;
    /**
     * Query lexer which is used to extract tokens.
     */
    private QueryLexer lexer;
    /**
     * Indicates that the query is direct which enables us to get certain types of queries in O(1)
     */
    private boolean directQuery;


    /**
     * Constructor for the parser.
     * @param input Input is a query which will be passed to the lexer.
     */
    public QueryParser(String input) {
        query = new ArrayList<>();
        lexer = new QueryLexer(input);
        parse();
    }

    /**
     * Method which organizes lexer token in a sensible way.
     *
     * Data is stored in a query.
     */
    public void parse() {
        extractExpression();
        while (true) {
            lexer.nextToken();
            if (lexer.getToken().getType().equals(TokenType.EOF)) {
                break;
            }
            if ((((String) lexer.getToken().getValue()).toLowerCase()).equals("and")) {
                continue;
            }
            extractExpression();
        }
        if ((JMBAG != null) && (query.size() == 1)) {
            directQuery = true;
        }
    }

    /**
     * Private method used to extract the expression.
     */
    private void extractExpression() {
        String currentGetter = ((String) lexer.getToken().getValue());
        IFieldValueGetter fieldValueGetter = getFieldValueGetter();
        lexer.nextToken();
        String currentOperatorAsString = ((String) lexer.getToken().getValue());
        IComparisonOperator comparisonOperator = getComparisonOperator();
        lexer.nextToken();
        if (!lexer.getToken().getType().equals(TokenType.STRING_LITERAL)) {
            throw new IllegalArgumentException("Invalid string literal");
        }
        String stringLiteral = (String) lexer.getToken().getValue();
        if (currentGetter.equals("jmbag") && currentOperatorAsString.equals("=")) {
            JMBAG = stringLiteral;
        }
        query.add(new ConditionalExpression(fieldValueGetter, stringLiteral, comparisonOperator));
    }

    /**
     * Gets the correct {@link FieldValueGetters}
     * @return Returns {@link FieldValueGetters}
     */
    private IFieldValueGetter getFieldValueGetter() {
        String value = ((String)lexer.getToken().getValue());
        switch (value) {
            case "jmbag":
                return FieldValueGetters.JMBAG;
            case "firstName":
                return FieldValueGetters.FIRST_NAME;
            case "lastName":
                return FieldValueGetters.LAST_NAME;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Retruns proper operator.
     * @return Returns {@link ComparisonOperators}
     */
    private IComparisonOperator getComparisonOperator() {
        String value = ((String)lexer.getToken().getValue());
        switch (value) {
            case "<":
                return ComparisonOperators.LESS;
            case "<=":
                return ComparisonOperators.LESS_OR_EQUALS;
            case ">":
                return ComparisonOperators.GREATER;
            case ">=":
                return ComparisonOperators.GREATER_OR_EQUALS;
            case "=":
                return ComparisonOperators.EQUALS;
            case "!=":
                return ComparisonOperators.NOT_EQUALS;
            case "LIKE":
                return ComparisonOperators.LIKE;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Checks the query is direct.
     * @return Returns true if the query is direct, returns false otherwise.
     */
    public boolean isDirectQuery() {
        return directQuery;
    }

    /**
     * Returns queries JMBAG but only of the query is direct
     * @return Returns JMBAG
     * @throws IllegalStateException Thrown when query is not direct.
     */
    public String getQueriedJMBAG(){
        if (!isDirectQuery()) {
            throw new IllegalStateException();
        }
        return JMBAG;
    }

    /**
     * Getter for query.
     * @return Returns query.
     */
    public List<ConditionalExpression> getQuery() {
        return query;
    }


}
