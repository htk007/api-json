package com.example.currencyconverter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tryText, cadText, usdText, jpyText, chfText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        chfText = findViewById(R.id.chfText);
    }

    public void getRates(View view){
            DownloadData downloadData= new DownloadData();

            try{
                String url= "http://data.fixer.io/api/latest?access_key=#################################"; //you can here access_key from data.fixer.io website free
                downloadData.execute(url);

            }catch (Exception e){}
    }
                                        //<What will you send me, will i show you a progress bar, What will I return>
    private class DownloadData extends AsyncTask<String, Void, String>{


                                            @Override
                                            protected String doInBackground(String... strings) {

                                                String result="";
                                                URL url;
                                                HttpURLConnection httpURLConnection;

                                                try{

                                                    url = new URL(strings[0]);
                                                    httpURLConnection=(HttpURLConnection) url.openConnection();//we opened the link.

                                                    InputStream inputStream= httpURLConnection.getInputStream();
                                                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                                                    int data = inputStreamReader.read();

                                                    while (data >0){

                                                        char character = (char) data;
                                                        result += character;

                                                        data = inputStreamReader.read();

                                                    }
                                                    return result;

                                                }catch (Exception e){
                                                    return null;
                                                }
                                            }

                                            @Override
                                            protected void onPostExecute(String s) {
                                                super.onPostExecute(s);

                                                try{

                                                    JSONObject jsonObject= new JSONObject(s);

                                                    String race = jsonObject.getString("base");

                                                    //System.out.println("base" + race);
                                                    String rates = jsonObject.getString("rates");
                                                   // System.out.println("rates" + rates);

                                                    JSONObject jsonObject1 = new JSONObject(rates);
                                                    String turkishLira = jsonObject1.getString("TRY");
                                                    tryText.setText("TRY: "+ turkishLira);
                                                    String usd = jsonObject1.getString("USD");
                                                    usdText.setText("USD: "+ usd);
                                                    String cad = jsonObject1.getString("CAD");
                                                    cadText.setText("CAD: "+ cad);
                                                    String chf = jsonObject1.getString("CHF");
                                                    chfText.setText("CHF: "+ chf);
                                                    String jpy = jsonObject1.getString("JPY");
                                                    jpyText.setText("JPY: "+ turkishLira);

                                                } catch (Exception e){}


                                              //  System.out.println("Alınan data: " + s);
                                            }
                                        }
}
