public class MyProduct {

    public MyProduct(String title, int amount, int price) {
        this.title = title;
        this.amount = amount;
        this.price = price;
    }

    private String title;
    private int amount;
    private int price;

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
