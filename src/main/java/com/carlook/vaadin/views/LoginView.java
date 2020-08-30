package com.carlook.vaadin.views;

import com.carlook.domain.User;
import com.carlook.repository.UserAuthenticationDAO;
import com.carlook.vaadin.ViewNavigator;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = LoginView.NAME)
@UIScope
public class LoginView extends CustomComponent implements View {

    private UserAuthenticationDAO userAuthenticationDAO;

    public static final String NAME = "";
    private Binder<User> userBinder = new Binder<>();
    private User user = new User("", "");
    private TextField emailTextField = new TextField("Email");
    private PasswordField passwordTextField = new PasswordField("Password");
    private Button signInButton = new Button("Sign in", e ->  signIn(user));
    private Label newUserLabel = new Label("<span style='cursor: pointer; color:blue'>new user?</span>",
            ContentMode.HTML);

    @Autowired
    public void setUserAuthenticationDAO(UserAuthenticationDAO userAuthenticationDAO){

        this.userAuthenticationDAO = userAuthenticationDAO;
    }

    public LoginView(){

        // Initialize and arrange layout components
        HorizontalLayout signUpLayout = new HorizontalLayout(newUserLabel);
        FormLayout logInFormLayout = new FormLayout(emailTextField, passwordTextField,
                                                    signUpLayout, signInButton);
        VerticalLayout logInPageLayout = new VerticalLayout(logInFormLayout);
        logInFormLayout.setSizeUndefined();
        logInPageLayout.setSizeFull();
        logInPageLayout.setComponentAlignment(logInFormLayout, Alignment.TOP_CENTER);

        // Bind the user object to text fields for reading in form inputs
        userBinder.bind(emailTextField, User::getEmail, User::setEmail);
        userBinder.bind(passwordTextField, User::getPassword, User::setPassword);
        userBinder.setBean(user);

        // Set up button and link
        signInButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signUpLayout.addLayoutClickListener(e -> ViewNavigator.navigator.navigateTo(SignUpView.NAME));

        setCompositionRoot(logInPageLayout);
    }

    /**
     * If the user has valid credentials, show the main page; else, show the error message
     * @param userRequest User object encapsulating the input form data, namely user_name and password
     */
    private void signIn(User userRequest){

        if(userAuthenticationDAO.checkAuthentication(userRequest)){

            ViewNavigator.navigator.navigateTo(MainView.NAME);
        } else{

            Notification.show("Invalid user name or password", Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void beforeLeave (ViewBeforeLeaveEvent event){

        // Clear the text fields before redirection
        emailTextField.setValue("");
        passwordTextField.setValue("");
        event.navigate();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
