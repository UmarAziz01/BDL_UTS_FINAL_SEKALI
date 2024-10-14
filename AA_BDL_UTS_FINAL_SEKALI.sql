-- Table pelanggan
CREATE TABLE pelanggan(
	id_pelanggan VARCHAR(10) PRIMARY KEY,
	nama VARCHAR(100) NOT NULL,
	alamat VARCHAR(255) NOT NULL,
	no_telepon VARCHAR(20) NOT NULL UNIQUE
);

-- Table produk
CREATE TABLE produk (
	id_produk VARCHAR(10) PRIMARY KEY,
	nama_produk VARCHAR(100) NOT NULL,
	harga DOUBLE PRECISION NOT NULL,
	stock INT NOT NULL 
);

ALTER TABLE produk
ADD CONSTRAINT check_stock CHECK (stock > 0);


CREATE TABLE transaksi (
	tanggal VARCHAR(40) NOT NULL,
	id_transaksi VARCHAR(100) PRIMARY KEY,
	id_pelanggan VARCHAR(100) NOT NULL,
	total_harga DOUBLE PRECISION NULL CHECK (total_harga > 0)
);

ALTER TABLE transaksi
ADD CONSTRAINT fk_pelanggan
FOREIGN KEY (id_pelanggan) REFERENCES pelanggan(id_pelanggan);

ALTER TABLE transaksi 
	ALTER COLUMN tanggal TYPE varchar(40)

CREATE TABLE detail_transaksi (
	id_transaksi VARCHAR NOT NULL,
	id_produk VARCHAR NOT NULL,
	jumlah INT,
    harga_satuan DOUBLE PRECISION NOT NULL CHECK (harga_satuan > 0),
    subtotal DOUBLE PRECISION NOT NULL CHECK (subtotal > 0),
	FOREIGN KEY (id_transaksi) REFERENCES transaksi(id_transaksi),
	FOREIGN KEY (id_produk) REFERENCES produk(id_produk) 
);
	
DROP TABLE produk
DROP TABLE pelanggan
DROP TABLE transaksi
DROP TABLE detail_transaksi

DELETE FROM transaksi
DELETE FROM detail _transaksi

SELECT * FROM produk
SELECT * FROM pelanggan
SELECT * FROM transaksi
SELECT * FROM detail_transaksi

SELECT id_produk, nama_produk, harga, stock FROM produk WHERE id_produk = 'P1'

INSERT INTO produk VALUES ('P1','B1',2000,100);
INSERT INTO produk VALUES ('P2','B2',2200,100);
INSERT INTO produk VALUES ('P3','B3',2300,100);
INSERT INTO produk VALUES ('P4','B4',2400,100);
INSERT INTO produk VALUES ('P5','B5',2500,100);

INSERT INTO pelanggan VALUES ('L1','Lala','SDA','1231');
INSERT INTO pelanggan VALUES ('L2','Lili','SDA','1232');
INSERT INTO pelanggan VALUES ('L3','Lulu','SDA','1233');
INSERT INTO pelanggan VALUES ('L4','Lele','SDA','1234');
INSERT INTO pelanggan VALUES ('L5','Lolo','SDA','1235');

INSERT INTO transaksi VALUES ('Minggu, 13-Oktober-2024 20:19:40 WIB','T1','L1');
INSERT INTO transaksi VALUES ('Minggu, 13-Oktober-2024 20:29:40 WIB','T2','L1');
INSERT INTO transaksi VALUES ('Minggu, 13-Oktober-2024 20:39:40 WIB','T3','L1');
INSERT INTO transaksi VALUES ('Minggu, 13-Oktober-2024 20:49:40 WIB','T4','L1');
INSERT INTO transaksi VALUES ('Minggu, 13-Oktober-2024 20:59:40 WIB','T5','L1');

BEGIN
INSERT INTO transaksi VALUES ('Minggu, 13-Oktober-2024 20:39:49 WIB','T9','L1',5000);
COMMIT
INSERT INTO detail_transaksi VALUES ('DT2','T1','P1',10,100,10000);
ROLLBACK

INSERT INTO detail_transaksi VALUES ('T1','P1',10,100,10000);
INSERT INTO detail_transaksi VALUES ('T2','P1',10,100,10000);
INSERT INTO detail_transaksi VALUES ('T2','P2',10,100,10000);

-- Membuat fungsi trigger
CREATE OR REPLACE FUNCTION update_stock()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE produk
    SET stock = stock - NEW.jumlah
    WHERE id_produk = NEW.id_produk;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Membuat sebuah trigger
CREATE TRIGGER trg_update_stock
AFTER INSERT ON detail_transaksi
FOR EACH ROW
EXECUTE PROCEDURE update_stock();

---Untuk Menjumlahkan subtotal dengan id transaksi yang sama dengan SUM---
SELECT SUM(subtotal) as TOTAL_HARGA from detail_transaksi where id_transaksi = 'T2';
SELECT SUM(subtotal) FROM detail_transaksi WHERE id_transaksi = 'T2'

---Untuk Menjumlahkan subtotal dengan id transaksi yang sama dengan JOIN---
--TIDAK DIPAKAI DALAM PROGRAM
SELECT t.id_transaksi, SUM(dt.subtotal) AS total_harga  
                     FROM transaksi t 
                     JOIN detail_transaksi dt ON t.id_transaksi = dt.id_transaksi 
                     WHERE t.id_transaksi = 'T2'
                     GROUP BY t.id_transaksi

--UNTUK menjumlahkan semua id transaksi --
--TIDAK DIPAKAI DALAM PROGRAM
SELECT t.id_transaksi, SUM(dt.subtotal) AS total_harga
FROM transaksi t
JOIN detail_transaksi dt ON t.id_transaksi = dt.id_transaksi
GROUP BY t.id_transaksi;

--INNER JOIN__
SELECT dt.id_transaksi, p.nama AS nama_pelanggan, pr.nama_produk, dt.jumlah, dt.harga_satuan, dt.subtotal
FROM detail_transaksi dt
INNER JOIN transaksi t ON dt.id_transaksi = t.id_transaksi
INNER JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan
INNER JOIN produk pr ON dt.id_produk = pr.id_produk;


SELECT SUM(subtotal) as totalHarga FROM detail_transaksi WHERE id_transaksi = 'D1';
