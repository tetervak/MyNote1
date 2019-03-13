package ca.javateacher.mynotes1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.javateacher.mynotes1.database.NoteEntity;
import ca.javateacher.mynotes1.viewmodel.EditorViewModel;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import static ca.javateacher.mynotes1.utilities.Constants.EDITING_KEY;
import static ca.javateacher.mynotes1.utilities.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity {

  @BindView(R.id.note_text)
  EditText mEditText;

  private EditorViewModel mViewModel;
  private boolean mNewNote = false;
  private boolean mEditing = false;

  @SuppressWarnings("ConstantConditions")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_editor);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    ButterKnife.bind(this);

    if(savedInstanceState != null){
      mEditing = savedInstanceState.getBoolean(EDITING_KEY);
    }

    initViewModel();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if(! mNewNote){
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu_editor, menu);
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == android.R.id.home){
      saveAndReturn();
      return true;
    }else if(item.getItemId() == R.id.action_delete){
      mViewModel.deleteNote();
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    saveAndReturn();
  }

  private void saveAndReturn() {
    mViewModel.saveNote(mEditText.getText().toString());
    finish();
  }

  private void initViewModel() {
    mViewModel = ViewModelProviders.of(this)
        .get(EditorViewModel.class);

    mViewModel.mNoteData.observe(this, new Observer<NoteEntity>() {
      @Override
      public void onChanged(NoteEntity noteEntity) {
        if(noteEntity != null && !mEditing) {
          mEditText.setText(noteEntity.getText());
        }
      }
    });

    Bundle extras = getIntent().getExtras();
    if(extras == null){
      setTitle(R.string.new_note);
      mNewNote = true;
    }else{
      setTitle(R.string.edit_note);
      int noteId = extras.getInt(NOTE_ID_KEY);
      mViewModel.loadData(noteId);
    }
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    outState.putBoolean(EDITING_KEY, true);
    super.onSaveInstanceState(outState);
  }
}
