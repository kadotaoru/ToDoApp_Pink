package com.hirauchi.todosample

interface TaskContract {

    //Presenterを実装するにあたり、必要な処理を明確にするため、interfaceを作成
    interface View {

        //onLoadTasks()は、Presenterでタスクの一覧取得が行われた後に呼ばれることを想定しており、
        //Viewをimplementしたクラスでは、onLoadTasks()をOverrideし、リスト表示の更新処理を行う。
        fun onLoadTasks(tasks: List<Task>)

        fun showError(message: String?)
    }

    interface Presenter {

        //loadTasks()は、タスクの一覧取得処理が実装されることを想定
        fun loadTasks()

        //loadTasks()は、タスクの一覧取得処理が実装されることを想定
        fun insertTask(description: String)

        //updateTaskState()は、タスクの状態の更新処理が実装されることを想定
        fun updateTaskState(task: Task)

        //updateTaskDescription()は、タスクの内容の更新処理が実装されることを想定
        fun updateTaskDescription(task: Task, description: String)

        //deleteTask()は、タスクの削除処理が実装されることを想定
        fun deleteTask(task: Task)
    }
}
