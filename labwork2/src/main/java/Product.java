
public class Product {
    private final String name;
    private int count;
    private double value;
    private final String id;

    Product(String productId, String productName, int countOfProduct, double price) throws Exception {
        id = productId;
        name = productName;
        if (countOfProduct < 0) {
            throw new Exception("Incorrect count of " + name + "with id: " + id);
        }
        count = countOfProduct;
        if (price <= 0.0D) {
            throw new Exception("Communism doesn't work");
        }
        this.value = price;
    }

    Product(String productId, String productName, int countOfProduct) throws Exception {
        id = productId;
        name = productName;
        if (countOfProduct < 0) {
            throw new Exception("Incorrect count of " + name + "with id: " + id);
        }
        count = countOfProduct;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    public int getCount() {
        return count;
    }

    public void changeCount(int number) {
        count = number;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
