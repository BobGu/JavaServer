import decoders.ParameterDecoder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ParameterDecoderTest {
    private ParameterDecoder decoder;

    @Before
    public void setup() {
        decoder = new ParameterDecoder();
    }

    @Test
    public void TestItCanDecodeASingleCharacter(){
        String encodedCharacter = "%20";
        assertEquals(" ", decoder.decode(encodedCharacter));
    }

    @Test
    public void TestItCanDecodeAllTheReservedCharacters() {
        Map<String,String> encodedCharacters = createReservedEncodedCharacters();

        for (Map.Entry<String, String> entry : encodedCharacters.entrySet()) {
            assertEquals(entry.getValue(), decoder.decode(entry.getKey()));
        }
    }

    @Test
    public void TestItCanDecodeCommonCharacters() {
        Map<String, String> encodedCharacters = createCommonEncodedCharacters();

        for (Map.Entry<String, String> entry : encodedCharacters.entrySet()) {
            assertEquals(entry.getValue(), decoder.decode(entry.getKey()));
        }
    }

    @Test
    public void TestItCanDecodeManyCharacters() {
        String encodedParameter = "%21%20%3D";
        assertEquals("! =", decoder.decode(encodedParameter));
    }

    @Test
    public void TestCharactersThatAreNotEncodedGetReturned() {
        String encodedParameter = "%22is%20that%20all%22name=bob";
        assertEquals("\"is that all\"name=bob", decoder.decode(encodedParameter));
    }

    @Test
    public void ReturnsTheOrignalStringIfNoCharactersAreEncoded() {
        String parameter = "name=bob";
        assertEquals("name=bob", decoder.decode(parameter));
    }

    private Map<String, String> createReservedEncodedCharacters() {
        Map<String, String> encodedCharacters = new HashMap<String,String>();
        encodedCharacters.put("%21", "!");
        encodedCharacters.put("%23", "#");
        encodedCharacters.put("%24", "$");
        encodedCharacters.put("%26", "&");
        encodedCharacters.put("%27", "'");
        encodedCharacters.put("%28", "(");
        encodedCharacters.put("%29", ")");
        encodedCharacters.put("%2A", "*");
        encodedCharacters.put("%2B", "+");
        encodedCharacters.put("%2C", ",");
        encodedCharacters.put("%2F", "/");
        encodedCharacters.put("%3A", ":");
        encodedCharacters.put("%3B", ";");
        encodedCharacters.put("%3D", "=");
        encodedCharacters.put("%3F", "?");
        encodedCharacters.put("%40", "@");
        encodedCharacters.put("%5B", "[");
        encodedCharacters.put("%5D", "]");

        return encodedCharacters;
    }

    private Map<String, String> createCommonEncodedCharacters() {
        Map<String,String> encodedCharacters = new HashMap<String, String>();
        encodedCharacters.put("%20", " ");
        encodedCharacters.put("%22", "\"" );
        encodedCharacters.put("%25", "%");
        encodedCharacters.put("%2C", ",");
        encodedCharacters.put("%2D", "-");
        encodedCharacters.put("%2E", ".");
        encodedCharacters.put("%3C", "<");
        encodedCharacters.put("%3E", ">");
        encodedCharacters.put("%5C", "\\");
        encodedCharacters.put("%5E", "^");
        encodedCharacters.put("%5F", "_");
        encodedCharacters.put("%60", "`");
        encodedCharacters.put("%7B", "{");
        encodedCharacters.put("%7C", "|");
        encodedCharacters.put("%7D", "}");
        encodedCharacters.put("%7E", "~");

        return encodedCharacters;
    }

}
