package siergo_o.onlinernews.presentation.screen.detail;

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import siergo_o.onlinernews.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : Fragment() {

    companion object {
        private const val ARG_POST_URL = "postUrl"
    }

    private var _viewBinding: FragmentNewsDetailsBinding? = null
    private val viewBinding: FragmentNewsDetailsBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            FragmentNewsDetailsBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.webView.apply {
            settings.javaScriptEnabled = true
            loadUrl(arguments?.getString(ARG_POST_URL))
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
