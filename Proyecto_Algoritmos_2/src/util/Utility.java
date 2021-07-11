/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Food;
import domain.Product;
import domain.Restaurant;
import domain.list.CircularDoublyLinkedList;
import domain.list.CircularLinkedList;
import domain.list.SinglyLinkedList;
import domain.Security;
import domain.Supermarket;
import domain.graph.AdjacencyListGraph;
import domain.graph.AdjacencyMatrixGraph;
import domain.graph.EdgeWeight;
import domain.graph.GraphException;
import domain.graph.Vertex;
import domain.list.ListException;
import domain.tree.BST;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Profesor Lic. Gilberth Chaves Avila
 */
public class Utility {

    public static String dateFormat(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static int random() {
        return 1 + (int) Math.floor(Math.random() * 99);
    }

    public static int random(int bound) {
        //return 1+random.nextInt(bound);
        return 1 + (int) Math.floor(Math.random() * bound);
    }

    public static int random(int low, int higth) {
        //return 1+random.nextInt(bound);
        return low + (int) Math.floor(Math.random() * (higth - low));
    }

    public static boolean ValidarMail(String email) {

//      ^ especifica el inicio de la entrada.
//([_a-z0-9-]) primer grupo. Se refiere a la aparición de uno o más caracteres compuestos por guión bajo, letras, números y guiones.  
// \.[_a-z0-9-]) segundo grupo. Puede ser opcional y repetible, se refiere a la aparición de un punto seguido de uno o más caracteres compuestos por guión bajo, letras, números y guiones. 
// Luego la verificacion del carácter arroba @.
// Despues de repetite lo mismo, [a-z0-9-]). Tercer grupo. Especifica la aparición de uno o más caracteres compuestos por letras, números y guiones.
//(\.[a-z0-9-]) cuarto grupo. Especifica un punto seguido de uno o más caracteres compuestos por letras, números y guiones.
//(\.[a-z]{2,4}) quinto grupo. Especifica un punto seguido de entre 2 y 4 letras, con el fin de considerar dominios terminados, por ejemplo, en .com y .info
// "$" especifica el fin de la entrada.
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$");   // Patron para validar el email y se compila la expresion regular

        Matcher mather = pattern.matcher(email);
        return mather.find();// retorna si es valido o no 
    }

    public static String randomPass() {
        String result = "";
        for (int i = 0; i < 8; i++) {
            int rnd = (int) (Math.random() * 52); // or use Random or whatever
            char base = (rnd < 26) ? 'A' : 'a';
            result += (char) (base + rnd % 26);
        }
        return result;
    }

    public static boolean equals(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "integer":
                Integer x = (Integer) a;
                Integer y = (Integer) b;
                return x.equals(y);
            case "string":
                String s1 = (String) a;
                String s2 = (String) b;
                //return s1.compareTo(s2)==0; //OPCION 1
                return s1.equalsIgnoreCase(s2); //OPCION 2   
            case "security":
                Security sc1 = (Security) a;
                Security sc2 = (Security) b;
                return sc1.getUser().equals(sc2.getUser()) && sc1.getPassword().equals(sc2.getPassword());
            case "character":
                Character c1 = (Character) a;
                Character c2 = (Character) b;
                return c1.equals(c2);
            case "edgeWeight":
                EdgeWeight ew1 = (EdgeWeight) a;
                EdgeWeight ew2 = (EdgeWeight) b;
                return equals(ew1.getEdge(), ew2.getEdge());
            case "product":
                Product p1 = (Product) a;
                Product p2 = (Product) b;
                return p1.getName().equals(p2.getName()) && p1.getPrice() == p2.getPrice() && p1.getID() == p2.getID() && p1.getSupermarketID() == p2.getSupermarketID();
            case "vertex":
                Vertex v1 = (Vertex) a;
                Vertex v2 = (Vertex) b;
                return equals(v1.data, v2.data);
            case "supermarket":
                Supermarket sp1 = (Supermarket) a;
                Supermarket sp2 = (Supermarket) b;
                return sp1.getName().equals(sp2.getName()) && sp1.getLocation().equals(sp2.getLocation());
            case "restaurant":
                Restaurant r1 = (Restaurant) a;
                Restaurant r2 = (Restaurant) b;
                return r1.getName().equals(r2.getName()) && r1.getLocation().equals(r2.getLocation());
            case "food":
                Food f1 = (Food) a;
                Food f2 = (Food) b;
                return f1.getName().equalsIgnoreCase(f2.getName()) && f1.getRestaurantID() == (f2.getRestaurantID());
        }

        return false; //en cualquier otro caso
    }

