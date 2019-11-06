package com.travelandtime.Roulette;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.travelandtime.Confirmations.Confirms;
import com.travelandtime.R;
import com.travelandtime.Social.Community;

import java.util.Random;

public class RouletteGame extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private ImageView rot;
    private ImageView pointer;
    private ImageView imageView3;
    private EditText bet;
    Animation Blink;
    private String betData="";
    private ImageView gameWonImg;
    private ImageView gameLostImg;
    private ImageView gameWonBtn;
    private ImageView starTag;
    private ImageView help;





    Random r;
    int degree=0,old_deg=0;
    // because there are 36 sector on the wheel(10degree for each)
    private  static final float factor=4.80f;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.roulettegame);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        rot = (ImageView) findViewById(R.id.rot);
        pointer = (ImageView) findViewById(R.id.pointer);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        bet = (EditText) findViewById(R.id.bet);
        gameWonImg = (ImageView) findViewById(R.id.game_won_img);
        gameLostImg = (ImageView) findViewById(R.id.game_lost_img);
        gameWonBtn = (ImageView) findViewById(R.id.game_won_btn);
        starTag = (ImageView) findViewById(R.id.star_tag);
        help = (ImageView) findViewById(R.id.help);

        r=new Random();


        // clik on help button
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RouletteGame.this, Confirms.class);
                intent.putExtra("page","RouletteGame");
                intent.putExtra("lay","RouletteGame");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // Click on spin button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starTag.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                rot.setVisibility(View.VISIBLE);
                // Collect bet data into a variable
                betData = bet.getText().toString();
                // check bet box is empty
                if (bet.getText().toString().isEmpty()) {
                    Toast.makeText(RouletteGame.this, "Enter Your Bet", Toast.LENGTH_SHORT).show();
                } else {
                    imageView3.setImageResource(R.drawable.nob);
                    old_deg = degree % 360;
                    degree = r.nextInt(3600) + 720;
                    RotateAnimation rotate = new RotateAnimation(old_deg, degree, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(3600);
                    rotate.setFillAfter(true);
                    rotate.setInterpolator(new DecelerateInterpolator());
                    rotate.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            Blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.r_anim);
                            textView.setText("");
                            textView.startAnimation(Blink);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            textView.setText(currentno(360 - (degree % 360)));

                            if (textView.getText().toString().equals(String.valueOf(betData))) {
                                gameWonImg.setVisibility(View.VISIBLE);
                                gameWonBtn.setVisibility(View.VISIBLE);
                                starTag.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);
                                button.setVisibility(View.GONE);
                                bet.setVisibility(View.GONE);
                            } else {
                                gameLostImg.setVisibility(View.VISIBLE);
                                gameWonBtn.setVisibility(View.VISIBLE);
                                starTag.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);
                                button.setVisibility(View.GONE);
                                bet.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    rot.startAnimation(rotate);
                }
            }
        });
        // close won and lost popup
        gameWonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameWonImg.setVisibility(View.GONE);
                gameLostImg.setVisibility(View.GONE);
                gameWonBtn.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                bet.setText("");
                bet.setVisibility(View.VISIBLE);

            }
        });
    }  // onCreate closer



    private String currentno(int degrees){
        String text="";
        // do this for each of the number
        if(degrees>=(factor*1) && degrees<(factor*3)){
            text="28";
        }
        if(degrees>=(factor*3) && degrees<(factor*5)){
            text="9";
        }
        if(degrees>=(factor*5) && degrees<(factor*7)){
            text="26";
        }
        if(degrees>=(factor*7) && degrees<(factor*9)){
            text="30";
        }
        if(degrees>=(factor*9) && degrees<(factor*11)){
            text="11";
        }
        if(degrees>=(factor*11) && degrees<(factor*13)){
            text="7";
        }
        if(degrees>=(factor*13) && degrees<(factor*15)){
            text="20";
        }
        if(degrees>=(factor*15) && degrees<(factor*17)){
            text="32";
        }
        if(degrees>=(factor*17) && degrees<(factor*19)){
            text="17";
        }
        if(degrees>=(factor*19) && degrees<(factor*21)){
            text="5";
        }
        if(degrees>=(factor*21) && degrees<(factor*23)){
            text="22";
        }
        if(degrees>=(factor*23) && degrees<(factor*25)){
            text="34";
        }
        if(degrees>=(factor*25) && degrees<(factor*27)){
            text="16";
        }
        if(degrees>=(factor*27) && degrees<(factor*29)){
            text="3";
        }
        if(degrees>=(factor*29) && degrees<(factor*31)){
            text="24";
        }
        if(degrees>=(factor*31) && degrees<(factor*33)){
            text="36";
        }
        if(degrees>=(factor*33) && degrees<(factor*35)){
            text="13";
        }
        if(degrees>=(factor*35) && degrees<(factor*37)){
            text="1";
        }
        if(degrees>=(factor*37) && degrees<(factor*39)){
            text="00";
        }
        if(degrees>=(factor*39) && degrees<(factor*41)){
            text="27";
        }
        if(degrees>=(factor*41) && degrees<(factor*43)){
            text="10";
        }
        if(degrees>=(factor*43) && degrees<(factor*45)){
            text="25";
        }
        if(degrees>=(factor*45) && degrees<(factor*47)){
            text="29";
        }
        if(degrees>=(factor*47) && degrees<(factor*49)){
            text="12";
        }
        if(degrees>=(factor*49) && degrees<(factor*51)){
            text="8";
        }
        if(degrees>=(factor*51) && degrees<(factor*53)){
            text="19";
        }
        if(degrees>=(factor*53) && degrees<(factor*55)){
            text="31";
        }
        if(degrees>=(factor*55) && degrees<(factor*57)){
            text="18";
        }
        if(degrees>=(factor*57) && degrees<(factor*59)){
            text="6";
        }
        if(degrees>=(factor*59) && degrees<(factor*61)){
            text="21";
        }
        if(degrees>=(factor*61) && degrees<(factor*63)){
            text="33";
        }
        if(degrees>=(factor*63) && degrees<(factor*65)){
            text="16";
        }
        if(degrees>=(factor*65) && degrees<(factor*67)){
            text="4";
        }
        if(degrees>=(factor*67) && degrees<(factor*69)){
            text="23";
        }
        if(degrees>=(factor*69) && degrees<(factor*71)){
            text="35";
        }
        if(degrees>=(factor*71) && degrees<(factor*73)){
            text="14";
        }
        if(degrees>=(factor*73) && degrees<(factor*75)){
            text="2";
        }
        if((degrees>=(factor*75) && degrees<360) || (degrees>=0 && degrees<(factor*1))){
            text="0";
        }


        return text;
    }

    // Backkey pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {

                Intent back1Intent = new Intent(RouletteGame.this, Community.class);
                startActivity(back1Intent);
                finish();

        }

        return super.onKeyDown(keyCode, event);
    }
}
