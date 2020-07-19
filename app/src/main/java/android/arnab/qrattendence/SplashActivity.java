package android.arnab.qrattendence;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;
import com.hanks.htextview.fade.FadeTextView;
import com.hanks.htextview.fall.FallTextView;
import com.hanks.htextview.scale.ScaleTextView;

public class SplashActivity extends AppCompatActivity
{
    ImageView brandLogo;
    FadeTextView brandName;

    Class nextActivity=TeacherHome.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        brandLogo=findViewById(R.id.brandLogo);
        brandName=findViewById(R.id.brandName);


        Glide.with(this).load(R.drawable.company_logo_final).into(brandLogo);


        Animation aniFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_fade_in);
        brandLogo.startAnimation(aniFadeIn);
        brandName.animateText("TRIPLE A DEV TEAM");
        brandName.setAnimationDuration(1000);
        brandName.setProgress(0);
        brandName.animate();
        aniFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                Intent intent=new Intent(getApplicationContext(),nextActivity);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
