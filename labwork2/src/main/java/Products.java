import java.util.ArrayList;

public class Products {
    private ArrayList<Product> products;

    Products(){
        products = new ArrayList<>();
    }

    public void add(Product product){
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
