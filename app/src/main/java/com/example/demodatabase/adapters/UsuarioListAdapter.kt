package com.example.demodatabase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demodatabase.R
import com.example.demodatabase.dataroom.Usuario

class UsuarioListAdapter : ListAdapter<Usuario, UsuarioListAdapter.UsuarioViewHolder>( UsuariosComparator() ) {

    private var usuarioDelete: ((Usuario) -> Unit)? = null

    fun setUsuarioDelete(action: (Usuario) -> Unit) {
        usuarioDelete = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    inner class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeItemView: TextView = itemView.findViewById(R.id.nome)
        private val emailItemView: TextView = itemView.findViewById(R.id.email)
        private val btDelete: Button = itemView.findViewById(R.id.btDelete)

        fun bind(usuario: Usuario) {
            nomeItemView.text = usuario.name
            emailItemView.text = usuario.email

            btDelete.setOnClickListener {
                usuarioDelete?.invoke(usuario)
            }
        }
    }

    class UsuariosComparator : DiffUtil.ItemCallback<Usuario>() {
        override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.id == newItem.id
        }
    }
}