package com.matano.calculator

import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
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
    Font(R.font.ds_digi_italic, FontWeight.Normal, FontStyle.Italic)
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
fun ButtonComp( modifier: Modifier = Modifier, shape: Shape = CircleShape, textColor: Color = GoldishColor, buttonColor: Color= Color.Black,  text: String) {

    Button(colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        modifier = modifier,
        shape = shape,
        onClick = { /*TODO*/ }) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            fontFamily = DigitalNumberFamily,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Visible,
            letterSpacing = (-0.14).sp,
            softWrap = false,
            color = textColor
        )
    }
}

enum class Arithmetic(val symbol: String) {
    CLEAR("AC"),
    BRACKET("[]"),
    DELETE("C"),
    DIVIDE("/"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    MULTIPLY("X"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SUBTRACT("-"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    ADD("+"),
    ZERO("0"),
    DECIMAL_POINT("."),
    EQUALS("="),
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ButtonsSection(modifier: Modifier = Modifier) {
    val buttonsArithmetics = listOf<Arithmetic>(
        Arithmetic.CLEAR,
        Arithmetic.BRACKET,
        Arithmetic.DELETE,
        Arithmetic.DIVIDE,
        Arithmetic.SEVEN,
        Arithmetic.EIGHT,
        Arithmetic.NINE,
        Arithmetic.MULTIPLY,
        Arithmetic.FOUR,
        Arithmetic.FIVE,
        Arithmetic.SIX,
        Arithmetic.SUBTRACT,
        Arithmetic.ONE,
        Arithmetic.TWO,
        Arithmetic.THREE,
        Arithmetic.ADD,
        Arithmetic.ZERO,
        Arithmetic.DECIMAL_POINT,
        Arithmetic.EQUALS,
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(11.dp, 40.dp)
    ) {
        FlowRow(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            maxItemsInEachRow = 4
        ) {
            for (buttonsArithmetic in buttonsArithmetics) {
                val isEqualButton = buttonsArithmetic.symbol == Arithmetic.EQUALS.symbol
                val buttonModifier = Modifier
                    .size(90.dp)
                    .weight(1f)
                var buttonColor = Color.Black
                var textColor = GoldishColor
                var shape = CircleShape
                if (isEqualButton) {
                    buttonColor = GoldishColor
                    textColor = Color.Black
                    buttonModifier.weight(2f)
                    shape = RoundedCornerShape(20.dp)

                }
                ButtonComp(modifier = buttonModifier,shape, textColor, buttonColor, text = buttonsArithmetic.symbol)
            }

        }
//        ButtonComp("5")
    }
}


@Composable
fun CalculationScreenOperationText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
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
fun CalculationResultText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.End,
        fontSize = 28.sp,
        letterSpacing = 2.4.sp,
        fontFamily = DigitalNumberFamily,
        color = LightYellowColor,
        lineHeight = 33.sp,
        text = "70,000",
    )
}

@Composable
fun CalculationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
            .background(color = LightGreyColor)
            .padding(11.dp, 40.dp)
            .fillMaxWidth()
    ) {
        CalculationScreenOperationText(modifier= Modifier.fillMaxWidth())
        CalculationResultText(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 34.dp))
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
        modifier
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