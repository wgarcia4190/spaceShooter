package com.softcube.spaceshooter.view.main.dialogs;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.view.main.SpaceShooterActivity;

/**
 * Created by Wilson on 6/20/16.
 */
public abstract class BaseCustomDialog implements View.OnTouchListener, Animation.AnimationListener {

    protected final SpaceShooterActivity parent;

    private boolean isShowing;
    private ViewGroup rootLayout;
    private View rootView;

    public BaseCustomDialog(SpaceShooterActivity parent){
        this.parent = parent;
    }

    protected void setContentView(int dialogResId){
        ViewGroup activityRoot = (ViewGroup) parent.findViewById(android.R.id.content);
        rootView = LayoutInflater.from(parent).inflate(dialogResId, activityRoot, false);
    }

    public void show(){
        if(isShowing){
            return;
        }

        isShowing = true;

        ViewGroup activityRoot = (ViewGroup) parent.findViewById(android.R.id.content);
        rootLayout = (ViewGroup) LayoutInflater.from(parent).inflate(R.layout.overlay_dialog, activityRoot, false);
        activityRoot.addView(rootLayout);
        rootLayout.setOnTouchListener(this);
        rootLayout.addView(rootView);
        startShowAnimation();

    }

    public void dismiss(){
        if(!isShowing){
            return;
        }

        isShowing = false;
        startHideAnimation();

    }

    protected void onViewClicked(){
        return;
    }
    protected abstract void onDismissed();

    protected View findViewById(int id) {
        return rootView.findViewById(id);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        hideViews();
        onDismissed();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    public boolean isShowing() {
        return isShowing;
    }

    private void startShowAnimation(){
        Animation dialogIn = AnimationUtils.loadAnimation(parent, R.anim.enter_from_top);
        rootView.startAnimation(dialogIn);
    }

    private void startHideAnimation() {
        Animation dialogOut = AnimationUtils.loadAnimation(parent, R.anim.exit_trough_top);
        dialogOut.setAnimationListener(this);
        rootView.startAnimation(dialogOut);
    }

    private void hideViews() {
        rootLayout.removeView(rootView);
        ViewGroup activityRoot = (ViewGroup) parent.findViewById(android.R.id.content);
        activityRoot.removeView(rootLayout);
    }
}
