package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.VariableElement;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser.createOriginalDocumentBody;
import static org.junit.jupiter.api.Assertions.*;

class SmartScriptParserTest {

    @Test
    void readInputFromAFile() {
        String docBody = loader("document1.txt");
        assertTrue(stringToDocumentToStringToDocument(docBody));
    }

    @Test
    void nonExistentTag() {
        String docBody = loader("document2.txt");
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    void parseEchoWithAllTypesOfElements() {
        SmartScriptParser parser = new SmartScriptParser("{$= sa \"qq\" 1.42 5 * @sin a$}");
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        assertEquals(document, document2);
        Element[] elements = ((EchoNode)document.getChild(0)).getElements();
        assertTrue(elements[0] instanceof ElementVariable);
        assertTrue(elements[1] instanceof ElementString);
        assertTrue(elements[2] instanceof ElementConstantDouble);
        assertTrue(elements[3] instanceof ElementConstantInteger);
        assertTrue(elements[4] instanceof ElementOperator);
        assertTrue(elements[5] instanceof ElementFunction);
        assertTrue(elements[6] instanceof ElementVariable);
    }

    @Test
    void everyValidEscapeInTagString() {
        SmartScriptParser parser = new SmartScriptParser("{$= sa \"Joe \\\"Long\\\" \\r\\n\\t \\\\\\n  Smith\" 1.42 5 * @sin a$}");
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        assertEquals(document, document2);
    }

    @Test
    void multipleNestedTags() {
        String docBody = loader("document3.txt");
        assertTrue(stringToDocumentToStringToDocument(docBody));
    }

    @Test
    void joinedTags() {
        assertTrue(stringToDocumentToStringToDocument("{$=1$}{$=1$}{$for a b c$}{$=1$}{$end$}"));
    }

    @Test
    void nullSentAsDocumentBody() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(null));

    }

    @Test
    void nonExistentValidTagsThatAreSimilarToExistingTags() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ fore 1 2 3 $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ end_1 2 3 d $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ f=or 2 3 4$}"));
    }

    @Test
    void invalidTags() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ _a1 $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ 1 $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ * 2 3 4$}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ \"for\" 1 2 3 $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ :a 2 3 d $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ 2a_ 3 4$}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ @fore 1 2 3 $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ $end_1 2 3 d $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ {2 3 4$}"));
    }

    @Test
    void invalidNumberOfArgumentsInFor() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR 3 1 10 1 $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR * \"1\" -10 \"1\" $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year @sin 10 $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year 1 10 \"1\" \"10\" $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year 1 10 1 3 $}"));
    }

    @Test
    void testEscaping() {
        assertThrows(SmartScriptParserException.class, () -> stringToDocumentToStringToDocument("Example \\{$=1$}. \\\\ Now actually write one {$=1$}\\{"));
        assertThrows(SmartScriptParserException.class, () -> stringToDocumentToStringToDocument("Example \\{$=1$}. \\\\ Now actually write one \\{$=1$}\\"));
        assertThrows(SmartScriptParserException.class, () -> stringToDocumentToStringToDocument("Example \\{$=1$}. \\\\{ Now actually write one \\{$=1$}\\"));
    }

    @Test
    void illegalEscapingOfCharacters() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ for \\i hello $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ for \\\" bam $}"));
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ for \\\\{$ for a b c}"));
    }

    @Test
    void escapedTagWithThreeElements() {
        assertTrue(stringToDocumentToStringToDocument("Example \\{$ for 1 2 3$}"));
    }

    @Test
    void forTagWithThreeElements() {
        assertTrue(stringToDocumentToStringToDocument("Example {$ for a 2 3$} blabla {$end$}")); // 3 elements
    }

    @Test
    void forTagWithFourElements() {
        assertTrue(stringToDocumentToStringToDocument("Example {$ for b 2 3 4$} 123 {$end$}")); // 4 elements
    }

    @Test
    void elementsInForAreJoined() {
        SmartScriptParser parser1 = new SmartScriptParser("{$ FOR i-1.35bbb\"1\" $}{$end$}");
        DocumentNode document1 = parser1.getDocumentNode();
        String document1Body = createOriginalDocumentBody(document1);
        SmartScriptParser parser2 = new SmartScriptParser("{$ FOR i -1.35 bbb \"1\" $}{$end$}");
        DocumentNode document2 = parser2.getDocumentNode();
        String document2Body = createOriginalDocumentBody(document2);
        assertTrue(document1.equals(document2));
    }

    @Test
    void reparsingEscapedTag() {
        SmartScriptParser parser1 = new SmartScriptParser("\\{$ FOR i-1.35bbb\"1\" $}\\{$end$}");
        DocumentNode document1 = parser1.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document1);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        SmartScriptParser parser3 = new SmartScriptParser("{$ FOR i -1.35 bbb \"1\" $}{$end$}");
        DocumentNode document3 = parser3.getDocumentNode();
        assertTrue(document1.equals(document2));
        assertFalse(document2.equals(document3));
    }

    @Test
    void emptyEchoTag() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$=$}"));
    }

    @Test
    void testingEqualsWithNotEqualDocuments() {
        SmartScriptParser parser1 = new SmartScriptParser("{$ FOR i-1.35bbb\"1\" $}{$end$}");
        DocumentNode document1 = parser1.getDocumentNode();
        SmartScriptParser parser2 = new SmartScriptParser("{$ FOR i -1.35 bbb \"1\" $}Text{$end$}");
        DocumentNode document2 = parser2.getDocumentNode();
        assertFalse(document1.equals(document2));

        SmartScriptParser parser3 = new SmartScriptParser("{$ FOR i-1.35cbb\"1\" $}{$end$}");
        DocumentNode document3 = parser1.getDocumentNode();
        SmartScriptParser parser4 = new SmartScriptParser("{$ FOR i -1.35 bbb \"1\" $}Text{$end$}");
        DocumentNode document4 = parser2.getDocumentNode();
        assertFalse(document3.equals(document4));
    }

    @Test
    void singleTextTag() {
        SmartScriptParser parser1 = new SmartScriptParser("Example { bla } blu \\{$=1$}. Nothing interesting {=here}.");
        DocumentNode document1 = parser1.getDocumentNode();
        String originalDocument = createOriginalDocumentBody(document1);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocument);
        DocumentNode document2 = parser2.getDocumentNode();
        assertEquals(document1, document2);
    }

    private boolean stringToDocumentToStringToDocument(String input) {
        SmartScriptParser parser = new SmartScriptParser(input); // 4 elements
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        return document.equals(document2);
    }

    private String loader(String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                if (read < 1) break;
                bos.write(buffer, 0, read);
            }
            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return null;
        }
    }

}
