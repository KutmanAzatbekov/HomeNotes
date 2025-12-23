package com.geeks.homenotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.geeks.homenotes.R
import com.geeks.homenotes.data.local.Pref
import com.geeks.homenotes.data.models.OnBoardModel
import com.geeks.homenotes.databinding.FragmentOnBoardBinding
import com.geeks.homenotes.ui.main.adapter.OnBoardAdapter

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    private lateinit var adapter: OnBoardAdapter

    private lateinit var pref: Pref


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OnBoardAdapter(getOnBoardList(), ::navigateToMain, ::onSkip)
        binding.vpOnBoard.adapter = adapter
        binding.circleIndicator.setViewPager(binding.vpOnBoard)
        adapter.registerAdapterDataObserver(binding.circleIndicator.adapterDataObserver)
        pref = Pref(requireContext())

        binding.vpOnBoard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val LastPage = position == adapter.itemCount - 1
                if (LastPage){
                    binding.btnStart.visibility = View.VISIBLE
                } else{
                    binding.btnStart.visibility = View.INVISIBLE
                }
            }
        })

        binding.btnStart.setOnClickListener {
            pref.setOnBoardingShown()
            navigateToMain()
        }

    }

    private fun navigateToMain(){
        findNavController().navigate(OnBoardFragmentDirections.actionOnBoardFragmentToAuthFragment())
    }

    private fun onSkip(endPosition: Int){
        binding.vpOnBoard.currentItem = endPosition
    }

    private fun getOnBoardList(): List<OnBoardModel>{
        return listOf(
            OnBoardModel(
                "Удобство",
                "Удобство\n" +
                        "Создавайте заметки в два клика! Записывайте мысли, идеи и важные задачи мгновенно.",
                R.drawable.gif_onboard_1
            ),
            OnBoardModel(
                "Организация",
                "Организация\n" +
                        "Организуйте заметки по папкам и тегам. Легко находите нужную информацию в любое время.",
                R.drawable.gif_onboard_2
            ),
            OnBoardModel(
                "Синхронизация",
                "Синхронизация\n" +
                        "Синхронизация на всех устройствах. Доступ к записям в любое время и в любом месте.",
                R.drawable.gif_onboard_3
            )
        )
    }

}