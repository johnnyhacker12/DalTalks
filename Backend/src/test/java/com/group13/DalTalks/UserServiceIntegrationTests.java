package com.group13.DalTalks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.group13.DalTalks.model.User;
import com.group13.DalTalks.repository.UserRepository;
import com.group13.DalTalks.service.Implementations.UserServiceImpl; // Import concrete implementation
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
public class UserServiceIntegrationTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService; // Use concrete implementation

    private User validUser;

    @BeforeEach
    public void setUp() {
        validUser = new User();
        validUser.setEmail("valid@example.com");
        validUser.setPassword("validPassword");
        validUser.setSecurityAnswer("correctAnswer");
    }

    @Test
    public void testSuccessfulLogin() {
        when(userRepository.findByEmail("valid@example.com")).thenReturn(Optional.of(validUser));
        User user = userService.login("valid@example.com", "validPassword");
        assertEquals(validUser, user);
    }

    @Test
    public void testLoginWithInvalidEmail() {
        when(userRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("invalid@example.com", "anyPassword");
        });
        assertEquals("Invalid email address!", thrown.getMessage());
    }

    @Test
    public void testLoginWithInvalidPassword() {
        when(userRepository.findByEmail("valid@example.com")).thenReturn(Optional.of(validUser));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("valid@example.com", "invalidPassword");
        });
        assertEquals("Invalid password!", thrown.getMessage());
    }

    @Test
    public void testLoginWithNullEmail() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.login(null, "anyPassword");
        });
        assertEquals("Invalid email address!", thrown.getMessage());
    }

    @Test
    public void testLoginWithNullPassword() {
        when(userRepository.findByEmail("valid@example.com")).thenReturn(Optional.of(validUser));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("valid@example.com", null);
        });
        assertEquals("Invalid password!", thrown.getMessage());
    }

    @Test
    public void testLoginWithEmptyEmail() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("", "anyPassword");
        });
        assertEquals("Invalid email address!", thrown.getMessage());
    }

    @Test
    public void testLoginWithEmptyPassword() {
        when(userRepository.findByEmail("valid@example.com")).thenReturn(Optional.of(validUser));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("valid@example.com", "");
        });
        assertEquals("Invalid password!", thrown.getMessage());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("sample1@dal.ca");
        user.setPassword("Password!123");
        user.setSecurityAnswer("What is your mother's maiden name?");
        user.setSecurityAnswer("ABC");

        assertEquals("User created successfully",userService.createUser(user));
    }

    @Test
    public void testCreateUserInvalidEmail() {
        User user = new User();
        user.setEmail("sample1@gmail.com");
        user.setPassword("Password!123");
        user.setSecurityAnswer("What is your mother's maiden name?");
        user.setSecurityAnswer("ABC");

        String expectedMessage = "The email address used is invalid. This application is only targeted for employees" +
                " and students who are currently enrolled in Dalhousie.";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });

        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testCreateUserInvalidPassword() {
        User user = new User();
        user.setEmail("sample1@dal.ca");
        user.setPassword("Password");
        user.setSecurityAnswer("What is your mother's maiden name?");
        user.setSecurityAnswer("ABC");

        String expectedMessage = "Password must be at least 8 characters long, contain at least one uppercase " +
                "letter, one lowercase letter, one number, and one special character.";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });

        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testForgotPassword() {
        String email = "valid@example.com";
        String securityAnswer = "correctAnswer";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(validUser));

        User result = userService.forgotPassword(email, securityAnswer);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testForgotPassword_IncorrectSecurityAnswer() {
        String email = "valid@example.com";
        String securityAnswer = "incorrectAnswer";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(validUser));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.forgotPassword(email, securityAnswer);
        });

        assertEquals("Security answer is incorrect!", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testResetPassword() {
        String email = "valid@example.com";
        String newPassword = "NewValidPassword!123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(validUser));

        userService.resetPassword(email, newPassword);

        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(validUser);
        assertEquals(newPassword, validUser.getPassword());
    }

    @Test
    public void testResetPassword_InvalidEmail() {
        String email = "invalid@example.com";
        String newPassword = "NewValidPassword!123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.resetPassword(email, newPassword);
        });

        assertEquals("Invalid email address!", thrown.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(0)).save(any(User.class));
    }

}
