package org.akbarokta;

import lombok.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class Order implements IMenu {
    private List<OrderItem> items = new ArrayList<>();
    private Integer totalQty = 0;
    private Integer totalPrice = 0;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    @Override
    public void PrintMenu() {

    }

    @Override
    public void errorHandling() {
        System.out.println("=================================");
        System.out.println("Mohon masukan input \npilihan anda");
        System.out.println("=================================");
        System.out.println("(Y) untuk lanjut");
        System.out.println("(n) untuk keluar");
    }

    @Data
    public static class OrderItem {
        private String itemName;
        private Integer itemQty;
        private Integer itemPrice;

        public OrderItem(String itemName, Integer itemQty, Integer itemPrice) {
            this.itemName = itemName;
            this.itemQty = itemQty;
            this.itemPrice = itemPrice;
        }
    }

    public void addItem(String itemName, Integer itemQty, Integer itemPrice) {

        for (OrderItem item : items) {
            if (item.getItemName().equals(itemName)) {
                item.setItemQty(itemQty);
                item.setItemPrice(itemPrice);
                return;
            }
        }

        OrderItem newItem = new OrderItem(itemName, itemQty, itemPrice);
        items.add(newItem);
    }


    public void printOrderDetails() {
        String formattedTotalPrice = "";
        totalPrice = 0;
        totalQty = 0;
        System.out.println("\n=============================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("=============================\n");
        System.out.println("Rincian Pesanan:");
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            String formattedPrice = decimalFormat.format((long) item.getItemPrice() * item.getItemQty());
            System.out.println((i + 1) + ". " + item.getItemName() + "\t" + item.getItemQty() + "\t" + "Rp. " + formattedPrice);
            totalQty += item.getItemQty();
            totalPrice += item.getItemPrice() * item.getItemQty();
            formattedTotalPrice = decimalFormat.format(totalPrice);
        }
        System.out.println("----------------------------------+");
        System.out.println("Total \t\t\t\t" + totalQty + "\t" +"Rp. " + formattedTotalPrice + "\n");
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
    }

    public void printReceipt() {
        int counter = 1;
        String newFileName = "Struct.txt";

        while (new File(newFileName).exists()) {
            newFileName = "Struct_" + counter + ".txt";
            counter++;
        }
        File file = new File(newFileName);

        try(FileWriter fw = new FileWriter(file);
            BufferedWriter StructWr = new BufferedWriter(fw);
            )
        {
            StructWr.write("=============================\n");
            StructWr.write("Binar Food\n");
            StructWr.write("=============================\n\n");
            StructWr.write("Terima kasih sudah memesan \ndi Binar Food\n\n");
            StructWr.write("Dibawah ini adalah pesanan anda\n\n");

            for (OrderItem item : items) {
                long subtotal = (long) item.getItemPrice() * item.getItemQty();
                String formattedPrice = decimalFormat.format((long) item.getItemPrice() * item.getItemQty());
                String strukItem = item.getItemName() + "\t" + item.getItemQty() + "\t" + "Rp. " + formattedPrice;
                StructWr.write(strukItem);
                StructWr.newLine();
            }

            StructWr.write("-------------------------------+\n");
            StructWr.write("Total \t\t\t" + totalQty + "\t" + "Rp. " + totalPrice);
            StructWr.newLine();
            StructWr.write("\n\nPembayaran : BinarCash\n\n");

            StructWr.write("=============================\n");
            StructWr.write("Simpan Struk ini sebagai \nbukti pembayaran\n");
            StructWr.write("=============================");

            StructWr.flush();

            System.out.println("Struk berhasil disimpan di " + newFileName);
        } catch (IOException e) {
            System.out.print("Terjadi kesalahan saat menyimpan struk : ");
            e.getCause();
        }
    }
}
