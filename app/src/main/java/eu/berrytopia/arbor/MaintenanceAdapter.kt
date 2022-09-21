package eu.berrytopia.arbor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MaintenanceAdapter(val context: Context, val mGeoObject: List<GeoObject>, val treePicList: List<Photos>) :
    RecyclerView.Adapter<MaintenanceAdapter.ViewHolder>() {
    inner class ViewHolder : RecyclerView.ViewHolder {
        var treePic: ImageView
        var nameTextView: TextView

        constructor(itemView: View) : super(itemView) {
            treePic = itemView.findViewById(R.id.imageView)
            nameTextView = itemView.findViewById(R.id.treeGridName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.maintenance_tile, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = mGeoObject[position].name
        holder.treePic.setImageURI(treePicList[position].fullUri)
    }

    override fun getItemCount(): Int {
        return mGeoObject.size
    }

}