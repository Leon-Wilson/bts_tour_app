package fs.sdvbs.bts_tour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        try {
            player current_player = player.getInstance();
            current_player.setQuizList();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent login_intent = new Intent(this, nav_main.class);
        startActivity(login_intent);
        finish();
    }


}
