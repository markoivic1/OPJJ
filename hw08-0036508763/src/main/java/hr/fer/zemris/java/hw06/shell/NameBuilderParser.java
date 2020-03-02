package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.lexer.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Parser is used to interpret tokens extracted with lexer.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class NameBuilderParser {
    /**
     * Name builder is used to store all of the defined strategies.
     */
    private NameBuilder nameBuilder;
    /**
     * Lexer which will extract tokens.
     */
    private NameBuilderLexer lexer;

    /**
     * Constructor which initializes lexer and parses it.
     * @param expression Expression which will be passed to lexer.
     */
    public NameBuilderParser(String expression) {
        lexer = new NameBuilderLexer(expression);
        parse();
    }

    /**
     * This method will interpret tokens.
     */
    public void parse() {
        List<NameBuilder> listOfNameBuilders = new ArrayList<>();
        while (lexer.getToken().getType() != TokenType.EOF) {
            if (lexer.getToken().getType() == TokenType.TEXT) {
                listOfNameBuilders.add(text((String) lexer.getToken().getValue()));
            } else {
                String[] data = ((String) lexer.getToken().getValue()).split(",");
                data[0] = data[0].trim();
                if (Integer.parseInt(data[0]) < 0) {
                    throw new IllegalArgumentException("Illegal group number.");
                }
                if (data.length == 1) {
                    listOfNameBuilders.add(group(Integer.parseInt(data[0].trim())));
                } else if (data.length == 2) {
                    data[1] = data[1].trim();
                    if (data[1].split(" ").length != 1) {
                        throw new IllegalArgumentException("Invalid group");
                    }
                    char padding = ' ';
                    int offset = 0;
                    if ((data[1].length() != 1) && (data[1].charAt(0) == '0')) {
                        padding = '0';
                        offset = 1;
                    }
                    int minWidth = data[1].length() == 1 ? Integer.parseInt(data[1]) : Integer.parseInt(String.valueOf(data[1].toCharArray(), offset, data[1].toCharArray().length - offset));
                    listOfNameBuilders.add(group(Integer.parseInt(data[0]), padding, minWidth));
                }
            }
            lexer.nextToken();
        }
        nameBuilder = executeAll(listOfNameBuilders);
    }

    /**
     * Getter form nameBuilder
     * @return Returns nameBuilder.
     */
    public NameBuilder getNameBuilder() {
        return nameBuilder;
    }

    /**
     * Lambda which appends text to a given StringBuilder.
     * @param t Text which will be appended.
     * @return Returns executable NameBuilder.
     */
    private static NameBuilder text(String t) {
        return (e, s) -> s.append(t);
    }

    /**
     * Lambda which appends group from a given index.
     * @param index Index which indicates the group.
     * @return Returns executable NameBuilder.
     */
    private static NameBuilder group(int index) {
        return (e, s) -> s.append(e.group(index));
    }

    /**
     * Lambda which sets minimum width of a group and adds appropriate padding if needed.
     * @param index Index of a group.
     * @param padding Padding which will be used.
     * @param minWidth Minimum width of a group.
     * @return Returns executable NameBuilder.
     */
    private static NameBuilder group(int index, char padding, int minWidth) {
        return (e, s) -> {
            String group = e.group(index);
            int repeatPadding = (group.length() < minWidth) ? minWidth - group.length() : 0;
            s.append(String.valueOf(padding).repeat(repeatPadding));
            s.append(group);
        };
    }

    /**
     * Lambda which executes every other lambda at once.
     * @param list List of previously defined lambdas.
     * @return Returns NameBuilder which had executed every given lambda.
     */
    private static NameBuilder executeAll(List<NameBuilder> list) {
        return (e, s) -> list.forEach(nb -> nb.execute(e, s));
    }
}
