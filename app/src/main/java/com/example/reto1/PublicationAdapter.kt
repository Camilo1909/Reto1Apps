package com.example.reto1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PublicationAdapter :  RecyclerView.Adapter<PublicationViewHolder>(){

    private val publications = ArrayList<Publication>()

    init {
        publications.add(Publication("Juan","123456","8:52","sdfsdfsdf"))
        publications.add(Publication("Fernanda","987456","9:35","dsfdsfs"))
        publications.add(Publication("Josue","321789","10:02","dsfsdfsd"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.publicationrow,parent,false)
        val publicationView = PublicationViewHolder(row)
        return publicationView
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val publication = publications[position]
        holder.nameUserText.text = publication.nameUser
        holder.dateText.text = publication.date
        holder.locationText.text = publication.location
        holder.captionText.text = publication.caption

    }

    fun addPublication(publication:Publication){
        publications.add(publication)
    }

    override fun getItemCount(): Int {
        return publications.size
    }
}