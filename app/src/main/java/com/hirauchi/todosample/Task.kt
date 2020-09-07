package com.hirauchi.todosample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//一覧表示するタスクのデータクラスを作成
//id(インデックス)、state(状態)、description(タスク内容)
//Entityは、データベースのテーブルを表すクラスのこと
@Entity
data class Task(
        //utoGenerate = trueとすると、idが自動生成される
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        //@ColumnInfoは、カラム名を指定する際に使用、
        // @ColumnInfoを指定しなかった場合、パラメータ名がカラム名となる
        @ColumnInfo(name = "state")
        var state: Int,

        @ColumnInfo(name = "description")
        var description: String
)
