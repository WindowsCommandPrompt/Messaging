package sg.np.edu.mad.animationtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.*;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;

public class ChangePersonalParticulars extends AppCompatActivity {

    private int[] ScreenDimensions() {
        DisplayMetrics measurements = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(measurements);
        int height = measurements.heightPixels;
        int width = measurements.widthPixels;
        return new int[] { width, height };
    }

    private void SetPopUpLocations(){
        int[] results = ScreenDimensions();
        ((LinearLayout) findViewById(R.id.base1Layout)).setX((float) (results[0] * 0.55));
        ((LinearLayout) findViewById(R.id.base1Layout)).setY((float) (results[1] * 0.20));
        ((LinearLayout) findViewById(R.id.base2Layout)).setX((float) (results[0] * 0.10));
        ((LinearLayout) findViewById(R.id.base2Layout)).setY((float) (results[1] * 0.35));
        ((LinearLayout) findViewById(R.id.base3Layout)).setX((float) (results[0] * 0.35));
        ((LinearLayout) findViewById(R.id.base3Layout)).setY((float) (results[1] * 0.465));
        ((LinearLayout) findViewById(R.id.base4Layout)).setX((float) (results[0] * 0.354));
        ((LinearLayout) findViewById(R.id.base4Layout)).setY((float) (results[1] * 0.59));
    }

    private void SetGap() {
        int[] results = ScreenDimensions();
        int increment = 1;
        do {
            ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildAt(increment).setMinimumWidth((int) (results[0] * 0.0413));
            increment += 2;
        }
        while (increment < ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildCount());
    }

    private void IndicatorInitializer() {
        ((RelativeLayout) findViewById(R.id.prevButton)).setEnabled(false);
        ((RelativeLayout) findViewById(R.id.prevButton)).setAlpha(0.5F);
    }

    private void SetNextPrevToggleButtonsPositions() {
        int[] results = ScreenDimensions();
        //1080px, 1440px, 480px (smallest)
        Log.d("ScreenWidth", "" + results[0]);
        //((Button) findViewById(R.id.confirmationSignUpButton)).getX();
        ((RelativeLayout) findViewById(R.id.prevButton)).setLayoutParams(new RelativeLayout.LayoutParams((int) (results[0] > 900 && results[0] <= 1100 ? 105.6 : (results[0] > 1100 && results[0] <= 1300 ? 110.543 : (results[0] > 1300 && results[0] <= 1500 ? 114.127 : (results[0] > 1500 && results[0] <= 1700 ? 117.913 : 102.213)))), (int) (results[0] > 900 && results[0] <= 1100 ? 105.6 : (results[0] > 1100 && results[0] <= 1300 ? 110.543 : (results[0] > 1300 && results[0] <= 1500 ? 114.127 : (results[0] > 1500 && results[0] <= 1700 ? 117.913 : 102.213))))));
        ((RelativeLayout) findViewById(R.id.prevButton)).setX(
                (float) ((results[0] * 0.25) * (results[0] > 900 && results[0] <= 1100 ? 0.45 : (results[0] > 1100 && results[0] <= 1300 ? 0.55 : (results[0] > 1300 && results[0] <= 1500 ? 0.65 : (results[0] > 1500 && results[0] <= 1700 ? 0.75 : 0.35)))))
        );
        ((RelativeLayout) findViewById(R.id.nextButton)).setLayoutParams(new RelativeLayout.LayoutParams((int) (results[0] > 900 && results[0] <= 1100 ? 105.6 : (results[0] > 1100 && results[0] <= 1300 ? 110.543 : (results[0] > 1300 && results[0] <= 1500 ? 114.127 : (results[0] > 1500 && results[0] <= 1700 ? 117.913 : 102.213)))), (int) (results[0] > 900 && results[0] <= 1100 ? 105.6 : (results[0] > 1100 && results[0] <= 1300 ? 110.543 : (results[0] > 1300 && results[0] <= 1500 ? 114.127 : (results[0] > 1500 && results[0] <= 1700 ? 117.913 : 102.213))))));
        ((RelativeLayout) findViewById(R.id.nextButton)).setX(
                (float) ((results[0] * 0.25) * (results[0] > 900 && results[0] <= 1100 ? 1.15 : (results[0] > 1100 && results[0] <= 1300 ? 1.25 : (results[0] > 1300 && results[0] <= 1500 ? 1.35 : (results[0] > 1500 && results[0] <= 1700 ? 1.45 : 0.95)))))
        );
    }

    private void SetOverlayTextPositioning() {
        int[] results = ScreenDimensions();
        ((CardView) findViewById(R.id.introductoryText)).setMinimumWidth((int) (results[0] * 0.85));
        ((CardView) findViewById(R.id.introductoryText)).setMinimumHeight((int) (results[1] * 0.85));
        ((LinearLayout) findViewById(R.id.introductoryTextInnerContainer)).setLayoutParams(new CardView.LayoutParams(((CardView) findViewById(R.id.introductoryText)).getMinimumWidth(), ((CardView) findViewById(R.id.introductoryText)).getMinimumHeight()));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_info);

