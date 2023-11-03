package com.graysan.jakarta.repository;

import com.graysan.jakarta.Constants;
import com.graysan.jakarta.model.DersOgrenci;

import java.sql.*;
import java.util.ArrayList;

public class DersOgrenciRepository {
    public ArrayList<DersOgrenci> getAll() throws SQLException
    {
        ArrayList<DersOgrenci> list = new ArrayList<>();
        Connection con = Constants.getConnection();
        Statement stmnt = con.createStatement();
        ResultSet result = stmnt.executeQuery("SELECT * FROM public.\"DERS_OGRENCI\" order by \"ID\" asc");
        while (result.next())
        {
            long id = result.getLong("ID");
            long dersId = result.getLong("DERS_ID");
            long ogrId = result.getLong("OGR_ID");
            long devamsizlik = result.getLong("DEVAMSIZLIK");
            long note = result.getLong("NOTE");
            list.add(new DersOgrenci(id, dersId, ogrId, devamsizlik, note));
        }
        result.close();
        stmnt.close();
        con.close();
        return list;
    }

    public boolean save(DersOgrenci dersOgrenci) throws SQLException
    {
        boolean result = false;
        Connection con = Constants.getConnection();
        String sql = "INSERT INTO public.\"DERS_OGRENCI\"(\"DERS_ID\", \"OGR_ID\", \"DEVAMSIZLIK\", \"NOTE\") VALUES (?, ?, ?, ?)";
        PreparedStatement stmnt = con.prepareStatement(sql);
        stmnt.setLong(1, dersOgrenci.getDERS_ID());
        stmnt.setLong(2, dersOgrenci.getOGR_ID());
        stmnt.setLong(3, dersOgrenci.getDevamsizlik());
        stmnt.setLong(4, dersOgrenci.getNote());
        result = stmnt.executeUpdate() == 1;
        stmnt.close();
        con.close();
        return result;
    }

    public boolean deleteByID(long id) throws SQLException
    {
        Connection con = Constants.getConnection();
        String sql = "DELETE FROM public.\"DERS_OGRENCI\" WHERE \"ID\" = ?";
        PreparedStatement stmnt = con.prepareStatement(sql);
        stmnt.setLong(1, id);
        boolean result = stmnt.executeUpdate() == 1;
        stmnt.close();
        con.close();
        return result;
    }

    public DersOgrenci getByID(long id) throws SQLException
    {
        DersOgrenci dersOgrenci = null;
        Connection con = Constants.getConnection();
        String sql = "select * from \"public\".\"DERS_OGRENCI\" where \"ID\" = ?";
        PreparedStatement stmnt = con.prepareStatement(sql);
        stmnt.setLong(1, id);
        ResultSet result = stmnt.executeQuery();
        while (result.next())
        {
            dersOgrenci = new DersOgrenci(result.getLong("ID"), result.getLong("DERS_ID"), result.getLong("OGR_ID"), result.getLong("DEVAMSIZLIK"), result.getLong("NOTE"));
        }
        result.close();
        stmnt.close();
        con.close();
        return dersOgrenci;
    }
}
