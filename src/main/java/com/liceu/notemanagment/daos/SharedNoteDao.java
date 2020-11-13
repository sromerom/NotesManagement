package com.liceu.notemanagment.daos;


import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;

import java.util.List;

public interface SharedNoteDao {
    public List<SharedNote> getAllSharedNotes();

    public List<SharedNote> getSharedNotesWithMe(long userid);

    public List<SharedNote> getSharedNotes(long userid);

    public List<SharedNote> filterSharedNotesWithMeByTitle(long userid, String titol) throws Exception;

    public List<SharedNote> filterSharedNotesWithMeByDate(long userid, String initDate, String endDate) throws Exception;

    List<SharedNote> filterSharedNotesWithMeAll(long userid, String title, String initDate, String endDate) throws Exception;

    public void create(List<SharedNote> sharedNotes) throws Exception;

    public void delete(long sharedNoteId) throws Exception;
}
