import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shop {
    private final String id;
    private final String name;
    private final String address;
    private ArrayList<Product> products;

    Shop(String shopName, String shopAddress, String shopId) {
        products = new ArrayList<>();
        name = shopName;
        address = shopAddress;
        id = shopId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }


    public void addProduct(Product product) throws Exception {
        boolean isExistProduct = false;
        for (Product existProduct: products) {
            if (existProduct.getId().equals(product.getId())) {
                isExistProduct = true;
                existProduct.addCount(product);
            }
        }
        if (!isExistProduct)
            products.add(product);
    }

    public void addProduct(ArrayList<Product> arrayListProducts) throws Exception {
        for (Product product: arrayListProducts) {
            this.addProduct(product);
        }
    }

    private int countForTryBuy(Product product, double money) {
        if (product.getCount() > (int) money/product.getValue()) {
            return (int)(money/product.getValue());
        } else{
            return product.getCount();
        }
    }

    public Map<String, Integer> tryBuy(double money) {

        Map<String, Integer> countProduct = new HashMap<>();
        for (Product product: products) {
            countProduct.put(product.getId(), countForTryBuy(product, money));
        }
        return countProduct;
    }

    public double cheapestProduct(String productId) {
        double min = Double.MAX_VALUE;
        for(Product p: products) {
            if (p.getId().equals(productId) && p.getValue() < min) {
                min = p.getValue();
            }
        }
        return min;
    }

    public void setDelivery(Transportation delivery) throws Exception {
        for(Product product: delivery.getProducts()){
            this.addProduct(product);
        }
    }

    public double getBatch(Map<String, Integer> batch) {
        int countProductsInShop = 0;
        double shopPrice = 0;
        for(String key: batch.keySet()) {
            for (Product product: products) {
                if (product.getId().equals(key) && product.getCount() < batch.get(key)) {
                    return Double.MAX_VALUE;
                } else if (product.getId().equals(key)) {
                    countProductsInShop++;
                    shopPrice += batch.get(key) * product.getValue();
                }
            }
        }
        if (countProductsInShop == batch.size()){
            return shopPrice;
        } else {
            return Double.MAX_VALUE;
        }
    }

    public double setPurchase(Transportation purchase) throws Exception {
        double resultSum = 0;
        for(Product product: purchase.getProducts()) {
            for(Product productInShop: products) {
                if(product.getId().equals(productInShop.getId())) {
                    resultSum += productInShop.purchase(product);
                }
            }
        }
        return resultSum;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }
}
