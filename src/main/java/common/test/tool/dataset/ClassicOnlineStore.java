package common.test.tool.dataset;

import common.test.tool.entity.Customer;
import common.test.tool.entity.Item;
import common.test.tool.entity.OnlineShoppingMall;
import common.test.tool.entity.Shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClassicOnlineStore {

    protected final OnlineShoppingMall mall = new OnlineShoppingMall();
    List<Shop> shops = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
    ;

    public ClassicOnlineStore() {
        addAShop(new String[]{"chair", "table", "small chair", "small table"},
                new int[]{2000, 5500, 1800, 2800},
                "White Furniture");
        addAShop(new String[]{"cup", "plate", "fork", "spoon", "chopsticks"},
                new int[]{380, 680, 210, 210, 180},
                "Dish Devices");
        addAShop(new String[]{"rope", "saw", "bond", "plane", "screwdriver"},
                new int[]{800, 1400, 480, 2200, 600},
                "The Do It Ourselves");
        addAShop(new String[]{"chair", "desk", "cable", "speaker", "headphone", "earphone"},
                new int[]{600, 1800, 230, 19000, 8800, 7800},
                "Electrics");
        addAShop(new String[]{"cold medicine", "ointment", "eye-drops", "poultice"},
                new int[]{800, 500, 600, 900},
                "Amazing Apothecary");
        addAShop(new String[]{"spinach", "ginseng", "onion", "ice cream", "crisps"},
                new int[]{100, 120, 160, 200, 80},
                "The Rapid Supermarket");
        addACustomer("Joe", 22, 8000, new String[]{"small table", "plate", "fork"});
        addACustomer("Steven", 27, 2000, new String[]{"ice cream", "screwdriver", "cable", "earphone"});
        addACustomer("Patrick", 28, 4000, new String[]{"onion", "ice cream", "crisps", "chopsticks"});
        addACustomer("Diana", 38, 12000, new String[]{"cable", "speaker", "headphone"});
        addACustomer("Chris", 26, 9000, new String[]{"saw", "bond", "plane", "bag"});
        addACustomer("Kathy", 22, 6000, new String[]{"cold medicine"});
        addACustomer("Alice", 32, 2500, new String[]{"chair", "desk"});
        addACustomer("Andrew", 35, 11000, new String[]{"pants", "coat"});
        addACustomer("Martin", 21, 1000, new String[]{"cup", "plate", "fork", "spoon"});
        addACustomer("Amy", 36, 2000, new String[]{"ointment", "poultice", "spinach", "ginseng", "onion"});
        mall.setShopList(shops);
        mall.setCustomerList(customers);

    }


    private void addAShop(String[] names, int[] prices, String shopname) {
        Shop shop = new Shop();
        shop.setName(shopname);
        Item[] itemArray = new Item[names.length];
        for (int i = 0; i < names.length; i++) itemArray[i] = new Item();
        for (int i = 0; i < names.length; i++) {
            itemArray[i].setPrice(prices[i]);
            itemArray[i].setName(names[i]);
        }
        List itemList = Arrays.asList(itemArray);
        shop.setItemList(itemList);
        shops.add(shop);
    }

    private void addACustomer(String name, int age, int budget, String[] itemNames) {
        Customer cust = new Customer();
        cust.setName(name);
        cust.setAge(age);
        cust.setBudget(budget);
        List<Item> itemList = new ArrayList<>();
        for (String s : itemNames) {
            Item item = new Item();
            item.setName(s);
            item.setPrice(-1);
            itemList.add(item);
        }

        cust.setWantToBuy(itemList);
        customers.add(cust);
    }

//    public static void main(String[] args) {
//        ClassicOnlineStore cos = new ClassicOnlineStore();
//        System.out.println(cos.mall.getCustomerList());
//    }


}
