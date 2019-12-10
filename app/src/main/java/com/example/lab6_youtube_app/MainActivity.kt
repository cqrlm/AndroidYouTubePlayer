package com.example.lab6_youtube_app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lab6_youtube_app.fragment.*
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var drawer: Drawer
    private val homeItem = PrimaryDrawerItem().withName(R.string.home_title)
    private val firstPlaylistItem = PrimaryDrawerItem().withName(R.string.playlist_1_title)
    private val secondPlaylistItem = PrimaryDrawerItem().withName(R.string.playlist_2_title)
    private val thirdPlaylistItem = PrimaryDrawerItem().withName(R.string.playlist_3_title)
    private val infoItem = SecondaryDrawerItem().withName(R.string.info_title)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accountHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .build()

        setSupportActionBar(toolbar)

        drawer = DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withAccountHeader(accountHeader)
            .withActionBarDrawerToggle(true)
            .withActionBarDrawerToggleAnimated(true)
            .addDrawerItems(
                homeItem.withIcon(GoogleMaterial.Icon.gmd_home),
                firstPlaylistItem.withIcon(GoogleMaterial.Icon.gmd_video_library),
                secondPlaylistItem.withIcon(GoogleMaterial.Icon.gmd_video_library),
                thirdPlaylistItem.withIcon(GoogleMaterial.Icon.gmd_video_library),
                DividerDrawerItem(),
                infoItem.withIcon(GoogleMaterial.Icon.gmd_info)
            )
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    selectItem(drawerItem)
                    return true
                }
            })
            .build()
        selectItem()
    }

    private fun selectItem(drawerItem: IDrawerItem<*>) {
        val fragment: Fragment = when (drawerItem) {
            homeItem -> HomeFragment()
            firstPlaylistItem -> FirstPlaylistFragment()
            secondPlaylistItem -> SecondPlaylistFragment()
            thirdPlaylistItem -> ThirdPlaylistFragment()
            infoItem -> InfoFragment()
            else -> HomeFragment()
        }
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.activity_main_frame, fragment)
            .commit()
        drawer.closeDrawer()
    }

    private fun selectItem() {
        when (supportFragmentManager.findFragmentById(R.id.activity_main_frame)) {
            is HomeFragment -> drawer.setSelection(homeItem)
            is FirstPlaylistFragment -> drawer.setSelection(firstPlaylistItem)
            is SecondPlaylistFragment -> drawer.setSelection(secondPlaylistItem)
            is ThirdPlaylistFragment -> drawer.setSelection(thirdPlaylistItem)
            is InfoFragment -> drawer.setSelection(infoItem)
            else -> drawer.setSelection(homeItem)
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen) {
            drawer.closeDrawer()
        } else {
            if (supportFragmentManager.backStackEntryCount > 1) {
                super.onBackPressed()
                supportFragmentManager.popBackStack()
                selectItem()
            } else {
                this.finish()
            }
        }
    }
}
