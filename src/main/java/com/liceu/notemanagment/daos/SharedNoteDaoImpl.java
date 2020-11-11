package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;
import com.liceu.notemanagment.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SharedNoteDaoImpl implements SharedNoteDao {
    private List<SharedNote> sharedNotes = new ArrayList<>();

    public SharedNoteDaoImpl() {
        try {
            Connection c = Database.getConnection();
            PreparedStatement all = c.prepareStatement("SELECT s.user_id, email, username, password, s.note_id, user_iduser, title, body, creationDate, lastModificationDate FROM sharedNote AS s INNER JOIN user ON user.user_id = s.user_id INNER JOIN note ON note.note_id = s.note_id");
            PreparedStatement ownerNote = c.prepareStatement("SELECT * FROM user WHERE user_id = ?");
            ResultSet rs = all.executeQuery();
            User ownerSharedNote = null;
            while (rs.next()) {
                long userid = rs.getLong(1);
                String email = rs.getString(2);
                String username = rs.getString(3);
                String password = rs.getString(4);
                long noteid = rs.getLong(5);
                long user_iduser = rs.getLong(6);
                String title = rs.getString(7);
                String body = rs.getString(8);
                String creationDate = rs.getString(9);
                String lastModificationDate = rs.getString(10);

                ownerNote.setLong(1, user_iduser);
                ResultSet rs2 = ownerNote.executeQuery();
                while (rs2.next()) {
                    long ownerUserid = rs2.getLong(1);
                    String ownerEmail = rs2.getString(2);
                    String ownerUsername = rs2.getString(3);
                    String ownerPassword = rs2.getString(4);
                    ownerSharedNote = new User(ownerUserid, ownerEmail, ownerUsername, ownerPassword);
                }
                SharedNote sn = new SharedNote(new Note(noteid, ownerSharedNote, title, body, creationDate, lastModificationDate), new User(userid, email, username, password));
                sharedNotes.add(sn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SharedNote> getAllSharedNotes() {
        List<SharedNote> result = new ArrayList<>();
        for (SharedNote s : this.sharedNotes) {
            result.add(new SharedNote(new Note(s.getNote().getIdnote(), s.getNote().getUser(), s.getNote().getTitle(), s.getNote().getBody(), s.getNote().getCreationDate(), s.getNote().getLastModification()), new User(s.getUser().getIduser(), s.getUser().getEmail(), s.getUser().getUsername(), s.getUser().getPassword())));
        }
        return result;
    }

    @Override
    public List<Note> getSharedNotesById(long userid) {
        List<Note> result = new ArrayList<>();

        for (SharedNote sn : this.sharedNotes) {
            if (sn.getUser().getIduser() == userid) {
                result.add(sn.getNote());
            }

            //if (n.getUser().getIduser() == iduser) {
              //  result.add(new Note(n.getIdnote(), n.getUser(), n.getTitle(), n.getBody(), n.getCreationDate(), n.getLastModification()));
            //}
        }
        return result;
        //return null;
    }

    @Override
    public void create(List<SharedNote> sharedNotes) throws Exception {

    }
}
