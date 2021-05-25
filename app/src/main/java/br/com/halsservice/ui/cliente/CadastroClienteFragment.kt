package br.com.halsservice.ui.cliente

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.halsservice.R
import br.com.halsservice.databinding.FragmentCadastroClienteBinding
import br.com.halsservice.domain.Cliente
import br.com.halsservice.state.EnderecoState
import br.com.halsservice.ui.main.MainActivity
import br.com.halsservice.utils.formatter.FormatadorTelefone
import br.com.halsservice.utils.validator.Validador
import br.com.halsservice.utils.validator.ValidadorPadrao
import br.com.halsservice.utils.validator.ValidadorTelefone
import br.com.halsservice.viewmodel.cliente.ClienteViewModel
import br.com.halsservice.viewmodel.endereco.EnderecoViewModel
import com.google.android.material.button.MaterialButton.ICON_GRAVITY_TEXT_START
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class CadastroClienteFragment : Fragment() {

    private var _binding: FragmentCadastroClienteBinding? = null
    private val binding get() = _binding!!
    lateinit var nome: String
    lateinit var endereco: String
    private lateinit var numero: String
    lateinit var bairro: String
    private lateinit var estado: String
    lateinit var telefone: String

    private val args by navArgs<CadastroClienteFragmentArgs>()
    private val clienteViewModel:ClienteViewModel by viewModel()
    private val enderecoViewModel:EnderecoViewModel by viewModel()
    private val validadores:MutableList<Validador> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_cadastro_cliente)
        _binding = FragmentCadastroClienteBinding.inflate(inflater, container, false)

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

        initValidadores()
        initCampos()


    }

    private fun salvar() {

        val estaValido = validarTodosCampos()

        if (estaValido){

            initEditText()

            val cliente = Cliente(0, nome, endereco,numero.toInt(),bairro,estado,telefone,false)

            clienteViewModel.adicionarCliente(cliente)
            mensagemAviso("Cliente salvo com sucesso")
            findNavController().navigate(R.id.action_navigation_cadastro_cliente_to_navigation_cliente)

        }


    }

    private fun validarTodosCampos(): Boolean {

        var estaValido = true
        validadores.forEach { validador -> if (!validador.estaValido()) estaValido = false }
        return estaValido

    }

    private fun atualizar(){

        val estaValido = validarTodosCampos()

        if (estaValido){

            initEditText()

            val cliente = Cliente(args.id, nome, endereco,numero.toInt(), bairro, estado, telefone, false)
            clienteViewModel.atualizarCliente(cliente)
            mensagemAviso("Cliente atualizada com sucesso")
            findNavController().navigate(R.id.action_navigation_cadastro_cliente_to_navigation_cliente)

        }


    }


    private fun initCampos(){


        if (args.cliente == null){

            binding.cadastroAtualizacao.iconGravity = ICON_GRAVITY_TEXT_START
            binding.cadastroAtualizacao.setIconResource(R.drawable.ic_content_save)
            binding.cadastroAtualizacao.setText(R.string.cadastrar_cliente)
            binding.cadastroAtualizacao.setOnClickListener {
                salvar()
            }


        }else{

            (activity as MainActivity).supportActionBar?.setTitle(R.string.title_atualizacao_cliente)
            binding.nome.setText(args.cliente?.nome)
            binding.endereco.setText(args.cliente?.endereco)
            binding.bairro.setText(args.cliente?.bairro)
            binding.estado.setText(args.cliente?.estado)
            binding.numero.setText(args.cliente?.numero.toString())
            binding.telefone.setText(args.cliente?.telefone)
            binding.cadastroAtualizacao.iconGravity = ICON_GRAVITY_TEXT_START
            binding.cadastroAtualizacao.setIconResource(R.drawable.ic_update_white)
            binding.cadastroAtualizacao.setText(R.string.atualizar_cliente)
            binding.cadastroAtualizacao.setOnClickListener {
                atualizar()
            }

        }

    }

    private fun initEditText() {

        nome = binding.nome.text.toString().trim()
        endereco = binding.endereco.text.toString().trim()
        numero = binding.numero.text.toString().trim()
        bairro = binding.bairro.text.toString().trim()
        estado = binding.estado.text.toString().trim()
        telefone = binding.telefone.text.toString().trim()


    }

    private fun mensagemAviso(mensagem: String) {

        Snackbar.make(requireView(), mensagem, Snackbar.LENGTH_SHORT).show()


    }

    private fun buscaCEP() {

        val cep = binding.cep.editableText.toString().trim()
        enderecoViewModel.buscaEnderecoPelo(cep)

        lifecycleScope.launchWhenStarted {

            enderecoViewModel.enderecoState.collect { state->

                when(state){
                    EnderecoState.Empty -> TODO()
                    is EnderecoState.Error -> { mensagemAviso(state.message) }
                    is EnderecoState.Loading -> {

                        if (state.isLoading) binding.progressBar.visibility = View.VISIBLE
                        else binding.progressBar.visibility = View.INVISIBLE

                    }
                    is EnderecoState.Sucess -> {

                        state.response.let { cep->

                            if(cep?.logradouro == null && cep?.uf == null && cep?.bairro == null){

                                mensagemAviso("CEP não encontrado")

                            }else{

                                binding.endereco.setText(cep.logradouro)
                                binding.estado.setText(cep.uf)
                                binding.bairro.setText(cep.bairro)

                                binding.nome.requestFocus()
                            }



                        }



                    }
                }

            }

        }

    }

    private fun initValidadores(){

        validadorPadrao(binding.campoNome)
        validadorPadrao(binding.campoEndereco)
        validadorPadrao(binding.campoNumero)
        validadorPadrao(binding.campoBairro)
        validadorPadrao(binding.campoEstado)
        validadorTelefone(binding.campoTelefone)

    }


    private fun validadorTelefone(textInputLayout: TextInputLayout){

        val campo = textInputLayout.editText

        val validadorTelefone = ValidadorTelefone(textInputLayout, campo as TextInputEditText)
        val formatador = FormatadorTelefone()
        campo.setOnFocusChangeListener { _, hasFocus ->

            val telefoneDDD = campo.text.toString()

            if (hasFocus){
                val telefoneRemovido = formatador.remove(telefoneDDD)
                campo.setText(telefoneRemovido)
            }
            else{
                validadorTelefone.estaValido()
            }

        }

        campo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                textInputLayout.error = ""
            }

        })

    }


    private fun validadorPadrao(textInputLayout: TextInputLayout){

        val campo = textInputLayout.editText

        val validado = ValidadorPadrao(textInputLayout, campo as TextInputEditText)
        validadores.add(validado)

        campo.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus){
                if (!validado.estaValido()){
                    return@setOnFocusChangeListener
                } else{
                    validado.removeError(textInputLayout)
                }

            }

        }

        campo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                textInputLayout.error = ""
            }

        })

    }


}