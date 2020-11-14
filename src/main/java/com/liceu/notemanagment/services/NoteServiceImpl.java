package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.*;
import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.SharedNote;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NoteServiceImpl implements NoteService {
    @Override
    public List<Note> getAll() {
        NoteDao nd = new NoteDaoImpl();
        return nd.getAllNotes();
    }

    @Override
    public List<Note> getNotesFromUser(long id, int offset) {
        NoteDao nd = new NoteDaoImpl();
        SharedNoteDao sns = new SharedNoteDaoImpl();

        try {
            //long notesLength = nd.getNotesLengthFromUser(id);
            //long sharedNotesLength = sns.getSharedNotesLengthFromUser(id);
            int parsedOffset = offset;
            //int limitCreated = 5;
            //int limitShared = 5;
            int limit = 5;
            if (offset != 0) {

            }

            int actualLengthCreatedNotes = nd.getAllNotesFromUser(id, limit, parsedOffset).size();
            int actualLengthSharedNotes = sns.getSharedNotesWithMe(id, limit, parsedOffset).size();

            System.out.println("createdNotes: " + actualLengthCreatedNotes);
            System.out.println("sharedNotes: " + actualLengthSharedNotes);
            /*
            if (actualLengthCreatedNotes - actualLengthSharedNotes != 0 && actualLengthCreatedNotes + actualLengthSharedNotes >= 5) {
                int diff = actualLengthCreatedNotes - actualLengthSharedNotes;
                if (diff > 0) {
                    limitCreated = limitCreated + (diff);
                }
                if (diff < 0) {
                    limitShared = limitShared + (diff);
                }
            }

             */
            /*
            if (offset != 0) {
                parsedOffset = parsedOffset - 5;
                if (offset > sharedNotesLength) {
                    limit = (int) (parsedOffset + (offset - sharedNotesLength));

                } else if (offset > notesLength) {
                    limit = (int) (parsedOffset + (offset - notesLength));
                }
            }
             */
            System.out.println("offset: " + parsedOffset);
            //System.out.println("limitCreated: " + limitCreated);
            //System.out.println("limitShared: " + limitShared);


            List<Note> createdNotes = nd.getAllNotesFromUser(id, limit, offset);
            List<SharedNote> sharedNotes = sns.getSharedNotesWithMe(id, limit, offset);
            List<Note> allNotes = new ArrayList<>();

            for (Note n : createdNotes) {
                allNotes.add(n);
            }

            for (SharedNote sn : sharedNotes) {
                allNotes.add(sn.getNote());
            }

            //return nd.getAllNotesFromUser(id, offset);
            //System.out.println(allNotes);
            return allNotes;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Note> getCreatedNotes(long id, int offset) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getAllNotesFromUser(id, 5, offset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getAllNotesLength(long id) {
        NoteDao nd = new NoteDaoImpl();
        SharedNoteDao sns = new SharedNoteDaoImpl();
        try {
            System.out.println("Tamaño propias: " + nd.getNotesLengthFromUser(id));
            System.out.println("Tamaño shared: " + sns.getSharedNotesWithMeLength(id));
            return nd.getNotesLengthFromUser(id) + sns.getSharedNotesWithMeLength(id);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public long getCreatedNotesLength(long id) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getNotesLengthFromUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean checkFilter(String title, String initDate, String endDate) {
        if (title != null && initDate != null && endDate != null) {

            if (!title.equals("") && !initDate.equals("") && !endDate.equals("")) {
                return true;
            }

            if (!title.equals("") && initDate.equals("") && endDate.equals("")) {
                return true;
            }

            if (title.equals("") && !initDate.equals("") && !endDate.equals("")) {
                return true;
            }

        }
        return false;
    }


    @Override
    public List<Note> filter(long userid, String type, String title, String initDate, String endDate, int offset) {
        NoteDao nd = new NoteDaoImpl();
        SharedNoteDao snd = new SharedNoteDaoImpl();
        List<Note> createdNotes = new ArrayList<>();
        List<SharedNote> sharedNotes = new ArrayList<>();
        List<Note> allNotes = new ArrayList<>();
        try {
            if (type == null || type.equals("")) {
                System.out.println("filtramos todo...");
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByTitle")) {
                    //return nd.filterByTitle(userid, title);
                    createdNotes = nd.filterByTitle(userid, title, 5, offset);
                    sharedNotes = snd.filterSharedNotesWithMeByTitle(userid, title, 5, offset);
                } else if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByDate")) {
                    createdNotes = nd.filterByDate(userid, initDate + " 00:00:00", endDate + " 23:59:59", 5, offset);
                    sharedNotes = snd.filterSharedNotesWithMeByDate(userid, initDate, endDate, 5, offset);
                    //return nd.filterByDate(userid, initDate + " 00:00:00", endDate + " 23:59:59");
                } else if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterAll")) {
                    createdNotes = nd.filterAll(userid, title, initDate, endDate, 5, offset);
                    sharedNotes = snd.filterSharedNotesWithMeAll(userid, title, initDate, endDate, 5, offset);
                    //return nd.filterAll(userid, title, initDate, endDate);
                }
            } else {
                System.out.println("filtramos solo lo seleccionado...");
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByTitle")) {
                    if (!type.equals("propies")) {
                        System.out.println("filter sharedNotes by title");
                        sharedNotes = snd.filterSharedNotesWithMeByTitle(userid, title, 5, offset);
                    } else {
                        System.out.println("filter createdNotes by title");
                        createdNotes = nd.filterByTitle(userid, title, 5, offset);
                    }
                }

                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByDate")) {
                    if (!type.equals("propies")) {
                        System.out.println("filter sharedNotes by date");
                        sharedNotes = snd.filterSharedNotesWithMeByDate(userid, initDate, endDate, 5, offset);
                    } else {
                        System.out.println("filter createdNotes by date");
                        createdNotes = nd.filterByDate(userid, initDate + " 00:00:00", endDate + " 23:59:59", 5, offset);
                    }
                }

                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterAll") && !type.equals("propies")) {

                    if (!type.equals("propies")) {
                        System.out.println("filter sharedNotes by all");
                        sharedNotes = snd.filterSharedNotesWithMeByDate(userid, initDate, endDate, 5, offset);
                    } else {
                        System.out.println("filter createdNotes by all");
                        sharedNotes = snd.filterSharedNotesWithMeAll(userid, title, initDate, endDate, 5, offset);
                    }
                }
            }

            for (Note n : createdNotes) {
                allNotes.add(n);
            }

            for (SharedNote sn : sharedNotes) {
                allNotes.add(sn.getNote());
            }

        } catch (Exception e) {
            return null;
        }
        return allNotes;
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
