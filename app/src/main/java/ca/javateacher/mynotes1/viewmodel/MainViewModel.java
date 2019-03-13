package ca.javateacher.mynotes1.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ca.javateacher.mynotes1.database.NoteRepository;
import ca.javateacher.mynotes1.database.NoteEntity;

public class MainViewModel extends AndroidViewModel {

  public LiveData<List<NoteEntity>> mNoteData;
  private NoteRepository mNoteRepository;

  public MainViewModel(@NonNull Application application) {
    super(application);

    mNoteRepository = NoteRepository.getInstance(application.getApplicationContext());
    mNoteData = mNoteRepository.mNoteData;
  }

  public void addSampleData() {
    mNoteRepository.addSampleData();
  }

  public void deleteAllNotes() {
    mNoteRepository.deleteAllNotes();
  }
}
