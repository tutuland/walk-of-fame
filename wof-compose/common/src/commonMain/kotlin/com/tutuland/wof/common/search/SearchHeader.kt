package com.tutuland.wof.common.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tutuland.wof.common.theme.BrandOnPrimary
import com.tutuland.wof.common.theme.CircleCornerShape
import com.tutuland.wof.common.theme.SearchBackgroundColor
import com.tutuland.wof.common.theme.SearchIconColor
import com.tutuland.wof.core.search.viewmodel.SearchViewModel

@Composable
fun SearchHeader(viewModel: SearchViewModel, contentPadding: Dp) {
    var showHeader by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }
    var leadingIcon: @Composable (() -> Unit)? by remember { mutableStateOf({ SearchFieldIcon() }) }
    var trailingIcon: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val localFocusManager = LocalFocusManager.current

    fun submit(search: String) {
        viewModel.searchFor(search)
        localFocusManager.clearFocus()
    }

    fun clearSearch() {
        text = ""
    }

    fun backFromField() {
        clearSearch()
        localFocusManager.clearFocus()
    }

    fun focusChanged(isFocused: Boolean) {
        showHeader = isFocused.not()
        if (isFocused) {
            leadingIcon = { SearchFieldBack { backFromField() } }
            trailingIcon = { SearchFieldClean { clearSearch() } }
        } else {
            leadingIcon = { SearchFieldIcon() }
            trailingIcon = null
        }
    }

    Column {
        SearchIntro(showHeader, contentPadding)
        SearchField(
            text = text,
            onTextChange = { text = it },
            onFocusChange = { focusChanged(it) },
            onImeAction = { submit(it) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            contentPadding = contentPadding,
        )
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun SearchField(
    text: String,
    onTextChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onImeAction: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    contentPadding: Dp,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    fun onDone() {
        onImeAction(text)
        keyboardController?.hide()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = contentPadding)
            .padding(top = 16.dp, bottom = 16.dp)
            .defaultMinSize(minHeight = 60.dp)
            .background(color = SearchBackgroundColor, shape = CircleCornerShape),
    ) {
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            TextField(
                value = text,
                placeholder = placeholder,
                onValueChange = onTextChange,
                colors = textFieldColors(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onDone() }),
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        onFocusChange(it.isFocused)
                    }
                    .onPreviewKeyEvent {
                        if (it.key == Key.Enter) {
                            onDone()
                            true
                        } else false
                    },
            )
        }
    }
}

val customTextSelectionColors = TextSelectionColors(
    handleColor = BrandOnPrimary,
    backgroundColor = BrandOnPrimary.copy(alpha = 0.4f)
)

val placeholder = @Composable { Text("Search by name", style = MaterialTheme.typography.body1) }

@Composable
fun textFieldColors(): TextFieldColors = TextFieldDefaults.textFieldColors(
    backgroundColor = Color.Transparent,
    cursorColor = BrandOnPrimary,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    focusedLabelColor = BrandOnPrimary,
    unfocusedLabelColor = BrandOnPrimary,
    leadingIconColor = SearchIconColor,
    trailingIconColor = SearchIconColor,
    placeholderColor = BrandOnPrimary,
)


@Composable
fun SearchFieldBack(onBackClicked: () -> Unit) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        tint = SearchIconColor,
        contentDescription = null,
        modifier = Modifier.clickable(onClickLabel = "Close search field") { onBackClicked() },
    )
}

@Composable
fun SearchFieldClean(onCleanClicked: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Close,
        tint = SearchIconColor,
        contentDescription = null,
        modifier = Modifier.clickable(onClickLabel = "Clear search field text") { onCleanClicked() },
    )
}

@Composable
fun SearchFieldIcon() {
    Icon(
        imageVector = Icons.Default.Search,
        tint = SearchIconColor,
        contentDescription = null,
    )
}

@Composable
fun SearchIntro(isVisible: Boolean, contentPadding: Dp) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = contentPadding)
        ) {
            Text(
                text = "Walk of fame",
                style = MaterialTheme.typography.h2,
            )
            Text(
                text = "Let's find movie stars and amazing creativity people!",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}
