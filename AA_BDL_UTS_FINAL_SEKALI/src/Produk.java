/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
public class Produk {

    private String id_produk;
    private String nama_produk;
    private Double harga;
    private Integer stock;

    public Produk(String id_produk, String nama, Double harga, Integer stock) {
        this.id_produk = id_produk;
        this.nama_produk = nama;
        this.harga = harga;
        this.stock = stock;
    }

    /**
     * @return the id
     */
    public String getId_produk() {
        return id_produk;
    }

    /**
     * @param id_produk the id to set
     */
    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    /**
     * @return the harga
     */
    public Double getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(Double harga) {
        this.harga = harga;
    }

    /**
     * @return the stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @return the nama_produk
     */
    public String getNama_produk() {
        return nama_produk;
    }

    /**
     * @param nama_produk the nama_produk to set
     */
    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }
}
