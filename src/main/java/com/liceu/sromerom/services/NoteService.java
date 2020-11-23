package com.liceu.sromerom.services;

import com.liceu.sromerom.model.Note;
import com.liceu.sromerom.utils.RenderableNote;

import java.util.List;

public interface NoteService {
    public List<RenderableNote> getNotesFromUser(long userid, int offset);
    //public Map<Note, Boolean> getNotesFromUser(long userid, int offset);

    public List<RenderableNote> getCreatedNotes(long userid, int offset);

    public long getAllNotesLength(long id);

    public long getCreatedNotesLength(long id);

    public boolean checkFilter(String search, String initDate, String endDate);

    public List<RenderableNote> filter(long userid, String type, String search, String initDate, String endDate, int offset);

    public Note getNoteById(long userid, long noteid);

    //public String getTitleById(long noteid);
    //public String getBodyById(long noteid);
    public String getParsedBodyToHTML(String body);

    public String getParsedBodyEscapeText(String body);
    public boolean addNote(long userid, String title, String body);

    public boolean editNote(long userid, long idnote, String title, String body);

    public boolean deleteNote(long userid, long idnote);

    //Shared Notes Services

    public long getSharedNoteId(long noteid);

    public List<RenderableNote> getSharedNoteWithMe(long userid, int offset);

    public List<RenderableNote> getSharedNotes(long userid, int offset);

    public long getLengthSharedNoteWithMe(long userid);

    public long getLengthSharedNotes(long userid);

    public boolean shareNote(long userid, long noteid, String[] usernames);

    public boolean deleteShareNote(long userid, long noteid, String[] usernames);

    public boolean deleteAllShareNote(long userid, long noteid);

}
