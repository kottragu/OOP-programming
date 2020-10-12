import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void testProgram1() throws Exception {
        ShopManager shopManager = new ShopManager();
        shopManager.setShop("Лавочный", "зибенштрассе 71", "aqw12-qwe1-213");
        shopManager.addProduct("aqw12-qwe1-213", new Product("p123", "Ананас", 150, 80));
        shopManager.addProduct("aqw12-qwe1-213", new Product("nt1418", "Корм для кошек", 15, 20));
        shopManager.addProduct("aqw12-qwe1-213", new Product("rfdj1", "Мяско свиньи", 8, 450));
        Throwable exception = assertThrows(Exception.class, () -> shopManager.addProduct("aqw12-qwe1-213", new Product("p123", "Корм для кошек", 15, 20)));
        assertEquals("Incorrect name of product. Product with ID p123 already has name Ананас", exception.getMessage());
    }

    @Test
    public  void testProgram2() throws  Exception{
        ShopManager shopManager = new ShopManager();
        shopManager.setShop("Лавочный", "зибенштрассе 71", "aqw12-qwe1-213");
        shopManager.addProduct("aqw12-qwe1-213", new Product("p123", "Ананас", 150, 80));
        shopManager.addProduct("aqw12-qwe1-213", new Product("nt1418", "Корм для кошек", 15, 20));
        shopManager.addProduct("aqw12-qwe1-213", new Product("akgag", "Мяско свиньи", 8, 450));
        shopManager.addProduct("aqw12-qwe1-213", new Product("bd/,b;kmhnsv", "Молоко", 1234, 65));
        shopManager.addProduct("aqw12-qwe1-213", new Product("vbjmca,", "Хлеб", 23, 30));
        shopManager.addProduct("aqw12-qwe1-213", new Product("acga", "Кефир", 7, 80));
        shopManager.addProduct("aqw12-qwe1-213", new Product("a1971573", "приправа для супа", 11, 45));
        Map<String, Integer> resultMap = shopManager.tryBuy("aqw12-qwe1-213", 1000);
        String expectedResult = "p123: 12 acga: 7 bd/,b;kmhnsv: 15 akgag: 2 a1971573: 11 vbjmca,: 23 nt1418: 15 ";
        String result = "";
        for(String s: resultMap.keySet()){
            result += s + ": " + resultMap.get(s) + " ";
        }
        assertEquals(expectedResult, result);
    }

    @Test
    public void testProgram3() throws Exception {
        ShopManager shopManager = new ShopManager();
        shopManager.setShop("Лавочный", "зибенштрассе 71", "aqw12-qwe1-213");
        shopManager.addProduct("aqw12-qwe1-213", new Product("p123", "Ананас", 150, 80));
        shopManager.addProduct("aqw12-qwe1-213", new Product("nt1418", "Корм для кошек", 15, 20));
        shopManager.addProduct("aqw12-qwe1-213", new Product("akgag", "Мяско свиньи", 8, 450));
        shopManager.addProduct("aqw12-qwe1-213", new Product("bd/,b;kmhnsv", "Молоко", 1234, 65));
        shopManager.addProduct("aqw12-qwe1-213", new Product("vbjmca,", "Хлеб", 23, 30));
        shopManager.addProduct("aqw12-qwe1-213", new Product("acga", "Кефир", 7, 80));
        shopManager.addProduct("aqw12-qwe1-213", new Product("a1971573", "приправа для супа", 11, 45));

        shopManager.setShop("Бар Голубая Устрица", "пр. Кронверкский, д. 49", "PSG");
        shopManager.addProduct("PSG", new Product("p123", "Ананас", 15, 100));
        shopManager.addProduct("PSG", new Product("nt1418", "Корм для кошек", 150, 20));
        shopManager.addProduct("PSG", new Product("akgag", "Мяско свиньи", 80, 400));
        shopManager.addProduct("PSG", new Product("bd/,b;kmhnsv", "Молоко", 124, 70));
        shopManager.addProduct("PSG", new Product("vbjmca,", "Хлеб", 20, 28));
        shopManager.addProduct("PSG", new Product("acga", "Кефир", 7, 80));
        shopManager.addProduct("PSG", new Product("aafvegs", "шава", 1, 200));

        shopManager.setShop("Драконорождённый", "Вызима, дворец Фольтеста", "Qwe1109t8qqrf");
        shopManager.addProduct("Qwe1109t8qqrf", new Product("p123", "Ананас", 97, 80));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("nt1418", "Корм для кошек", 16, 20));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("akgag", "Мяско свиньи", 8, 450));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("bd/,b;kmhnsv", "Молоко", 1234, 65));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("vbjmca,", "Хлеб", 23, 30));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("lstjbhm", "Сгущёнка", 5, 85));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("a1971573", "приправа для супа", 11, 45));

        Transportation products = new Transportation();
        products.add(new Product("a1971573", "приправа для супа", 10));
        products.add(new Product("vbjmca,", "Хлеб", 23));
        assertEquals(shopManager.purchase("Qwe1109t8qqrf", products), 1140.0);
    }

    @Test
    public void testProgram4() throws Exception {
        ShopManager shopManager = new ShopManager();
        shopManager.setShop("Лавочный", "зибенштрассе 71", "aqw12-qwe1-213"); //2550
        shopManager.addProduct("aqw12-qwe1-213", new Product("p123", "Ананас", 150, 80));
        shopManager.addProduct("aqw12-qwe1-213", new Product("nt1418", "Корм для кошек", 15, 20));
        shopManager.addProduct("aqw12-qwe1-213", new Product("akgag", "Мяско свиньи", 8, 450));
        shopManager.addProduct("aqw12-qwe1-213", new Product("bd/,b;kmhnsv", "Молоко", 1234, 65));
        shopManager.addProduct("aqw12-qwe1-213", new Product("vbjmca,", "Хлеб", 23, 30));
        shopManager.addProduct("aqw12-qwe1-213", new Product("acga", "Кефир", 7, 80));
        shopManager.addProduct("aqw12-qwe1-213", new Product("a1971573", "приправа для супа", 11, 45));

        shopManager.setShop("Бар Голубая Устрица", "пр. Кронверкский, д. 49", "PSG"); //2700
        shopManager.addProduct("PSG", new Product("p123", "Ананас", 15, 100));
        shopManager.addProduct("PSG", new Product("nt1418", "Корм для кошек", 150, 20));
        shopManager.addProduct("PSG", new Product("akgag", "Мяско свиньи", 80, 400));
        shopManager.addProduct("PSG", new Product("bd/,b;kmhnsv", "Молоко", 124, 70));
        shopManager.addProduct("PSG", new Product("vbjmca,", "Хлеб", 20, 28));
        shopManager.addProduct("PSG", new Product("acga", "Кефир", 7, 80));
        shopManager.addProduct("PSG", new Product("aafvegs", "шава", 1, 200));

        shopManager.setShop("Драконорождённый", "Вызима, дворец Фольтеста", "Qwe1109t8qqrf"); //2550
        shopManager.addProduct("Qwe1109t8qqrf", new Product("p123", "Ананас", 97, 80));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("nt1418", "Корм для кошек", 16, 20));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("akgag", "Мяско свиньи", 8, 450));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("bd/,b;kmhnsv", "Молоко", 1234, 65));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("vbjmca,", "Хлеб", 23, 30));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("lstjbhm", "Сгущёнка", 5, 85));
        shopManager.addProduct("Qwe1109t8qqrf", new Product("a1971573", "приправа для супа", 11, 45));

        Map<String, Integer> batch = new HashMap<>();
        batch.put("p123", 10);
        batch.put("nt1418", 10);
        batch.put("bd/,b;kmhnsv", 10);
        batch.put("akgag", 2);
        assertEquals(shopManager.getCheapestBatch(batch), "aqw12-qwe1-213");
    }
}