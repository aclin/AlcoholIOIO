package game;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDBHelper extends SQLiteOpenHelper {

	/*SQLiteOpenHelper. need to migrate with */
	private static final String DATABASE_NAME = "Brac_History";
	private static final int DB_VERSION = 2;
	
	public GameDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE AlcoholTreeGame (" +
                        " _ID INTEGER PRIMARY KEY, " +
                        " _STATE INTEGER NOT NULL, " +
                        " _COIN INTEGER NOT NULL" +
                ")"
        );

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old_ver, int new_ver){
		db.execSQL("DROP TABLE IF EXISTS AlcoholTreeGame");
		onCreate(db);
	}
	
	public void onOpen(SQLiteDatabase db){
		super.onOpen(db);
	}
	public synchronized void close(){
		super.close();
	}

}
