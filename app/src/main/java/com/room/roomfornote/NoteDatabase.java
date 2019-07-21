package com.room.roomfornote;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {


    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context)

    {
        if (instance==null) {

            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();


        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallBack=new RoomDatabase.Callback()
    {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populatedbAsyncTask(instance).execute();
        }


    };

    private static class populatedbAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NoteDao noteDao;

        private populatedbAsyncTask(NoteDatabase db){
            noteDao=db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            noteDao.insert(new Note("title 1","Description 1",1));
            noteDao.insert(new Note("title 2","Description 2",2));
            noteDao.insert(new Note("title 3","Description 3",3));
            return null;
        }
    }

}
