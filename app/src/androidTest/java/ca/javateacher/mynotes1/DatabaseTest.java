package ca.javateacher.mynotes1;

import android.content.Context;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import ca.javateacher.mynotes1.database.AppDatabase;
import ca.javateacher.mynotes1.database.NoteDao;
import ca.javateacher.mynotes1.database.NoteEntity;
import ca.javateacher.mynotes1.utilities.SampleData;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

  public static final String TAG = "JUnit";
  private AppDatabase mAppDatabase;
  private NoteDao mNoteDao;

  @Before
  public void createDatabase(){
    Context context = ApplicationProvider.getApplicationContext();
    mAppDatabase = Room.inMemoryDatabaseBuilder(
        context, AppDatabase.class).build();
    mNoteDao = mAppDatabase.noteDao();
    Log.i(TAG,"createDatabase");
  }

  @After
  public void closeDatabase(){
    mAppDatabase.close();
    Log.i(TAG,"closeDatabase");
  }

  @Test
  public void createAndRetrieveNotes(){
    mNoteDao.insertAll(SampleData.getNotes());
    int count = mNoteDao.getCount();
    Log.i(TAG,"createAndRetrieveNotes: count=" + count);
    assertEquals(SampleData.getNotes().size(), count);
  }

  @Test
  public void compareStrings(){
    mNoteDao.insertAll(SampleData.getNotes());
    NoteEntity original = SampleData.getNotes().get(0);
    NoteEntity fromDatabase = mNoteDao.getNoteById(1);
    assertEquals(original.getText(), fromDatabase.getText());
    assertEquals(1, fromDatabase.getId());
  }

}
