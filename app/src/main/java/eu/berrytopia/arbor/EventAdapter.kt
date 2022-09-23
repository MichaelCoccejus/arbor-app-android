package eu.berrytopia.arbor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EventAdapter(private val mEvent: List<Event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    /*
    Bestimmt die View-Elemente für den Adapter.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventNameView: TextView
        var timestampTextView: TextView

        init {
            eventNameView = itemView.findViewById(R.id.eventName)
            timestampTextView = itemView.findViewById(R.id.timestampText)
        }
    }

    /*
    Es wird derzeit nur diese Art von Adapter benötigt.
    Die View (event_list_view) ist bereits im Layoutverzeichnis.
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
        holder.eventNameView.text = mEvent[position].eventName
        holder.timestampTextView.text = mEvent[position].timestamp.toString()
    }

    // Selbsterklärend.
    override fun getItemCount(): Int {
        return mEvent.size
    }
}