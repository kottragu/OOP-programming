import java.util.*;


public class ShopManager {
    private ArrayList<Shop> shops;

    ShopManager(){
        shops = new ArrayList<>();
    }

    public void addProduct(UUID shopId, Product product) throws Exception {
        shops.stream().filter(x -> x.getId().equals(shopId))
                                    .findFirst()
                                    .orElseThrow()
                                    .addProduct(product);
    }

    public void addProduct(UUID shopId, ArrayList<Product> defaultProducts) throws Exception {
        shops.stream().filter(x -> x.getId().equals(shopId))
                                    .findFirst()
                                    .orElseThrow()
                                    .addProduct(defaultProducts);
    }

    public UUID setShop(String shopName, String address) throws Exception {
        UUID shopId  = UUID.randomUUID();
        shops.add(new Shop(shopName, address, shopId));
        return shopId;
    }

    public ArrayList<Shop> getShops(){
        return shops;
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
            shopsId.add(shop.getId().toString());
        }
        return shopsId.toArray(new String[shops.size()]);
    }

    public void delivery(UUID shopId, Transportation delivery) throws Exception {
        if (shops.stream().noneMatch(x -> x.getId().equals(shopId)))
            throw new Exception("Incorrect shopID: " + shopId);
        shops.stream().filter(x -> x.getId().equals(shopId)).findFirst().orElseThrow().setDelivery(delivery);
    }

    public double purchase(UUID shopId, Transportation purchase) throws Exception {
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

    public UUID getShopIdWithCheapestProduct(UUID productId) throws Exception {
        UUID resultID = null;
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

    public Map<UUID, Integer> tryBuy (UUID shopId, double money) {
        Map<UUID, Integer> buy = new HashMap<>();
        for (Shop shop: shops) {
            if(shop.getId().equals(shopId)) {
                buy.putAll(shop.tryBuy(money));
            }
        }
        return buy;
    }

    public UUID getCheapestBatch(Map<UUID, Integer> batch) throws Exception {
        UUID cheapestShopId = null;
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
        if (cheapestShopId == null) {
            throw new Exception("There isn't the batch");
        } else
            return cheapestShopId;
    }

}
