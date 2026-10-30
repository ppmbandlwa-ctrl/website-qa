/**
 * The "system under test." A simple registration validator.
 * Equivalence classes per field:
 *   username: too-short | valid | too-long
 *   password: too-short | valid-no-digit | valid | too-long
 *   age:      underage | valid | (buggy upper boundary) | overage
 *
 * NOTE: validateAge() below has a deliberate off-by-one bug seeded in it
 * on purpose, so the boundary-value tests in QARunner can catch it.
 */
public class UserRegistration {

    public static boolean validateUsername(String username) {
        if (username == null) return false;
        int len = username.length();
        return len >= 3 && len <= 20 && username.chars().allMatch(Character::isLetterOrDigit);
    }

    public static boolean validatePassword(String password) {
        if (password == null) return false;
        int len = password.length();
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        return len >= 8 && len <= 16 && hasDigit;
    }

    // Requirement: accept ages 18 through 65 inclusive.
    public static boolean validateAge(int age) {
        // BUG: should be age <= 65, but someone wrote age < 65 instead.
        // This rejects a perfectly valid 65-year-old user.
        return age >= 18 && age < 65;
    }

    // The fixed version — used later to demonstrate a retest after a bug fix.
    public static boolean validateAgeFixed(int age) {
        return age >= 18 && age <= 65;
    }

    public static boolean register(String username, String password, int age) {
        return validateUsername(username) && validatePassword(password) && validateAge(age);
    }
}
