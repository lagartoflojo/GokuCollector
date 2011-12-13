package com.plegnic.goku.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Simple content provider that demonstrates the basics of creating a content
 * provider that stores basic video meta-data.
 */
public class GokuCollectionContentProvider extends ContentProvider {
    public static final String AUTHORITY =
            "com.oreilly.demo.pa.finchvideo.SimpleFinchVideo";

    public static final String GOKU_COLLECTOR = "goku_collector";

    private static final int COLLECTIONS = 1;
    private static final int COLLECTION_ID = 2;
    private static final int COLLECTION_THUMB = 3;
    private static final int COLLECTION_ITEMS = 4;
    private static final int COLLECTION_ITEM_ID = 5;
    private static final int COLLECTION_ITEM_THUMB = 5;

    private static UriMatcher sUriMatcher;

    private static HashMap<String, String> sVideosProjectionMap;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        sUriMatcher.addURI(AUTHORITY,
        		GokuCollection.Collection.COLLECTIONS_PATH,
        		COLLECTIONS);

        sUriMatcher.addURI(AUTHORITY,
                GokuCollection.Collection.COLLECTIONS_PATH + "/#",
                COLLECTION_ID);
        
        sUriMatcher.addURI(AUTHORITY,
                GokuCollection.Collection.COLLECTIONS_PATH + "/#/" +
                		GokuCollection.Collection.THUMB_PATH,
                COLLECTION_THUMB);
        
        sUriMatcher.addURI(AUTHORITY,
                GokuCollection.Collection.COLLECTIONS_PATH + "/#/" +
                		GokuCollection.Collection.ITEMS_PATH,
                COLLECTION_ITEMS);
        
        sUriMatcher.addURI(AUTHORITY,
                GokuCollection.Collection.COLLECTIONS_PATH + "/#/" +
                		GokuCollection.Collection.ITEMS_PATH + "/#",
                COLLECTION_ITEM_ID);
        
