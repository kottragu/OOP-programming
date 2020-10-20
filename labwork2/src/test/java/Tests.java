import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void testProgram1() throws Exception {
        ShopManager shopManager = new ShopManager();
        UUID shopID = shopManager.setShop("Лавочный", "зибенштрассе 71");
        shopManager.addProduct(shopID, new Product( "Ананас", 150, 80));
        UUID productID = shopManager.getShops().get(0).getProducts().get(0).getId();
        shopManager.addProduct(shopID, new Product( "Корм для кошек", 15, 20));
        shopManager.addProduct(shopID, new Product( "Мяско свиньи", 8, 450));
        Throwable exception = assertThrows(Exception.class, () -> shopManager.addProduct(shopID, new Product(productID, "Корм для кошек", 15, 20)));
        assertEquals("Incorrect name of product. Product with ID " + productID + " already exist with different name", exception.getMessage());
    }

    @Test
    public void testProgram2() throws  Exception{
        ShopManager shopManager = new ShopManager();
        UUID shopID = shopManager.setShop("Лавочный", "зибенштрассе 71");
        ArrayList<Product> products = new ArrayList<>(){{
            add(new Product("Ананас", 150, 80));
            add(new Product("Корм для кошек", 15, 20));
            add(new Product("Мяско свиньи", 8, 450));
            add(new Product("Молоко", 1234, 65));
            add(new Product("Хлеб", 23, 30));
            add(new Product("Кефир", 7, 80));
            add(new Product("приправа для супа", 11, 45));
        }};
        shopManager.addProduct(shopID, products);
        Map<UUID, Integer> resultMap = shopManager.tryBuy(shopID, 1000);

        Map<UUID, Integer> expectedResult = new HashMap<>() {{
            put(products.get(0).getId(), 12);
            put(products.get(1).getId(), 15);
            put(products.get(2).getId(), 2);
            put(products.get(3).getId(), 15);
            put(products.get(4).getId(), 23);
            put(products.get(5).getId(), 7);
            put(products.get(6).getId(), 11);
        }};

        assertTrue(() ->  expectedResult.keySet().stream().allMatch(uuid -> expectedResult.get(uuid).equals(resultMap.get(uuid))));
    }

    @Test
    public void testProgram3() throws Exception {
        ShopManager shopManager = new ShopManager();
        UUID shopID = shopManager.setShop("Драконорождённый", "Вызима, дворец Фольтеста");
        shopManager.addProduct(shopID, new Product( "Ананас", 97, 80));
        shopManager.addProduct(shopID, new Product( "Корм для кошек", 16, 20));
        shopManager.addProduct(shopID, new Product( "Мяско свиньи", 8, 450));
        shopManager.addProduct(shopID, new Product( "Молоко", 1234, 65));
        shopManager.addProduct(shopID, new Product( "Хлеб", 23, 30)); //этот
        UUID firstProductID = shopManager.getShops().get(0).getProducts().get(4).getId();
        shopManager.addProduct(shopID, new Product( "Сгущёнка", 5, 85));
        shopManager.addProduct(shopID, new Product( "приправа для супа", 11, 45)); //этот
        UUID secondProductID = shopManager.getShops().get(0).getProducts().get(6).getId();

        Transportation products = new Transportation();
        products.add(new Product(secondProductID, "приправа для супа", 10));
        products.add(new Product(firstProductID, "Хлеб", 23));
        assertEquals(shopManager.purchase(shopID, products), 1140.0);
    }

    @Test
    public void testProgram4() throws Exception {
        ShopManager shopManager = new ShopManager();
        UUID shopIDFirst = shopManager.setShop("Лавочный", "зибенштрассе 71"); //2550
        shopManager.addProduct(shopIDFirst, new Product( "Ананас", 150, 80));
        UUID firstProduct = shopManager.getShops().get(0).getProducts().get(0).getId(); //нумерация в имени в порядке добавления в batch!
        shopManager.addProduct(shopIDFirst, new Product( "Корм для кошек", 15, 20));
        UUID secondProduct = shopManager.getShops().get(0).getProducts().get(1).getId();
        shopManager.addProduct(shopIDFirst, new Product( "Мяско свиньи", 8, 450));
        UUID fourthProduct = shopManager.getShops().get(0).getProducts().get(2).getId();
        shopManager.addProduct(shopIDFirst, new Product( "Молоко", 1234, 65));
        UUID thirdProduct = shopManager.getShops().get(0).getProducts().get(3).getId();
        shopManager.addProduct(shopIDFirst, new Product( "Хлеб", 23, 30));
        shopManager.addProduct(shopIDFirst, new Product( "Кефир", 7, 80));
        shopManager.addProduct(shopIDFirst, new Product( "приправа для супа", 11, 45));

        UUID shopIDSecond = shopManager.setShop("Бар Голубая Устрица", "пр. Кронверкский, д. 49"); //2700
        shopManager.addProduct(shopIDSecond, new Product( firstProduct, "Ананас", 15, 100));
        shopManager.addProduct(shopIDSecond, new Product( secondProduct, "Корм для кошек", 150, 20));
        shopManager.addProduct(shopIDSecond, new Product( fourthProduct,"Мяско свиньи", 80, 400));
        shopManager.addProduct(shopIDSecond, new Product( thirdProduct, "Молоко", 124, 70));
        shopManager.addProduct(shopIDSecond, new Product( "Хлеб", 20, 28));
        shopManager.addProduct(shopIDSecond, new Product( "Кефир", 7, 80));
        shopManager.addProduct(shopIDSecond, new Product( "шава", 1, 200));

        UUID shopIdThird = shopManager.setShop("Драконорождённый", "Вызима, дворец Фольтеста"); //2550
        shopManager.addProduct(shopIdThird, new Product( firstProduct,"Ананас", 97, 80));
        shopManager.addProduct(shopIdThird, new Product( secondProduct, "Корм для кошек", 16, 20));
        shopManager.addProduct(shopIdThird, new Product( fourthProduct,"Мяско свиньи", 8, 450));
        shopManager.addProduct(shopIdThird, new Product( thirdProduct,"Молоко", 1234, 65));
        shopManager.addProduct(shopIdThird, new Product( "Хлеб", 23, 30));
        shopManager.addProduct(shopIdThird, new Product( "Сгущёнка", 5, 85));
        shopManager.addProduct(shopIdThird, new Product( "приправа для супа", 11, 45));

        Map<UUID, Integer> batch = new HashMap<>();
        batch.put(firstProduct, 10);
        batch.put(secondProduct, 10);
        batch.put(thirdProduct, 10);
        batch.put(fourthProduct, 2);
        assertEquals(shopManager.getCheapestBatch(batch), shopIDFirst);
    }
}