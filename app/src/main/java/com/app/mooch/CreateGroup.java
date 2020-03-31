package com.app.mooch;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.app.mooch.CustomViews.CustomViewPager;
import com.app.mooch.CustomViews.ZoomOutPageTransformer;
import com.app.mooch.adapters.SelectedUserAdapter;
import com.app.mooch.adapters.ViewAddedFriendsAdapter;
import com.app.mooch.fragments.AddFriends;
import com.app.mooch.fragments.GroupSettings;
import com.app.mooch.fragments.NameGroup;
import com.app.mooch.modals.Group;
import com.app.mooch.modals.User;

import java.util.ArrayList;
import java.util.List;

public class CreateGroup extends AppCompatActivity {

    private Button addUsers;

    private RecyclerView addedFriends;
    private RecyclerView.LayoutManager layoutManager;
    private ViewAddedFriendsAdapter viewAddedFriendsAdapter;


    private CustomViewPager customViewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    private AddFriends addFriendsScreen;
    private NameGroup nameGroup;
    private GroupSettings groupSettings;

    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        group = new Group();

        addFriendsScreen = new AddFriends();
        nameGroup = new NameGroup();
        groupSettings = new GroupSettings();

        List<Fragment> viewPagerScreens = new ArrayList<>();

        viewPagerScreens.add( addFriendsScreen );
        viewPagerScreens.add(nameGroup);
        viewPagerScreens.add( groupSettings );

        customViewPager = (CustomViewPager) findViewById(R.id.createGroupFlow);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(customViewPager);
        pagerAdapter = new ScreenSliderAdapter( getSupportFragmentManager(), viewPagerScreens );
        customViewPager.setAdapter(pagerAdapter);
        customViewPager.setPageTransformer(true , new ZoomOutPageTransformer());


        //pagerAdapter.setPageTransformer(true, new ZoomOutPageTransformer());

        /*
        List<User> users = new ArrayList<>();
        users.add( new User( "1", "username" ) );

        addUsers = (Button) findViewById(R.id.addFriendsButton);

        addedFriends = (RecyclerView) findViewById(R.id.addFriends);
        layoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false );
        viewAddedFriendsAdapter = new ViewAddedFriendsAdapter( users );
        addedFriends.setLayoutManager( layoutManager );
        addedFriends.setAdapter( viewAddedFriendsAdapter );
        //addUsers = (Button) findViewById(R.id.addUsers);

        addUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( CreateGroup.this, FindUsers.class) );
            }
        });

    */

    }



    public void moveView( int nextView ){
        this.customViewPager.setCurrentItem( nextView );
    }

    public Group getGroup(){
        return this.group;
    }

    public void updateView(){
        this.addFriendsScreen.getSelectedUserAdapter().update();
        this.addFriendsScreen.getSelectUserAdapter().update();
        this.nameGroup.getViewAddedFriendAdapter().update();
        //this.groupSettings.getViewAddedFriendsAdapter().update();
    }

    private class ScreenSliderAdapter extends FragmentPagerAdapter {

        private List<Fragment> screens;

        public ScreenSliderAdapter(FragmentManager fm, List<Fragment> screens) {
            super(fm);
            this.screens = screens;
        }

        @Override
        public Fragment getItem(int i) {
            return this.screens.get(i);
        }

        @Override
        public int getCount() {
            return this.screens.size();
        }
    }

}
