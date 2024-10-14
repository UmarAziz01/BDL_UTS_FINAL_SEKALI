/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.HeadlessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class CRUD {

    Connection cn;
    PreparedStatement ps;
    Statement st;

    String driver = "org.postgresql.Driver";
    String koneksi = "jdbc:postgresql://localhost:5432/AA_BDL_UTS_FINAL_SEKALI";
    String user = "postgres";
    String password = " ";

    private String infoBegin, infoEnd, infoRollback, info;

    public CRUD() {
        try {
            Class.forName(driver);
            cn = DriverManager.getConnection(koneksi, user, password);
            System.out.println("Koneksi Sukses");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void joinTotalHarga(String idTransaksi) {
        try {

            String sql = "SELECT SUM(subtotal) FROM detail_transaksi WHERE id_transaksi = ?";

            ps = cn.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id_transaksi");
                double totalHarga = rs.getDouble("total_harga");

                // Menampilkan data atau bisa menyimpannya ke dalam variabel lokal
                System.out.println("ID Transaksi: " + id + ", Total Harga: " + totalHarga);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            // Pastikan ada koneksi ke database
            String sql = "SELECT t.id_transaksi, SUM(dt.subtotal) AS total_harga "
                    + "FROM transaksi t "
                    + "JOIN detail_transaksi dt ON t.id_transaksi = dt.id_transaksi "
                    + "WHERE t.id_transaksi = ? "
                    + "GROUP BY t.id_transaksi";

            ps = cn.prepareStatement(sql);
            ps.setString(1, idTransaksi);
            ResultSet rs = st.executeQuery(sql);

            double totalHarga;
            if (rs.next()) {
                totalHarga = rs.getDouble("total_harga");

                // Menampilkan total harga
                System.out.println("Total Harga untuk ID Transaksi " + idTransaksi + " adalah: " + totalHarga);

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public boolean tambahPelanggan(String idPe, String namaPe, String alamatPe, String noPe) {
        String sql = "INSERT INTO Pelanggan (id_pelanggan, nama, alamat, no_telepon) VALUES (?, ?, ?, ?)";
        try {

            ps = cn.prepareStatement(sql);
            ps.setString(1, idPe);
            ps.setString(2, namaPe);
            ps.setString(3, alamatPe);
            ps.setString(4, noPe);
            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean tambahProduk(String idPr, String namaPr, Double hargaPr, Integer stock) {
        String sql = "INSERT INTO Produk (id_produk, nama_produk, harga, stock) VALUES (?, ?, ?, ?)";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, idPr);
            ps.setString(2, namaPr);
            ps.setDouble(3, hargaPr);
            ps.setInt(4, stock);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean tambahTransaksi(String tanggal, String id_transaksi, String id_pelanggan, Double total_harga) {
        String sql = "INSERT INTO transaksi (tanggal, id_transaksi, id_pelanggan, total_harga) VALUES (?, ?, ?, ?)";
        try {
            cn.setAutoCommit(false);
            ps = cn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ps.setString(2, id_transaksi);
            ps.setString(3, id_pelanggan);
            ps.setDouble(4, total_harga);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean tambahTransaksiDetail(String idTr, String idPr, int jumlah, double hargaSatuan, double subtotal) {
        String sql = "INSERT INTO detail_transaksi (id_transaksi, id_produk, jumlah, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, idTr);
            ps.setString(2, idPr);
            ps.setInt(3, jumlah);
            ps.setDouble(4, hargaSatuan);
            ps.setDouble(5, subtotal);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Pelanggan> tampilPelanggan() {//list nama class
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM Pelanggan";
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Pelanggan peList = new Pelanggan( //nama class
                        rs.getString("id_pelanggan"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("no_telepon")
                );
                list.add(peList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Produk> tampilProduk() {//list nama class
        List<Produk> listProduk = new ArrayList<>();
        String sql = "SELECT * FROM Produk";
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Produk n = new Produk( //nama class
                        rs.getString("id_produk"),
                        rs.getString("nama_produk"),
                        rs.getDouble("harga"),
                        rs.getInt("stock")
                );
                listProduk.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduk;
    }

    public List<Transaksi> tampilTransaksi() {//list nama class
        List<Transaksi> listTransaksi = new ArrayList<>();
        String sql = "SELECT * FROM Transaksi";
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Transaksi t = new Transaksi( //nama class
                        rs.getString("tanggal"),
                        rs.getString("id_transaksi"),
                        rs.getString("id_pelanggan"),
                        rs.getDouble("total_harga")
                );
                listTransaksi.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTransaksi;
    }

    public double tHarga(String id) {
        String sql = "SELECT SUM(subtotal) as totalHarga FROM detail_transaksi WHERE id_transaksi = ?";
        double totalHarga;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                totalHarga = rs.getDouble("totalHarga");
                // Tampilkan total harga ke text field tfTotalHarga
                JOptionPane.showMessageDialog(null, "Data berhasil ditemukan.");

            } else {
                JOptionPane.showMessageDialog(null, "Transaksi tidak ditemukan");
                totalHarga = 0;
                // Kosongkan tfTotalHarga jika transaksi tidak ditemukan
                //tfTotalHarga.setText("");
            }
        } catch (HeadlessException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            // Kosongkan tfTotalHarga jika terjadi kesalahan
            //tfTotalHarga.setText("");
            totalHarga = 0;
        }
        return totalHarga;
    }

    public List<TransaksiDetail> tampilDetailTransaksi() {
        List<TransaksiDetail> listDetailTransaksi = new ArrayList<>();
        String sql = "SELECT * FROM detail_transaksi";

        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                TransaksiDetail detailTransaksi = new TransaksiDetail( //nama class
                        rs.getString("id_transaksi"),
                        rs.getString("id_produk"),
                        rs.getInt("jumlah"),
                        rs.getDouble("harga_satuan"),
                        rs.getDouble("subtotal")
                );
                listDetailTransaksi.add(detailTransaksi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDetailTransaksi;
    }

    public boolean hapusPelanggan(String id) {
        String sql = "DELETE FROM Pelanggan WHERE id_pelanggan = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean hapusProduk(String id) {
        String sql = "DELETE FROM Produk WHERE id_produk = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean hapusTransaksi(String id) {
        String sql = "DELETE FROM transaksi WHERE id_transaksi = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean hapusTransaksiDetail(String id) {
        String sql = "DELETE FROM detail_transaksi WHERE id_transaksi = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updatePelanggan(String id, String nama, String alamat, String no) {
        String sql = "UPDATE pelanggan SET nama = ?, alamat = ?, no_telepon = ? WHERE id_pelanggan = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, alamat);
            ps.setString(3, no);
            ps.setString(4, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updateProduk(String id, String nama, Double harga, Integer stock) {
        String sql = "UPDATE produk SET nama_produk = ?, harga = ?, stock = ? WHERE id_produk = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setDouble(2, harga);
            ps.setInt(3, stock);
            ps.setString(4, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updateTransaksi(String tanggal, String id_transaksi, String id_pelanggan, Double total_harga) {
        String sql = "UPDATE transaksi SET tanggal = ?, id_pelanggan=?, total_harga=? WHERE id_transaksi= ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ps.setString(2, id_pelanggan);
            ps.setDouble(3, total_harga);
            ps.setString(4, id_transaksi);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void hapusSemuaPelanggan() {
        String sql = "DELETE FROM pelanggan";
        try {
            st = cn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hapusSemuaProduk() {
        String sql = "DELETE FROM produk";
        try {
            st = cn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hapusSemuaTransaksi() {
        String sql = "DELETE FROM transaksi";
        try {
            st = cn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hapusSemuaTransaksiDetail() {
        String sql = "DELETE FROM detail_transaksi";
        try {
            st = cn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pelanggan cariDataByIdPelanggan(String id) throws DataTidakDitemukanException {// public nama class
        String sql = "SELECT * FROM pelanggan WHERE nama = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Pelanggan( // new .. nama class
                        rs.getString("id_pelanggan"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("no_telepon")
                );
            } else {
                throw new DataTidakDitemukanException("Data dengan ID Pelanggan " + id + " tidak ditemukan.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataTidakDitemukanException("Terjadi kesalahan saat mencari data.");
        }
    }

    public Produk cariDataByIdProduk(String id) throws DataTidakDitemukanException {
        String sql = "SELECT * FROM produk WHERE id_produk = ?";

        try {

            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Mengembalikan objek Produk jika ditemukan
                return new Produk(
                        rs.getString("id_produk"),
                        rs.getString("nama_produk"),
                        rs.getDouble("harga"),
                        rs.getInt("stock")
                );
            } else {
                throw new DataTidakDitemukanException("Data dengan ID Produk " + id + " tidak ditemukan.");
            }
        } catch (SQLException ex) {
            throw new DataTidakDitemukanException("Terjadi kesalahan saat mencari data: " + ex.getMessage());
        }
    }

    public Transaksi cariDataByIdTransaksi(String idTransaksi) throws DataTidakDitemukanException {// public nama class
        String sql = "SELECT * FROM transaksi WHERE id_transaksi = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, idTransaksi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Transaksi(// new .. nama class
                        rs.getString("tanggal"),
                        rs.getString("id_transaksi"),
                        rs.getString("id_pelanggan"),
                        rs.getDouble("total_harga")
                );
            } else {
                throw new DataTidakDitemukanException("Data dengan ID Transaksi " + idTransaksi + " tidak ditemukan.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataTidakDitemukanException("Terjadi kesalahan saat mencari data.");
        }
    }

    public Produk cariHargaByIdProduk(String id) throws DataTidakDitemukanException {
        String sql = "SELECT id_produk, nama_produk, harga, stock FROM produk WHERE id_produk = ?";

        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Produk(
                        rs.getString("id_produk"),
                        rs.getString("nama_produk"),
                        rs.getDouble("harga"),
                        rs.getInt("stock")
                );
            } else {
                throw new DataTidakDitemukanException("Data dengan ID Produk " + id + " tidak ditemukan.");
            }
        } catch (SQLException ex) {
            throw new DataTidakDitemukanException("Terjadi kesalahan saat mencari data: " + ex.getMessage());
        }
    }

    public void tutupKoneksi() {
        try {
            if (ps != null) {
                ps.close();
            }
            if (st != null) {
                st.close();
            }
            if (cn != null) {
                cn.close();
            }
            System.out.println("Koneksi Sudah Ditutup");
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void beginSql() {
        try {
            cn.setAutoCommit(false);// Menonaktifkan auto-commit (Memulai transaksi)
            infoBegin = "Transaksi dimulai. Auto-commit dimatikan.";
            System.out.println(infoBegin);
        } catch (SQLException e) {
            System.out.println("Gagal memulai transaksi");
        }
    }

    public void endSql() {
        try {
            cn.commit();// Menonaktifkan auto-commit (Memulai transaksi)
            infoEnd = "Transaksi berhasil disimpan. Auto-commit dihidupkan.";
            System.out.println("Transaksi berhasil disimpan. Auto-commit dihidupkan.");
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan transaksi. Auto-commit dihidupkan.");
        }
    }

    public void rollbackSql() {
        try {
            cn.rollback();// Menonaktifkan auto-commit (Memulai transaksi)
            System.out.println("Transaksi dibatalkan. Auto-commit dihidupkan.");
        } catch (SQLException e) {
            System.out.println("Gagal membatalkan transaksi. Auto-commit dihidupkan.");
        }
    }

    public void kembaliAuto() {
        try {
            cn.setAutoCommit(true);// Menonaktifkan auto-commit (Memulai transaksi)
            infoBegin = "Transaksi dimulai. Auto-commit dihidupkan kembali.";
            System.out.println("Transaksi dibatalkan. Auto-commit dihidupkan.");
        } catch (SQLException e) {
            System.out.println("Gagal membatalkan transaksi. Auto-commit dihidupkan.");
        }
    }
}
