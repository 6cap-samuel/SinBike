package com.example.sinbike.Activities;

import java.util.ArrayList;
import java.util.List;

import com.example.sinbike.Activities.AnimatedExpandableListView.AnimatedExpandableListAdapter;
import com.example.sinbike.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class CheckFine extends AppCompatActivity implements View.OnClickListener {
    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_fine);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckFine.this , ManageDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final List<GroupItem> items = new ArrayList<>();
        final ArrayList<String> accountId = new ArrayList<>();
        final ArrayList<String> accountId1 = new ArrayList<>();

        // Populate our list with groups and it's children

        for(int i = 1; i < 4; i++) {
        final GroupItem item = new GroupItem();
        item.title = "Parking Fine " + i;
            DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("account");
            accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot result) {
                    for (DataSnapshot dsp : result.getChildren()) {
                        Item listItem = dsp.getValue(Item.class); //add result into array list
                        if (listItem != null) {
                            accountId.add(listItem.getAccountID());
                        }
                    }

                    DatabaseReference fineRef = FirebaseDatabase.getInstance().getReference("parkingFine");
                    fineRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot result) {
                            for (DataSnapshot dsp : result.getChildren()) {
                                Item listItem = dsp.getValue(Item.class); //add result into array list
                                if (listItem != null) {
                                    accountId1.add(listItem.getAccountID());
                                }

                                for(int t = 0; t<accountId1.size(); t++)
                                {
                                    if(accountId.get(t).equals(accountId1.get(t)))
                                    {
                                        List<String> fineDate = new ArrayList<>();
                                        fineDate.add(listItem.getFineDate());

                                        List<String> fineLocation = new ArrayList<>();
                                        fineLocation.add(listItem.getFineLocation());

                                        List<String> fineTime = new ArrayList<>();
                                        fineTime.add(listItem.getFineTime());

                                        List<String> fineAmount = new ArrayList<>();
                                        fineAmount.add(listItem.getFineAmount());

                                        ChildItem child = new ChildItem();
                                        child.fineDate = "Date: " + fineDate.get(t-1);
                                        child.fineLocation = "Location: " + fineLocation.get(t-1);
                                        child.fineTime = "Time: " + fineTime.get(t-1);
                                        child.fineAmount = "Amount: $" + fineAmount.get(t-1);

                                        item.items.add(child);

                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });items.add(item);
        }

        adapter = new ExampleAdapter(this);
        adapter.setData(items);

        listView = (AnimatedExpandableListView) findViewById(R.id.expandableListView);
        listView.setAdapter(adapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
    }

    @Override
    public void onClick(View v) {

    }

    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String fineDate;
        String fineLocation;
        String fineTime;
        String fineAmount;
    }

    private static class ChildHolder {
        TextView fineDate;
        TextView fineLocation;
        TextView fineTime;
        TextView fineAmount;
    }

    private static class GroupHolder {
        TextView title;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                holder.fineDate = (TextView) convertView.findViewById(R.id.textFineDate);
                holder.fineLocation = (TextView) convertView.findViewById(R.id.textFineLocation);
                holder.fineTime = (TextView) convertView.findViewById(R.id.textFineTime);
                holder.fineAmount = (TextView) convertView.findViewById(R.id.textFineAmount);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.fineDate.setText(item.fineDate);
            holder.fineLocation.setText(item.fineLocation);
            holder.fineTime.setText(item.fineTime);
            holder.fineAmount.setText(item.fineAmount);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.listTitle);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }

}
