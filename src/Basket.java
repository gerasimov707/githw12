import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    private transient List<Product> products;

    private List<MyProduct> myProducts;

    public Basket(List<Product> products) {
        this.products = products;
        myProducts = new ArrayList<>();
    }

    public void addToCart(int productNum, int amount) {
        Product product = products.get(productNum - 1);
        String title = product.getTitle();
        MyProduct myProduct = getProductByTitleFromBasket(title);
        if (myProduct != null) {
            int index = myProducts.indexOf(myProduct);
            myProduct.addAmount(amount);
            myProducts.set(index, myProduct);
        } else {
            myProducts.add(
                    new MyProduct(title, amount, product.getPrice())
            );
        }
    }

    public void printCart() {
        for (MyProduct myProduct : myProducts) {
            System.out.println(myProduct.getTitle() + " " + myProduct.getAmount() + " штук " + myProduct.getPrice() + "руб/шт " + myProduct.getAmount() * myProduct.getPrice() + " руб сумма");
        }
    }

    private MyProduct getProductByTitleFromBasket(String title) {
        for (MyProduct myProduct : myProducts) {
            if (myProduct.getTitle().contains(title)) {
                return myProduct;
            }
        }
        return null;
    }

    public void saveTxt(File txtFile) {
        try (PrintWriter printWriter = new PrintWriter(txtFile)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (MyProduct product : myProducts) {
                String rezString = product.getTitle() + " " + product.getAmount() + " " + product.getPrice();
                stringBuilder.append(rezString);
                stringBuilder.append("$");
            }
            printWriter.write(stringBuilder.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File txtFile,List<Product> products) {
        try (BufferedReader br
                     = new BufferedReader(new FileReader(txtFile))) {
            String[] myProducts = br.readLine().split("\\$");
            List<MyProduct> list = new ArrayList<>();
            for (String prod : myProducts) {
                String[] product = prod.split(" ");
                list.add(new MyProduct(product[0],Integer.parseInt(product[1]),Integer.parseInt(product[2])));
            }
            Basket basket = new Basket(products);
            basket.setMyProducts(list);
            return basket;
        } catch (Exception e) {
        }
        return null;
    }

    public void setMyProducts(List<MyProduct> myProducts) {
        this.myProducts = myProducts;
    }
}
