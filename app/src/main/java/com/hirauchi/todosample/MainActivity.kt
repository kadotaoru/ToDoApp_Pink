package com.hirauchi.todosample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ToolbarをActionVarに設定setSupportActionBar
        toolbar.also {
            it.setTitleTextColor(getColor(R.color.white)) //Accentにしてみた
            setSupportActionBar(it)
        }
        //Fragmentの表示
        //FragmentTransactionの、replace()に、FrameLayoutのIDと、MainFragmentを指定し、
        // MainFragmentを表示するよう実装
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.contentFrame, MainFragment.newInstance())
        }.commit()
    }
}
