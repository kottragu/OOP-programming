
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopManager {
    ArrayList<Shop> shops;

    ShopManager(){
        shops = new ArrayList<>();
    }

    public void addProduct(String shopId, Product product) throws Exception {
        for(Shop shop: shops) {
            if (shop.getId().equals(shopId)){
                shop.addProduct(product);
            }
        }
    }

    public void addProduct(String shopId, ArrayList<Product> defaultProducts) throws Exception {
        for(Shop shop: shops) {
            if (shop.getId().equals(shopId)){
                shop.addProduct(defaultProducts);
            }
        }
    }

    public void setShop(String shopName, String address, String shopId) {
        shops.add(new Shop(shopName, address, shopId));
    }

    public String[] getShopsNames() {
        List<String> shopsNames = new ArrayList<>();
        for (Shop shop:shops) {
            shopsNames.add(shop.getName());
        }
        return shopsNames.toArray(new String[shops.size()]);
    }

    public String[] getShopsId() {
        List<String> shopsId = new ArrayList<>();
        for (Shop shop:shops) {
            shopsId.add(shop.getId());
        }
        return shopsId.toArray(new String[shops.size()]);
    }

    public void delivery(String shopId, Transportation delivery) throws Exception {
        for(Shop shop: shops){
            if(shop.getId().equals(shopId)) {
                shop.setDelivery(delivery);
            }
        }
        throw new Exception("Incorrect shopID: " + shopId);
    }

    public double purchase(String shopId, Transportation purchase) throws Exception {
        double resultSum = 0;
        boolean isShopExist = false;
        for(Shop shop: shops) {
            if(shop.getId().equals(shopId)) {
                isShopExist = true;
                resultSum += shop.setPurchase(purchase);
            }
        }
        if (!isShopExist) {
            throw new Exception("Incorrect shopID: " + shopId);
        } else
            return resultSum;
    }

    public String getShopIdWithCheapestProduct(String productId) throws Exception {
        String resultID = null;
        double minPrice = Double.MAX_VALUE;
        double tempMin;
        for (Shop shop: shops){
            tempMin = shop.cheapestProduct(productId);
            if(tempMin < minPrice){
                resultID = shop.getId();
                minPrice = tempMin;
            }
        }
        if (minPrice != Double.MAX_VALUE) {
            throw new Exception("Product with ID " + productId + " doesn't exist");
        }
        return resultID;
    }

    public Map<String, Integer> tryBuy (String shopId, double money) {
        Map<String, Integer> buy = new HashMap<>();
        for (Shop shop: shops) {
            if(shop.getId().equals(shopId)) {
                buy.putAll(shop.tryBuy(money));
            }
        }
        return buy;
    }

    public String getCheapestBatch(Map<String, Integer> batch) {
        String cheapestShopId = null;
        double minPrice = Double.MAX_VALUE;

        for (Shop shop: shops) {
            double shopPrice  = shop.getBatch(batch);
            if (shopPrice < minPrice){
                minPrice = shopPrice;
                cheapestShopId = shop.getId();
            }
        }
        return cheapestShopId;
    }

}
