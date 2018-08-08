package com.softcube.spaceshooter.view.main.dialogs;

import android.view.View;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.view.main.GameFragment;
import com.softcube.spaceshooter.view.main.dialogs.listeners.GameOverDialogListener;

/**
 * Created by Wilson on 6/20/16.
 */
public class GameOverDialog extends BaseCustomDialog implements View.OnClickListener {

    private GameOverDialogListener listener;
    private int selectedId;

    public GameOverDialog(GameFragment parent){
        super(parent.getSpaceShooterActivity());

        setContentView(R.layout.dialog_game_over);
        findViewById(R.id.btn_exit).setOnClickListener(this);
        findViewById(R.id.btn_resume).setOnClickListener(this);

    }

    public void setListener (GameOverDialogListener listener) {
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
            listener.exitGame();
        }
        else if (selectedId == R.id.btn_resume) {
            listener.startNewGame();
        }
    }
}
