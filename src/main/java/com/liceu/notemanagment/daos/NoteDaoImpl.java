package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl implements NoteDao {
    @Override
    public List<Note> getAllNotes() {
        List<Note> result = new ArrayList<>();
        try {
            //UserDao userDao = new UserDaoImpl();

            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, user_id, email, username, password  FROM note INNER JOIN user ON user.user_id = note.user_iduser");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long idnote = rs.getLong(1);
                String title = rs.getString(3);
                String body = rs.getString(4);
                Date creationDate = rs.getDate(5);
                Date lastModificationDate = rs.getDate(6);
                long userid = rs.getLong(7);
                String email = rs.getString(8);
                String username = rs.getString(9);
                String password = rs.getString(10);
                Note note = new Note(idnote, new User(userid, email, username, password), title, body, creationDate, lastModificationDate);
                result.add(note);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Note getNoteById(long id) {
        Note note = null;
        try {
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM note WHERE note_id =" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long idnote = rs.getLong(1);
                String title = rs.getString(3);
                String body = rs.getString(4);
                Date creationDate = rs.getDate(5);
                Date lastModificationDate = rs.getDate(6);
                long userid = rs.getLong(7);
                String email = rs.getString(8);
                String username = rs.getString(9);
                String password = rs.getString(10);
                note = new Note(idnote, new User(userid, email, username, password), title, body, creationDate, lastModificationDate);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;
    }

    @Override
    public boolean create(Note note) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) VALUES (?,?,?,?,?)");
            ps.setLong(1, note.getUser().getIduser());
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getBody());
            ps.setDate(4, note.getCreationDate());
            ps.setDate(5, note.getLastModification());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Note note) {
        return false;
    }

    @Override
    public boolean delete(Note note) {
        return false;
    }
}
