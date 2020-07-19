package android.arnab.qrattendence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Credits extends AppCompatActivity {
    final String named = "Abhijit Dey", nameb = "Arnab Banerjee", names = "Adarsha Shaw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        final LinearLayout t = findViewById(R.id.titledev);
        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadein.setDuration(4000);
        fadein.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final TextView t2 = findViewById(R.id.nameall);
                t2.setText(named + "\n" + names + "\n" + nameb);
                final Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                final Animation fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                fadein.setDuration(2000);
                fadeout.setDuration(2000);
                fadein.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation delay = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.delay);
                        delay.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                fadeout.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        Animation moveup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
                                        moveup.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                LinearLayout temp = findViewById(R.id.titleafter);
                                                temp.setVisibility(View.VISIBLE);
                                                TextView backd = findViewById(R.id.backd);
                                                TextView frontd = findViewById(R.id.frontd);
                                                TextView ui = findViewById(R.id.ui);
                                                TextView launchi = findViewById(R.id.launchi);
                                                TextView launcha = findViewById(R.id.launcha);
                                                TextView backname = findViewById(R.id.backname);
                                                TextView frontname = findViewById(R.id.frontname);
                                                TextView uiname = findViewById(R.id.uiname);
                                                TextView launchiname = findViewById(R.id.launchiname);
                                                TextView launchaname = findViewById(R.id.launchaname);
                                                backname.setText(nameb);
                                                frontname.setText(named+"\n"+names+"\n"+nameb);
                                                uiname.setText("(Lead)"+named+"\n(Ast.)"+names);
                                                launchaname.setText(named+"\n"+nameb);
                                                launchiname.setText(names);
                                                Animation last = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                                                last.setDuration(2000);
                                                backd.startAnimation(last);
                                                frontd.startAnimation(last);
                                                ui.startAnimation(last);
                                                launcha.startAnimation(last);
                                                launchi.startAnimation(last);
                                                frontname.startAnimation(last);
                                                backname.startAnimation(last);
                                                uiname.startAnimation(last);
                                                launchaname.startAnimation(last);
                                                launchiname.startAnimation(last);
                                                backd.setVisibility(View.VISIBLE);
                                                frontd.setVisibility(View.VISIBLE);
                                                ui.setVisibility(View.VISIBLE);
                                                launchi.setVisibility(View.VISIBLE);
                                                launcha.setVisibility(View.VISIBLE);
                                                backname.setVisibility(View.VISIBLE);
                                                frontname.setVisibility(View.VISIBLE);
                                                uiname.setVisibility(View.VISIBLE);
                                                launchaname.setVisibility(View.VISIBLE);
                                                launchiname.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                        t.startAnimation(moveup);
                                        t.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                t2.startAnimation(fadeout);
                                t2.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        t2.startAnimation(delay);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                t2.startAnimation(fadein);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        t.startAnimation(fadein);
    }
    @Override
    protected void onResume() {
        super.onResume();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}