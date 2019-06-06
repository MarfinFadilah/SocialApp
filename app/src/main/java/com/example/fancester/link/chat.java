package com.example.fancester.link;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class chat extends AppCompatActivity {

    private List<Chatting> Ch = new ArrayList<Chatting>();
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
    private int imgs, imic;
    ImageView icn;
    String str, nameHeader;
    TextView chatText;
    Timer timer;
    MyTimerTask mytimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Button btn = (Button) this.findViewById(R.id.chatButton);
        nameHeader = getIntent().getStringExtra("EXTRA_SESSION_CHATUSR");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(nameHeader);
        str = getIntent().getStringExtra("EXTRA_SESSION_CHATCONTENT");
        params.gravity = Gravity.LEFT;
        params.leftMargin = 108;
        imgs = R.drawable.left;

        switch (nameHeader) {
            case "Ney":
                imic = R.drawable.user1;
                break;
            case "Axel":
                imic = R.drawable.user2;
                break;
            case "Rod":
                imic = R.drawable.user3;
                break;
        }
        operateChatting();
        operateViewing();
        ImageView imLoc = (ImageView) findViewById(R.id.imageView4);
        EditText edt = (EditText) findViewById(R.id.chatBox);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Button btn = (Button) findViewById(R.id.chatButton);
                btn.setBackgroundResource(R.drawable.callsend);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText edt = (EditText) findViewById(R.id.chatBox);
                Button btn = (Button) findViewById(R.id.chatButton);
                if (edt.getText().length() == 0) {
                    btn.setBackgroundResource(R.drawable.callsend);
                } else {
                    btn.setBackgroundResource(R.drawable.chatsend);
                }
            }
        });
        imLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chat.this, MapsActivity.class);
                intent.putExtra("EXTRA_SESSION_CHATUSR", nameHeader);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = (EditText) findViewById(R.id.chatBox);
                str = edt.getText().toString();
                params.gravity = Gravity.RIGHT;
                params.leftMargin = 0;
                imgs = R.drawable.right;
                imic = 0;
                operateChatting();
                operateViewing();
                edt.setText("");
                if (timer != null)
                    timer.cancel();
                timer = new Timer();
                mytimerTask = new MyTimerTask();
                timer.schedule(mytimerTask, 1000, 10000);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    str = "Hello Lee, what's up?";
                    params.gravity = Gravity.LEFT;
                    params.leftMargin = 108;
                    imgs = R.drawable.left;
                    switch (nameHeader) {
                        case "Ney":
                            imic = R.drawable.user1;
                            break;
                        case "Axel":
                            imic = R.drawable.user2;
                            break;
                        case "Rod":
                            imic = R.drawable.user2;
                            break;
                    }
                    operateChatting();
                    operateViewing();
                    timer.cancel();
                }
            });
        }
    }

    private void operateChatting() {
        Ch.add(new Chatting(str,params,imgs,imic));
    }
    private void operateViewing() {
        ArrayAdapter<Chatting> adapter = new chat.MyChattingView();
        ListView lst = (ListView) findViewById(R.id.chat_list);
        lst.setAdapter(adapter);
    }
    private class MyChattingView extends ArrayAdapter<Chatting> {
        public MyChattingView() {
            super(chat.this, R.layout.chat_view, Ch);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup Parent) {
            View itemView = convertView;
            Chatting currentChat = Ch.get(position);
                itemView = getLayoutInflater().inflate(R.layout.chat_view, Parent, false);

            chatText = (TextView) itemView.findViewById(R.id.textView5);
            icn = (ImageView) itemView.findViewById(R.id.imageView);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), currentChat.getIconID());
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            icn.setImageDrawable(roundedBitmapDrawable);
            chatText.setText(currentChat.getStr());
            chatText.setLayoutParams(currentChat.getParams());
            chatText.setBackgroundResource(currentChat.getChatID());
            return itemView;

            // return super.getView(position, convertView, Parent);
        }
    }
}
