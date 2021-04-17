package br.com.halsservice.presenter.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.halsservice.databinding.ClienteAdapterBinding
import br.com.halsservice.domain.Cliente
import br.com.halsservice.utilities.RandomColors
import java.util.*

/**
 * Created by RubioAlves on 16/04/2021
 */
class ClienteAdapter(private val lista: MutableList<Cliente>) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {


    inner class ClienteViewHolder(private val itemBinding: ClienteAdapterBinding) : RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(cliente: Cliente) {

            val locale = Locale("pt", "BR")
            val colors = RandomColors()
            val random = colors.getColor()

            val nome = cliente.nome
            val letraMaiuscula = nome.substring(0,1).toUpperCase(locale)

            itemBinding.nomeCliente.text = cliente.nome
            itemBinding.enderecoCliente.text = cliente.endereco
            itemBinding.bairroCliente.text = cliente.bairro
            itemBinding.numeroCliente.text = cliente.numero.toString()
            itemBinding.telefoneCliente.text = cliente.telefone
            itemBinding.letraOval.text = letraMaiuscula

            itemBinding.letraOval.background = GradientDrawable().apply {

                shape = GradientDrawable.OVAL
                cornerRadius = 48f
                setColor(random)


            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {

        val itemBinding = ClienteAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClienteViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {

        holder.bind(lista[position])
    }

    override fun getItemCount(): Int = lista.size

}