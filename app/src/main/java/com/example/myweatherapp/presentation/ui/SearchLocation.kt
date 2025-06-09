package com.example.myweatherapp.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SearchLocation(
    viewModel: WeatherViewModel,
    innerPadding: PaddingValues,
    backgroundColor: Color,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    val searchText by viewModel.searchText.collectAsState()
    val cities by viewModel.cities.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = 8.dp,
                end = 8.dp
            )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back button",
                tint = Color.White,
                modifier = modifier.size(20.dp)
                    .clickable{
                        Log.d("SEARCH", "Back button click")
                        viewModel.setSearching(false)
                    }
            )

            Spacer(modifier = modifier.width(4.dp))

            OutlinedTextField(
                value = searchText,
                shape = RoundedCornerShape(10.dp),
                onValueChange = viewModel::onSearchTextChange,
                label = { Text("Search", color = Color.White) },
                modifier = modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    focusedLabelColor = textColor,
                    unfocusedLabelColor = Color.LightGray
                )
            )
        }

        Spacer(modifier = modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
                .clickable {
                    viewModel.selectCurrentLocation()
                }
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Current Location",
                tint = Color.White,
                modifier = modifier.size(20.dp)
            )

            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = "Current Location",
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
        if(isSearching) {
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(items = cities) { city ->
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(16.dp,)
                            .clickable {
                                viewModel.selectNewLocation(city)
                            }
                    ) {
                        Text(
                            text = city.name,
                            color = textColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = modifier.fillMaxWidth()
                        )
                        Spacer(modifier = modifier.height(2.dp))
                        Text(
                            text = city.country,
                            color = textColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Thin,
                            modifier = modifier.fillMaxWidth()

                        )
                    }

                }
            }
        }
    }
}