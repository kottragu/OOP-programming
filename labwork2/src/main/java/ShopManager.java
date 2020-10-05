
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

    public void delivery(String shopId, Products delivery) throws Exception {
        for(Shop shop: shops){
            if(shop.getId().equals(shopId)) {
                shop.setDelivery(delivery);
            }
        }
        throw new Exception("Incorrect shopID: " + shopId);
    }

    public double purchase(String shopId, Products purchase) throws Exception {
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

    public String getShopIdWithCheapestProduct(String productId) {
        String resultID = null;
        double minPrice = 0.0D;
        boolean isFirst = true;
        for (Shop shop: shops){
            for(Product product: shop.getProducts()) {
                if(product.getId().equals(productId)) {
                    if (product.getValue() == 0.0D) {
                        continue;
                    }
                    if (isFirst){
                        minPrice = product.getValue();
                        resultID = product.getId();
                        isFirst = false;
                        continue;
                    }
                    if (minPrice > product.getValue()) {
                        minPrice = product.getValue();
                        resultID = product.getId();
                    }
                }
            }
        }
        return resultID;
    }

    private int countForTryBuy(Product product, double money) {
        if( product.getCount() > (int) money/product.getValue()) {
                return (int)(money/product.getValue());
        } else{
            return product.getCount();
        }
    }

    public Map<String, Integer> tryBuy (String shopId, double money) {
        Map<String, Integer> buy = new HashMap<>();
        for (Shop shop: shops) {
            if(shop.getId().equals(shopId)) {
                for(Product product: shop.getProducts()) {
                    int count = countForTryBuy(product, money);
                    buy.put(product.getId(), count);
                }
            }
        }
        return buy;
    }

    public String getCheapestBatch(Map<String, Integer> batch) {
        String cheapestShopId = null;
        double minPrice = Double.MAX_VALUE;

        for (Shop shop: shops){
            double shopPrice = 0;
            Integer countProductsInShop = 0;

            янеумеювнейминг:
                for(String key: batch.keySet()) {
                    for(Product product: shop.getProducts()){
                        if(product.getId().equals(key)){
                            if (product.getCount() < batch.get(key)){
                                break янеумеювнейминг;
                            } else { // ПРОВЕРИТЬ ВСЁ ЛИ ЭТО  // нет надо добавить сравнение с мин
                                countProductsInShop++;
                                shopPrice += batch.get(key) * product.getValue();
                            }
                        }
                    }
                }

            if (countProductsInShop.equals(batch.size())){
                if (shopPrice < minPrice){
                    minPrice = shopPrice;
                    cheapestShopId = shop.getId();
                }
            }
        }
        return cheapestShopId;
    }

}
