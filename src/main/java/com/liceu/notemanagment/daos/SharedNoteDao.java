package com.liceu.notemanagment.daos;


import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;

import java.util.List;

public interface SharedNoteDao {
    public List<SharedNote> getAllSharedNotes();
    public List<Note> getSharedNotesById(long userid);
    public void create(List<SharedNote> sharedNotes) throws Exception;
}
