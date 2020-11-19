package com.liceu.sromerom.services;

import com.liceu.sromerom.daos.*;
import com.liceu.sromerom.model.Note;
import com.liceu.sromerom.model.User;
import com.liceu.sromerom.utils.Filter;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteServiceImpl implements NoteService {
    private final int LIMIT = 10;

    @Override
    public List<Note> getAll() {
        NoteDao nd = new NoteDaoImpl();
        return nd.getAllNotes();
    }

    @Override
    public List<Note> getNotesFromUser(long userid, int offset) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getAllNotesFromUser(userid, LIMIT, offset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Note> getCreatedNotes(long id, int offset) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getCreatedNotesFromUser(id, LIMIT, offset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getAllNotesLength(long id) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getNotesLengthFromUser(id) + nd.getSharedNotesWithMeLength(id);
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

        String initDateParsed = "";
        String endDateParsed = "";
        if (!initDate.equals("") && !endDate.equals("") && initDate != null && endDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime initDateTime = LocalDateTime.parse(initDate);
            LocalDateTime endDataTime = LocalDateTime.parse(endDate);
            initDateParsed = formatter.format(initDateTime);
            endDateParsed = formatter.format(endDataTime);
            System.out.println(initDateParsed);
            System.out.println(endDateParsed);
            //System.out.println(endDataTime);
        }
        try {
            if (type == null || type.equals("")) {
                System.out.println("filtramos todo...");
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByTitle")) {
                    //return nd.filterByTitle(userid, title, LIMIT, offset);
                    return nd.filterTypeOfNoteByTitle(userid, title, LIMIT, offset);
                } else if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByDate")) {
                    //return nd.filterByDate(userid, initDate, endDate, LIMIT, offset);
                    return nd.filterTypeOfNoteByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                } else if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterAll")) {
                    //return nd.filterAll(userid, title, initDate, endDate, LIMIT, offset);
                    return nd.filterAllTypeOfNote(userid, title, initDateParsed, endDateParsed, LIMIT, offset);
                }
            } else {
                System.out.println("filtramos solo lo seleccionado...");
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByTitle")) {
                    if (type.equals("compartides")) {
                        System.out.println("Compartides by title!!");
                        return nd.filterSharedNotesWithMeByTitle(userid, title, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        return nd.filterSharedNotesByTitle(userid, title, LIMIT, offset);
                    } else {
                        return nd.filterCreatedNotesByTitle(userid, title, LIMIT, offset);
                    }
                }
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByDate")) {
                    if (type.equals("compartides")) {
                        return nd.filterSharedNotesWithMeByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        return nd.filterSharedNotesByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    } else {
                        return nd.filterCreatedNotesByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    }
                }
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterAll")) {
                    if (type.equals("compartides")) {
                        return nd.filterAllSharedNotesWithMe(userid, title, initDateParsed, endDateParsed, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        return nd.filterAllSharedNotes(userid, title, initDateParsed, endDateParsed, LIMIT, offset);
                    } else {
                        return nd.filterAllCreatedNotes(userid, title, initDateParsed, endDateParsed, LIMIT, offset);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    @Override
    public Note getNoteById(long userid, long noteid) {
        NoteDao nd = new NoteDaoImpl();
        try {
            if (nd.isOwnerNote(userid, noteid) || nd.sharedNoteExists(userid, noteid)) {
                return nd.getNoteById(noteid);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getParsedBodyNote(String body) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(body);
        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).sanitizeUrls(true).build();
        return renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
    }

    @Override
    public boolean addNote(long userid, String title, String body) {
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
            nd.create(new Note(0, ud.getUserById(userid), title, body, date, date));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //public Note(long idnote, User user, String title, String body, Date creationDate, Date lastModification) {
        //nd.create(new Note(0, iduser, title, body, formatter.format(date), formatter.format(date)));
    }

    @Override
    public boolean editNote(long userid, long noteid, String title, String body) {
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

        try {
            Note noteToUpdate = nd.getNoteById(noteid);
            if (nd.isOwnerNote(userid, noteid)) {
                nd.update(new Note(noteid, noteToUpdate.getUser(), title, body, noteToUpdate.getCreationDate(), lastModificationDate));
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteNote(long userid, long noteid) {
        NoteDao nd = new NoteDaoImpl();

        try {
            if (nd.isOwnerNote(userid, noteid)) {
                nd.delete(noteid);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public long getSharedNoteId(long noteid) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getSharedNoteId(noteid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Note> getSharedNoteWithMe(long userid, int offset) {
        NoteDao nd = new NoteDaoImpl();
        try {
            System.out.println(nd.getSharedNotesWithMe(userid, LIMIT, offset));
            return nd.getSharedNotesWithMe(userid, LIMIT, offset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Note> getSharedNotes(long userid, int offset) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getSharedNotes(userid, LIMIT, offset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getLengthSharedNoteWithMe(long userid) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getSharedNotesWithMeLength(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public long getLengthSharedNotes(long userid) {
        NoteDao nd = new NoteDaoImpl();
        try {
            return nd.getSharedNotesLength(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean shareNote(long userWhoShares, long noteid, String[] usernames) {
        NoteDao nd = new NoteDaoImpl();
        UserDao ud = new UserDaoImpl();

        try {
            Note noteForShare = nd.getNoteById(noteid);

            List<User> users = new ArrayList<>();
            if (noteForShare != null) {
                for (String username : usernames) {
                    long userid = ud.getUserIdByUsername(username);
                    System.out.println(nd.sharedNoteExists(userid, noteid));
                    if (!nd.sharedNoteExists(userid, noteid)) {
                        User user = ud.getUserById(userid);
                        users.add(user);
                    }
                    //sharedNotes.add(new SharedNote(0, noteForShare, ud.getUserById(userid)));
                }

                if (users.size() != 0) {
                    nd.createShare(noteForShare, users);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteShareNote(long userid, long noteid, long sharedNoteId) {
        NoteDao nd = new NoteDaoImpl();
        try {
            List<Note> sharedNotes = nd.getSharedNotes(userid, 50, 0);
            List<Note> sharedWithMe = nd.getSharedNotesWithMe(userid, 50, 0);
            boolean canDelete = false;

            for (Note n : sharedWithMe) {
                System.out.println(n.getIdnote() + " =? " + noteid);
                if (n.getIdnote() == noteid) {
                    System.out.println("Eliminamos nota que nos han compartido");
                    canDelete = true;
                }
            }

            System.out.println("can delete? " + canDelete);
            if (canDelete) {
                nd.deleteShare(sharedNoteId);
                return true;
            }


            for (Note n : sharedNotes) {
                if (n.getUser().getIduser() == userid && n.getIdnote() == noteid) {
                    System.out.println("Eliminamos nota que hemos compartido");
                    canDelete = true;
                }
            }

            if (canDelete) {
                nd.deleteShare(sharedNoteId);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private boolean checkHTMLTags(String body) {
        Pattern pattern = Pattern.compile("<.+?>");
        Matcher matcher = pattern.matcher(body);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        } else {
            return false;
        }
    }
}
