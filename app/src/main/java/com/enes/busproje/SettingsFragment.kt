package com.enes.busproje

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enes.busproje.databinding.*
import com.enes.busproje.settings.AboutUsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GithubAuthCredential
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {
    private lateinit var intent: Intent
lateinit var binding: FragmentSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPaylas.setOnClickListener {
            intent = Intent(Intent.ACTION_SEND)
            intent.type = "type/palin"
            val shareBody = "Please download the app"
            val shareSub =
                "https://play.google.com/store/apps/details?id=com.asis.akillibilet&gl=TR"
            intent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
            intent.putExtra(Intent.EXTRA_TEXT, shareSub)
            startActivity(Intent.createChooser(intent, "Share Our App"))
        }
        binding.btnHakkimizda.setOnClickListener {
            Intent(requireActivity(),AboutUsActivity::class.java).let {
                activity?.startActivity(it)
            }
        }
        binding.btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Intent(requireActivity(),MainActivity::class.java).let{
                activity?.startActivity(it)
                activity?.finish()
            }
        }
        binding.btnChangePassword.setOnClickListener {
            Intent(requireActivity(),ChangePasswordActivity::class.java).let {
                activity?.startActivity(it)
            }
        }
        binding.btnDilDegistir.setOnClickListener {
            Intent(requireActivity(),LanguageActivity::class.java).let{
                activity?.startActivity(it)
            }
        }
    }

}





