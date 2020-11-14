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
    public long getSharedNoteId(long noteid, long userid) {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        try {
            return snd.getSharedNoteId(noteid, userid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<SharedNote> getSharedNoteWithMe(long userid) {
        //haredNoteDao snd = new SharedNoteDaoImpl();
        //return snd.getSharedNotesWithMe(userid);
        return null;
    }

    @Override
    public List<SharedNote> getSharedNotes(long userid) {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        return snd.getSharedNotes(userid);
    }

    @Override
    public List<SharedNote> filter(long userid, String title, String initDate, String endDate) {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        try {
            if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByTitle")) {
                return snd.filterSharedNotesWithMeByTitle(userid, title);
            } else if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByDate")) {
                return snd.filterSharedNotesWithMeByDate(userid, initDate + " 00:00:00", endDate + " 23:59:59");
            } else if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterAll")) {
                return snd.filterSharedNotesWithMeAll(userid, title, initDate + " 00:00:00", endDate + " 23:59:59");
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean shareNote(long noteid, String[] usernames) {
        try {
            SharedNoteDao snd = new SharedNoteDaoImpl();
            NoteDao nd = new NoteDaoImpl();
            UserDao ud = new UserDaoImpl();

            Note noteForShare = nd.getNoteById(noteid);
            List<SharedNote> sharedNotes = new ArrayList<>();

            for (String username : usernames) {
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
