package com.liceu.notemanagment.services;


import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;

import java.util.List;

public interface SharedNoteService {
    public List<SharedNote> getAll();
    public List<Note> getSharedNotes(long userid);
    public boolean shareNote(long noteid, String [] usernames);
}
