package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<Note> getAllNotesFromUser(long userid, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        //PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, user_id, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, user_id, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE note.user_iduser = ? OR note.note_id IN (SELECT sharedNote.note_id FROM sharedNote INNER JOIN note ON sharedNote.note_id = note.note_id WHERE sharedNote.user_id = ?) ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        ps.setLong(2, userid);
        ps.setInt(3, limit);
        ps.setInt(4, offset);
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
    }

    @Override
    public List<Note> getCreatedNotesFromUser(long userid, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON note.user_iduser = user.user_id WHERE user_iduser = ? ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        ps.setInt(2, limit);
        ps.setInt(3, offset);
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
        ps.close();
        return result;
    }

    @Override
    public long getNotesLengthFromUser(long userid) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(note_id) FROM note WHERE user_iduser = ?");
        ps.setLong(1, userid);
        ResultSet rs = ps.executeQuery();
        long totalNotes = rs.getInt(1);
        ps.close();
        return totalNotes;
    }

    @Override
    public List<Note> filterByTitle(long userid, String titol, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? AND title LIKE ? ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        ps.setString(2, "%" + titol + "%");
        ps.setInt(3, limit);
        ps.setInt(4, offset);
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
        ps.close();
        return result;
    }

    @Override
    public List<Note> filterByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? AND creationDate > ? AND lastModificationDate < ? ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        //2020-11-12 00:00:00
        //2020-11-12 23:59:59
        ps.setString(2, initDate);
        ps.setString(3, endDate);
        ps.setInt(4, limit);
        ps.setInt(5, offset);
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
        ps.close();
        return result;
    }

    @Override
    public List<Note> filterAll(long userid, String title, String initDate, String endDate, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? AND title LIKE ? AND creationDate > ? AND lastModificationDate < ? ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        //2020-11-12 00:00:00
        //2020-11-12 23:59:59
        ps.setString(2, "%" + title + "%");
        ps.setString(3, initDate);
        ps.setString(4, endDate);
        ps.setInt(5, limit);
        ps.setInt(6, offset);
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
        ps.close();
        return result;
    }

    @Override
    public List<Note> filterByTitleAllTypeNotes(long userid, String titol, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE note.user_iduser = ?  AND title LIKE ? OR note.note_id IN (SELECT sharedNote.note_id FROM sharedNote INNER JOIN note ON sharedNote.note_id = note.note_id WHERE sharedNote.user_id = ?) ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        ps.setString(2, "%" + titol + "%");
        ps.setLong(3, userid);
        ps.setInt(4, limit);
        ps.setInt(5, offset);
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
        ps.close();
        return result;
    }

    @Override
    public List<Note> filterByDateAllTypeNotes(long userid, String initDate, String endDate, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE note.user_iduser = ? AND creationDate > ? AND lastModificationDate < ? OR note.note_id IN (SELECT sharedNote.note_id FROM sharedNote INNER JOIN note ON sharedNote.note_id = note.note_id WHERE sharedNote.user_id = ?) ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        ps.setString(2, initDate);
        ps.setString(3, endDate);
        ps.setLong(4, userid);
        ps.setInt(5, limit);
        ps.setInt(6, offset);
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
        ps.close();
        return result;
    }

    @Override
    public List<Note> filterAllAllTypeNotes(long userid, String title, String initDate, String endDate, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, title, body, creationDate, lastModificationDate, user_id, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE note.user_iduser = ? AND title LIKE ? AND creationDate > ? AND lastModificationDate < ? OR note.note_id IN (SELECT sharedNote.note_id FROM sharedNote INNER JOIN note ON sharedNote.note_id = note.note_id WHERE sharedNote.user_id = ?) ORDER BY note.note_id DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        ps.setString(2, "%" + title + "%");
        ps.setString(3, initDate);
        ps.setString(4, endDate);
        ps.setLong(5, userid);
        ps.setInt(6, limit);
        ps.setInt(7, offset);
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
        ps.close();
        return result;
    }

    @Override
    public Note getNoteById(long userid, long noteid) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT title, body, creationDate, lastModificationDate, email, username, password FROM note INNER JOIN user ON user.user_id = note.user_iduser WHERE user_iduser = ? AND note_id = ?");
        ps.setLong(1, userid);
        ps.setLong(2, noteid);
        ResultSet rs = ps.executeQuery();
        String titleActual = rs.getString(1);
        String body = rs.getString(2);
        String creationDate = rs.getString(3);
        String lastModificationDate = rs.getString(4);
        String email = rs.getString(5);
        String username = rs.getString(6);
        String password = rs.getString(7);
        rs.close();
        ps.close();
        return new Note(noteid, new User(userid, email, username, password), titleActual, body, creationDate, lastModificationDate);
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

    // Shared Notes operations
    @Override
    public List<Note> getSharedNotesWithMe(long userid, int limit, int offset) throws SQLException {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        //note_id, title, body, creationDate, lastModificationDate, user_id, email, username, password
        PreparedStatement ps = conn.prepareStatement("SELECT sharedNote.note_id, title, body, creationDate, lastModificationDate, user_iduser, email, username, password FROM sharedNote INNER JOIN note ON sharedNote.note_id = note.note_id INNER JOIN user ON note.user_iduser = user.user_id WHERE sharedNote.user_id = ? ORDER BY sharedNote.shared_note DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        ps.setInt(2, limit);
        ps.setInt(3, offset);
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
        return result;
    }

    @Override
    public List<Note> getSharedNotes(long userid, int limit, int offset) throws Exception {
        List<Note> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement(" SELECT sharedNote.note_id, title, body, creationDate, lastModificationDate, user_iduser, email, username, password FROM sharedNote INNER JOIN note ON sharedNote.note_id = note.note_id INNER JOIN user ON note.user_iduser = user.user_id WHERE note.user_iduser = ? ORDER BY sharedNote.shared_note DESC LIMIT ? OFFSET ?");
        ps.setLong(1, userid);
        ps.setInt(2, limit);
        ps.setInt(3, offset);
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
        return result;
    }

    @Override
    public long[] getSharedNoteById(long shareNoteId) throws Exception{
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT note_id, user_id FROM sharedNote WHERE shared_note = ?");
        ps.setLong(1, shareNoteId);
        ResultSet rs = ps.executeQuery();
        long noteid = rs.getLong(1);
        long userid = rs.getLong(2);
        ps.close();
        return new long[]{noteid, userid};
    }

    @Override
    public long getSharedNoteId(long noteid) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT shared_note FROM sharedNote WHERE note_id = ?");
        ps.setLong(1, noteid);
        ResultSet rs = ps.executeQuery();
        long sharedNoteId = rs.getLong(1);
        ps.close();
        return sharedNoteId;
    }

    @Override
    public long getSharedNotesWithMeLength(long userid) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(shared_note) FROM sharedNote WHERE user_id = ?");
        ps.setLong(1, userid);
        ResultSet rs = ps.executeQuery();
        long totalNotes = rs.getLong(1);
        ps.close();
        return totalNotes;
    }

    @Override
    public long getSharedNotesLength(long userid) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement(" SELECT COUNT(user_iduser) FROM sharedNote INNER JOIN note ON note.note_id = sharedNote.note_id WHERE user_iduser = ?;");
        ps.setLong(1, userid);
        ResultSet rs = ps.executeQuery();
        long totalNotes = rs.getLong(1);
        ps.close();
        return totalNotes;
    }

    @Override
    public List<Note> filterSharedNotesWithMeByTitle(long userid, String titol, int limit, int offset) throws Exception {
        return null;
    }

    @Override
    public List<Note> filterSharedNotesWithMeByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception {
        return null;
    }

    @Override
    public List<Note> filterSharedNotesWithMeAll(long userid, String title, String initDate, String endDate, int limit, int offset) throws Exception {
        return null;
    }

    @Override
    public void createShare(Note noteForShare, List<User> users) throws Exception {
        Connection c = Database.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO sharedNote (note_id, user_id) VALUES (?,?)");
        for (User user : users) {
            ps.setLong(1, noteForShare.getIdnote());
            ps.setLong(2, user.getIduser());
            //ps.setLong(2, sn.getUser().getIduser());
            ps.execute();
        }
        ps.close();
    }

    @Override
    public void deleteShare(long sharedNoteId) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM sharedNote WHERE shared_note = ?");
        ps.setLong(1, sharedNoteId);
        ps.execute();
        ps.close();
    }


}
