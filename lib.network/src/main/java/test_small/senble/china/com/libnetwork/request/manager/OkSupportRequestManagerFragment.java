package test_small.senble.china.com.libnetwork.request.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;

import com.bumptech.glide.RequestManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Administrator on 2017/4/20.
 */
public class OkSupportRequestManagerFragment extends Fragment {

    private OkRequestManager requestManager;
    private OkActivityFragmentLifecycle lifecycle;

    private OkSupportRequestManagerFragment rootRequestManagerFragment;
    private final HashSet<OkSupportRequestManagerFragment> childRequestManagerFragments =
            new HashSet<OkSupportRequestManagerFragment>();
    private final OkSupportFragmentRequestManagerTreeNode requestManagerTreeNode =
            new OkSupportFragmentRequestManagerTreeNode();

    public OkSupportRequestManagerFragment() {
        this(new OkActivityFragmentLifecycle());
    }

    // For testing only.
    @SuppressLint("ValidFragment")
    public OkSupportRequestManagerFragment(OkActivityFragmentLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    /**
     * Sets the current {@link RequestManager}.
     *
     * @param requestManager The manager to set.
     */
    public void setRequestManager(OkRequestManager requestManager) {
        this.requestManager = requestManager;
    }

    OkActivityFragmentLifecycle getLifecycle() {
        return lifecycle;
    }

    /**
     * Returns the current {@link RequestManager} or null if none is set.
     */
    public OkRequestManager getRequestManager() {
        return requestManager;
    }

    /**
     * Returns the {@link OkRequestManagerTreeNode} that provides tree traversal methods relative to the associated
     * {@link RequestManager}.
     */
    public OkRequestManagerTreeNode getRequestManagerTreeNode() {
        return requestManagerTreeNode;
    }

    private void addChildRequestManagerFragment(OkSupportRequestManagerFragment child) {
        childRequestManagerFragments.add(child);
    }

    private void removeChildRequestManagerFragment(OkSupportRequestManagerFragment child) {
        childRequestManagerFragments.remove(child);
    }

    /**
     * Returns the set of fragments that this RequestManagerFragment's parent is a parent to. (i.e. our parent is
     * the fragment that we are annotating).
     */
    public Set<OkSupportRequestManagerFragment> getDescendantRequestManagerFragments() {
        if (rootRequestManagerFragment == null) {
            return Collections.emptySet();
        } else if (rootRequestManagerFragment == this) {
            return Collections.unmodifiableSet(childRequestManagerFragments);
        } else {
            HashSet<OkSupportRequestManagerFragment> descendants =
                    new HashSet<OkSupportRequestManagerFragment>();
            for (OkSupportRequestManagerFragment fragment
                    : rootRequestManagerFragment.getDescendantRequestManagerFragments()) {
                if (isDescendant(fragment.getParentFragment())) {
                    descendants.add(fragment);
                }
            }
            return Collections.unmodifiableSet(descendants);
        }
    }

    /**
     * Returns true if the fragment is a descendant of our parent.
     */
    private boolean isDescendant(Fragment fragment) {
        Fragment root = this.getParentFragment();
        while (fragment.getParentFragment() != null) {
            if (fragment.getParentFragment() == root) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        rootRequestManagerFragment = OkRequestManagerRetriever.get()
                .getSupportRequestManagerFragment(getActivity().getSupportFragmentManager());
        if (rootRequestManagerFragment != this) {
            rootRequestManagerFragment.addChildRequestManagerFragment(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (rootRequestManagerFragment != null) {
            rootRequestManagerFragment.removeChildRequestManagerFragment(this);
            rootRequestManagerFragment = null;
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        lifecycle.onStart();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        lifecycle.onStop();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // If an activity is re-created, onLowMemory may be called before a manager is ever set.
        // See #329.
//        if (requestManager != null) {
//            requestManager.onLowMemory();
//        }
    }

    private class OkSupportFragmentRequestManagerTreeNode implements OkRequestManagerTreeNode {
        @Override
        public Set<OkRequestManager> getDescendants() {
            Set<OkSupportRequestManagerFragment> descendantFragments = getDescendantRequestManagerFragments();
            HashSet<OkRequestManager> descendants = new HashSet<OkRequestManager>(descendantFragments.size());
            for (OkSupportRequestManagerFragment fragment : descendantFragments) {
                if (fragment.getRequestManager() != null) {
                    descendants.add(fragment.getRequestManager());
                }
            }
            return descendants;
        }
    }

    public interface OkRequestManagerTreeNode {
        /**
         * Returns all descendant {@link RequestManager}s relative to the context of the current {@link RequestManager}.
         */
        Set<OkRequestManager> getDescendants();
    }
}
