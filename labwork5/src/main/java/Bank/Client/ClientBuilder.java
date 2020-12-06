package Bank.Client;

import java.util.UUID;

public class ClientBuilder implements Builder {
    private String firstName = null;
    private String secondName = null;
    private String address = null;
    private Integer passport = null;


    private void check() throws Exception {
        if (firstName == null || secondName == null) {
            throw new Exception("BAN!");
        }
    }

    public Builder setName(String firstName, String secondName) {
        this.address = null;
        this.passport = null;

        this.firstName = firstName;
        this.secondName = secondName;
        return this;
    }

    public Builder setAddress(String address) throws Exception {
        check();
        this.address = address;
        return this;
    }

    public Builder setPassport(Integer passport) throws Exception {
        check();
        this.passport = passport;
        return this;
    }

    public Client create() throws Exception {
        check();
        if (address != null && passport != null) {
            return new Client(firstName,secondName,passport,address);
        } else if (address != null) {
            return new Client(firstName, secondName, address);
        } else if (passport != null) {
            return new Client(firstName, secondName, passport);
        } else
            return new Client(firstName, secondName);
    }

    public Builder updateClient(Client client) {
        this.address = null;
        this.passport = null;

        this.firstName = client.getFirstName();
        this.secondName = client.getSecondName();
        return this;
    }
}
