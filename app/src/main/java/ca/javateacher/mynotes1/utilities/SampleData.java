package ca.javateacher.mynotes1.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ca.javateacher.mynotes1.database.NoteEntity;

public class SampleData {
  private static final String SAMPLE_TEXT_1 = "Room provides an abstraction layer over SQLite.";
  private static final String SAMPLE_TEXT_2 = "Google highly recommends using Room instead of SQLite. " +
      "\nHowever, if you prefer to use SQLite APIs directly, read Save Data Using SQLite.";
  private static final String SAMPLE_TEXT_3 = "Apps that handle non-trivial amounts of structured data can benefit greatly from persisting that data locally." +
      "\nThe most common use case is to cache relevant pieces of data.";

  private static Date getDate(int diff){
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.add(Calendar.MILLISECOND, diff);
    return calendar.getTime();
  }

  public static List<NoteEntity> getNotes(){
    List<NoteEntity> list = new ArrayList<>();
    list.add(new NoteEntity(0,getDate(1),SAMPLE_TEXT_1));
    list.add(new NoteEntity(0,getDate(-1),SAMPLE_TEXT_2));
    list.add(new NoteEntity(0,getDate(-2),SAMPLE_TEXT_3));
    return list;
  }

}
