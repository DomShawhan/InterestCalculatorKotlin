package com.example.interestcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interestcalculator.ui.theme.InterestCalculatorTheme
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterestCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InterestCalculatorLayout()
                }
            }
        }
    }
}

@Composable
fun InterestCalculatorLayout(modifier: Modifier = Modifier) {
    var amountInput by remember { mutableStateOf("0") }
    var interestRate by remember { mutableStateOf("0") }

    val amt = amountInput.toDoubleOrNull()
    val interest = (interestRate.toDoubleOrNull() ?: 0.0) / 100.0
    val df = DecimalFormat("$#,##0.00")
    df.setRoundingMode(RoundingMode.HALF_UP)
    val pf = DecimalFormat("##.###%")
    pf.setRoundingMode(RoundingMode.HALF_UP)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.app_name),
            modifier = modifier
        )
        NumberField(
            label = "Loan Amount",
            value = amountInput,
            onValueChange = { amountInput = it},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        NumberField(
            label = "Interest Rate",
            value = interestRate,
            onValueChange = { interestRate = it},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = modifier.padding(top = 5.dp)
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(
                R.string.loan_amount_output,
                df.format((amt ?: 0.0))
            ),
            modifier = modifier
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(
                R.string.interest_rate_output,
                pf.format(interest )
            ),
            modifier = modifier
        )
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(
                R.string.interest_amount_output,
                df.format(calcluateInterestAmt(amt ?: 0.0, interest))
            ),
            modifier = modifier
        )
    }
}

@Composable
fun NumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        label = { Text(label)},
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        singleLine = true
    )
}

fun calcluateInterestAmt(
    loanAmt: Double,
    interestRate: Double
) : Double {
    return (loanAmt * interestRate)
}


@Preview(showBackground = true)
@Composable
fun InterestCalculatorPreview() {
    InterestCalculatorTheme {
        InterestCalculatorLayout()
    }
}