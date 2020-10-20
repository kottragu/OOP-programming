import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;


public class Shop {
    private final UUID id;
    private final String name;
    private final String address;
    private ArrayList<Product> products;

    Shop(String shopName, String shopAddress, UUID shopId) {
        products = new ArrayList<>();
        name = shopName;
        address = shopAddress;
        id = shopId;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }


    public void addProduct(Product product) throws Exception {
        if (products.stream().anyMatch(x-> x.equals(product) && !x.getName().equals(product.getName()))) {
            throw new Exception("Incorrect name of product. Product with ID " + product.getId() + " already exist with different name");
        }
        for (Product existProduct: products) {
            if (existProduct.equals(product)) {
                existProduct.addCount(product);
                return;
            }
        }
        products.add(product);
    }

    public void addProduct(ArrayList<Product> arrayListProducts) throws Exception {
        for (Product product: arrayListProducts) {
            addProduct(product);
        }
    }

    private int countForTryBuy(Product product, double money) {
        return Math.min(product.getCount(), (int)(money/product.getValue()));
    }

    public Map<UUID, Integer> tryBuy(double money) {
        Map<UUID, Integer> countProduct = new HashMap<>();
        for (Product product: products) {
            countProduct.put(product.getId(), countForTryBuy(product, money));
        }
        return countProduct;
    }

    public boolean tryGetCheapestProduct(UUID productId) {
        return products.stream().anyMatch(p->p.getId().equals(productId) && p.getValue() != 0.0D);
    }

    public double cheapestProduct(UUID productId) {
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
            addProduct(product);
        }
    }

    public boolean tryGetBatch (Map<UUID, Integer> batch) {
        for (UUID key: batch.keySet()) {
            if (products.stream().noneMatch(p -> p.getId().equals(key) && p.getCount() >= batch.get(key)))
                return false;
        }
        return true;
    }

    public double getBatch(Map<UUID, Integer> batch) {
        double shopPrice = 0;
        for (UUID key: batch.keySet()) {
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
