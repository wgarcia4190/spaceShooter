package com.softcube.spaceshooter.view.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.softcube.spaceshooter.R;

import java.util.Locale;

/**
 * Created by Wilson on 6/8/16.
 */
public class SpaceShooterButton extends Button implements CustomComponent {

    public SpaceShooterButton(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        if(!isInEditMode())
            initializeElement(context, attrs);
    }

    public SpaceShooterButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        if(!isInEditMode())
            initializeElement(context, attrs);
    }

    @Override
    public void initializeElement(final Context context, final AttributeSet set){
        TypedArray typedArray = context.obtainStyledAttributes(set, R.styleable.SpaceText);

        for(int index =0, total = typedArray.getIndexCount(); index < total; index++){
            int attrId = typedArray.getIndex(index);

            switch(attrId){
                case R.styleable.SpaceText_font:
                    String fontName = context.getString(R.string.fonts_location).concat(typedArray.getString(attrId));
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);

                    this.setTypeface(typeface);
                    break;

                case R.styleable.SpaceText_toUpperCase:
                    this.setText(this.getText().toString().toUpperCase(Locale.US));
                    break;
                default:
                    break;
            }
        }

        typedArray.recycle();
    }

    @Override
    public boolean onSetAlpha(int alpha)
    {
        setTextColor(Color.argb(alpha, 255, 255, 255));
        return true;
    }
}
