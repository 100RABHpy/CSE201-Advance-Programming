import java.util.*;

public class Application implements QueryHandle {
    Scanner in = new Scanner(System.in);
    private double CompanyEarningByRestro = 0;
    private double CompanyEarningByDelivery = 0;

    private ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();
    private ArrayList<Restaurant> listOfRestaurants = new ArrayList<Restaurant>();

    public double getCompanyEarningByDelivery() {
        return CompanyEarningByDelivery;
    }

    public void setCompanyEarningByDelivery(double companyEarningByDelivery) {
        CompanyEarningByDelivery += companyEarningByDelivery;
    }

    public double getCompanyEarningByRestro() {
        return CompanyEarningByRestro;
    }

    public void setCompanyEarningByRestro(double companyEarningByRestro) {
        CompanyEarningByRestro += companyEarningByRestro;
    }

    public void addRestaurant(Restaurant restaurant) {
        listOfRestaurants.add(restaurant);

    }

    public Restaurant getRestaurant(int index) {
        Restaurant restaurant = listOfRestaurants.get(index);
        return restaurant;
    }

    public void chooseRestaurant() {
        for (Restaurant restaurant : listOfRestaurants) {
            System.out.println(restaurant);
        }
    }

    public void addCustomer(Customer customer) {
        listOfCustomers.add(customer);
    }

    public Customer getCustomer(int index) {
        Customer customer = listOfCustomers.get(index);
        return customer;
    }

    public void chooseCustomer() {
        for (Customer customer : listOfCustomers) {
            System.out.println(customer);
        }

    }

    @Override
    public String displayMenu() {
        String returnString = String.format("1) Enter as Restaurant Owner\n" + "2) Enter as Customer\n"
                + "3) Check User Details\n" + "4) Company Account details\n" + "5) Exit");
        return returnString;

    }

    @Override
    public void QueryHandler(Application application) {
        int exit = 1;
        while (exit > 0) {
            System.out.println("Welcome to Zotato: ");
            System.out.println(application.displayMenu());
            int query = Integer.parseInt(in.nextLine());
            if (query == 1) {
                RestroOwnerLogin owner = new RestroOwnerLogin();
                owner.QueryHandler(application);
            } else if (query == 2) {
                CustomerLogin customerLogin = new CustomerLogin();
                customerLogin.QueryHandler(application);
            } else if (query == 3) {
                query3();
            } else if (query == 4) {
                query4();
            } else if (query == 5) {
                exit = 0;
            }

        }
    }

    private void query4() {
        double a = this.getCompanyEarningByRestro();
        double b = this.getCompanyEarningByDelivery();
        System.out.println("Total Company balance - INR " + a + "/-");
        System.out.println("Total Delivery Charges Collected - INR " + b + "/-");
    }

    private void query3() {
        System.out.println("1) Customer List\n" + "2) Restaurant List");
        int input = Integer.parseInt(in.nextLine());
        if (input == 1) {
            this.chooseCustomer();
            int customerChosen = Integer.parseInt(in.nextLine());
            Customer customer = this.getCustomer(customerChosen - 1);
            String printString = String.format("%s , %s , %f/-", customer, customer.getAddress(),
                    customer.getWalletBalance());
            System.out.println(printString);

        } else if (input == 2) {
            this.chooseRestaurant();
            int restroChosen = Integer.parseInt(in.nextLine());
            Restaurant restaurant = this.getRestaurant(restroChosen - 1);
            String printString = String.format("%s , %s , %d/-", restaurant, restaurant.getAddress(),
                    restaurant.getNoOfOrderTaken());
            System.out.println(printString);
        }

    }

    public static void main(String[] args) {
        Application app = new Application();
        Customer Ram = new EliteCustomer("Ram", "Jaipur");
        Customer Sam = new EliteCustomer("Sam", "Delhi");
        Customer Tim = new SpecialCustomer("Tim", "Pune");
        Customer Kim = new NormalCustomer("Kim", "Kolkata");
        Customer Jim = new NormalCustomer("Jim", "Bombay");
        Restaurant shah = new Authentic("Shah", "Delhi");
        Restaurant ravi = new NormalRestro("Ravi's", "Delhi");
        Restaurant chinese = new Authentic("The Chinese", "Delhi");
        Restaurant wang = new FastFood("Wang's", "Delhi");
        Restaurant paradise = new NormalRestro("Paradise", "Delhi");
        app.addCustomer(Ram);
        app.addCustomer(Sam);
        app.addCustomer(Tim);
        app.addCustomer(Kim);
        app.addCustomer(Jim);
        app.addRestaurant(shah);
        app.addRestaurant(ravi);
        app.addRestaurant(chinese);
        app.addRestaurant(wang);
        app.addRestaurant(paradise);
        app.QueryHandler(app);
    }
}

