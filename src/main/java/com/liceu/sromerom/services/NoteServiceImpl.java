package com.liceu.sromerom.services;

import com.liceu.sromerom.daos.*;
import com.liceu.sromerom.model.Note;
import com.liceu.sromerom.model.User;
import com.liceu.sromerom.utils.Filter;
import com.liceu.sromerom.utils.MarkdownUtil;
import com.liceu.sromerom.utils.RenderableNote;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteServiceImpl implements NoteService {
    private final int LIMIT = 10;

    @Override
    public List<RenderableNote> getNotesFromUser(long userid, int offset) {
        List<Note> allNotes;
        List<RenderableNote> renderableNotes = new ArrayList<>();
        NoteDao nd = new NoteDaoImpl();
        UserDao ud = new UserDaoImpl();
        try {
            List<Note> sharedNotes = nd.getSharedNotes(userid, LIMIT, offset);
            allNotes = nd.getAllNotesFromUser(userid, LIMIT, offset);
            for (Note allNote : allNotes) {
                //User sharedUser = null;
                List<User> sharedUsersFromNote = null;
                for (int j = 0; j < sharedNotes.size(); j++) {
                    if (sharedNotes.get(j).getNoteid() == allNote.getNoteid()) {
                        //sharedUser = sharedNotes.get(j).getUser();
                        sharedUsersFromNote = ud.getUsersFromSharedNote(sharedNotes.get(j).getNoteid());
                        sharedNotes.remove(j);
                        break;
                    }
                }
                //long noteid, User noteOwner, User sharedUser, String title, String body, String creationDate, String lastModification
                renderableNotes.add(new RenderableNote(allNote.getNoteid(), allNote.getUser(), sharedUsersFromNote, allNote.getTitle(), allNote.getBody(), allNote.getCreationDate(), allNote.getLastModification()));
            }
            return renderableNotes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RenderableNote> getCreatedNotes(long userid, int offset) {
        List<RenderableNote> renderableNotes = new ArrayList<>();
        NoteDao nd = new NoteDaoImpl();
        try {
            List<Note> createdNotes = nd.getCreatedNotesFromUser(userid, LIMIT, offset);
            for (Note n : createdNotes) {
                renderableNotes.add(new RenderableNote(n.getNoteid(), n.getUser(), null, n.getTitle(), n.getBody(), n.getCreationDate(), n.getLastModification()));
            }
            return renderableNotes;
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
    public boolean checkFilter(String search, String initDate, String endDate) {
        if (search != null && initDate != null && endDate != null) {
            if (!search.equals("") && !initDate.equals("") && !endDate.equals("")) return true;
            if (!search.equals("") && initDate.equals("") && endDate.equals("")) return true;
            return search.equals("") && !initDate.equals("") && !endDate.equals("");

        }
        return false;
    }


    @Override
    public List<RenderableNote> filter(long userid, String type, String search, String initDate, String endDate, int offset) {
        NoteDao nd = new NoteDaoImpl();
        UserDao ud = new UserDaoImpl();
        List<Note> notes = new ArrayList<>();
        List<RenderableNote> renderableNotes = new ArrayList<>();
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
                if (Filter.checkTypeFilter(search, initDate, endDate).equals("filterByTitle")) {
                    notes = nd.filterTypeOfNoteBySearch(userid, search, LIMIT, offset);
                    //return nd.filterByTitleAllTypeNotes(userid, title, LIMIT, offset);
                } else if (Filter.checkTypeFilter(search, initDate, endDate).equals("filterByDate")) {
                    notes = nd.filterTypeOfNoteByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    //return nd.filterByDateAllTypeNotes(userid, initDate, endDate, LIMIT, offset);
                } else if (Filter.checkTypeFilter(search, initDate, endDate).equals("filterAll")) {
                    notes = nd.filterAllTypeOfNote(userid, search, initDateParsed, endDateParsed, LIMIT, offset);
                    //return nd.filterAllAllTypeNotes(userid, title, initDate, endDate, LIMIT, offset);
                }


            } else {
                System.out.println("filtramos solo lo seleccionado...");
                if (Filter.checkTypeFilter(search, initDate, endDate).equals("filterByTitle")) {
                    if (type.equals("compartides")) {
                        notes = nd.filterSharedNotesWithMeBySearch(userid, search, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        notes = nd.filterSharedNotesBySearch(userid, search, LIMIT, offset);
                    } else {
                        notes = nd.filterCreatedNotesBySearch(userid, search, LIMIT, offset);
                    }
                }
                if (Filter.checkTypeFilter(search, initDate, endDate).equals("filterByDate")) {
                    if (type.equals("compartides")) {
                        notes = nd.filterSharedNotesWithMeByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        notes = nd.filterSharedNotesByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    } else {
                        notes = nd.filterCreatedNotesByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    }
                }
                if (Filter.checkTypeFilter(search, initDate, endDate).equals("filterAll")) {
                    if (type.equals("compartides")) {
                        notes = nd.filterAllSharedNotesWithMe(userid, search, initDateParsed, endDateParsed, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        notes = nd.filterAllSharedNotes(userid, search, initDateParsed, endDateParsed, LIMIT, offset);
                    } else {
                        notes = nd.filterAllCreatedNotes(userid, search, initDateParsed, endDateParsed, LIMIT, offset);
                    }
                }
            }

            List<Note> sharedNotes = nd.getSharedNotes(userid, LIMIT, offset);
            for (Note n : notes) {
                //User sharedUser = null;
                List<User> sharedUsersFromNote = null;
                for (int j = 0; j < sharedNotes.size(); j++) {
                    if (sharedNotes.get(j).getNoteid() == n.getNoteid()) {
                        //sharedUser = sharedNotes.get(j).getUser();
                        sharedUsersFromNote = ud.getUsersFromSharedNote(sharedNotes.get(j).getNoteid());
                        sharedNotes.remove(j);
                        break;
                    }
                }
                //long noteid, User noteOwner, User sharedUser, String title, String body, String creationDate, String lastModification
                renderableNotes.add(new RenderableNote(n.getNoteid(), n.getUser(), sharedUsersFromNote, n.getTitle(), n.getBody(), n.getCreationDate(), n.getLastModification()));
            }

            return renderableNotes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
    public boolean addNote(long userid, String title, String body) {
        NoteDao nd = new NoteDaoImpl();
        UserDao ud = new UserDaoImpl();

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

    }

    @Override
    public boolean editNote(long userid, long noteid, String title, String body) {
        NoteDao nd = new NoteDaoImpl();
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
    public List<RenderableNote> getSharedNoteWithMe(long userid, int offset) {
        List<RenderableNote> renderableNotes = new ArrayList<>();
        NoteDao nd = new NoteDaoImpl();
        try {
            List<Note> sharedNotesWithMe = nd.getSharedNotesWithMe(userid, LIMIT, offset);
            for (Note n : sharedNotesWithMe) {
                renderableNotes.add(new RenderableNote(n.getNoteid(), n.getUser(), null, n.getTitle(), n.getBody(), n.getCreationDate(), n.getLastModification()));
            }
            return renderableNotes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RenderableNote> getSharedNotes(long userid, int offset) {
        List<RenderableNote> renderableNotes = new ArrayList<>();
        NoteDao nd = new NoteDaoImpl();
        UserDao ud = new UserDaoImpl();
        try {
            List<Note> sharedNotes = nd.getSharedNotes(userid, LIMIT, offset);
            for (Note n : sharedNotes) {
                List<User> sharedUsersFromNote = ud.getUsersFromSharedNote(n.getNoteid());
                renderableNotes.add(new RenderableNote(n.getNoteid(), n.getUser(), sharedUsersFromNote, n.getTitle(), n.getBody(), n.getCreationDate(), n.getLastModification()));
            }
            return renderableNotes;
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
                }

                if (users.size() != 0) {
                    nd.createShare(noteForShare, users);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteShareNote(long userWhoDeleteShares, long noteid, String[] usernames) {
        NoteDao nd = new NoteDaoImpl();
        UserDao ud = new UserDaoImpl();
        try {
            Note noteForDeleteShare = nd.getNoteById(noteid);
            List<User> users = new ArrayList<>();
            if (noteForDeleteShare != null) {
                for (String username : usernames) {
                    long userid = ud.getUserIdByUsername(username);
                    System.out.println(nd.sharedNoteExists(userid, noteid));
                    if (nd.sharedNoteExists(userid, noteid)) {
                        User user = ud.getUserById(userid);
                        users.add(user);
                    }
                }
                if (users.size() != 0) {
                    //nd.createShare(noteForDeleteShare, users);
                    nd.deleteShare(noteForDeleteShare, users);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Override
    public boolean deleteAllShareNote(long userid, long noteid) {
        NoteDao nd = new NoteDaoImpl();
        try {
            List<Note> sharedNotes = nd.getSharedNotes(userid, 50, 0);
            List<Note> sharedWithMe = nd.getSharedNotesWithMe(userid, 50, 0);
            boolean canDelete = false;

            for (Note n : sharedWithMe) {
                System.out.println(n.getNoteid() + " =? " + noteid);
                if (n.getNoteid() == noteid) {
                    System.out.println("Eliminamos nota que nos han compartido");
                    canDelete = true;
                }
            }

            System.out.println("can delete? " + canDelete);
            if (canDelete) {
                nd.deleteAllSharesByNoteId(noteid);
                return true;
            }

            for (Note n : sharedNotes) {
                if (n.getUser().getUserid() == userid && n.getNoteid() == noteid) {
                    System.out.println("Eliminamos nota que hemos compartido");
                    canDelete = true;
                }
            }

            if (canDelete) {
                nd.deleteAllSharesByNoteId(noteid);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;


        /*
        NoteDao nd = new NoteDaoImpl();
        try {
            nd.deleteAllSharesByNoteId(noteid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
         */
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
