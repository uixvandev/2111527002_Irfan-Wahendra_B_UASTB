package tb;

import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//class penjualan, inherit class App, implement interface bbm
public class penjualan extends App implements bbm {

    static Connection con;
    Scanner i = new Scanner(System.in);
    // method date
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    float beli;
    float belipm = 0;
    float belipt = 0;
    float belipx = 0;

    int harga, no, ino;
    String admin, tgl, url, sql;

    // contructor class Penjualan
    public penjualan() {

    }

    @Override
    public int noFaktur(int iharga) throws SQLException {
        try {
            url = "jdbc:mysql://localhost:8889/2111527002_irfanwahendra_TB_UAS_PBO?serverTimezone=Asia/Jakarta";
            con = DriverManager.getConnection(url, "root", "root");
            String sql = "SELECT MAX(No) FROM penjualan_bbm";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                int max = result.getInt(1);
                if (max > 0) {
                    no = max + 1;
                } else {
                    no = 1;
                }

            }

        } catch (Exception e) {
            System.out.println("Penomoran faktur gagal");
            System.err.println(e.getMessage());
        }
        return no;
    }

    public String admin(String iadm) {
        // method string
        admin = iadm.toUpperCase();
        return admin;
    }

    public int harga(int iharga) {
        harga = iharga;
        return harga;
    }

    public String tanggal(int iharga) {
        // method date;
        tgl = myDateObj.format(myFormatObj);
        return tgl;
    }

    // Proses ulang Matematika
    // premium = 6500/L
    public float jualPremium(int iharga) {
        beli = ((float) iharga / 6500);
        return beli;
    }

    // pertalit = 8000/L
    public float jualPertalite(int iharga) {
        beli = ((float) iharga / 8000);
        return beli;
    }

    // pertamax = 10000/L
    public float jualPertamax(int iharga) {
        beli = ((float) iharga / 10000);
        return beli;
    }

    // insert or create data
    public void tambahPenjualan(String iadm) throws SQLException {
        // exception
        try {
            url = "jdbc:mysql://localhost:8889/2111527002_irfanwahendra_TB_UAS_PBO?serverTimezone=Asia/Jakarta";
            con = DriverManager.getConnection(url, "root", "root");
            Statement statement = con.createStatement();
            System.out.print("\n---TAMBAH DATA PENJUALAN BBM---");
            System.out.println("\n1. Premium\n2. Pertalite\n3. Pertamax");
            System.out.print("Jenis: ");
            int jenis = i.nextInt();
            System.out.print("Harga : ");
            int iharga = i.nextInt();
            if (jenis == 1) {
                belipm = jualPremium(iharga);
                belipt = 0;
                belipx = 0;
                sql = "INSERT INTO penjualan_bbm (No, Tanggal, Admin, Kuantitas_Premium, Kuantitas_Pertalite, Kuantitas_Pertamax, Total_Harga) VALUES ('"
                        + noFaktur(iharga) + "','" + tanggal(iharga) + "','" + admin(iadm) + "','" + belipm + "','"
                        + belipt + "','" + belipx + "','" + harga(iharga) + "')";
                statement.executeUpdate(sql);
                System.out.println("Data dengan nomor faktur " + no + " berhasil ditambahkan!");
            } else if (jenis == 2) {
                belipt = jualPertalite(iharga);
                belipm = 0;
                belipx = 0;
                sql = "INSERT INTO penjualan_bbm (No, Tanggal, Admin, Kuantitas_Premium, Kuantitas_Pertalite, Kuantitas_Pertamax, Total_Harga) VALUES ('"
                        + noFaktur(iharga) + "','" + tanggal(iharga) + "','" + admin(iadm) + "','" + belipm + "','"
                        + belipt + "','" + belipx + "','" + harga(iharga) + "')";
                statement.executeUpdate(sql);
                System.out.println("Data dengan nomor faktur " + no + " berhasil ditambahkan!");
            } else if (jenis == 3) {
                belipx = jualPertamax(iharga);
                belipm = 0;
                belipt = 0;
                sql = "INSERT INTO penjualan_bbm (No, Tanggal, Admin, Kuantitas_Premium, Kuantitas_Pertalite, Kuantitas_Pertamax, Total_Harga) VALUES ('"
                        + noFaktur(iharga) + "','" + tanggal(iharga) + "','" + admin(iadm) + "','" + belipm + "','"
                        + belipt + "','" + belipx + "','" + harga(iharga) + "')";
                statement.executeUpdate(sql);
                System.out.println("Data dengan nomor faktur " + no + " berhasil ditambahkan!");

            } else {
                System.out.println("Jenis BBM tidak tersedia");
            }
        } catch (SQLException e) {
            System.out.println("Data gagal ditambahkan!");
            System.err.println(e.getMessage());
        }
    }

    // select or view data
    public void riwayatPenjualan(String iadm) throws SQLException {
        // exception
        try {
            url = "jdbc:mysql://localhost:8889/2111527002_irfanwahendra_TB_UAS_PBO?serverTimezone=Asia/Jakarta";
            con = DriverManager.getConnection(url, "root", "root");
            String sqlc = "SELECT COUNT(*) AS total FROM penjualan_bbm";
            Statement statementc = con.createStatement();
            ResultSet resultc = statementc.executeQuery(sqlc);

            if (resultc.next()) {
                int jml = resultc.getInt("total");
                if (jml > 0) {
                    String sql = "SELECT * FROM penjualan_bbm";
                    Statement statement = con.createStatement();
                    ResultSet result = statement.executeQuery(sql);
                    System.out.println("\n\n---RIWAYAT TRANSAKSI PENJUALAN---");
                    while (result.next()) {
                        System.out.print("\nNo Faktur  : ");
                        System.out.print(result.getInt("No"));
                        System.out.print("\nAdmin      : ");
                        System.out.print(result.getString("Admin"));
                        System.out.print("\nTanggal    : ");
                        System.out.print(result.getString("Tanggal"));
                        System.out.print("\nJenis      : ");
                        if (result.getDouble("Kuantitas_Premium") != 0) {
                            System.out.print("Premium");
                        } else if (result.getDouble("Kuantitas_Pertalite") != 0) {
                            System.out.print("Pertalite");
                        } else if (result.getDouble("Kuantitas_Pertamax") != 0) {
                            System.out.print("Pertamax");
                        }
                        System.out.print("\nJumlah     : ");
                        if (result.getDouble("Kuantitas_Premium") != 0) {
                            System.out.print(result.getDouble("Kuantitas_Premium") + " Liter");
                        } else if (result.getDouble("Kuantitas_Pertalite") != 0) {
                            System.out.print(result.getDouble("Kuantitas_Pertalite") + " Liter");

                        } else if (result.getDouble("Kuantitas_Pertamax") != 0) {
                            System.out.print(result.getDouble("Kuantitas_Pertamax") + " Liter");
                        }
                        System.out.print("\nHarga      : ");
                        System.out.print(result.getDouble("Total_Harga"));
                        System.out.println("\n");
                    }
                } else {
                    System.out.println("Database kosong");
                }
            }
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam menampilkan data");
            System.err.println(e.getMessage());
        }
    }

    // edit or update data
    public void editPenjualan(String iadm) throws SQLException {
        // exception
        try {
            riwayatPenjualan(iadm);
            System.out.print("Masukkan nomor faktur yang akan diedit : ");
            no = i.nextInt();
            String sql = "SELECT * FROM penjualan_bbm WHERE NO = " + no;
            url = "jdbc:mysql://localhost:8889/2111527002_irfanwahendra_TB_UAS_PBO?serverTimezone=Asia/Jakarta";
            con = DriverManager.getConnection(url, "root", "root");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                System.out.println("\n\nNama sebelumnya : " + result.getString("ADMIN"));
                System.out.print("Nama terbaru : ");
                String inm = i.next();
                sql = "UPDATE penjualan_bbm SET ADMIN='" + inm.toUpperCase() + "' WHERE NO='" + no + "'";
                if (statement.executeUpdate(sql) > 0) {
                    System.out.println("Berhasil memperbaharui nama admin pada faktur nomor " + no + " menjadi " + inm);
                }
            }
            statement.close();
            i.nextLine();
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
    }

    // delete data
    public void hapusPenjualan(String iadm) throws SQLException {
        // exception
        try {
            url = "jdbc:mysql://localhost:8889/2111527002_irfanwahendra_TB_UAS_PBO?serverTimezone=Asia/Jakarta";
            con = DriverManager.getConnection(url, "root", "root");
            String sql;
            System.out.println("---HAPUS DATA PENJUALAN---");
            riwayatPenjualan(iadm);
            System.out.println("\n1. Hapus nomor tertentu\n2. Hapus semua");
            System.out.print("Kategori Hapus: ");
            no = i.nextInt();
            if (no == 1) {
                System.out.print("\nNomor faktur yang akan dihapus : ");
                no = i.nextInt();
                sql = "DELETE FROM penjualan_bbm WHERE No = " + no;
                Statement statement = con.createStatement();
                if (statement.executeUpdate(sql) > 0) {
                    System.out.println("Berhasil menghapus data penjualan dengan nomor " + no);
                }
            } else if (no == 2) {
                sql = "TRUNCATE TABLE penjualan_bbm";
                Statement statement = con.createStatement();
                if (statement.executeUpdate(sql) > 0) {
                    System.out.println("Berhasil menghapus keseluruhan data!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan dalam menghapus data barang");
            System.err.println(e.getMessage());
        }
    }

    // seacrh data
    public void cariPenjualan(String iadm) throws SQLException {
        try {
            System.out.println("---CARI DATA PENJUALAN---");
            riwayatPenjualan(iadm);
            System.out.println("\n1. Nomor faktur\n2. Nama admin");
            System.out.print("Berdasarkan: ");
            int ic = i.nextInt();
            if (ic == 1) {
                searchNo(iadm);
            } else if (ic == 2) {
                searchAdmin(iadm);
            } else {
                System.out.println("Data hanya dapat dicari berdasarkan nomor faktur atau nama admin!");
            }

        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }
    }

    public void searchNo(String iadm) throws SQLException {
        try {
            clean.clearScreen();
            System.out.print("\nMasukkan nomor faktur yang dicari: ");
            no = i.nextInt();
            url = "jdbc:mysql://localhost:8889/2111527002_irfanwahendra_TB_UAS_PBO?serverTimezone=Asia/Jakarta";
            String sql = "SELECT * FROM penjualan_bbm WHERE No LIKE '%" + no + "%'";
            con = DriverManager.getConnection(url, "root", "root");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.print("\nNo Faktur  : ");
                System.out.print(result.getInt("No"));
                System.out.print("\nAdmin      : ");
                System.out.print(result.getString("Admin"));
                System.out.print("\nTanggal    : ");
                System.out.print(result.getString("Tanggal"));
                System.out.print("\nJenis      : ");
                if (result.getDouble("Kuantitas_Premium") != 0) {
                    System.out.print("Premium");

                } else if (result.getDouble("Kuantitas_Pertalite") != 0) {
                    System.out.print("Pertalite");

                } else if (result.getDouble("Kuantitas_Pertamax") != 0) {
                    System.out.print("Pertamax");

                }
                System.out.print("\nJumlah     : ");
                if (result.getDouble("Kuantitas_Premium") != 0) {
                    System.out.print(result.getDouble("Kuantitas_Premium") + " Liter");

                } else if (result.getDouble("Kuantitas_Pertalite") != 0) {
                    System.out.print(result.getDouble("Kuantitas_Pertalite") + " Liter");

                } else if (result.getDouble("Kuantitas_Pertamax") != 0) {
                    System.out.print(result.getDouble("Kuantitas_Pertamax") + " Liter");

                }
                System.out.print("\nHarga      : ");
                System.out.print(result.getInt("Total_Harga"));
                System.out.println("");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }
    }

    public void searchAdmin(String iadm) throws SQLException {
        try {
            clean.clearScreen();
            i.nextLine();
            System.out.print("\nMasukkan nama admin yang dicari: ");
            String inm = i.nextLine();
            url = "jdbc:mysql://localhost:8889/2111527002_irfanwahendra_TB_UAS_PBO?serverTimezone=Asia/Jakarta";
            String sql = "SELECT * FROM penjualan_bbm WHERE Admin LIKE '%" + inm + "%'";
            con = DriverManager.getConnection(url, "root", "root");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.print("\nNo Faktur  : ");
                System.out.print(result.getInt("No"));
                System.out.print("\nAdmin      : ");
                System.out.print(result.getString("Admin"));
                System.out.print("\nTanggal    : ");
                System.out.print(result.getString("Tanggal"));
                System.out.print("\nJenis      : ");
                if (result.getDouble("Kuantitas_Premium") != 0) {
                    System.out.print("Premium");

                } else if (result.getDouble("Kuantitas_Pertalite") != 0) {
                    System.out.print("Pertalite");

                } else if (result.getDouble("Kuantitas_Pertamax") != 0) {
                    System.out.print("Pertamax");

                }
                System.out.print("\nJumlah     : ");
                if (result.getDouble("Kuantitas_Premium") != 0) {
                    System.out.print(result.getDouble("Kuantitas_Premium") + " Liter");

                } else if (result.getDouble("Kuantitas_Pertalite") != 0) {
                    System.out.print(result.getDouble("Kuantitas_Pertalite") + " Liter");

                } else if (result.getDouble("Kuantitas_Pertamax") != 0) {
                    System.out.print(result.getDouble("Kuantitas_Pertamax") + " Liter");

                }
                System.out.print("\nHarga      : ");
                System.out.print(result.getInt("Total_Harga"));
                System.out.println("");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }
    }

}
