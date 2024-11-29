

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import qrscanner.QrScanner

@Composable
fun QrScannerView(onScanned: (String) -> Unit) {


    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        QrScanner(
            modifier = Modifier,
            flashlightOn = false,

            onCompletion = {
                onScanned(it)
            },
            imagePickerHandler = {
                println("")
            },
            onFailure = {
                println("")
            },
            openImagePicker = false
        )

        // QR Scanner Mask
        QRScannerMask(
            clearAreaSize = 250.dp,
            borderColor = Color.White
        )
    }

}


@Composable
fun QRScannerMask(
    maskColor: Color = Color.Black.copy(alpha = 0.5f),
    clearAreaSize: Dp = 200.dp,
    borderColor: Color = Color.White
) {
    Box(modifier = Modifier.fillMaxSize()) {


        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .background(maskColor)
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(clearAreaSize)
            ){

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                        .background(maskColor)
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(clearAreaSize)
                        .background(Color.Transparent)
                )


                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                        .background(maskColor)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .background(maskColor)
            )
        }
        // Mask layer

    }
}