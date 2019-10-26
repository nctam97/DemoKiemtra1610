package com.example.demokiemtra1610;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    static  final String AUTHORITY = "com.example.demokiemtra1610";
     static final String CONTEN_PATH="bookdata";
     static final String URL="content://" + AUTHORITY + "/" + CONTEN_PATH;
     static final Uri CONTENT_URL = Uri.parse(URL);
     static final String TABLE_NAME = "Books";
    private SQLiteDatabase db;

    private static HashMap<String, String> BOOKS_PROJECTION_MAP;

    public static final int ALLITEMS = 1;
    public static final int ONEITEM = 2;

     static final UriMatcher uriMatcher ;
     static {
         uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
         uriMatcher.addURI(AUTHORITY,CONTEN_PATH,ALLITEMS);
         uriMatcher.addURI(AUTHORITY,CONTEN_PATH + "/#",ONEITEM);
     }
    public MyContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count = 0;
        switch (uriMatcher.match(uri)){
            case ALLITEMS:
                count = db.delete(TABLE_NAME,selection,selectionArgs);
                break;
            case ONEITEM:
                String id = uri.getPathSegments().get(1);
                count = db.delete(TABLE_NAME,"id_book" + " = " + id + (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""),selectionArgs);
                break;
            default: throw new UnsupportedOperationException("Unknown URL" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        long number_row = db.insert(TABLE_NAME,"",values);
        if(number_row>0){
            Uri uri1 = ContentUris.withAppendedId(CONTENT_URL, number_row);
            getContext().getContentResolver().notifyChange(uri1,null);
            return uri1;
        }
        throw new UnsupportedOperationException("Failed to add a record into" + uri);

    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Context context = getContext();
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        if(db==null)
            return false;
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder sqlite_querybuilder=new SQLiteQueryBuilder();
        sqlite_querybuilder.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)){
            case ALLITEMS:
                sqlite_querybuilder.setProjectionMap(BOOKS_PROJECTION_MAP);
                break;
            case ONEITEM:
                sqlite_querybuilder.appendWhere("id_book" + "=" + uri.getPathSegments().get(1));
                break;
        }
        if(sortOrder == null || sortOrder == ""){
            sortOrder = "title";
        }
        Cursor cursor = sqlite_querybuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return  cursor;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int count = 0;
        switch (uriMatcher.match(uri)){
            case ALLITEMS:
                count = db.update(TABLE_NAME,values,selection,selectionArgs);
                break;
            case ONEITEM:

                count = db.update(TABLE_NAME,values,"id_book" + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""),selectionArgs);
                break;
            default: throw new UnsupportedOperationException("Unknown URL" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;

    }
}
