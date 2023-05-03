package com.vxplore.thetimesgroup.mainController

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import com.caysn.autoreplyprint.AutoReplyPrint
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import com.vxplore.thetimesgroup.R
import com.vxplore.thetimesgroup.navigation.MainNavGraph
import com.vxplore.thetimesgroup.ui.theme.TheTimesGroupTheme
import com.vxplore.thetimesgroup.utility.FileDownloadWorker
import com.vxplore.thetimesgroup.utility.ItemFile
import com.vxplore.thetimesgroup.utility.MyFileModel
import com.vxplore.thetimesgroup.utility.printerService.TestUtils
import com.vxplore.thetimesgroup.viewModels.BaseViewModel
import com.vxplore.thetimesgroup.viewModels.BillingScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import kotlin.io.path.absolutePathString
import kotlin.io.path.outputStream


val client = HttpClient(Android) {
    engine {
        // this: AndroidEngineConfig
        connectTimeout = 100_000
        socketTimeout = 100_000
    }
}

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity(), AutoReplyPrint.CP_OnPortOpenedEvent_Callback {
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mmDevice: BluetoothDevice? = null

    //end of class
    private var h: Pointer? = null
    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>

//////////////////////onCreate/////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {

        val baseViewModel by viewModels<BaseViewModel>()
        super.onCreate(savedInstanceState)
    //findBTaddress()
  //  Toast.makeText(this,"BtAddress::::${mmDevice?.address}",Toast.LENGTH_SHORT).show()

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
        success: (String) -> Unit,
        failed: (String) -> Unit,
        running: () -> Unit
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

            val pdfData = remember {
                mutableStateOf(
                    MyFileModel(
                        id = "10",
                        name = "Pdf File Testt",
                        type = "PDF",
                        url = "",
                        //url = "https://www.v-xplore.com/dev/rohan/toi-ci3/assets/uploads/pdf_bills/WA4RzbMoti.pdf",
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
//------------------------------------------------------------------------------------------------

                        if ((viewModel.pdfData!=null)) {
                            AutoReplyPrint.INSTANCE.CP_Port_Close(h)
                            openPrinterPort()
                            convertPdfToBitmap(target = viewModel.pdfData.value,coroutineScope = lifecycleScope) {
                                tryToPrint(it)
                            }
                        }

//------------------------------------------------------------------------------------------------
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setDataAndType(it.downloadedUri?.toUri(), "application/pdf")
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        ContextCompat.startActivity(context, intent, null)

                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context,"Can't open Pdf",Toast.LENGTH_SHORT).show()
                    }
                },
                viewModel = viewModel
            )

            LaunchedEffect(key1 = viewModel.pdfData.value) {
                viewModel.pdfData.value.let {
                    if (it.isNotEmpty()) {
                        pdfData.value = pdfData.value.copy(downloadedUri = it)
                    }
                }
            }
        }
    }

    /////////////////////////print service////////////////////////

    private fun tryToPrint(bitmaps: List<Bitmap>) {
        bitmaps.forEach { bitmap ->
            bitmap.apply {
                var printwidth = 384
                val width_mm = IntByReference()
                val height_mm = IntByReference()
                val dots_per_mm = IntByReference()

                Thread {
                    if (!AutoReplyPrint.INSTANCE.CP_Printer_GetPrinterResolutionInfo(
                            h,
                            width_mm,
                            height_mm,
                            dots_per_mm
                        )
                    ) {
                        printwidth = width_mm.value * dots_per_mm.value
                    }

                    val nh = (height * (384 / width))
                    val scaled = TestUtils.scaleImageToWidth(
                        this@apply,
                        printwidth
                    ) //Bitmap.createScaledBitmap(this@apply, 500, height_mm.value, true)


                    val result =
                        AutoReplyPrint.CP_Pos_PrintRasterImageFromData_Helper.PrintRasterImageFromBitmap(
                            h,
                            scaled.width,
                            scaled.height,
                            scaled,
                            AutoReplyPrint.CP_ImageBinarizationMethod_ErrorDiffusion,
                            AutoReplyPrint.CP_ImageCompressionMethod_None
                        )
                    if (!result) TestUtils.showMessageOnUiThread(
                        this@MainActivity,
                        "Write failed"
                    )
                    AutoReplyPrint.INSTANCE.CP_Pos_Beep(h, 1, 500)
                    //AutoReplyPrint.INSTANCE.CP_Port_Close(h)
                }.start()
            }
        }

    }

    private fun Context.convertPdfToBitmap(
        target: String ="",
        coroutineScope: CoroutineScope,
        onBitmapCreated: (List<Bitmap>) -> Unit
    ) {
        val bitmaps = mutableListOf<Bitmap>()
        coroutineScope.launch {
            try {
                val inputStream = client.get(urlString = target).body<InputStream>()
                val path = kotlin.io.path.createTempFile(prefix = "sample", suffix = ".pdf")
                inputStream.use { input ->
                    path.outputStream().use {
                        input.copyTo(it)
                    }
                }
                val file = File(path.absolutePathString())
                Log.d(
                    "TESTING",
                    "File ${path.absolutePathString()} ${file.isFile} ${file.name} ${file.path}"
                )
                val pdfRenderer = PdfRenderer(
                    ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                )
                repeat(pdfRenderer.pageCount) { idx ->
                    val page = pdfRenderer.openPage(idx)
                    val w = resources.displayMetrics.densityDpi / 72 * page.width
                    val h = resources.displayMetrics.densityDpi / 72 * page.height
                    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_PRINT)
                    val newBitmap = Bitmap.createBitmap(
                        bitmap.width,
                        bitmap.height, bitmap.config
                    )
                    val canvas = Canvas(newBitmap)
                    canvas.drawColor(Color.WHITE)
                    canvas.drawBitmap(bitmap, 0f, 0f, Paint())
                    newBitmap?.let {
                        bitmaps.add(it)
                    }
                    page.close()
                }
                onBitmapCreated(bitmaps)
                pdfRenderer.close()
            } catch (ex: Exception) {
                Toast.makeText(
                    this@convertPdfToBitmap,
                    "${ex.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun openPrinterPort() {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

            if (!mBluetoothAdapter!!.isEnabled()) {
                val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    startActivityForResult(enableBluetooth, 0)
                    return
                }
            }
            val pairedDevices: Set<BluetoothDevice> = mBluetoothAdapter!!.getBondedDevices()
            if (pairedDevices.size > 0) {
                for (device in pairedDevices) {
                   mmDevice = device
                }

            }
            AutoReplyPrint.INSTANCE.CP_Port_AddOnPortOpenedEvent(this@MainActivity, Pointer.NULL)
            h = AutoReplyPrint.INSTANCE.CP_Port_OpenBtSpp("${mmDevice?.address}",0)
            //h = AutoReplyPrint.INSTANCE.CP_Port_OpenBtSpp("86:67:7A:11:E4:93",0)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }//openPrinterPort()

    override fun CP_OnPortOpenedEvent(p0: Pointer?, p1: String?, p2: Pointer?) {
        lifecycleScope.launchWhenCreated {
            Toast.makeText(
                this@MainActivity,
                "Status Pointer1 ${p0.toString()} Name: $p1 Pointer2 ${p2.toString()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }





}