        SetPopUpLocations();
        SetGap();
        SetNextPrevToggleButtonsPositions();
        SetOverlayTextPositioning();
        IndicatorInitializer();

        user_landing ul = new user_landing();

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        String dataString = intent.getStringExtra("DataStr");
        String password = intent.getStringExtra("PasswordForVerification");

        String usernameInput = ((EditText) findViewById(R.id.newUsername)).getText().toString();
        String oldPasswordInput = ((EditText) findViewById(R.id.oldPassword)).getText().toString();
        String passwordInput = ((EditText) findViewById(R.id.newPassword)).getText().toString();
        String confirmPasswordInput = ((EditText) findViewById(R.id.confirmPassword)).getText().toString();

        AlertDialog.Builder duplicateUsername = new AlertDialog.Builder(this);
        AlertDialog.Builder mismatchedPassword = new AlertDialog.Builder(this);
        AlertDialog.Builder emptyFields = new AlertDialog.Builder(this);

        if (usernameInput.length() != 0 && oldPasswordInput.length() != 0 && passwordInput.length() != 0) {
            if (ul.hasDuplicate(username, dataString, null, null)) {
                duplicateUsername
                .setTitle("Duplicate username found")
                .setMessage("Duplicate username has been found within the database. Please use a new username")
                .setPositiveButton(
                    "OK",
                    (DialogInterface di, int i) -> {
                        di.dismiss();
                    }
                )
                .setCancelable(false);
                duplicateUsername.create().show();
            } else {
                if (oldPasswordInput.equals(password)) {
                    //Check whether the confirmation password matches
                    if (password.equals(confirmPasswordInput)){

                    } else {

                    }
                } else {
                    //Password do not match
                    mismatchedPassword
                    .setTitle("Password mismatch")
                    .setMessage("The passwords do not match. Please try entering the password again")
                    .setPositiveButton(
                        "OK",
                        (DialogInterface di, int i) -> {
                            di.dismiss();
                        }
                    )
                    .setCancelable(false);
                }
            }
        }
        else {
            //One of the fields was not filled up.
            emptyFields
            .setTitle("Empty field was found!")
            .setPositiveButton(
                "OK",
                (DialogInterface di, int i) -> {
                    di.dismiss();
                }
            )
            .setCancelable(false);
            if (usernameInput.length() == 0){
                emptyFields.setMessage("The username field has been left blank");
                if (oldPasswordInput.length() == 0){
                    emptyFields.setMessage("The username field has been left blank\nThe old password field has been left blank");
                    if (passwordInput.length() == 0){
                        emptyFields.setMessage("The username field has been left blank\nThe old password field has been left blank\nThe new password field has been left blank");
                    }
                }
            }
        }

        //Close the overlay menu.
        ((ImageView) findViewById(R.id.close)).setOnClickListener(function -> {
            //go to the next element....
            AtomicReference<Float> tempValue = new AtomicReference<>(0F );
            new CountDownTimer(10, 10){
                @Override
                public void onTick(long l) {
                    float currAlphaVal = ((LinearLayout) findViewById(R.id.guideBase)).getAlpha();
                    tempValue.set(currAlphaVal -= 0.1F);
                }
                @Override
                public void onFinish() {
                    ((LinearLayout) findViewById(R.id.guideBase)).setAlpha(tempValue.get());
                    if (((LinearLayout) findViewById(R.id.guideBase)).getAlpha() < 1F){
                        this.start();
                    }
                    new CountDownTimer(500, 500) {
                        @Override
                        public void onTick(long l) { }
                        @Override
                        public void onFinish() {
                            ((LinearLayout) findViewById(R.id.guideBase)).setVisibility(View.GONE);
                        }
                    }.start();
                }
            }.start();
        });

        ((RelativeLayout) findViewById(R.id.prevButton)).setOnClickListener(function -> {
            //return to the previous element....
            if (!((RelativeLayout) findViewById(R.id.nextButton)).isEnabled()) {
                ((RelativeLayout) findViewById(R.id.nextButton)).setEnabled(true);
                ((RelativeLayout) findViewById(R.id.nextButton)).setAlpha(1F);
            }
            for (int i = 0; i < ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildCount(); i++){

            }
        });

