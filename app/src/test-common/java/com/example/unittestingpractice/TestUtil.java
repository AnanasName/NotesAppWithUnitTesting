package com.example.unittestingpractice;

import com.example.unittestingpractice.models.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil {

    public static final String TIMESTAMP_1 = "10-2020";
    public static final Note TEST_NOTE_1 = new Note("Do something useful", "Useful thing", TIMESTAMP_1);

    public static final String TIMESTAMP_2 = "11-2020";
    public static final Note TEST_NOTE_2 = new Note("Important day", "Go to the meeting", TIMESTAMP_2);

    public static final List<Note> TEST_NOTES_LIST = Collections.unmodifiableList(
            new ArrayList<Note>(){{
                add(new Note(1, "Do something useful", "Useful thing", TIMESTAMP_1));
                add(new Note(2, "Important day", "Go to the meeting", TIMESTAMP_2 ));
            }}
    );
}
