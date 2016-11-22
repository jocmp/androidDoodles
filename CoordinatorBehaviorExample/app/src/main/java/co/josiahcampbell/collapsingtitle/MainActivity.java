package co.josiahcampbell.collapsingtitle;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.25f;
    private static final int ALPHA_ANIMATIONS_DURATION = 100;

    private boolean isTitleVisible;

    private TextView title;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTitleVisible = false;

        bindActivity();

        appBarLayout.addOnOffsetChangedListener(this);

        toolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(title, 0, View.INVISIBLE);
    }

    private void bindActivity() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        title = (TextView) findViewById(R.id.main_textview_title);
        appBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        // maxScroll returns the scroll range of all children
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

//        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!isTitleVisible) {
                startAlphaAnimation(title, ALPHA_ANIMATIONS_DURATION, VISIBLE);
                isTitleVisible = true;
                toolbar.setBackground(ContextCompat.getDrawable(this, R.color.primary));
            }
        } else if (isTitleVisible) {
            startAlphaAnimation(title, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
            isTitleVisible = false;
            toolbar.setBackground(null);
        }
    }

    public static void startAlphaAnimation(View view, long duration, int visibility) {
        AlphaAnimation alphaAnimation = null;
        if (visibility == VISIBLE) {
            alphaAnimation = new AlphaAnimation(0f, 1f);
        } else {
            alphaAnimation = new AlphaAnimation(1f, 0f);
        }
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }
}
