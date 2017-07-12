package com.example.adrogozhilov.bonus;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public String cquery ;
   // public final static String EXTRA_QUERY="";
   private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Вызываем наше окно с результатом
        TextView textView = (TextView)findViewById(R.id.txtb);
        // задаём текст
        textView.setText(" ");
        // ссылку на кабинет
       // TextView textsing = (TextView) findViewById(R.id.singin);
        TextView textsing = (TextView) findViewById(R.id.singin);
        //textsing.setText(Html.fromHtml(getResources().getString(R.string.singin)));
        textsing.setText(Html.fromHtml( "<a href=\"http://uniweb.factor.ua:8080/tdfactor\">Личный кабинет</a> " ));
        textsing.setMovementMethod(LinkMovementMethod.getInstance());

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

        if (id == R.id.action_settings) {
            onClickSetting();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
// Нажатие на инфо
    public void onClickSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Информация")
                .setMessage("автор: "+getString(R.string.cauthor)+"\n"+getString(R.string.cversion))
                //.setIcon(R.drawable.)
                .setCancelable(false)
                .setNegativeButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void proverka(){
   //     TextView textView = (TextView)findViewById(R.id.txtb);
// задаём текст
  //      textView.setText("Вы не ввели номер телефона...");
  //      textView = (TextView)findViewById(R.id.txtbon);
 //       textView.setText(" ");
        Toast toast = Toast.makeText(getApplicationContext(),"Вы не ввели номер телефона...",Toast.LENGTH_SHORT);
        toast.show();
    }
    public void onClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        cquery = editText.getText().toString();
        cquery  = cquery .replaceAll(" ", "");
        if("".equals(cquery)||(cquery.length()<9)) {
           proverka();
        }
        else{
            if(!"38".equals(cquery.substring(0,2))){
                cquery="38"+cquery.trim();
            }

            Task tt = new Task();
            tt.execute();
        }

    }

    public void OnClick2(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        cquery = editText.getText().toString();
        cquery = cquery.replaceAll(" ", "");
        if("".equals(cquery)||(cquery.length()<9)) {
            proverka();
        } else {
            if(!"38".equals(cquery.substring(0,2))){
                cquery="38"+cquery.trim();
            }
            switch (view.getId()) {
                case R.id.button2:
                    Task2 tt2 = new Task2();
                    tt2.execute();
                    // TODO Call second activity



                    break;
                default:
                    break;
            }

        }
    }

    public void OnClick3(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        cquery = editText.getText().toString();
        cquery = cquery.replaceAll(" ", "");
        if("".equals(cquery)||(cquery.length()<9)) {
            proverka();
        } else {
            if(!"38".equals(cquery.substring(0,2))){
                cquery="38"+cquery.trim();
            }
            switch (view.getId()) {
                case R.id.button3:
                    Task3 tt3 = new Task3();
                    tt3.execute();
                    // вызов 3его активити
                    break;
                default:
                    break;
            }

        }
    }

    // Первое активити
    class Task extends AsyncTask<Void,Void,String > {
        private ProgressDialog spinner;
        public ProgressDialog dialog;
        private String responseString="";
        Context ctx;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinner = new ProgressDialog(MainActivity.this);
            spinner.setMessage("Идет загрузка...");
            spinner.show();
        }
        @Override
        protected String doInBackground(Void... urls) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(getString(R.string.cConnect)+cquery);
            HttpResponse response;

            //	Log.d("TAG", "Начали");
            try {
                Log.d("TAG_", "Начали");
                Log.d("TAG_", responseString);
                response = httpclient.execute(httppost);
                responseString = EntityUtils.toString(response.getEntity(), "UTF-8");//response.toString() ;
                Log.d("TAG_", responseString);
                Log.d("TAG_", "Конец");

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return responseString;

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            spinner.dismiss();
            String cnumkarta="";
            String cnamevlad="";
            String ctyp="";
            int bonus=0;

            super.onPostExecute(result);
            try {
                JSONObject dataJsonObj= new JSONObject(result);
                cnumkarta=dataJsonObj.getString("name");
                cnamevlad=dataJsonObj.getString("vlad");
                bonus=Integer.parseInt(dataJsonObj.getString("bonus"));
                ctyp=dataJsonObj.getString("aktiv");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("TAG_", result);

          //  Toast toast = Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT);
          //  toast.show();

            TextView textView = (TextView)findViewById(R.id.txtbon);
            if("1".equals(ctyp)){
              ctyp="Ваша карта активна";
            }
            else
            {
                ctyp="Ваша карта не активна.";
            }

// задаём текст

            if ("".equals(cnumkarta) ) {
                textView.setText("");
                Toast toast = Toast.makeText(getApplicationContext(),"Пользователь с такими данными не зарегистрирован.",Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                textView.setText(ctyp+" №"+cnumkarta+"\n"+cnamevlad+"\n"+"Остаток:"+bonus);
            }
            textView = (TextView)findViewById(R.id.txtb);


        }
    }

//**************************************** Второе активити**********************************************
    class Task2 extends AsyncTask<Void,Void,String > {
        private ProgressDialog spinner;
        private String responseString="";
        @Override
        protected void onPreExecute() {
            spinner = new ProgressDialog(MainActivity.this);
            spinner.setMessage("Идет загрузка...");
            spinner.show();
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(Void... urls) {

           // Intent intent = getIntent();
          //  String cquery = intent.getStringExtra(MainActivity.EXTRA_QUERY);
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(getString(R.string.cConnecthist)+cquery);
            HttpResponse response;

            try {
                Log.d("TAGH", "История1");
                response = httpclient.execute(httppost);
                responseString = EntityUtils.toString(response.getEntity());//response.toString() ;
                Log.d("TAGH", "История2");
                Log.d("TAGH", getString(R.string.cConnecthist)+cquery);
                Log.d("TAGH", responseString);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return responseString;

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            boolean bFlag=false;
            spinner.dismiss();
            super.onPostExecute(result);
            ArrayList anames = new ArrayList();

            try {
                JSONObject dataJsonObj= new JSONObject(result);
                JSONArray dataar = dataJsonObj.getJSONArray("data");
                JSONArray bonusar = dataJsonObj.getJSONArray("bonus");
                JSONArray commarr = dataJsonObj.getJSONArray("comment");
                JSONArray typarr = dataJsonObj.getJSONArray("typ");
                Log.d("LOG_JS", ""+dataar.getString(1) );
                for (int i = 0; i < dataar.length(); i++) {

                    anames.add(i+1+". "+dataar.getString(i).trim()+"\n"+bonusar.getString(i).trim()+" "+typarr.getString(i).trim()+"\n"+commarr.getString(i).trim());


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (anames.size()==0) {
                Toast toast = Toast.makeText(getApplicationContext(),"Пользователь с такими данными не зарегистрирован.",Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                // intent.putExtra(EXTRA_QUERY, cquery);
                intent.putExtra("anames", anames);
                startActivity(intent);
            }



        }
    }

    //**************************************** Третье активити**********************************************
    class Task3 extends AsyncTask<Void,Void,String > {
        private String responseString="";
        private ProgressDialog spinner;
        @Override
        protected void onPreExecute() {
            spinner = new ProgressDialog(MainActivity.this);
            spinner.setMessage("Идет загрузка...");
            spinner.show();
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(Void... urls) {


            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(getString(R.string.cConnectpriz)+cquery);
            HttpResponse response;

            try {
                Log.d("TAGH", "призы");
                response = httpclient.execute(httppost);
                responseString = EntityUtils.toString(response.getEntity());//response.toString() ;
                Log.d("TAGH", "призы2");
                Log.d("TAGH", getString(R.string.cConnecthist)+cquery);
                Log.d("TAGH", responseString);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return responseString;

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            spinner.dismiss();
            super.onPostExecute(result);
            ArrayList anames = new ArrayList();
            try {
                JSONObject dataJsonObj= new JSONObject(result);
                JSONArray dataar = dataJsonObj.getJSONArray("tov");
                JSONArray datcen = dataJsonObj.getJSONArray("cen");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    readContacts();
                }
               for (int i = 0; i < dataar.length(); i++) {

                    anames.add(i+1+". "+dataar.getString(i).trim()+"\n стоимость в бонусах :"+datcen.getString((i)));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (anames.size()==0) {
                Toast toast = Toast.makeText(getApplicationContext(),"Пользователь с такими данными не зарегистрирован.",Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                // intent.putExtra(EXTRA_QUERY, cquery);
                intent.putExtra("anames", anames);
                startActivity(intent);
            }



        }
    }

    public void readContacts() {
        HashMap contactsMap = new HashMap();
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        Cursor cursor =contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,     null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Log.d("TAGP",name);

                if (Integer
                        .parseInt(cursor.getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[]{id}, null);
                    while (phoneCursor.moveToNext()) {
                        int phoneType = phoneCursor
                                .getInt(phoneCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        String phoneNumber = phoneCursor
                                .getString(phoneCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("TAGP",phoneNumber);
                        switch (phoneType) {
                            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                               // contactsMap.put(phoneNumber, name + " (Mobile)");
                                Log.d("TAGP",name);
                                break;

                            default:
                                break;
                        }
                    }
                    phoneCursor.close();
                }
            }
        }
    }

}
