package com.example.reto1

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.net.URI

class PublicationAdapter :  RecyclerView.Adapter<PublicationViewHolder>(){

    private val publications = ArrayList<Publication>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.publicationrow,parent,false)
        val publicationView = PublicationViewHolder(row)
        return publicationView
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val publication = publications[position]
        val UR1 = Uri.parse(publication.URI)
        val URIprofile = Uri.parse(publication.URIprofile)
        holder.nameUserText.text = publication.nameUser
        holder.dateText.text = publication.date
        holder.locationText.text = publication.location
        holder.captionText.text = publication.caption
        holder.imagePublication.setImageURI(UR1)
        holder.imageProfile.setImageURI(URIprofile)

    }

    fun addPublication(publication:Publication){
        publications.add(publication)
    }

    override fun getItemCount(): Int {
        return publications.size
    }
}