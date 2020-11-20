package com.liceu.sromerom.services;

import com.liceu.sromerom.daos.*;
import com.liceu.sromerom.model.Note;
import com.liceu.sromerom.model.User;
import com.liceu.sromerom.utils.Filter;
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
    public List<Note> getAll() {
        NoteDao nd = new NoteDaoImpl();
        return nd.getAllNotes();
    }

    @Override
    public List<Note> getNotesFromUser(long userid, int offset) {
        List<Note> allNotes;
        List<RenderableNote> renderableNotes = new ArrayList<>();
        NoteDao nd = new NoteDaoImpl();
        try {
            List<Note> sharedNotes = nd.getSharedNotes(userid, LIMIT, offset);
            allNotes = nd.getAllNotesFromUser(userid, LIMIT, offset);
            for (int i = 0; i < allNotes.size(); i++) {
                /*
                for (int j = 0; j < sharedNotes.size(); j++) {

                    if (sharedNotes.get(j).getNoteid() == allNotes.get(i).getNoteid()) {

                    }
                }
                 */
                //long noteid, User noteOwner, User sharedUser, String title, String body, String creationDate, String lastModification
                renderableNotes.add(new RenderableNote(allNotes.get(i).getNoteid(), allNotes.get(i).getUser(), null, allNotes.get(i).getTitle(), allNotes.get(i).getBody(), allNotes.get(i).getCreationDate(), allNotes.get(i).getLastModification()));
            }


            for (Note n : allNotes) {
                String bodyParsed = getParsedBodyEscapeText(n.getBody());
                n.setBody(bodyParsed);
            }
            return allNotes;
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
            if (!title.equals("") && !initDate.equals("") && !endDate.equals("")) return true;
            if (!title.equals("") && initDate.equals("") && endDate.equals("")) return true;
            if (title.equals("") && !initDate.equals("") && !endDate.equals("")) return true;

        }
        return false;
    }


    @Override
    public List<Note> filter(long userid, String type, String title, String initDate, String endDate, int offset) {
        NoteDao nd = new NoteDaoImpl();
        List<Note> notes = new ArrayList<>();
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
                switch (Filter.checkTypeFilter(title, initDate, endDate)) {
                    case "filterByTitle":
                        notes = nd.filterTypeOfNoteByTitle(userid, title, LIMIT, offset);
                    case "filterByDate":
                        notes = nd.filterTypeOfNoteByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    case "filterAll":
                        notes = nd.filterAllTypeOfNote(userid, title, initDateParsed, endDateParsed, LIMIT, offset);
                }
            } else {
                System.out.println("filtramos solo lo seleccionado...");
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByTitle")) {
                    if (type.equals("compartides")) {
                        notes = nd.filterSharedNotesWithMeByTitle(userid, title, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        notes = nd.filterSharedNotesByTitle(userid, title, LIMIT, offset);
                    } else {
                        notes = nd.filterCreatedNotesByTitle(userid, title, LIMIT, offset);
                    }
                }
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterByDate")) {
                    if (type.equals("compartides")) {
                        notes = nd.filterSharedNotesWithMeByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        notes = nd.filterSharedNotesByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    } else {
                        notes = nd.filterCreatedNotesByDate(userid, initDateParsed, endDateParsed, LIMIT, offset);
                    }
                }
                if (Filter.checkTypeFilter(title, initDate, endDate).equals("filterAll")) {
                    if (type.equals("compartides")) {
                        notes = nd.filterAllSharedNotesWithMe(userid, title, initDateParsed, endDateParsed, LIMIT, offset);
                    } else if (type.equals("compartit")) {
                        notes = nd.filterAllSharedNotes(userid, title, initDateParsed, endDateParsed, LIMIT, offset);
                    } else {
                        notes = nd.filterAllCreatedNotes(userid, title, initDateParsed, endDateParsed, LIMIT, offset);
                    }
                }
            }
            for (Note n : notes) {
                String bodyParsed = getParsedBodyEscapeText(n.getBody());
                n.setBody(bodyParsed);
            }
            return notes;
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
    public String getParsedBodyToHTML(String body) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(body);


        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).sanitizeUrls(true).build();
        return renderer.render(document);
    }

    @Override
    public String getParsedBodyEscapeText(String body) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(body);
        TextContentRenderer renderer2 = TextContentRenderer.builder().build();
        return renderer2.render(document);
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
                System.out.println(n.getNoteid() + " =? " + noteid);
                if (n.getNoteid() == noteid) {
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
                if (n.getUser().getIduser() == userid && n.getNoteid() == noteid) {
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
