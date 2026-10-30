/**
 * A bug report. Severity (how broken) and priority (how urgent) are tracked
 * separately on purpose, since they don't always agree. Also models the
 * bug lifecycle: NEW -> OPEN -> RESOLVED -> VERIFIED -> CLOSED
 * (with REOPENED looping back to OPEN if a retest still fails).
 */
public class BugReport {

    enum Severity { TRIVIAL, MINOR, MAJOR, CRITICAL, BLOCKER }
    enum Priority { LOW, MEDIUM, HIGH }
    enum Status { NEW, OPEN, RESOLVED, VERIFIED, CLOSED, REOPENED }

    final String id;
    final String title;
    final String stepsToReproduce;
    final String expectedResult;
    final String actualResult;
    final Severity severity;
    final Priority priority;
    Status status;

    BugReport(String id, String title, String stepsToReproduce,
              String expectedResult, String actualResult,
              Severity severity, Priority priority) {
        this.id = id;
        this.title = title;
        this.stepsToReproduce = stepsToReproduce;
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        this.severity = severity;
        this.priority = priority;
        this.status = Status.NEW;
    }

    void open() { status = Status.OPEN; }

    void markResolved() { status = Status.RESOLVED; }

    // QA retests the fix. Pass -> VERIFIED then CLOSED. Fail -> REOPENED.
    void retest(boolean fixConfirmed) {
        status = fixConfirmed ? Status.VERIFIED : Status.REOPENED;
        if (status == Status.VERIFIED) status = Status.CLOSED;
    }

    void print() {
        System.out.println("  Bug " + id + ": " + title);
        System.out.println("    Severity: " + severity + "   Priority: " + priority + "   Status: " + status);
        System.out.println("    Steps to reproduce: " + stepsToReproduce);
        System.out.println("    Expected: " + expectedResult + "   |   Actual: " + actualResult);
    }
}
