package com.example.myapplication.fragment.profile
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.snackBar
import com.example.myapplication.util.*
import com.lassi.data.media.MiMedia
import com.lassi.domain.media.LassiOption
import com.lassi.presentation.builder.Lassi
import com.lassi.presentation.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private var editProfileUrl: File? = null

    private lateinit var lassiResult: ActivityResultLauncher<Intent>

    var sideMenuBroadcaster: LocalBroadcastManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        ViewCompat.setOnApplyWindowInsetsListener(requireActivity().window.decorView) { v, insets ->
            val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())

            val height = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

            if (_binding != null) {
                if (isKeyboardVisible) {
                    binding.clMain.setPadding(
                        0, 0, 0, height
                    )
                } else {
                    binding.clMain.setPadding(
                        0, 0, 0, 30
                    )
                }
            }

            return@setOnApplyWindowInsetsListener insets
        }
        binding.viewModels = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpUi()
        setOnClicks()
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goBack()
                }
            })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun goBack() {
        sideMenuBroadcaster?.sendBroadcast(
            Intent(Constant1.BRODCAST_RECIVER.SIDE_MENU)
        )
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {
        viewModel.user.observe(viewLifecycleOwner){users->
            users.firstOrNull()?.let {
                binding.user = it
                binding.txtphoneno.setText("${it.countryCode} ${it.phone}")
                viewModel.firstName.value=it.firstName?:""
                viewModel.lastName.value=it.lastName?:""


            }

        }
        viewModel.verifyprofilersponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Error ->{
                    response.message?.snackBar(requireView(),requireContext())
                }
                is NetworkResult.Idle ->{

                }
                is NetworkResult.Loading ->{

                }
                is NetworkResult.Success ->{
                    response.data?.data?.let {
                        lifecycleScope.launch{
                            with(Dispatchers.IO){
                                viewModel.saveUser(it.copy(uuid = 1))
                                PrefManager.setBoolean(PrefManager.IS_LOGIN, true)
                                PrefManager.setString(PrefManager.ACCESS_TOKEN, it.authToken)

                            }

                        }
                    }
                }


                else -> {}
            }

        }

        viewModel.verifydeleteaccount.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Error -> {

                    response.message?.let {

                    }
                }
                is NetworkResult.Idle -> {

                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    Utils.logout(requireContext())

                }
                else -> {}
            }
        }


    }

    private fun setOnClicks() {
        binding.imgback.setOnClickListener {
            goBack()
        }
        binding.txtchangepassword.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()

            )

        }

        binding.deleteaccount.setOnClickListener {
            viewModel.verifyDelete()
        }
        binding.img1.setOnClickListener {
            lassiResult.launch(
                Lassi(requireContext())
                    .with(LassiOption.CAMERA_AND_GALLERY)
                    .setMaxCount(1)
                    .setMediaType(com.lassi.domain.media.MediaType.IMAGE)
                    .setCropType(CropImageView.CropShape.RECTANGLE)
                    .setStatusBarColor(R.color.purple)
                    .setToolbarColor(R.color.purple)
                    .setProgressBarColor(R.color.purple)
                    .setCropAspectRatio(1, 1)
                    .enableFlip()
                    .enableRotate()
                    .build()
            )
        }
    }

    private fun setUpUi() {
        sideMenuBroadcaster = LocalBroadcastManager.getInstance(requireContext())
        lassiResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedMedia =
                    it.data?.getSerializableExtra(KeyUtils.SELECTED_MEDIA) as ArrayList<MiMedia>
                if (!selectedMedia.isNullOrEmpty()) {
                    selectedMedia.forEachIndexed { index, miMedia ->
                        val file = File(miMedia.path)
                        if(file.exists()){
                            binding.img1.load(file){
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            }
                            editProfileUrl = file
//                            val imageBean = ImageBean()
//                            imageBean.imageUrl = file.toURI().toString()
//                            if(reviewImages.size <= 1){
//                                reviewImages.add(imageBean)
//                            }
//                            showReviewImages()
                        }
                    }
                }
            }
        }

    }
}


