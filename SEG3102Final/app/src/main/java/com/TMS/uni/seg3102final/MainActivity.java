package com.TMS.uni.seg3102final;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.TMS.uni.seg3102final.tasks.LoginTask;
import com.TMS.uni.seg3102final.tasks.RegisterTask;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    JSONObject response;
    public static final String IP_ADDRESS = "10.0.2.2";
    public ProgressDialog progress;
    private boolean register = false;
    LinearLayout registerLayout;
    ImageButton imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progress = new ProgressDialog(this);
        registerLayout = (LinearLayout) findViewById(R.id.registerLayout);
        //imgButton =(ImageButton)findViewById(R.id.imageButton);
        //imgButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  Toast.makeText(getApplicationContext(), "You download is resumed", Toast.LENGTH_LONG).show();
            //}
        //});
    }

    public void register(View view) {

        if (registerLayout.getVisibility() == View.GONE) {
            Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            registerLayout.startAnimation(slideUp);
            registerLayout.setVisibility(View.VISIBLE);
        }else{

            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            EditText email = (EditText) findViewById(R.id.email);
            EditText firstName = (EditText) findViewById(R.id.firstName);
            EditText lastName = (EditText) findViewById(R.id.lastName);

            RadioGroup rg = (RadioGroup)findViewById(R.id.radio_group_user);
            String radioValue = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

            String[] params = {radioValue, username.getText().toString(), password.getText().toString(),email.getText().toString(),firstName.getText().toString(),lastName.getText().toString()};

            progress.setTitle("Register");
            progress.setMessage("Registering Please Wait...");
            progress.show();
            new RegisterTask(this).execute(params);
        }

    }

    public void displayError() {
        Toast.makeText(getApplicationContext(), "Invalid User Or Password", Toast.LENGTH_LONG).show();
    }

    public void dismiss() {
        progress.dismiss();
    }

    public void login(View view) {
        if (registerLayout.getVisibility() == View.GONE) {
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            String[] params = {username.getText().toString(), password.getText().toString()};

            progress.setTitle("Log in");
            progress.setMessage("Logging in...");
            progress.show();
            new LoginTask(this).execute(params);
        }else{
            Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            registerLayout.startAnimation(slideDown);
            registerLayout.setVisibility(View.GONE);
        }
    }


    public void registerMessage(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Information");

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();

            }
        });

        // Showing Alert Message
        alertDialog.show();


    }

    public void loadOperations(String type) {
        Intent intent;

        if(type.equals("instructor")) {
            startActivity(new Intent(this, InstructorOperations.class));
        }else if(type.equals("student")) {
            intent = new Intent(this, StudentOperations.class);
            intent.putExtra("isLiason", false);
            startActivity(intent);
        }else if(type.equals("liason")) {
            intent = new Intent(this, StudentOperations.class);
            intent.putExtra("isLiason", true);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
