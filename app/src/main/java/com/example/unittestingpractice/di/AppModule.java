package com.example.unittestingpractice.di;

import android.app.Application;

import androidx.room.Room;

import com.example.unittestingpractice.persistance.NoteDAO;
import com.example.unittestingpractice.persistance.NoteDatabase;
import com.example.unittestingpractice.repository.NoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.unittestingpractice.persistance.NoteDatabase.DATABASE_NAME;

@Module
class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application){
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static NoteDAO provideNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }
    
    @Singleton
    @Provides
    static NoteRepository provideNoteRepository(NoteDAO noteDAO){
        return new NoteRepository(noteDAO);
    }
}
