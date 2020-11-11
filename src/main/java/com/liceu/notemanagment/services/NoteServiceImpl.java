package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.NoteDao;
import com.liceu.notemanagment.daos.NoteDaoImpl;
import com.liceu.notemanagment.daos.UserDao;
import com.liceu.notemanagment.daos.UserDaoImpl;
import com.liceu.notemanagment.model.Note;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NoteServiceImpl implements NoteService {
    @Override
    public List<Note> getAll() {
        NoteDao nd = new NoteDaoImpl();
        return nd.getAllNotes();
    }

    @Override
    public List<Note> getNotesFromUser(long id) {
        NoteDao nd = new NoteDaoImpl();
        return nd.getAllNotesFromUser(id);
    }

    @Override
    public Note getNoteById(long id) {
        NoteDao nd = new NoteDaoImpl();
        return nd.getNoteById(id);
    }

    @Override
    public String getTitleById(long noteid) {
        NoteDao nd = new NoteDaoImpl();
        return nd.getNoteById(noteid).getTitle();
    }

    @Override
    public String getBodyById(long noteid) {
        NoteDao nd = new NoteDaoImpl();
        return nd.getNoteById(noteid).getBody();
    }

    @Override
    public boolean addNote(long iduser, String title, String body) {
        //ES CORRECTE EMPRAR ALTRES DAOS EN UN SERVICE QUE ES DE NOTE?
        NoteDao nd = new NoteDaoImpl();
        UserDao ud = new UserDaoImpl();


        //2020-11-10 12:46:03
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String date = formatter.parse(formatter.format(new Date()));

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = myDateObj.format(myFormatObj);

        try {
            nd.create(new Note(0, ud.getUserById(iduser), title, body, date, date));
        } catch (Exception e) {
            return false;
        }


        //public Note(long idnote, User user, String title, String body, Date creationDate, Date lastModification) {
        //nd.create(new Note(0, iduser, title, body, formatter.format(date), formatter.format(date)));
        return true;
    }

    @Override
    public boolean editNote(long idnote, String title, String body) {
        NoteDao nd = new NoteDaoImpl();
        UserDao ud = new UserDaoImpl();
        //2020-11-10 12:46:03
        //With SimpleDateFormat
        //String pattern = "yyyy-MM-dd HH:mm:ss";
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "ES"));
        //String lastModificationDate = simpleDateFormat.format(new Date());

        //With LocalDateTime
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String lastModificationDate = myDateObj.format(myFormatObj);
        Note noteToUpdate = nd.getNoteById(idnote);

        try {
            nd.update(new Note(idnote, noteToUpdate.getUser(), title, body, noteToUpdate.getCreationDate(), lastModificationDate));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteNote(long idnote) {
        NoteDao nd = new NoteDaoImpl();
        try {
            nd.delete(idnote);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
