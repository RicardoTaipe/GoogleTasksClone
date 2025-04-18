package com.example.googletasksclone

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.example.googletasksclone.addeditcategory.AddEditCategoryFragment
import com.example.googletasksclone.addtask.AddTasksFragment
import com.example.googletasksclone.data.Category
import com.example.googletasksclone.databinding.ActivityMainBinding
import com.example.googletasksclone.home.HomeViewModel
import com.example.googletasksclone.home.TasksCollectionAdapter
import com.example.googletasksclone.moreoptions.MoreOptionsEvent
import com.example.googletasksclone.moreoptions.MoreOptionsFragment
import com.example.googletasksclone.moreoptions.MoreOptionsFragment.Companion.FAVORITE_KEY
import com.example.googletasksclone.sort.SortFragment
import com.example.googletasksclone.switchcategory.SwitchCategoryFragment
import com.example.googletasksclone.switchcategory.SwitchEvent
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksCollectionAdapter: TasksCollectionAdapter

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//
//        }
        tasksCollectionAdapter = TasksCollectionAdapter(this).also {
            binding.contentMain.tasksLists.adapter = it
        }
        handleCustomTabAction()
        handleBottomBarActions()
        navigateToAddTasksFragment()
        binding.contentMain.tasksLists.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                PreferencesMock.selectedList = viewModel.categories.value?.get(position)
            }
        })

        viewModel.prepopulateDatabase()

        viewModel.categories.observe(this) {
            tasksCollectionAdapter.categories = it
            renderTitlesInTabLayout(it)
        }
    }

    private fun renderTitlesInTabLayout(categories: List<Category>) {
        TabLayoutMediator(
            binding.contentMain.tabLayout,
            binding.contentMain.tasksLists
        ) { tab, position ->
            val category = categories.getOrNull(position)
            when (position) {
                0 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_24)
                }

                else -> {
                    tab.text = category?.name
                }
            }
        }.attach()
        binding.contentMain.tabLayout.addCustomTab(getString(R.string.new_list))
    }

    private fun handleCustomTabAction() {
        binding.contentMain.tabLayout.apply {
            setOnCustomTabSelectedListener {
                navigateToNewCategoryFragment()
            }
        }
    }

    private fun handleBottomBarActions() {
        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.switch_lists -> {
                    navigateToSwitchListsFragment()
                    true
                }

                R.id.sort_tasks -> {
                    navigateToSortFragment()
                    true
                }

                R.id.more_options -> {
                    navigateToMoreOptionsDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun navigateToSortFragment() {
        // update list with order change from flow
        SortFragment().show(supportFragmentManager, SortFragment.TAG)
    }

    private fun navigateToMoreOptionsDialog() {
        MoreOptionsFragment().apply {
            arguments = Bundle().apply {
                putString(MoreOptionsFragment.CATEGORY_ID, PreferencesMock.selectedList?.id)
                putBoolean(FAVORITE_KEY, PreferencesMock.selectedList?.isFavorite ?: false)
            }
            onListItemSelected = {
                when (it) {
                    MoreOptionsEvent.RenameList -> {
                        navigateToNewCategoryFragment(PreferencesMock.selectedList?.id.orEmpty())
                    }

                    MoreOptionsEvent.DeleteList -> {
                        Snackbar.make(binding.root, "List deleted", Snackbar.LENGTH_SHORT)
                            .setAnchorView(binding.bottomAppBar).show()
                    }


                    MoreOptionsEvent.DeleteAllCompletedTasks -> {

                    }
                }
                this.dismiss()
            }
            show(supportFragmentManager, MoreOptionsFragment.TAG)
        }
    }

    private fun navigateToSwitchListsFragment() {
        SwitchCategoryFragment().apply {
            onListItemSelected = {
                when (it) {
                    is SwitchEvent.ItemSelected -> {
                        binding.contentMain.tasksLists.setCurrentItem(it.position, false)
                        this.dismiss()
                    }
                }
            }
            show(supportFragmentManager, SwitchCategoryFragment.TAG)
        }
    }

    private fun navigateToAddTasksFragment() {
        binding.addTasksButton.setOnClickListener {
            AddTasksFragment().show(supportFragmentManager, AddTasksFragment.TAG)
        }
    }

    private fun navigateToNewCategoryFragment(categoryId: String = "") {
        AddEditCategoryFragment.newInstance(categoryId)
            .show(supportFragmentManager, AddEditCategoryFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}