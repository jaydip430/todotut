package com.jaydip.todotut.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jaydip.todotut.R
import com.jaydip.todotut.data.models.Priority
import com.jaydip.todotut.data.models.ToDoData
import com.jaydip.todotut.fragments.update.UpdateFragmentArgs

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title_text).text = dataList[position].title

        holder.itemView.findViewById<TextView>(R.id.description_text).text =
            dataList[position].description

        val item= holder.itemView.findViewById<ConstraintLayout>(R.id.row_background)

       item.setOnClickListener{
           val action=ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])

            holder.itemView.findNavController().navigate(action)
        }

        val priority = dataList[position].priority

        when (priority) {
            Priority.HIGH -> holder.itemView.findViewById<CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(
                    ContextCompat.getColor
                        (holder.itemView.context, R.color.red)
                )
            Priority.MEDIUM -> holder.itemView.findViewById<CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(
                    ContextCompat.getColor
                        (holder.itemView.context, R.color.yellow)
                )
            Priority.LOW -> holder.itemView.findViewById<CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(
                    ContextCompat.getColor
                        (holder.itemView.context, R.color.green)
                )
        }



    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoData: List<ToDoData>){
        this.dataList=toDoData
        notifyDataSetChanged()
    }
}