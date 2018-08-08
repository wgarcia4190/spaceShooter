package com.softcube.spaceshooter.application;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.softcube.spaceshooter.view.main.SpaceShooterActivity;
import com.softcube.spaceshooter.view.main.dialogs.BaseCustomDialog;

/**
 * Created by Wilson on 6/8/16.
 */
public abstract class SpaceShooterBaseFragment extends Fragment {

    BaseCustomDialog currentDialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();

        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ViewTreeObserver viewTreeObserver = getView().getViewTreeObserver();

                if(viewTreeObserver.isAlive()){
                    viewTreeObserver.removeOnGlobalLayoutListener(this);

                    onLayoutCompleted();
                }
            }
        });
    }

    public boolean onBackPressed(){
        return true;
    }



    protected abstract void onLayoutCompleted();

    public SpaceShooterActivity getSpaceShooterActivity(){
        return (SpaceShooterActivity) getActivity();
    }

    public void showDialog (BaseCustomDialog newDialog) {
        showDialog(newDialog, false);
    }

    public void showDialog (BaseCustomDialog newDialog, boolean dismissOtherDialog) {
        if (currentDialog != null && currentDialog.isShowing()) {
            if (dismissOtherDialog) {
                currentDialog.dismiss();
            }
            else {
                return;
            }
        }
        currentDialog = newDialog;
        currentDialog.show();
    }


}
