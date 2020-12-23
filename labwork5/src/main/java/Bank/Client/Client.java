package Bank.Client;

import Bank.Bank;

import java.util.UUID;

public class Client {
    private final UUID id;
    private final String firstName;
    private final String secondName;
    private String address = null;
    private Integer passport = null;
    private boolean doubtful = true;

    public Client(String firstName, String secondName) {
        id = UUID.randomUUID();
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Client(String firstName, String secondName, String address) {
        this(firstName, secondName);
        this.address = address;
        doubtful = false;
    }

    public Client(String firstName, String secondName, Integer passport) {
        this(firstName, secondName);
        this.passport = passport;
        doubtful = false;
    }

    public Client(String firstName, String secondName, Integer passport, String address) {
        this(firstName, secondName);
        this.passport = passport;
        this.address = address;
        doubtful = false;
    }

    public boolean isDoubtful() {
        return doubtful;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPassport() {
        return passport;
    }
}
