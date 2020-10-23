package com.example.unittestingpractice.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.unittestingpractice.LiveDataTestUtil;
import com.example.unittestingpractice.TestUtil;
import com.example.unittestingpractice.models.Note;
import com.example.unittestingpractice.persistance.NoteDAO;
import com.example.unittestingpractice.ui.Resource;
import com.example.unittestingpractice.util.InstantExecutorExtension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.example.unittestingpractice.repository.NoteRepository.DELETE_FAILURE;
import static com.example.unittestingpractice.repository.NoteRepository.DELETE_SUCCESS;
import static com.example.unittestingpractice.repository.NoteRepository.INSERT_FAILURE;
import static com.example.unittestingpractice.repository.NoteRepository.INSERT_SUCCESS;
import static com.example.unittestingpractice.repository.NoteRepository.INVALID_NOTE_ID;
import static com.example.unittestingpractice.repository.NoteRepository.NOTE_TITLE_NULL;
import static com.example.unittestingpractice.repository.NoteRepository.UPDATE_FAILURE;
import static com.example.unittestingpractice.repository.NoteRepository.UPDATE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(InstantExecutorExtension.class)
public class NoteRepositoryTest {

    public static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);

    private NoteRepository noteRepository;

    private NoteDAO noteDAO;

    @BeforeEach
    public void initEach(){
        noteDAO = mock(NoteDAO.class);
        noteRepository = new NoteRepository(noteDAO);
    }

    /*
        insert note
        verify the correct method is called
        confirm observer is triggered
        confirm new rows inserted
     */

    @Test
    void insertNote_returnRow() throws Exception {

        //Arrange
        final Long inserterRow = 1L;
        final Single<Long> returnedData = Single.just(inserterRow);
        when(noteDAO.insertNote(any(Note.class))).thenReturn(returnedData);

        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        verify(noteDAO).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDAO);

        assertEquals(Resource.success(1, INSERT_SUCCESS), returnedValue);

        //Act

        //Assert
    }

    /*
        Insert note
        Failure (return -1)
     */

    @Test
    void insertNote_returnFailure() throws Exception {

        //Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDAO.insertNote(any(Note.class))).thenReturn(returnedData);

        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        verify(noteDAO).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDAO);

        assertEquals(Resource.error(null, INSERT_FAILURE), returnedValue);

        //Act

        //Assert
    }

    /*
        insert note
        null title
        confirm throw exception
     */

    @Test
    void insertNote_nullTitle_throwException() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }

    /*
        update note
        verify correct method is called
        confirm observer os triggered
        confirm number of rows updated
     */

    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {
        //Arrange
        final int updatedRow = 1;
        when(noteDAO.updateNote(any(Note.class))).thenReturn(Single.just(updatedRow));

        //Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDAO).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDAO);

        assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnedValue);
    }
    
    /*
        update note
        Failure(-1)
     */

    @Test
    void updateNote_returnFailure() throws Exception {
        // Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);
        when(noteDAO.updateNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        // Assert
        verify(noteDAO).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDAO);

        assertEquals(Resource.error(null, UPDATE_FAILURE), returnedValue);
    }
    
    /*
        update note
        null title
        throw exception
     */

    @Test
    void updateNote_nullTitle_throwException() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.updateNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }

    /*
        delete note
        null id
        throw exception
     */

    @Test
    void deleteNote_nullId_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setId(-1);
                noteRepository.deleteNote(note);
            }
        });

        assertEquals(INVALID_NOTE_ID, exception.getMessage());
    }

    /*
        delete note
        delete success
        return Resource.success with deleted row
     */

    @Test
    void deleteNote_deleteSuccess_returnResourceSuccess() throws Exception {
        //Arrange
        final int deletedRow = 1;
        Resource<Integer> successResponce= Resource.success(deletedRow, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDAO.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        //Act
        Resource<Integer> observedResponce = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        //Assert
        assertEquals(successResponce, observedResponce);
    }

    /*
        delete note
        delete failure
        return Resource.error
     */

    @Test
    void deleteNote_deleteFailure_returnResourceError() throws Exception {
        //Arrange
        final int deletedRow = -1;
        Resource<Integer> errorResponce = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDAO.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        //Act
        Resource<Integer> observedResponce = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        //Assert
        assertEquals(errorResponce, observedResponce);
    }

    /*
        retrieve notes
        return list of notes
     */

    @Test
    void getNotes_returnListWithNotes() throws Exception {
        //Arrange
        List<Note> notes = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDAO.getNotes()).thenReturn(returnedData);

        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        //Assert
        assertEquals(notes, observedData);
    }

    /*
        retrieve notes
        return empty list
     */

    @Test
    void getNotes_returnEmptyList() throws Exception {
        //Arrange
        List<Note> notes = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDAO.getNotes()).thenReturn(returnedData);

        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        //Assert
        assertEquals(notes, observedData);
    }
}
