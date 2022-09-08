package com.example.noteclone.data

import androidx.room.*
import java.lang.Integer.parseInt

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "time")
    var time: String,

    @ColumnInfo(name = "color")
    var color: String
) {
    override fun toString() : String {
        var res = ""

        res = res.plus("$id,$title,$content,$time,$color")

        return res
    }
}

fun toNote(string: String) : Note {
    var split = string.split(",")

    var note : Note = Note(
        id = parseInt(split[0]),
        title = split[1],
        content = split[2],
        time = split[3],
        color = split[4]
    )

    return note
}
