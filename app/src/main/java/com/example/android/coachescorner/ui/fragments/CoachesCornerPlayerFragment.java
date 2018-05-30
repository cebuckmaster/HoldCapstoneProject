package com.example.android.coachescorner.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.coachescorner.R;
import com.example.android.coachescorner.adapter.PlayerAdapter;
import com.example.android.coachescorner.common.Utils;
import com.example.android.coachescorner.data.CoachesCornerDBContract;
import com.example.android.coachescorner.data.Player;
import com.example.android.coachescorner.helpers.PlayerRecyclerItemTouchHelper;
import com.example.android.coachescorner.ui.PlayerDetailActivity;
import com.example.android.coachescorner.ui.dialogs.AddPlayerDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cebuc on 4/16/2018.
 */

public class CoachesCornerPlayerFragment extends Fragment
    implements PlayerRecyclerItemTouchHelper.PlayerItemTouchHelperListener,
                PlayerAdapter.PlayerAdapterOnClickHandler {

    private static final int ID_PLAYER_LOADER = 6;


    private static final String PARCELABLEKEY = "Players";

    private PlayerAdapter mPlayerAdapter;
    public ArrayList<Player> mPlayers;
    @BindView(R.id.recyclerview_player) RecyclerView mPlayerRecyclerView;
    @BindView(R.id.tv_no_players_added) TextView mNoPlayerMsgTextView;
    @BindView(R.id.fab_add_player) FloatingActionButton mAddPlayerFAB;
    @BindView(R.id.player_view) CoordinatorLayout mCoordinatorLayout;
    private Context mContext;
//    private CoordinatorLayout mCoordinatorLayout;


//    private static CoachesCornerPlayerFragment instance = null;
//    public static CoachesCornerPlayerFragment newInstance() {
//        if(instance == null) {
//            instance = new CoachesCornerPlayerFragment();
//        }
//        return instance;
//    }

    public CoachesCornerPlayerFragment() {
        //Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("CoachCornerPlayerFragment", "in OnCreate");

//        setRetainInstance(true);

//        if (savedInstanceState != null) {
//            if (savedInstanceState.containsKey(PARCELABLEKEY)) {
//                mPlayers = savedInstanceState.getParcelableArrayList(PARCELABLEKEY);
//            }
//        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View viewPlayers = inflater.inflate(R.layout.fragment_coaches_corner_players, container, false);
        ButterKnife.bind(this, viewPlayers);

        Log.d("CoachCornerPlayerFragment", "in OnCreateView");

        mContext = viewPlayers.getContext();


//        int numberOfColumns = 1;

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, numberOfColumns);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
//        mPlayerRecyclerView.setLayoutManager(layoutManager);
//        mPlayerRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mPlayerRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
//
//
//        mPlayerAdapter = new PlayerAdapter(mContext, this);
//        mPlayerRecyclerView.setAdapter(mPlayerAdapter);
//
//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new PlayerRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mPlayerRecyclerView);
//
//
//        mAddPlayerFAB = (FloatingActionButton) viewPlayers.findViewById(R.id.fab_add_player);
//
//        mAddPlayerFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getFragmentManager();
//                AddPlayerDialogFragment addPlayerDialog = new AddPlayerDialogFragment();
//                addPlayerDialog.setDialogTitle("Enter in Player Information");
//                addPlayerDialog.show(fragmentManager, "Add Player Dialog");
//            }
//        });
//


//        if (mPlayers != null && !mPlayers.isEmpty()) {
//            showPlayerData();
//            mPlayerAdapter.setPlayers(mPlayers);
//        } else {
//            showAddPlayerMsg();
//        }
//
//        if (savedInstanceState == null || !savedInstanceState.containsKey(PARCELABLEKEY)) {
//            loadPlayerData();
//        } else {
//            mPlayers = savedInstanceState.getParcelableArrayList(PARCELABLEKEY);
//            showPlayerData();
//            mPlayerAdapter.setPlayers(mPlayers);
//        }


        return viewPlayers;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPlayerData();
        Log.d("CoachCornerPlayerFragment", "in OnResume");

    }


