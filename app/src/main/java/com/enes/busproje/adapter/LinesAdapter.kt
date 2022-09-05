package com.enes.busproje.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enes.busproje.R
import com.enes.busproje.model.Service
import kotlinx.android.synthetic.main.recycler_row.view.*
import java.util.ArrayList

class LinesAdapter(private val list: ArrayList<Service>, private val listener: Listener) : RecyclerView.Adapter<LinesAdapter.LinesVH>() {
    interface Listener{
        fun onItemClick(services: Service)//service : Service de alabilir.
    }
    class LinesVH(itemView : View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinesVH {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return LinesVH(itemView)
    }

    override fun onBindViewHolder(holder: LinesVH, position: Int) {
        holder.itemView.btnRecyclerView.text = list[position].description.toString()
        holder.itemView.btnRecyclerView.setOnClickListener{
            listener.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}