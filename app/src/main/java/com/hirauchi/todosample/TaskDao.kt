package com.hirauchi.todosample

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

//Daoを実装。Daoは、Data Access Objectsの略称
//データベースにアクセスするためのメソッドを定義するクラス
//@Daoをつける事で、Daoクラスとなる
//@Queryは、SQLiteのQueryを実行する際に使用するアノテーション
//データの追加、更新、削除には、それぞれ@Insert、@Update、@Deleteというアノテーションを使用可能
@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): Single<List<Task>>

    @Insert
    fun insert(task: Task): Completable

    @Update
    fun update(task: Task): Completable

    @Delete
    fun delete(task: Task): Completable
}
