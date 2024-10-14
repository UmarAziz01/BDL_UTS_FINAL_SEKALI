/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class TransaksiDetail {

    private String id_transaksi;
    private String id_produk;
    private int jumlah;
    private Double harga_satuan;
    private Double subtotal;

    public TransaksiDetail(String id_transaksi, String id_produk, int jumlah, Double harga_satuan, Double subtotal) {
        this.id_transaksi = id_transaksi;
        this.id_produk = id_produk;
        this.jumlah = jumlah;
        this.harga_satuan = harga_satuan;
        this.subtotal = subtotal;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    /**
     * @param id_transaksi the id_transaksi to set
     */
    public void setiId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    /**
     * @return the id_produk
     */
    public String getId_produk() {
        return id_produk;
    }

    /**
     * @param id_produk the id_produk to set
     */
    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    /**
     * @return the jumlah
     */
    public Integer getJumlah() {
        return jumlah;
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    /**
     * @return the harga_satuan
     */
    public Double getHarga_satuan() {
        return harga_satuan;
    }

    /**
     * @param harga_satuan the harga_satuan to set
     */
    public void setHarga_satuan(Double harga_satuan) {
        this.harga_satuan = harga_satuan;
    }

    /**
     * @return the subtotal
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
