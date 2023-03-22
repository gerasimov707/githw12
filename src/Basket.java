import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    private transient List<Product> products;

    public void setProducts(List<Product> products) {
        this.products = products;
    }

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

    public static Basket loadFromTxtFile(File txtFile) {
        try (BufferedReader br
                     = new BufferedReader(new FileReader(txtFile))) {
            String[] myProducts = br.readLine().split("\\$");
            List<MyProduct> list = new ArrayList<>();
            for (String prod : myProducts) {
                String[] product = prod.split(" ");
                list.add(new MyProduct(product[0],Integer.parseInt(product[1]),Integer.parseInt(product[2])));
            }
            Basket basket = new Basket(new ArrayList<>());
            basket.setMyProducts(list);
            return basket;
        } catch (Exception e) {
        }
        return null;
    }

    public void setMyProducts(List<MyProduct> myProducts) {
        this.myProducts = myProducts;
    }
    public void saveJson(File txtFile) {
        JSONObject obj = new JSONObject();
        JSONArray products = new JSONArray();
        for (MyProduct myProduct: myProducts){
            JSONObject obj2 = new JSONObject();
            obj2.put("title",myProduct.getTitle());
            obj2.put("price",String.valueOf(myProduct.getPrice()));
            obj2.put("amount",String.valueOf(myProduct.getAmount()));
            products.add(obj2);
        }
        obj.put("products", products);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(txtFile.getPath());
            fileWriter.write(obj.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Basket loadFromJsonFile(File txtFile) {
        JSONParser parser = new JSONParser();
        List<MyProduct> list = new ArrayList<>();
        try {
            Object obj = parser.parse(new FileReader(txtFile.getPath()));
            JSONObject jsonObject =  (JSONObject) obj;

            JSONArray products1 = (JSONArray) jsonObject.get("products");
            for (JSONObject jsonObject1 : (Iterable<JSONObject>) products1) {
                list.add(new MyProduct(jsonObject1.get("title").toString(), Integer.parseInt(jsonObject1.get("amount").toString()), Integer.parseInt(jsonObject1.get("price").toString())));
            }
            Basket basket = new Basket(new ArrayList<>());
            basket.setMyProducts(list);
            return basket;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