        ((RelativeLayout) findViewById(R.id.nextButton)).setOnClickListener(function -> {
            Log.d("NextButtonPressed", "Next button pressed");
            if (!((RelativeLayout) findViewById(R.id.prevButton)).isEnabled()) {
                ((RelativeLayout) findViewById(R.id.prevButton)).setEnabled(true);
                ((RelativeLayout) findViewById(R.id.prevButton)).setAlpha(1F);
            }
            for (int i = 0; i < ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildCount(); i++){
                Log.d("IMOD2IS0", "Entering the if clause");
                Log.d("COLORCODENUMBER", "" + ((CardView) ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildAt(i)).getCardBackgroundColor().getDefaultColor());
                if (((CardView) ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildAt(i)).getCardBackgroundColor().getDefaultColor() == Color.RED) {
                    Log.d("IVALUE", "The value of i is " + i);
                    ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
                    if (i + 2 < ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildCount()) {
                        Log.d("ENTRY", "Entered the third if statement");
                        ((LinearLayout) findViewById(R.id.linearLayoutContainerForIndicator)).getChildAt(i + 2).setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    }
                    else {
                        //Disable the next button
                        ((RelativeLayout) findViewById(R.id.nextButton)).setEnabled(false);
                    }
                }
            }
        });

