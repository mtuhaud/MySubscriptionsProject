package com.mysubscriptionsproject.config.profile;

import com.mysubscriptionsproject.dto.SubscriptionDto;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.dto.model.Category;
import com.mysubscriptionsproject.dto.model.Formule;
import com.mysubscriptionsproject.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Profile("dev")
public class InitData {

    private final UserService userService;

    public InitData(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void loadMockDatabase() {
        var userOne = new UserDto();
        userOne.setName("John");
        var subscriptionsJohn = new ArrayList<SubscriptionDto>();
        var subscriptionJohnOne = new SubscriptionDto();
        subscriptionJohnOne.setName("Netflix");
        subscriptionJohnOne.setPrice(20);
        subscriptionJohnOne.setFormule(Formule.PREMIUM);
        subscriptionJohnOne.setCategory(Category.SERIES_AND_MOVIES);
        subscriptionsJohn.add(subscriptionJohnOne);
        var subscriptionJohnTwo = new SubscriptionDto();
        subscriptionJohnTwo.setName("Deezer");
        subscriptionJohnTwo.setPrice(10);
        subscriptionJohnTwo.setFormule(Formule.PREMIUM);
        subscriptionJohnTwo.setCategory(Category.MUSIC);
        subscriptionsJohn.add(subscriptionJohnTwo);
        var subscriptionJohnThree = new SubscriptionDto();
        subscriptionJohnThree.setName("Canal");
        subscriptionJohnThree.setPrice(30);
        subscriptionJohnThree.setFormule(Formule.PREMIUM);
        subscriptionJohnThree.setCategory(Category.SERIES_AND_MOVIES);
        subscriptionsJohn.add(subscriptionJohnThree);
        userOne.setSubscriptions(subscriptionsJohn);
        userService.addUser(userOne);

        var userTwo = new UserDto();
        userTwo.setName("Tim");
        var subscriptionsTim = new ArrayList<SubscriptionDto>();
        var subscriptionTimOne = new SubscriptionDto();
        subscriptionTimOne.setName("Amazon Prime");
        subscriptionTimOne.setPrice(20);
        subscriptionTimOne.setFormule(Formule.PREMIUM);
        subscriptionTimOne.setCategory(Category.SERIES_AND_MOVIES);
        subscriptionsTim.add(subscriptionTimOne);
        var subscriptionTimTwo = new SubscriptionDto();
        subscriptionTimTwo.setName("Deezer");
        subscriptionTimTwo.setPrice(10);
        subscriptionTimTwo.setFormule(Formule.PREMIUM);
        subscriptionTimTwo.setCategory(Category.MUSIC);
        subscriptionsTim.add(subscriptionTimTwo);
        var subscriptionTimThree = new SubscriptionDto();
        subscriptionTimThree.setName("Canal");
        subscriptionTimThree.setPrice(30);
        subscriptionTimThree.setFormule(Formule.PREMIUM);
        subscriptionTimThree.setCategory(Category.SERIES_AND_MOVIES);
        subscriptionsTim.add(subscriptionTimThree);
        userTwo.setSubscriptions(subscriptionsTim);
        userService.addUser(userTwo);
    }


}
