package logs;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private ArrayList<String> visits = new ArrayList<String>();
    private static Log log = new Log();

    private Log() {}

    public static Log getInstance() {
        return log;
    }

    public List<String> recentVisits(int numberOfVisits) {
        int visitsLastIndex = visits.size();
        int startingIndex = visitsLastIndex - numberOfVisits >= 0 ? visitsLastIndex - numberOfVisits : 0;
        return visits.subList(startingIndex, visitsLastIndex);
    }

    public void addVisit(String visit) {
        visits.add(visit);
    }

}
