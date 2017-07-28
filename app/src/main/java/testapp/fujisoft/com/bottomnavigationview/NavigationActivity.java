package testapp.fujisoft.com.bottomnavigationview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;
    private SearchFragment searchFragment;
    private Fragment[] fragments;
    private int lastShowFragment = 0;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);//解决item个数大于三的时候，文本消失的问题。
        initFragments();
        navigation.setOnNavigationItemSelectedListener(this);
    }

    /**
     * 初始化所有的fragment
     */
    private void initFragments() {
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        searchFragment = new SearchFragment();
        fragments = new Fragment[]{homeFragment, dashboardFragment, notificationsFragment,searchFragment};//将fragment添加到集合
        lastShowFragment = 0;//默认选中的条目是0，所以首先显示的应该是HomeFragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, homeFragment)
                .show(homeFragment)
                .commit();
    }

    /**
     * BottomNavigationView的选择监听
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (lastShowFragment != 0) {
                    switchFrament(lastShowFragment, 0);
                    lastShowFragment = 0;
                }
                return true;
            case R.id.navigation_dashboard:
                if (lastShowFragment != 1) {
                    switchFrament(lastShowFragment, 1);
                    lastShowFragment = 1;
                }
                return true;
            case R.id.navigation_notifications:
                if (lastShowFragment != 2) {
                    switchFrament(lastShowFragment, 2);
                    lastShowFragment = 2;
                }
                return true;
            case R.id.navigation_seach:
                if (lastShowFragment != 3) {
                    switchFrament(lastShowFragment, 3);
                    lastShowFragment = 3;
                }
                return true;
        }
        return false;
    }

    /**
     * 切换Fragment
     *
     * @param lastIndex 上个显示Fragment的索引
     * @param index     需要显示的Fragment的索引
     */
    public void switchFrament(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}
