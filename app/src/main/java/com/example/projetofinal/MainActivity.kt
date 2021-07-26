package com.example.projetofinal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projetofinal.dataSource.TaskDataSource
import com.example.projetofinal.databinding.ActivityMainBinding
import com.example.projetofinal.ui.AddTaskActivity
import com.example.projetofinal.ui.AddTaskActivity.Companion.TASK_ID
import com.example.projetofinal.ui.TaskListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTask.adapter = adapter
        updateList()

        insertListeners()
    }
    private fun insertListeners() {
        binding.btadd.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)

        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)


        }

        adapter.listenerDelete ={
            TaskDataSource.deleteTask(it)
            updateList()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList() {
        val list = TaskDataSource.getList()
        binding.includeEmpty.emptyState.visibility = if(list.isEmpty()) View.VISIBLE

     else View.GONE


        adapter.submitList(list)
    }

    companion object{
        private const val CREATE_NEW_TASK = 1000
    }


}