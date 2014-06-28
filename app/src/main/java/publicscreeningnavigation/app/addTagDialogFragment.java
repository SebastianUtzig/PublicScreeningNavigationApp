package publicscreeningnavigation.app;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jo on 19.06.14.
 */

public class addTagDialogFragment extends DialogFragment {

    String collectedTags = "None";
    EditText input;
    TextView tags;

    public interface  addTagDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    addTagDialogListener listener;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            listener = (addTagDialogListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement addTagDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle args = getArguments();
        collectedTags = args.getString("tags");
        View v = inflater.inflate(R.layout.fragment_add_tag_dialog, null);
        builder.setView(v);
        tags = (TextView) v.findViewById(R.id.tagCollection);
        input = (EditText) v.findViewById(R.id.tagInput);
        tags.setText(collectedTags);

        builder.setMessage("Adding a tag")
                .setPositiveButton("Add tag", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(addTagDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(addTagDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public String getCurrentInputText(){
        return input.getText().toString();
    }
}