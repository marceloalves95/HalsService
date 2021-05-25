package br.com.halsservice.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.halsservice.R
import br.com.halsservice.databinding.ClienteAdapterBinding
import br.com.halsservice.domain.Cliente
import br.com.halsservice.ui.cliente.ClienteArgs
import br.com.halsservice.ui.cliente.ClienteFragment
import br.com.halsservice.ui.cliente.ClienteFragmentDirections
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by RubioAlves on 16/04/2021
 */
class ClienteAdapter(val listaClientes: MutableList<Cliente>, val clienteFragment: ClienteFragment) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>(), Filterable {

    val selectedItems = SparseBooleanArray()
    private var currentSelectedPos = -1
    private var listaClientesAll:MutableList<Cliente> = ArrayList()

    init {
        listaClientesAll.addAll(listaClientes)
    }

    inner class ClienteViewHolder(private val itemBinding: ClienteAdapterBinding) : RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(cliente: Cliente) {

            val locale = Locale("pt", "BR")

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
                setColor(Color.parseColor("#3F51B5"))


            }

            itemBinding.cardView.setOnLongClickListener {

                onItemLongClick?.invoke(adapterPosition)


                return@setOnLongClickListener true


            }
            itemBinding.cardView.setOnClickListener {

                if (selectedItems.isNotEmpty()) onItemClick?.invoke(adapterPosition)

                clienteFragment.actionMode?.finish()


                argumentosCliente(cliente)

            }

            if (currentSelectedPos == adapterPosition) currentSelectedPos = -1

            itemBinding.cardView.isChecked = cliente.selected

            if (itemBinding.cardView.isChecked) itemBinding.cardView.strokeColor = ContextCompat.getColor(clienteFragment.requireContext(), R.color.blueAccent)
            else itemBinding.cardView.strokeColor = ContextCompat.getColor(clienteFragment.requireContext(), R.color.white)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {

        val itemBinding = ClienteAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClienteViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {

        holder.bind(listaClientes[position])
    }

    override fun getItemCount(): Int = listaClientes.size

    var onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null

    fun toggleSelection(position: Int) {

        currentSelectedPos = position
        if (selectedItems[position, false]) {
            selectedItems.delete(position)

            listaClientes[position].selected = false

        } else {
            selectedItems.put(position, true)
            listaClientes[position].selected = true
        }

        notifyItemChanged(position)


    }

    fun deletarClientes(){

        listaClientes.removeAll(listaClientes.filter { it.selected })

        notifyDataSetChanged()
        currentSelectedPos = -1


    }

    private fun argumentosCliente(cliente: Cliente){

        val clienteArgs = ClienteArgs(cliente.nome, cliente.endereco, cliente.bairro, cliente.numero, cliente.estado, cliente.telefone)
        val id = cliente.clienteId

        val action = ClienteFragmentDirections.actionNavigationClienteToNavigationCadastroCliente(clienteArgs, id)

        clienteFragment.findNavController().navigate(action)


    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val filteredList: MutableList<Cliente> = ArrayList()

                val locale = Locale("pt","BR")

                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList.addAll(listaClientesAll)
                }

                else {

                    listaClientesAll.forEach { cliente ->

                        if (cliente.nome.toLowerCase(locale).contains(constraint.toString().toLowerCase(locale)) || cliente.endereco.toLowerCase(locale).contains(constraint.toString().toLowerCase(locale)) || cliente.bairro.toLowerCase(locale).contains(constraint.toString().toLowerCase(locale)))  filteredList.add(cliente)

                    }


                }
                val results = FilterResults()
                results.values = filteredList

                return results


            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                listaClientes.clear()
                listaClientes.addAll(results?.values as MutableList<Cliente>)
                notifyDataSetChanged()

            }
        }

    }

}