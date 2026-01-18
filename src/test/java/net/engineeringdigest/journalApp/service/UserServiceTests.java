package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
public class UserServiceTests
{
    @Autowired
    public UserRepository userRepository;


    @ParameterizedTest
    @ValueSource(strings = {
            "Suryansh",
            "Aadi",
            "Diva",
            "Ryan"
    })
    public void testFindByUsername(String name)
    {
        User user=userRepository.findByUserName(name);
        assertTrue(!user.getJournalEntries().isEmpty(), "failed for - "+name);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource(
            {
                    "1, 1, 2",
                    "2, 2, 4",
                    "3, 2, 9"
            }
    )
    public void test(int a, int b, int expected)
    {
        assertEquals(expected, a+b);
    }
}
