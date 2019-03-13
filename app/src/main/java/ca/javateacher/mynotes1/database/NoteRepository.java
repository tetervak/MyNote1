package ca.javateacher.mynotes1.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import ca.javateacher.mynotes1.utilities.SampleData;

public class NoteRepository {
  private static NoteRepository ourInstance;

  public LiveData<List<NoteEntity>> mNoteData;
  private AppDatabase mAppDatabase;
  private Executor mExecutor = Executors.newSingleThreadExecutor();

  public static NoteRepository getInstance(Context context) {
    if(ourInstance == null){
      ourInstance = new NoteRepository(context);
    }

    return ourInstance;
  }

  private NoteRepository(Context context) {

    mAppDatabase = AppDatabase.getInstance(context);
    mNoteData = getNoteData();
  }

  public void addSampleData() {
    mExecutor.execute(new Runnable() {
      @Override
      public void run() {
        mAppDatabase.noteDao().insertAll(SampleData.getNotes());
      }
    });
  }

  private LiveData<List<NoteEntity>> getNoteData(){
    return mAppDatabase.noteDao().getAll();
  }

  public void deleteAllNotes() {
    mExecutor.execute(new Runnable() {
      @Override
      public void run() {
        mAppDatabase.noteDao().deleteAll();
      }
    });
  }

  public NoteEntity getNoteById(int noteId) {
    return mAppDatabase.noteDao().getNoteById(noteId);
  }

  public void insertNote(NoteEntity note) {
    mExecutor.execute(new Runnable() {
      @Override
      public void run() {
        mAppDatabase.noteDao().insertNote(note);
      }
    });
  }

  public void deleteNote(NoteEntity note) {
    mExecutor.execute(new Runnable() {
      @Override
      public void run() {
        mAppDatabase.noteDao().deleteNote(note);
      }
    });
  }
}
