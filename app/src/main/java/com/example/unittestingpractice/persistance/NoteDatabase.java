package com.example.unittestingpractice.persistance;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.unittestingpractice.models.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes_db";

    public abstract NoteDAO getNoteDao();
}
