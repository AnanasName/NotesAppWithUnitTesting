package com.example.unittestingpractice.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.unittestingpractice.models.Note;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface NoteDAO {

    @Insert
    Single<Long> insertNote(Note note) throws Exception;

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Delete
    Single<Integer> deleteNote(Note note) throws Exception;

    @Update
    Single<Integer> updateNote(Note note) throws Exception;
}
