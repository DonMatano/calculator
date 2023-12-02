package com.matano.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
fun ButtonComp( modifier: Modifier = Modifier, onButtonClickHandler: () -> Unit , shape: Shape = CircleShape, textColor: Color = GoldishColor, buttonColor: Color= Color.Black,  text: String) {

    Button(colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        modifier = modifier,
        shape = shape,
        onClick = { onButtonClickHandler() }) {
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

enum class Token(val symbol: String) {
    CLEAR("AC"),
    BRACKET("[]"),
    OPEN_BRACKET("("),
    CLOSE_BRACKET(")"),
    DELETE("C"),
    DIVIDE("/"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    MULTIPLY("x"),
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

fun handleInput(inputString: String) {


}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ButtonsSection(modifier: Modifier = Modifier, handler: (Token) -> Unit ) {
    val buttonsTokens = listOf<Token>(
        Token.CLEAR,
        Token.BRACKET,
        Token.DELETE,
        Token.DIVIDE,
        Token.SEVEN,
        Token.EIGHT,
        Token.NINE,
        Token.MULTIPLY,
        Token.FOUR,
        Token.FIVE,
        Token.SIX,
        Token.SUBTRACT,
        Token.ONE,
        Token.TWO,
        Token.THREE,
        Token.ADD,
        Token.ZERO,
        Token.DECIMAL_POINT,
        Token.EQUALS,
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
            for (buttonsToken in buttonsTokens) {
                val isEqualButton = buttonsToken.symbol == Token.EQUALS.symbol
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
                val onButtonClickHandler: () -> Unit = {
                   handler(buttonsToken)
                }
                ButtonComp(modifier = buttonModifier, onButtonClickHandler,shape, textColor, buttonColor, text = buttonsToken.symbol)
            }

        }
//        ButtonComp("5")
    }
}


@Composable
fun CalculationScreenOperationText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.End,
        fontSize = 28.sp,
        color = GoldishColor,
        letterSpacing = 2.4.sp,
        fontFamily = DigitalNumberFamily,
        lineHeight = 33.sp,
        text= text
    )
}

@Composable
fun CalculationResultText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.End,
        fontSize = 28.sp,
        letterSpacing = 2.4.sp,
        fontFamily = DigitalNumberFamily,
        color = LightYellowColor,
        lineHeight = 33.sp,
        text = text,
    )
}

@Composable
fun CalculationScreen(modifier: Modifier = Modifier, calculationText: String, calculationResultText: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
            .background(color = LightGreyColor)
            .padding(11.dp, 40.dp)
            .fillMaxWidth()
    ) {
        CalculationScreenOperationText(modifier= Modifier.fillMaxWidth(), text = calculationText)
        CalculationResultText(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 34.dp), text = calculationResultText)
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var calculationText by remember {
        mutableStateOf("")
    }
    val calculationResultText by remember {
        mutableStateOf("")
    }
    var listOfOperationsToRun by remember {
        mutableStateOf(mutableListOf(""))
    }

    var currentNumber by remember {
        mutableStateOf(0)
    }


    var isCurrentNumberADecimal by remember {
        mutableStateOf(false)
    }

    val numberListString = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
    val operationListString = listOf("(", ")","+", "/", "*", "-")
    fun handleNumber (numberInString: String) {
        var lastDigit = listOfOperationsToRun.last()
        val isANumberString = numberInString.contains(numberInString)
        val isLastDigitAnOperation = operationListString.contains(lastDigit)
        if (isLastDigitAnOperation) {
            listOfOperationsToRun.add(numberInString)
        } else {
            // Is a number append the value
            listOfOperationsToRun.removeLast()
            listOfOperationsToRun.add(lastDigit.plus(numberInString))
        }
    }
    fun handleAddingNumericDigit(digit: Token) {
        calculationText = calculationText.plus(digit.symbol)

    }

    val handleAction: (symbol: Token) -> Unit =  {
        calculationText = when(it) {
            Token.ONE -> calculationText.plus("1")
            Token.TWO -> calculationText.plus("2")
            Token.THREE ->calculationText.plus("3")
            Token.FOUR -> calculationText.plus("4")
            Token.FIVE -> calculationText.plus("5")
            Token.SIX -> calculationText.plus("6")
            Token.SEVEN ->  calculationText.plus("7")
            Token.EIGHT ->  calculationText.plus("8")
            Token.NINE ->  calculationText.plus("9")
            Token.ZERO ->  calculationText.plus("0")
            Token.CLEAR -> ""
            Token.BRACKET, Token.OPEN_BRACKET, Token.CLOSE_BRACKET -> calculationText.plus("(")
            Token.DELETE -> ""
            Token.DIVIDE ->  calculationText.plus("/")
            Token.MULTIPLY -> calculationText.plus("*")
            Token.SUBTRACT -> calculationText.plus("-")
            Token.ADD ->  calculationText.plus("+")
            Token.DECIMAL_POINT ->  calculationText.plus(".")
            Token.EQUALS -> ""
        }
    }
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .background(DarkGreyColor)
    ) {

        CalculationScreen(calculationText = calculationText, calculationResultText = calculationResultText )
        ButtonsSection(handler = handleAction)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        CalculatorScreen()
    }
}