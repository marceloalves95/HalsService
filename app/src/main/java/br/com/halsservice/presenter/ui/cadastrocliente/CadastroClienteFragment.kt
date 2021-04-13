package br.com.halsservice.presenter.ui.cadastrocliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.halsservice.R
import br.com.halsservice.framework.viewmodel.CadastroClienteViewModel

class CadastroClienteFragment : Fragment() {

    companion object {
        fun newInstance() = CadastroClienteFragment()
    }

    private lateinit var viewModel: CadastroClienteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cadastro_cliente_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CadastroClienteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}