    private static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return "integer";
        }
        if (a instanceof String && b instanceof String) {
            return "string";
        }
        if (a instanceof Security && b instanceof Security) {
            return "security";
        }
        if (a instanceof EdgeWeight && b instanceof EdgeWeight) {
            return "edgeWeight";
        }
        if (a instanceof Character && b instanceof Character) {
            return "character";
        }
        if (a instanceof Double && b instanceof Double) {
            return "double";
        }
        if (a instanceof Product && b instanceof Product) {
            return "product";
        }
        if (a instanceof Vertex && b instanceof Vertex) {
            return "vertex";
        }
        if (a instanceof Food && b instanceof Food) {
            return "food";
        }
        if (a instanceof Restaurant && b instanceof Restaurant) {
            return "restaurant";
        }
        if (a instanceof Supermarket && b instanceof Supermarket) {
            return "supermarket";
        }
        return "unknown"; //desconocido
    }

    public static boolean lessT(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "integer":
                Integer x = (Integer) a;
                Integer y = (Integer) b;
                return x < y;
            case "string":
                String s1 = (String) a;
                String s2 = (String) b;
                return s1.compareToIgnoreCase(s2) < 0;
            case "double":
                Double d1 = (Double) a;
                Double d2 = (Double) b;
                return d1 < d2;
            case "product":
                Product p1 = (Product) a;
                Product p2 = (Product) b;
                return p1.getName().compareToIgnoreCase(p2.getName()) < 0;
            case "food":
                Food f1 = (Food) a;
                Food f2 = (Food) b;
                return f1.getName().compareToIgnoreCase(f2.getName()) < 0;

        }
        return false; //en cualquier otro caso
    }

    public static boolean greaterT(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "integer":
                Integer x = (Integer) a;
                Integer y = (Integer) b;
                return x > y;
            case "string":
                String s1 = (String) a;
                String s2 = (String) b;
                return s1.compareToIgnoreCase(s2) > 0;
            case "double":
                Double d1 = (Double) a;
                Double d2 = (Double) b;
                return d1 > d2;
            case "product":
                Product p1 = (Product) a;
                Product p2 = (Product) b;
                return p1.getName().compareToIgnoreCase(p2.getName()) > 0;
            case "food":
                Food f1 = (Food) a;
                Food f2 = (Food) b;
                return f1.getName().compareToIgnoreCase(f2.getName()) > 0;
        }
        return false; //en cualquier otro caso
    }

    public static boolean checkPass(String pass) {
        boolean number = false;
        boolean leeterG = false;
        boolean leeterL = false;
        boolean sings = false;

        if (pass.length() < 8) // verifica que la clave tenga el tamaño minimo
        {
            return false;
        }

        for (int i = 0; i < pass.length(); i++) {
            int x = pass.charAt(i);

            if ((47 < x && x < 58)) // Verifica si tiene numeros
            {
                number = true;
            }
            if ((64 < x && x < 91)) // Verifica si tiene Letras Mayusculas
            {
                leeterG = true;
            }
            if ((96 < x && x < 123)) // Verifica si tiene Letras Minusculas
            {
                leeterL = true;
            }
            if ((32 < x && x < 44)) // Verifica si tiene Signos
            {
                sings = true;
            }
        }

        return number && leeterG && leeterL && sings;
    }

    //-------------------------------------------Metodos para las listas -------------------------------------------------------
    private static CircularLinkedList listUsers = new CircularLinkedList();
    private static AdjacencyMatrixGraph mGraphPlace = new AdjacencyMatrixGraph(20);
    private static AdjacencyListGraph lGraphRestaurants_Supermarkets = new AdjacencyListGraph(50);
    private static BST treeFood = new BST();
    private static BST treeProducts = new BST();
    private static CircularDoublyLinkedList listSearchs = new CircularDoublyLinkedList();
    private static Security intro = null;

    private static SinglyLinkedList listPlaces = new SinglyLinkedList();
    //private static Supermarket supermarket = new Supermarket();

    public static CircularLinkedList getUsers() {
        return listUsers;
    }

    public static AdjacencyMatrixGraph getmGraphPlace() {
        return mGraphPlace;
    }

    public static AdjacencyListGraph getlGraphRestaurants_Supermarkets() {
        return lGraphRestaurants_Supermarkets;
    }

    public static BST getTreeFood() {
        return treeFood;
    }

    public static BST getTreeProducts() {
        return treeProducts;
    }

    public static CircularDoublyLinkedList getListSearchs() {
        return listSearchs;
    }

    public static Security getIntro() {
        return intro;
    }

    public static void setIntro(Security intro) {
        Utility.intro = intro;
    }

    public static void setmGraphPlace(AdjacencyMatrixGraph mGraphPlace) {
        Utility.mGraphPlace = mGraphPlace;
    }

    public static void setTreeFood(BST treeFood) {
        Utility.treeFood = treeFood;
    }

    public static void setTreeProducts(BST treeProducts) {
        Utility.treeProducts = treeProducts;
    }

    public static void setListSearchs(CircularDoublyLinkedList listSearchs) {
        Utility.listSearchs = listSearchs;
    }

    public static void setListPlaces(SinglyLinkedList listPlaces) {
        Utility.listPlaces = listPlaces;
    }

