package com.example.reto1

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PublicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var nameUserText: TextView = itemView.findViewById(R.id.nameUserText)
    var dateText: TextView = itemView.findViewById(R.id.dateText)
    var locationText: TextView = itemView.findViewById(R.id.locationText)
    var captionText: TextView = itemView.findViewById(R.id.captionText)
    var imagePublication: ImageView = itemView.findViewById(R.id.imagePublication)
    var imageProfile:ImageView = itemView.findViewById(R.id.imageProfile)
    init {

    }

}