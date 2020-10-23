package com.example.unittestingpractice;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.unittestingpractice.persistance.NoteDAO;
import com.example.unittestingpractice.persistance.NoteDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class NoteDatabaseTest {

    private NoteDatabase noteDatabase;

    public NoteDAO getNoteDao(){
        return noteDatabase.getNoteDao();
    }

    @Before
    public void init(){
        noteDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).build();
    }

    @After
    public void finish(){
        noteDatabase.close();
    }
}
