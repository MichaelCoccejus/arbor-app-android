package eu.berrytopia.arbor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView

class MaintenanceAdapter(
    val context: Context,
    val mGeoObject: List<GeoObject>,
    val treePicList: List<Media>
) :
    RecyclerView.Adapter<MaintenanceAdapter.ViewHolder>() {

    /*
    Bestimmt die View-Elemente für den Adapter.
    */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var treePic: ImageView
        var nameTextView: TextView

        init {
            treePic = itemView.findViewById(R.id.imageView)
            nameTextView = itemView.findViewById(R.id.treeGridName)
        }

        // Das ist der OnClickListener für die Elemente.
        override fun onClick(p0: View?) {
            val intent = Intent()
            val currentPosition = adapterPosition
            intent.putExtra("GeoObj", mGeoObject[currentPosition])
            ContextCompat.startActivity(this.itemView.context, intent, null)
        }
    }

    /*
    Es wird derzeit nur diese Art von Adapter benötigt.
    Die View (maintenance_tile) ist bereits im Layoutverzeichnis.
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.maintenance_tile, parent, false)
        return ViewHolder(v)
    }

    /*
    Hier werden die Daten gesetzt.
    */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = mGeoObject[position].name
        holder.treePic.setImageURI(treePicList[position].fullUri.toUri())
    }

    // Selbsterklärend.
    override fun getItemCount(): Int {
        return mGeoObject.size
    }
}