package siergo_o.onlinernews.presentation.screen.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import siergo_o.onlinernews.databinding.FragmentSplashBinding

class SplashFragment: Fragment() {

    private var _viewBinding: FragmentSplashBinding? = null
    private val viewBinding: FragmentSplashBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            FragmentSplashBinding.inflate(inflater, container, false).also { _viewBinding = it }.root
}