interface QueryHandle {
    public String displayMenu();

    public void QueryHandler(Application application);
}

class Customer {
    private static int IdGenerator;
    private final int Id;
    private double WalletBalance = 1000;
    private double RewardPoints = 0;
    private final String Name;
    private final String Address;
    private ArrayList<Order> RecentOrders = new ArrayList<Order>();
    private final int DeliveryCharge;
    private final int CustomerDiscount = 0;

    public Customer(String name, String address, int deliveryCharge) {
        IdGenerator++;
        this.Id = IdGenerator;
        Name = name.strip();
        Address = address.strip();
        DeliveryCharge = deliveryCharge;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public double getWalletBalance() {
        return WalletBalance;
    }

    public void setWalletBalance(double newBalance) {
        WalletBalance = newBalance;
    }

    public double getRewardPoints() {
        return RewardPoints;
    }

    public void setRewardPoints(double newPoints) {
        RewardPoints += newPoints;
    }

    public void addOrder(Order order) {
        RecentOrders.add(order);
    }

    public ArrayList<Order> getRecentOrders() {
        return RecentOrders;
    }

    public int getDeliveryCharge() {
        return DeliveryCharge;
    }

    public int getCustomerDiscount(double afterRestoDiscount) {
        return CustomerDiscount;
    }

}

class EliteCustomer extends Customer {
    private final static int deliveryCharge = 0;
    private final static int customerDiscount = 50;

    public EliteCustomer(String name, String address) {
        super(name, address, deliveryCharge);
    }

    @Override
    public int getCustomerDiscount(double afterRestoDiscount) {

        if (afterRestoDiscount > 200) {

            return customerDiscount;
        }
        return 0;
    }

    @Override
    public String toString() {
        String returnString = String.format("%d.%s(Elite)", this.getId(), this.getName());
        return returnString;
    }

}

class SpecialCustomer extends Customer {
    private final static int deliveryCharge = 20;
    private final static int customerDiscount = 25;

    public SpecialCustomer(String name, String address) {
        super(name, address, deliveryCharge);
    }

    @Override
    public int getCustomerDiscount(double afterRestoDiscount) {
        if (afterRestoDiscount > 200) {
            return customerDiscount;
        }
        return 0;
    }

    @Override
    public String toString() {
        String returnString = String.format("%d.%s(Special)", this.getId(), this.getName());
        return returnString;
    }

}

class NormalCustomer extends Customer {
    private final static int deliveryCharge = 40;

    public NormalCustomer(String name, String address) {
        super(name, address, deliveryCharge);
    }

    @Override
    public String toString() {
        String returnString = String.format("%d.%s", this.getId(), this.getName());
        return returnString;
    }

}

class CustomerLogin implements QueryHandle {
    Customer customer;
    Cart cart;
    Restaurant restaurant;
    Scanner in = new Scanner(System.in);
    Application application;

    @Override
    public String displayMenu() {
        String returnString = String.format("1) Select Restaurant\n" + "2) checkout cart\n" + "3) Reward won\n"
                + "4) print the recent orders\n" + "5) Exit");
        return returnString;

    }

    public void QueryHandler(Application application) {
        application.chooseCustomer();
        int customerChosen = Integer.parseInt(in.nextLine());
        customer = application.getCustomer(customerChosen - 1);
        int exit = 1;

        while (exit > 0) {

            System.out.print("Welcome ");
            System.out.println(customer.getName());
            System.out.println("Customer Menu");
            System.out.println(this.displayMenu());
            int query = Integer.parseInt(in.nextLine());
            if (query == 1) {
                query1(application);
            } else if (query == 2) {
                query2();
            } else if (query == 3) {
                query3();
            } else if (query == 4) {
                query4();
            } else if (query == 5) {
                exit = query5();
            }
        }
    }

    public void query1(Application application) {
        if (cart == null) {
            application.chooseRestaurant();
            int restroChosen = Integer.parseInt(in.nextLine());
            restaurant = application.getRestaurant(restroChosen - 1);
            Hashtable<Integer, FoodItem> hashTable = restaurant.getFoodItemOffer();
            ArrayList<Integer> keys = new ArrayList<Integer>(hashTable.keySet());
            for (int i = keys.size() - 1; i >= 0; i--) {
                System.out.println(hashTable.get(keys.get(i)));
            }
            int itemChosen = Integer.parseInt(in.nextLine());
            FoodItem item = hashTable.get(itemChosen);
            System.out.println("Enter Item Quantity-");
            int quantity = Integer.parseInt(in.nextLine());
            item.setQuantity(item.getQuantity() - quantity);
            cart = new Cart(customer, item, quantity, restaurant, application);
            System.out.println("Item Added To Cart");
        } else {
            System.out.println("You have item in cart. \n Choose one option");
            System.out.println("1) checkout");
            System.out.println("2) resetCart ");
            int option = Integer.parseInt(in.nextLine());
            if (option == 1) {
                this.query2();
                return;
            } else {
                cart = null;
                this.query1(application);
                return;
            }
        }

    }

