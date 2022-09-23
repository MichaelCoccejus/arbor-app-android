package eu.berrytopia.arbor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val mTask: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    /*
    Bestimmt die View-Elemente für den Adapter.
    */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskNameView: TextView
        var createdView: TextView

        init {
            taskNameView = itemView.findViewById(R.id.taskName)
            createdView = itemView.findViewById(R.id.createdText)
        }
    }

    /*
    Es wird derzeit nur diese Art von Adapter benötigt.
    Die View (task_list_view) ist bereits im Layoutverzeichnis.
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.event_list_view, parent, false)
        return ViewHolder(v)
    }

    /*
    Hier werden die Daten gesetzt.
    */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskNameView.text = mTask[position].taskName
        holder.createdView.text = mTask[position].created.toString()
    }

    // Selbsterklärend.
    override fun getItemCount(): Int {
        return mTask.size
    }
}