package nkosi.roger.manutdcom.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

public class Splash extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUi();
    }

    private void setUi() {


//        addSlide(AppIntroFragment.newInstance("Welcome!", "Ryan Giggs holds the record for the fastest goal by a Manchester United player (15 seconds v Southampton, 18 November 19915", R.drawable.library, Color.parseColor("#A20016")));
//        addSlide(AppIntroFragment.newInstance("Welcome!", "Paul Scholes has made the third-highest number of appearances for Manchester United.", R.drawable.library_1, Color.parseColor("#A20016")));
//        addSlide(AppIntroFragment.newInstance("Simple, yet Customizable", "The library offers a lot of customization, while keeping it simple for those that like simple.", R.drawable.library, Color.parseColor("#00796B")));
//        addSlide(AppIntroFragment.newInstance("Explore", "Feel free to explore the rest of the library demo!", R.drawable.library_1, Color.parseColor("#00796B")));
//        addSlide(AppIntroFragment.newInstance("Explore", "yfu", R.drawable.library_1, Color.parseColor("#00796B")));
        // Hide Skip/Done button.

        addSlide(new FragmentThree());
        addSlide(new FragmentTwo());
        addSlide(new AppIntorFragmentOne());

        showSkipButton(true);
        setProgressButtonEnabled(true);

        setDepthAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        startActivity(new Intent(this, Home.class));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(this, Home.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
