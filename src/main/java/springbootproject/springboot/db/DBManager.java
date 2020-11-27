package springbootproject.springboot.db;

import java.time.LocalDate;
import java.util.ArrayList;

public class DBManager {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static ArrayList<ShopItem> shopItems = new ArrayList<>();
    static {
        tasks.add(new Task(1, "Task 1", "The first task is to create your first JavaEE project!!!",
                LocalDate.of(2020,9,1), true));
        tasks.add(new Task(2, "Task 2", "The second task is to use Bootstrap front-end design in your JavaEE project!!!",
                LocalDate.of(2020, 9, 8), true));
        tasks.add(new Task(3, "Task 3", "The third task is to work with database in your JavaEE project!!!",
                LocalDate.of(2020, 9, 15), true));


        shopItems.add(new ShopItem(1, "IPhone 12 Pro Max",
                "IPhone 12 Pro Max, 512 GB, 5G Network, Display 6.7, Frequency 6 GHz",
                1499, 1000, 5,
                "https://cnet1.cbsistatic.com/img/_qDPxqXKaC5iqAhCWbLxjhQHwoQ=/940x0/2020/10/13/603381b2-02f0-4688-9b47-e3ab991e9ff6/screen-shot-2020-10-13-at-2-04-50-pm-2.png"));
        shopItems.add(new ShopItem(2, "Meizu 16 Pro",
                "Meizu 16 Pro, Screen 6.2, 2232x1080px, 8 GB CPU, 4 GB RAM",
                599, 555, 3,
                "https://mobile-review.com/articles/2019/image/press-meizu-16s-pro/off/1.jpg"));
        shopItems.add(new ShopItem(3, "Samsung Galaxy 20",
                "Samsung Galaxy 20, Galaxy Fold 2, Z Flip 5G, FullHD, 256 GB Memory",
                799, 128, 4,
                "https://www.ferra.ru/thumb/1800x0/filters:quality(75):no_upscale()/imgs/2020/08/08/12/4041506/9b4fcc92a1ff1f0e82e765016f641fd4cf3ad379.jpg"));
        shopItems.add(new ShopItem(4, "Xiaomi Redmi Note 9",
                "Xiaomi Redmi Note 9, 6 GB CPU, 128 GB, 4G Network, Display 6.5, Frequency 5 GHz",
                349, 765, 5,
                "https://xiaomi-store.kz/images/stories/virtuemart/product/Xiaomi_Redmi_Note_9_Pro.jpg"));
    }

    private static int id = 5;

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static ArrayList<ShopItem> getShopItems() {
        return shopItems;
    }

    public static ArrayList<Task> getFilteredTasks(String name, LocalDate fromDeadLineDate, LocalDate toDeadLineDate, String isComplete) {
        ArrayList<Task> tasks = new ArrayList<>(DBManager.tasks);
        if (!name.isEmpty()) {
            tasks.removeIf(task -> !task.getName().toLowerCase().contains(name.toLowerCase()));
        }
        if (fromDeadLineDate != null) {
            tasks.removeIf(task -> task.getDeadLineDate().compareTo(fromDeadLineDate) < 0);
        }
        if (toDeadLineDate != null) {
            tasks.removeIf(task -> task.getDeadLineDate().compareTo(toDeadLineDate) > 0);
        }
        if (!isComplete.isEmpty()) {
            tasks.removeIf(task -> !String.valueOf(task.isCompleted()).equals(isComplete));
        }
        return tasks;
    }

    public static ArrayList<ShopItem> getFilteredShopItems(String name, int fromPrice, int toPrice, int stars) {
        ArrayList<ShopItem> shopItems = new ArrayList<>(DBManager.shopItems);
        if (!name.isEmpty()) {
            shopItems.removeIf(shopItem -> !shopItem.getName().toLowerCase().contains(name.toLowerCase()));
        }
        if (fromPrice > 0) {
            shopItems.removeIf(shopItem -> shopItem.getPrice() < fromPrice);
        }
        if (toPrice > 0) {
            shopItems.removeIf(shopItem -> shopItem.getPrice() > toPrice);
        }
        if (stars > 0) {
            shopItems.removeIf(shopItem -> shopItem.getStars() < stars);
        }
        return shopItems;
    }

    public static Task getTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id)
                return task;
        }
        return null;
    }

    public static ShopItem getShopItem(int id) {
        for (ShopItem shopItem : shopItems) {
            if (shopItem.getId() == id)
                return shopItem;
        }
        return null;
    }

    public static void addTask(Task task) {
        task.setId(id);
        tasks.add(task);
        id++;
    }

    public static void addShopItem(ShopItem shopItem) {
        shopItem.setId(id);
        shopItems.add(shopItem);
        id++;
    }

    public static void saveTask(Task task) {
        Task t = getTask(task.getId());
        if (t != null) {
            t.setName(task.getName());
            t.setDescription(task.getDescription());
            t.setDeadLineDate(task.getDeadLineDate());
            t.setCompleted(task.isCompleted());
        }
    }

    public static void saveShopItem(ShopItem shopItem) {
        ShopItem s = getShopItem(shopItem.getId());
        if (s != null) {
            s.setName(shopItem.getName());
            s.setDescription(shopItem.getDescription());
            s.setPrice(shopItem.getPrice());
            s.setAmount(shopItem.getAmount());
            s.setStars(shopItem.getStars());
            s.setPictureURL(shopItem.getPictureURL());
        }
    }

    public static void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    public static void deleteShopItem(int id) {
        shopItems.removeIf(shopItem -> shopItem.getId() == id);
    }
}
