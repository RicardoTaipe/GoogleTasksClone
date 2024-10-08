package com.example.googletasksclone

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.googletasksclone.addtask.AddTasksFragment
import com.example.googletasksclone.databinding.ActivityMainBinding
import com.example.googletasksclone.home.TasksCollectionAdapter
import com.example.googletasksclone.moreoptions.MoreOptionsEvent
import com.example.googletasksclone.moreoptions.MoreOptionsFragment
import com.example.googletasksclone.newlist.NewListFragment
import com.example.googletasksclone.sort.SortEvent
import com.example.googletasksclone.sort.SortFragment
import com.example.googletasksclone.switchlist.SwitchEvent
import com.example.googletasksclone.switchlist.SwitchListFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksCollectionAdapter: TasksCollectionAdapter
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
        renderTitlesInTabLayout()
        handleCustomTabAction()
        handleBottomBarActions()
        navigateToAddTasksFragment()
    }

    private fun renderTitlesInTabLayout() {
        TabLayoutMediator(
            binding.contentMain.tabLayout, binding.contentMain.tasksLists
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_24)
                }

                else -> {
                    tab.text = "LIST ${(position)}"
                }
            }
        }.attach()
    }

    private fun handleCustomTabAction() {
        binding.contentMain.tabLayout.apply {
            addCustomTab(getString(R.string.new_list))
            setOnCustomTabSelectedListener {
                navigateToNewListFragment()
//                Snackbar.make(binding.root, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).setAnchorView(binding.addTasksButton).show()
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
        SortFragment().apply {
            onListItemSelected = {
                when (it) {
                    SortEvent.Date -> {}
                    SortEvent.MyOrder -> {}
                    SortEvent.Starred -> {}
                }

            }
            show(supportFragmentManager, SortFragment.TAG)
        }
    }

    private fun navigateToMoreOptionsDialog() {
        MoreOptionsFragment().apply {
            onListItemSelected = {
                when (it) {
                    MoreOptionsEvent.DeleteAllCompletedTasks -> {}
                    MoreOptionsEvent.DeleteList -> {}
                    MoreOptionsEvent.RenameList -> {}
                }

            }
            show(supportFragmentManager, MoreOptionsFragment.TAG)
        }
    }

    private fun navigateToSwitchListsFragment() {
        SwitchListFragment().apply {
            onListItemSelected = {
                when (it) {
                    is SwitchEvent.ItemSelected -> {
                        binding.contentMain.tasksLists.setCurrentItem(it.id.toInt(), false)
                        this.dismiss()
                    }
                }
            }
            show(supportFragmentManager, SwitchListFragment.TAG)
        }
    }

    private fun navigateToAddTasksFragment() {
        binding.addTasksButton.setOnClickListener {
            AddTasksFragment().show(supportFragmentManager, AddTasksFragment.TAG)
        }
    }

    private fun navigateToNewListFragment() {
        NewListFragment().show(supportFragmentManager, NewListFragment.TAG)
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