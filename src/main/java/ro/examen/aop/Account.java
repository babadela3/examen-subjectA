package ro.examen.aop;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Account {

    //default existing persons in bank system
    private Map<Person, Integer> balanceAvailable = new HashMap<Person, Integer>() {{
        put(new Person("Bula", "Gluba"), 34000);
        put(new Person("Floro", "Martinez"), 56932);
    }};

    public void withdraw(int amount, Person person) {
        // check if the person is in bank system
        if (balanceAvailable.containsKey(person)) {
            // extract the amount of money
            int difference = balanceAvailable.get(person) - amount;
            // if the extract the amount of money is too big, raise RunTimeException
            if (difference < 0) {
                throw new RuntimeException("The amount is too much");
            } else {
                // update the remaining money
                balanceAvailable.put(person, difference);
            }
        } else {
            throw new RuntimeException("Could not found the amount for person: " + person);
        }
    }

    public int getAvailableAmount(Person person) {
        if (balanceAvailable.containsKey(person)) {
            return balanceAvailable.get(person);
        } else {
            throw new RuntimeException("Could not found the amount of person");
        }
    }
}