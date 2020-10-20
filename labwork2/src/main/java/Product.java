import java.util.UUID;

public class Product {
    private final String name;
    private int count;
    private double value;
    private final UUID id;

    Product(UUID newid, String productName, int countOfProduct, double price) throws Exception {
        id = newid;
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

    Product(String productName, int countOfProduct, double price) throws Exception {
        id = UUID.randomUUID();
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

    Product(UUID newid, String productName, int countOfProduct) throws Exception {
        id = newid;
        name = productName;
        if (countOfProduct < 0) {
            throw new Exception("Incorrect count of " + name + "with id: " + id);
        }
        count = countOfProduct;
    }

    Product(String productName, int countOfProduct) throws Exception {
        id = UUID.randomUUID();
        name = productName;
        if (countOfProduct < 0) {
            throw new Exception("Incorrect count of " + name + "with id: " + id);
        }
        count = countOfProduct;
    }

    public void addCount(Product newProduct) throws Exception {
        changeCount(getCount() + newProduct.getCount());
        if (newProduct.getValue() != 0.0D) {
            setValue(newProduct.getValue());
        }
    }


    public boolean equals(Product product) {
        return id.equals(product.id);
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
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

    public double purchase(Product purchasedProduct) {
        double resultSum = purchasedProduct.getCount() * getValue();
        changeCount(getCount() - purchasedProduct.getCount());
        return resultSum;
    }
}
