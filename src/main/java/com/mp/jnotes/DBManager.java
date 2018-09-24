package com.mp.jnotes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static Connection conn;

    private DBManager() {
    }

    public static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }

    public Connection getConnection() throws SQLException {
        return conn = DriverManager.getConnection("jdbc:sqlite:notes.db");
    }

    public List<Nota> getAll() throws SQLException {
        conn = DBManager.getInstance().getConnection();
        List<Nota> list = new ArrayList<>();
        try (PreparedStatement cstmt = conn.prepareStatement("SELECT * FROM notes")) {
            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    Nota n = new Nota();
                    n.setId(rs.getInt("id"));
                    n.setTitolo(rs.getString("titolo"));
                    n.setGruppo(rs.getString("gruppo"));
                    n.setTesto(rs.getString("testo"));
                    n.setAggiunta(rs.getString("aggiunta"));
                    n.setModifica(rs.getString("modifica"));
                    list.add(n);
                }
            }
        }
        return list;
    }

    public List<Nota> getGruppi() throws SQLException {
        conn = DBManager.getInstance().getConnection();
        List<Nota> list = new ArrayList<>();
        try (PreparedStatement cstmt = conn.prepareStatement("SELECT * FROM notes GROUP BY gruppo ORDER BY gruppo")) {
            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    Nota n = new Nota();
                    n.setGruppo(rs.getString("gruppo"));
                    list.add(n);
                }
            }
        }
        return list;
    }

    public List<Nota> getByGruppo(String gruppo) throws SQLException {
        conn = DBManager.getInstance().getConnection();
        List<Nota> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM notes WHERE gruppo = ? ORDER BY titolo")) {
            pstmt.setString(1, gruppo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Nota n = new Nota();
                    n.setId(rs.getInt("id"));
                    n.setTitolo(rs.getString("titolo"));
                    n.setGruppo(rs.getString("gruppo"));
                    n.setTesto(rs.getString("testo"));
                    n.setAggiunta(rs.getString("aggiunta"));
                    n.setModifica(rs.getString("modifica"));
                    list.add(n);
                }
            }
        }
        return list;
    }

    public void addNota(Nota nota) throws SQLException {
        conn = DBManager.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO notes VALUES(?,?,?,?,?,?)")) {
            pstmt.setInt(1, nota.getId());
            pstmt.setString(2, nota.getTitolo());
            pstmt.setString(3, nota.getGruppo());
            pstmt.setString(4, nota.getTesto());
            pstmt.setString(5, nota.getAggiunta());
            pstmt.setString(6, nota.getModifica());
            pstmt.executeUpdate();
        }
    }

    public void upNota(Nota nota) throws SQLException {
        conn = DBManager.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE notes SET titolo = ?, gruppo = ?, testo = ?, modifica = ? WHERE id = ?")) {
            pstmt.setString(1, nota.getTitolo());
            pstmt.setString(2, nota.getGruppo());
            pstmt.setString(3, nota.getTesto());
            pstmt.setString(4, nota.getModifica());
            pstmt.setInt(5, nota.getId());
            pstmt.executeUpdate();
        }
    }

    public void removeNota(Nota nota) throws SQLException {
        conn = DBManager.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM notes WHERE id = ?")) {
            pstmt.setInt(1, nota.getId());
            pstmt.executeUpdate();
        }
    }

    public void removeGruppo(Nota nota) throws SQLException {
        conn = DBManager.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM notes WHERE gruppo = ?")) {
            pstmt.setString(1, nota.getGruppo());
            pstmt.executeUpdate();
        }
    }

}
