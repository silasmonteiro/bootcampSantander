package com.example.projetofinal.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinal.R
import com.example.projetofinal.databinding.ItemTaksBinding
import com.example.projetofinal.model.Task

class TaskListAdapter : androidx.recyclerview.widget.ListAdapter<Task, TaskListAdapter.TaskViewHolder >(DiffCallbak()){

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaksBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaksBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Task) {
            binding.tituloJogo.text = item.jogo
            binding.dataJogo.text = "${item.date} ${item.hours}"
            binding.imgOpcao.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            val imgOpcao = binding.imgOpcao
            val popupMenu = PopupMenu(imgOpcao.context, imgOpcao)
            popupMenu.menuInflater.inflate(R.menu.popupmenu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.action_edit -> listenerEdit(item)

                    R.id.action_delete -> listenerDelete(item)

                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()

        }


    }

}

class DiffCallbak : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

}