        sUriMatcher.addURI(AUTHORITY,
                GokuCollection.Collection.COLLECTIONS_PATH + "/#/" +
                		GokuCollection.Collection.ITEMS_PATH + "/#/" +
                		GokuCollection.Collection.THUMB_PATH,
                COLLECTION_ITEM_THUMB);
    }

    public static final String COLLECTIONS_TABLE_NAME = "collections";
    public static final String ITEMS_TABLE_NAME = "items";

    public static final String DATABASE_NAME = GOKU_COLLECTOR + ".db";
    static int DATABASE_VERSION = 1;

    private static class CollectionDbHelper extends SQLiteOpenHelper {
        private CollectionDbHelper(Context context, String name,
                                    SQLiteDatabase.CursorFactory factory)
        {
            super(context, name, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            createTable(sqLiteDatabase);
        }

        private void createTable(SQLiteDatabase sqLiteDatabase) {
            String qs = "CREATE TABLE " + COLLECTIONS_TABLE_NAME + " (" +
                    BaseColumns._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GokuCollection.Collection.NAME + " TEXT, " +
                    GokuCollection.Collection.DESCRIPTION + " TEXT, " +
                    GokuCollection.Collection.LATITUDE + " REAL, " +
                    GokuCollection.Collection.LONGITUDE + " REAL, " +
                    GokuCollection.Collection._DATA + " TEXT UNIQUE);";
            sqLiteDatabase.execSQL(qs);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                              int oldv, int newv)
        {
//            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
//                    VIDEO_TABLE_NAME + ";");
//            createTable(sqLiteDatabase);
        }
    }

    private Context mContext;
    private CollectionDbHelper mOpenDbHelper;

    private SQLiteDatabase mDb;

    public GokuCollectionContentProvider() {
    }

    public GokuCollectionContentProvider(Context context) {
        mContext = context;
        init();
    }

    @Override
    public boolean onCreate() {
        init();
        return true;
    }

    // allows object initialization to be reused.
    private void init() {
        mOpenDbHelper = new CollectionDbHelper(getContext(),
                DATABASE_NAME, null);
        mDb = mOpenDbHelper.getWritableDatabase();
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String where,
                        String[] whereArgs, String sortOrder)
    {
        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = GokuCollection.Collection.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        int match = sUriMatcher.match(uri);

        Cursor c;

        switch (match) {
            case COLLECTIONS:
                // query the database for all collections
                c = mDb.query(COLLECTIONS_TABLE_NAME, projection,
                        where, whereArgs,
                        null, null, orderBy);

                c.setNotificationUri(getContext().getContentResolver(),
                        GokuCollection.Collection.CONTENT_URI);
                break;
            case COLLECTION_ID:
                // query the database for a specific video
                long videoID = ContentUris.parseId(uri);
                c = mDb.query(COLLECTIONS_TABLE_NAME, projection,
                        BaseColumns._ID + " = " + videoID +
                                (!TextUtils.isEmpty(where) ?
                                        " AND (" + where + ')' : ""),
                        whereArgs, null, null, orderBy);
                c.setNotificationUri(getContext().getContentResolver(),
                        GokuCollection.Collection.CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("unsupported uri: " + uri);
        }

        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case COLLECTIONS:
                return GokuCollection.Collection.CONTENT_TYPE;

            case COLLECTION_ID:
                return GokuCollection.Collection.CONTENT_COLLECTION_TYPE;

            default:
                throw new IllegalArgumentException("Unknown video type: " +
                        uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != COLLECTIONS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        verifyValues(values);

        // insert the initialValues into a new database row
        SQLiteDatabase db = mOpenDbHelper.getWritableDatabase();
        long rowId = db.insert(COLLECTIONS_TABLE_NAME,
                GokuCollection.Collection.NAME, values);
        if (rowId > 0) {
            Uri videoURi =
                    ContentUris.withAppendedId(
                            GokuCollection.Collection.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(videoURi, null);
            return videoURi;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    private void verifyValues(ContentValues values) {
        // Make sure that the fields are all set
        if (!values.containsKey(GokuCollection.Collection.NAME)) {
            Resources r = Resources.getSystem();
            values.put(GokuCollection.Collection.NAME,
                    r.getString(android.R.string.untitled));
        }

        if (!values.containsKey(GokuCollection.Collection.DESCRIPTION)) {
            values.put(GokuCollection.Collection.DESCRIPTION, "");
        }
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        int match = sUriMatcher.match(uri);
        int affected;

        switch (match) {
            case COLLECTIONS:
                affected = mDb.delete(COLLECTIONS_TABLE_NAME,
                        (!TextUtils.isEmpty(where) ?
                                " AND (" + where + ')' : ""),
                        whereArgs);
                break;
            case COLLECTION_ID:
                long videoId = ContentUris.parseId(uri);
                affected = mDb.delete(COLLECTIONS_TABLE_NAME,
                        BaseColumns._ID + "=" + videoId
                                + (!TextUtils.isEmpty(where) ?
                                " AND (" + where + ')' : ""),
                        whereArgs);

                // the call to notify the uri after deletion is explicit
                getContext().getContentResolver().notifyChange(uri, null);

                break;
            default:
                throw new IllegalArgumentException("unknown video element: " +
                        uri);
        }

        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where,
                      String[] whereArgs)
    {
        SQLiteDatabase db = mOpenDbHelper.getWritableDatabase();
        int affected;
        switch (sUriMatcher.match(uri)) {
            case COLLECTIONS:
                affected = db.update(COLLECTIONS_TABLE_NAME, values,
                        where, whereArgs);
                break;

            case COLLECTION_ID:
                String videoId = uri.getPathSegments().get(1);
                affected = db.update(COLLECTIONS_TABLE_NAME, values,
                        BaseColumns._ID + "=" + videoId
                                + (!TextUtils.isEmpty(where) ?
                                " AND (" + where + ')' : ""),
                        whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}
