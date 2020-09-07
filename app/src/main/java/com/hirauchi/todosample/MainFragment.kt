package com.hirauchi.todosample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), TaskContract.View {

    private lateinit var adapter: TasksAdapter
    //Presenter経由でデータベースにアクセスするよう修正
    private lateinit var presenter: TaskPresenter

    //1,空のフラグメント作成
    //companion(staticの代わり) objectで、Singletonを作成することができる
    companion object {
        fun newInstance() = MainFragment()
    }
    //1,空のフラグメント作成
    //onCreateView()にて、fragment_main.xmlをレイアウトに設定
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val context = context?: return

    adapter = TasksAdapter(itemListener)
    listView.adapter = adapter

    //TaskDatabase.getInstance(context)で、TaskDatabaseを取得
    val db = TaskDatabase.getInstance(context)
    presenter = TaskPresenter(TaskRepository(db.taskDao()), this)
    presenter.loadTasks()

    //FABをタップするとタスク追加の処理をする
    addTaskButton.setOnClickListener {
        val editText = EditText(context).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }

        val container = LinearLayout(context).apply {
            setPadding(48,0,48,0)
            addView(editText)
        }

        AlertDialog.Builder(context)
            .setTitle(getString(R.string.add_dialog_title))
            .setView(container)
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                presenter.insertTask(editText.text.toString())
            })
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}
//TaskItemListenerをimplementしたobjectを作成
// Callbackをoverrideして、イベント発生時に行いたい処理を実装
    val itemListener = object : TaskItemListener {
        override fun onStateClick(task: Task) {
            presenter.updateTaskState(task)
        }

    //onDescriptionClick()はTaskの内容がタップされた際に呼ばれる
        override fun onDescriptionClick(task: Task) {
            val context = context?: return

            val editText = EditText(context).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                setText(task.description)
            }

            val container = LinearLayout(context).apply {
                setPadding(48,0,48,0)
                addView(editText)
            }

            AlertDialog.Builder(context)
                .setTitle(getString(R.string.edit_dialog_title))
                .setView(container)
                .setPositiveButton(getString(R.string.ok), { dialog, which ->
                    presenter.updateTaskDescription(task, editText.text.toString())
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }

        override fun onDeleteClick(task: Task) {
            presenter.deleteTask(task)
        }
    }

    override fun onLoadTasks(tasks: List<Task>) {
        adapter.tasks = tasks
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    //ListViewのAdapterを実装
    //TODOやDONEなどの状態を表示するためのTextView、
    //タスクの内容を表示するためのTextView、
    //タスクを削除するためのボタンとなるImageView
    private class TasksAdapter(private val listener: TaskItemListener): BaseAdapter() {

        //Taskが追加された際に、リストが更新されるよう、TasksAdapterクラスにセッターを追加します。
        //TasksAdapterのtasksが更新されると、notifyDataSetChanged()が呼ばれ、リストが更新されるよう実装されております
        var tasks: List<Task> = listOf()
            set(tasks) {
                field = tasks
                notifyDataSetChanged()
            }

        private val stateTexts = listOf(R.string.todo, R.string.doing, R.string.done)
        private val stateColors = listOf(R.color.todo, R.color.doing, R.color.done)

        override fun getCount() = tasks.size

        override fun getItem(i: Int) = tasks[i]

        override fun getItemId(i: Int) = i.toLong()

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val task = getItem(i)
            val rowView = view ?: LayoutInflater.from(viewGroup.context).inflate(R.layout.task_item, viewGroup, false)

            rowView.findViewById<TextView>(R.id.taskState).apply {
                text = context.getString(stateTexts[task.state])
                setTextColor(ContextCompat.getColor(context, stateColors[task.state]))
                setOnClickListener {
                    listener.onStateClick(task)
                }
            }

            rowView.findViewById<TextView>(R.id.taskDescription).apply {
                text = task.description
                setOnClickListener {
                    listener.onDescriptionClick(task)
                }
            }

            rowView.findViewById<ImageView>(R.id.taskDeleteButton).setOnClickListener {
                listener.onDeleteClick(task)
            }

            return rowView
        }
    }

    //TaskItemListenerというInterfaceを実装
    //リストの状態表示、内容、削除ボタンがタップされた際のイベントをそれぞれ実装
    interface TaskItemListener {

        fun onStateClick(task: Task)

        fun onDescriptionClick(task: Task)

        fun onDeleteClick(task: Task)
    }
}