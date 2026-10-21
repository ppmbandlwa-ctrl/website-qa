import java.util.ArrayList;
import java.util.List;

/** A minimal bug-tracking system (stands in for Jira/Azure DevOps). */
public class BugTracker {

    private final List<BugReport> bugs = new ArrayList<>();
    private int nextId = 1;

    BugReport file(String title, String steps, String expected, String actual,
                    BugReport.Severity severity, BugReport.Priority priority) {
        BugReport bug = new BugReport("BUG-" + (nextId++), title, steps, expected, actual, severity, priority);
        bug.open();
        bugs.add(bug);
        return bug;
    }

    void printAll() {
        if (bugs.isEmpty()) {
            System.out.println("  (no bugs filed)");
            return;
        }
        for (BugReport b : bugs) b.print();
    }

    BugReport latest() {
        return bugs.isEmpty() ? null : bugs.get(bugs.size() - 1);
    }
}
