package br.com.halsservice.presenter.ui.cliente

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.com.halsservice.R
import br.com.halsservice.data.repository.EnderecoRepository
import br.com.halsservice.databinding.FragmentCadastroClienteBinding
import br.com.halsservice.domain.Cliente
import br.com.halsservice.framework.api.ApiHelper
import br.com.halsservice.framework.service.RetrofitBuilder
import br.com.halsservice.framework.viewmodel.cliente.ClienteViewModel
import br.com.halsservice.framework.viewmodel.cliente.ClienteViewModelFactory
import br.com.halsservice.framework.viewmodel.endereco.EnderecoViewModel
import br.com.halsservice.framework.viewmodel.endereco.EnderecoViewModelFactory
import br.com.halsservice.presenter.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

class CadastroClienteFragment : BaseFragment() {

    private var _binding: FragmentCadastroClienteBinding? = null
    private val binding get() = _binding!!
    lateinit var application: Application
    lateinit var nome: String
    lateinit var endereco: String
    lateinit var numero: String
    lateinit var bairro: String
    lateinit var estado: String
    lateinit var ddd: String
    lateinit var telefone: String

    val viewModel by lazy {

        ViewModelProvider(this, ClienteViewModelFactory(application)).get(ClienteViewModel::class.java)

    }

    val enderecoViewModel by lazy {

        ViewModelProviders.of(this, EnderecoViewModelFactory(ApiHelper(RetrofitBuilder.halsServiceApi))).get(EnderecoViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        application = requireActivity().application!!
        _binding = FragmentCadastroClienteBinding.inflate(inflater, container, false)

        binding.cadastroAtualizacao.setOnClickListener {
            salvar()
        }
        binding.busca.setOnClickListener {
            buscaCEP()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mudancaTexto()

    }


    /*
    private fun buscarCEP() {

        val busca_cep = binding.cep.editableText.toString().trim()
        val campo_novo_cep = busca_cep.replace("-", "")
        Log.d("Teste", campo_novo_cep)

        binding.progressBar.visibility = View.VISIBLE
        val call = RetrofitBuilder.halsServiceApi.getCEP(campo_novo_cep)
        call.enqueue(object : Callback<CEP> {
            override fun onResponse(call: Call<CEP>, response: Response<CEP>) {

                response.body()?.let { cep ->

                    binding.endereco.setText(cep.logradouro)
                    binding.estado.setText(cep.uf)
                    binding.bairro.setText(cep.bairro)
                    binding.ddd.setText(cep.ddd)

                    binding.group.visibility = View.VISIBLE
                    binding.cadastroAtualizacao.isEnabled = true
                    binding.nome.requestFocus()
                    binding.progressBar.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<CEP>, t: Throwable) {
                t.message?.let { it1 ->
                    Log.e("Erro", it1)
                    binding.group.visibility = View.INVISIBLE
                }

            }

        })
    }

     */

    private fun salvar() {

        initCampos()
        if (validarCampos()) {

            val cliente = Cliente(0, nome, endereco,numero.toInt(),bairro,estado,telefone,false)
            viewModel.adicionarCliente(cliente)
            mensagemAtualizada("Cliente atualizado com sucesso")
            findNavController().navigate(R.id.action_navigation_cadastro_cliente_to_navigation_cliente)
        }


    }

    private fun initCampos() {

        nome = binding.nome.text.toString().trim()
        endereco = binding.endereco.text.toString().trim()
        numero = binding.numero.text.toString().trim()
        bairro = binding.bairro.text.toString().trim()
        estado = binding.estado.text.toString().trim()
        ddd = binding.ddd.text.toString().trim()
        telefone = binding.telefone.text.toString().trim()


    }

    private fun mensagemAtualizada(mensagem: String) {

        Snackbar.make(requireView(), mensagem, Snackbar.LENGTH_SHORT).show()

    }

    private fun validarCampos(): Boolean {

        var validador = true
        initCampos()

        val mensagemErro = "Preencha o campo corretamente"

        when {

            nome.isEmpty() && numero.isEmpty() && telefone.isEmpty() -> {

                binding.campoNome.error = mensagemErro
                binding.campoNumero.error = mensagemErro
                binding.campoTelefone.error = mensagemErro

                validador = false
                return validador

            }
            nome.isNotEmpty() && numero.isEmpty() && telefone.isEmpty() -> {

                binding.campoNumero.error = mensagemErro
                binding.campoTelefone.error = mensagemErro

                validador = false
                return validador

            }

            nome.isEmpty() && numero.isNotEmpty() && telefone.isEmpty() -> {

                binding.campoNome.error = mensagemErro
                binding.campoTelefone.error = mensagemErro

                validador = false
                return validador

            }
            telefone.isNotEmpty() && nome.isEmpty() && numero.isEmpty() -> {

                binding.campoNome.error = mensagemErro
                binding.campoNumero.error = mensagemErro

                validador = false
                return validador

            }
            nome.isEmpty() && numero.isNotEmpty() && telefone.isNotEmpty() -> {

                binding.campoNome.error = mensagemErro

                validador = false
                return validador

            }
            numero.isEmpty() && nome.isNotEmpty() && telefone.isNotEmpty() -> {

                binding.campoNumero.error = mensagemErro

                validador = false
                return validador

            }
            telefone.isEmpty() && nome.isNotEmpty() && numero.isNotEmpty() -> {

                binding.campoTelefone.error = mensagemErro

                validador = false
                return validador

            }


        }

        return validador

    }

    private fun buscaCEP() {

        val cep = binding.cep.editableText.toString().trim()
        binding.progressBar.visibility = View.VISIBLE
        enderecoViewModel.buscaEnderecoPelo(cep).observe(viewLifecycleOwner, { resultado ->

            when (resultado) {

                is EnderecoRepository.Resultado.Sucesso -> {

                    resultado.dado?.let { cep->

                        binding.endereco.setText(cep.logradouro)
                        binding.estado.setText(cep.uf)
                        binding.bairro.setText(cep.bairro)
                        binding.ddd.setText(cep.ddd)

                        binding.group.visibility = View.VISIBLE
                        binding.cadastroAtualizacao.isEnabled = true
                        binding.nome.requestFocus()
                        binding.progressBar.visibility = View.GONE
                    }

                }
                is EnderecoRepository.Resultado.Erro -> {
                    mensagemAtualizada(resultado.exception.message.toString())
                    binding.group.visibility = View.INVISIBLE
                }


                else -> {


                }
            }


        })


    }

    private fun mudancaTexto(){

        binding.nome.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.campoNome.error = ""
            }

        })
        binding.telefone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.campoTelefone.error = ""
            }

        })
        binding.numero.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.campoNumero.error = ""
            }

        })



    }


}