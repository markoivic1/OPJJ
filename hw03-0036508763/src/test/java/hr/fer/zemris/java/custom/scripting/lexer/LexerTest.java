package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {


    @Test
    void testSomeFor() {
        Lexer lexer = new Lexer("{$ FOR i 1 10 1 $}");
        checkToken(lexer.getToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
    }

    @Test
    void forTest() {

        Lexer lexer = new Lexer("{$ FOR i-1.35bbb\"1\" $}");
        checkToken(lexer.getToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_DOUBLE, "-1.35"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "bbb"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "1"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);


        lexer = new Lexer("{$ FOR i -1.35 bbb \"1\" $}");
        checkToken(lexer.getToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_DOUBLE, "-1.35"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "bbb"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "1"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
    }

    @Test
    void echoTest() {
        Lexer lexer = new Lexer("{$= i i * @sin  \"0.000\" @decfmt $}");
        checkToken(lexer.getToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "*"));
        checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_NAME, "sin"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "0.000"));
        checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_NAME, "decfmt"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);


        lexer = new Lexer("{$=i i*@sin\"0.000\"@decfmt $}");
        checkToken(lexer.getToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "*"));
        checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_NAME, "sin"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "0.000"));
        checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_NAME, "decfmt"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
    }

    @Test
    void combinedInput() {
        Lexer lexer = new Lexer("This is sample text.\r\n{$FOR i 1 10 1 $}\r\n  This is {$= i $} {$ END   $}{$END$}");
        checkToken(lexer.getToken(), new Token(TokenType.TEXT, "This is sample text.\r\n"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INTEGER, "1"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INTEGER, "10"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INTEGER, "1"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n  This is "));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "END"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "END"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
    }

    @Test
    void escapedCharacters() {
        Lexer lexer = new Lexer("This \\{is sample \\\\ text.\r\n{$FOR i 1 10 1 $}\r\n  This is {$= i $} {$ END   $}{$END$}");
        checkToken(lexer.getToken(), new Token(TokenType.TEXT, "This {is sample \\ text.\r\n"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INTEGER, "1"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INTEGER, "10"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INTEGER, "1"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n  This is "));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "END"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "END"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
    }

    @Test
    void joinedTags() {
        Lexer lexer = new Lexer("{$FOR i \"10\" \"3\" \"2\"$}{$= i$}TextText{$  for ias 2 e $}");
        checkToken(lexer.getToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "FOR"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "10"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "3"));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "2"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "TextText"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "for"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "ias"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INTEGER, "2"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "e"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
    }

    @Test
    void escapedForChars() {
        Lexer lexer = new Lexer("\\{$FOR i \"10\" \"3\" \"2\"$}{$= i$}TextText{$  for ias 2 e $}");
        checkToken(lexer.getToken(), new Token(TokenType.TEXT, "{$FOR i \"10\" \"3\" \"2\"$}"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "i"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "TextText"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "for"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "ias"));
        checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INTEGER, "2"));
        checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_NAME, "e"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
    }

    @Test
    void invalidEscapeChars() {
        assertThrows(LexerException.class, () -> new Lexer("\\\""));
    }

    @Test
    void empty() {
        assertThrows(LexerException.class, () -> new Lexer("\\\""));
    }

    @Test
    void notClosedTag() {
        Lexer lexer = new Lexer("aa{$ for i 20 1");
        lexer.nextToken();
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        lexer.nextToken();
        lexer.nextToken();
        assertThrows(LexerException.class, () -> lexer.nextToken());
    }

    @Test
    void testEscapedCharsInString() {
        Lexer lexer = new Lexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");
        checkToken(lexer.getToken(), new Token(TokenType.TEXT, "A tag follows "));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENED, "{$"));
        lexer.setState(LexerState.TAG);
        checkToken(lexer.nextToken(), new Token(TokenType.TAG, "="));
        checkToken(lexer.nextToken(), new Token(TokenType.STRING, "Joe \"Long\" Smith"));
        checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSED, "$}"));
        lexer.setState(LexerState.TEXT);
        checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "."));
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
    }

    @Test
    void illegalDouble() {
        Lexer lexer = new Lexer("{$ for 0.000.000 $}");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertThrows(LexerException.class, () -> lexer.nextToken());
    }

    @Test
    void validTextInLexer() {
        Lexer lexer = new Lexer("{ $}");
        checkToken(lexer.getToken(), new Token(TokenType.TEXT, "{ $}"));
    }

    @Test
    void illegalEscapeInString() {
        Lexer lexer = new Lexer("{$= \"\\0\"$}");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertThrows(LexerException.class, () -> lexer.nextToken());
        Lexer lexer2 = new Lexer("{$= for \"\\b\"$}");
        lexer2.setState(LexerState.TAG);
        lexer2.nextToken();
        lexer2.nextToken();
        assertThrows(LexerException.class, () -> lexer2.nextToken());
    }

    @Test
    void emptyInput() {
        Lexer lexer = new Lexer("");
        checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
    }


    private void checkToken(Token actual, Token expected) {
        String msg = "Token are not equal.";
        assertEquals(expected.getType(), actual.getType(), msg);
        assertEquals(expected.getValue(), actual.getValue(), msg);
    }

}