package com.softcube.spaceshooter.view.main;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.softcube.spaceshooter.R;
import com.softcube.spaceshooter.application.SpaceShooterBaseFragment;

public class MainMenuFragment extends SpaceShooterBaseFragment implements View.OnClickListener {

    public MainMenuFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.startButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startButton:
                getSpaceShooterActivity().startGame();
                break;
        }
    }

    public void exit() {
        getSpaceShooterActivity().finish();
    }


    @Override
    protected void onLayoutCompleted() {
        animateTitle();

        ValueAnimator animation = ValueAnimator.ofFloat(0f, 42f);
        animation.setDuration(1000);
        animation.start();

    }

    /* Private Methods */

    private void animateTitle() {
        View title = getView().findViewById(R.id.main_title);
        View subtitle = getView().findViewById(R.id.main_subtitle);

        Animation titleAnimation = AnimationUtils.loadAnimation(getSpaceShooterActivity(), R.anim.title_enter);
        title.startAnimation(titleAnimation);

        Animation subtitleAnimation = AnimationUtils.loadAnimation(getSpaceShooterActivity(), R.anim.subtitle_enter);
        subtitle.startAnimation(subtitleAnimation);
    }



    private void updatePlayButtons() {

    }

    /* End Private Methods */
}
