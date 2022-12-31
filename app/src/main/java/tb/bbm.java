package tb;

import java.sql.SQLException;

//interface bbm
public interface bbm {
    public int noFaktur(int iharga) throws SQLException;
    public String admin(String iadm);
    public int harga(int iharga);
    public String tanggal(int iharga);
    public float jualPremium(int iharga);
    public float jualPertalite(int iharga);
    public float jualPertamax(int iharga);
}