//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.d("CoachCornerPlayerFragment", "in OnSaveInstanceState putting Parcable data");
//        outState.putParcelableArrayList(PARCELABLEKEY, mPlayers);
//    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof PlayerAdapter.PlayerViewHolder) {

            String playerName = mPlayers.get(viewHolder.getAdapterPosition()).getPlayerName();

            final Player deletePlayer = mPlayers.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            boolean success = Utils.deletePlayerFromDatabasebyID(mContext, deletePlayer.getPlayerId());

            if (success) {
                mPlayerAdapter.removePlayer(viewHolder.getAdapterPosition());
                Snackbar snackbar = Snackbar.make(mCoordinatorLayout, playerName + " removed from list!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPlayerAdapter.restorePlayer(deletePlayer, deleteIndex);
                        boolean addPlayerSuccess = Utils.addPlayerToDatabase(mContext, deletePlayer);
                        if (!addPlayerSuccess) {
                            Log.d("CoachCornerPlayerFragment", "Failed to add player back from UNDO");
                        }
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }

        }

    }



    private void loadPlayerData() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mPlayerRecyclerView.setLayoutManager(layoutManager);
        mPlayerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPlayerRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));


        mPlayerAdapter = new PlayerAdapter(mContext, this);
        mPlayerRecyclerView.setAdapter(mPlayerAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new PlayerRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mPlayerRecyclerView);

        mAddPlayerFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                AddPlayerDialogFragment addPlayerDialog = new AddPlayerDialogFragment();
                addPlayerDialog.setDialogTitle("Enter in Player Information");
                addPlayerDialog.show(fragmentManager, "Add Player Dialog");
            }
        });




        mPlayers = new ArrayList<>();
        getLoaderManager().initLoader(ID_PLAYER_LOADER, null, loaderCallbackCursorLoader);
    }

    private void showPlayerData() {
        mPlayerRecyclerView.setVisibility(View.VISIBLE);
        mNoPlayerMsgTextView.setVisibility(View.INVISIBLE);
    }

    private void showAddPlayerMsg() {
        mPlayerRecyclerView.setVisibility(View.INVISIBLE);
        mNoPlayerMsgTextView.setVisibility(View.VISIBLE);
    }

//    public void setPlayers(ArrayList<Player> player) {
//        mPlayers = player;
//    }


    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbackCursorLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

            Uri playerUri = CoachesCornerDBContract.PlayerDBEntry.CONTENT_URI;


            return new CursorLoader(mContext,
                    playerUri,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

            if (cursor == null || cursor.getCount() < 1) {
                showAddPlayerMsg();
                return;
            }

            mPlayers.clear();

            while (cursor.moveToNext()) {
                int playerId = cursor.getInt(cursor.getColumnIndex(CoachesCornerDBContract.PlayerDBEntry._ID));
                String playerName = cursor.getString(cursor.getColumnIndex(CoachesCornerDBContract.PlayerDBEntry.COLUMN_PLAYERNAME));
                String playerNum = cursor.getString(cursor.getColumnIndex(CoachesCornerDBContract.PlayerDBEntry.COLUMN_PLAYERNUM));
                String playerPic = cursor.getString(cursor.getColumnIndex(CoachesCornerDBContract.PlayerDBEntry.COLUMN_PLAYERPIC));
                int goalScored = cursor.getInt(cursor.getColumnIndex(CoachesCornerDBContract.ScoreDBEntry.COLUMN_GOALCOUNT));
                int assitsMade = cursor.getInt(cursor.getColumnIndex(CoachesCornerDBContract.ScoreDBEntry.COLUMN_ASSITCOUNT));
                int savesMade = cursor.getInt(cursor.getColumnIndex(CoachesCornerDBContract.ScoreDBEntry.COLUMN_SAVESCOUNT));
                String note = cursor.getString(cursor.getColumnIndex(CoachesCornerDBContract.PlayerDBEntry.COLUMN_PLAYERNOTE));

                mPlayers.add(new Player(playerId, playerName, playerNum, playerPic, goalScored, assitsMade, savesMade, note));
            }

            if (!mPlayers.isEmpty()) {
                showPlayerData();
                mPlayerAdapter.setPlayers(mPlayers);
            } else {
                showAddPlayerMsg();
            }

        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    };

    @Override
    public void onClick(Player player) {
        Intent intentPlayerEdit = new Intent(mContext, PlayerDetailActivity.class);
        intentPlayerEdit.putExtra("Player", player);
        startActivity(intentPlayerEdit);
    }
}
