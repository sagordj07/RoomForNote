package com.room.roomfornote;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;

    private  LiveData<List<Note>>AllNote;

    public NoteRepository(Application application)
    {
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.noteDao();

        AllNote=noteDao.getAllNotes();
    }

    public void insert(Note note)
    {
        new insertAsyncTask(noteDao).execute(note);

    }
    public void update(Note note)
    {
        new updateAsyncTask(noteDao).execute(note);

    }
    public void delete(Note note)
    {
        new deleteAsyncTask(noteDao).execute(note);

    }
    public void deleteAllNote()
    {
        new deleteAllAsyncTask(noteDao).execute();


    }

    public LiveData<List<Note>> getAllNote() {
        return AllNote;
    }

    private static class insertAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private insertAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insert(notes[0]);

            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private updateAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.update(notes[0]);

            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private deleteAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);

            return null;
        }
    }
    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NoteDao noteDao;
        private deleteAllAsyncTask(NoteDao noteDao) {
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {

            noteDao.deleteAllNotes();

            return null;
        }

    }


}
