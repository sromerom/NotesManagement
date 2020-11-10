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
    public List<Note> getAllNotesFromUser(long iduser) {
        List<Note> result = new ArrayList<>();

        for (Note n : this.notes) {
            if (n.getUser().getIduser() == iduser) {
                result.add(new Note(n.getIdnote(), n.getUser(), n.getTitle(), n.getBody(), n.getCreationDate(), n.getLastModification()));
            }
        }

        return result;
        /*
        try {
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, user_id, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user.user_id =" + iduser);
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
                result.add(note);
            }


            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
    }

    @Override
    public Note getNoteById(long id) {

        for (Note n : this.notes) {
            if (n.getIdnote() == id) {
                return n;
            }
        }

        return null;
        /*
        Note note = null;
        try {
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM note WHERE note_id =" + id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long idnote = rs.getLong(1);
                String title = rs.getString(3);
                String body = rs.getString(4);
                String creationDate = rs.getString(5);
                String lastModificationDate = rs.getString(6);
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
         */
    }

    @Override
    public boolean create(Note note) {

        System.out.println("Comprobando si la info de la nota es buena............");
        System.out.println(note);
        try {
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
