package com.test.mindteck.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.test.mindteck.model.ItemModel
import com.test.mindteck.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScaffoldApp()
        }
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScaffoldApp() {
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Mindteck")
                    }
                )
            },
            bottomBar = {

            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showBottomSheet = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { innerPadding ->

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    // Sheet content
                    Text("CurrentPage Item size : " +viewModel.recyclerViewItems.value.size.toString() + " Items")
                    Text(viewModel.itemNameArray.value.joinToString(", "))

                        val topThree = viewModel.itemNameArray.value
                            .flatMap { it.toList() }
                            .groupingBy { it }
                            .eachCount()
                            .entries
                            .sortedByDescending { it.value }
                            .take(3)
                            .joinToString(", ") { "${it.key} = ${it.value}" }

                        Text(topThree)


                }
            }
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                HorizontalPagerSample(viewModel.images)
                SingleSelectionList(options = viewModel.recyclerViewItems.value?: emptyList())

            }
        }
    }


    @Composable
    private fun HorizontalPagerSample(images : MutableState<List<Int>>) {


        val pageCount = images.value.size

        val pagerState = rememberPagerState(pageCount / 2) { pageCount }

        ConstraintLayout{
            val (pager, indicator) = createRefs()


            HorizontalPager(
                modifier = Modifier.fillMaxWidth().constrainAs(
                    pager
                ){
                },
                state = pagerState,
            ) { i ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .border(1.dp, Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(painterResource(images.value[i]),"pager",    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                }
            }

            Row(
                Modifier
                    .height(25.dp)
                    .fillMaxWidth()
                    .constrainAs(
                        indicator
                        ){
                    bottom.linkTo(pager.bottom, margin = 8.dp)
                },
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color, CircleShape)
                            .size(10.dp)
                    )
                }
            }


        }
    }

    @Composable
    fun SingleSelectionList(
        options: List<ItemModel>,
    ) {

        var searchedOption by rememberSaveable { mutableStateOf("") }
        val filteredItems = remember {
            mutableStateOf(options)
        }

        OutlinedTextField(
            searchedOption,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = { selectedSport ->
                searchedOption = selectedSport

                filteredItems.value =
                    if (searchedOption.isEmpty()) {
                        options
                    } else
                        filteredItems.value.filter {
                            it.toString().toLowerCase().contains(
                                searchedOption.toLowerCase(),
                            )
                        }

            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            placeholder = {
                Text(
                    text = "Search",

                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp,
                    ),

                    )
            },
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {

                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                // keyboardController?.show()
                            }
                        }
                    }
                },
            colors = TextFieldDefaults.colors(
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.padding(bottom = 100.dp)) {
            items(filteredItems.value.filter { it.name!=null }) { option ->  ImageWithName(option.image,option.name)}

        }

    }

    @Composable
    fun ImageWithName(imageResource: Int, title: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}