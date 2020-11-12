package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl implements NoteDao {

    List<Note> notes = new ArrayList<>();

    public NoteDaoImpl() {
        try {
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, user_id, email, username, password  FROM note INNER JOIN user ON user.user_id = note.user_iduser");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long noteid = rs.getLong(1);
                String title = rs.getString(2);
                String body = rs.getString(3);
                String creationDate = rs.getString(4);
                String lastModificationDate = rs.getString(5);
                long userid = rs.getLong(6);
                String email = rs.getString(7);
                String username = rs.getString(8);
                String password = rs.getString(9);

                //2020-11-10 12:46:03
                Note note = new Note(noteid, new User(userid, email, username, password), title, body, creationDate, lastModificationDate);
                this.notes.add(note);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> result = new ArrayList<>();
        for (Note n : this.notes) {
            result.add(new Note(n.getIdnote(), n.getUser(), n.getTitle(), n.getBody(), n.getCreationDate(), n.getLastModification()));
        }
        return result;
    }

    @Override
    public List<Note> getAllNotesFromUser(long userid, int offset) throws Exception{
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, user_id, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? LIMIT 10 OFFSET ?");
        ps.setLong(1, userid);
        ps.setInt(2, offset);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            long noteid = rs.getLong(1);
            String title = rs.getString(2);
            String body = rs.getString(3);
            String creationDate = rs.getString(4);
            String lastModificationDate = rs.getString(5);
            long useridNote = rs.getLong(6);
            String email = rs.getString(7);
            String username = rs.getString(8);
            String password = rs.getString(9);

            //2020-11-10 12:46:03
            Note note = new Note(noteid, new User(useridNote, email, username, password), title, body, creationDate, lastModificationDate);
            result.add(note);
        }
        ps.close();
        return result;
        /*
        List<Note> result = new ArrayList<>();

        for (Note n : this.notes) {
            if (n.getUser().getIduser() == userid) {
                result.add(new Note(n.getIdnote(), n.getUser(), n.getTitle(), n.getBody(), n.getCreationDate(), n.getLastModification()));
            }
        }
        return result;
         */
    }

    @Override
    public long getNotesLengthFromUser(long userid) throws Exception{
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(note_id) FROM note WHERE user_iduser = ?");
        ps.setLong(1, userid);
        ResultSet rs = ps.executeQuery();
        long totalNotes = rs.getInt(1);
        return totalNotes;
    }

    @Override
    public List<Note> filterByTitle(long userid, String titol) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? AND title LIKE ?");
        ps.setLong(1, userid);
        ps.setString(2, "%" + titol + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            long noteid = rs.getLong(1);
            String title = rs.getString(2);
            String body = rs.getString(3);
            String creationDate = rs.getString(4);
            String lastModificationDate = rs.getString(5);
            String email = rs.getString(6);
            String username = rs.getString(7);
            String password = rs.getString(8);

            //2020-11-10 12:46:03
            Note note = new Note(noteid, new User(userid, email, username, password), title, body, creationDate, lastModificationDate);
            System.out.println("Note: " + note);
            result.add(note);
        }

        return result;
    }

    @Override
    public List<Note> filterByDate(long userid, String initDate, String endDate) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? AND creationDate > ? AND lastModificationDate < ?");
        ps.setLong(1, userid);
        //2020-11-12 00:00:00
        //2020-11-12 23:59:59
        ps.setString(2, initDate);
        ps.setString(3, endDate);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            long noteid = rs.getLong(1);
            String title = rs.getString(2);
            String body = rs.getString(3);
            String creationDate = rs.getString(4);
            String lastModificationDate = rs.getString(5);
            String email = rs.getString(6);
            String username = rs.getString(7);
            String password = rs.getString(8);

            //2020-11-10 12:46:03
            Note note = new Note(noteid, new User(userid, email, username, password), title, body, creationDate, lastModificationDate);
            System.out.println("Note: " + note);
            result.add(note);
        }
        return result;
    }

    @Override
    public List<Note> filterAll(long userid, String title, String initDate, String endDate) throws Exception{
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? AND title = ? AND creationDate > ? AND lastModificationDate < ?");
        ps.setLong(1, userid);
        //2020-11-12 00:00:00
        //2020-11-12 23:59:59
        ps.setString(2, title);
        ps.setString(3, initDate);
        ps.setString(4, endDate);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            long noteid = rs.getLong(1);
            String titleActual = rs.getString(2);
            String body = rs.getString(3);
            String creationDate = rs.getString(4);
            String lastModificationDate = rs.getString(5);
            String email = rs.getString(6);
            String username = rs.getString(7);
            String password = rs.getString(8);

            //2020-11-10 12:46:03
            Note note = new Note(noteid, new User(userid, email, username, password), titleActual, body, creationDate, lastModificationDate);
            result.add(note);
        }
        return result;
    }

    @Override
    public Note getNoteById(long id) {

        for (Note n : this.notes) {
            if (n.getIdnote() == id) {
                return n;
            }
        }
        return null;
    }

    @Override
    public void create(Note note) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) VALUES (?,?,?,?,?)");
        ps.setLong(1, note.getUser().getIduser());
        ps.setString(2, note.getTitle());
        ps.setString(3, note.getBody());

        //2020-11-10 12:46:03

        ps.setString(4, note.getCreationDate());
        ps.setString(5, note.getLastModification());
        ps.execute();
        ps.close();

    }

    @Override
    public void update(Note note) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("UPDATE note SET title = ?, body = ?, lastModificationDate = ? WHERE note_id = ?");
        ps.setString(1, note.getTitle());
        ps.setString(2, note.getBody());


        ps.setString(3, note.getLastModification());
        ps.setLong(4, note.getIdnote());
        ps.execute();
        ps.close();
    }

    @Override
    public void delete(long idnote) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM note WHERE note_id = ?");
        ps.setLong(1, idnote);
        ps.execute();
        ps.close();
    }
}