//    private static Student introStudent = null;
//    public static void setIntro(Student s){introStudent = s;}
//    public static Student getIntro(){return introStudent;}
    public static void fillList() throws GraphException, ListException {  //metodo para cargar todas las listas necesarios al iniciar
        FileTXT file = new FileTXT();
        ArrayList<String> list = new ArrayList<>();

        if (file.existFile("Restaurant_Supermarket.txt")) {
            list = file.readFile("Restaurant_Supermarket.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                if (datos.length == 4) {
                    if (datos[0].equals("restaurant")) {
                        Restaurant obj = new Restaurant(datos[1], datos[2], Integer.parseInt(datos[3]));
                        obj.setAutoId(Integer.parseInt(datos[3]) + 1);
                        lGraphRestaurants_Supermarkets.addVertex(obj);
                    } else {
                        Supermarket obj = new Supermarket(datos[1], datos[2], Integer.parseInt(datos[3]));
                        obj.setAutoID(Integer.parseInt(datos[3]) + 1);
                        lGraphRestaurants_Supermarkets.addVertex(obj);
                    }
                } else {
                    lGraphRestaurants_Supermarkets.addEdge(lGraphRestaurants_Supermarkets.getVertexByIndex(Integer.parseInt(datos[0])),
                            lGraphRestaurants_Supermarkets.getVertexByIndex(Integer.parseInt(datos[1])));
                    lGraphRestaurants_Supermarkets.addWeight(lGraphRestaurants_Supermarkets.getVertexByIndex(Integer.parseInt(datos[0])),
                            lGraphRestaurants_Supermarkets.getVertexByIndex(Integer.parseInt(datos[1])),
                            Integer.parseInt(datos[2]));
                }

            }
        }
        if (file.existFile("Users.txt")) {
            list = file.readFile("Users.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                getUsers().add(new Security(datos[0], desBinaryCodify(datos[1])));
            }

        }
        if (file.existFile("comidas.txt")) {
            list = file.readFile("comidas.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                getTreeFood().add(new Food(datos[0], Double.valueOf(datos[1]), Integer.parseInt(datos[2])));
            }
        }
        if (file.existFile("productos.txt")) {
            list = file.readFile("productos.txt");
            for (int i = 0; i < list.size(); i++) {
                String[] datos = list.get(i).split(",");
                Product p = new Product(datos[1], Double.valueOf(datos[2]), Integer.parseInt(datos[3]));
                p.setID(Integer.parseInt(datos[0]));
                p.setAutoID(Integer.parseInt(datos[0]) + 1);
                getTreeProducts().add(p);
            }
        }
    }

    public static String binaryCodify(String dato) { //codifica un string
        String code = "";
        int[] numberCode = new int[dato.length()];//hacemos un vector del tamaño del string
        for (int i = 0; i < dato.length(); i++) {
            numberCode[i] = dato.charAt(i); //llenamos el vector con los chars del string 
        }
        for (int i = 0; i < numberCode.length; i++) { // convertimos el codigo ASCCI de cada char a un numero binario invertido
            while (numberCode[i] != 0) {
                code += numberCode[i] % 2 + "";
                numberCode[i] = numberCode[i] / 2;
            }
            code += "0@";//agregamos la divicion entre los numeros binarios
        }
        code += "end";
        return code;
    }

    public static String desBinaryCodify(String binary) {
        String text = "";
        String[] binaryText = binary.split("@"); // dividimos el string para cada binario
        int[] decimalText = new int[binaryText.length - 1];

        for (int i = 0; i < binaryText.length - 1; i++) {
            String aux = "";
            for (int j = 0; j < binaryText[i].length(); j++) {
                decimalText[i] += Integer.parseInt(binaryText[i].charAt(j) + "") * (Math.pow(2, j));
                //convertimos el binario en un numero decimal
            }
        }
        for (int i = 0; i < decimalText.length; i++) {
            text += (char) decimalText[i]; // de decimal lo pasamos a char
        }
        return text;
    }

}
