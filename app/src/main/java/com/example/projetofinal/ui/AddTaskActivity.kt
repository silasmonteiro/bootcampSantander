package com.example.projetofinal.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetofinal.dataSource.TaskDataSource
import com.example.projetofinal.databinding.ActivityAddTaskBinding
import com.example.projetofinal.extensions.format
import com.example.projetofinal.extensions.text
import com.example.projetofinal.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)){

          val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.jogo.text = it.jogo
                binding.data.text = it.date
                binding.hora.text = it.hours
                binding.descricao.text = it.description
            }
        }
        insertListeners()
    }

     private fun insertListeners(){
       binding.data.editText?.setOnClickListener{
          val datePicker = MaterialDatePicker.Builder.datePicker().build()
           val timeZone = TimeZone.getDefault()
           var offset = timeZone.getOffset(Date().time) * -1
           datePicker.addOnPositiveButtonClickListener {
               binding.data.text = Date(it + offset).format()
           }
            datePicker.show(supportFragmentManager, "DATA_PICKER_TAG")
        }

         binding.hora.editText?.setOnClickListener{

             val timerPiker = MaterialTimePicker.Builder()
                 .setTimeFormat(TimeFormat.CLOCK_24H)
                 .build()
             timerPiker.addOnPositiveButtonClickListener {
               val minute =  if(timerPiker.minute in 0..9) "0${timerPiker.minute}" else timerPiker.minute
                val hour =  if(timerPiker.hour in 0..9) "0${timerPiker.hour}" else timerPiker.hour

                 binding.hora.text = "$hour:$minute"

             }

             timerPiker.show(supportFragmentManager,null)
         }

         binding.btSalvar.setOnClickListener {
             val task = Task(
                 jogo = binding.jogo.text,
                 description = binding.descricao.text,
                 date = binding.data.text,
                 hours = binding.hora.text,
                 id = intent.getIntExtra(TASK_ID, 0)
             )
             TaskDataSource.insertTask(task)
             setResult(Activity.RESULT_OK)
             finish()
         }

         binding.btCancelar.setOnClickListener {
             finish()

         }

    }

    companion object {
        const val TASK_ID = "task_id"
    }
}