package com.liceu.notemanagment.services;
import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;

import java.util.List;

public interface SharedNoteService {
    public List<SharedNote> getAll();
    public long getSharedNoteId(long noteid, long userid);
    public List<Note> getSharedNoteWithMe(long userid, int offset);
    public long getLengthSharedNoteWithMe(long userid);
    public List<Note> getSharedNotes(long userid, int offset);
    public long getLengthSharedNotes(long userid);
    //public List<SharedNote> filter(long userid, String title, String initDate, String endDate, int offset);
    public boolean shareNote(long userid, long noteid, String [] usernames);
    public boolean deleteShareNote(long sharedNoteId);
}
