package fs.sdvbs.bts_tour;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class script_main extends AppCompatActivity {

    player current_player;
    script current_script;

    TextView script_name;
    TextView script_degrees;
    TextView script_description;
    TextView script_building;

    ListView script_important;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        script_name = findViewById(R.id.script_name);
        //script_degrees = findViewById(R.id.script_degrees);
        script_description = findViewById(R.id.script_description);
        script_important = findViewById(R.id.script_important);
        script_building = findViewById(R.id.script_building);


        current_player = player.getInstance();
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            int selected = extras.getInt("selected_script");
            current_script = current_player.getCurrentUser().getScripts()[selected];
        }

        script_name.setText(current_script.getName());
        script_building.setText(String.valueOf(current_script.building_num));
        //script_degrees.setText(current_script.getName());
        String[] important_things = new String[current_script.importance.length];
        for(int i = 0; i < important_things.length; i++)
        {
            important_things[i] = current_script.importance[i];
        }

        ArrayAdapter<String> important_adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1 ,important_things);
        script_important.setAdapter(important_adapter);

        script_description.setText(current_script.getDescription());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void loadImportances()
    {

    }
}
