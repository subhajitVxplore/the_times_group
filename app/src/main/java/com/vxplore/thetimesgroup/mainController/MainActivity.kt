package com.vxplore.thetimesgroup.mainController

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import com.vxplore.thetimesgroup.R

import com.vxplore.thetimesgroup.navigation.MainNavGraph
import com.vxplore.thetimesgroup.ui.theme.TheTimesGroupTheme
import com.vxplore.thetimesgroup.utility.FileDownloadWorker
import com.vxplore.thetimesgroup.utility.ItemFile
import com.vxplore.thetimesgroup.utility.MyFileModel
import com.vxplore.thetimesgroup.viewModels.BaseViewModel
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {

        val baseViewModel by viewModels<BaseViewModel>()

        super.onCreate(savedInstanceState)

        requestMultiplePermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            var isGranted = false
            it.forEach { s, b ->
                isGranted = b
            }

            if (!isGranted) {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }
        }


        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        setContent {
            TheTimesGroupTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    backgroundColor = MaterialTheme.colors.background,
                ) { paddingValues ->
                    MainNavGraph(
                        navHostController = rememberNavController(),
                        navigationChannel = baseViewModel.appNavigator.navigationChannel,
                        paddingValues = paddingValues,
                    )
                }



            }
//            val context= LocalContext.current
//            ShowItemFileLayout(context)
        }
    }


//////////////////////////////////////////////////////////////
    companion object {
        private var instance: MainActivity? = null
        @JvmStatic
        fun getInstance(): MainActivity? {
            return instance
        }
    }
//------------------------------------------------------
fun startDownloadingFile(
    file: MyFileModel,
    success:(String) -> Unit,
    failed:(String) -> Unit,
    running:() -> Unit
) {
    val data = Data.Builder()
    val workManager = WorkManager.getInstance(this)

    data.apply {
        putString(FileDownloadWorker.FileParams.KEY_FILE_NAME, file.name)
        putString(FileDownloadWorker.FileParams.KEY_FILE_URL, file.url)
        putString(FileDownloadWorker.FileParams.KEY_FILE_TYPE, file.type)
    }

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    val fileDownloadWorker = OneTimeWorkRequestBuilder<FileDownloadWorker>()
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()


    workManager.enqueueUniqueWork(
        "oneFileDownloadWork_${System.currentTimeMillis()}",
        ExistingWorkPolicy.KEEP,
        fileDownloadWorker
    )

    workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
        .observe(this){ info->
            info?.let {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        success(it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI) ?: "")
                    }
                    WorkInfo.State.FAILED -> {
                        failed("Downloading failed!")
                    }
                    WorkInfo.State.RUNNING -> {
                        running()
                    }
                    else -> {
                        failed("Something went wrong")
                    }
                }
            }
        }
}
//-------------------------------------------------------------------------
@Composable
fun ShowItemFileLayout(viewModel: BillingScreenViewModel, context: Context) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
val pdfString= remember {
    mutableStateOf("${viewModel.pdfData.value}")
}
        Log.d("ShowItemFileLayout", "ShowItemFileLayout:= ${viewModel.pdfData.value}")



        val pdfData = remember {
            mutableStateOf(
                MyFileModel(
                    id = "10",
                    name = "Pdf File Testt",
                    type = "PDF",
                    //url = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
                    // url = viewModel.pdfUrl.value,
                   // url ="${viewModel.pdfUrl.value}",
                    // url =pdfString.value,
                     url = "https://www.v-xplore.com/dev/rohan/toi-ci3/assets/uploads/pdf_bills/WA4RzbMoti.pdf",
                    downloadedUri = null
                )

            )
        }


        ItemFile(
            file = pdfData.value,
            startDownload = {
                startDownloadingFile(
                    file = pdfData.value,
                    success = {
                        pdfData.value = pdfData.value.copy().apply {
                            isDownloading = false
                            downloadedUri = it
                        }

                    },
                    failed = {
                        pdfData.value = pdfData.value.copy().apply {
                            isDownloading = false
                            downloadedUri = null
                        }
                    },
                    running = {
                        pdfData.value = pdfData.value.copy().apply {
                            isDownloading = true
                        }
                    }
                )
            },

            openFile = {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(it.downloadedUri?.toUri(), "application/pdf")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    ContextCompat.startActivity(context, intent, null)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Can't open Pdf", Toast.LENGTH_SHORT).show()
                }
            },
            viewModel = viewModel
        )

        LaunchedEffect(key1 = viewModel.pdfData.value) {
            viewModel.pdfData.value.let {
                if(it.isNotEmpty()) {
                    pdfData.value = pdfData.value.copy(downloadedUri = it)
                }
            }
        }
    }
}



}//end of class
