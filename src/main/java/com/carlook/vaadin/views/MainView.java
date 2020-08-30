package com.carlook.vaadin.views;

import com.carlook.repository.impl.UserAuthenticationDAOSQL;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = MainView.NAME)
@UIScope
public class MainView extends CustomComponent implements View {

    public static final String NAME = "main";
    private String currentUserName;
    private Label label = new Label();

    public MainView(){

        // Initialize layout component
        VerticalLayout mainViewLayout = new VerticalLayout(label);

        setCompositionRoot(mainViewLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){

        currentUserName =
                (String) VaadinSession.getCurrent().getAttribute(UserAuthenticationDAOSQL.AUTHENTICATED_USER_NAME);
        label.setValue("You are logged in, " + currentUserName);
    }
}
