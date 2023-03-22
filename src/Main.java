import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClientLog clientLog = new ClientLog();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Хлеб", 40));
        products.add(new Product("Яблоки", 100));
        products.add(new Product("Молоко", 250));

        File file = new File("basket.json");
        Basket basket = Objects.requireNonNullElseGet(Basket.loadFromJsonFile(file), () -> new Basket(products));
        basket.setProducts(products);
        Scanner scanner;
        label:
        while (true) {
            System.out.println("end - окончание программы, print - печать вашей корзины, 1 2 - добавить продукт (первое число это номер продукта, а второе количество), print products - печать доступных продуктов");
            scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            switch (answer) {
                case "end":
                    basket.saveJson(file);
                    clientLog.exportAsCSV(new File("log.csv"));
                    break label;
                case "print":
                    basket.printCart();
                    break;
                case "print products":
                    for (int i = 0; i < products.size(); i++) {
                        Product product = products.get(i);
                        System.out.println(i + 1 + " " + product.getTitle() + " " + product.getPrice());
                    }
                    break;
                default:
                    String[] strings = answer.split(" ");
                    clientLog.log(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
                    basket.addToCart(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
                    break;
            }
        }
    }
}