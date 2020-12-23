package Bank.Client;

public interface Builder {
    public Builder updateClient(Client client);
    public Builder setName(String firstName, String secondName);
    public Builder setAddress(String address) throws Exception;
    public Builder setPassport(Integer passport) throws Exception;
    public Client create() throws Exception;
}
