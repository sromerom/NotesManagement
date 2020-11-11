package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.SharedNoteDao;
import com.liceu.notemanagment.daos.SharedNoteDaoImpl;
import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;
import java.util.List;

public class SharedNoteServiceImpl implements SharedNoteService {
    @Override
    public List<SharedNote> getAll() {
        SharedNoteDao snd = new SharedNoteDaoImpl();
        return snd.getAllSharedNotes();
    }

    @Override
    public List<Note> getSharedNotes(long userid) {
        return null;
    }
}
