PGDMP                   	    |            AA_BDL_UTS_FINAL_SEKALI    16.3    16.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    25751    AA_BDL_UTS_FINAL_SEKALI    DATABASE     �   CREATE DATABASE "AA_BDL_UTS_FINAL_SEKALI" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_Indonesia.1252';
 )   DROP DATABASE "AA_BDL_UTS_FINAL_SEKALI";
                postgres    false            �            1255    25806    update_stock()    FUNCTION     �   CREATE FUNCTION public.update_stock() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE produk
    SET stock = stock - NEW.jumlah
    WHERE id_produk = NEW.id_produk;
    RETURN NEW;
END;
$$;
 %   DROP FUNCTION public.update_stock();
       public          postgres    false            �            1259    25789    detail_transaksi    TABLE     �  CREATE TABLE public.detail_transaksi (
    id_transaksi character varying NOT NULL,
    id_produk character varying NOT NULL,
    jumlah integer,
    harga_satuan double precision NOT NULL,
    subtotal double precision NOT NULL,
    CONSTRAINT detail_transaksi_harga_satuan_check CHECK ((harga_satuan > (0)::double precision)),
    CONSTRAINT detail_transaksi_subtotal_check CHECK ((subtotal > (0)::double precision))
);
 $   DROP TABLE public.detail_transaksi;
       public         heap    postgres    false            �            1259    25765 	   pelanggan    TABLE     �   CREATE TABLE public.pelanggan (
    id_pelanggan character varying(10) NOT NULL,
    nama character varying(100) NOT NULL,
    alamat character varying(255) NOT NULL,
    no_telepon character varying(20) NOT NULL
);
    DROP TABLE public.pelanggan;
       public         heap    postgres    false            �            1259    25772    produk    TABLE     �   CREATE TABLE public.produk (
    id_produk character varying(10) NOT NULL,
    nama_produk character varying(100) NOT NULL,
    harga double precision NOT NULL,
    stock integer NOT NULL,
    CONSTRAINT check_stock CHECK ((stock > 0))
);
    DROP TABLE public.produk;
       public         heap    postgres    false            �            1259    25778 	   transaksi    TABLE     .  CREATE TABLE public.transaksi (
    tanggal character varying(40) NOT NULL,
    id_transaksi character varying(100) NOT NULL,
    id_pelanggan character varying(100) NOT NULL,
    total_harga double precision,
    CONSTRAINT transaksi_total_harga_check CHECK ((total_harga > (0)::double precision))
);
    DROP TABLE public.transaksi;
       public         heap    postgres    false            �          0    25789    detail_transaksi 
   TABLE DATA           c   COPY public.detail_transaksi (id_transaksi, id_produk, jumlah, harga_satuan, subtotal) FROM stdin;
    public          postgres    false    218   "       �          0    25765 	   pelanggan 
   TABLE DATA           K   COPY public.pelanggan (id_pelanggan, nama, alamat, no_telepon) FROM stdin;
    public          postgres    false    215   b       �          0    25772    produk 
   TABLE DATA           F   COPY public.produk (id_produk, nama_produk, harga, stock) FROM stdin;
    public          postgres    false    216   �       �          0    25778 	   transaksi 
   TABLE DATA           U   COPY public.transaksi (tanggal, id_transaksi, id_pelanggan, total_harga) FROM stdin;
    public          postgres    false    217          a           2606    25771 "   pelanggan pelanggan_no_telepon_key 
   CONSTRAINT     c   ALTER TABLE ONLY public.pelanggan
    ADD CONSTRAINT pelanggan_no_telepon_key UNIQUE (no_telepon);
 L   ALTER TABLE ONLY public.pelanggan DROP CONSTRAINT pelanggan_no_telepon_key;
       public            postgres    false    215            c           2606    25769    pelanggan pelanggan_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.pelanggan
    ADD CONSTRAINT pelanggan_pkey PRIMARY KEY (id_pelanggan);
 B   ALTER TABLE ONLY public.pelanggan DROP CONSTRAINT pelanggan_pkey;
       public            postgres    false    215            e           2606    25776    produk produk_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.produk
    ADD CONSTRAINT produk_pkey PRIMARY KEY (id_produk);
 <   ALTER TABLE ONLY public.produk DROP CONSTRAINT produk_pkey;
       public            postgres    false    216            g           2606    25783    transaksi transaksi_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.transaksi
    ADD CONSTRAINT transaksi_pkey PRIMARY KEY (id_transaksi);
 B   ALTER TABLE ONLY public.transaksi DROP CONSTRAINT transaksi_pkey;
       public            postgres    false    217            k           2620    25807 !   detail_transaksi trg_update_stock    TRIGGER     }   CREATE TRIGGER trg_update_stock AFTER INSERT ON public.detail_transaksi FOR EACH ROW EXECUTE FUNCTION public.update_stock();
 :   DROP TRIGGER trg_update_stock ON public.detail_transaksi;
       public          postgres    false    218    219            i           2606    25801 0   detail_transaksi detail_transaksi_id_produk_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.detail_transaksi
    ADD CONSTRAINT detail_transaksi_id_produk_fkey FOREIGN KEY (id_produk) REFERENCES public.produk(id_produk);
 Z   ALTER TABLE ONLY public.detail_transaksi DROP CONSTRAINT detail_transaksi_id_produk_fkey;
       public          postgres    false    216    4709    218            j           2606    25796 3   detail_transaksi detail_transaksi_id_transaksi_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.detail_transaksi
    ADD CONSTRAINT detail_transaksi_id_transaksi_fkey FOREIGN KEY (id_transaksi) REFERENCES public.transaksi(id_transaksi);
 ]   ALTER TABLE ONLY public.detail_transaksi DROP CONSTRAINT detail_transaksi_id_transaksi_fkey;
       public          postgres    false    217    218    4711            h           2606    25784    transaksi fk_pelanggan    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaksi
    ADD CONSTRAINT fk_pelanggan FOREIGN KEY (id_pelanggan) REFERENCES public.pelanggan(id_pelanggan);
 @   ALTER TABLE ONLY public.transaksi DROP CONSTRAINT fk_pelanggan;
       public          postgres    false    215    4707    217            �   0   x�s1�0�4�4200�4\. !��1X�,f���SY� 9�G      �   G   x�5ȡ�0@�?L%�� �
���D����|�d�ȁ��aF9�rU8��ST�fΊFuh���.�?���      �   T   x�5̹�0A�����D� ���f9u7�P��F���zr��FRXHɉ'�ɅZɍ7�Ƀ:Ɋ+����e�>?W)��f      �   N   x�N����Q04���.�OJ-�5202Q02�21�22R�t�t1��1�42500�
ƭ�����ވ�ǈ��>F��� ��     