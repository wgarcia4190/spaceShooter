package com.softcube.spaceshooter.view.main.dialogs;

import android.view.View;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.view.main.SpaceShooterActivity;
import com.softcube.spaceshooter.view.main.dialogs.listeners.PauseDialogListener;

/**
 * Created by Wilson on 6/20/16.
 */
public class PauseDialog extends BaseCustomDialog implements View.OnClickListener {

    private PauseDialogListener listener;
    private int selectedId;

    public PauseDialog(SpaceShooterActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_pause);
        findViewById(R.id.btn_music).setOnClickListener(this);
        findViewById(R.id.btn_sound).setOnClickListener(this);
        findViewById(R.id.btn_exit).setOnClickListener(this);
        findViewById(R.id.btn_resume).setOnClickListener(this);
        updateSoundAndMusicButtons();
    }

    private void updateSoundAndMusicButtons() {

    }

    public void setListener(PauseDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sound) {
            //parent.getSoundManager().toggleSoundStatus();
            updateSoundAndMusicButtons();
        }
        else if (v.getId() == R.id.btn_music) {
            //parent.getSoundManager().toggleMusicStatus();
            updateSoundAndMusicButtons();
        }
        else if (v.getId() == R.id.btn_exit) {
            selectedId = v.getId();
            super.dismiss();
        }
        else if (v.getId() == R.id.btn_resume) {
            selectedId = v.getId();
            super.dismiss();
        }
    }

    @Override
    protected void onDismissed () {
        if (selectedId == R.id.btn_exit) {
            listener.exitGame();
        }
        else if (selectedId == R.id.btn_resume) {
            listener.resumeGame();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        selectedId = R.id.btn_resume;
    }
}
