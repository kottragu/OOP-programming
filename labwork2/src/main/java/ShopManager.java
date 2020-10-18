
import java.util.*;
import java.util.stream.Stream;


public class ShopManager {
    ArrayList<Shop> shops;

    ShopManager(){
        shops = new ArrayList<>();
    }

    public void addProduct(String shopId, Product product) throws Exception {
        shops.stream().filter(x -> x.getId().equals(shopId))
                                    .findFirst()
                                    .orElseThrow()
                                    .addProduct(product);
    }

    public void addProduct(String shopId, ArrayList<Product> defaultProducts) throws Exception {
        shops.stream().filter(x -> x.getId().equals(shopId))
                                    .findFirst()
                                    .orElseThrow()
                                    .addProduct(defaultProducts);
    }

    public void setShop(String shopName, String address, String shopId) throws Exception {
        if (shops.stream().noneMatch(x -> x.getId().equals(shopId)))
            shops.add(new Shop(shopName, address, shopId));
        else
            throw new Exception("Shop with id " + shopId + " already exist");
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
        if (shops.stream().noneMatch(x -> x.getId().equals(shopId)))
            throw new Exception("Incorrect shopID: " + shopId);
        shops.stream().filter(x -> x.getId().equals(shopId)).findFirst().orElseThrow().setDelivery(delivery);
    }

    public double purchase(String shopId, Transportation purchase) throws Exception {
        double resultSum = 0.0D;
        boolean isShopExist = false;
        for(Shop shop: shops) {
            if(shop.getId().equals(shopId)) {
                isShopExist = true;
                if (shop.tryGetPurchase(purchase))
                    resultSum += shop.setPurchase(purchase);
            }
        }
        if (!isShopExist) {
            throw new Exception("Incorrect shopID: " + shopId);
        } else if (resultSum == 0.0D) {
            throw new Exception("Недостаточно товара");
        } else
            return resultSum;
    }

    public String getShopIdWithCheapestProduct(String productId) throws Exception {
        String resultID = null;
        double minPrice = Double.MAX_VALUE;
        for (Shop shop: shops) {
            if(shop.tryGetCheapestProduct(productId)) {
                if (shop.cheapestProduct(productId) < minPrice) {
                    resultID = shop.getId();
                    minPrice = shop.cheapestProduct(productId);
                }
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

    public String getCheapestBatch(Map<String, Integer> batch) throws Exception {
        String cheapestShopId = null;
        double minPrice = Double.MAX_VALUE;
        for (Shop shop: shops) {
            if(shop.tryGetBatch(batch)) {
                double shopPrice = shop.getBatch(batch);
                if (shopPrice < minPrice) {
                    minPrice = shopPrice;
                    cheapestShopId = shop.getId();
                }
            }
        }
        System.out.println(cheapestShopId);
        if (cheapestShopId == null) {
            throw new Exception("There isn't the batch");
        } else
            return cheapestShopId;
    }

}
