package com.example.unittestingpractice;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.unittestingpractice.models.Note;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NoteDaoTest extends NoteDatabaseTest{

    public static final String TEST_TITLE = "This is a test title";
    public static final String TEST_CONTENT = "This is a some test content";
    public static final String TEST_TIMESTAMP = "09-2019";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    /*
        Insert -> read -> delete
     */

    @Test
    public void insertReadDelete() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);

        getNoteDao().insertNote(note).blockingGet();

        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);

        assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());
        assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        getNoteDao().deleteNote(note).blockingGet();

        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0, insertedNotes.size());
    }

    /*
        Insert -> read -> update -> read -> delete
     */

    @Test
    public void insertReadUpdateReadDelete() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);

        getNoteDao().insertNote(note).blockingGet();

        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);

        assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());
        assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        note.setTitle(TEST_TITLE);
        note.setContent(TEST_CONTENT);
        note.setTimestamp(TEST_TIMESTAMP);
        getNoteDao().updateNote(note).blockingGet();

        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertEquals(TEST_TITLE, insertedNotes.get(0).getTitle());
        assertEquals(TEST_CONTENT, insertedNotes.get(0).getContent());
        assertEquals(TEST_TIMESTAMP, insertedNotes.get(0).getTimestamp());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        getNoteDao().deleteNote(note).blockingGet();

        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0, insertedNotes.size());
    }

    /*
        Insert note with null title, throw Exception
     */

    @Test(expected = SQLiteConstraintException.class)
    public void insert_nullTitle_throwSQLiteConstraintException() throws Exception{

        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        getNoteDao().insertNote(note).blockingGet();
    }


    /*
        Insert, Update with null title, throw Exception
     */

    @Test(expected = SQLiteConstraintException.class)
    public void updateNote_nullTitle_throwSQLiteConstraintException() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);

        getNoteDao().insertNote(note).blockingGet();

        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> inserterNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertNotNull(inserterNotes);

        note = new Note(inserterNotes.get(0));
        note.setTitle(null);
        getNoteDao().updateNote(note).blockingGet();
    }
}
