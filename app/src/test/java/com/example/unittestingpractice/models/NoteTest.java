package com.example.unittestingpractice.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {
    public static final String TIMESTAMP_1 = "09-2020";
    public static final String TIMESTAMP_2 = "10-2020";

    //Compare two equals Notes
    @Test
    void isNotesEqual_identicalProperties_returnTrue() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note2.setId(1);
        //Act

        //Assert
        assertEquals(note1, note2);
        System.out.println("The note are equal!");
    }

    //Compare notes with 2 different ids
    @Test
    void isNotesEqual_differentIds_returnFalse() throws Exception{
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note2.setId(2);

        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal!");
    }

    //Compare 2 notes with different timestamps
    @Test
    void isNotesEqual_differentTimestamps_returnTrue() throws Exception{
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_2);
        note2.setId(1);

        assertEquals(note1, note2);
        System.out.println("The notes are equal!");
    }

    //Compare 2 notes with different titles
    @Test
    void isNoteEqual_differentTitles_returnFalse() throws Exception{
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);

        Note note2 = new Note("Note #2", "This is note #1", TIMESTAMP_2);
        note2.setId(2);

        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal");
    }

    //Compare 2 notes with different content
    @Test
    void isNoteEqual_differentContent_returnFalse() throws Exception{
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);

        Note note2 = new Note("Note #1", "This is note #2", TIMESTAMP_1);
        note1.setId(1);

        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal");
    }
}
