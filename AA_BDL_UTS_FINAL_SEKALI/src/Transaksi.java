/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
public class Transaksi {

    private String tanggal;
    private String id_transaksi;
    private String id_pelanggan;
    private Double total_harga;

    public Transaksi(String tanggal, String id_transaksi, String id_pelanggan, Double total_harga) {
        this.tanggal = tanggal;
        this.id_transaksi = id_transaksi;
        this.id_pelanggan = id_pelanggan;
        this.total_harga = total_harga;
    }

    /**
     * @return the tanggal
     */
    public String getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the id_transaksi
     */
    public String getId_transaksi() {
        return id_transaksi;
    }

    /**
     * @param id_transaksi the id_transaksi to set
     */
    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    /**
     * @return the id_pelanggan
     */
    public String getId_pelanggan() {
        return id_pelanggan;
    }

    /**
     * @param id_pelanggan the id_pelanggan to set
     */
    public void setId_pelanggan(String id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    /**
     * @return the total_harga
     */
    public Double getTotal_harga() {
        return total_harga;
    }

    /**
     * @param total_harga the total_harga to set
     */
    public void setTotal_harga(Double total_harga) {
        this.total_harga = total_harga;
    }
}
