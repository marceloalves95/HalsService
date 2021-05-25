package br.com.halsservice.ui.servico

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.halsservice.R
import br.com.halsservice.adapters.ServicoAdapter
import br.com.halsservice.databinding.FragmentServicoBinding
import br.com.halsservice.viewmodel.servico.ServicoViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ServicoFragment : Fragment() {

    private var _binding: FragmentServicoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:ServicoAdapter
    var actionMode: ActionMode? = null
    private val servicoViewModel:ServicoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicoBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {

            findNavController().navigate(R.id.action_navigation_servico_to_navigation_cadastro_servico)

        }
        atualizarLayout()
        binding.swipeLayout.setOnRefreshListener {

            binding.swipeLayout.isRefreshing = false

            Snackbar.make(requireView(), "Atualização Concluida", Snackbar.LENGTH_SHORT).show()

        }


    }

    private fun setupRecyclerView(){

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        adapter.onItemLongClick= {

            enableActionMode(it)

        }

    }
    private fun atualizarLayout() {

        servicoViewModel.atualizarLista()
        servicoViewModel.listAll.observe(viewLifecycleOwner,{lista->

            servicoViewModel.getNomesClientes()
            servicoViewModel.nomes.observe(viewLifecycleOwner,{ clientes->

                clientes.forEach {

                    adapter = ServicoAdapter(lista, this,it)
                    setupRecyclerView()

                }

            })


        })

    }

    private fun enableActionMode(position: Int) {

        if (actionMode == null) actionMode = activity?.startActionMode(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                mode?.title = "Deletar Serviço"

                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item?.itemId == R.id.action_delete){
                    mode?.finish()
                    return true

                }
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                adapter.selectedItems.clear()
                adapter.lista
                    .filter { it.selected }
                    .forEach { it.selected = false }

                adapter.notifyDataSetChanged()
                actionMode = null
            }

        })

        adapter.toggleSelection(position)
        val size = adapter.selectedItems.size()
        if (size == 0){
            actionMode?.finish()
        }else{
            actionMode?.subtitle = "$size servico(s) selecionado(s)"
            actionMode?.invalidate()

        }



    }

}