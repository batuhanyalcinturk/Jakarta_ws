package com.graysan.jakarta.repository;

import com.graysan.jakarta.Constants;
import com.graysan.jakarta.model.*;

import java.sql.*;
import java.util.ArrayList;

public class OgrenciRepository {
    public ArrayList<Ogrenci> getAll() throws SQLException
    {
        ArrayList<Ogrenci> list = new ArrayList<>();
        Connection con = Constants.getConnection();
        Statement stmnt = con.createStatement();
        ResultSet result = stmnt.executeQuery("select * from \"public\".\"OGRENCI\" order by \"ID\" asc");
        while (result.next())
        {
            long id = result.getLong("ID");
            String name = result.getString("NAME");
            long number = result.getLong("OGR_NUMBER");
            long year = result.getLong("YEAR");
            list.add(new Ogrenci(id, name, number,year));
        }
        result.close();
        stmnt.close();
        con.close();
        return list;
    }

    public boolean save(Ogrenci ogrenci) throws SQLException
    {
        boolean result = false;
        Connection con = Constants.getConnection();
        String sql = "INSERT INTO public.\"OGRENCI\"(\"NAME\", \"OGR_NUMBER\", \"YEAR\") VALUES (?, ?, ?)";
        PreparedStatement stmnt = con.prepareStatement(sql);
        stmnt.setString(1, ogrenci.getName());
        stmnt.setLong(2, ogrenci.getNumber());
        stmnt.setLong(3, ogrenci.getYear());
        result = stmnt.executeUpdate() == 1;
        stmnt.close();
        con.close();
        return result;
    }

    public boolean deleteByID(long id) throws SQLException
    {
        Connection con = Constants.getConnection();
        String sql = "DELETE FROM public.\"OGRENCI\" WHERE \"ID\" = ?";
        PreparedStatement stmnt = con.prepareStatement(sql);
        stmnt.setLong(1, id);
        boolean result = stmnt.executeUpdate() == 1;
        stmnt.close();
        con.close();
        return result;
    }

    public Ogrenci getByID(long id) throws SQLException
    {
        Ogrenci ogrenci = null;
        Connection con = Constants.getConnection();
        String sql = "select * from \"public\".\"OGRENCI\" where \"ID\" = ?";
        PreparedStatement stmnt = con.prepareStatement(sql);
        stmnt.setLong(1, id);
        ResultSet result = stmnt.executeQuery();
        while (result.next())
        {
            ogrenci = new Ogrenci(result.getLong("ID"), result.getString("NAME"), result.getLong("OGR_NUMBER"), result.getLong("YEAR"));
        }
        result.close();
        stmnt.close();
        con.close();
        return ogrenci;
    }
}
