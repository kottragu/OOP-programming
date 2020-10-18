import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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


    public void addProduct(Product product) throws Exception { // проверка на имя
        for (Product existProduct: products) {
            if (existProduct.getId().equals(product.getId())) {
                existProduct.addCount(product);
                return;
            }
        }
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
        } else {
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

    public boolean tryGetCheapestProduct(String productId) {
        for (Product p: products) {
            if (p.getId().equals(productId) && p.getValue() != 0.0D) {
                return true;
            }
        }
        return false;
    }

    public double cheapestProduct(String productId) {
        double value = 0;
        for (Product p: products) {
            if (p.getId().equals(productId)) {
                value = p.getValue();
            }
        }
        return value;
    }

    public void setDelivery(Transportation delivery) throws Exception {
        for (Product product: delivery.getProducts()) {
            this.addProduct(product);
        }
    }

    public boolean tryGetBatch (Map<String, Integer> batch) {
        for (String key: batch.keySet()) {
            if (products.stream().noneMatch(p -> p.getId().equals(key) && p.getCount() >= batch.get(key)))
                return false;
        }
        return true;
    }

    public double getBatch(Map<String, Integer> batch) {
        double shopPrice = 0;
        for (String key: batch.keySet()) {
            for (Product product: products) {
                shopPrice += batch.get(key) * product.getValue();
            }
        }
        return shopPrice;
    }

    public boolean tryGetPurchase(Transportation purchase) {
        for (Product product: purchase.getProducts()) {
            Stream<Product> productStream = products.stream();
            if (productStream.noneMatch(x -> x.getId().equals(product.getId()) &&
                                             x.getCount() >= product.getCount() &&
                                             x.getName().equals(product.getName())))
                return false;

        }
        return true;
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
