package Bank.Client;

import Bank.Bank;

import java.util.UUID;

public class Client {
    private final UUID id;
    private final String firstName;
    private final String secondName;
    private String address;
    private Integer passport;
    private boolean doubtful = true;

    public Client(String firstName, String secondName) {
        id = UUID.randomUUID();
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public void setAddress(String address) {
        this.address = address;
        doubtful = false;
    }

    public void setPassport(Integer passport) {
        this.passport = passport;
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
