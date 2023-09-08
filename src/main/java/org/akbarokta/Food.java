package org.akbarokta;

import java.text.DecimalFormat;

public class Food extends Product implements IMenu{

    public Food(String name, Integer price) {
        super(name, price);
    }
    public Food(){

    }
    @Override
    public void PrintMenu() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(getPrice());
        System.out.println(getName() + " \t| " + "Rp. "+ formattedPrice);
    }

    @Override
    public void errorHandling() {

    }

    public void orderMenu() {
        System.out.println("\n=============================");
        System.out.println("Berapa Pesanan anda");
        System.out.println("=============================\n");
        PrintMenu();
        System.out.println("(input 0 untuk kembali)");
    }
}