    public void query2() {
        if (cart != null) {
            System.out.println("Item In Cart:");

            System.out.println(cart.checkOutString());
            System.out.println("\t" + "1)Proceed To Checkout");
            int checkout = Integer.parseInt(in.nextLine());

            if (checkout == 1) {

                System.out.println(cart.checkOut());
                cart = null;
            }
        } else {
            System.out.println("Your cart is empty");
            this.QueryHandler(application);
            return;
        }
    }

    public void query3() {
        System.out.println(customer.getRewardPoints());
    }

    public void query4() {
        ArrayList<Order> a = customer.getRecentOrders();
        int size = a.size();
        int maxIndex = size;
        if (maxIndex > 10) {
            maxIndex = 10;
        }
        for (int i = 0; i < maxIndex; i++) {
            Order order = a.get(size - 1 - i);
            System.out.println(order);
        }
    }

    public int query5() {
        return 0;
    }
}

class Restaurant {
    Scanner in = new Scanner(System.in);
    private static int IdGenerator;
    private final int Id;
    private final String Name;
    private final String Address;
    private double RewardPointsOfRestaurant = 0;
    private Hashtable<Integer, FoodItem> FoodItemOffer = new Hashtable<Integer, FoodItem>();
    private int NoOfOrderTaken = 0;
    private int RestaurantDiscount = 0;
    private static final int rewardPointForCustomer = 5;

    public Restaurant(String name, String address) {
        IdGenerator++;
        this.Id = IdGenerator;
        this.Name = name.strip();
        this.Address = address.strip();

    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public double getRewardPoints() {
        return RewardPointsOfRestaurant;
    }

    public void setRewardPoints(double newRewardPoint) {
        RewardPointsOfRestaurant += newRewardPoint;
    }

    public void addFoodItem(FoodItem foodItem) {
        int id = foodItem.getUniqueId();
        FoodItemOffer.put(id, foodItem);
    }

    public Hashtable<Integer, FoodItem> getFoodItemOffer() {
        return FoodItemOffer;
    }

    public int getNoOfOrderTaken() {
        return NoOfOrderTaken;

    }

    public void setNoOfOrderTaken() {
        NoOfOrderTaken++;
    }

    public int getRestaurantDiscount() {
        return RestaurantDiscount;
    }

    public void setRestaurantDiscount() {
        System.out.print("Enter offer on total bill value -");
        int restaurantDiscount = Integer.parseInt(in.nextLine());
        RestaurantDiscount = restaurantDiscount;
    }

    // just for authentic restaurant
    public int getExtraDiscount() {
        return 0;
    }

    public double calculateReward(double totalAmount) {
        if (totalAmount > 100) {
            int noOfPoint = (int) totalAmount / 100;
            double newRewardPoint = 5 * noOfPoint;
            this.setRewardPoints(newRewardPoint);
            return newRewardPoint;
        }
        return 0;
    }

}

class NormalRestro extends Restaurant {

    public NormalRestro(String name, String address) {
        super(name, address);
    }

    @Override
    public void setRestaurantDiscount() {
        System.out.println("Not Available For Your Restaurant");
    }

    @Override
    public String toString() {
        String returnString = String.format("%d) %s", this.getId(), this.getName());
        return returnString;
    }
}

class FastFood extends Restaurant {
    private static final int rewardPointForCustomer = 10;

    public FastFood(String name, String address) {
        super(name, address);
    }

    @Override

    public double calculateReward(double totalAmount) {
        if (totalAmount >= 150) {
            int noOfPoint = (int) totalAmount / 150;
            double newRewardPoint = rewardPointForCustomer * noOfPoint;
            this.setRewardPoints(newRewardPoint);
            return newRewardPoint;
        }
        return 0;

    }

    @Override
    public String toString() {
        String returnString = String.format("%d) %s(Fast Food)", this.getId(), this.getName());
        return returnString;
    }

}

class Authentic extends Restaurant {
    private final int extraDiscountAfterRestaurantDiscount = 50;
    private static final int rewardPointForCustomer = 25;

