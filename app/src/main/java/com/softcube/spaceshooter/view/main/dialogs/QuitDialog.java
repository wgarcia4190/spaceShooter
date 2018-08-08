package com.softcube.spaceshooter.view.main.dialogs;

import android.view.View;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.view.main.SpaceShooterActivity;
import com.softcube.spaceshooter.view.main.dialogs.listeners.QuitDialogListener;

/**
 * Created by Wilson on 6/20/16.
 */
public class QuitDialog extends BaseCustomDialog implements View.OnClickListener {

    private QuitDialogListener listener;
    private int selectedId;

    public QuitDialog(SpaceShooterActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_quit);
        findViewById(R.id.btn_exit).setOnClickListener(this);
        findViewById(R.id.btn_resume).setOnClickListener(this);
    }

    public void setListener(QuitDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        selectedId = v.getId();
        dismiss();
    }

    @Override
    protected void onDismissed() {
        if (selectedId == R.id.btn_exit) {
            listener.exit();
        }
    }

}
