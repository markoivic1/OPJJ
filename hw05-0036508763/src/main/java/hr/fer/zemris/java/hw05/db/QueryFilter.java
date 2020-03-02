package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * QueryFilter is used to check {@link StudentRecord} for each of the query requests.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class QueryFilter implements IFilter{
    /**
     * List of conditional expressions.
     */
    private List<ConditionalExpression> query;

    /**
     * Constructor takes the list of conditional expressions and stores it.
     * @param query Query which is stored.
     */
    public QueryFilter(List<ConditionalExpression> query) {
        this.query = query;
    }

    /**
     * {@inheritDoc}
     * @param record Record which is being checked.
     * @return
     */
    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression condition : query) {
            if (!condition.getComparisonOperator().satisfied(condition.getFieldGetter().get(record), condition.getStringLiteral())) {
                return false;
            }
        }
        return true;
    }
}
