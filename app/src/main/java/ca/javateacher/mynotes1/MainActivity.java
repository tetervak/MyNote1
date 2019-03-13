package ca.javateacher.mynotes1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.javateacher.mynotes1.database.NoteEntity;
import ca.javateacher.mynotes1.ui.NotesAdapter;
import ca.javateacher.mynotes1.viewmodel.MainViewModel;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;

  private MainViewModel mMainViewModel;

  @OnClick(R.id.fab)
  void fabClickHandler(){
    Intent intent = new Intent(this, EditorActivity.class);
    startActivity(intent);
  }

  private List<NoteEntity> mNoteEntityList = new ArrayList<>();
  private NotesAdapter mNotesAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ButterKnife.bind(this);

    initRecyclerView();
    initViewModel();
  }

  private void initViewModel() {

    final Observer<List<NoteEntity>> noteObserver =
        new Observer<List<NoteEntity>>() {
          @Override
          public void onChanged(List<NoteEntity> noteEntities) {
            mNoteEntityList.clear();
            mNoteEntityList.addAll(noteEntities);
            if(mNotesAdapter == null){
              mNotesAdapter = new NotesAdapter(mNoteEntityList, MainActivity.this);
              mRecyclerView.setAdapter(mNotesAdapter);
            }else {
              mNotesAdapter.notifyDataSetChanged();
            }
          }
        };

    mMainViewModel =
        ViewModelProviders.of(this).get(MainViewModel.class);

    mMainViewModel.mNoteData.observe(this, noteObserver);
  }

  private void initRecyclerView() {
    mRecyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(layoutManager);
    DividerItemDecoration divider =
        new DividerItemDecoration(this, layoutManager.getOrientation());
    mRecyclerView.addItemDecoration(divider);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_add_sample_data) {
      addSampleData();
      return true;
    } else if (id == R.id.action_delete_all){
      deleteAllNotes();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void deleteAllNotes() {
    mMainViewModel.deleteAllNotes();
  }

  private void addSampleData() {
    mMainViewModel.addSampleData();
  }
}
