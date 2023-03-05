public class Product {

    public Product(String title, int price){
        this.price = price;
        this.title = title;
    }
    private String title;
    private int price;

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }
}
