import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Config config = getConfig();
        if (config == null) {
            System.out.println("Ошибка загрузки конфигурации!");
        } else {
            ClientLog clientLog = new ClientLog();
            List<Product> products = new ArrayList<>();
            products.add(new Product("Хлеб", 40));
            products.add(new Product("Яблоки", 100));
            products.add(new Product("Молоко", 250));

            File file = new File(config.getLoad().getFileName());
            Basket basket;
            if (config.getLoad().isEnabled()) {
                if (config.getLoad().getFormat().equals("text")) {
                    basket = Objects.requireNonNullElseGet(Basket.loadFromTxtFile(file), () -> new Basket(products));
                } else {
                    basket = Objects.requireNonNullElseGet(Basket.loadFromJsonFile(file), () -> new Basket(products));
                }
            } else {
                basket = new Basket(products);
            }

            Scanner scanner;
            label:
            while (true) {
                System.out.println("end - окончание программы, print - печать вашей корзины, 1 2 - добавить продукт (первое число это номер продукта, а второе количество), print products - печать доступных продуктов");
                scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                switch (answer) {
                    case "end":
                        File fileSave = new File(config.getSave().getFileName());
                        if (config.getSave().isEnabled()) {
                            if (config.getSave().getFormat().equals("text")) {
                                basket.saveTxt(fileSave);
                            } else {
                                basket.saveJson(fileSave);
                            }
                        }
                        if (config.getLog().isEnabled()) {
                            File csvFile = new File(config.getLog().getFileName());
                            clientLog.exportAsCSV(csvFile);
                        }
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
    private static Config getConfig() {
        try {
            File file = new File("shop.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("load");
            Element element = (Element) nodeList.item(0);
            boolean loadIsEnable = getBoolean(element.getElementsByTagName("enabled").item(0).getTextContent());
            String loadFileName = element.getElementsByTagName("fileName").item(0).getTextContent();
            String loadFormat = element.getElementsByTagName("format").item(0).getTextContent();
            Config.Load load = new Config.Load(loadIsEnable, loadFileName, loadFormat);

            nodeList = doc.getElementsByTagName("save");
            element = (Element) nodeList.item(0);
            boolean saveIsEnable = getBoolean(element.getElementsByTagName("enabled").item(0).getTextContent());
            String saveFileName = element.getElementsByTagName("fileName").item(0).getTextContent();
            String saveFormat = element.getElementsByTagName("format").item(0).getTextContent();
            Config.Save save = new Config.Save(saveIsEnable, saveFileName, saveFormat);

            nodeList = doc.getElementsByTagName("log");
            element = (Element) nodeList.item(0);
            boolean logIsEnable = getBoolean(element.getElementsByTagName("enabled").item(0).getTextContent());
            String logFileName = element.getElementsByTagName("fileName").item(0).getTextContent();
            Config.Log log = new Config.Log(logIsEnable, logFileName);
            return new Config(load, save, log);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private static boolean getBoolean(String enabled) {
        return enabled.equals("true");
    }
}


