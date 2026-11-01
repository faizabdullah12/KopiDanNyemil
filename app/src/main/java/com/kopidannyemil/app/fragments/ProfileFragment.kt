package com.kopidannyemil.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kopidannyemil.app.data.AppState
import com.kopidannyemil.app.databinding.FragmentProfileBinding
import com.kopidannyemil.app.util.openWelcomeAndClear

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.setText(AppState.currentUser?.fullName ?: "")
        refreshHeader()

        binding.btnSave.setOnClickListener {
            val ok = AppState.updateName(binding.etName.text.toString())
            val message = if (ok) "Profil berhasil disimpan" else "Nama tidak boleh kosong"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            refreshHeader()
        }

        binding.btnLogout.setOnClickListener {
            AppState.logout()
            requireActivity().openWelcomeAndClear()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) refreshHeader()
    }

    private fun refreshHeader() {
        if (_binding == null) return
        val name = AppState.displayName
        binding.tvGreeting.text = "Halo $name,"
        binding.tvAvatar.text = name.take(1).uppercase()
        binding.tvFullName.text = name
        binding.tvUsername.text = "@${AppState.currentUser?.username ?: "-"}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
