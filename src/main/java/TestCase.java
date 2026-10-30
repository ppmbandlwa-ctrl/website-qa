import java.util.function.BooleanSupplier;

/**
 * A test case: ID, priority, suite membership, steps (as a description),
 * expected result, and — once run — the actual result and pass/fail status.
 * Mirrors the test case fields covered in the course: ID, priority,
 * description, expected result, actual result, pass/fail.
 */
public class TestCase {

    enum Suite { SMOKE, BOUNDARY, REGRESSION }
    enum Priority { P0_BLOCKER, P1_CRITICAL, P2_MAJOR, P3_MINOR }

    final String id;
    final Suite suite;
    final Priority priority;
    final String description;
    final boolean expectedResult;
    private final BooleanSupplier action;

    Boolean actualResult;   // null until run
    boolean passed;

    TestCase(String id, Suite suite, Priority priority, String description,
              boolean expectedResult, BooleanSupplier action) {
        this.id = id;
        this.suite = suite;
        this.priority = priority;
        this.description = description;
        this.expectedResult = expectedResult;
        this.action = action;
    }

    void run() {
        actualResult = action.getAsBoolean();
        passed = actualResult.equals(expectedResult);
    }

    @Override
    public String toString() {
        String status = actualResult == null ? "NOT RUN" : (passed ? "PASS" : "FAIL");
        return String.format("[%s] %-10s %-7s %-55s expected=%-5s actual=%-5s -> %s",
                id, suite, priority, description, expectedResult, actualResult, status);
    }
}
