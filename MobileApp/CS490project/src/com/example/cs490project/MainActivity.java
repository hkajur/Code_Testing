package com.example.cs490project;
/*
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
*/
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;


public class MainActivity extends Activity {

   private EditText  username=null;
   private EditText  password=null;
   private Button login;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      username = (EditText)findViewById(R.id.ucidText);
      password = (EditText)findViewById(R.id.passText);
      login = (Button)findViewById(R.id.button1);
      
      
      loginFunctions userFunctions = new loginFunctions();        
      // user is not logged in show login screen
      Intent login = new Intent(getApplicationContext(), LoginActivity.class);
      login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(login);
      // Closing dashboard screen
      
   }

   public void login(View view){
	   String ucid = username.getText().toString();
	   String pass = password.getText().toString();
	   loginFunctions session = new loginFunctions(ucid,pass);
	   JSONObject jsonObject = session.loginUser();
	   if(true){
		   Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();		   
	   }
	   else{
		   Toast.makeText(getApplicationContext(), "Incorrect UCID or password",Toast.LENGTH_SHORT).show();   
	   }
	   
//	   Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
//	   Toast.makeText(getApplicationContext(), "Incorrect UCID or password",Toast.LENGTH_SHORT).show();
   }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

}