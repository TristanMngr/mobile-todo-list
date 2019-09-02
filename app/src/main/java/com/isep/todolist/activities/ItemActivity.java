package com.isep.todolist.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.isep.todolist.R;
import com.isep.todolist.Utils.Helper;
import com.isep.todolist.adapters.ItemAdapter;
import com.isep.todolist.adapters.TodoAdapter;
import com.isep.todolist.interfaces.ItemService;
import com.isep.todolist.interfaces.TodoService;
import com.isep.todolist.models.Item;
import com.isep.todolist.models.Todo;
import com.isep.todolist.remote.ServiceGenerator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";

    private ListView itemListView;
    private ArrayAdapter<Item> arrayAdapter;
    private String todoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        todoId = intent.getStringExtra("todoId");

        Log.d(TAG, "super id >>> " + todoId);

        setContentView(R.layout.activity_item);
        itemListView = findViewById(R.id.list_items);

        loadItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.add_todo:
                alertCreationItem();
                return true;
            case R.id.sign_out:
                Helper.removeToken(this);

                Intent myIntent = new Intent(getBaseContext(), AuthenticateActivity.class);
                startActivity(myIntent);
                return true;
                default:
                    Log.d(TAG, "no options");
            return super.onOptionsItemSelected(menuItem);
        }
    }

    private void alertCreationItem() {
        final EditText itemEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("Add a new item")
        .setMessage("What do you want to do next?")
        .setView(itemEditText)
        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemName = String.valueOf(itemEditText.getText());
                createItem(itemName);
            }
        })
        .setNegativeButton("Cancel", null)
        .create();
        dialog.show();
    }

    public void loadItems() {
        ItemService itemService = ServiceGenerator.createService(ItemService.class, Helper.getToken(this));

        itemService.loadItems(todoId).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Item> itemList= new ArrayList<>();
                    for (Item item : response.body()) {
                        itemList.add(item);
                    }

                    if (arrayAdapter == null) {
                        arrayAdapter = new ItemAdapter(getBaseContext(), itemList);
                        itemListView.setAdapter(arrayAdapter);
                    } else {
                        arrayAdapter.clear();
                        arrayAdapter.addAll(itemList);
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d(TAG, "Unable to fetch items");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }

    public void createItem(String name) {

        ItemService itemService = ServiceGenerator.createService(ItemService.class, Helper.getToken(this));

        itemService.create(todoId, name).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful()) {
                    Item item = response.body();
                    loadItems();
                } else {
                    Log.d(TAG, "Unable to create the item");
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }

    public void onClickDoneButton(View view) {
        View parent = (View) view.getParent();
        TextView itemTextView = parent.findViewById(R.id.item_title);
        String itemId = String.valueOf(itemTextView.getTag());
        Log.d(TAG, ">>>>> " + itemId);
        deleteItem(itemId);

        loadItems();
    }


    public void deleteItem(String id) {
        ItemService itemService = ServiceGenerator.createService(ItemService.class, Helper.getToken(this));

        itemService.deleteItem(todoId, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call <ResponseBody>call, Response <ResponseBody>response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Unable to delete Item");
                }
            }

            @Override
            public void onFailure(Call <ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage() + t.getCause());
            }
        });
    }

    public void onLinkTaskClick(View view) {
        Intent myIntent = new Intent(getBaseContext(), TodoActivity.class);
        startActivity(myIntent);
    }
}
