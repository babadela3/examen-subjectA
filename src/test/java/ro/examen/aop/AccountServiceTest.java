package ro.examen.aop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    Account account;

    @Test
    public void testAccout() {
        // perform some withdraws for Floro Martinez
        Person person1 = new Person("Floro", "Martinez");
        account.withdraw(50, person1);
        account.withdraw(101, person1);

        // perform some withdraws for Bula Gluba
        Person person2 = new Person("Bula", "Gluba");
        account.withdraw(150, person2);
        account.withdraw(567, person2);
        account.withdraw(67, person2);
        account.withdraw(823, person2);

        // check how many times Bula Gluba withdraws over limit
        assertEquals(3, AccountAspect.OVER_LIMIT.get(person2).longValue());

        // check how many times Floro Martinez withdraws over limit
        assertEquals(1, AccountAspect.OVER_LIMIT.get(person1).longValue());
    }
}
