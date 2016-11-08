package com.example.sj.testapplication2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;

    String myJSON;
    String keyword;
    String GET_JSON_DATA_HTTP_URL = "http://172.27.82.202/yamyam_api/search.php";
    String JSON_RESULTS = "result";
    String JSON_NAME = "name";
    String JSON_SUBJECT = "genre";
    String JSON_ADDRESS = "addr";

    Button button;

    JSONArray stores = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        button = (Button) findViewById(R.id.button);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        Intent i = getIntent();
        keyword = i.getStringExtra("name");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                /*
                String link = "http://172.27.82.202/yamyam_api/getdata.php?name="+keyword;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);*/

                getData(GET_JSON_DATA_HTTP_URL);

            }
        });

    }

    public void sendHttpWithMsg(String url) {

//기본적인 설정
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 3000);
        HttpConnectionParams.setSoTimeout(params, 3000);
        post.setHeader("Content-type", "application/json; charset=utf-8");
// JSON OBject를 생성하고 데이터를 입력합니다.
//여기서 처음에 봤던 데이터가 만들어집니다.

        JSONObject jObj = new JSONObject();
        try {
            jObj.put("name", "hong");
            jObj.put("phone", "000-0000");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        try {
// JSON을 String 형변환하여 httpEntity에 넣어줍니다.
            StringEntity se;
            se = new StringEntity(jObj.toString());
            HttpEntity he = se;
            post.setEntity(he);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
//httpPost 를 서버로 보내고 응답을 받습니다.
            HttpResponse response = client.execute(post);
// 받아온 응답으로부터 내용을 받아옵니다.
        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                JSON_PARSE_DATA_AFTER_WEBCALL();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            stores = jsonObj.getJSONArray(JSON_RESULTS);

            for (int i = 0; i < stores.length(); i++) {
                JSONObject c = stores.getJSONObject(i);
                GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

                GetDataAdapter2.setName(c.getString(JSON_NAME));

                GetDataAdapter2.setSubject(c.getString(JSON_SUBJECT));

                GetDataAdapter2.setAddress(c.getString(JSON_ADDRESS));

                GetDataAdapter1.add(GetDataAdapter2);
            }

            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

            recyclerView.setAdapter(recyclerViewadapter);

            progressBar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}