package com.example.demokiemtra1610;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class AuthorActivity extends AppCompatActivity {

    EditText et_id,et_diachi,et_email,et_address;
    Button bt_saveauthor,bt_select,bt_exit,bt_update;
    GridView gv_display;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        et_id=(EditText) findViewById(R.id.edtNhapms);
        et_diachi=(EditText) findViewById(R.id.edtNhapdiachi);
        et_email=(EditText) findViewById(R.id.edtNhapemail);
        gv_display=(GridView)findViewById(R.id.grvDisplay);

        dbHelper=new DBHelper(this);

        bt_saveauthor=(Button)findViewById(R.id.btnSaveauthor);
        bt_saveauthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Author author=new Author();
                author.setId_author(Integer.parseInt(et_id.getText().toString()));
                author.setName(et_diachi.getText().toString());
                author.setEmail(et_address.getText().toString());
                author.setAddress(et_diachi.getText().toString());
                if(dbHelper.insertAuthor(author))
                    Toast.makeText(getApplicationContext(),"Đã lưu thành công",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Lưu không thành công",Toast.LENGTH_LONG).show();
            }
        });
        bt_select=(Button)findViewById(R.id.btnSelect);
        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list=new ArrayList<>();
                ArrayList<Author> authorlist=new ArrayList<>();
                authorlist=dbHelper.getAllAuthor();
                for(Author author:authorlist){
                    list.add(author.getId_author()+" ");
                    list.add(author.getName()+" ");
                    list.add(author.getAddress()+" ");
                    list.add(author.getEmail()+" ");
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(AuthorActivity.this,android.R.layout.simple_list_item_1,list);
                gv_display.setAdapter(adapter);
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