    public Authentic(String name, String address) {
        super(name, address);

    }

    @Override
    public double calculateReward(double totalAmount) {
        if (totalAmount >= 200) {
            int noOfPoint = (int) totalAmount / 200;
            double newRewardPoint = rewardPointForCustomer * noOfPoint;
            this.setRewardPoints(newRewardPoint);
            return newRewardPoint;
        }
        return 0;
    }

    @Override
    public int getExtraDiscount() {
        return extraDiscountAfterRestaurantDiscount;
    }

    @Override
    public String toString() {
        String returnString = String.format("%d) %s(Authentic)", this.getId(), this.getName());
        return returnString;
    }

}

class RestroOwnerLogin implements QueryHandle {
    Scanner in = new Scanner(System.in);
    Restaurant restaurant;

    @Override
    public String displayMenu() {

        String returnString = String.format(
                "1) Add item\n" + "2) Edit item\n" + "3) Print Rewards\n" + "4) Discount on bill value\n" + "5) Exit");
        return returnString;
    }

    @Override
    public void QueryHandler(Application application) {

        int exit = 1;
        Scanner in = new Scanner(System.in);
        application.chooseRestaurant();
        int restroChosen = Integer.parseInt(in.nextLine());
        restaurant = application.getRestaurant(restroChosen - 1);
        while (exit > 0) {

            System.out.print("Welcome ");
            System.out.println(restaurant.getName());
            System.out.println("Restaurant Menu");
            System.out.println(this.displayMenu());
            int query = in.nextInt();
            if (query == 1) {
                query1();
            } else if (query == 2) {
                query2();
            } else if (query == 3) {
                query3();
            } else if (query == 4) {
                query4();
            } else if (query == 5) {
                exit = query5();
            }

        }
    }

    public void query1() {
        System.out.println("Enter Food Item Details");
        System.out.print("Food Name:");
        String name = restaurant.getName() + "-" + in.nextLine();
        System.out.print("Item Price:");
        int price = Integer.parseInt(in.nextLine());
        System.out.print("Item Quantity:");
        int quantity = Integer.parseInt(in.nextLine());
        System.out.print("Item Category:");
        String category = in.nextLine();
        System.out.print("Offer:");
        int discount = Integer.parseInt(in.nextLine());
        FoodItem foodItem = new FoodItem(name, price, quantity, category, discount);
        restaurant.addFoodItem(foodItem);
        System.out.println(foodItem);
    }

    public void query2() {
        Hashtable<Integer, FoodItem> hashTable = restaurant.getFoodItemOffer();
        ArrayList<Integer> keys = new ArrayList<Integer>(hashTable.keySet());
        for (int i = keys.size() - 1; i >= 0; i--) {
            System.out.println(hashTable.get(keys.get(i)));
        }

        int itemChosen = Integer.parseInt(in.nextLine());
        FoodItem item = hashTable.get(itemChosen);

        System.out.println(displayEditOption());
        int editNo = Integer.parseInt(in.nextLine());
        if (editNo == 1) {
            System.out.print("Enter the new name: ");
            String name = in.nextLine();
            item.setName(name);
            System.out.println(item);
        }
        if (editNo == 2) {
            System.out.print("Enter the new price: ");
            int price = Integer.parseInt(in.nextLine());
            item.setPrice(price);
            System.out.println(item);
        }
        if (editNo == 3) {
            System.out.print("Enter the new quantity: ");
            int quantity = Integer.parseInt(in.nextLine());
            item.setQuantity(quantity);
            System.out.println(item);

        }
        if (editNo == 4) {
            System.out.print("Enter the new Category: ");
            String category = in.nextLine();
            item.setCategory(category);
            System.out.println(item);
        }
        if (editNo == 5) {
            System.out.print("Enter the new offer: ");
            int discount = Integer.parseInt(in.nextLine());
            item.setOffer(discount);
            System.out.println(item);
        }
    }

    private String displayEditOption() {
        String returnString = String
                .format("1) Name\n" + "2) Price\n" + "3) Quantity\n" + "4) Category\n" + "5) Offer");
        return returnString;
    }

    public void query3() {
        System.out.println("Reward Points: " + restaurant.getRewardPoints());
    }

    public void query4() {

        restaurant.setRestaurantDiscount();

    }

    public int query5() {
        return 0;
    }

}

class FoodItem {
    private static int IdGenerator = 0;
    private final int UniqueId;
    private String Name;
    private int Price;
    private String Category;
    private int Quantity;
    private int FoodItemDiscount;

