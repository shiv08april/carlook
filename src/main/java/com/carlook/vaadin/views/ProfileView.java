package com.carlook.vaadin.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = ProfileView.NAME)
@UIScope
public class ProfileView extends CustomComponent implements View {

    public static final String NAME = "profile";
    private Label label = new Label("Profile page (to be implemented)");

    public ProfileView(){

        // Initialize layout component
        VerticalLayout profileViewLayout = new VerticalLayout(label);

        setCompositionRoot(profileViewLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
