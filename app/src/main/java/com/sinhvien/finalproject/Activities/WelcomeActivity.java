package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.View;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.sinhvien.finalproject.R;

import java.util.ArrayList;
import java.util.Locale;


public class WelcomeActivity extends AppCompatActivity{

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        fragmentManager = getSupportFragmentManager();
        
        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnBoarding());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_ob, paperOnboardingFragment);
        fragmentTransaction.commit();
    }



    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {
        PaperOnboardingPage src1 = new PaperOnboardingPage("Sales Manager", "Manage sales at the store through the App, cash out, print invoices quickly, manage orders, track sales reports at all times.", Color.parseColor("#ffffff"),
                R.drawable.caffeslide,R.drawable.circle_drawable);
        PaperOnboardingPage src2 = new PaperOnboardingPage("Simple, easy to use", "Simple, friendly and smart interface. It only takes staff a few minutes to get acquainted with sales.", Color.parseColor("#ffffff"),
                R.drawable.caffeslide1,R.drawable.circle_drawable);
        PaperOnboardingPage src3 = new PaperOnboardingPage("Cost savings", "Free installation, deployment, upgrades and support. Cheaper than a glass of iced tea a day.", Color.parseColor("#ffffff"),
                R.drawable.caffeslide2,R.drawable.circle_drawable);
        PaperOnboardingPage src4 = new PaperOnboardingPage("", "", Color.parseColor("#ffffff"),
                R.drawable.logocoffe1,R.drawable.circle_drawable);


        ArrayList<PaperOnboardingPage> element = new ArrayList<>();
        element.add(src1);
        element.add(src2);
        element.add(src3);
        element.add(src4);
        return element;


    }

    ;



    //chuyển sang trang đăng nhập
    public void callLoginFromWel(View view)
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_login),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
    // chuyển sang trang đăng ký
    public void callSignUpFromWel(View view)
    {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_signup),"transition_signup");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }

}