        ((CardView) findViewById(R.id.help1)).setOnClickListener(function -> {
            if (((LinearLayout) findViewById(R.id.guideBase)).getVisibility() != View.VISIBLE) {
                //To prevent double clicking
                Log.d("Help1ButtonActivated", "Help1 button has been activated");
                ((LinearLayout) findViewById(R.id.base1Layout)).setVisibility(View.VISIBLE);
                AtomicReference<Float> TempValue = new AtomicReference<>(0F);
                new CountDownTimer(10, 10) {
                    @Override
                    public void onTick(long l) {
                        float currAlphaVal = ((LinearLayout) findViewById(R.id.base1Layout)).getAlpha();
                        TempValue.set(currAlphaVal += 0.1);
                    }

                    @Override
                    public void onFinish() {
                        Log.d("CurrentAlphaValueForHelp1", "" + TempValue.get());
                        ((LinearLayout) findViewById(R.id.base1Layout)).setAlpha(TempValue.get());
                        if (((LinearLayout) findViewById(R.id.base1Layout)).getAlpha() < 1F) {
                            this.start();
                        }
                    }
                }.start();
                TempValue.set(0F);
                //Once the countdown timer has finished
                new CountDownTimer(2000, 2000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        new CountDownTimer(10, 10) {
                            @Override
                            public void onTick(long l) {
                                float currAlphaVal = ((LinearLayout) findViewById(R.id.base1Layout)).getAlpha();
                                TempValue.set(currAlphaVal -= 0.1);
                            }

                            @Override
                            public void onFinish() {
                                Log.d("CurrentAlphaValueForHelp1", "" + TempValue.get());
                                ((LinearLayout) findViewById(R.id.base1Layout)).setAlpha(TempValue.get());
                                if (((LinearLayout) findViewById(R.id.base1Layout)).getAlpha() > 0F) {
                                    this.start();
                                }
                                new CountDownTimer(500, 500) {
                                    @Override
                                    public void onTick(long l) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        ((LinearLayout) findViewById(R.id.base1Layout)).setVisibility(View.GONE);
                                    }
                                }.start();
                            }
                        }.start();
                    }
                }.start();
            }
        });
        ((CardView) findViewById(R.id.help2)).setOnClickListener(function -> {
            if (((LinearLayout) findViewById(R.id.guideBase)).getVisibility() != View.VISIBLE) {
                Log.d("Help1ButtonActivated", "Help1 button has been activated");
                ((LinearLayout) findViewById(R.id.base2Layout)).setVisibility(View.VISIBLE);
                AtomicReference<Float> TempValue = new AtomicReference<>(0F);
                new CountDownTimer(10, 10) {
                    @Override
                    public void onTick(long l) {
                        float currAlphaVal = ((LinearLayout) findViewById(R.id.base2Layout)).getAlpha();
                        TempValue.set(currAlphaVal += 0.1);
                    }

                    @Override
                    public void onFinish() {
                        Log.d("CurrentAlphaValueForHelp1", "" + TempValue.get());
                        ((LinearLayout) findViewById(R.id.base2Layout)).setAlpha(TempValue.get());
                        if (((LinearLayout) findViewById(R.id.base2Layout)).getAlpha() < 1F) {
                            this.start();
                        }
                    }
                }.start();
                TempValue.set(0F);
                //Once the countdown timer has finished
                new CountDownTimer(2000, 2000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        new CountDownTimer(10, 10) {
                            @Override
                            public void onTick(long l) {
                                float currAlphaVal = ((LinearLayout) findViewById(R.id.base2Layout)).getAlpha();
                                TempValue.set(currAlphaVal -= 0.1);
                            }

                            @Override
                            public void onFinish() {
                                Log.d("CurrentAlphaValueForHelp1", "" + TempValue.get());
                                ((LinearLayout) findViewById(R.id.base2Layout)).setAlpha(TempValue.get());
                                if (((LinearLayout) findViewById(R.id.base2Layout)).getAlpha() > 0F) {
                                    this.start();
                                }
                                new CountDownTimer(500, 500) {
                                    @Override
                                    public void onTick(long l) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        ((LinearLayout) findViewById(R.id.base2Layout)).setVisibility(View.GONE);
                                    }
                                }.start();
                            }
                        }.start();
                    }
                }.start();
            }
        });
        ((CardView) findViewById(R.id.help3)).setOnClickListener(function -> {
            if (((LinearLayout) findViewById(R.id.guideBase)).getVisibility() != View.VISIBLE) {
                Log.d("Help1ButtonActivated", "Help1 button has been activated");
                ((LinearLayout) findViewById(R.id.base3Layout)).setVisibility(View.VISIBLE);
                AtomicReference<Float> TempValue = new AtomicReference<>(0F);
                new CountDownTimer(10, 10) {
                    @Override
                    public void onTick(long l) {
                        float currAlphaVal = ((LinearLayout) findViewById(R.id.base3Layout)).getAlpha();
                        TempValue.set(currAlphaVal += 0.1);
                    }

                    @Override
                    public void onFinish() {
                        Log.d("CurrentAlphaValueForHelp1", "" + TempValue.get());
                        ((LinearLayout) findViewById(R.id.base3Layout)).setAlpha(TempValue.get());
                        if (((LinearLayout) findViewById(R.id.base3Layout)).getAlpha() < 1F) {
                            this.start();
                        }
                    }
                }.start();
                TempValue.set(0F);
                //Once the countdown timer has finished
                new CountDownTimer(2000, 2000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        new CountDownTimer(10, 10) {
                            @Override
                            public void onTick(long l) {
                                float currAlphaVal = ((LinearLayout) findViewById(R.id.base3Layout)).getAlpha();
                                TempValue.set(currAlphaVal -= 0.1);
                            }

                            @Override
                            public void onFinish() {
                                Log.d("CurrentAlphaValueForHelp1", "" + TempValue.get());
                                ((LinearLayout) findViewById(R.id.base3Layout)).setAlpha(TempValue.get());
                                if (((LinearLayout) findViewById(R.id.base3Layout)).getAlpha() > 0F) {
                                    this.start();
                                }
                                new CountDownTimer(500, 500) {
                                    @Override
                                    public void onTick(long l) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        ((LinearLayout) findViewById(R.id.base3Layout)).setVisibility(View.GONE);
                                    }
                                }.start();
                            }
                        }.start();
                    }
                }.start();
            }
        });
        ((CardView) findViewById(R.id.help4)).setOnClickListener(function -> {
            if (((LinearLayout) findViewById(R.id.guideBase)).getVisibility() != View.VISIBLE) {
                Log.d("Help1ButtonActivated", "Help1 button has been activated");
                ((LinearLayout) findViewById(R.id.base4Layout)).setVisibility(View.VISIBLE);
                AtomicReference<Float> TempValue = new AtomicReference<>(0F);
                new CountDownTimer(10, 10) {
                    @Override
                    public void onTick(long l) {
                        float currAlphaVal = ((LinearLayout) findViewById(R.id.base4Layout)).getAlpha();
                        TempValue.set(currAlphaVal += 0.1);
                    }

                    @Override
                    public void onFinish() {
                        Log.d("CurrentAlphaValueForHelp1", "" + TempValue.get());
                        ((LinearLayout) findViewById(R.id.base4Layout)).setAlpha(TempValue.get());
                        if (((LinearLayout) findViewById(R.id.base4Layout)).getAlpha() < 1F) {
                            this.start();
                        }
                    }
                }.start();
                TempValue.set(0F);
                //Once the countdown timer has finished
                new CountDownTimer(2000, 2000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        new CountDownTimer(10, 10) {
                            @Override
                            public void onTick(long l) {
                                float currAlphaVal = ((LinearLayout) findViewById(R.id.base4Layout)).getAlpha();
                                TempValue.set(currAlphaVal -= 0.1);
                            }

                            @Override
                            public void onFinish() {
                                Log.d("CurrentAlphaValueForHelp1", "" + TempValue.get());
                                ((LinearLayout) findViewById(R.id.base4Layout)).setAlpha(TempValue.get());
                                if (((LinearLayout) findViewById(R.id.base4Layout)).getAlpha() > 0F) {
                                    this.start();
                                }
                                new CountDownTimer(500, 500) {
                                    @Override
                                    public void onTick(long l) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        ((LinearLayout) findViewById(R.id.base4Layout)).setVisibility(View.GONE);
                                    }
                                }.start();
                            }
                        }.start();
                    }
                }.start();
            }
        });
    }
}
