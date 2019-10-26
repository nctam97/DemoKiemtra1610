package com.example.demokiemtra1610;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    DBHelper(Context context){
        super(context,"BookDatabase3.sqlite",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Authors("+ " id_author interger primary key,"+" name text,"+" address text,"+" email text )");
        db.execSQL("CREATE TABLE Books("+"id_book interger primary key,"+"title text, "+" id_author interger "+" constraint id_author references Authors(id_author)"+"On Delete Cascade ON UPDATE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Books");
        onCreate(db);
    }
    public boolean insertAuthor(Author author){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id_author",author.getId_author());
        contentValues.put("name",author.getName());
        contentValues.put("address",author.getAddress());
        contentValues.put("email",author.getEmail());
        int result= (int)db.insert("Authors",null,contentValues);
        db.close();
        return true;
    }
    public ArrayList<Author> getAllAuthor(){
        ArrayList<Author> list=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from Authors ",null);
        if(cursor!=null)
            cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            list.add(new Author(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }
    public ArrayList<String> getBookAuthor(int id){
        ArrayList<String> list=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String sqlstr= " select Books.id_author,title " + "from Authors inner join Books on " + " Authors.id_author = Books.id_author " + " where Authors.id_author= "+id;
        return list;
    }
    //Chèn một book vào cơ sở dữ liệu
    public boolean insertBook(Book book){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id_book",book.getId());
        contentValues.put("title",book.getTitle());
        contentValues.put("id_author",book.getAuthor());
        db.insert("Books",null,contentValues);
        return true;
    }
    //Truy vấn dữ liệu
    public Book getBook(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from Books where id="+id,null);
        if(cursor!=null)
            cursor.moveToFirst();
        Book book=new Book(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
        cursor.close();
        db.close();
        return book;
    }
    //Lây tất cả các cuốn sách
    public ArrayList<Book> getAllBook(){
        ArrayList<Book> list=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from Books ",null);
        if(cursor!=null)
            cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            list.add(new  Book(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }
    public boolean deleteBook(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        int count=db.delete("Books","id" +"=?",new String[]{String.valueOf(id)});
        db.close();
        if(count>0)
            return true;
        return false;
    }
    public boolean updateBook(Book book){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",book.getTitle());
        contentValues.put("author",book.getAuthor());
        int count=db.update("Books",contentValues,"id"+"=?",new String[]{String.valueOf(book.getId())});
        if(count>0)
            return true;
        return false;
    }
}
