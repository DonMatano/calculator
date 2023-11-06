package com.matano.calculator

import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matano.calculator.ui.theme.CalculatorTheme


val DigitalNumberFamily = FontFamily(
    Font(R.font.ds_digi_regular, FontWeight.Normal),
    Font(R.font.ds_digi_bold, FontWeight.Bold),
    Font(R.font.ds_digi_italic, FontWeight.Normal ,FontStyle.Italic)
)

val LightGreyColor = Color(0xFF454444)
val DarkGreyColor = Color(0xFF383838)
val GoldishColor = Color(0XFFA5914B)
val LightYellowColor = Color(0XFFB8C248)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Surface(modifier = Modifier.fillMaxSize(), color = DarkGreyColor) {
                    CalculatorScreen()
                }
            }
        }
    }
}
@Composable
fun ButtonComp(text: String, isEqualButton: Boolean = false) {

    Button( colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        modifier = Modifier.size(70.dp),
        shape = CircleShape,
        onClick = { /*TODO*/ }) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            fontFamily = DigitalNumberFamily,
            fontSize = 28.sp,
            softWrap= false,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Visible,
            letterSpacing = (-0.14).sp,
            color = GoldishColor
        )
    }
}

@Composable
fun ButtonsSection() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(11.dp, 40.dp)
    ) {
//        ButtonComp("5")
        ButtonComp("AC")
    }
}


@Composable
fun CalculationScreenOperationText() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
        fontSize = 28.sp,
        color = GoldishColor,
        letterSpacing = 2.4.sp,
        fontFamily = DigitalNumberFamily,
        lineHeight = 33.sp,
        text = "50,000 + 20,000"
    )
}
@Composable
fun CalculationResultText() {
    Text(
        modifier= Modifier
            .fillMaxWidth()
            .padding(top = 34.dp),
        textAlign = TextAlign.End,
        fontSize = 28.sp,
        letterSpacing = 2.4.sp,
        fontFamily = DigitalNumberFamily,
        color= LightYellowColor,
        lineHeight = 33.sp,
        text = "70,000",
    )
}

@Composable
fun CalculationScreen() {
    Column(modifier = Modifier
        .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
        .background(color = LightGreyColor)
        .padding(11.dp, 40.dp)
    ) {
        CalculationScreenOperationText()
        CalculationResultText()
    }
}

@Composable
fun CalculatorScreen() {
    Column(modifier =
    Modifier
        .fillMaxSize()
        .background(DarkGreyColor)
    ) {

        CalculationScreen()
        ButtonsSection()
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorScreen()
    }
}