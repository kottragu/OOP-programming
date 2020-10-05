import java.util.ArrayList;

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

    public void addProduct(Product product) throws Exception {
        boolean isExistProduct = false;
        for (Product existProduct: products) {
            if (existProduct.getId().equals(product.getId())) {
                isExistProduct = true;
                if (existProduct.getName().equals(product.getName())) {
                    existProduct.changeCount(existProduct.getCount() + product.getCount());
                    if (product.getValue() != 0.0D){
                        existProduct.setValue(product.getValue());
                    }

                } else
                    throw new Exception("Incorrect name of product. Product with ID " + existProduct.getId() + " already has name " + existProduct.getName());
            }
        }
        if(!isExistProduct)
            products.add(product);
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

    public void setDelivery(Products delivery) throws Exception {
        for(Product product: delivery.getProducts()){
            this.addProduct(product);
        }
    }

    public double setPurchase(Products purchase) throws Exception {
        double resultSum = 0;
        for(Product product: purchase.getProducts()){
            for (Product productInShop: products){
                if(product.getId().equals(productInShop.getId()) && product.getName().equals(productInShop.getName())){
                    if(productInShop.getCount() < product.getCount()){
                        throw new Exception("Нема продуктов в магазине");
                    } else {
                        resultSum += product.getCount() * productInShop.getValue();
                        productInShop.changeCount(productInShop.getCount() - product.getCount());
                    }
                }
            }
        }
        return resultSum;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }
}
