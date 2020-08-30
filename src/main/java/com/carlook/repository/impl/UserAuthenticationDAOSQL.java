package com.carlook.repository.impl;

import com.carlook.domain.User;
import com.carlook.repository.UserAuthenticationDAO;
import com.carlook.repository.UserRepository;
import com.carlook.repository.exceptions.EmptyFieldException;
import com.carlook.repository.exceptions.NonUniqueUserNameException;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserAuthenticationDAOSQL implements UserAuthenticationDAO {

    // stores the user name during the session; the string serves as the key for the value
    public static final String AUTHENTICATED_USER_NAME = "authenticatedUserName";

    private  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationDAOSQL(UserRepository userRepository, PasswordEncoder passwordEncoder){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAuthentication(User userRequest){

        boolean result = false;

        Optional<User> users = userRepository.findByEmail(userRequest.getEmail());

        if(users.isPresent()){

            User user = users.get();
            if(passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {

                result = true;
                VaadinSession.getCurrent().setAttribute(AUTHENTICATED_USER_NAME, user.getEmail());
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewUser(User userRequest) throws Exception {

        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        // All form fields are required; if any of them is empty, throw an exception.
        if(email.equals("")|| email.equals("") || password.equals("")){

            throw new EmptyFieldException();
        }

        try{
            // If the specified user_name (PK) is available for a new user,
            // insert the user object with encoded password; otherwise, rethrow an exception.
            userRequest.setPassword(passwordEncoder.encode(password));
            userRepository.saveAndFlush(userRequest);
        } catch(DuplicateKeyException e){

            throw new NonUniqueUserNameException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signOut(){

        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }
}
