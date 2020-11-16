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
            return snd.getSharedNoteId(noteid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Note> getSharedNoteWithMe(long userid, int offset) {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        List<Note> result = new ArrayList<>();
        try {
            List<SharedNote> sharedNotes = snd.getSharedNotesWithMe(userid, 5, offset);
            for (SharedNote sn : sharedNotes) {
                result.add(sn.getNote());
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getLengthSharedNoteWithMe(long userid) {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        try {
            return snd.getSharedNotesWithMeLength(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Note> getSharedNotes(long userid, int offset) {
        //return snd.getSharedNotes(userid);
        SharedNoteDao snd = new SharedNoteDaoImpl();
        List<Note> result = new ArrayList<>();
        try {
            List<SharedNote> ownerSharedNotes = snd.getSharedNotes(userid, 5, offset);
            System.out.println(ownerSharedNotes);
            for (SharedNote sn : ownerSharedNotes) {
                result.add(sn.getNote());
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getLengthSharedNotes(long userid) {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        try {
            return snd.getSharedNotesLength(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean shareNote(long useridOwner, long noteid, String[] usernames) {
        try {
            SharedNoteDao snd = new SharedNoteDaoImpl();
            NoteDao nd = new NoteDaoImpl();
            UserDao ud = new UserDaoImpl();

            Note noteForShare = nd.getNoteById(useridOwner, noteid);
            List<SharedNote> sharedNotes = new ArrayList<>();

            if (noteForShare != null) {
                for (String username : usernames) {
                    long userid = ud.getUserIdByUsername(username);
                    sharedNotes.add(new SharedNote(0, noteForShare, ud.getUserById(userid)));
                }
                snd.create(sharedNotes);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteShareNote(long userid, long noteid, long sharedNoteId) {
        try {
            //1 36
            SharedNoteDao snd = new SharedNoteDaoImpl();
            NoteDao nd = new NoteDaoImpl();
            List<SharedNote> sharedNotes = snd.getSharedNotes(userid, 50, 0);
            List<SharedNote> sharedWithMe = snd.getSharedNotesWithMe(userid, 50, 0);
            boolean canDelete = false;

            for (SharedNote sn : sharedWithMe) {
                if (sn.getUser().getIduser() == userid && sn.getIdShareNote() == sharedNoteId) {
                    System.out.println(sn.getNote().getIdnote() + " =? " + noteid);
                    System.out.println("Eliminamos nota que nos han compartido...");
                    canDelete = true;
                    break;
                } else {
                    canDelete = false;
                }
            }

            if (canDelete) {
                snd.delete(sharedNoteId);
                return true;
            }

            for (SharedNote sn : sharedNotes) {
                if (sn.getNote().getIdnote() == noteid && sn.getIdShareNote() == sharedNoteId) {
                    System.out.println("Eliminamos nota que hemos compartido...");
                    canDelete = true;
                    break;
                } else {
                    canDelete = false;
                }
            }

            if (canDelete) {
                snd.delete(sharedNoteId);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
