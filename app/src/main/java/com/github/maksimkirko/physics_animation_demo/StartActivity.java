package com.github.maksimkirko.physics_animation_demo;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

public class StartActivity extends Activity {

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        relativeLayout = findViewById(R.id.layout_activity_main);

        initAnimation();
    }

    private void initAnimation() {
        SpringForce springForce = new SpringForce(0f);
        relativeLayout.setPivotX(0f);
        relativeLayout.setPivotY(0f);
        SpringAnimation springAnimation =
                new SpringAnimation(relativeLayout, DynamicAnimation.ROTATION);
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);
        springAnimation.setSpring(springForce);
        springAnimation.setStartValue(80f);

        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value,
                                       float velocity) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                float height = (float) displayMetrics.heightPixels;
                float width = (float) displayMetrics.widthPixels;
                relativeLayout.animate()
                        .setStartDelay(1)
                        .translationXBy(width / 2)
                        .translationYBy(height)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {
                            }
                        })
                        .setInterpolator(new DecelerateInterpolator(1f))
                        .start();
            }
        });

        springAnimation.start();
    }
}
