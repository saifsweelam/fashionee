package com.saifsweelam.fashionee.fragments.main;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saifsweelam.fashionee.Authentication;
import com.saifsweelam.fashionee.R;

import java.util.Locale;

public class ProfileFragment extends Fragment {
    Button logoutButton;
    Button switchLanguageButton;

    ImageView userAvatarView;
    TextView userNameView;
    TextView userEmailView;

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Authentication auth = new Authentication(requireContext());

        logoutButton = view.findViewById(R.id.logoutButton);
        switchLanguageButton = view.findViewById(R.id.switchLanguageButton);
        userAvatarView = view.findViewById(R.id.userAvatarView);
        userNameView = view.findViewById(R.id.userNameView);
        userEmailView = view.findViewById(R.id.userEmailView);

        userNameView.setText(auth.getCurrentUserName());
        userEmailView.setText(auth.getCurrentUserEmail());
        Glide.with(this)
                .load(auth.getCurrentUserAvatar())
                .centerCrop()
                .into(userAvatarView);

        logoutButton.setOnClickListener(v -> auth.logout());

        switchLanguageButton.setOnClickListener(v -> {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = getActivity().getBaseContext().getResources().getConfiguration();
            config.setLocale(locale);
            getActivity().getBaseContext().getResources().updateConfiguration(
                    config,
                    getActivity().getBaseContext().getResources().getDisplayMetrics()
            );

            auth.goToMain();
        });

        return view;
    }
}