package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.*;
import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;

import java.util.ArrayList;
import java.util.List;

public class SharedNoteServiceImpl implements SharedNoteService {
    @Override
    public List<SharedNote> getAll() {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        return snd.getAllSharedNotes();
    }

    @Override
    public List<SharedNote> getSharedNoteWithMe(long userid) {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        return snd.getSharedNotesWithMe(userid);
    }

    @Override
    public List<SharedNote> getSharedNotes(long userid) {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        return snd.getSharedNotes(userid);
    }

    @Override
    public boolean shareNote(long noteid, String [] usernames) {
        try {
            SharedNoteDao snd = new SharedNoteDaoImpl();
            NoteDao nd = new NoteDaoImpl();
            UserDao ud = new UserDaoImpl();

            Note noteForShare = nd.getNoteById(noteid);
            List<SharedNote> sharedNotes = new ArrayList<>();

            for (String username: usernames) {
                long userid = ud.getUserIdByUsername(username);
                sharedNotes.add(new SharedNote(0, noteForShare, ud.getUserById(userid)));
            }
            snd.create(sharedNotes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteShareNote(long sharedNoteId) {
        try {
            SharedNoteDao snd = new SharedNoteDaoImpl();
            snd.delete(sharedNoteId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
