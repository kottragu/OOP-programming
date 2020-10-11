
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

    public void addCount(Product newProduct) throws Exception {
        if (this.getName().equals(newProduct.getName())) {
            this.changeCount(this.getCount() + newProduct.getCount());
            if (newProduct.getValue() != 0.0D) {
                this.setValue(newProduct.getValue());
            }
        } else
            throw new Exception("Incorrect name of product. Product with ID " + this.getId() + " already has name " + this.getName());

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

    public double purchase(Product purchasedProduct) throws Exception {
        double resultSum = 0;
        if (this.getName().equals(purchasedProduct.getName())) {
            if(this.getCount() < purchasedProduct.getCount()){
                throw new Exception("Нема продуктов в магазине");
            } else {
                resultSum += purchasedProduct.getCount() * this.getValue();
                this.changeCount(this.getCount() - purchasedProduct.getCount());
            }
        }
        return resultSum;
    }
}
