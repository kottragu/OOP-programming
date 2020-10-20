import java.util.ArrayList;

public class Transportation {
    private ArrayList<Product> products;

    Transportation(){
        products = new ArrayList<>();
    }

    public void add(Product product){
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
