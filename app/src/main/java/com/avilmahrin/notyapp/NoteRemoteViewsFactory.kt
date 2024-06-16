package com.avilmahrin.notyapp

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.avilmahrin.notyapp.database.NotesDatabase
import com.avilmahrin.notyapp.model.Notes

class NoteRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private var noteList: List<Notes> = listOf()

    override fun onCreate() {
        // No-op
    }

    override fun onDataSetChanged() {
        // Fetch data synchronously from the database
        val dao = NotesDatabase.getDatabaseInstace(context).myNotesDao()
        noteList = dao.getNotesSynchronously() // Metode untuk mengambil catatan secara sinkron
    }

    override fun onDestroy() {
        // No-op
    }

    override fun getCount(): Int {
        return noteList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val note = noteList[position]
        val views = RemoteViews(context.packageName, R.layout.widget_item_layout)
        views.setTextViewText(R.id.widget_note_title, note.title)
        views.setTextViewText(R.id.widget_note_content, note.subTitle)
        views.setTextViewText(R.id.widget_note_details, note.notes)
        views.setTextViewText(R.id.widget_note_date, note.date)

        // Set fill-in intent
        val fillInIntent = Intent()
        views.setOnClickFillInIntent(R.id.widget_item_layout, fillInIntent)

        return views
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}
