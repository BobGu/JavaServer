import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandlerTest {

    @Test
    public void TestCanSetARouteField() {
        Handler handler = new Handler();
        String route = "GET localhost:5000/";
        handler.setRoute(route);
        assertEquals(route, handler.getRoute());
    }

}
