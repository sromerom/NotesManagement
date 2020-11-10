package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.NoteDao;
import com.liceu.notemanagment.daos.NoteDaoImpl;
import com.liceu.notemanagment.daos.UserDao;
import com.liceu.notemanagment.daos.UserDaoImpl;
import com.liceu.notemanagment.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    public boolean addNote(long iduser, String title, String body) {
        //ES CORRECTE EMPRAR ELS DOS DAOS EN UN SERVICE QUE ES DE NOTE?
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
        return false;
    }

    @Override
    public boolean deleteNote(String title, String body) {
        return false;
    }
}
