package com.liceu.notemanagment.services;


import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;

import java.util.List;

public interface SharedNoteService {
    public List<SharedNote> getAll();
    public List<SharedNote> getSharedNoteWithMe(long userid);
    public List<SharedNote> getSharedNotes(long userid);
    public boolean shareNote(long noteid, String [] usernames);
    public boolean deleteShareNote(long sharedNoteId);
}
