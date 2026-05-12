package com.fourbeat.presentation.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.fourbeat.presentation.theme.Black
import com.fourbeat.presentation.theme.Gray200
import com.fourbeat.presentation.theme.Gray500
import com.fourbeat.presentation.theme.normal16
import com.fourbeat.presentation.theme.normal20

@Composable
fun FourBeatTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    placeholder: String,
    maxLength: Int,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val underlineColor = if (isFocused) Black else Gray200

    Column(modifier = modifier) {
        FourBeatLabel(text = label)
        FourBeatSpacer(size = 12)
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            value = value,
            onValueChange = { if (it.length <= maxLength) onValueChange(it) },
            textStyle = normal20,
            interactionSource = interactionSource,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = normal20,
                                color = Gray200,
                            )
                        }
                        innerTextField()
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(normal16.copy(color = Black).toSpanStyle()) {
                                append(value.length.toString())
                            }
                            withStyle(normal16.copy(color = Gray500).toSpanStyle()) {
                                append("/$maxLength")
                            }
                        },
                    )
                }
            }
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp),
            color = underlineColor,
        )
    }
}
