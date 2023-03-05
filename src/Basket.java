import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Basket implements Serializable {

    private transient List<Product> products;

    private List<MyProduct> myProducts;

    public Basket(List<Product> products) {
        this.products = products;
        myProducts = new ArrayList<>();
    }
    public void setProducts(List<Product> products) {
        this.products = products;
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

    public void saveBin(File textFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(textFile.getName()))) {
            oos.writeObject(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromBinFile(File txtFile,List<Product> products) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(txtFile.getName()))) {
            Basket basket = (Basket) ois.readObject();
            basket.setProducts(products);
            return basket;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void setMyProducts(List<MyProduct> myProducts) {
        this.myProducts = myProducts;
    }
}
