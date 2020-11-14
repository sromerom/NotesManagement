package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;
import com.liceu.notemanagment.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SharedNoteDaoImpl implements SharedNoteDao {
    private List<SharedNote> sharedNotes = new ArrayList<>();

    public SharedNoteDaoImpl() {
        try {
            Connection c = Database.getConnection();
            PreparedStatement all = c.prepareStatement("SELECT shared_note, s.user_id, email, username, password, s.note_id, user_iduser, title, body, creationDate, lastModificationDate FROM sharedNote AS s INNER JOIN user ON user.user_id = s.user_id INNER JOIN note ON note.note_id = s.note_id");
            PreparedStatement ownerNote = c.prepareStatement("SELECT * FROM user WHERE user_id = ?");
            ResultSet rs = all.executeQuery();
            User ownerSharedNote = null;
            while (rs.next()) {
                long sharedNote = rs.getLong(1);
                long userid = rs.getLong(2);
                String email = rs.getString(3);
                String username = rs.getString(4);
                String password = rs.getString(5);
                long noteid = rs.getLong(6);
                long user_iduser = rs.getLong(7);
                String title = rs.getString(8);
                String body = rs.getString(9);
                String creationDate = rs.getString(10);
                String lastModificationDate = rs.getString(11);

                ownerNote.setLong(1, user_iduser);
                ResultSet rs2 = ownerNote.executeQuery();
                while (rs2.next()) {
                    long ownerUserid = rs2.getLong(1);
                    String ownerEmail = rs2.getString(2);
                    String ownerUsername = rs2.getString(3);
                    String ownerPassword = rs2.getString(4);
                    ownerSharedNote = new User(ownerUserid, ownerEmail, ownerUsername, ownerPassword);
                }
                SharedNote sn = new SharedNote(sharedNote, new Note(noteid, ownerSharedNote, title, body, creationDate, lastModificationDate), new User(userid, email, username, password));
                sharedNotes.add(sn);
            }
            all.close();
            ownerNote.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SharedNote> getAllSharedNotes() {
        List<SharedNote> result = new ArrayList<>();
        for (SharedNote s : this.sharedNotes) {
            result.add(new SharedNote(s.getIdShareNote(), new Note(s.getNote().getIdnote(), s.getNote().getUser(), s.getNote().getTitle(), s.getNote().getBody(), s.getNote().getCreationDate(), s.getNote().getLastModification()), new User(s.getUser().getIduser(), s.getUser().getEmail(), s.getUser().getUsername(), s.getUser().getPassword())));
        }
        return result;
    }

    @Override
    public List<SharedNote> getSharedNotesWithMe(long userid, int limit, int offset) throws SQLException {
        List<SharedNote> result = new ArrayList<>();
        Connection c = Database.getConnection();
        PreparedStatement all = c.prepareStatement("SELECT shared_note, s.user_id, email, username, password, s.note_id, user_iduser, title, body, creationDate, lastModificationDate FROM sharedNote AS s INNER JOIN user ON user.user_id = s.user_id INNER JOIN note ON note.note_id = s.note_id WHERE s.user_id = ? LIMIT ? OFFSET ?");
        all.setLong(1, userid);
        all.setInt(2, limit);
        all.setInt(3, offset);
        PreparedStatement ownerNote = c.prepareStatement("SELECT * FROM user WHERE user_id = ?");
        ResultSet rs = all.executeQuery();
        User ownerSharedNote = null;
        while (rs.next()) {
            long sharedNote = rs.getLong(1);
            long useridActual = rs.getLong(2);
            String email = rs.getString(3);
            String username = rs.getString(4);
            String password = rs.getString(5);
            long noteid = rs.getLong(6);
            long user_iduser = rs.getLong(7);
            String title = rs.getString(8);
            String body = rs.getString(9);
            String creationDate = rs.getString(10);
            String lastModificationDate = rs.getString(11);

            ownerNote.setLong(1, user_iduser);
            ResultSet rs2 = ownerNote.executeQuery();
            while (rs2.next()) {
                long ownerUserid = rs2.getLong(1);
                String ownerEmail = rs2.getString(2);
                String ownerUsername = rs2.getString(3);
                String ownerPassword = rs2.getString(4);
                ownerSharedNote = new User(ownerUserid, ownerEmail, ownerUsername, ownerPassword);
            }
            SharedNote sn = new SharedNote(sharedNote, new Note(noteid, ownerSharedNote, title, body, creationDate, lastModificationDate), new User(useridActual, email, username, password));
            result.add(sn);
        }
        all.close();
        ownerNote.close();
        return result;
        /*
        List<SharedNote> result = new ArrayList<>();

        for (SharedNote sn : this.sharedNotes) {
            if (sn.getUser().getIduser() == userid) {
                result.add(sn);
            }
        }
        return result;
         */
    }

    @Override
    public List<SharedNote> getSharedNotes(long userid, int limit, int offset) throws Exception {
        List<SharedNote> result = new ArrayList<>();
        Connection c = Database.getConnection();
        PreparedStatement all = c.prepareStatement("SELECT shared_note, s.user_id, email, username, password, s.note_id, user_iduser, title, body, creationDate, lastModificationDate FROM sharedNote AS s INNER JOIN user ON user.user_id = s.user_id INNER JOIN note ON note.note_id = s.note_id WHERE note.user_iduser = ? LIMIT ? OFFSET ?");
        all.setLong(1, userid);
        all.setInt(2, limit);
        all.setInt(3, offset);
        PreparedStatement ownerNote = c.prepareStatement("SELECT * FROM user WHERE user_id = ?");
        ResultSet rs = all.executeQuery();
        User ownerSharedNote = null;
        while (rs.next()) {
            long sharedNote = rs.getLong(1);
            long useridActual = rs.getLong(2);
            String email = rs.getString(3);
            String username = rs.getString(4);
            String password = rs.getString(5);
            long noteid = rs.getLong(6);
            long user_iduser = rs.getLong(7);
            String title = rs.getString(8);
            String body = rs.getString(9);
            String creationDate = rs.getString(10);
            String lastModificationDate = rs.getString(11);

            ownerNote.setLong(1, user_iduser);
            ResultSet rs2 = ownerNote.executeQuery();
            while (rs2.next()) {
                long ownerUserid = rs2.getLong(1);
                String ownerEmail = rs2.getString(2);
                String ownerUsername = rs2.getString(3);
                String ownerPassword = rs2.getString(4);
                ownerSharedNote = new User(ownerUserid, ownerEmail, ownerUsername, ownerPassword);
            }
            SharedNote sn = new SharedNote(sharedNote, new Note(noteid, ownerSharedNote, title, body, creationDate, lastModificationDate), new User(useridActual, email, username, password));
            result.add(sn);
        }
        all.close();
        ownerNote.close();
        return result;
        /*
        List<SharedNote> result = new ArrayList<>();

        for (SharedNote sn : this.sharedNotes) {
            if (sn.getNote().getUser().getIduser() == userid) {
                result.add(sn);
            }
        }
        return result;
         */
    }

    @Override
    public long getSharedNoteId(long noteid, long userid) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT shared_note FROM sharedNote WHERE note_id = ? AND user_id = ?");
        ps.setLong(1, noteid);
        ps.setLong(2, userid);
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
    public List<SharedNote> filterSharedNotesWithMeByTitle(long userid, String titol, int limit, int offset) throws Exception {
        List<SharedNote> result = new ArrayList<>();
        Connection c = Database.getConnection();
        System.out.println(userid + " " + titol);
        PreparedStatement all = c.prepareStatement("SELECT shared_note, s.user_id, email, username, password, s.note_id, user_iduser, title, body, creationDate, lastModificationDate FROM sharedNote AS s INNER JOIN user ON user.user_id = s.user_id INNER JOIN note ON note.note_id = s.note_id WHERE s.user_id = ? AND note.title LIKE ? LIMIT ? OFFSET ?");
        all.setLong(1, userid);
        all.setString(2, "%" + titol + "%");
        all.setInt(3, limit);
        all.setInt(4, offset);
        PreparedStatement ownerNote = c.prepareStatement("SELECT * FROM user WHERE user_id = ?");
        ResultSet rs = all.executeQuery();
        User ownerSharedNote = null;
        while (rs.next()) {
            long sharedNote = rs.getLong(1);
            long useridActual = rs.getLong(2);
            String email = rs.getString(3);
            String username = rs.getString(4);
            String password = rs.getString(5);
            long noteid = rs.getLong(6);
            long user_iduser = rs.getLong(7);
            String title = rs.getString(8);
            String body = rs.getString(9);
            String creationDate = rs.getString(10);
            String lastModificationDate = rs.getString(11);

            ownerNote.setLong(1, user_iduser);
            ResultSet rs2 = ownerNote.executeQuery();
            while (rs2.next()) {
                long ownerUserid = rs2.getLong(1);
                String ownerEmail = rs2.getString(2);
                String ownerUsername = rs2.getString(3);
                String ownerPassword = rs2.getString(4);
                ownerSharedNote = new User(ownerUserid, ownerEmail, ownerUsername, ownerPassword);
            }
            SharedNote sn = new SharedNote(sharedNote, new Note(noteid, ownerSharedNote, title, body, creationDate, lastModificationDate), new User(useridActual, email, username, password));
            result.add(sn);
        }
        all.close();
        ownerNote.close();
        return result;
    }

    @Override
    public List<SharedNote> filterSharedNotesWithMeByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception {
        List<SharedNote> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement all = conn.prepareStatement("SELECT shared_note, s.user_id, email, username, password, s.note_id, user_iduser, title, body, creationDate, lastModificationDate FROM sharedNote AS s INNER JOIN user ON user.user_id = s.user_id INNER JOIN note ON note.note_id = s.note_id WHERE s.user_id = ? AND creationDate > ? AND lastModificationDate < ? LIMIT ? OFFSET ?");
        all.setLong(1, userid);
        //2020-11-12 00:00:00
        //2020-11-12 23:59:59
        all.setString(2, initDate);
        all.setString(3, endDate);
        all.setInt(4, limit);
        all.setInt(5, offset);
        PreparedStatement ownerNote = conn.prepareStatement("SELECT * FROM user WHERE user_id = ?");
        ResultSet rs = all.executeQuery();
        User ownerSharedNote = null;
        while (rs.next()) {
            long sharedNote = rs.getLong(1);
            long useridActual = rs.getLong(2);
            String email = rs.getString(3);
            String username = rs.getString(4);
            String password = rs.getString(5);
            long noteid = rs.getLong(6);
            long user_iduser = rs.getLong(7);
            String title = rs.getString(8);
            String body = rs.getString(9);
            String creationDate = rs.getString(10);
            String lastModificationDate = rs.getString(11);

            ownerNote.setLong(1, user_iduser);
            ResultSet rs2 = ownerNote.executeQuery();
            while (rs2.next()) {
                long ownerUserid = rs2.getLong(1);
                String ownerEmail = rs2.getString(2);
                String ownerUsername = rs2.getString(3);
                String ownerPassword = rs2.getString(4);
                ownerSharedNote = new User(ownerUserid, ownerEmail, ownerUsername, ownerPassword);
            }
            SharedNote sn = new SharedNote(sharedNote, new Note(noteid, ownerSharedNote, title, body, creationDate, lastModificationDate), new User(useridActual, email, username, password));
            result.add(sn);
        }
        all.close();
        ownerNote.close();
        return result;
    }

    @Override
    public List<SharedNote> filterSharedNotesWithMeAll(long userid, String title, String initDate, String endDate, int limit, int offset) throws Exception {
        List<SharedNote> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement all = conn.prepareStatement("SELECT shared_note, s.user_id, email, username, password, s.note_id, user_iduser, title, body, creationDate, lastModificationDate FROM sharedNote AS s INNER JOIN user ON user.user_id = s.user_id INNER JOIN note ON note.note_id = s.note_id WHERE s.user_id = ? AND title LIKE ? AND creationDate > ? AND lastModificationDate < ? LIMIT ? OFFSET ?");
        all.setLong(1, userid);
        //2020-11-12 00:00:00
        //2020-11-12 23:59:59
        all.setString(2, "%" + title + "%");
        all.setString(3, initDate);
        all.setString(4, endDate);
        all.setInt(5, limit);
        all.setInt(6, offset);
        PreparedStatement ownerNote = conn.prepareStatement("SELECT * FROM user WHERE user_id = ?");
        ResultSet rs = all.executeQuery();
        User ownerSharedNote = null;
        while (rs.next()) {
            long sharedNote = rs.getLong(1);
            long useridActual = rs.getLong(2);
            String email = rs.getString(3);
            String username = rs.getString(4);
            String password = rs.getString(5);
            long noteid = rs.getLong(6);
            long user_iduser = rs.getLong(7);
            String titleNote = rs.getString(8);
            String body = rs.getString(9);
            String creationDate = rs.getString(10);
            String lastModificationDate = rs.getString(11);

            ownerNote.setLong(1, user_iduser);
            ResultSet rs2 = ownerNote.executeQuery();
            while (rs2.next()) {
                long ownerUserid = rs2.getLong(1);
                String ownerEmail = rs2.getString(2);
                String ownerUsername = rs2.getString(3);
                String ownerPassword = rs2.getString(4);
                ownerSharedNote = new User(ownerUserid, ownerEmail, ownerUsername, ownerPassword);
            }
            SharedNote sn = new SharedNote(sharedNote, new Note(noteid, ownerSharedNote, titleNote, body, creationDate, lastModificationDate), new User(useridActual, email, username, password));
            result.add(sn);
        }
        all.close();
        ownerNote.close();
        return result;
    }

    @Override
    public void create(List<SharedNote> sharedNotes) throws Exception {
        try {
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO sharedNote (note_id, user_id) VALUES (?,?)");

            System.out.println(sharedNotes);

            for (SharedNote sn : sharedNotes) {
                ps.setLong(1, sn.getNote().getIdnote());
                ps.setLong(2, sn.getUser().getIduser());
                ps.execute();
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long sharedNoteId) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM sharedNote WHERE shared_note = ?");
        ps.setLong(1, sharedNoteId);
        ps.execute();
        ps.close();
    }
}
