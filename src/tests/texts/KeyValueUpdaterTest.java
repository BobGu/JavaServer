import org.junit.Before;
import org.junit.Test;
import texts.KeyValueUpdater;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class KeyValueUpdaterTest {
    private KeyValueUpdater textUpdate;

    @Before
    public void setup() {
        textUpdate = new KeyValueUpdater();
    }

    @Test
    public void TestItAddsAKeyAndValueWhenNoExisitingText() {
        assertThat(textUpdate.update("", "name=hello"), containsString("name=hello"));
    }

    @Test
    public void TestItUpdatesAValueIfKeyIsTheSame() {
        String updatedText = textUpdate.update("name=bob", "name=john");

        assertThat(updatedText, containsString("name=john"));
        assertFalse(updatedText.contains("name=bob"));
    }

    @Test
    public void TestItAddsAKeyAndValueWithExisitingText() {
        String updatedText = textUpdate.update("name=bob", "address=theUSA");

        assertThat(updatedText, containsString("name=bob"));
        assertThat(updatedText, containsString("address=theUSA"));
    }

}
