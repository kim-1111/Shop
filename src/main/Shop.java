package main;

import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;
import model.Amount;
import model.Client;
import model.Employee;

public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private int numberProducts;
    private ArrayList<Sale> sales;

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.loadInventory();

        shop.initSession();
        Scanner scanner = new Scanner(System.in);

        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver ventas total");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = scanner.nextInt();
            } catch (Exception e) {
                System.err.println("Por favor, introduce un valor numerico");
                scanner.nextLine();
            }

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;

                case 8:
                    shop.showTotalSalesAmount();
                    break;

                case 9:
                    shop.deleteProduct();
                    break;

                case 10:
                    exit = true;
                    break;
            }
        } while (exit != true);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        addProduct(new Product("Manzana", 10.00, true, 10));
        addProduct(new Product("Pera", 20.00, true, 20));
        addProduct(new Product("Hamburguesa", 30.00, true, 30));
        addProduct(new Product("Fresa", 5.00, true, 20));
    }

    public void initSession() {
        Scanner sc = new Scanner(System.in);
        int number;
        String password;
        Employee employee;
        boolean login = false;
        do {
            try {
                System.out.println("Introduce el numero de empleado: ");
                number = sc.nextInt();
                System.out.println("Introduce la contrase�a: ");
                password = sc.next();
                employee = new Employee(number, password);
                if (employee.login(number, password)) {
                    login = true;
                } else {
                    System.err.println("Credenciales incorrectas");
                }
            } catch (Exception e) {
                System.err.println("Error: Tipo de dato incorrecto [NUMERO EMPLEATO: valor numerico]");
                sc.nextLine();
            }
        } while (!login);

    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("Dinero actual: " + this.cash);
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {
        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos");
            return;
        }

        boolean exist = false;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        for (Product product : inventory) {
            if (product != null) {
                if (product.getName().equals(name)) {
                    exist = true;
                }
            }
        }
        if (exist == true) {
            System.out.println("Product exists");
        } else {
            try {
                System.out.print("Precio mayorista: ");
                double wholesalerPrice = scanner.nextDouble();
                System.out.print("Stock: ");
                int stock = scanner.nextInt();

                addProduct(new Product(name, wholesalerPrice, true, stock));
            } catch (Exception e) {
                System.err.println("Por favor, introduce un valor numerico");
            }

        }
    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);
        try {
            if (product != null) {
                // ask for stock
                System.out.print("Seleccione la cantidad a añadir: ");
                int stock = scanner.nextInt();
                // update stock product
                product.setStock(product.getStock() + stock);
                System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

            } else {
                System.out.println("No se ha encontrado el producto con nombre " + name);
            }
        } catch (Exception e) {
            System.err.println("Por favor, introduce un valor numerico");
        }

    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());

        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product.getName());
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {
        // ask for client name
        Scanner scan = new Scanner(System.in);

        ArrayList<Product> productsSold = new ArrayList<>();

        // sale product until input name is not 0
        Amount totalAmount = new Amount(0.0);
        String name = "";
        try {
            System.out.println("Realizar venta, escribir nombre cliente:");

            String nombre = scan.nextLine();

            System.out.println("Introduce el numero del cliente: ");
            int numero = scan.nextInt();

            Client client = new Client(numero,new Amount(50),nombre);

            if (numero != client.getMemberid()) {
                System.out.println("No se encuentra el cliente");
            } else {
                while (!name.equals("0")) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
                    name = sc.nextLine();

                    if (name.equals("0")) {
                        break;
                    }
                    Product product = findProduct(name);
                    boolean productAvailable = false;

                    if (product != null && product.isAvailable()) {
                        productAvailable = true;
                        totalAmount.add(product.getPublicPrice());
                        product.setStock(product.getStock() - 1);

                        productsSold.add(product);

                        // if no more stock, set as not available to sale
                        if (product.getStock() == 0) {
                            product.setAvailable(false);
                        }
                        System.out.println("Producto añadido con éxito");
                    } else {
                        System.out.println("Producto no encontrado o sin stock");
                    }

                    // show cost total
                    totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
                    cash.add(totalAmount);

                    Sale sale = new Sale(client, productsSold, totalAmount);
                    sales.add(sale);
                }
                if (client.pay(totalAmount) == true) {
                    System.out.println("Venta realizada con éxito, total: " + totalAmount);
                    System.out.println("Banlance Actual: " + client.getBalance());
                }
            }
        } catch (Exception e) {
            System.err.println("Por favor, introduce un valor numerico");
        }
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.println(sale.toString());
            }
        }
    }

    private void showTotalSalesAmount() {
        Amount total = new Amount(0.0);
        for (Sale sale : sales) {
            total.add(sale.getAmount());
        }
        System.out.println("Total amount of all sales: " + total);

    }

    /**
     * add a product to inventory
     *
     * @param product
     */
    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos, se ha alcanzado el máximo de " + inventory.size());
            return;
        }
        inventory.add(product);
    }

    /**
     * check if inventory is full or not
     *
     * @return true if inventory is full
     */
    public boolean isInventoryFull() {
        return false;
    }

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) != null && inventory.get(i).getName().equals(name)) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto eliminado");

        } else {
            System.out.println("NO existe el producto");
        }

    }

}
