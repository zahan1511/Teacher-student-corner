package com.example.zahan.tsc;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem("Add Notice", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("Add CT", R.drawable.ic_action_help));
		dataList.add(new DrawerItem("Cancel Class", R.drawable.ic_action_about));
		dataList.add(new DrawerItem("Set Extra Class", R.drawable.ic_action_cloud));
		dataList.add(new DrawerItem("Change Class", R.drawable.ic_action_import_export));
		dataList.add(new DrawerItem("CT dates", R.drawable.ic_action_search));
		dataList.add(new DrawerItem("Cancelled Classes", R.drawable.ic_action_labels));
		dataList.add(new DrawerItem("Extra Classes", R.drawable.ic_action_good));
        dataList.add(new DrawerItem("Changed Class", R.drawable.ic_action_settings));
        dataList.add(new DrawerItem("Notices", R.drawable.ic_action_gamepad));
		dataList.add(new DrawerItem("Courses", R.drawable.ic_action_group));



		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			SelectItem(0);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void SelectItem(int possition) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {
		case 0:
			fragment = new FragmentOne();
			args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 1:
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 2:
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 3:
			fragment = new FragmentFour();
			args.putString(FragmentFour.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentFour.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 4:
			fragment = new FragmentFive();
			args.putString(FragmentFive.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentFive.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 5:
			fragment = new FragmentSix();
			args.putString(FragmentSix.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentSix.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 6:
			fragment = new FragmentCancel();
			args.putString(FragmentCancel.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentCancel.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 7:
			fragment = new FragmentSeven();
			args.putString(FragmentSeven.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentSeven.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 8:
			fragment = new FragmentEight();
			args.putString(FragmentEight.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentEight.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 9:
			fragment = new FragmentNine();
			args.putString(FragmentNine.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentNine.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
            case 10:
                fragment = new FragmentTen();
                args.putString(FragmentTen.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentTen.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;

		default:
			break;
		}

		fragment.setArguments(args);
		FragmentManager frgManager = getSupportFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);

		}
	}

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Do you really want to quit?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();

                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }
}
