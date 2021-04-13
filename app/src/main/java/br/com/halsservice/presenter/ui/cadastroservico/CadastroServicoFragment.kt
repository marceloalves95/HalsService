package br.com.halsservice.presenter.ui.cadastroservico

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.halsservice.R
import br.com.halsservice.framework.viewmodel.CadastroServicoViewModel

class CadastroServicoFragment : Fragment() {

    companion object {
        fun newInstance() = CadastroServicoFragment()
    }

    private lateinit var viewModel: CadastroServicoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cadastro_servico_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CadastroServicoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}