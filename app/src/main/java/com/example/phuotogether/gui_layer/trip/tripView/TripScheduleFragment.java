package com.example.phuotogether.gui_layer.trip.tripView;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phuotogether.R;
import com.example.phuotogether.businesslogic_layer.trip.tripDestinations.TripDestinationsManager;
import com.example.phuotogether.data_layer.map.GooglePlaceModel;
import com.example.phuotogether.data_layer.map.GoogleResponseModel;
import com.example.phuotogether.data_layer.trip.tripList.Trip;
import com.example.phuotogether.databinding.FragmentTripScheduleBinding;
import com.example.phuotogether.dto.PlannedDestination;
import com.example.phuotogether.gui_layer.trip.destinationList.AddDestinationFragment;
import com.example.phuotogether.gui_layer.trip.destinationList.DestinationListAdapter;
import com.example.phuotogether.gui_layer.trip.destinationList.OnDataFetchedListener;
import com.example.phuotogether.gui_layer.trip.destinationList.OnUpdateDestinationListenr;
import com.example.phuotogether.gui_layer.trip.destinationList.PlaceAutoCompleteAdapter;
import com.example.phuotogether.gui_layer.trip.destinationList.PopularDestinationsAdapter;
import com.example.phuotogether.service.RetrofitAPI;
import com.example.phuotogether.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripScheduleFragment extends Fragment implements OnUpdateDestinationListenr {

    private FragmentTripScheduleBinding binding;
    private Context fragmentContext;
    private static Trip selectedTrip;
    private TripDestinationsManager tripDestinationsManager;
    boolean fragmentAlreadyLoaded = false;
    private List<PlannedDestination> plannedDestinationList;

    public TripScheduleFragment() {
    }

    public static TripScheduleFragment newInstance(Trip trip) {
        TripScheduleFragment fragment = new TripScheduleFragment();
        selectedTrip = trip;
        Log.d("TripScheduleFragment", "newInstance: " + selectedTrip.getArrivalPlace());
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentContext = container.getContext();
        binding= FragmentTripScheduleBinding.inflate(inflater, container, false);
        binding.rvDestinationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripDestinationsManager = TripDestinationsManager.getInstance();

        plannedDestinationList = new ArrayList<>();
        tripDestinationsManager.getDestinationList(selectedTrip.getId(), new TripDestinationsManager.FetchDestinationCallback() {
            @Override
            public void onFetchDestinationResult(boolean success, List<PlannedDestination> destinationList) {
                if (success) {
                    Log.d("TripScheduleFragment", "onFetchDestinationResult: " + destinationList.size());
                    plannedDestinationList.clear();
                    plannedDestinationList.addAll(destinationList);
                    TextView numberDestination = binding.noDestination;
                    numberDestination.setText("Bạn có " + destinationList.size() + " điểm đến trong lịch trình");
                    DestinationListAdapter destinationListAdapter = new DestinationListAdapter(getActivity(),destinationList, tripDestinationsManager);
                    destinationListAdapter.setOnUpdateDestinationListenr(TripScheduleFragment.this);
                    binding.rvDestinationList.setAdapter(destinationListAdapter);
                }
            }
        });
//        Log.e("TripScheduleFragment", "onCreateView: " + plannedDestinationList.size());
//        DestinationListAdapter destinationListAdapter = new DestinationListAdapter(getActivity(),plannedDestinationList);
//        binding.rvDestinationList.setAdapter(destinationListAdapter);


        binding.autocompleteSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.rvDestinationList.setVisibility(View.GONE);
                binding.rvPopularPlaces.setVisibility(View.VISIBLE);
                return false;
            }
        });


        requireActivity().getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        onResume();
                    }
                });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<GooglePlaceModel> popularPlaces = new ArrayList<>();
        fetchPopularPlaces(new OnDataFetchedListener() {
            @Override
            public void onDataFetched(ArrayList<GooglePlaceModel> data) {
                // Xử lý dữ liệu khi nó đã được trả về
                popularPlaces.addAll(data);
                Log.d("SearchPlacesActivity", "onDataFetched: " + popularPlaces.size());

                // Tạo và đặt Adapter cho AutoCompleteTextView
                binding.autocompleteSearch.setThreshold(1);
                PlaceAutoCompleteAdapter adapter = new PlaceAutoCompleteAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, popularPlaces);
                binding.autocompleteSearch.setAdapter(adapter);


                // Bắt sự kiện khi dữ liệu thay đổi
                binding.autocompleteSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // Cập nhật dữ liệu khi người dùng nhập
                        Log.d("SearchPlacesActivity", "afterTextChanged: " + charSequence.toString());
                        adapter.getFilter().filter(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                binding.autocompleteSearch.setOnItemClickListener((parent, view, position, id) -> {
                    GooglePlaceModel place = adapter.getItem(position);
                    binding.autocompleteSearch.setText(place.getName());
                    Log.d("SearchPlacesActivity", "onCreate: " + place.getName());
                    int destinationNo = plannedDestinationList.size() + 1;
                    AddDestinationFragment addDestinationFragment = AddDestinationFragment.newInstance(place, selectedTrip, destinationNo);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.tripschedule, addDestinationFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                });


                // Tạo và đặt Adapter cho RecyclerView
                PopularDestinationsAdapter popularDestinationsAdapter = new PopularDestinationsAdapter(popularPlaces, requireContext(), selectedTrip);
                binding.rvPopularPlaces.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
                binding.rvPopularPlaces.setAdapter(popularDestinationsAdapter);

            }
        });
        Log.e("TripScheduleFragment", "onViewCreated: ");
    }
    @Override
    public void onResume() {
        Log.e("TripScheduleFragment", "onResume: ");
        super.onResume();
        tripDestinationsManager.getDestinationList(selectedTrip.getId(), new TripDestinationsManager.FetchDestinationCallback() {
            @Override
            public void onFetchDestinationResult(boolean success, List<PlannedDestination> destinationList) {
                if (success) {
                    Log.d("TripScheduleFragment", "onFetchDestinationResult: " + destinationList.size());
                    plannedDestinationList.clear();
                    plannedDestinationList.addAll(destinationList);
                    TextView numberDestination = binding.noDestination;
                    numberDestination.setText("Bạn có " + destinationList.size() + " điểm đến trong lịch trình");
                    DestinationListAdapter destinationListAdapter = new DestinationListAdapter(getActivity(),destinationList, tripDestinationsManager);
                    destinationListAdapter.setOnUpdateDestinationListenr(TripScheduleFragment.this);
                    binding.rvDestinationList.setAdapter(destinationListAdapter);
                }
            }
        });
//        if (plannedDestinationList.size() >0 ) {
//            TextView numberDestination = binding.noDestination;
//            numberDestination.setText("Bạn có " + plannedDestinationList.size() + " điểm đến trong lịch trình");
//            binding.rvPopularPlaces.setVisibility(View.GONE);
//            binding.rvDestinationList.setVisibility(View.VISIBLE);
//            DestinationListAdapter destinationListAdapter = new DestinationListAdapter(getActivity(), plannedDestinationList);
//            binding.rvDestinationList.setAdapter(destinationListAdapter);
//        }
    }
    public void fetchPopularPlaces(OnDataFetchedListener listener) {
        RetrofitAPI retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI.class);
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=popular+places+in+" + selectedTrip.getArrivalPlace() + "&key=" + getResources().getString(R.string.place_api_key);
        Log.d("SearchPlacesActivity", "fetchPopularPlaces: " + url);
        retrofitAPI.getPopularPlaces(url).enqueue(new Callback<GoogleResponseModel>() {
            @Override
            public void onResponse(Call<GoogleResponseModel> call, Response<GoogleResponseModel> response) {
                Log.d("SearchPlacesActivity", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    GoogleResponseModel googleResponseModel = response.body();
                    if (googleResponseModel.getError() == null) {
                        ArrayList<GooglePlaceModel> data = new ArrayList<>(googleResponseModel.getGooglePlaceModelList());
                        Log.d("SearchPlacesActivity", "onResponse: " + data.size());
                        listener.onDataFetched(data);
                    }
                    Log.d("SearchPlacesActivity", "onResponse: " + googleResponseModel.getGooglePlaceModelList().size());
                }
                else {
                    Log.d("SearchPlacesActivity", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GoogleResponseModel> call, Throwable t) {
                Log.d("SearchPlacesActivity", "onFailure: " + t.getMessage());
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentContext = null;
    }


    @Override
    public void onUpdateDestination() {
        Log.d("TripScheduleFragment", "onUpdateDestination: ");
        onResume();
    }
}