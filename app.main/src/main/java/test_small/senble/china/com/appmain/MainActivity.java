package test_small.senble.china.com.appmain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.wequick.small.Small;

public class MainActivity extends AppCompatActivity {

    private TextView switchTv;
    private FrameLayout containerFl;
    /**
     * 0:br
     * 1:p2p
     */
    private int curFragmentTag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchTv = (TextView) findViewById(R.id.switchTv);
        switchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
            }
        });
        containerFl = (FrameLayout) findViewById(R.id.containerFl);

        changeFragment();
    }

    private void changeFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment curFragment = null;
        if(curFragmentTag==0){
            curFragment = Small.createObject("fragment-v4","bank_repository",MainActivity.this);
            curFragmentTag = 1;
        }else{
            curFragment = Small.createObject("fragment-v4","p2p",MainActivity.this);
            curFragmentTag = 0;
        }
        if(curFragment==null){
            curFragment = PlaceholderFragment.newInstance(curFragmentTag);
        }
        ft.replace(R.id.containerFl,curFragment);

        ft.commitAllowingStateLoss();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
