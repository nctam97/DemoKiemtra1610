package com.example.demokiemtra1610;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    EditText et_id,et_title,et_author;
    Button bt_savebook,bt_select,bt_exit,bt_update;
    GridView gv_display;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        et_id=(EditText) findViewById(R.id.edtNhapms);
        et_title=(EditText) findViewById(R.id.edtNhaptd);
        et_author=(EditText) findViewById(R.id.edtNhaptentg);
        gv_display=(GridView)findViewById(R.id.grvDisplay);
        dbHelper=new DBHelper(this);

        bt_savebook=(Button)findViewById(R.id.btn_Savebook);
        /*bt_savebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book=new Book();
                book.setId(Integer.parseInt(et_id.getText().toString()));
                book.setTitle(et_title.getText().toString());
                book.setAuthor(Integer.parseInt(et_author.getText().toString()));
                if(dbHelper.insertBook(book))
                    Toast.makeText(getApplicationContext(),"Đã lưu thành công",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Lưu không thành công",Toast.LENGTH_LONG).show();
            }
        });*/
        bt_savebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("id_book",et_id.getText().toString());
                values.put("title",et_title.getText().toString());
                values.put("id_author",et_author.getText().toString());
                String uri="content://com.example.demokiemtra1610";
                Uri book = Uri.parse(uri);
                Uri insert_uri = getContentResolver().insert(book,values);
                Toast.makeText(getApplicationContext(),"Lưu thành công",Toast.LENGTH_LONG).show();
            }
        });


        /*bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list=new ArrayList<>();
                ArrayList<Book> booklist=new ArrayList<>();
                booklist=dbHelper.getAllBook();
                for(Book book:booklist){
                    list.add(book.toString());
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(BookActivity.this,android.R.layout.simple_list_item_1,list);
                gv_display.setAdapter(adapter);
            }
        });*/
        bt_select=(Button)findViewById(R.id.btnSelect);
        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list=new ArrayList<>();
                String uri = "content://com.example.demokiemtra1610";
                Uri book = Uri.parse(uri);
                Cursor cursor = getContentResolver().query(book,null,null,null,"title");
                if(cursor != null){
                    cursor.moveToFirst();
                    do{
                        list.add(cursor.getInt(0)+"");
                        list.add(cursor.getString(1)+"");
                        list.add(cursor.getInt(2)+"");
                    }while (cursor.moveToNext());
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(BookActivity.this,android.R.layout.simple_list_item_1,list);
                    gv_display.setAdapter(adapter);
                }
            }
        });
        bt_update=(Button)findViewById(R.id.btnUpdate);
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bt_exit=(Button)findViewById(R.id.btnExit);
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