    public FoodItem(String name, int price, int quantity, String category, int discount) {
        this.IdGenerator++;
        this.UniqueId = IdGenerator;
        this.Name = name.strip();
        this.Price = price;
        this.Category = category.strip();
        this.Quantity = quantity;
        this.FoodItemDiscount = discount;
    }

    @Override
    public String toString() {

        String returnString = String.format("%d %s %d %d %d off %s", UniqueId, Name, Price, Quantity, FoodItemDiscount,
                Category);
        return returnString;
    }

    public String getName() {
        return Name;
    }

    public String getCategory() {
        return Category;
    }

    public int getUniqueId() {
        return UniqueId;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;

    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getQuantity() {
        return Quantity;

    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getOffer() {
        return FoodItemDiscount;
    }

    public void setOffer(int discount) {
        FoodItemDiscount = discount;
    }

}

class Order {
    private final Restaurant restaurant;
    private final FoodItem foodItem;
    private final int quantity;
    private final double totalAmount;
    private final double DeliveryCharge;

    public Order(Restaurant restaurant, FoodItem foodItem, int quantity, double totalAmount, double deliveryCharge) {
        this.restaurant = restaurant;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.DeliveryCharge = deliveryCharge;
    }

    @Override
    public String toString() {
        String returnString = String.format(
                "Bought item: %s, quantity: %d for Rs %d \n from Restaurant %s and Delivery Charge:%f",
                foodItem.getName(), quantity, foodItem.getPrice(), restaurant.getName(), DeliveryCharge);
        return returnString;
    }
}

class Cart {

    private final FoodItem foodItem;
    private final int quantity;
    private double TotalAmount;
    private final Customer customer;
    private final Restaurant restaurant;
    private final Application application;

    public Cart(Customer customer, FoodItem item, int quantity, Restaurant restaurant, Application application) {

        this.customer = customer;
        this.quantity = quantity;
        this.foodItem = item;
        this.restaurant = restaurant;
        this.application = application;
        bill();

    }

    private void bill() {
        double payableAmount = foodItem.getPrice() * quantity;

        double afterItemDiscount = calculator(payableAmount, foodItem.getOffer());

        double overallBill = calculator(afterItemDiscount, restaurant.getRestaurantDiscount());

        if (overallBill > 100) {
            overallBill -= restaurant.getExtraDiscount();
        }

        double afterCustomerDiscount = overallBill - customer.getCustomerDiscount(overallBill);

        double DeliveryCharge = customer.getDeliveryCharge();
        double restroCharge = (afterCustomerDiscount * 1) / 100;
        TotalAmount = afterCustomerDiscount + DeliveryCharge;

        application.setCompanyEarningByRestro(restroCharge);
        application.setCompanyEarningByDelivery(DeliveryCharge);
        Order order = new Order(restaurant, foodItem, quantity, TotalAmount, DeliveryCharge);
        customer.addOrder(order);
    }

    private double calculator(double payableAmount, int discount) {
        double temp = (payableAmount * discount) / 100;
        payableAmount -= temp;
        return payableAmount;

    }

    @Override
    public String toString() {
        String returnString = String.format("%s \nDelivery charge - %d/- \nTotal order value - INR %f/-", foodItem,
                customer.getDeliveryCharge(), TotalAmount);
        return returnString;

    }

    public String checkOut() {
        double amount = TotalAmount;
        while (amount > 0) {
            double walletMoney = customer.getWalletBalance();
            double rewardMoney = customer.getRewardPoints();
            if (amount < rewardMoney) {
                rewardMoney -= amount;
                customer.setRewardPoints(rewardMoney);
                amount = 0;
            } else if (amount > rewardMoney && rewardMoney != (double) 0) {
                amount -= rewardMoney;
                rewardMoney = 0;
                customer.setRewardPoints(rewardMoney);
            } else if (amount < walletMoney) {
                walletMoney -= amount;
                customer.setWalletBalance(walletMoney);
                amount = 0;
            } else {
                System.out.println("Insufficient Balance App will show  Unexpected behavior ");
            }

        }

        restaurant.setNoOfOrderTaken();

        customer.setRewardPoints(restaurant.calculateReward(TotalAmount));
        String returnString = String.format("%d items successfully bought for INR %f /-", quantity, TotalAmount);

        return returnString;

    }

    public String checkOutString() {
        String returnString = String.format(
                "%d %s %d %d %d off %s\nDelivery charge - %d/- \nTotal order value - INR %f/-", foodItem.getUniqueId(),
                foodItem.getName(), foodItem.getPrice(), quantity, foodItem.getOffer(), foodItem.getCategory(),
                customer.getDeliveryCharge(), TotalAmount);
        return returnString;
    }

}