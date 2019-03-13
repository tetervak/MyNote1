package ca.javateacher.mynotes1.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import ca.javateacher.mynotes1.database.NoteRepository;
import ca.javateacher.mynotes1.database.NoteEntity;

public class EditorViewModel extends AndroidViewModel {

  public MutableLiveData<NoteEntity> mNoteData =
      new MutableLiveData<>();

  private NoteRepository mNoteRepository;
  private Executor mExecutor = Executors.newSingleThreadExecutor();

  public EditorViewModel(@NonNull Application application) {
    super(application);
    mNoteRepository = NoteRepository.getInstance(getApplication());
  }

  public void loadData(int noteId) {
    mExecutor.execute(new Runnable() {
      @Override
      public void run() {
        NoteEntity note = mNoteRepository.getNoteById(noteId);
        mNoteData.postValue(note);
      }
    });
  }

  public void saveNote(String noteText) {
    NoteEntity note = mNoteData.getValue();
    if(note == null){
        if(TextUtils.isEmpty(noteText.trim())){
          return;
        }
        note = new NoteEntity(new Date(),noteText.trim());
    }else{
      note.setText(noteText.trim());
    }
    mNoteRepository.insertNote(note);
  }

  public void deleteNote() {
    mNoteRepository.deleteNote(mNoteData.getValue());
  }
}
