package br.com.halsservice.adapters

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.RecyclerView
import br.com.halsservice.R
import br.com.halsservice.databinding.ServicoAdapterBinding
import br.com.halsservice.domain.Servico
import br.com.halsservice.ui.servico.ServicoFragment
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by RubioAlves on 23/04/2021
 */
class ServicoAdapter(val lista: MutableList<Servico>, val servicoFragment: ServicoFragment, val clientes:String) : RecyclerView.Adapter<ServicoAdapter.ServicoAdapterViewHolder>() {

    val selectedItems = SparseBooleanArray()
    private var currentSelectedPos = -1
    private var listaServicosAll:MutableList<Servico> = ArrayList()

    init {
        listaServicosAll.addAll(lista)
    }

    inner class ServicoAdapterViewHolder(private val itemBinding: ServicoAdapterBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(servico:Servico) {

            val decimal = BigDecimal(servico.valor_servico).setScale(2, RoundingMode.HALF_EVEN)
            val valorServico = "R$ $decimal"
            val dataReparo = "Reparo concluído no dia ${servico.data_reparo}"
            itemBinding.viewServico.text = servico.tipo_servico
            itemBinding.viewDataReparo.text = dataReparo
            itemBinding.viewDescricaoServico.text = servico.descricao
            itemBinding.viewValor.text = valorServico
            itemBinding.viewCliente.text = clientes
            itemBinding.viewGarantia.text = servico.garantia
            itemBinding.imageService.setImageResource(R.drawable.ic_toolbox_outline)

            itemBinding.cardView.setOnLongClickListener {

                onItemLongClick?.invoke(adapterPosition)


                return@setOnLongClickListener true


            }
            itemBinding.cardView.setOnClickListener {

                if (selectedItems.isNotEmpty()) onItemClick?.invoke(adapterPosition)

                servicoFragment.actionMode?.finish()


            }

            if (currentSelectedPos == adapterPosition) currentSelectedPos = -1

            itemBinding.cardView.isChecked = servico.selected

            if (itemBinding.cardView.isChecked) itemBinding.cardView.strokeColor = ContextCompat.getColor(servicoFragment.requireContext(), R.color.blueAccent)
            else itemBinding.cardView.strokeColor = ContextCompat.getColor(servicoFragment.requireContext(), R.color.white)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicoAdapterViewHolder {

        val itemBinding = ServicoAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServicoAdapterViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder:ServicoAdapterViewHolder, position: Int) {

        holder.bind(lista[position])
    }

    override fun getItemCount(): Int = lista.size

    var onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null

    fun toggleSelection(position: Int) {

        currentSelectedPos = position
        if (selectedItems[position, false]) {
            selectedItems.delete(position)

            lista[position].selected = false

        } else {
            selectedItems.put(position, true)
            lista[position].selected = true
        }

        notifyItemChanged(position)


    }

}