package org.akbarokta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static Order order = new Order();
    static List<Food> foodList = new ArrayList<>(Arrays.asList(
            new Food("Kwetiaw Goreng", 15000),
            new Food("Mie Goreng\t", 13000),
            new Food("Nasi + Ayam\t", 18000),
            new Food("Es Teh Manis ", 3000),
            new Food("Es Jeruk  \t", 5000)
    ));

    public static void main(String[] args) {
        while (true) {
            System.out.println("========================================");
            System.out.println("Selamat Datang di Binar Food");
            System.out.println("========================================\n");
            System.out.println("Silahkan pilih makanan : ");

            for (int i = 0; i < foodList.size(); i++) {
                Food item = foodList.get(i);
                System.out.print((i + 1) + ". ");
                item.PrintMenu();
            }

            System.out.println("99. Pesan dan Bayar");
            System.out.println("0. Keluar Aplikasi");

            System.out.print("=> ");
            int choice = 0;
            try {
                choice = input.nextInt();
            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                if(handleInputError().equalsIgnoreCase("continue")){
                    continue;
                }
            }

            if (choice == 0) {
                System.out.println("Terima kasih, selamat berkunjung kembali!");
                System.exit(1);
                break;
            } else if (choice == 99) {
                order.printOrderDetails();
                System.out.print("=> ");
                int confirm = 0;
                try {
                    confirm = input.nextInt();
                } catch (InputMismatchException e) {
                    if(handleInputError().equalsIgnoreCase("continue")){
                        continue;
                    }
                }

                if (confirm == 1) {
                    order.printReceipt();
                } else if (confirm == 2) {
                    continue;
                } else if (confirm == 0) {
                    System.exit(1);
                }
                break;
            } else if (choice >= 1 && choice <= foodList.size())
            {
                Food selectedMenuItem = foodList.get(choice - 1);
                selectedMenuItem.orderMenu();
                String itemName = selectedMenuItem.getName();
                Integer itemPrice = selectedMenuItem.getPrice();
                while (true) {
                    System.out.print("qty => ");
                    int quantity = 0;
                    try {
                        quantity = input.nextInt();

                    } catch (InputMismatchException e) {
                        if(handleInputError().equalsIgnoreCase("continue")){
                            System.out.println("Masukkan jumlah pesanan ");
                            continue;
                        }
                    }

                    if (quantity > 0) {
                        order.addItem(itemName, quantity, itemPrice);
                        System.out.println("Pesanan telah ditambahkan ke dalam keranjang.");
                    } else if (quantity == 0) {
                        break;
                    } else {
                        System.out.println("==============================");
                        System.out.println("Minimal 1 jumlah Pesanan");
                        System.out.println("==============================");
                    }
                }
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        input.close();
    }

    private static String handleInputError() {
        order.errorHandling();
        input.nextLine(); // Membersihkan buffer input
        System.out.print("=> ");
        String errorChoice = input.nextLine();
        return errorChoice.equalsIgnoreCase("Y") ? "continue" : "exit";
    }
}
