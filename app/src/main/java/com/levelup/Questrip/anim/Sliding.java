package com.levelup.Questrip.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.levelup.Questrip.R;
import com.levelup.Questrip.common.Bootstrapper;

/**
 * 화면 위아래로 떠오르고 사라지는 애니메이션을 구현하는 클래스입니다.
 *
 * 담당자: 김호, 이동욱
 *
 * 역할: 대상을 화면 위아래로 떠오르거나 사라지게 합니다.
 */
public final class Sliding {

    private static Animation animSlidingDown;
    private static Animation animSlidingUp;

    /**
     * 애니메이션을 초기화합니다.
     * 반드시 앱 구동 시 초기화되어야 합니다.
     * @param activity 현재 액티비티
     */
    public static void init(Bootstrapper activity) {
        animSlidingDown = AnimationUtils.loadAnimation(activity, R.anim.sliding_down);
        animSlidingUp = AnimationUtils.loadAnimation(activity, R.anim.sliding_up);
    }

    /**
     * 화면 위로 떠오르는 애니메이션을 실행합니다.
     * @param view 대상
     */
    public static void up(View view) {
        // 터치 불가, 화면 표시
        view.setClickable(false);
        view.setVisibility(View.VISIBLE);
        // 등장 후 터치 가능
        doAnimation(view, animSlidingUp, () -> view.setClickable(true), 1f);
    }

    /**
     * 화면 아래로 사라지는 애니메이션을 실행합니다.
     * @param view 대상
     */
    public static void down(View view) {
        // 터치 불가, 화면 표시
        view.setClickable(false);
        view.setVisibility(View.VISIBLE);
        // 퇴장 후 안보이게
        doAnimation(view, animSlidingDown, () -> view.setVisibility(View.GONE), 0f);
    }

    /**
     * 애니메이션을 실행합니다.
     * @param view 대상
     * @param animation 애니메이션
     * @param onEnd 종료 이벤트
     * @param alpha 목적 투명도
     */
    private static void doAnimation(View view, Animation animation, Runnable onEnd, float alpha) {
        // 투명도 조절
        view.animate().setDuration(animation.getDuration()).alpha(alpha);
        // 종료 이벤트 호출
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onEnd.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // 애니메이션 실행
        view.startAnimation(animation);
    }

}
