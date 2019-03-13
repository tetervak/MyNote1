package ca.javateacher.mynotes1.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.javateacher.mynotes1.EditorActivity;
import ca.javateacher.mynotes1.R;
import ca.javateacher.mynotes1.database.NoteEntity;

import static ca.javateacher.mynotes1.utilities.Constants.NOTE_ID_KEY;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

  private final List<NoteEntity> mNoteEntityList;
  private final Context mContext;


  public NotesAdapter(List<NoteEntity> noteEntityList, Context context) {
    mNoteEntityList = noteEntityList;
    mContext = context;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.note_list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final NoteEntity note = mNoteEntityList.get(position);
    holder.mTextView.setText(note.getText());
    holder.mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(mContext, EditorActivity.class);
        intent.putExtra(NOTE_ID_KEY, note.getId());
        mContext.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mNoteEntityList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.note_text)
    TextView mTextView;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
