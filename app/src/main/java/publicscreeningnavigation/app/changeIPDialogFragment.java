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

public class changeIPDialogFragment extends DialogFragment {

    EditText input;

    public interface  changeIPListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    changeIPListener listener;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            listener = (changeIPListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement changeIPListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.options_popup, null);
        builder.setView(v);

        input = (EditText) v.findViewById(R.id.ipEdit);


        builder.setMessage("Changing the IP")
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(changeIPDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(changeIPDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public String getCurrentInputText(){
        return input.getText().toString();
    }
}