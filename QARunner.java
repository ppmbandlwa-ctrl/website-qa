import java.util.ArrayList;
import java.util.List;

/**
 * Orchestrates the whole demo, following the QA process flow from the
 * course: Plan (design tests) -> Do (execute) -> Check (analyze results,
 * file bugs) -> Act (fix + regress). Along the way it demonstrates:
 *   - smoke testing as a gate before deeper testing
 *   - equivalence partitioning + boundary value analysis for test design
 *   - positive vs negative test cases
 *   - severity vs priority on bug reports
 *   - the bug lifecycle (open -> resolved -> verified -> closed)
 *   - regression testing after a fix
 */
public class QARunner {

    public static void main(String[] args) {
        System.out.println("================ QA PROCESS DEMO ================");
        System.out.println("SDLC stage: Requirement Analysis -> Test Planning -> Test Design -> Execute -> Report -> Regress -> Release");
        System.out.println("QA cycle:   PLAN (design cases) -> DO (run them) -> CHECK (analyze) -> ACT (fix & retest)\n");

        BugTracker tracker = new BugTracker();

        // ---------- PLAN: design the smoke suite (critical path only) ----------
        List<TestCase> smoke = new ArrayList<>();
        smoke.add(new TestCase("TC-SMOKE-1", TestCase.Suite.SMOKE, TestCase.Priority.P0_BLOCKER,
                "Register with fully valid data (critical path)", true,
                () -> UserRegistration.register("artm22", "Passw0rd!", 30)));

        // ---------- DO: run smoke first. If it fails, stop everything. ----------
        System.out.println("--- Running SMOKE suite ---");
        boolean smokeOk = runSuite(smoke);
        if (!smokeOk) {
            System.out.println("Smoke test failed - build is unstable. Halting further testing.");
            return;
        }
        System.out.println("Smoke suite passed. Build is stable enough for deeper testing.\n");

        // ---------- PLAN: boundary suite using equivalence partitioning + BVA ----------
        List<TestCase> boundary = new ArrayList<>();

        // Username: equivalence classes = too-short | valid | too-long
        boundary.add(tc("TC-B1", "Username at lower boundary - 1 (2 chars) -> reject", false,
                () -> UserRegistration.validateUsername("ab")));
        boundary.add(tc("TC-B2", "Username at lower boundary (3 chars) -> accept", true,
                () -> UserRegistration.validateUsername("abc")));
        boundary.add(tc("TC-B3", "Username at upper boundary (20 chars) -> accept", true,
                () -> UserRegistration.validateUsername("a".repeat(20))));
        boundary.add(tc("TC-B4", "Username at upper boundary + 1 (21 chars) -> reject", false,
                () -> UserRegistration.validateUsername("a".repeat(21))));

        // Password: equivalence classes = too-short | no-digit | valid | too-long
        boundary.add(tc("TC-B5", "Password below min length (7 chars) -> reject", false,
                () -> UserRegistration.validatePassword("Pass0w!")));
        boundary.add(tc("TC-B6", "Password with no digit (negative case) -> reject", false,
                () -> UserRegistration.validatePassword("Password")));
        boundary.add(tc("TC-B7", "Password at min length w/ digit (8 chars) -> accept", true,
                () -> UserRegistration.validatePassword("Passw0rd")));

        // Age: equivalence classes = underage | valid | overage. This is where
        // the seeded off-by-one bug lives — age 65 should be valid.
        boundary.add(tc("TC-B8", "Age below min (17) -> reject", false,
                () -> UserRegistration.validateAge(17)));
        boundary.add(tc("TC-B9", "Age at lower boundary (18) -> accept", true,
                () -> UserRegistration.validateAge(18)));
        boundary.add(tc("TC-B10", "Age at upper boundary (65) -> accept", true,
                () -> UserRegistration.validateAge(65)));
        boundary.add(tc("TC-B11", "Age above max (66) -> reject", false,
                () -> UserRegistration.validateAge(66)));

        System.out.println("--- Running BOUNDARY suite (equivalence partitioning + boundary value analysis) ---");
        runSuite(boundary);

        // ---------- CHECK: find failures, file bug reports (severity vs priority) ----------
        System.out.println("\n--- Filing bugs for failed test cases ---");
        for (TestCase t : boundary) {
            if (!t.passed) {
                BugReport bug = tracker.file(
                        "Registration rejects a valid age at the upper boundary",
                        "Call UserRegistration.validateAge(65)",
                        String.valueOf(t.expectedResult),
                        String.valueOf(t.actualResult),
                        // Severity: moderate — one valid user type is blocked, not a crash.
                        BugReport.Severity.MAJOR,
                        // Priority: high — it directly blocks real users from registering.
                        BugReport.Priority.HIGH);
                System.out.println("Filed " + bug.id + " for " + t.id
                        + " (severity=" + bug.severity + " because it's a functional defect, not a crash; "
                        + "priority=" + bug.priority + " because it blocks real users right now).");
            }
        }
        tracker.printAll();

        // ---------- ACT: simulate the fix being applied, then regress ----------
        System.out.println("\n--- Developer applies the fix (validateAgeFixed). QA reruns the failed case + a regression pass ---");
        TestCase retest = tc("TC-REG-1", "Regression: age at upper boundary (65) -> accept, using the fix", true,
                () -> UserRegistration.validateAgeFixed(65));
        retest.run();
        System.out.println(retest);

        boolean fixConfirmed = retest.passed;
        // Mark the matching bug resolved, then verify/close it based on the retest.
        BugReport fixedBug = tracker.latest();
        if (fixedBug != null) {
            fixedBug.markResolved();
            fixedBug.retest(fixConfirmed);
            System.out.println("\n" + fixedBug.id + " moved through: OPEN -> RESOLVED -> "
                    + (fixConfirmed ? "VERIFIED -> CLOSED" : "REOPENED"));
        }

        // Full regression: rerun smoke + boundary (with the fix) to prove nothing else broke.
        System.out.println("\n--- Full regression pass (smoke + boundary, using the fixed logic) ---");
        List<TestCase> regression = new ArrayList<>();
        regression.add(new TestCase("TC-REG-SMOKE", TestCase.Suite.REGRESSION, TestCase.Priority.P0_BLOCKER,
                "Smoke: register with fully valid data still works", true,
                () -> UserRegistration.register("artm22", "Passw0rd!", 30)));
        regression.add(new TestCase("TC-REG-B10", TestCase.Suite.REGRESSION, TestCase.Priority.P2_MAJOR,
                "Age at upper boundary (65) now accepts", true,
                () -> UserRegistration.validateAgeFixed(65)));
        regression.add(new TestCase("TC-REG-B11", TestCase.Suite.REGRESSION, TestCase.Priority.P2_MAJOR,
                "Age above max (66) still correctly rejected", false,
                () -> UserRegistration.validateAgeFixed(66)));
        boolean regressionOk = runSuite(regression);

        // ---------- Final report ----------
        int totalRun = smoke.size() + boundary.size() + 1 + regression.size();
        long totalPassed = countPassed(smoke) + countPassed(boundary) + (retest.passed ? 1 : 0) + countPassed(regression);
        System.out.println("\n================ FINAL REPORT ================");
        System.out.println("Total test cases run: " + totalRun);
        System.out.println("Passed: " + totalPassed + "   Failed: " + (totalRun - totalPassed));
        System.out.println("Build verdict: " + (regressionOk ? "STABLE - ready to release" : "UNSTABLE - do not release"));
        System.out.println("================================================");
    }

    private static TestCase tc(String id, String desc, boolean expected, java.util.function.BooleanSupplier action) {
        return new TestCase(id, TestCase.Suite.BOUNDARY, TestCase.Priority.P2_MAJOR, desc, expected, action);
    }

    private static boolean runSuite(List<TestCase> suite) {
        boolean allPassed = true;
        for (TestCase t : suite) {
            t.run();
            System.out.println(t);
            if (!t.passed) allPassed = false;
        }
        return allPassed;
    }

    private static long countPassed(List<TestCase> suite) {
        return suite.stream().filter(t -> t.passed).count();
    }
}
