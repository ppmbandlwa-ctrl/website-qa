import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserRegistrationTest {

    @Test
    void validateUsername_acceptsValidValuesAndRejectsInvalidOnes() {
        assertTrue(UserRegistration.validateUsername("abc"));
        assertTrue(UserRegistration.validateUsername("a".repeat(20)));

        assertFalse(UserRegistration.validateUsername(null));
        assertFalse(UserRegistration.validateUsername("ab"));
        assertFalse(UserRegistration.validateUsername("a".repeat(21)));
        assertFalse(UserRegistration.validateUsername("user!"));
        assertFalse(UserRegistration.validateUsername("us er"));
    }

    @Test
    void validatePassword_acceptsValidValuesAndRejectsInvalidOnes() {
        assertTrue(UserRegistration.validatePassword("Passw0rd"));
        assertTrue(UserRegistration.validatePassword("Abcdef1!"));

        assertFalse(UserRegistration.validatePassword(null));
        assertFalse(UserRegistration.validatePassword("Pass0w!"));
        assertFalse(UserRegistration.validatePassword("Password"));
        assertFalse(UserRegistration.validatePassword("A1b2C3d4E5f6G7h8i9"));
    }

    @Test
    void validateAge_acceptsRequiredAgeRangeAndRejectsOutOfRangeValues() {
        assertFalse(UserRegistration.validateAge(17));
        assertTrue(UserRegistration.validateAge(18));
        assertTrue(UserRegistration.validateAge(65));
        assertFalse(UserRegistration.validateAge(66));
    }

    @Test
    void register_requiresAllFieldsToBeValid() {
        assertTrue(UserRegistration.register("artm22", "Passw0rd!", 30));
        assertTrue(UserRegistration.register("abc", "A1bcdefg", 18));

        assertFalse(UserRegistration.register("ab", "Passw0rd!", 30));
        assertFalse(UserRegistration.register("artm22", "Password", 30));
        assertFalse(UserRegistration.register("artm22", "Passw0rd!", 17));
    }
}
