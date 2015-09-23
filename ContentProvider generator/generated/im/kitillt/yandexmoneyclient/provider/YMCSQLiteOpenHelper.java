package im.kirillt.yandexmoneyclient.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import im.kirillt.yandexmoneyclient.BuildConfig;
import im.kirillt.yandexmoneyclient.provider.account.AccountColumns;
import im.kirillt.yandexmoneyclient.provider.operation.OperationColumns;

public class YMCSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = YMCSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "yandexmoneyclient.db";
    private static final int DATABASE_VERSION = 1;
    private static YMCSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final YMCSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_ACCOUNT = "CREATE TABLE IF NOT EXISTS "
            + AccountColumns.TABLE_NAME + " ( "
            + AccountColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AccountColumns.ACCOUNTNUMBER + " TEXT NOT NULL, "
            + AccountColumns.ACCOUNTUSERNAME + " TEXT NOT NULL, "
            + AccountColumns.BALANCE + " TEXT NOT NULL, "
            + AccountColumns.BALANCEHOLD + " TEXT NOT NULL, "
            + AccountColumns.AVATAR + " TEXT NOT NULL "
            + " );";

    public static final String SQL_CREATE_TABLE_OPERATION = "CREATE TABLE IF NOT EXISTS "
            + OperationColumns.TABLE_NAME + " ( "
            + OperationColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OperationColumns.OPERATIONID + " TEXT NOT NULL, "
            + OperationColumns.STATUS + " INTEGER NOT NULL, "
            + OperationColumns.DIRECTION + " INTEGER NOT NULL, "
            + OperationColumns.AMOUNT + " TEXT NOT NULL, "
            + OperationColumns.AMOUNTDUE + " TEXT NOT NULL, "
            + OperationColumns.FEE + " TEXT NOT NULL, "
            + OperationColumns.DATETIME + " INTEGER NOT NULL, "
            + OperationColumns.TITLE + " TEXT NOT NULL, "
            + OperationColumns.SENDER + " TEXT NOT NULL, "
            + OperationColumns.RECIPIENT + " TEXT NOT NULL, "
            + OperationColumns.PAYEEIDENTIFIERTYPE + " INTEGER NOT NULL, "
            + OperationColumns.MESSAGE + " TEXT NOT NULL, "
            + OperationColumns.COMMENT + " TEXT NOT NULL, "
            + OperationColumns.CODEPRO + " INTEGER NOT NULL, "
            + OperationColumns.PROTECTIONCODE + " TEXT NOT NULL, "
            + OperationColumns.EXPIRES + " INTEGER NOT NULL, "
            + OperationColumns.ANSWERDATETIME + " INTEGER NOT NULL, "
            + OperationColumns.LABEL + " TEXT NOT NULL, "
            + OperationColumns.DETAILS + " TEXT NOT NULL, "
            + OperationColumns.REPEATABLE + " INTEGER NOT NULL, "
            + OperationColumns.FAVORITE + " INTEGER NOT NULL, "
            + OperationColumns.PAYMENTTYPE + " INTEGER NOT NULL "
            + " );";

    // @formatter:on

    public static YMCSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static YMCSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static YMCSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new YMCSQLiteOpenHelper(context);
    }

    private YMCSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new YMCSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static YMCSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new YMCSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private YMCSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new YMCSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_ACCOUNT);
        db.execSQL(SQL_CREATE_TABLE_OPERATION);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
